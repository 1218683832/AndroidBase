package com.mrrun.lib.androidbase.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * <b>类功能描述:</b><br>
 * Java utils 实现的Zip工具<br>
 * 
 * @author lipin
 * @version 1.0
 */
public class ZipUtils {
	private static final int BUFF_SIZE = 1024 * 1024; // 1M Byte

	private static boolean stopZipFlag;

	private ZipUtils() {
	}

	public static boolean isStopZipFlag() {
		return stopZipFlag;
	}

	public static void setStopZipFlag(boolean stopZipFlag) {
		ZipUtils.stopZipFlag = stopZipFlag;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 压缩文件
	 * 
	 * @param resFile
	 *            需要压缩的文件（夹）
	 * @param zipout
	 *            压缩流
	 * @param rootpath
	 *            压缩的文件路径
	 */
	private static void zipFile(File resFile, ZipOutputStream zipout,
			String rootpath, ZipListener zipListener) {
		BufferedInputStream in = null;
		try {
			rootpath = rootpath
					+ (rootpath.trim().length() == 0 ? "" : File.separator)
					+ resFile.getName();
			rootpath = new String(rootpath.getBytes("8859_1"), "GB2312");
			if (resFile.isDirectory()) {
				File[] fileList = resFile.listFiles();
				int length = fileList.length;
				// Log.e("zipprogress", (int)((1 / (float)
				// (length+1))*100)+"%");
				zipListener
						.zipProgress((int) ((1 / (float) (length + 1)) * 100));
				for (int i = 0; i < length; i++) {
					if (stopZipFlag) {
						break;
					}
					File file = fileList[i];
					zipFile(file, zipout, rootpath, zipListener);
					// Log.e("zipprogress", (int)(((i+2) / (float)
					// (length+1))*100)+"%");
					zipListener
							.zipProgress((int) (((i + 2) / (float) (length + 1)) * 100));
				}
			} else {
				byte[] buffer = new byte[BUFF_SIZE];
				in = new BufferedInputStream(new FileInputStream(resFile),
						BUFF_SIZE);
				zipout.putNextEntry(new ZipEntry(rootpath));
				int realLength;
				while ((realLength = in.read(buffer)) != -1) {
					if (stopZipFlag) {
						break;
					}
					zipout.write(buffer, 0, realLength);
				}
				zipout.flush();
				zipout.closeEntry();
			}
		} catch (Exception e) {
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(zipout);
		}

	}

	/**
	 * <b>类功能描述:</b><br>
	 * 批量压缩文件（夹）
	 * 
	 * @param resFileList
	 *            要压缩的文件（夹）列表
	 * @param zipFile
	 *            生成的压缩文件
	 * @param zipListener
	 *            zipListener
	 */
	public static void zipFiles(Collection<File> resFileList, File zipFile,
			ZipListener zipListener) {
		ZipOutputStream zipout = null;
		try {
			zipout = new ZipOutputStream(new BufferedOutputStream(
					new FileOutputStream(zipFile), BUFF_SIZE));
			for (File resFile : resFileList) {
				if (stopZipFlag) {
					break;
				}
				zipFile(resFile, zipout, "", zipListener);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(zipout);
		}
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 批量压缩文件（夹）
	 * 
	 * @param resFileList
	 *            要压缩的文件（夹）列表
	 * @param zipFile
	 *            生成的压缩文件
	 * @param comment
	 *            压缩文件的注释
	 * @param zipListener
	 *            zipListener
	 */
	public static void zipFiles(Collection<File> resFileList, File zipFile,
			String comment, ZipListener zipListener) {
		ZipOutputStream zipout = null;
		try {
			zipout = new ZipOutputStream(new BufferedOutputStream(
					new FileOutputStream(zipFile), BUFF_SIZE));
			for (File resFile : resFileList) {
				zipFile(resFile, zipout, "", zipListener);
			}
			zipout.setComment(comment);
			zipout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 解压缩一个文件
	 * 
	 * @param zipFile
	 *            压缩文件
	 * @param folderPath
	 *            解压缩的目标目录
	 */
	public static void upZipFile(File zipFile, String folderPath) {
		File desDir = new File(folderPath);
		if (!desDir.exists()) {
			desDir.mkdirs();
		}
		ZipFile zf = null;
		InputStream in = null;
		OutputStream out = null;
		try {
			zf = new ZipFile(zipFile);
			for (Enumeration<?> entries = zf.entries(); entries
					.hasMoreElements();) {
				ZipEntry entry = ((ZipEntry) entries.nextElement());
				in = zf.getInputStream(entry);
				String str = folderPath + File.separator + entry.getName();
				str = new String(str.getBytes("8859_1"), "GB2312");
				File desFile = new File(str);
				if (!desFile.exists()) {
					File fileParentDir = desFile.getParentFile();
					if (!fileParentDir.exists()) {
						fileParentDir.mkdirs();
					}
					desFile.createNewFile();
				}
				out = new FileOutputStream(desFile);
				byte[] buffer = new byte[BUFF_SIZE];
				int realLength;
				while ((realLength = in.read(buffer)) > 0) {
					out.write(buffer, 0, realLength);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}

	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 解压文件名包含传入文字的文件
	 * 
	 * @param zipFile
	 *            压缩文件
	 * @param folderPath
	 *            目标文件夹
	 * @param nameContains
	 *            传入的文件匹配名
	 * @return 返回的集合
	 */
	@SuppressWarnings("resource")
	public static ArrayList<File> upZipSelectedFile(File zipFile,
			String folderPath, String nameContains) {

		ArrayList<File> fileList = new ArrayList<File>();

		File desDir = new File(folderPath);
		if (!desDir.exists()) {
			desDir.mkdir();
		}

		ZipFile zf = null;
		try {
			zf = new ZipFile(zipFile);
			for (Enumeration<?> entries = zf.entries(); entries
					.hasMoreElements();) {
				ZipEntry entry = ((ZipEntry) entries.nextElement());
				if (entry.getName().contains(nameContains)) {
					InputStream in = zf.getInputStream(entry);
					String str = folderPath + File.separator + entry.getName();
					str = new String(str.getBytes("8859_1"), "GB2312");
					// str.getBytes("GB2312"),"8859_1" 输出
					// str.getBytes("8859_1"),"GB2312" 输入
					File desFile = new File(str);
					if (!desFile.exists()) {
						File fileParentDir = desFile.getParentFile();
						if (!fileParentDir.exists()) {
							fileParentDir.mkdirs();
						}
						desFile.createNewFile();
					}
					OutputStream out = new FileOutputStream(desFile);
					byte[] buffer = new byte[BUFF_SIZE];
					int realLength;
					while ((realLength = in.read(buffer)) > 0) {
						out.write(buffer, 0, realLength);
					}
					IOUtils.closeQuietly(in);
					IOUtils.closeQuietly(out);
					fileList.add(desFile);
				}
			}
			return fileList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获得压缩文件内文件列表
	 * 
	 * @param zipFile
	 *            压缩文件
	 * @return 压缩文件内文件名称
	 */
	public static ArrayList<String> getEntriesNames(File zipFile) {

		ArrayList<String> entryNames = new ArrayList<String>();
		Enumeration<?> entries = null;
		try {
			entries = getEntriesEnumeration(zipFile);
			while (entries.hasMoreElements()) {
				ZipEntry entry = ((ZipEntry) entries.nextElement());
				entryNames.add(new String(getEntryName(entry)
						.getBytes("GB2312"), "8859_1"));
			}
			return entryNames;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获得压缩文件内压缩文件对象以取得其属性
	 * 
	 * @param zipFile
	 *            压缩文件
	 * @return 返回一个压缩文件列表
	 */
	@SuppressWarnings("resource")
	public static Enumeration<?> getEntriesEnumeration(File zipFile) {
		ZipFile zf = null;
		try {
			zf = new ZipFile(zipFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return zf.entries();

	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 取得压缩文件对象的注释
	 * 
	 * @param entry
	 *            压缩文件对象
	 * @return 压缩文件对象的注释
	 */
	public static String getEntryComment(ZipEntry entry) {
		try {
			return new String(entry.getComment().getBytes("GB2312"), "8859_1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 取得压缩文件对象的名称
	 * 
	 * @param entry
	 *            压缩文件对象
	 * @return 压缩文件对象的名称
	 */
	public static String getEntryName(ZipEntry entry) {
		try {
			return new String(entry.getName().getBytes("GB2312"), "8859_1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 取得压缩文件对象的名称
	 * 
	 * @param filePath
	 *            压缩文件的路径
	 * @return 压缩文件对象的名称
	 */
	public static String getEntryName(String filePath) {
		if (FileUtils.exists(filePath)) {
			try {
				return new String(new ZipFile(filePath).getName().getBytes(
						"GB2312"), "8859_1");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * <b>类功能描述:</b><br>
	 * 压缩进度监听器<br>
	 * 
	 * @author lipin
	 * @version 1.0
	 */
	public interface ZipListener {
		void zipProgress(int zipProgress);
	}
}
