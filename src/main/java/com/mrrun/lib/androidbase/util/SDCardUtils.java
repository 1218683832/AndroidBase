package com.mrrun.lib.androidbase.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 
 * <b>类功能描述:</b><br>
 * 存储卡操作工具类
 * 
 * @author lipin
 * @version 1.0
 * 
 */
public class SDCardUtils {

	/**
	 * <b>属性描述:</b><br>
	 * 根目录
	 */
	public static final String ROOTDIR = "/";

	// #################################################################################
	// #####################-------以下方法针对SDCard操作-------##############################
	// #################################################################################
	/**
	 * <b>方法功能描述:</b><br>
	 * 判断SD卡是否被挂载
	 * 
	 * @return
	 * 
	 * @see Environment#getExternalStorageState()
	 */
	public static boolean isSDCardMounted() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取SD卡的根目录
	 * 
	 * @return
	 * 
	 * @see Environment#getExternalStorageDirectory()
	 */
	public static String getSDCardBaseDir() {
		if (isSDCardMounted()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath();
		}
		return null;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取SD卡总容量空间大小，返回MB
	 * 
	 * @return long数值类型
	 * 
	 * @see StatFs
	 */
	public static long getSDCardTotalSize() {
		if (isSDCardMounted()) {
			StatFs fs = new StatFs(getSDCardBaseDir());
			long count = fs.getBlockCountLong();
			long size = fs.getBlockSizeLong();
			return count * size / 1024 / 1024;
		}
		return 0;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取SD卡剩余空间大小，返回MB<br>
	 * 等同于{@link #getSDCardAvailableSize()}
	 * 
	 * @return long数值类型
	 * 
	 * @see StatFs
	 */
	public static long getSDCardFreeSize() {
		if (isSDCardMounted()) {
			StatFs fs = new StatFs(getSDCardBaseDir());
			long count = fs.getFreeBlocksLong();
			long size = fs.getBlockSizeLong();
			return count * size / 1024 / 1024;
		}
		return 0;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取SD卡可用空间大小，返回MB<br>
	 * 等同于{@link #getSDCardAvailableSize()}
	 * 
	 * @return long数值类型
	 * 
	 * @see StatFs
	 */
	public static long getSDCardAvailableSize() {
		if (isSDCardMounted()) {
			StatFs fs = new StatFs(getSDCardBaseDir());
			long count = fs.getAvailableBlocksLong();
			long size = fs.getBlockSizeLong();
			return count * size / 1024 / 1024;
		}
		return 0;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取SD卡公有目录的路径
	 * 
	 * @param type
	 *            目录名称
	 * @return null or SD卡公有目录的路径
	 */
	public static String getSDCardPublicDir(String type) {
		return Environment.getExternalStoragePublicDirectory(type)
				.getAbsolutePath();
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取SD卡私有Cache目录的路径
	 * 
	 * @param context
	 * @return null or SD卡私有Cache目录的路径
	 */
	public static String getSDCardPrivateCacheDir(Context context) {
		return context.getExternalCacheDir().getAbsolutePath();
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取SD卡私有Files目录的路径
	 * 
	 * @param context
	 * @param type
	 *            目录名称
	 * @return null or SD卡私有Files目录的路径
	 */
	public static String getSDCardPrivateFilesDir(Context context, String type) {
		return context.getExternalFilesDir(type).getAbsolutePath();
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 往SD卡公有目录下保存文件
	 * 
	 * @param data
	 * @param type
	 *            目录名称
	 * @param fileName
	 *            保存的文件名称
	 * @return 保存成功true, 保存失败false
	 */
	public static boolean saveFileToSDCardPublicDir(byte[] data, String type,
			String fileName) {
		if (isSDCardMounted()) {
			String filePath = getSDCardPublicDir(type);
			if (!FileUtils.exists(filePath)) {
				if (!FileUtils.makeFolders(filePath)) {
					return false;
				}
			}
			BufferedOutputStream bos = null;
			try {
				bos = new BufferedOutputStream(new FileOutputStream(new File(
						filePath, fileName)));
				bos.write(data);
				bos.flush();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				IOUtils.close(bos);
			}
		}
		return false;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 往SD卡自定义目录下保存文件
	 * 
	 * @param data
	 * @param dir
	 *            目录名称
	 * @param fileName
	 *            保存的文件名称
	 * @return
	 */
	public static boolean saveFileToSDCardCustomDir(byte[] data, String dir,
			String fileName) {
		if (isSDCardMounted()) {
			String filePath = getSDCardBaseDir() + File.separator + dir;
			if (!FileUtils.exists(filePath)) {
				if (!FileUtils.makeFolders(filePath)) { // 创建自定义目录
					return false;
				}
			}
			BufferedOutputStream bos = null;
			try {
				bos = new BufferedOutputStream(new FileOutputStream(new File(
						filePath, fileName)));
				bos.write(data);
				bos.flush();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				IOUtils.close(bos);
			}
		}
		return false;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 往SD卡的私有Files目录下保存文件
	 * 
	 * @param data
	 * @param type
	 *            目录名称
	 * @param fileName
	 * @param context
	 * @return
	 */
	public static boolean saveFileToSDCardPrivateFilesDir(byte[] data,
			String type, String fileName, Context context) {
		if (isSDCardMounted()) {
			String filePath = getSDCardPrivateFilesDir(context, type);
			if (!FileUtils.exists(filePath)) {
				if (!FileUtils.makeFolders(filePath)) { // 创建自定义目录
					return false;
				}
			}
			BufferedOutputStream bos = null;
			try {
				bos = new BufferedOutputStream(new FileOutputStream(new File(
						filePath, fileName)));
				bos.write(data);
				bos.flush();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				IOUtils.close(bos);
			}
		}
		return false;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 往SD卡的私有Cache目录下保存文件
	 * 
	 * @param data
	 * @param fileName
	 * @param context
	 * @return
	 */
	public static boolean saveFileToSDCardPrivateCacheDir(byte[] data,
			String fileName, Context context) {
		if (isSDCardMounted()) {
			String filePath = getSDCardPrivateCacheDir(context);
			if (!FileUtils.exists(filePath)) {
				if (!FileUtils.makeFolders(filePath)) { // 创建自定义目录
					return false;
				}
			}
			BufferedOutputStream bos = null;
			try {
				bos = new BufferedOutputStream(new FileOutputStream(new File(
						filePath, fileName)));
				bos.write(data);
				bos.flush();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				IOUtils.close(bos);
			}
		}
		return false;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 保存bitmap图片到SDCard的私有Cache目录
	 * 
	 * @param bitmap
	 * @param fileName
	 * @param context
	 * @return
	 */
	public static boolean saveBitmapToSDCardPrivateCacheDir(Bitmap bitmap,
			String fileName, Context context) {
		if (isSDCardMounted()) {
			String filePath = getSDCardPrivateCacheDir(context);
			if (!FileUtils.exists(filePath)) {
				if (!FileUtils.makeFolders(filePath)) { // 创建自定义目录
					return false;
				}
			}
			BufferedOutputStream bos = null;
			try {
				bos = new BufferedOutputStream(new FileOutputStream(new File(
						filePath, fileName)));
				if (fileName != null
						&& (fileName.contains(".png") || fileName
								.contains(".PNG"))) {
					bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
				} else {
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
				}
				bos.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				IOUtils.close(bos);
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 从SD卡获取文件
	 * 
	 * @param filePah
	 * @return null or byte[]
	 */
	public static byte[] loadFileFromSDCard(String filePah) {
		BufferedInputStream bis = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			bis = new BufferedInputStream(
					new FileInputStream(new File(filePah)));
			byte[] buffer = new byte[8 * 1024];
			int c = 0;
			while ((c = bis.read(buffer)) != -1) {
				baos.write(buffer, 0, c);
				baos.flush();
			}
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(baos);
			IOUtils.close(bis);
		}
		return null;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 从SDCard中寻找指定目录下的文件，返回Bitmap
	 * 
	 * @param filePath
	 *            文件路径
	 * @return null or Bitmap
	 */
	public Bitmap loadBitmapFromSDCard(String filePath) {
		byte[] data = loadFileFromSDCard(filePath);
		if (data != null) {
			Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
            return bm;
		}
		return null;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 从sdcard中删除文件、删除文件夹
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 是否删除成
	 */
	public static boolean removeFileFromSDCard(String filePath) {
		return FileUtils.deleteFile(filePath);
	}

	// #################################################################################
	// #####################-------以下方法针对系统内部存储操作-------############################
	// #################################################################################
	/**
	 * <b>方法功能描述:</b><br>
	 * 获取Android系统的"system"分区目录的路径(内部存储)
	 * 
	 * @return 返回Android系统的核心"system"分区目录路径,总是呈现并是只读目录. "/system"
	 */
	public static String getOSSystemDir() {
		return Environment.getRootDirectory().getAbsolutePath();
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取Android系统的"data"分区目录的路径(内部存储)
	 * 
	 * @return 返回Android系统的核心"data"分区目录路径. "/data"
	 */
	public static String getOSDataDir() {
		return Environment.getDataDirectory().getAbsolutePath();
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取Android系统的"cache"分区目录的路径(内部存储)
	 * 
	 * @return 返回Android系统的"cache"分区目录路径. "/cache"
	 */
	public static String getOSDownloadCacheDir() {
		return Environment.getDownloadCacheDirectory().getAbsolutePath();
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取该应用在系统内部存储中的files目录的路径
	 * 
	 * @param context
	 * @return 返回该应用在系统内部存储中的"files"目录路径. "/data/data/包名/files"
	 */
	public static String getFilesDir(Context context) {
		return context.getFilesDir().getAbsolutePath();
	}
	
	/**<b>方法功能描述:</b><br>
	 * 获取该应用在系统内部存储中的cache目录的路径
	 * 
	 * @param context
	 * @return 返回该应用在系统内部存储中的"files"目录路径. "/data/data/包名/cache"
	 */
	public static String getCacheDir(Context context) {
		return context.getCacheDir().getAbsolutePath();
	}
}