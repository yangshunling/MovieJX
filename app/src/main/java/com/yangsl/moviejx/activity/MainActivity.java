package com.yangsl.moviejx.activity;

import android.content.Intent;

import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.yangsl.moviejx.R;
import com.yangsl.moviejx.base.BaseActivity;
import com.yangsl.moviejx.callback.PermissionsCallback;
import com.yangsl.moviejx.fragment.MineFragment;
import com.yangsl.moviejx.fragment.MovieFragment;
import com.yangsl.moviejx.fragment.UtilFragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;

/**
 * MainActivity
 */
public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    @BindView(R.id.nav_bar)
    BottomNavigationBar mNavBar;

    private FragmentManager manager;
    private FragmentTransaction transaction;

    private MovieFragment mMovieFragment;
    private UtilFragment mUtilFragment;
    private MineFragment mMineFragment;

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        initNavBar();
        initFragment();
    }

    @Override
    public void initData() {
        requestPermissions(new PermissionsCallback() {
            @Override
            public void onAccept() {

            }

            @Override
            public void onDenied() {

            }
        });
    }

    private void initNavBar() {
        mNavBar.setTabSelectedListener(this);
        mNavBar.addItem(new BottomNavigationItem(R.drawable.nav_movie, "电影").setActiveColorResource(R.color.app_color).setInActiveColorResource(R.color.white))
                .addItem(new BottomNavigationItem(R.drawable.nav_util, "工具").setActiveColorResource(R.color.azure).setInActiveColorResource(R.color.white))
                        .addItem(new BottomNavigationItem(R.drawable.nav_mine, "我的").setActiveColorResource(R.color.green).setInActiveColorResource(R.color.white))
                        .setFirstSelectedPosition(0)
                        .initialise();
    }

    private void initFragment() {
        //实例化
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        //创建Fragment
        mMovieFragment = new MovieFragment();
        transaction.add(R.id.main_content, mMovieFragment);
        transaction.commit();
    }

    @Override
    public void onTabSelected(int position) {
        initStatusBar();
        switch (position) {
            case 0:
                transaction = manager.beginTransaction();
                if (mMovieFragment == null) {
                    mMovieFragment = new MovieFragment();
                    transaction.add(R.id.main_content, mMovieFragment);
                } else {
                    hintFragment(transaction);
                    transaction.show(mMovieFragment);
                }
                transaction.commit();
                break;
            case 1:
                transaction = manager.beginTransaction();
                if (mUtilFragment == null) {
                    mUtilFragment = new UtilFragment();
                    transaction.add(R.id.main_content, mUtilFragment);
                } else {
                    hintFragment(transaction);
                    transaction.show(mUtilFragment);
                }
                transaction.commit();
                break;
            case 2:
                transaction = manager.beginTransaction();
                if (mMineFragment == null) {
                    mMineFragment = new MineFragment();
                    transaction.add(R.id.main_content, mMineFragment);
                } else {
                    hintFragment(transaction);
                    transaction.show(mMineFragment);
                }
                transaction.commit();
                break;
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    private void hintFragment(FragmentTransaction transaction) {
        if (mMovieFragment != null) {
            transaction.hide(mMovieFragment);
        }
        if (mUtilFragment != null) {
            transaction.hide(mUtilFragment);
        }
        if (mMineFragment != null) {
            transaction.hide(mMineFragment);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            long firstTime = 0;
            if (System.currentTimeMillis() - firstTime > 2000) {
                showToast("再按一次退出程序");
                firstTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
