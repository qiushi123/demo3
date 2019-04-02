package com.example.qcl.demo.xuexi.baoguang;

import android.app.Activity;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.qcl.demo.utils.UiUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 2019/4/2 13:31
 * author: qcl
 * desc: 安卓曝光量统计工具类
 * wechat:2501902696
 */
public class ViewShowCountUtils {

    //刚进入列表时统计当前屏幕可见views
    private boolean isFirstVisible = true;

    //用于统计曝光量的map
    private ConcurrentHashMap<String, Integer> hashMap = new ConcurrentHashMap<String, Integer>();


    /*
     * 统计RecyclerView里当前屏幕可见子view的曝光量
     *
     * */
    void recordViewShowCount(RecyclerView recyclerView) {
        hashMap.clear();
        if (recyclerView == null || recyclerView.getVisibility() != View.VISIBLE) {
            return;
        }
        //检测recylerview的滚动事件
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                /*
                我这里通过的是停止滚动后屏幕上可见view。如果滚动过程中的可见view也要统计，你可以根据newState去做区分
                SCROLL_STATE_IDLE:停止滚动
                SCROLL_STATE_DRAGGING: 用户慢慢拖动
                SCROLL_STATE_SETTLING：惯性滚动
                */
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    getVisibleViews(recyclerView);
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //刚进入列表时统计当前屏幕可见views
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
        //保险起见，为了不让统计影响正常业务，这里做下try-catch
        try {
            int[] range = new int[2];
            RecyclerView.LayoutManager manager = reView.getLayoutManager();
            if (manager instanceof LinearLayoutManager) {
                range = findRangeLinear((LinearLayoutManager) manager);
            } else if (manager instanceof GridLayoutManager) {
                range = findRangeGrid((GridLayoutManager) manager);
            } else if (manager instanceof StaggeredGridLayoutManager) {
                range = findRangeStaggeredGrid((StaggeredGridLayoutManager) manager);
            }
            if (range == null || range.length < 2) {
                return;
            }
            Log.i("qcl0402", "屏幕内可见条目的起始位置：" + range[0] + "---" + range[1]);
            for (int i = range[0]; i <= range[1]; i++) {
                View view = manager.findViewByPosition(i);
                recordViewCount(view);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

        int screenHeight = UiUtils.getScreenHeight((Activity) view.getContext());
        int statusBarHeight = UiUtils.getStatusBarHeight(view.getContext());

        if (top < 0 && Math.abs(top) > halfHeight) {
            return;
        }
        if (top > screenHeight - halfHeight - statusBarHeight) {
            return;
        }

        //这里获取的是我们view绑定的数据，相应的你要去在你的view里setTag，只有set了，才能get
        ItemData tag = (ItemData) view.getTag();
        String key = tag.toString();
        if (TextUtils.isEmpty(key)) {
            return;
        }
        hashMap.put(key, !hashMap.containsKey(key) ? 1 : (hashMap.get(key) + 1));
        Log.i("qcl0402", key + "----出现次数：" + hashMap.get(key));
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


}
