package com.example.qcl.demo.xuexi.baoguang.other;

import android.view.View;

public interface RealVisibleInterface {
    void registerView(View v, OnRealVisibleListener listener);
 
    /**
     * 注册组合view  比如ListView LinearLayout RecyclerView 等
     * 需要计算其子item的展现
     * 注意LinearLayout 只能计算其子一级 不能子2级 3级
     * @param view
     * @param listener
     */
    void registerParentView(View  view, OnRealVisibleListener listener);
 
    void calculateRealVisible();
 
    /**
     * 清除打点
     */
    void clearRealVisibleTag();
 
    interface OnRealVisibleListener {
        void onRealVisible(int position);
    }
}