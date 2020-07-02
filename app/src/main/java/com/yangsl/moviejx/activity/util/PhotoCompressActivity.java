package com.yangsl.moviejx.activity.util;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.yangsl.moviejx.R;
import com.yangsl.moviejx.base.BaseActivity;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * @Description: PhotoCompressActivity
 * @Author: Anonymous
 * @Time: 2020/7/1 19:37
 */
public class PhotoCompressActivity extends BaseActivity {

    @BindView(R.id.before)
    ImageView mBefore;
    @BindView(R.id.choose)
    QMUIRoundButton mChoose;
    @BindView(R.id.save)
    QMUIRoundButton mSave;
    @BindView(R.id.after)
    ImageView mAfter;

    @Override
    public int getContentView() {
        return R.layout.activity_photo_compress;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.choose, R.id.save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.choose:
                chooseImage();
                break;
            case R.id.save:
                saveImage();
                break;
        }
    }

    private void chooseImage() {
        Matisse.from(PhotoCompressActivity.this)
                .choose(MimeType.ofImage())//图片类型
                .countable(false)//true:选中后显示数字;false:选中后显示对号
                .maxSelectable(1)//可选的最大数
                .capture(true)//选择照片时，是否显示拍照
                .captureStrategy(new CaptureStrategy(true, "com.jsf.piccompresstest"))//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                .imageEngine(new GlideEngine())//图片加载引擎
                .forResult(1000);//
    }

    private void saveImage() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            //图片路径 同样视频地址也是这个 根据requestCode
            List<Uri> pathList = Matisse.obtainResult(data);
            Glide.with(this).load(pathList.get(0)).into(mBefore);
            compressImage(pathList.get(0));
        }
    }

    private void compressImage(Uri uri) {
        Luban.with(this)
                .load(uri)
                .ignoreBy(100)
                .setTargetDir("getPath()")
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                    }
                }).launch();
    }
}
