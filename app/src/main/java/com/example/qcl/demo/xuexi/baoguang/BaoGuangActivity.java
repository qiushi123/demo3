package com.example.qcl.demo.xuexi.baoguang;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.example.qcl.demo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class BaoGuangActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DelegateAdapter vAdapter;
    VirtualLayoutManager vLayoutManager;
    private BaoGuangAdapter adapter;

    private List<ItemData> listData = new ArrayList<>();

    private boolean isFirstVisible = true;
    private ConcurrentHashMap<String, Integer> hashMap = new ConcurrentHashMap<String, Integer>();

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

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    getVisibleViews(recyclerView);
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isFirstVisible) {
                    getVisibleViews(recyclerView);
                    isFirstVisible = false;
                }
            }
        });
    }

    /*
     * 获取当前屏幕上可见的view
     * */
    private void getVisibleViews(RecyclerView reView) {
        if (reView == null || reView.getVisibility() != View.VISIBLE ||
                !reView.isShown() || !reView.getGlobalVisibleRect(new Rect())) {
            return;
        }
        int[] range = new int[2];
        RecyclerView.LayoutManager manager = reView.getLayoutManager();
        if (manager instanceof LinearLayoutManager) {
            range = findRangeLinear((LinearLayoutManager) manager);
        } else if (manager instanceof GridLayoutManager) {
            range = findRangeGrid((GridLayoutManager) manager);
        } else if (manager instanceof StaggeredGridLayoutManager) {
            range = findRangeStaggeredGrid((StaggeredGridLayoutManager) manager);
        }
        for (int i = range[0]; i <= range[1]; i++) {
            View view = manager.findViewByPosition(i);
            recordViewCount(view);

        }
    }

    //获取view绑定的数据
    private void recordViewCount(View view) {
        if (view == null || view.getVisibility() != View.VISIBLE ||
                !view.isShown() || !view.getGlobalVisibleRect(new Rect())) {
            return;
        }
        int top = view.getTop();
        int halfHeight = view.getHeight() / 2;

        //不包含底部虚拟键的高度
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int screenHeight = outMetrics.heightPixels;
        int statusBarHeight = getStatusBarHeight();

        Log.i("qcl0402", "top：" + top);
        Log.i("qcl0402", "screenHeight：" + screenHeight);
        Log.i("qcl0402", "statusBarHeight：" + statusBarHeight);
        Log.i("qcl0402", "height：" + halfHeight);
        if (top < 0 && Math.abs(top) > halfHeight) {
            return;
        }
        if (top > screenHeight - halfHeight - statusBarHeight) {
            return;
        }


        ItemData tag = (ItemData) view.getTag();
        String key = tag.toString();
        if (TextUtils.isEmpty(key)) {
            return;
        }
        hashMap.put(key, !hashMap.containsKey(key) ? 1 : (hashMap.get(key) + 1));
        Log.i("qcl0402", key + "----出现次数：" + hashMap.get(key));
    }

    //获取状态栏的高度
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    private int[] findRangeLinear(LinearLayoutManager manager) {
        int[] range = new int[2];
        range[0] = manager.findFirstVisibleItemPosition();
        range[1] = manager.findLastVisibleItemPosition();
        return range;
    }

    private int[] findRangeGrid(GridLayoutManager manager) {
        int[] range = new int[2];
        range[0] = manager.findFirstVisibleItemPosition();
        range[1] = manager.findLastVisibleItemPosition();
        return range;

    }

    private int[] findRangeStaggeredGrid(StaggeredGridLayoutManager manager) {
        int[] startPos = new int[manager.getSpanCount()];
        int[] endPos = new int[manager.getSpanCount()];
        manager.findFirstVisibleItemPositions(startPos);
        manager.findLastVisibleItemPositions(endPos);
        int[] range = findRange(startPos, endPos);
        return range;
    }

    private int[] findRange(int[] startPos, int[] endPos) {
        int start = startPos[0];
        int end = endPos[0];
        for (int i = 1; i < startPos.length; i++) {
            if (start > startPos[i]) {
                start = startPos[i];
            }
        }
        for (int i = 1; i < endPos.length; i++) {
            if (end < endPos[i]) {
                end = endPos[i];
            }
        }
        int[] res = new int[]{start, end};
        return res;
    }


    private void initVlayout() {

        vAdapter = new DelegateAdapter(vLayoutManager);
        recyclerView.setLayoutManager(vLayoutManager);
        recyclerView.setAdapter(vAdapter);

        adapter = new BaoGuangAdapter(this);
        vAdapter.addAdapter(adapter);


    }
}
