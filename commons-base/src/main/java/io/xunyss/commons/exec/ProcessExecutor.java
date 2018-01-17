package io.xunyss.commons.exec;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.xunyss.commons.io.IOUtils;
import io.xunyss.commons.lang.ArrayUtils;
import io.xunyss.commons.lang.RegularExpressions;
import io.xunyss.commons.lang.SystemUtils;

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
	
	
	private File workingDirectory = null;
	
	private Environment environment = null;
	
	private StreamHandler streamHandler = null;
	
	
	public ProcessExecutor() {
		
	}
	
	
	public void setWorkingDirectory(File workingDirectory) {
		this.workingDirectory = workingDirectory;
	}
	
	public void setWorkingDirectory(String workingDirectory) {
		this.workingDirectory = new File(workingDirectory);
	}
	
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	
	public void setStreamHandler(StreamHandler streamHandler) {
		this.streamHandler = streamHandler;
	}
	
	public Process execute(String... arguments) throws IOException, InterruptedException {
		final Process process = RUNTIME.exec(
				toCmdArray(arguments),
				Environment.toStrings(environment),
				workingDirectory);
		
		/*
		 * 
		 */
		if (streamHandler != null) {
			// set process streams
			streamHandler.setProcessInputStream(process.getInputStream());
			streamHandler.setProcessErrorStream(process.getErrorStream());
			streamHandler.setProcessOutputStream(process.getOutputStream());
			
			//
			streamHandler.start();
			
			try {
				process.waitFor();
			}
			catch (InterruptedException ie) {
				// 어떤 경우에??
				process.destroy();
			}
			
			//
			streamHandler.stop();
			
			// close process streams
			IOUtils.closeQuietly(process.getOutputStream());
			IOUtils.closeQuietly(process.getInputStream());
			IOUtils.closeQuietly(process.getErrorStream());
		}
		
		// TODO: handle exitValue
		return process;
	}
	
	public Process executeConsole(final String... arguments) throws IOException, InterruptedException {
		String[] commandArguments; 
		if (SystemUtils.IS_OS_WINDOWS) {
			commandArguments = ArrayUtils.add("cmd /c", arguments);
		}
		else {
			// 확인 필요
			commandArguments = ArrayUtils.add("sh -c", arguments);
		}
		
		return execute(commandArguments);
	}
	
	/**
	 * <p>
	 * 따옴표로 감싸진 공백을 포함한 아규먼트 처리.
	 * 무조건 " "(공백) 으로 분리하면 Process.exec 메소드가 다 알아서 함
	 * <p/>
	 *
	 * <pre>
	 * exec({"openssl", "asn1parse", "-genstr", "UTF8:\"hello world\""})    -> 실패
	 * exec({"openssl", "asn1parse", "-genstr", "UTF8:\"hello", "world\""}) -> 성공
	 * exec("openssl asn1parse -genstr UTF8:\"hello world\"")               -> 성공
	 * </pre>
	 *
	 * @param arguments argument array
	 * @return command array
	 *
	 * @see Runtime#exec(String, String[], File)
	 */
	private String[] toCmdArray(String[] arguments) {
		List<String> cmdList = new ArrayList<>();
		for (String argument : arguments) {
			for (String element : argument.split(RegularExpressions.DELIMITER_SPACE)) {
				cmdList.add(element);
			}
		}
		return cmdList.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
	}
}
