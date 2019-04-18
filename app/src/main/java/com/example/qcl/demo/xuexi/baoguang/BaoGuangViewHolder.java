package com.example.qcl.demo.xuexi.baoguang;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qcl.demo.R;

public class BaoGuangViewHolder extends RecyclerView.ViewHolder {
    private Context mContext;
    private TextView tv;
    private ImageView iv;
    private View viewRoot;

    public BaoGuangViewHolder(View view, Context context) {
        super(view);
        mContext = context;
        viewRoot = view;
        tv = itemView.findViewById(R.id.tv);
        iv = itemView.findViewById(R.id.iv);
    }


    public void bindItemData(ItemData mItem) {
        if (mItem == null) {
            return;
        }
        tv.setText(mItem.getTitle());
        Glide.with(mContext)
                .load(mItem.getImgUrl())
                .into(iv);
        //        Log.i("qcl0404", viewRoot.hashCode() + mItem.getTitle() +
        //                "被加载==================================");
        viewRoot.setTag(mItem);

        tv.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                if (v instanceof TextView) {
                    Log.i("qcl0415", v.hashCode() + ((TextView) v).getText().toString() +
                            "添加进屏幕");
                }
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                if (v instanceof TextView) {
                    Log.i("qcl0415", v.hashCode() + ((TextView) v).getText().toString() +
                            "移除屏幕");
                }
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            tv.getViewTreeObserver().addOnWindowFocusChangeListener(new ViewTreeObserver.OnWindowFocusChangeListener() {
                @Override
                public void onWindowFocusChanged(boolean hasFocus) {
                    Log.i("qcl0415", "hasFocus" + hasFocus);
                }
            });
        }
    }
    //    private class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
    //
    //        @Override
    //        public void onGlobalLayout() {
    //            //默认会调用俩次，只需要一次，第一次进入就移除
    //            tv.getViewTreeObserver().removeGlobalOnLayoutListener(MyOnGlobalLayoutListener.this);
    //            Log.i("qcl0415", "view树改变");
    //        }
    //    }


}
