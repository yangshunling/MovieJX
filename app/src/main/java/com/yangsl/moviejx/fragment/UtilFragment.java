package com.yangsl.moviejx.fragment;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yangsl.moviejx.R;
import com.yangsl.moviejx.activity.util.PhotoCompressActivity;
import com.yangsl.moviejx.activity.util.ScanQRActivity;
import com.yangsl.moviejx.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class UtilFragment extends BaseFragment {

    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    private MyAdapter mMyAdapter;
    private List<Map<String, String>> mUtilList = new ArrayList<>();

    @Override
    public int getContentView() {
        return R.layout.fragment_util;
    }

    @Override
    public void initView(View rootView) {

    }

    /**
     * hot:0-非热销 1-热销
     */
    @Override
    public void initData() {
        Map<String, String> map0 = new HashMap<>();
        map0.put("title", "二维码工具");
        map0.put("hot", "0");
        mUtilList.add(map0);
        Map<String, String> map1 = new HashMap<>();
        map1.put("title", "图片压缩");
        map1.put("hot", "0");
        mUtilList.add(map1);
        mMyAdapter = new MyAdapter(R.layout.item_util_recycle, mUtilList);
        mRvList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRvList.setAdapter(mMyAdapter);
        mMyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(getActivity(), ScanQRActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), PhotoCompressActivity.class));
                        break;
                }
            }
        });
    }

    public class MyAdapter extends BaseQuickAdapter<Map<String, String>, BaseViewHolder> {
        public MyAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Map<String, String> item) {
            helper.setText(R.id.title, item.get("title"));
        }
    }
}
