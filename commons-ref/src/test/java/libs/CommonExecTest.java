package libs;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.junit.Test;

public class CommonExecTest {
	
	@Test
	public void execAsync() throws Exception {
		String command=
		"C:\\xdev\\git\\commons\\openssl\\target\\classes\\io\\xunyss\\openssl\\binary\\win32\\openssl.exe asn1parse -genstr UTF8:\"hello world\""
		;
		
		DefaultExecutor executor = new DefaultExecutor();
		PumpStreamHandler streamHandler = new PumpStreamHandler(System.out);
		executor.setStreamHandler(streamHandler);
		executor.execute(CommandLine.parse(command));
	}
}
