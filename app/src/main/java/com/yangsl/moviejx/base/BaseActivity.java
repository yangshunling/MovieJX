package com.yangsl.moviejx.base;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.yangsl.moviejx.R;
import com.yangsl.moviejx.callback.PermissionsCallback;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * BaseActivity基类
 */
public abstract class BaseActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private FrameLayout mViewContent;

    private String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private PermissionsCallback mCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        //初始化沉浸式
        initStatusBar();
        //解析子布局
        resolveView();
        //绑定插件
        bindPlugins();
        //初始化控件
        initView();
        //初始化数据
        initData();
    }

    /**
     * 初始化沉浸式
     */
    public void initStatusBar() {
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_BAR).init();
    }

    /**
     * 沉浸式图片
     */
    public void setTransparent() {
        ImmersionBar.with(this).fitsSystemWindows(false).transparentStatusBar().init();
    }

    /**
     * 初始化Fragment
     */
    public void resolveView() {
        mViewContent = findViewById(R.id.viewContent);
        //将继承 TopBarBaseActivity 的布局解析到 FrameLayout 里面
        LayoutInflater.from(this).inflate(getContentView(), mViewContent);
    }

    /**
     * 绑定插件
     */
    public void bindPlugins() {
        ButterKnife.bind(this);
    }

    /**
     * 获取布局文件
     *
     * @return
     */
    public abstract int getContentView();

    /**
     * 初始化控件
     */
    public abstract void initView();

    /**
     * 初始化数据（控件的赋值）
     */
    public abstract void initData();

    /**
     * 动态权限接口定义
     *
     * @param callback
     */
    public void requestPermissions(PermissionsCallback callback) {
        mCallback = callback;
        if (EasyPermissions.hasPermissions(this, perms)) {
            mCallback.onAccept();
        } else {
            EasyPermissions.requestPermissions(this, "请同意以下权限!", 100, perms);
        }
    }

    /**
     * 动态权限回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 权限接受回调
     *
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        mCallback.onAccept();
    }

    /**
     * 权限拒绝回调
     *
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        mCallback.onDenied();
    }

    /**
     * 显示Toast
     *
     * @param msg
     */
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
