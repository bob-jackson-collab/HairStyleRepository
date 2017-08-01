package com.hair.hairstyle.utils;

import android.app.DownloadManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.hair.hairstyle.property.UpdaterConfig;

public class Updater {

    /**
     * DownloadManagerUtils.getDownloadStatus如果没找到会返回-1
     */
    private static final int STATUS_UN_FIND = -1;

    private static Updater instance;

    private Updater() {
        //
    }

    public synchronized static Updater get() {
        if (instance == null) {
            synchronized (Updater.class) {
                if (instance == null) {
                    instance = new Updater();
                }
            }
        }
        return instance;
    }


    public void download(UpdaterConfig updaterConfig) {
        if (!UpdaterUtils.checkDownloadState(updaterConfig.getContext())) {
            Toast.makeText(updaterConfig.getContext(), "系统下载组件不可用", Toast.LENGTH_SHORT).show();
            UpdaterUtils.showDownloadSetting(updaterConfig.getContext());
            return;
        }

        long downloadId = UpdaterUtils.getLocalDownloadId(updaterConfig.getContext());
        Log.d("111", "local download id is " + downloadId);
        if (downloadId != -1L) {
            DownloadManagerUtils downloadManagerUtils = DownloadManagerUtils.getInstance();
            //获取下载状态
            int status = downloadManagerUtils.getDownloadStatus(updaterConfig.getContext(), downloadId);
            switch (status) {
                //下载成功
                case DownloadManager.STATUS_SUCCESSFUL:
                    Log.d("111","downloadId=" + downloadId + " ,status = STATUS_SUCCESSFUL");
                    Uri uri = downloadManagerUtils.getDownloadUri(updaterConfig.getContext(), downloadId);
                    if (uri != null) {
                        //本地的版本大于当前程序的版本直接安装
                        if (UpdaterUtils.compare(updaterConfig.getContext(), uri.getPath())) {
                            Log.d("111","start install UI");
                            UpdaterUtils.startInstall(updaterConfig.getContext(), uri);
                            return;
                        } else {
                            //从FileDownloadManager中移除这个任务
                            downloadManagerUtils.getDownloadManager(updaterConfig.getContext()).remove(downloadId);
                        }
                    }
                    //重新下载
                    startDownload(updaterConfig);
                    break;
                //下载失败
                case DownloadManager.STATUS_FAILED:
                    Log.d("111","download failed " + downloadId);
                    startDownload(updaterConfig);
                    break;
                case DownloadManager.STATUS_RUNNING:
                    Log.d("111","downloadId=" + downloadId + " ,status = STATUS_RUNNING");
                    break;
                case DownloadManager.STATUS_PENDING:
                    Log.d("111","downloadId=" + downloadId + " ,status = STATUS_PENDING");
                    break;
                case DownloadManager.STATUS_PAUSED:
                    Log.d("111","downloadId=" + downloadId + " ,status = STATUS_PAUSED");
                    break;
                case STATUS_UN_FIND:
                    Log.d("111","downloadId=" + downloadId + " ,status = STATUS_UN_FIND");
                    startDownload(updaterConfig);
                    break;
                default:
                    Log.d("111","downloadId=" + downloadId + " ,status = " + status);
                    break;
            }
        } else {
            startDownload(updaterConfig);
        }
    }

    private void startDownload(UpdaterConfig updaterConfig) {
        long id = DownloadManagerUtils.getInstance().startDownload(updaterConfig);
        Log.d("111","apk download start, downloadId is " + id);
    }

}