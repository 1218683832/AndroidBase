package com.mrrun.lib.androidbase.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <b>类功能描述:</b><br>
 * 文件工具类<br>
 *
 * @author lipin
 * @version 1.0
 */
public class FileUtils {

    /**
     * 文件拓展分隔符
     */
    public final static String FILE_EXTENSION_SEPARATOR = ".";

    /**
     * URI类型：file
     */
    public static final String URI_TYPE_FILE = "file";

    private FileUtils() {
    }

    /**
     * <b>方法功能描述:</b><br>
     * 读文件
     *
     * @param filePath    文件路径
     * @param charsetName 指定字符集解码
     * @return 文件不出在返回null，否则返回文件内容
     * @throws RuntimeException 操作出错
     */
    public static StringBuilder readFile(String filePath, String charsetName) {
        File file = new File(filePath);
        StringBuilder fileContent = new StringBuilder();
        if (file == null || !file.isFile()) {
            return null;
        }

        BufferedReader reader = null;
        InputStreamReader is = null;
        try {
            is = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!fileContent.toString().equals("")) {
                    fileContent.append("\r\n");
                }
                fileContent.append(line);
            }
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            IOUtils.close(is);
            IOUtils.close(reader);
        }
    }

    /**
     * <b>方法功能描述:</b><br>
     * 读文件
     *
     * @param filePath    文件路径
     * @param charsetName 指定字符集解码
     * @return 文件不出在返回null，否则返回文件内容 集合
     * @throws RuntimeException 操作出错
     */
    public static List<String> readFileToList(String filePath,
                                              String charsetName) {
        File file = new File(filePath);
        List<String> fileContent = new ArrayList<String>();
        if (file == null || !file.isFile()) {
            return null;
        }

        BufferedReader reader = null;
        InputStreamReader is = null;
        try {
            is = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line = null;
            while ((line = reader.readLine()) != null) {
                fileContent.add(line);
            }
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            IOUtils.close(is);
            IOUtils.close(reader);
        }
    }

    /**
     * <b>方法功能描述:</b><br>
     * 写文件
     *
     * @param filePath 文件路径
     * @param content  内容
     * @param append   是否追加， 如果是, 在原有文件尾部写入内容, 否则清空文件内容再写入
     * @return 如果内容为空或操作出错返回false, 否则 true
     */
    public static boolean writeFile(String filePath, String content,
                                    boolean append) {
        if (StringUtils.isEmpty(content)) {
            return false;
        }

        FileWriter fileWriter = null;
        try {
            makeDirs(filePath);
            fileWriter = new FileWriter(filePath, append);
            fileWriter.write(content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            IOUtils.close(fileWriter);
        }
    }

    /**
     * <b>方法功能描述:</b><br>
     * 写文件
     *
     * @param filePath    文件路径
     * @param contentList 内容集合
     * @param append      是否追加， 如果是, 在原有文件尾部写入内容, 否则清空文件内容再写入
     * @return 如果内容为空或操作出错返回false, 否则 true
     */
    public static boolean writeFile(String filePath, List<String> contentList,
                                    boolean append) {
        if (contentList.size() == 0 || null == contentList) {
            return false;
        }

        FileWriter fileWriter = null;
        try {
            makeDirs(filePath);
            fileWriter = new FileWriter(filePath, append);
            int i = 0;
            for (String line : contentList) {
                if (i++ > 0) {
                    fileWriter.write("\r\n");
                }
                fileWriter.write(line);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            IOUtils.close(fileWriter);
        }
    }

    /**
     * <b>方法功能描述:</b><br>
     * 写文件，内容将被写入到文件的开头
     *
     * @param filePath 文件路径
     * @param content  内容
     * @return 是否写入成功
     */
    public static boolean writeFile(String filePath, String content) {
        return writeFile(filePath, content, false);
    }

    /**
     * <b>方法功能描述:</b><br>
     * 写文件，内容将被写入到文件的开头
     *
     * @param filePath    文件路径
     * @param contentList 内容集合
     * @return 是否写入成功
     */
    public static boolean writeFile(String filePath, List<String> contentList) {
        return writeFile(filePath, contentList, false);
    }

    /**
     * <b>方法功能描述:</b><br>
     * 写文件，内容将被写入到文件的开头
     *
     * @param filePath 文件路径
     * @param stream   输入流
     * @return 返回是否写入成功
     */
    public static boolean writeFile(String filePath, InputStream stream) {
        return writeFile(filePath, stream, false);
    }

    /**
     * <b>方法功能描述:</b><br>
     * 写文件
     *
     * @param filePath 文件路径
     * @param stream   输入流
     * @param append   是否追加， 如果是, 在原有文件尾部写入内容, 否则清空文件内容再写入
     * @return 返回是否写入成功
     */
    public static boolean writeFile(String filePath, InputStream stream,
                                    boolean append) {
        return writeFile(filePath != null ? new File(filePath) : null, stream,
                append);
    }

    /**
     * <b>方法功能描述:</b><br>
     * 写文件
     *
     * @param file   文件对象
     * @param stream 输入流
     * @return 返回是否写入成功
     */
    public static boolean writeFile(File file, InputStream stream) {
        return writeFile(file, stream, false);
    }

    /**
     * <b>方法功能描述:</b><br>
     * 写文件
     *
     * @param file   待写入内容的文件对象
     * @param stream 输入流
     * @param append 是否追加， 如果是, 在原有文件尾部写入内容, 否则清空文件内容再写入
     * @return 如果内容为空或操作出错返回false, 否则 true
     */
    public static boolean writeFile(File file, InputStream stream,
                                    boolean append) {
        if (null == stream) {
            return false;
        }

        OutputStream o = null;
        try {
            makeDirs(file.getAbsolutePath());
            o = new FileOutputStream(file, append);
            byte[] data = new byte[1024];
            int length = -1;
            while ((length = stream.read(data)) != -1) {
                o.write(data, 0, length);
            }
            o.flush();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            IOUtils.close(o);
            IOUtils.close(stream);
        }
    }

    /**
     * <b>方法功能描述:</b><br>
     * 移动文件
     *
     * @param sourceFilePath 源文件路径
     * @param destFilePath   目标文件路径
     * @return 是否移动成功
     */
    public static boolean moveFile(String sourceFilePath, String destFilePath) {
        if (TextUtils.isEmpty(sourceFilePath)
                || TextUtils.isEmpty(destFilePath)) {
            new Throwable(
                    "Both sourceFilePath and destFilePath cannot be null.")
                    .printStackTrace();
            return false;
        }
        return moveFile(new File(sourceFilePath), new File(destFilePath));
    }

    /**
     * <b>方法功能描述:</b><br>
     * 移动文件
     *
     * @param srcFile  源文件对象
     * @param destFile 目标文件对象
     * @return 是否移动成功
     */
    private static boolean moveFile(File srcFile, File destFile) {
        boolean rename = srcFile.renameTo(destFile);
        if (rename) {
            return true;
        }
        if (copyFile(srcFile.getAbsolutePath(), destFile.getAbsolutePath())) {
            return deleteFile(srcFile.getAbsolutePath());
        }
        return false;
    }

    /**
     * <b>方法功能描述:</b><br>
     * 复制文件
     *
     * @param sourceFilePath 源文件路径
     * @param destFilePath   目标文件路径
     * @return 返回是否成功
     * @throws RuntimeException 操作出错
     */
    public static boolean copyFile(String sourceFilePath, String destFilePath) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(sourceFilePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException occurred. ", e);
        }
        return writeFile(destFilePath, inputStream);
    }

    /**
     * @param filePath 文件的路径
     * @return 返回文件的信息
     */
    public static String getFileNameWithoutExtension(String filePath) {

        if (StringUtils.isEmpty(filePath)) {
            return filePath;
        }

        int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePosi = filePath.lastIndexOf(File.separator);
        if (filePosi == -1) {
            return (extenPosi == -1 ? filePath : filePath.substring(0,
                    extenPosi));
        }
        if (extenPosi == -1) {
            return filePath.substring(filePosi + 1);
        }
        return (filePosi < extenPosi ? filePath.substring(filePosi + 1,
                extenPosi) : filePath.substring(filePosi + 1));
    }

    /**
     * get file name from path, include suffix
     *
     * <pre>
     * getFileName(null) = null
     * getFileName("") = ""
     * getFileName(" ") = " "
     * getFileName("a.mp3") = "a.mp3"
     * getFileName("a.b.rmvb") = "a.b.rmvb"
     * getFileName("abc") = "abc"
     * getFileName("c:\\") = ""
     * getFileName("c:\\a") = "a"
     * getFileName("c:\\a.b") = "a.b"
     * getFileName("c:a.txt\\a") = "a"
     * getFileName("/home/admin") = "admin"
     * getFileName("/home/admin/a.txt/b.mp3") = "b.mp3"
     * </pre>
     *
     * @param filePath 路径
     * @return file name from path, include suffix
     */
    public static String getFileName(String filePath) {

        if (StringUtils.isEmpty(filePath)) {
            return filePath;
        }

        int filePosi = filePath.lastIndexOf(File.separator);
        new File(filePath).getName();
        return (filePosi == -1) ? filePath : filePath.substring(filePosi + 1);
    }

    /**
     * get folder name from path
     *
     * <pre>
     * getFolderName(null) = null
     * getFolderName("") = ""
     * getFolderName(" ") = ""
     * getFolderName("a.mp3") = ""
     * getFolderName("a.b.rmvb") = ""
     * getFolderName("abc") = ""
     * getFolderName("c:\\") = "c:"
     * getFolderName("c:\\a") = "c:"
     * getFolderName("c:\\a.b") = "c:"
     * getFolderName("c:a.txt\\a") = "c:a.txt"
     * getFolderName("c:a\\b\\c\\d.txt") = "c:a\\b\\c"
     * getFolderName("/home/admin") = "/home"
     * getFolderName("/home/admin/a.txt/b.mp3") = "/home/admin/a.txt"
     * </pre>
     *
     * @param filePath 路径
     * @return file name from path, include suffix
     */
    public static String getFolderName(String filePath) {

        if (StringUtils.isEmpty(filePath)) {
            return filePath;
        }

        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
    }

    /**
     * get suffix of file from path
     *
     * <pre>
     * getFileExtension(null) = ""
     * getFileExtension("") = ""
     * getFileExtension(" ") = " "
     * getFileExtension("a.mp3") = "mp3"
     * getFileExtension("a.b.rmvb") = "rmvb"
     * getFileExtension("abc") = ""
     * getFileExtension("c:\\") = ""
     * getFileExtension("c:\\a") = ""
     * getFileExtension("c:\\a.b") = "b"
     * getFileExtension("c:a.txt\\a") = ""
     * getFileExtension("/home/admin") = ""
     * getFileExtension("/home/admin/a.txt/b") = ""
     * getFileExtension("/home/admin/a.txt/b.mp3") = "mp3"
     * </pre>
     *
     * @param filePath 路径
     * @return 信息
     */
    public static String getFileExtension(String filePath) {

        if (StringUtils.isBlank(filePath)) {
            return filePath;
        }

        int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePosi = filePath.lastIndexOf(File.separator);
        if (extenPosi == -1) {
            return "";
        }
        return (filePosi >= extenPosi) ? "" : filePath.substring(extenPosi + 1);
    }

    /**
     * <b>方法功能描述:</b><br>
     * 创建文件目录
     *
     * @param filePath 文件路径
     * @return 是否创建成功
     */
    public static boolean makeDirs(String filePath) {
        String folderName = getFolderName(filePath);
        if (StringUtils.isEmpty(folderName)) {
            return false;
        }
        File folder = new File(folderName);
        return (folder.exists() && folder.isDirectory()) || folder
                .mkdirs();
    }

    /**
     * <b>方法功能描述:</b><br>
     * 创建文件夹
     *
     * @param filePath 文件路径
     * @return 是否创建成功
     */
    public static boolean makeFolders(String filePath) {
        return makeDirs(filePath);
    }

    /**
     * <b>方法功能描述:</b><br>
     * 文件或文件目录(夹)是否存在
     *
     * @param path 路径
     * @return true存在, false不存在
     */
    public static boolean exists(String path) {
        if (StringUtils.isBlank(path)) {
            return false;
        }
        return new File(path).exists();
    }

    /**
     * <b>方法功能描述:</b><br>
     * 文件或文件目录(夹)是否存在
     *
     * @param file 文件对象
     * @return true存在, false不存在
     */
    public static boolean exists(File file) {
        if (null == file) {
            return false;
        }
        return file.exists();
    }

    /**
     * <b>方法功能描述:</b><br>
     * 是否是文件
     *
     * @param file 文件对象
     * @return true是文件, false不是文件
     */
    public static boolean isFile(File file) {
        return (FileUtils.exists(file) && file.isFile());
    }

    /**
     * <b>方法功能描述:</b><br>
     * 是否是文件
     *
     * @param path 路径
     * @return true是文件, false不是文件
     */
    public static boolean isFile(String path) {
        return (FileUtils.exists(path) && new File(path).isFile());
    }

    /**
     * <b>方法功能描述:</b><br>
     * 是否是文件目录(夹)
     *
     * @param file 文件对象
     * @return true是文件目录(夹), false不是文件目录(夹)
     */
    public static boolean isDirectory(File file) {
        return (FileUtils.exists(file) && file.isDirectory());
    }

    /**
     * <b>方法功能描述:</b><br>
     * 是否是文件目录(夹)
     *
     * @param path 路径
     * @return true是文件目录(夹), false不是文件目录(夹)
     */
    public static boolean isDirectory(String path) {
        return (FileUtils.exists(path) && new File(path).isDirectory());
    }

    /**
     * <b>方法功能描述:</b><br>
     * 删除文件、删除文件夹
     *
     * @param filePath 文件路径
     * @return 是否删除成功
     */
    public static boolean deleteFile(String filePath) {
        Log.d("deleteFile", "filePath: " + filePath);
        if (StringUtils.isBlank(filePath)) {
            return false;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                deleteFile(f.getAbsolutePath());
            }
        }
        return file.delete();
    }

    /**
     * <b>方法功能描述:</b><br>
     * 获取文件大小
     *
     * @param filePath 文件路径
     * @return 返回文件大小
     */
    public static long getFileSize(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return -1;
        }

        File file = new File(filePath);
        return (file.exists() && file.isFile() ? file.length() : -1);
    }

    /**
     * <b>方法功能描述:</b><br>
     * 获取目录里所有文件大小
     *
     * @param dirPath 文件路径
     * @return 返回文件大小
     */
    public static long getDirSize(String dirPath) {
        if (StringUtils.isBlank(dirPath)) {
            return -1;
        }
        File dirFile = new File(dirPath);
        long size = 0;
        if (dirFile.exists()) {
            for (File file : dirFile.listFiles()) {
                size = size + getFileSize(file.getPath());
            }
        }
        return size;
    }

    /**
     * <b>方法功能描述:</b><br>
     * 保存多媒体数据为文件.
     *
     * @param data     多媒体数据
     * @param fileName 保存文件名
     * @return 保存成功或失败
     */
    public static boolean save2File(InputStream data, String fileName) {
        File file = new File(fileName);
        FileOutputStream fos = null;
        try {
            // 文件或目录不存在时,创建目录和文件.
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            // 写入数据
            fos = new FileOutputStream(file);
            byte[] b = new byte[1024];
            int len;
            while ((len = data.read(b)) > -1) {
                fos.write(b, 0, len);
            }
            return true;
        } catch (IOException ex) {
            return false;
        } finally {
            IOUtils.close(fos);
        }
    }

    /**
     * <b>方法功能描述:</b><br>
     * 读取文件的字节数组.
     *
     * @param file 文件对象
     * @return 字节数组
     */
    public static byte[] readFile4Bytes(File file) {
        // 如果文件不存在,返回空
        if (!file.exists()) {
            return null;
        }
        FileInputStream fis = null;
        try {
            // 读取文件内容.
            fis = new FileInputStream(file);
            byte[] arrData = new byte[(int) file.length()];
            fis.read(arrData);
            // 返回
            return arrData;
        } catch (IOException e) {
            return null;
        } finally {
            IOUtils.close(fis);
        }
    }

    /**
     * <b>方法功能描述:</b><br>
     * 读取文本文件内容，以行的形式读取
     *
     * @param filePathAndName 带有完整绝对路径的文件名
     * @return String 返回文本文件的内容或者null
     */
    public static String readFileContent(String filePathAndName) {
        try {
            return readFileContent(filePathAndName, null, null, 1024);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <b>方法功能描述:</b><br>
     * 读取文本文件内容，以行的形式读取
     *
     * @param filePathAndName 带有完整绝对路径的文件名
     * @param encoding        文本文件打开的编码方式 例如 GBK,UTF-8
     * @param sep             分隔符 例如：#，默认为\n;
     * @param bufLen          设置缓冲区大小
     * @return String 返回文本文件的内容
     */
    public static String readFileContent(String filePathAndName,
                                         String encoding, String sep, int bufLen) {
        if (filePathAndName == null || filePathAndName.equals("")) {
            return null;
        }
        if (sep == null || sep.equals("")) {
            sep = "\n";
        }
        if (!new File(filePathAndName).exists()) {
            return null;
        }
        StringBuffer str = new StringBuffer();
        FileInputStream fs = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            fs = new FileInputStream(filePathAndName);
            if (encoding == null || encoding.trim().equals("")) {
                isr = new InputStreamReader(fs);
            } else {
                isr = new InputStreamReader(fs, encoding.trim());
            }
            br = new BufferedReader(isr, bufLen);

            String data = "";
            while ((data = br.readLine()) != null) {
                str.append(data).append(sep);
            }
        } catch (IOException e) {
            return null;
        } finally {
            IOUtils.close(br);
            IOUtils.close(isr);
            IOUtils.close(fs);
        }
        return str.toString();
    }

    /**
     * <b>方法功能描述:</b><br>
     * 把Assets里的文件拷贝到sd卡上
     *
     * @param assetManager    AssetManager
     * @param fileName        Asset文件名
     * @param destinationPath 完整目标路径
     * @return 拷贝成功
     */
    public static boolean copyAssetToSDCard(AssetManager assetManager,
                                            String fileName, String destinationPath) {
        InputStream is = null;
        FileOutputStream os = null;
        try {
            is = assetManager.open(fileName);
            os = new FileOutputStream(destinationPath);
            if (is != null && os != null) {
                byte[] data = new byte[1024];
                int len;
                while ((len = is.read(data)) > 0) {
                    os.write(data, 0, len);
                }
            }
        } catch (IOException e) {
            return false;
        } finally {
            IOUtils.close(os);
            IOUtils.close(is);
        }
        return true;
    }

    /**
     * <b>方法功能描述:</b><br>
     * 调用系统方式打开文件.
     *
     * @param context 上下文
     * @param file    文件对象
     * @return true打开成功, false打开失败
     */
    public static boolean openFile(Context context, File file) {
        try {
            // 调用系统程序打开文件.
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(
                    Uri.fromFile(file),
                    MimeTypeMap.getSingleton()
                            .getMimeTypeFromExtension(
                                    MimeTypeMap.getFileExtensionFromUrl(file
                                            .getPath())));
            context.startActivity(intent);
            return true;// 打开成功
        } catch (Exception ex) {
            return false;// 打开失败
        }
    }

    /**
     * <b>方法功能描述:</b><br>
     * 根据文件路径，检查文件是否不大于指定大小
     *
     * @param filePath 文件路径
     * @param maxSize  最大
     * @return true不大于指定大小, false大于指定大小
     */
    public static boolean checkFileSize(String filePath, int maxSize) {
        File file = new File(filePath);
        if (!file.exists() || file.isDirectory()) {
            return false;
        }
        return file.length() <= maxSize * 1024;
    }

    /**
     * <b>方法功能描述:</b><br>
     * 打开媒体文件
     *
     * @param context 上下文
     * @param file    文件对象
     * @return true打开成功, false打开失败
     */
    public static boolean openMedia(Context context, File file) {
        if (file.getName().endsWith(".png") || file.getName().endsWith(".jpg")
                || file.getName().endsWith(".jpeg")) {
            return viewPhoto(context, file);
        } else {
            return openFile(context, file);
        }
    }

    /**
     * <b>方法功能描述:</b><br>
     * 打开照片
     *
     * @param context  上下文
     * @param filePath 文件路径
     * @return true打开成功, false打开失败
     */
    public static boolean viewPhoto(Context context, String filePath) {
        return viewPhoto(context, new File(filePath));
    }

    /**
     * <b>方法功能描述:</b><br>
     * 打开照片
     *
     * @param context 上下文
     * @param file    文件对象
     * @return true打开成功, false打开失败
     */
    public static boolean viewPhoto(Context context, File file) {
        try {
            // 调用系统程序打开文件.
            Intent intent = new Intent(Intent.ACTION_VIEW);
            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "image/*");
            context.startActivity(intent);
            return true;// 打开成功
        } catch (Exception ex) {
            return false;// 打开失败
        }
    }

    /**
     * <b>方法功能描述:</b><br>
     * 打开音频文件.
     *
     * @param context 上下文
     * @param file    多媒体文件对象
     * @return true打开成功, false打开失败
     */
    public static boolean playSound(Context context, String file) {
        return playSound(context, new File(file));
    }

    /**
     * <b>方法功能描述:</b><br>
     * 打开音频文件.
     *
     * @param context 上下文
     * @param file    多媒体文件
     * @return true打开成功, false打开失败
     */
    public static boolean playSound(Context context, File file) {
        try {
            // 调用系统程序打开文件.
            Intent intent = new Intent(Intent.ACTION_VIEW);
            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // intent.setClassName("com.android.music",
            // "com.android.music.MediaPlaybackActivity");
            intent.setDataAndType(Uri.fromFile(file), "audio/*");
            context.startActivity(intent);
            return true;// 打开成功
        } catch (Exception ex) {
            return false;// 打开失败
        }
    }

    /**
     * <b>方法功能描述:</b><br>
     * 打开视频文件.
     *
     * @param context 上下文
     * @param file    视频文件对象
     * @return true打开成功, false打开失败
     */
    public static boolean playVideo(Context context, String file) {

        return playVideo(context, new File(file));
    }

    /**
     * <b>方法功能描述:</b><br>
     * 打开视频文件.
     *
     * @param context 上下文
     * @param file    视频文件对象
     * @return true打开成功, false打开失败
     */
    public static boolean playVideo(Context context, File file) {
        try {
            // 调用系统程序打开文件.
            Intent intent = new Intent(Intent.ACTION_VIEW);
            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "video/*");
            context.startActivity(intent);
            return true;// 打开成功
        } catch (Exception ex) {
            return false;// 打开失败
        }
    }

    /**
     * <b>方法功能描述:</b><br>
     * 将字符串以UTF-8编码保存到文件中
     *
     * @param str      保存的字符串
     * @param fileName 文件名字
     * @return 是否保存成功
     */
    public static boolean saveStrToFile(String str, String fileName) {
        return saveStrToFile(str, fileName, "UTF-8");
    }

    /**
     * <b>方法功能描述:</b><br>
     * 将字符串以charsetName编码保存到文件中
     *
     * @param str         保存的字符串
     * @param fileName    文件名字
     * @param charsetName 字符串编码
     * @return 是否保存成功
     */
    public static boolean saveStrToFile(String str, String fileName,
                                        String charsetName) {
        if (str == null || "".equals(str)) {
            return false;
        }

        FileOutputStream stream = null;
        try {
            File file = new File(fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            byte[] b = null;
            if (charsetName != null && !"".equals(charsetName)) {
                b = str.getBytes(charsetName);
            } else {
                b = str.getBytes();
            }

            stream = new FileOutputStream(file);
            stream.write(b, 0, b.length);
            stream.flush();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            IOUtils.close(stream);
        }
    }

    /**
     * <b>方法功能描述:</b><br>
     * 将content://形式的uri转为实际文件路径
     *
     * @param context 上下文
     * @param uri     地址
     * @return uri转为实际文件路径
     */
    public static String uriToPath(Context context, Uri uri) {
        Cursor cursor = null;
        try {
            if (uri.getScheme().equalsIgnoreCase(URI_TYPE_FILE)) {
                return uri.getPath();
            }
            cursor = context.getContentResolver().query(uri, null, null, null,
                    null);
            if (cursor.moveToFirst()) {
                return cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA)); // 图片文件路径
            }
        } catch (Exception e) {
            return null;
        } finally {
            IOUtils.close(cursor);
        }
        return null;
    }

    /**
     * <b>方法功能描述:</b><br>
     * 文件重命名
     *
     * @param oldPath 旧的文件名字
     * @param newPath 新的文件名字
     */
    public static boolean renameFile(String oldPath, String newPath) {
        try {
            if (!TextUtils.isEmpty(oldPath) && !TextUtils.isEmpty(newPath)
                    && !oldPath.equals(newPath)) {
                File fileOld = new File(oldPath);
                File fileNew = new File(newPath);
                fileOld.renameTo(fileNew);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * <b>方法功能描述:</b><br>
     * 获取子文件
     *
     * @param path 路径
     * @return 如果路径不是文件目录(夹)目录返回null, 否则返回list
     */
    public static ArrayList<File> getChildFiles(String path) {
        if (!FileUtils.isDirectory(path)) {// 不是文件目录(夹)
            return null;
        }
        ArrayList<File> list = new ArrayList<File>();
        File[] files = new File(path).listFiles();
        if (ArrayUtils.isEmpty(files)) {
            return list;
        }
        for (File file : files) {
            list.add(file);
        }
        return list;
    }

    /**
     * <b>方法功能描述:</b><br>
     * 获取子文件
     *
     * @param file 文件对象
     * @return 如果路径不是文件目录(夹)目录返回null, 否则返回list
     */
    public static ArrayList<File> getChildFiles(File file) {
        if (!FileUtils.isDirectory(file)) {// 不是文件目录(夹)
            return null;
        }
        ArrayList<File> list = new ArrayList<File>();
        File[] files = file.listFiles();
        if (ArrayUtils.isEmpty(files)) {
            return list;
        }
        for (File f : files) {
            list.add(f);
        }
        return list;
    }
}