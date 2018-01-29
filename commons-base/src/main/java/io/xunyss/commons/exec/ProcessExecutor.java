package io.xunyss.commons.exec;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.xunyss.commons.io.IOUtils;
import io.xunyss.commons.lang.ArrayUtils;
import io.xunyss.commons.lang.RegularExpressions;

/**
 *
 * TODO: MS949 output --> UTF-8 구현
 * TODO: console cli 수행 전용 메소드
 * TODO: stdout, stderr 같이 나오게
 * TODO: web-app 에서 테스트 해 봐야 함 (VM 종료 안하는 상태에서)
 * TODO: 여러군데로 output stream 쓰기 (console, 파일, ...)
 * TODO: process result 처리
 * 
 * @author XUNYSS
 */
public class ProcessExecutor {
	
	/**
	 *
	 */
	private static Runtime RUNTIME = Runtime.getRuntime();
	
	/**
	 *
	 */
	private static StreamHandler NULL_STREAM_HANDLER = new NullStreamHandler();
	
	/**
	 *
	 */
	public static int EXITVALUE_NORMAL = 0;
	
	/**
	 * TODO: define value
	 */
	public static int EXITVALUE_NOT_EXITED = 0xdeadbeef;
	
	
	private boolean forceWait = false;
	
	private File workingDirectory = null;
	
	private Environment environment = null;
	
	private StreamHandler streamHandler = null;
	
	
	public ProcessExecutor(boolean forceWait) {
		// 1. launcher thread 미동작시 (ResultHandler 미사용)
		//    streamHandler 가 존재 하면 의미 없음
		// 2. launcher thread 동작시 (ResultHandler 사용)
		//    launcher thread 종료시 까지 block
		this.forceWait = forceWait;
	}
	
	public ProcessExecutor() {
		this(false);
	}
	
	public void setWorkingDirectory(File workingDirectory) {
		this.workingDirectory = workingDirectory;
	}
	
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	
	public void setStreamHandler(StreamHandler streamHandler) {
		this.streamHandler = streamHandler;
	}
	
	
	public int execute(final String... commands) throws ExecuteException {
		try {
			Process process = executeInternal(commands,
					// stream handler 없이 forceWait 가 true 일 경우 NULL_STREAM_HANDLER 를 사용하여 강제 대기
					// forceWait 가 false 이고, stream handler 도 null 일 경우
					// (waitFor 수행하지 않고, process stream close 하지 않을 경우)
					// TODO: waitFor 미수행, process stream close 미수행 상황에 따른
					// TODO: process 미종료, thread 미종료 되는 상황 테스트 필요
					forceWait && streamHandler == null ? NULL_STREAM_HANDLER : streamHandler
			);
			return process.exitValue();
		}
		catch (IllegalThreadStateException ex) {
			// streamHandler == null 일 경우 다음 exception 발생
			// java.lang.IllegalThreadStateException: process has not exited
			return EXITVALUE_NOT_EXITED;
		}
//		catch (ExecuteException ex) {
//			throw ex;
//		}
	}
	
