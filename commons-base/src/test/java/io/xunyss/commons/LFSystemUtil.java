package io.xunyss.commons;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 시스템 함수에 관한 유틸
 */

public class LFSystemUtil {

	private LFSystemUtil() {
	}

	private String execCommand(String[] cmd, boolean isWait) throws Exception {
		String result = "";

		try {
			Process process = Runtime.getRuntime().exec(cmd);
			if (isWait) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				ByteArrayOutputStream err = new ByteArrayOutputStream();
				StreamHandler streamHandler = new StreamHandler(process.getInputStream(), process.getErrorStream(), out, err);

				streamHandler.start();
				process.waitFor();
				streamHandler.stop();

				int exitValue = process.exitValue();
				if (exitValue != 0) {
//					throw new Exception(stderr.toString());
				}
				result = out.toString();
			}
			else {
				result = "";
			}

			closeQuietly(process.getInputStream());
			closeQuietly(process.getErrorStream());
			closeQuietly(process.getOutputStream());
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

	private static class StreamHandler {
		private InputStream procIn, procErr;
		private OutputStream sout, serr;
		private Thread outputThread, errorThread;
		public StreamHandler(InputStream procIn, InputStream procErr, OutputStream sout, OutputStream serr) {
			this.procIn = procIn;
			this.procErr = procErr;
			this.sout = sout;
			this.serr = serr;
		}
		public void start() {
			outputThread = startPump(procIn, sout);
			errorThread = startPump(procErr, serr);
		}
		public void stop() {
			stopPump(outputThread);
			stopPump(errorThread);
		}
		private Thread startPump(InputStream in, OutputStream out) {
			Thread t = new Thread(new Pumper(in, out));
			t.start();
			return t;
		}
		private void stopPump(Thread t) {
			try {
				t.join();
			} catch (InterruptedException ex) {
				t.interrupt();
			}
		}
	}

	private static class Pumper implements Runnable {
		private InputStream is;
		private OutputStream os;
		public Pumper(InputStream is, OutputStream os) {
			this.is = is;
			this.os = os;
		}
		public void run() {
			try {
				final byte[] buffer = new byte[4096];
				int len;
				while ((len = is.read(buffer)) > -1) {
					os.write(buffer, 0, len);
					if (Thread.interrupted()) {
						break;
					}
				}
			}
			catch (IOException ignored) {
				ignored.printStackTrace();
			}
			finally {
				try {
					os.flush();
				} catch (IOException ignored) {}
				closeQuietly(os);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		final String[] cmd = new String[] {"/bin/sh", "-c", "pwd"};

		ExecutorService executorService = Executors.newFixedThreadPool(100);
		for(int i = 0; i < 100; i++) {
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					try {
						String str = LFSystemUtil.execute(cmd, true);
						System.out.println("result: " + str);
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}

		executorService.shutdown();
	}

	private static void closeQuietly(Closeable c) {
		if (c != null) {
			try {
				c.close();
			}
			catch (IOException ignored) {}
		}
	}
}
