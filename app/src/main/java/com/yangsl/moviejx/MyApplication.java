package com.yangsl.moviejx;

import android.app.Application;
import android.widget.Toast;

import com.tencent.bugly.Bugly;
import com.tencent.mmkv.MMKV;
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
        initMMKV();
        initX5();
    }

    private void initMMKV() {
        //初始化MMKV
        MMKV.initialize(this);
    }

    private void initX5() {
        //非wifi情况下，主动下载x5内核
        QbSdk.setDownloadWithoutWifi(true);
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), new QbSdk.PreInitCallback() {

            @Override
            public void onCoreInitFinished() {

            }

            @Override
            public void onViewInitFinished(boolean b) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                if (b)
                    Toast.makeText(MyApplication.this, "内核引擎加载成功", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MyApplication.this, "内核引擎加载失败，请稍后重启", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
