package com.oncloudsoft.sdk.utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;

import com.oncloudsoft.sdk.yunxin.uikit.common.util.string.StringUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {

    public static int FILETYPE_IMAGE = 1;//文件类型 图片
    public static int FILETYPE_VIDEO = 2;//文件类型 视频
    public static int FILETYPE_AUDIO = 3;//文件类型 音频

    /**
     * 获取文件时长
     *
     * @param file
     * @return
     */
    public static long getFileLength(File file) {
        long time;
        MediaPlayer meidaPlayer = new MediaPlayer();

        try {
//            FileInputStream fis = new FileInputStream(file);

            meidaPlayer.setDataSource(file.getPath());
            meidaPlayer.prepare();
            time = meidaPlayer.getDuration();

        } catch (IOException e) {
            e.printStackTrace();
            time = -1;
        }
        return time;
    }

    /**
     * 获取音频文件路径
     *
     * @return
     */
    public static String getAuditTempt() {
        return getSongjiangStoragePath() + "/record/audit";
    }

    /**
     * 获取视屏文件路径
     *
     * @return
     */
    public static String getVideoTempt() {
        return getSongjiangStoragePath() + "/record/video";
    }

    /**
     * 获取视屏文件路径
     *
     * @return
     */
    public static String getImageTempt() {
        return getSongjiangStoragePath() + "/record/image";
    }

    /**
     * 创建松江的统一创建文件夹
     *
     * @param type
     * @return
     */
    public static String createSongJiangFile(int type) {
        String path;
        String fileName;

        if (type == FILETYPE_AUDIO) {
            path = FileUtil.getAuditTempt();
            fileName = ".mp3";
        } else if (type == FILETYPE_VIDEO) {
            path = FileUtil.getVideoTempt();
            fileName = ".mp4";
        } else if (type == FILETYPE_IMAGE) {
            path = FileUtil.getImageTempt();
            fileName = ".jpg";
        } else {
            path = FileUtil.getImageTempt();
            fileName = ".jpg";
        }
        FileUtil.creatFile(path);

        path = path + "/" + StringUtil.get32UUID() + fileName;
        return path;
    }


    public static File creatFile(String path) {
        //新建一个File，传入文件夹目录
        File file = new File(path);

        //判断文件夹是否存在，如果不存在就创建，否则不创建
        //
        if (!file.exists()) {
            //通过file的mkdirs()方法创建目录中包含却不存在的文件夹
            file.mkdirs();
        }
        return file;
    }


    /**
     * 获取手机的文件根目录
     *
     * @return
     */
    public static String getPhoneStoragePath() {
        return Environment.getExternalStoragePublicDirectory("") + "";
    }

    public static String getSongjiangStoragePath() {
        return getPhoneStoragePath() + "/songjiang";
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
//                Log.i(TAG,"isExternalStorageDocument***"+uri.toString());
//                Log.i(TAG,"docId***"+docId);
//                以下是打印示例：
//                isExternalStorageDocument***content://com.android.externalstorage.documents/document/primary%3ATset%2FROC2018421103253.wav
//                docId***primary:Test/ROC2018421103253.wav
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
//                Log.i(TAG,"isDownloadsDocument***"+uri.toString());
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
//                Log.i(TAG,"isMediaDocument***"+uri.toString());
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
//            Log.i(TAG,"content***"+uri.toString());
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            Log.i(TAG,"file***"+uri.toString());
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    public static String getEnglishName(File file) {
        String name;
        try {
            //替换文件名称中的中文字段
            String regex = "[\u4e00-\u9fa5]";
            Pattern pat = Pattern.compile(regex);
            Matcher mat = pat.matcher(file.getName());
            name = mat.replaceAll("singlefile");
        } catch (Exception e) {
            name = "exception";
        }

        return name;
    }

    /**
     * 获取文件时长  获取的是秒
     *
     * @param file
     * @return
     */
    public static String getFileLengtSecond(File file) {
        long fileLength = getFileLength(file);//毫秒
        return getSecond(fileLength);
    }


    /**
     * 将毫秒转成秒
     *
     * @param fileLength
     * @return
     */
    private static String getSecond(long fileLength) {

        float f = fileLength / 1000;
        int round = Math.round(f);//四舍五入取整

        if (round <= 0) {//最少一秒
            round = 1;
        }
        return round + "";

    }

    /**
     * 截取文件名称的扩展名
     * @param fileName
     * @return
     */
    public static String getFielxtensionName(String fileName) {

     return fileName.substring(fileName.lastIndexOf(".") + 1);

    }

    public static void saveBitmap(String filePath, String fileName, Bitmap bitmap){
        creatFile(filePath);
        File f = new File(filePath, fileName);
        if (f.exists()) {
            f.delete();
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

}
