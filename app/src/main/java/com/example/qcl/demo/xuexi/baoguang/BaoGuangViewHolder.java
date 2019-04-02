package com.example.qcl.demo.xuexi.baoguang;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.qcl.demo.R;

public class BaoGuangViewHolder extends RecyclerView.ViewHolder {
    private TextView tv;
    private View viewRoot;

    public BaoGuangViewHolder(View view, Context context) {
        super(view);
        viewRoot = view;
        tv = itemView.findViewById(R.id.tv);
    }


    public void bindItemData(ItemData mItem) {
        if (mItem == null) {
            return;
        }
        tv.setText(mItem.getTitle() + "---" + mItem.getDesc());
        viewRoot.setTag(mItem);
    }


}
