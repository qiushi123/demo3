package com.example.qcl.demo.floorview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.example.qcl.demo.R;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class FloorView extends LinearLayout {
    private Context context;
    private View widgetView;
    private RecyclerView recyclerView;
    MyAdapter myAdapter;

    private boolean isArrowDown;
    private ImageView ivArrow;

    VirtualLayoutManager vLayoutManager;

    private int selectNum = 0;
    private List<String> listAll;
    private List<String> listFirstLine;

    public FloorView(Context context) {
        this(context, null);
    }

    public FloorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public FloorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        widgetView = inflate(context, R.layout.widget_indicator_floor_view, this);
        initRecylerView();
        initArrow();
    }

    private void initArrow() {
        ivArrow = widgetView.findViewById(R.id.iv_arrow);
        ivArrow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isArrowDown) {

                } else {

                }
                isArrowDown = !isArrowDown;
                setArrow(isArrowDown);
            }
        });
    }

    private void initRecylerView() {
        recyclerView = widgetView.findViewById(R.id.rv_list);
        //设置flexbox
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(context);
        //        layoutManager.setFlexDirection(FlexDirection.ROW);
        //        layoutManager.setJustifyContent(JustifyContent.FLEX_END);
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
    }

    public void addDisplayItem(ArrayList<String> lists, int num) {
        if (lists == null) {
            return;
        }
        listAll = lists;
        myAdapter.setSelectPosition(num);

        if (lists.size() > 4) {
            listFirstLine = lists.subList(0, 4);
            ivArrow.setVisibility(View.VISIBLE);
            setArrow(true);
            myAdapter.setListData(listFirstLine);
            myAdapter.notifyDataSetChanged();
        } else {
            ivArrow.setVisibility(View.GONE);
            myAdapter.setListData(listAll);
            myAdapter.notifyDataSetChanged();
        }
    }

    //展开或者折叠的箭头
    public void setArrow(boolean isDown) {
        if (isDown) {
            ivArrow.setImageResource(R.drawable.arrow_down);
            myAdapter.setListData(listAll);
            myAdapter.notifyDataSetChanged();
        } else {
            ivArrow.setImageResource(R.drawable.arrow_up);
            myAdapter.setListData(listFirstLine);
            myAdapter.notifyDataSetChanged();
        }
    }

    public void setRecyclerView(RecyclerView outRecyclerView, VirtualLayoutManager
            vLayoutManager2) {
        this.vLayoutManager = vLayoutManager2;
        outRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int firstVisibleItemPosition = vLayoutManager.findFirstVisibleItemPosition();
                myAdapter.setSelectPosition((firstVisibleItemPosition / 5) % 8);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public void selectPosition(int position) {
        if (vLayoutManager == null) {
            return;
        }
        LinearSmoothScroller smoothScroller = new LinearSmoothScroller
                (context) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }

            //设置滑动1px所需时间
            @Override
            protected float calculateSpeedPerPixel
            (DisplayMetrics displayMetrics) {
                //缩短每px的滑动时间
                float MILLISECONDS_PER_INCH = getResources().getDisplayMetrics()
                        .density * 0.03f;
                return MILLISECONDS_PER_INCH / displayMetrics.density;
                //返回滑动一个pixel需要多少毫秒
            }
        };
        smoothScroller.setTargetPosition(position * 5);
        vLayoutManager.startSmoothScroll(smoothScroller);
        myAdapter.setSelectPosition(position);
    }

    /**
     * 用内部类来写adapter
     */
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<String> contents;

        public void setListData(List<String> lists) {
            this.contents = lists;
        }

        public void setSelectPosition(int num) {
            selectNum = num;
            notifyDataSetChanged();
        }


        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_floor, viewGroup, false);
            ViewHolder vh = new ViewHolder(view);
            return vh;
        }

        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            viewHolder.mTextView.setText(contents.get(position));
            if (selectNum == position) {
                viewHolder.mTextView.setBackgroundResource(R.drawable.shape_show_all_indicator_rectangle_active);
                viewHolder.mTextView.setTextColor(0xff12CFC9);
            } else {
                viewHolder.mTextView.setBackgroundResource(R.drawable.shape_show_all_indicator_rectangle);
                viewHolder.mTextView.setTextColor(0xff666666);
            }
            viewHolder.mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelectPosition(position);
                    selectPosition(position);
                }
            });
        }

        //获取数据的数量
        @Override
        public int getItemCount() {
            return contents.size();
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        class ViewHolder extends RecyclerView.ViewHolder {

            TextView mTextView;

            ViewHolder(View view) {
                super(view);
                mTextView = view.findViewById(R.id.tv);
            }
        }


    }

}
