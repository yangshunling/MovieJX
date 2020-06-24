package com.yangsl.moviejx.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.came.viewbguilib.ButtonBgUi;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.tencent.bugly.Bugly;
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
    @BindView(R.id.go)
    ButtonBgUi mGo;
    @BindView(R.id.custom)
    ButtonBgUi mCustom;
    @BindView(R.id.url)
    MaterialEditText mUrl;
    private String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化Bugly
        Bugly.init(getApplicationContext(), "2c650db6bb", false);
        //初始化沉浸式
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_BAR).init();
        //初始化绑定
        ButterKnife.bind(this);
        //申请权限
        requestPermissions(false);
        //设置样式
        setTextViewStyles(mTitle);
        mUrl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mUrl.setSelection(0);
            }
        });
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
        float[] position = {0.2f, 0.5f, 0.8f};//颜色渐变位置的数组
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
