package com.example.qcl.demo.xuexi.baoguang;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.example.qcl.demo.R;

import java.util.ArrayList;
import java.util.List;

public class BaoGuangActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DelegateAdapter vAdapter;
    VirtualLayoutManager vLayoutManager;
    private BaoGuangAdapter adapter;

    private List<ItemData> listData = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_guang);
        recyclerView = findViewById(R.id.rv_list);
        vLayoutManager = new VirtualLayoutManager(this);
        initVlayout();
        initData();
    }


    private void initData() {
        for (int i = 0; i < 40; i++) {
            listData.add(new ItemData("标题" + i, "描述" + i));
        }
        adapter.setListData(listData);
        adapter.notifyDataSetChanged();

        ViewShowCountUtils viewShowCountUtils = new ViewShowCountUtils();
        viewShowCountUtils.recordViewShowCount(recyclerView);
    }

    private void initVlayout() {
        vAdapter = new DelegateAdapter(vLayoutManager);
        recyclerView.setLayoutManager(vLayoutManager);
        recyclerView.setAdapter(vAdapter);
        adapter = new BaoGuangAdapter(this);
        vAdapter.addAdapter(adapter);
    }
}
