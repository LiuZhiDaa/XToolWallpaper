package app.xtoolwallpaper.com.myapplication.utils;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;


import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import app.xtoolwallpaper.com.myapplication.Constants;
import app.xtoolwallpaper.com.myapplication.XApplication;

/**
 */
public class FileUtil {

    public static void writeLog(String strLog) {

        try {
            String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "AFileDemo";
            File dir = new File(filePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(filePath + File.separator + "ALog.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(file, true);

            do {
                fos.write((strLog + "---***---\n").getBytes());
                fos.flush();
            } while (false);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    public static boolean checkFileExist(final String path) {
        if (TextUtils.isEmpty(path))
            return false;

        File file = new File(path);
        return file.exists();
    }

    // 获取文件扩展名
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return "";
    }

    /**
     * 将图片文件加入到相册
     *
     * @param context
     * @param dstPath
     */
    public static void ablumUpdate(final Context context, final String dstPath) {
        if (TextUtils.isEmpty(dstPath) || context == null)
            return;

        File file = new File(dstPath);
        //System.out.println("panyi  file.length() = "+file.length());
        if (!file.exists() || file.length() == 0) {//文件若不存在  则不操作
            return;
        }

        ContentValues values = new ContentValues(2);
        String extensionName = getExtensionName(dstPath);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/" + (TextUtils.isEmpty(extensionName) ? "jpeg" : extensionName));
        values.put(MediaStore.Images.Media.DATA, dstPath);
        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    public static File getSavedShotsFolder() {
        File folder;
        if (TextUtils.equals(Environment.getExternalStorageState(), Environment.MEDIA_MOUNTED)) {
            folder = XApplication.APP.getExternalFilesDir(Constants.VALUE_STR_SAVE_PIC_FILE_PATH_NAME);
        } else {
            folder = new File(Environment.getDataDirectory(), Constants.VALUE_STR_SAVE_PIC_FILE_PATH_NAME);
        }
        if (folder == null || !folder.exists() && !folder.mkdirs()) {
            return null;
        }
        return folder;
    }

    /**
     *  
     *
     * @param
     * @param dir      存储目录
     * @return
     */
    public static String getFilePath( String dir) {
        String directoryPath = "";
//判断SD卡是否可用 
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            directoryPath = XApplication.APP.getExternalFilesDir(dir).getAbsolutePath();
// directoryPath =context.getExternalCacheDir().getAbsolutePath() ;  
        } else {
//没内存卡就存机身内存  
            directoryPath = XApplication.APP.getFilesDir() + File.separator + dir;
// directoryPath=context.getCacheDir()+File.separator+dir;
        }
        File file = new File(directoryPath);
        if (!file.exists()) {//判断文件目录是否存在  
            file.mkdirs();
        }

        return directoryPath;
    }


    public static File getOutputMediaFile() {
        File mediaStorageDir = getSavedShotsFolder();
        if (mediaStorageDir == null) {
            return null;
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINESE).format(new Date());
        String newExtension;

        newExtension = "." + "png";

        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + newExtension);

        return mediaFile;
    }

    public static File getSavedCacheFolder() {
        File folder;
        if (TextUtils.equals(Environment.getExternalStorageState(), Environment.MEDIA_MOUNTED)) {
            folder = XApplication.APP.getExternalFilesDir(Constants.VALUE_STR_SAVE_PIC_FILE_PATH_NAME);
        } else {
            folder = new File(Environment.getDataDirectory(), Constants.VALUE_STR_SAVE_PIC_FILE_PATH_NAME);
        }
        if (folder == null || !folder.exists() && !folder.mkdirs()) {
            return null;
        }
        return folder;
    }

//    public static File getSavedTempFolder() {
//        File folder;
//        if (TextUtils.equals(Environment.getExternalStorageState(), Environment.MEDIA_MOUNTED)) {
//            folder = XApplication.APP.getExternalFilesDir(Constant.VALUE_STR_SAVE_TEMP_FILE_PATH_NAME);
//        } else {
//            folder = new File(Environment.getDataDirectory(), Constant.VALUE_STR_SAVE_TEMP_FILE_PATH_NAME);
//        }
//        if (folder == null || !folder.exists() && !folder.mkdirs()) {
//            return null;
//        }
//        return folder;
//    }


    /**
     * 保存缓存的临时图片
     *
     * @param imgName
     * @param bitmap
     */
    public static String saveCacheImgFile(String imgName, Bitmap bitmap) {
        if (TextUtils.isEmpty(imgName) || bitmap == null || bitmap.isRecycled()) {
            return null;
        }
        return saveBitmapToFile(FileUtil.getSavedCacheFolder(), imgName, bitmap);
    }

    /**
     * 保存Bitmap到指定文件
     *
     * @param folder
     * @param fileName
     * @param bitmap
     * @return 返回保存之后的文件完整路径
     */
    public static String saveBitmapToFile(File folder, String fileName, Bitmap bitmap) {
        if (folder == null || fileName == null || fileName.trim().length() == 0 || bitmap == null || bitmap.isRecycled()) {
            return null;
        }
        if (!folder.exists() && !folder.mkdirs()) {
            return null;
        }
        File file = new File(folder, fileName);
        if (file.exists() && !file.delete()) {
            return null;
        }
        FileOutputStream fos = null;
        try {
            if (!file.createNewFile()) {
                return null;
            }
            fos = new FileOutputStream(file);
            Bitmap.CompressFormat compressFormat;
            if (fileName.toLowerCase().endsWith(".png")) {
                compressFormat = Bitmap.CompressFormat.PNG;
            } else {
                compressFormat = Bitmap.CompressFormat.JPEG;
            }
            bitmap.compress(compressFormat, 100, fos);
            fos.flush();
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static boolean deleteDirectory(String dir) {
        //如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }
        File dirFile = new File(dir);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            System.out.println("删除目录失败" + dir + "目录不存在！");
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
            //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }

        if (!flag) {
            System.out.println("删除目录失败");
            return false;
        }

        //删除当前目录
        if (dirFile.delete()) {
            System.out.println("删除目录" + dir + "成功！");
            return true;
        } else {
            System.out.println("删除目录" + dir + "失败！");
            return false;
        }
    }

    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.isFile() && file.exists()) {
            file.delete();
            System.out.println("删除单个文件" + fileName + "成功！");
            return true;
        } else {
            System.out.println("删除单个文件" + fileName + "失败！");
            return false;
        }
    }
}//end class
