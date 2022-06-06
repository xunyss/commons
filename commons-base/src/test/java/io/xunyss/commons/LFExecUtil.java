package io.xunyss.commons;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LFExecUtil {

	private static class LLog {
		public static class info {
			public static void println(Object o) {
//				System.out.println(o);
			}
		}
		public static class err {
			public static void println(Object o) {
//				System.out.println(o);
			}
		}
	}
	private static class DevonException extends Exception {
		public DevonException(String msg) {
			super(msg);
		}
	}

	private LFExecUtil() {

	}

	/**
	 * <pre>
	 * 명령을 실행한다.
	 * </pre>
	 *
	 * 사용예)<br>
	 * 			String[] cmd = {"/bin/sh", "-c", "sar 1 1 | tail -1 | awk '{print $5}'"};<br>
	 * 			LFSystemUtil.execute(cmd, true);<br>
	 *
	 *
	 * <br>
	 * @param cmd 명령어
	 * @param isWait process의 terminate 를 기다리는지 여부
	 * @return void
	 * @throws DevonException
	 */
	public static String execute(String[] cmd, boolean isWait) throws DevonException {
		String result = "";
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(cmd);

			if (isWait) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				ByteArrayOutputStream err = new ByteArrayOutputStream();
				StreamHandler streamHandler = new StreamHandler(process.getInputStream(), process.getErrorStream(), out, err);

				int retVal = 0, exitValue;

				try {
					streamHandler.start();
					retVal = process.waitFor();
				}
				catch (InterruptedException ex) {
					process.destroy();
				}
				finally {
					streamHandler.stop();
				}
				exitValue = process.exitValue();

				LLog.info.println("=========================================");
				for (int i = 0; i < cmd.length; i++) {
					LLog.info.println("cmd[" + i + "] : " + cmd[i]);
				}
				LLog.info.println("return value : " + retVal);
				LLog.info.println("exit   value : " + exitValue);
				LLog.info.println("=========================================");

				if (exitValue != 0) {
					LLog.err.println("stderr : " + err);
					throw new DevonException(err.toString());
				}
				result = out.toString();
			}
		}
		catch (IOException e) {
			throw new DevonException(e.getMessage());
		}
		finally {
			if (process != null) {
				closeQuietly(process.getInputStream());
				closeQuietly(process.getErrorStream());
				closeQuietly(process.getOutputStream());
			}
		}

		return result;
	}

	/**
	 * <pre>
	 * 명령을 실행한다.
	 * </pre>
	 *
	 * 사용예)<br>
	 * 			LFSystemUtil.execute("sh /weblogic/cbs/cbsDomain01/shl/tmp_stat.sh, true);<br>
	 *
	 *
	 * <br>
	 * @param cmd 명령어
	 * @param isWait process의 terminate 를 기다리는지 여부
	 * @return void
	 * @throws DevonException
	 */
	public static String execute(String cmd, boolean isWait) throws DevonException {
		return execute(new String[] {cmd}, isWait);
	}

	private static class StreamHandler {
		private final InputStream procIn, procErr;
		private final OutputStream sout, serr;
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
			}
			catch (InterruptedException ex) {
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

		@Override
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
			catch (IOException ex) {
				ex.printStackTrace();
			}
			finally {
				try {
					os.flush();
				} catch (IOException ignored) {}
				closeQuietly(os);
			}
		}
	}

	private static void closeQuietly(Closeable c) {
		if (c != null) {
			try {
				c.close();
			}
			catch (IOException ignored) {}
		}
	}


	public static void main(String[] args) {
		final String[] command = {"/bin/sh", "-c", "pwd"};
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 100; i++) {
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					try {
						String ret = LFExecUtil.execute(command, true);
						System.out.println("ret: " + ret);
					}
					catch (DevonException ex) {
						ex.printStackTrace();
					}
				}
			});
		}

		executorService.shutdown();
	}
}
