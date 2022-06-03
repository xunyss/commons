package io.xunyss.commons;

import java.io.*;

/**
 * 시스템 함수에 관한 유틸
 */

public class LFSystemUtil {

	private LFSystemUtil() {
	}

	private String execCommand(String[] cmd, boolean isWait) throws Exception {
		String result = "";
		Process process = null;

		try {
			process = Runtime.getRuntime().exec(cmd);

			StringWriter stdout = new StringWriter();
			StringWriter stderr = new StringWriter();

			StreamHandler outHandler = new StreamHandler(process.getInputStream(), stdout);
			StreamHandler errHandler = new StreamHandler(process.getErrorStream(), stderr);

			outHandler.setDaemon(true);
			errHandler.setDaemon(true);

			outHandler.start();
			errHandler.start();

			if (isWait) {
				int retVal = process.waitFor();
				int exitValue = process.exitValue();

				if (retVal != 0) {
					throw new Exception(stderr.toString());
				}
			}

			outHandler.interrupt();
			errHandler.interrupt();

			result = stdout.toString();
		}
		catch (IOException ioe) {
			throw new Exception(ioe.getMessage());
		}
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return result;
	}

	public static String execute(String[] cmd, boolean isWait) throws Exception {
		return new LFSystemUtil().execCommand(cmd, isWait);
	}
	public static String execute(String cmd, boolean isWait) throws Exception {
		String[] cmds = {cmd};
		return new LFSystemUtil().execCommand(cmds, isWait);
	}

	public class StreamHandler extends Thread {
		InputStream is;
		Writer writer;

		public StreamHandler(InputStream is, Writer writer) {
			this.is = is;
			this.writer = writer;
		}

		public void run() {
			BufferedReader br = null;

			try {
				br = new BufferedReader(new InputStreamReader(is));
				String line;
				while ((line = br.readLine()) != null) {
					writer.write(line + "\n");
					writer.flush();
					if (Thread.interrupted()) {
						break;
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				try {
					writer.flush();
				}
				catch (IOException ex) {}

				if (br != null) {
					try {
						br.close();
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (is != null) {
					try {
						is.close();
					}
					catch (Exception e) {
						e.printStackTrace();
						is = null;
					}
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		String[] cmd = new String[] {"/bin/sh", "-c", "echo hello"};
		String str = LFSystemUtil.execute(cmd, true);
		System.out.println(str);
	}
}
