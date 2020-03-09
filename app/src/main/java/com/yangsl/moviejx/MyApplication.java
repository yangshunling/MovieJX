package com.yangsl.moviejx;

import android.app.Application;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;

/**
 * @Description: MyApplication
 * @Author: Anonymous
 * @Time: 2020/2/18 14:26
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //非wifi情况下，主动下载x5内核
        QbSdk.setDownloadWithoutWifi(true);
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.i("x5", "腾讯X5内核初始化回调 onViewInitFinished is " + arg0);
            }
            @Override
            public void onCoreInitFinished() {
                Log.i("x5", "腾讯X5内核初始化回调 onCoreInitFinished");
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }
}
