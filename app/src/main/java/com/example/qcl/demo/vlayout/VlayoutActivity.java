package com.example.qcl.demo.vlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.example.qcl.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * v-layout
 */
public class VlayoutActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DelegateAdapter vAdapter;
    private CourseAdapter adapter;

    private List<String> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vlayout);
        initVlayout();
        initData();
    }

    private void initData() {
        for (int i = 0; i < 50; i++) {
            listData.add("辩题行数" + i);
        }
        adapter.setListData(listData);
        adapter.notifyDataSetChanged();
    }

    private void initVlayout() {
        recyclerView = findViewById(R.id.rv_list);
        VirtualLayoutManager vLayoutManager = new VirtualLayoutManager(this);
        vAdapter = new DelegateAdapter(vLayoutManager);
        recyclerView.setLayoutManager(vLayoutManager);
        recyclerView.setAdapter(vAdapter);

        adapter = new CourseAdapter(this);
        vAdapter.addAdapter(adapter);


    }
}
