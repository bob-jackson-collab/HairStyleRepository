package com.hair.hairstyle.utils;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by yunshan on 17/9/28.
 */

public class PhotoUtils {

    public static File getPhotoFileFromIntent(Context context, @NonNull Intent data) {
        Uri uri = data.getData();
        File file = null;
        Bitmap photo = null;
        if (uri != null) {
            String path = getPathByUri(context, uri);
            if (path == null) {
                return null;
            }
            file = new File(path);
////            photo = PhotoUtil.getBitmap(this, uri);
//        } else {
//            Bundle bundle = data.getExtras();
//            if (bundle != null) {
//                photo = (Bitmap) bundle.get("data"); //get bitmap
//            } else {
//                String path = FileUtil.getPath();
//                if (path != null) {
//                    file = new File(path);
//                }
//                return file;
//            }
//            file = FileUtil.saveBitmapFile(photo);
        }
        return file;
    }

    public static String getPathByUri(Context context, Uri uri) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            // DocumentProvider
            if (DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }
                }
                // DownloadsProvider
                else if (isDownloadsDocument(uri)) {
                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"),
                            Long.valueOf(id));
                    return getDataColumn(context, contentUri, null, null);
                }
                // MediaProvider
                else if (isMediaDocument(uri)) {
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
                    final String selection = MediaStore.MediaColumns._ID + "=?";
                    final String[] selectionArgs = new String[]{split[1]};
                    return getDataColumn(context, contentUri, selection,
                            selectionArgs);

                }
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                // Return the remote address
                if (isGooglePhotosUri(uri))
                    return uri.getLastPathSegment();
                return getDataColumn(context, uri, null, null);

            }
            // File
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
            return null;
        } else {
            Cursor cursor;
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(uri, proj, null, null, null);
            if (cursor == null) {
                return null;
            }
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            cursor.moveToFirst();
            String path = cursor.getString(columnIndex);
            cursor.close();
            return path;
        }
    }

    public static Bitmap getBitmap(Context context, Uri uri) {
        try {
            return MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri); //显得到bitmap图片
        } catch (IOException e) {
            return null;
        }
    }


    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = MediaStore.MediaColumns.DATA;
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static Bitmap compressBitmap(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高
        Bitmap bitmap = BitmapFactory.decodeFile(path, options); //此时返回bm为空
        options.inJustDecodeBounds = false;
        //计算缩放比
        int be = (int) (options.outHeight / (float) 1000); //1000就是转换后的高度
        if (be <= 0)
            be = 1;
        options.inSampleSize = be;
        //重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false哦
        bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }

    public static File saveTempBitmap(Bitmap bmp) {
        File dir = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            dir = new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + File.separator + "photo" + File.separator + "temp" + File.separator);

        } else {
            dir = new File(Environment.getRootDirectory().getAbsolutePath() + File.separator + "photo" + File.separator + "temp" + File.separator);
        }

        if (!dir.exists()) {
            boolean mkdirs = dir.mkdirs();
            if (!mkdirs) {
                return null;
            }
        }
        File file = new File(dir, System.currentTimeMillis() + ".jpg");//将要保存图片的路径
        if (!file.exists()) {
            try {
                boolean b = file.createNewFile();
                if (!b) {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return saveBitmap(bmp, file);
    }

    public static File saveBitmap(Bitmap bmp, File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            return null;
        }
        return file;

    }

}
