package com.yangsl.moviejx;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.LinearLayout;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Description: MoviePlayActivity
 * @Author: Anonymous
 * @Time: 2020/2/18 12:52
 */
@SuppressWarnings("ALL")
public class MoviePlayActivity extends AppCompatActivity {

    private String url = "";
    private WebView mX5webview;
    private WebSettings mSettings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_play);
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_BAR).init();
        init();
    }

    private void init() {
        url = getIntent().getStringExtra("url");
        mX5webview = (WebView) findViewById(R.id.x5webview);
        mSettings = mX5webview.getSettings();
        mSettings.setJavaScriptEnabled(true);
        mSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        mSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        mSettings.setDisplayZoomControls(true); //隐藏原生的缩放控件
        mSettings.setBlockNetworkImage(false);//解决图片不显示
        mSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        mSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        mX5webview.setWebViewClient(new MyWebviewClient());
        mX5webview.setWebChromeClient(new WebChromeClient());
        mX5webview.loadUrl("http://jqaaa.com/jx.php?url=" + url);
    }

    public class MyWebviewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String s) {
            webView.loadUrl(s);
            return true;
        }
    }

    public class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mX5webview.onPause();
        mSettings.setLightTouchEnabled(false);
    }

    @Override
    public void onDestroy() {
        if (mX5webview != null) {
            mX5webview.destroy();
        }
        super.onDestroy();
    }

    /**
     * 实现WebView的回退栈
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
