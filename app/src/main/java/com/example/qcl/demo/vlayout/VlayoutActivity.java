package com.example.qcl.demo.vlayout;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.example.qcl.demo.R;
import com.example.qcl.demo.floorview.FloorView;

import java.util.ArrayList;
import java.util.List;

/**
 * v-layout
 */
public class VlayoutActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DelegateAdapter vAdapter;
    VirtualLayoutManager vLayoutManager;
    private CourseAdapter adapter;

    private FloorView sv;

    private List<String> listData = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vlayout);
        recyclerView = findViewById(R.id.rv_list);
        vLayoutManager = new VirtualLayoutManager(this);
        initSv();
        initVlayout();
        initData();
    }

    private void initSv() {
        sv = findViewById(R.id.widget_show_all_tabs);
        ArrayList<String> tabsName = new ArrayList<>();
        tabsName.add("标题一");
        tabsName.add("标题2222");
        tabsName.add("标题3333");
        tabsName.add("标题4444");
        tabsName.add("标题55555");
        tabsName.add("标题6");
        tabsName.add("标题7");
        tabsName.add("标题8");
        sv.addDisplayItem(tabsName, 1);
        sv.setRecyclerView(recyclerView, vLayoutManager);
    }


    private void initData() {
        for (int i = 0; i < 40; i++) {
            listData.add("辩题行数" + i);
        }
        adapter.setListData(listData);
        adapter.notifyDataSetChanged();
    }

    private void initVlayout() {

        vAdapter = new DelegateAdapter(vLayoutManager);
        recyclerView.setLayoutManager(vLayoutManager);
        recyclerView.setAdapter(vAdapter);

        adapter = new CourseAdapter(this);
        vAdapter.addAdapter(adapter);


    }
}
