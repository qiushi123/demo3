package com.example.qcl.demo.floorview;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.qcl.demo.R;
import com.example.qcl.demo.utils.UiUtils;

import java.util.ArrayList;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Company    : 年糕妈妈
 * Author     : 段宇鹏
 * Date       : 2/22/17
 */
public class IndicatorDisplayScrollView extends LinearLayout implements View.OnClickListener {

    private Context context;
    private View widgetView;
    private LinearLayout llDisplayContaner;
    private OnSelectListener onSelectListener;
    private ArrayList<Button> buttons;

    public static String tag = "IndicatorDisplayScrollView";
    private LinearLayout llEmptyArea;

    public IndicatorDisplayScrollView(Context context) {
        this(context, null);
    }

    public IndicatorDisplayScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public IndicatorDisplayScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        widgetView = inflate(context, R.layout.widget_indicator_display_scroll_view, this);

        initView();
        initListener();
        initData();
    }

    private void initView() {
        llDisplayContaner = (LinearLayout) widgetView.findViewById(R.id.ll_indicator_display_container);
        llEmptyArea = (LinearLayout) widgetView.findViewById(R.id.ll_empty_area);
    }

    private void initListener() {
        llEmptyArea.setOnClickListener(this);
    }

    private void initData() {
        buttons = new ArrayList<>();
    }

    public void addDisplayItem(ArrayList<String> lists) {
        LinearLayout linearLayout = null;
        for (int i = 0; i < lists.size(); i++) {
            if (i % 4 == 0) {
                linearLayout = new LinearLayout(context);
                linearLayout.setWeightSum(4);
                LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, 0, UiUtils.dip2px(context, 10));
                linearLayout.setLayoutParams(layoutParams);
                linearLayout.setOrientation(HORIZONTAL);
            }
            Button button = createButton(lists.get(i));
            buttons.add(button);
            if (i % 4 == 1) {
                LayoutParams layoutParams = (LayoutParams) button.getLayoutParams();
                layoutParams.setMargins(UiUtils.dip2px(context, 6), 0, UiUtils.dip2px(context, 3), 0);
                button.setLayoutParams(layoutParams);
            }
            if (i % 4 == 2) {
                LayoutParams layoutParams = (LayoutParams) button.getLayoutParams();
                layoutParams.setMargins(UiUtils.dip2px(context, 3), 0, UiUtils.dip2px(context, 6), 0);
                button.setLayoutParams(layoutParams);
            }
            button.setTag(i);
            button.setOnClickListener(this);
            linearLayout.addView(button);

            if (i % 4 == 3) {
                llDisplayContaner.addView(linearLayout);
                linearLayout = null;
            }
        }

        if (linearLayout != null) {
            int childCount = linearLayout.getChildCount();
            if (childCount == 1) {
                linearLayout.setPadding(0, 0, UiUtils.dip2px(context, 18), 0);
            } else if (childCount == 2) {
                linearLayout.setPadding(0, 0, UiUtils.dip2px(context, 11), 0);
            } else if (childCount == 3) {
                linearLayout.setPadding(0, 0, UiUtils.dip2px(context, 1), 0);
            }

            llDisplayContaner.addView(linearLayout);
        }
    }

    private Button createButton(String text) {
        Button button = new Button(context);

        LayoutParams layoutParams = new LayoutParams(0, UiUtils.dip2px(context, 27));
        layoutParams.weight = 1;
        button.setLayoutParams(layoutParams);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            button.setBackground(context.getResources().getDrawable(R.drawable.shape_show_all_indicator_rectangle));
        }
        button.setTextSize(12);
        button.setTextColor(getResources().getColor(R.color.grey666));
        button.setPadding(0, 0, 0, 0);
        button.setText(text);
        return button;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_empty_area) {
            putAway();
            return;
        }
        int position = (int) v.getTag();
        onSelectListener.doInSelect(position);
    }

    public void clearAllChildeView() {
        buttons = new ArrayList<>();
        llDisplayContaner.removeAllViews();
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
