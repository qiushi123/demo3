package com.example.qcl.demo.xuexi.baoguang;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewTreeObserver;

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


        ViewTreeObserver treeObserver = recyclerView.getViewTreeObserver();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            treeObserver.addOnWindowFocusChangeListener(new ViewTreeObserver.OnWindowFocusChangeListener() {
                @Override
                public void onWindowFocusChanged(boolean hasFocus) {
                    //                    Log.i("qcl0403", "onWindowFocusChanged");
                }
            });
        }

        //添加或移除
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            treeObserver.addOnWindowAttachListener(new ViewTreeObserver.OnWindowAttachListener() {
                @Override
                public void onWindowAttached() {
                    //                    Log.i("qcl0403", "onWindowAttached");
                }

                @Override
                public void onWindowDetached() {
                    //                    Log.i("qcl0403", "onWindowDetached");
                }
            });
        }
        //布局改变
        treeObserver.addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        //                        Log.i("qcl0403", "view树改变");
                    }
                });


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