	public void execute(final String[] commands, final ResultHandler resultHandler) throws ExecuteException {
		// launcher thread
		final Thread launcher = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Process process = executeInternal(commands,
							// process 종료까지 대기 시 (ResultHandler 가 있는 경우 반드시 process 종료시 까지 대기)
							// process waitFor 메소드 수행 하고 process 의 stream 들을 모두 close 함
							streamHandler != null ? streamHandler : NULL_STREAM_HANDLER
					);
					
					resultHandler.onProcessComplete(process.exitValue());
				}
				catch (ExecuteException ex) {
					resultHandler.onProcessFailed(ex);
				}
				catch (Exception ex) {
					resultHandler.onProcessFailed(new ExecuteException(ex));
				}
			}
		}, "Process Launcher");
		
		// start launcher thread
		launcher.start();
		
		// wait until launcher thread finish
		if (forceWait) {
			try {
				launcher.join();
			}
			// when current thread is interrupted
			catch (InterruptedException ex) {
				throw new ExecuteException(ex);
			}
		}
	}
	
	public void execute(final String command, final ResultHandler resultHandler) throws ExecuteException {
		execute(ArrayUtils.toArray(command), resultHandler);
	}
	
	private Process executeInternal(String[] commands, StreamHandler streamHandler) throws ExecuteException {
		//------------------------------------------------------------------------------------------
		// execute using Runtime.getRuntime().exec()
		//------------------------------------------------------------------------------------------
		final Process process;
		try {
			process = RUNTIME.exec(
					toProcessCommandArray(commands),							// cmdarray
					environment != null ? environment.toStrings() : null,		// envp
					workingDirectory											// dir
			);
		}
		catch (IOException ex) {
			throw new ExecuteException("Cannot execute process", ex);
		}
		//------------------------------------------------------------------------------------------
		// handle streams
		//------------------------------------------------------------------------------------------
		if (streamHandler != null) {
			// set process streams
			streamHandler.setProcessInputStream(process.getInputStream());
			streamHandler.setProcessErrorStream(process.getErrorStream());
			streamHandler.setProcessOutputStream(process.getOutputStream());
			
			// start handle streams
			streamHandler.start();
			
			try {
				// 스트림을 핸들링 할 경우 process 가 종료할 때 까지 기다림
				process.waitFor();
			}
			catch (InterruptedException ex) {
				process.destroy();
			}
			
			// stop handle streams
			streamHandler.stop();
			
			// close process streams
			IOUtils.closeQuietly(process.getInputStream());
			IOUtils.closeQuietly(process.getErrorStream());
			IOUtils.closeQuietly(process.getOutputStream());
		}
		
		return process;
	}
	
	/**
	 * Build command array.
	 * 
	 * <p> Examples:
	 * <ul>
	 *   <li>exec({"openssl", "asn1parse", "-genstr", "UTF8:\"hello world\""}) -> fail</li>
	 *   <li>exec({"openssl", "asn1parse", "-genstr", "UTF8:\"hello", "world\""}) -> success</li>
	 *   <li>exec("openssl asn1parse -genstr UTF8:\"hello world\"") -> success</li>
	 * </ul>
	 *
	 * @param commands command array
	 * @return new command array
	 * @see Runtime#exec(String, String[], File)
	 */
	private String[] toProcessCommandArray(String[] commands) {
		List<String> commandList = new ArrayList<>();
		for (String command : commands) {
			/*
			 * 따옴표로 감싸진 공백을 포함한 아규먼트 처리
			 * 무조건 " "(공백) 으로 분리하면 {@code Process.exec} 메소드가 다 알아서 함
			 */
//			for (String commandToken : command.split(RegularExpressions.DELIMITER_SPACE)) {
//				commandList.add(commandToken);
//			}
			commandList.addAll(Arrays.asList(command.split(RegularExpressions.DELIMITER_SPACE)));
		}
		return commandList.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
	}
	
	
	//----------------------------------------------------------------------------------------------
	
	/**
	 * StreamHandler that do nothing.
	 *
	 * @author XUNYSS
	 */
	private static class NullStreamHandler extends StreamHandler {
		
		@Override
		void setProcessInputStream(InputStream processInputStream) {
			// do nothing
		}
		
		@Override
		void setProcessErrorStream(InputStream processErrorStream) {
			// do nothing
		}
		
		@Override
		void setProcessOutputStream(OutputStream processOutputStream) {
			// do nothing
		}
		
		@Override
		protected InputStream getProcessInputStream() {
			return null;
		}
		
		@Override
		protected InputStream getProcessErrorStream() {
			return null;
		}
		
		@Override
		protected OutputStream getProcessOutputStream() {
			return null;
		}
		
		@Override
		public void start() {
			// do nothing
		}
		@Override
		public void stop() {
			// do nothing
		}
	}
}
