package com.example.qcl.demo.xuexi.baoguang.other;

import android.graphics.Rect;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import com.example.qcl.demo.xuexi.baoguang.ItemData;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 2019/4/2 13:31
 * author: qcl
 * desc: RecyclerView列表的安卓曝光量统计工具类
 * wechat:2501902696
 */
public class RecyclerViewShowCountUtils {
    //用于统计曝光量的map
    private ConcurrentHashMap<String, Integer> hashMap = new ConcurrentHashMap<String, Integer>();

    /*
     * 统计RecyclerView里当前屏幕可见子view的曝光量
     *
     * */
    void recordViewShowCount(final RecyclerView recyclerView) {
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
        });

        Button view=new Button(recyclerView.getContext());
        view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {

            }

            @Override
            public void onViewDetachedFromWindow(View v) {

            }
        });


        //从后台进入前台时也算一次曝光
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            recyclerView.getViewTreeObserver().addOnWindowFocusChangeListener(new ViewTreeObserver.OnWindowFocusChangeListener() {
                @Override
                public void onWindowFocusChanged(boolean hasFocus) {
                    if (hasFocus) {
                        Log.i("qcl0403", "onWindowFocusChanged:" + hasFocus);
                        getVisibleViews(recyclerView);
                    }
                }
            });
        }

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

        Rect rect = new Rect();
        boolean cover = view.getGlobalVisibleRect(rect);
        if (!cover) {
            return;
        }
        if (rect.height() < view.getMeasuredHeight() / 2) {
            //大于一半被覆盖,就不统计
            return;
        }
        //        if (rect.width() >= view.getMeasuredWidth() && rect.height() >= view.getMeasuredHeight()) {
        //            //没有被覆盖
        //        }

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
