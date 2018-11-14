package com.example.yucong.downloadservice;

public interface DownloadListener {

    void onProgress(int progress);//下载进度

    void onSuccess();

    void onFailed();

    void onPaused();

    void onCanceled();

}
