package com.yangsl.moviejx.activity.util;

import android.os.Vibrator;
import android.widget.Toast;

import com.yangsl.moviejx.R;
import com.yangsl.moviejx.base.BaseActivity;

import butterknife.BindView;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * 二维码工具
 */
public class ScanQRActivity extends BaseActivity implements QRCodeView.Delegate {

    @BindView(R.id.zxingview)
    ZXingView mZxingview;

    @Override
    public int getContentView() {
        return R.layout.activity_scan_qr;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        mZxingview.setDelegate(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mZxingview.startCamera();//打开相机
        mZxingview.showScanRect();//显示扫描框
        mZxingview.startSpot();//开始识别二维码
    }
    @Override
    protected void onStop() {
        mZxingview.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mZxingview.onDestroy();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();
        Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {

    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Toast.makeText(this,"识别错误",Toast.LENGTH_SHORT).show();
    }
}
