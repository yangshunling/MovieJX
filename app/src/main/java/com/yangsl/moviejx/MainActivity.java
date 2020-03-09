package com.yangsl.moviejx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.came.viewbguilib.ButtonBgUi;
import com.google.android.material.textfield.TextInputEditText;
import com.tencent.smtt.sdk.TbsVideo;

public class MainActivity extends AppCompatActivity {

    private TextView mTitle;
    private TextInputEditText mUrl;
    private ButtonBgUi mGo;
    private ButtonBgUi mCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = findViewById(R.id.title);
        mUrl = findViewById(R.id.url);
        mGo = findViewById(R.id.go);
        mCustom = findViewById(R.id.custom);
        setTextViewStyles(mTitle);
        mGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String movieUrl = mUrl.getText().toString().trim();
                if (TextUtils.isEmpty(movieUrl)) {
                    Toast.makeText(MainActivity.this, "请输入有效的资源链接", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, MoviePlayActivity.class);
                    intent.putExtra("url", movieUrl);
                    startActivity(intent);
                }
            }
        });
        mCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "该功能暂未开启，请持续关注", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setTextViewStyles(TextView textView) {
        int[] colors = {Color.RED, Color.GREEN, Color.BLUE};//颜色的数组
        float[] position = {0f, 0.7f, 1.0f};//颜色渐变位置的数组
        LinearGradient mLinearGradient = new LinearGradient(0, 0, textView.getPaint().getTextSize() * textView.getText().length(), 0, colors, position, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(mLinearGradient);
        textView.invalidate();
    }
}
