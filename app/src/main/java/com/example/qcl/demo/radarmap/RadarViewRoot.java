package com.example.qcl.demo.radarmap;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.qcl.demo.R;

import java.util.ArrayList;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Company    : 年糕妈妈
 * Author     : 段宇鹏
 * Date       : 2/22/17
 */
public class RadarViewRoot extends LinearLayout implements View.OnClickListener {

    private Context context;
    private View rootView;
    private OnSelectListener onSelectListener;
    private ArrayList<Button> buttons;
    private Button button1, button2, button3, button4, button5;

    public RadarViewRoot(Context context) {
        this(context, null);
    }

    public RadarViewRoot(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarViewRoot(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.d("qclqcl", "onLayout,l:" + l + ",t:" + t);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d("qclqcl", "w:" + w + ",h:" + h);
        //一旦size发生改变，重新绘制
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }


    private void initView() {
        Log.d("qclqcl", "initView:");
        rootView = inflate(context, R.layout.rardarview_root, this);

        button1 = rootView.findViewById(R.id.button1);
        button2 = rootView.findViewById(R.id.button2);
        button3 = rootView.findViewById(R.id.button3);
        button4 = rootView.findViewById(R.id.button4);
        button5 = rootView.findViewById(R.id.button5);

        buttons = new ArrayList<>(5);
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        buttons.add(button5);

        button1.setTag(0);
        button1.setOnClickListener(this);
        button2.setTag(1);
        button2.setOnClickListener(this);
        button3.setTag(2);
        button3.setOnClickListener(this);
        button4.setTag(3);
        button4.setOnClickListener(this);
        button5.setTag(4);
        button5.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        setSelectButton(position);
        //        onSelectListener.doInSelect(position);
    }


    /**
     * Description: 选中监听
     * Copyright  : Copyright (c) 2017
     * Company    : 年糕妈妈
     * Author     : 段宇鹏
     * Date       : 2/26/17
     */
    public interface OnSelectListener {
        void doInSelect(int select);

        void putAway();
    }

    public OnSelectListener getOnSelectListener() {
        return onSelectListener;
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    public void putAway() {
        onSelectListener.putAway();
    }

    public void setSelectButton(int index) {
        if (buttons == null) {
            return;
        }
        for (int i = 0; i < buttons.size(); i++) {
            if (i == index) {
                setSelected(buttons.get(i));
            } else {
                setUnSelected(buttons.get(i));
            }
        }
    }

    private void setSelected(Button button) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            button.setBackground(context.getResources().getDrawable(R.drawable.shape_show_all_indicator_rectangle_active));
        }
        button.setTextColor(getResources().getColor(R.color.colorChoiceBlue));

    }

    private void setUnSelected(Button button) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            button.setBackground(context.getResources().getDrawable(R.drawable.shape_show_all_indicator_rectangle));
        }
        button.setTextColor(getResources().getColor(R.color.grey666));
    }
}
