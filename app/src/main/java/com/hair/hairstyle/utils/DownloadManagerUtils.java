package com.hair.hairstyle.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

import com.hair.hairstyle.property.UpdaterConfig;

/**
 * Created by yunshan on 17/7/31.
 */

public class DownloadManagerUtils {

    private static volatile DownloadManagerUtils instance;
    private DownloadManager mDownloadManager;

    private DownloadManagerUtils() {

    }

    public static DownloadManagerUtils getInstance() {
        if (instance == null) {
            synchronized (DownloadManagerUtils.class) {
                if (instance == null) {
                    instance = new DownloadManagerUtils();
                }
            }
        }
        return instance;
    }

    public DownloadManager getDownloadManager(Context context){
        if(mDownloadManager == null){
            mDownloadManager = (DownloadManager) context.getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
        }
        return mDownloadManager;
    }


    long startDownload(UpdaterConfig updaterConfig) {
        DownloadManager.Request req = new DownloadManager.Request(Uri.parse(updaterConfig.getFileUrl()));
        req.setAllowedNetworkTypes(updaterConfig.getAllowedNetworkTypes());
        //req.setAllowedOverMetered()
        //移动网络是否允许下载
        req.setAllowedOverRoaming(updaterConfig.isAllowedOverRoaming());

        if (updaterConfig.isCanMediaScanner()) {
            //能够被MediaScanner扫描
            req.allowScanningByMediaScanner();
        }

        //是否显示状态栏下载UI
        req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //点击正在下载的Notification进入下载详情界面，如果设为true则可以看到下载任务的进度，如果设为false，则看不到我们下载的任务
        req.setVisibleInDownloadsUi(updaterConfig.isShowDownloadUI());

        //设置文件的保存的位置[三种方式]
        //第一种
        //file:///storage/emulated/0/Android/data/your-package/files/Download/update.apk
        req.setDestinationInExternalFilesDir(updaterConfig.getContext(), Environment.DIRECTORY_DOWNLOADS, "update.apk");
        //第二种
        //file:///storage/emulated/0/Download/update.apk
        //req.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "update.apk");
        //第三种 自定义文件路径
        //req.setDestinationUri()


        // 设置一些基本显示信息
        req.setTitle(updaterConfig.getTitle());
        req.setDescription(updaterConfig.getDescription());


        //req.setMimeType("application/vnd.android.package-archive");

        long id = getDownloadManager(updaterConfig.getContext()).enqueue(req);
        //把DownloadId保存到本地
        UpdaterUtils.saveDownloadId(updaterConfig.getContext(), id);
        return id;
        //long downloadId = mDownloadManager.enqueue(req);
        //Log.d("DownloadManager", downloadId + "");
        //mDownloadManager.openDownloadedFile()
    }

    /**
     * 获取下载文件的路径
     * @param context
     * @param downloadId
     * @return
     */
    private String getDownloadPath(Context context, long downloadId) {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor c = getDownloadManager(context).query(query);
        if (c != null) {
            try {
                if (c.moveToFirst()) {
                    return c.getString(c.getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI));
                }
            } finally {
                c.close();
            }
        }
        return null;
    }

    /**
     * 获取下载文件路径的地址
     * @param context
     * @param downloadId
     * @return
     */
    public Uri getDownloadUri(Context context, long downloadId) {
        return getDownloadManager(context).getUriForDownloadedFile(downloadId);
    }

    /**
     * 获取下载的状态
     * @param context
     * @param downloadId
     * @return
     */
    public int getDownloadStatus(Context context, long downloadId) {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor c = getDownloadManager(context).query(query);
        if (c != null) {
            try {
                if (c.moveToFirst()) {
                    return c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS));
                }
            } finally {
                c.close();
            }
        }
        return -1;
    }


}
