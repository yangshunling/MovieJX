package com.yangsl.moviejx.activity.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Vibrator;
import android.text.InputType;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.yangsl.moviejx.R;
import com.yangsl.moviejx.activity.MainActivity;
import com.yangsl.moviejx.base.BaseActivity;
import com.yangsl.moviejx.utils.SpUtil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
        showMessage(result);
    }

    private void showMessage(String result) {
        new MaterialDialog.Builder(ScanQRActivity.this)
                .title("文本内容")
                .content(result)
                .positiveText("复制文本")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        clipboard(result);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                    }
                })
                .show();
    }

    /**
     * 复制剪贴板
     *
     * @param str 要复制的字符串
     */
    public void clipboard(String str) {
        //获取剪贴板管理器
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", str);
        // 将ClipData内容放到系统剪贴板里
        cm.setPrimaryClip(mClipData);
        showToast("文本复制成功");
        finish();
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {

    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Toast.makeText(this, "识别错误", Toast.LENGTH_SHORT).show();
    }
}
