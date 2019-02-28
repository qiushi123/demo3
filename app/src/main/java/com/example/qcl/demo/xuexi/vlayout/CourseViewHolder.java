package com.example.qcl.demo.xuexi.vlayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.qcl.demo.R;

public class CourseViewHolder extends RecyclerView.ViewHolder {
    private TextView tv;


    public CourseViewHolder(View view, Context context) {
        super(view);
        tv = (TextView) itemView.findViewById(R.id.tv);
    }


    public void bindItemData(final String mItem) {
        if (mItem == null) {
            return;
        }
        tv.setText(mItem);
    }


}
