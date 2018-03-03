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
 * Execute an external process.
 * 
 * @author XUNYSS
 */
public class ProcessExecutor {
	
	// TODO: console cli 수행 전용 메소드
	// TODO: WebApp 상에서 테스트 필요 (VM 종료 안하는 상태 + 멀티스레드 환경)
	
	
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
	 * 
	 */
	public static int EXITVALUE_NOT_EXITED = 0xdeadbeef;	// TODO: define value
	
	
	private boolean forceWait = false;
	
	private File workingDirectory = null;
	
	private Environment environment = null;
	
	private StreamHandler streamHandler = null;
	
	private Watchdog watchdog = null;
	
	
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
	
	public void setWatchdog(Watchdog watchdog) {
		this.watchdog = watchdog;
	}
	
	
	public int execute(final String[] commands) throws ExecuteException {
		if (commands.length == 0) {
			throw new ExecuteException("Execution command must not be empty");
		}
		
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
	
	public int execute(final String command, final String... commands) throws ExecuteException {
		// 2018.01.31 XUNYSS
		// method signature 변경 - 가변인자 사용하되 적어도 한개 요소는 입력하게 하기 위함
		// (final String... commands) 에서
		// (final String command, final String... commands) 로
		
		return execute(ArrayUtils.add(command, commands));
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
					// cmdarray: 명령어 배열
					toProcessCommandArray(commands),
					// envp: null 일 경우 현재 프로세스의 환경변수를 상속 받음
					environment != null ? environment.toStrings() : null,
					// dir: null 일 경우 현재 디렉토리
					workingDirectory
			);
		}
		catch (IOException ex) {
			throw new ExecuteException("Failed to execute process: " + ArrayUtils.toString(commands), ex);
		}
		
		//------------------------------------------------------------------------------------------
		// start watchdog
		//------------------------------------------------------------------------------------------
		if (watchdog != null) {
			watchdog.startMonitoring(process);	// TODO: RuntimeException 발생 대비
		}
		
		//------------------------------------------------------------------------------------------
		// handle streams
		//------------------------------------------------------------------------------------------
		if (streamHandler != null) {
			// set process streams	// TODO: RuntimeException 발생 대비
			streamHandler.setProcessInputStream(process.getInputStream());
			streamHandler.setProcessErrorStream(process.getErrorStream());
			streamHandler.setProcessOutputStream(process.getOutputStream());
			
			// start handle streams	// TODO: RuntimeException 발생 대비
			streamHandler.start();
			
			try {
				// 스트림을 핸들링 할 경우 process 가 종료할 때 까지 기다림
				process.waitFor();
			}
			catch (InterruptedException ex) {
				process.destroy();
			}
			
			// stop handle streams
			streamHandler.stop();	// TODO: RuntimeException 발생 대비
			
			// close process streams
			IOUtils.closeQuietly(process.getInputStream());
			IOUtils.closeQuietly(process.getErrorStream());
			IOUtils.closeQuietly(process.getOutputStream());
		}
		
		//------------------------------------------------------------------------------------------
		// stop watchdog
		//------------------------------------------------------------------------------------------
		if (watchdog != null) {
			watchdog.stopMonitoring();	// TODO: RuntimeException 발생 대비
		}
		
		//------------------------------------------------------------------------------------------
		// return process
		//------------------------------------------------------------------------------------------
		return process;
	}
	
	/**
	 * Build command array.
	 * 
	 * <p> Examples:
	 * <ul>
	 *   <li>exec({"openssl", "asn1parse", "-genstr", "UTF8:\"hello world\""}) --> fail</li>
	 *   <li>exec({"openssl", "asn1parse", "-genstr", "UTF8:\"hello", "world\""}) --> success</li>
	 *   <li>exec("openssl asn1parse -genstr UTF8:\"hello world\"") --> success</li>
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
//			==>
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
