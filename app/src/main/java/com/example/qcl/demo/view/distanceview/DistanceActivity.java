package com.example.qcl.demo.view.distanceview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.qcl.demo.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/*
* 仿虎嗅视差创意广告
* */
public class DistanceActivity extends AppCompatActivity {

    private RecyclerView rvList;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance);
        rvList = findViewById(R.id.rv_list);
        initData();
    }

    private void initData() {
        List<String> mDatas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mDatas.add(i + "");
        }
        rvList.setLayoutManager(mLinearLayoutManager = new LinearLayoutManager(this));
        rvList.setAdapter(new CommonAdapter<String>(DistanceActivity.this, R.layout.item, mDatas) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                if (position > 0 && position % 9 == 0) {
                    holder.setVisible(R.id.id_tv_title, false);
                    holder.setVisible(R.id.id_tv_desc, false);
                    holder.setVisible(R.id.id_iv_ad, true);
                } else {
                    holder.setVisible(R.id.id_tv_title, true);
                    holder.setVisible(R.id.id_tv_desc, true);
                    holder.setVisible(R.id.id_iv_ad, false);
                }

            }
        });

        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int fPos = mLinearLayoutManager.findFirstVisibleItemPosition();
                int lPos = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                //view在可见范围中
                Log.d("qcl111", "fPos " + fPos + "lPos  " + lPos);
                for (int i = fPos; i <= lPos; i++) {
                    View view = mLinearLayoutManager.findViewByPosition(i);
                    AdImageView adImageView = view.findViewById(R.id.id_iv_ad);
                    if (adImageView.getVisibility() == View.VISIBLE) {
                        adImageView.setmDx(mLinearLayoutManager.getHeight() - view.getTop());

                    }
                }
            }
        });

    }
}
