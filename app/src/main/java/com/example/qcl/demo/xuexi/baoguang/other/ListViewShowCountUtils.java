package com.example.qcl.demo.xuexi.baoguang.other;

import android.graphics.Rect;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.example.qcl.demo.xuexi.baoguang.ItemData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 2019/4/2 13:31
 * author: qcl
 * desc: RecyclerView列表的安卓曝光量统计工具类
 * wechat:2501902696
 */
public class ListViewShowCountUtils {
    private ConcurrentHashMap<Integer, Long> timeHasMap = new ConcurrentHashMap<>();
    //用于统计曝光量的map
    private ConcurrentHashMap<String, Integer> hashMap = new ConcurrentHashMap<String, Integer>();

    private List<String> list = new ArrayList<>();

    /*
     * 统计RecyclerView里当前屏幕可见子view的曝光量
     *
     * */
    void recordViewShowCount(final RecyclerView recyclerView) {
        hashMap.clear();
        if (recyclerView == null || recyclerView.getVisibility() != View.VISIBLE) {
            return;
        }
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                int viewCode = view.hashCode();
                long currentTime = System.currentTimeMillis();
                timeHasMap.put(viewCode, currentTime);
                //                Log.i("qcl0408", viewCode + "初始化存入hasmap的时间:" + currentTime);
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                getViewShowTime(view);
            }
        });


        recyclerView.getParent();

        //        recyclerView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
        //            @Override
        //            public void onViewAttachedToWindow(View v) {
        //                Log.i("qcl0408", "rv---onViewAttachedToWindow");
        //            }
        //
        //            @Override
        //            public void onViewDetachedFromWindow(View v) {
        //                Log.i("qcl0408", "rv---onViewDetachedFromWindow");
        //            }
        //        });

        //检测recylerview的滚动事件
        //        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
        //            @Override
        //            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        //                /*
        //                我这里通过的是停止滚动后屏幕上可见view。如果滚动过程中的可见view也要统计，你可以根据newState去做区分
        //                SCROLL_STATE_IDLE:停止滚动
        //                SCROLL_STATE_DRAGGING: 用户慢慢拖动
        //                SCROLL_STATE_SETTLING：惯性滚动
        //                */
        //
        //                //                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
        //                //                    //第一种方法：  
        //                //                    getVisibleViews(recyclerView);
        //                //                }
        //            }
        //
        //            //            @Override
        //            //            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        //            //                super.onScrolled(recyclerView, dx, dy);
        //            //                Log.i("qcl0408", "onScrolled");
        //            //            }
        //        });


        ViewTreeObserver treeObserver = recyclerView.getViewTreeObserver();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            //从后台进入前台时也算一次曝光
            treeObserver.addOnWindowFocusChangeListener(new ViewTreeObserver.OnWindowFocusChangeListener() {
                @Override
                public void onWindowFocusChanged(boolean hasFocus) {
                    Log.i("qcl0403", "onWindowFocusChanged:" + hasFocus);
                    if (!hasFocus) {
                        getVisibleViews(recyclerView);
                    }
                }
            });
        }


    }

    //获取view从显示到消失的时间
    private void getViewShowTime(View view) {
        Long saveTime = timeHasMap.get(view.hashCode());
        long currentTime = System.currentTimeMillis();
        long time = currentTime - saveTime;//毫秒
        if (time > 2000) {
            float second = time / 1000f;//秒
            ItemData tag = (ItemData) view.getTag();
            String title = tag.getTitle();
            Log.i("qcl0408", view.hashCode() + title + "移出屏幕时间：" + second + "秒");
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
            int childCount = reView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = reView.getChildAt(i);
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
        //if (rect.width() >= view.getMeasuredWidth() && rect.height() >= view.getMeasuredHeight()) {
        //    //没有被覆盖
        //}
        getViewShowTime(view);

        //setTag和getTag
        //        ItemData tag = (ItemData) view.getTag();
        //        String key = tag.getTitle();
        //        Log.i("qcl0404", view.hashCode() + key + "可见");
        //        if (TextUtils.isEmpty(key)) {
        //            return;
        //        }
        //        //        hashMap.put(key, !hashMap.containsKey(key) ? 1 : (hashMap.get(key) + 1));
        //        //        Log.i("qcl0402", key + "----出现次数：" + hashMap.get(key));
        //        if (scrollNum % 2 == 0) {
        //            list.add(key);
        //            Log.i("qcl0404", "第一次：" + Arrays.toString(list.toArray()));
        //        } else {
        //            if (!list.contains(key)) {
        //                list.add(key);
        //                Log.i("qcl0404", "第二次：" + Arrays.toString(list.toArray()));
        //                list.clear();
        //            }
        //        }
    }

    /*
     * 过时的用法
     * */
    @Deprecated
    private int[] findRangeLinear(LinearLayoutManager manager) {
        int[] range = new int[2];
        range[0] = manager.findFirstVisibleItemPosition();
        range[1] = manager.findLastVisibleItemPosition();
        return range;
    }

    @Deprecated
    private int[] findRangeGrid(GridLayoutManager manager) {
        int[] range = new int[2];
        range[0] = manager.findFirstVisibleItemPosition();
        range[1] = manager.findLastVisibleItemPosition();
        return range;

    }

    @Deprecated
    private int[] findRangeStaggeredGrid(StaggeredGridLayoutManager manager) {
        int[] startPos = new int[manager.getSpanCount()];
        int[] endPos = new int[manager.getSpanCount()];
        manager.findFirstVisibleItemPositions(startPos);
        manager.findLastVisibleItemPositions(endPos);
        int[] range = findRange(startPos, endPos);
        return range;
    }

    @Deprecated
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
