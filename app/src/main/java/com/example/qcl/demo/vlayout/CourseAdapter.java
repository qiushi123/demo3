package com.example.qcl.demo.vlayout;

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
public class CourseAdapter extends DelegateAdapter.Adapter<CourseViewHolder> {
    private Context context;
    private List<String> contents;

    public CourseAdapter(Context context) {
        this.context = context;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_vlayout, parent,
                false);
        return new CourseViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        String currentContent = contents.get(position);
        holder.bindItemData(currentContent);
    }

    @Override
    public int getItemCount() {
        if (contents == null) {
            return 0;
        }
        return contents.size();
    }


    public void setListData(List<String> data) {
        this.contents = data;
    }


}
