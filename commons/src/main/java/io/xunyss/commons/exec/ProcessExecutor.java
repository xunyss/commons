package io.xunyss.commons.exec;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import io.xunyss.commons.io.IOUtils;
import io.xunyss.commons.lang.ArrayUtils;
import io.xunyss.commons.lang.RegularExpressions;

/**
 *
 * @author XUNYSS
 */
public class ProcessExecutor {
	
	/**
	 *
	 */
	private Runtime runtime = Runtime.getRuntime();
	
	private StreamHandler streamHandler;
	
	public void setStreamHandler(StreamHandler streamHandler) {
		this.streamHandler = streamHandler;
	}
	
	public int execute(String... arguments) throws InterruptedException, IOException {
		OutputStream outputStream = System.out;
		
		Process process;
		
		try {
			String[] cmdArray = toCmdArray(arguments);
			process = runtime.exec(cmdArray);
			
			// stdout
			IOUtils.closeQuietly(process.getOutputStream());
			
			// stdin
			InputStream processInputStream = process.getInputStream();
			IOUtils.copy(processInputStream, outputStream);
			IOUtils.closeQuietly(processInputStream);
			
			// stderr
			InputStream processErrorStream = process.getErrorStream();
			IOUtils.copy(processErrorStream, outputStream);
			IOUtils.closeQuietly(processErrorStream);
			
			process.waitFor();
			process.destroy();
			
			// 정상: 0
			System.out.println("exit value: " + process.exitValue());
			return process.exitValue();
		}
		catch (InterruptedException | IOException e) {
			e.printStackTrace();
			throw e;
		}
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
