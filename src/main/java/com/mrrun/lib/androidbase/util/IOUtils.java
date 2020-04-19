package com.mrrun.lib.androidbase.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;

/**
 * <b>类功能描述:</b><br>
 * IO工具类<br>
 * 
 * @author lipin
 * @version 1.0
 */

public class IOUtils {

	private IOUtils() {
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * Close closable object and wrap {@link IOException} with
	 * {@link RuntimeException}
	 * 
	 * @param closeable
	 *            closeable object
	 * @return 无返回值
	 */
	public static void close(Closeable closeable) {
		if (null != closeable) {
			try {
				closeable.close();
			} catch (IOException e) {
				throw new RuntimeException("IOException occurred. ", e);
			}
		}
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * Close closable and hide possible {@link IOException}
	 * 
	 * @param closeable
	 *            closeable object
	 * @return 无返回值
	 */
	public static void closeQuietly(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				throw new RuntimeException("IOException occurred. ", e);
			}
		}
	}

	// 输入流转换成字节数组
	public static byte[] toByteArray(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		copy(input, output);
		return output.toByteArray();
	}

	public static int copy(InputStream input, OutputStream output)
			throws IOException {
		byte[] buffer = new byte[1024 * 4];
		int count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

	public static byte[] ungzip(byte[] bs) throws Exception {
		GZIPInputStream gzin = null;
		ByteArrayInputStream bin = null;
		try {
			bin = new ByteArrayInputStream(bs);
			gzin = new GZIPInputStream(bin);
			return toByteArray(gzin);
		} catch (Exception e) {
			throw e;
		} finally {
			if (bin != null) {
				bin.close();
			}
			if (gzin != null) {
				gzin.close();
			}
		}
	}

	/**
	 * <br>功能简述:将输入流转换为字符串
	 * @param is
	 * @param charset
	 */
	public static String getStringFromInputStream(InputStream is, String charset) {
		StringBuilder builder = new StringBuilder();
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		try {
			if (charset == null) {
				inputStreamReader = new InputStreamReader(is);
			} else {
				inputStreamReader = new InputStreamReader(is, charset);
			}
			reader = new BufferedReader(inputStreamReader);
			//一次只申请少量内存，避免一次性加载完，导致OOM
			int bufferLength;
			char[] tmpBuffer = new char[1024 * 4];
			while ((bufferLength = reader.read(tmpBuffer)) != -1) {
				builder.append(tmpBuffer, 0, bufferLength);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputStreamReader != null) {
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return builder.toString();
	}
}