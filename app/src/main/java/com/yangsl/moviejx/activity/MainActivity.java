package com.yangsl.moviejx.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.came.viewbguilib.ButtonBgUi;
import com.google.android.material.textfield.TextInputEditText;
import com.yangsl.moviejx.utils.LiveDataBus;
import com.yangsl.moviejx.R;
import com.yangsl.moviejx.utils.SpUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.url)
    TextInputEditText mUrl;
    @BindView(R.id.go)
    ButtonBgUi mGo;
    @BindView(R.id.custom)
    ButtonBgUi mCustom;
    private MaterialDialog mBuilder;
    private String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化绑定
        ButterKnife.bind(this);
        //申请权限
        requestPermissions(false);
        //初始化Dialog
        initDialog();
        //初始化LiveDataBus
        initLiveDataBus();
        //设置样式
        setTextViewStyles(mTitle);
    }

    private void initLiveDataBus() {
        LiveDataBus.get()
                .with("progress", Boolean.class)
                .observe(this, b -> {
                    if (b) mBuilder.show();
                    else mBuilder.dismiss();
                });
    }

    private void initDialog() {
        mBuilder = new MaterialDialog.Builder(MainActivity.this)
                .title("初始化破解引擎")
                .content("若时间过长，请杀死进程，重新启动")
                .canceledOnTouchOutside(false)
                .progress(true, 0)
                .progressIndeterminateStyle(true).build();
    }

    @OnClick({R.id.go, R.id.custom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.go:
                requestPermissions(true);
                break;
            case R.id.custom:
                customJX();
                break;
        }
    }

    private void customJX() {
        new MaterialDialog.Builder(MainActivity.this)
                .title("自定义解析器")
                .content("请输入解析地址")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("如：https://www.baidu.com", SpUtil.getString("baseurl"), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        SpUtil.put("baseurl", input.toString());
                    }
                })
                .positiveText("确定")
                .show();
    }

    /**
     * 申请动态权限
     */
    public void requestPermissions(boolean isStart) {
        if (EasyPermissions.hasPermissions(this, perms)) {
            if (isStart)
                startJX();
        } else {
            EasyPermissions.requestPermissions(this, "请同意以下权限!", 100, perms);
        }
    }

    /**
     * 开始解析
     */
    private void startJX() {
        String movieUrl = mUrl.getText().toString().trim();
        if (TextUtils.isEmpty(movieUrl)) {
            Toast.makeText(MainActivity.this, "请输入有效的资源链接", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(MainActivity.this, MoviePlayActivity.class);
            intent.putExtra("url", movieUrl);
            startActivity(intent);
        }
    }

    private void setTextViewStyles(TextView textView) {
        int[] colors = {Color.RED, Color.GREEN, Color.BLUE};//颜色的数组
        float[] position = {0f, 0.7f, 1.0f};//颜色渐变位置的数组
        LinearGradient mLinearGradient = new LinearGradient(0, 0, textView.getPaint().getTextSize() * textView.getText().length(), 0, colors, position, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(mLinearGradient);
        textView.invalidate();
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }
}
