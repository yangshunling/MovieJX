package com.yangsl.moviejx.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.tencent.bugly.Bugly;
import com.yangsl.moviejx.R;
import com.yangsl.moviejx.activity.MoviePlayActivity;
import com.yangsl.moviejx.base.BaseFragment;
import com.yangsl.moviejx.utils.SpUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class MovieFragment extends BaseFragment {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.go)
    QMUIRoundButton mGo;
    @BindView(R.id.custom)
    QMUIRoundButton mCustom;
    @BindView(R.id.url)
    MaterialEditText mUrl;

    @Override
    public int getContentView() {
        return R.layout.fragment_movie;
    }

    @Override
    public void initView(View rootView) {
        //初始化Bugly
        Bugly.init(getActivity().getApplicationContext(), "2c650db6bb", false);
        //初始化沉浸式
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_BAR).init();
    }

    @Override
    public void initData() {
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
                startJX();
                break;
            case R.id.custom:
                customJX();
                break;
        }
    }

    private void customJX() {
        new MaterialDialog.Builder(getActivity())
                .title("自定义解析器")
                .content("请输入解析地址")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("如：https://www.baidu.com", SpUtil.getString("baseurl"), (dialog, input) -> SpUtil.put("baseurl", input.toString()))
                .positiveText("确定")
                .show();
    }

    /**
     * 开始解析
     */
    private void startJX() {
        String movieUrl = mUrl.getText().toString().trim();
        if (TextUtils.isEmpty(movieUrl)) {
            Toast.makeText(getActivity(), "请输入有效的资源链接", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getActivity(), MoviePlayActivity.class);
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
}
