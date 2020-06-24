package com.yangsl.moviejx.activity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.yangsl.moviejx.R;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import yanzhikai.textpath.AsyncTextPathView;
import yanzhikai.textpath.PathAnimatorListener;

public class WelcomeActivity extends AppCompatActivity {

    @BindView(R.id.vip)
    AsyncTextPathView mVip;
    @BindView(R.id.wx)
    ImageView mWx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_BAR).init();
        ButterKnife.bind(this);
        initData();
    }

    public void initData() {
        mVip = findViewById(R.id.vip);
        mVip.setFillColor(true);
        //从无到显示
        mVip.startAnimation(0, 1);
        mVip.setAnimatorListener(new PathAnimatorListener(){
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mWx.setVisibility(View.VISIBLE);
                mWx.setAnimation(AnimationUtils.loadAnimation(WelcomeActivity.this,R.anim.alpha_anim));
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                finish();
            }
        }, 5000);
    }

}
