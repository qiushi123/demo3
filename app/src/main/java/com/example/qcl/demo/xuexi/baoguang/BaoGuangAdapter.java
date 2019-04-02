package com.example.qcl.demo.xuexi.baoguang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.example.qcl.demo.R;

import java.util.List;

/*
 *  搜索结果---课程的adapter
 *  qcl 2017-12-20
 * */
public class BaoGuangAdapter extends DelegateAdapter.Adapter<BaoGuangViewHolder> {
    private Context context;
    private List<ItemData> contents;

    public BaoGuangAdapter(Context context) {
        this.context = context;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }

    @Override
    public BaoGuangViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_vlayout, parent,
                false);
        return new BaoGuangViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(BaoGuangViewHolder holder, int position) {
        ItemData currentContent = contents.get(position);
        holder.bindItemData(currentContent);
    }

    @Override
    public int getItemCount() {
        if (contents == null) {
            return 0;
        }
        return contents.size();
    }


    public void setListData(List<ItemData> data) {
        this.contents = data;
    }


}
