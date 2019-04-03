package com.example.qcl.demo.xuexi.baoguang;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bumptech.glide.Glide;
import com.example.qcl.demo.R;

import java.util.ArrayList;
import java.util.List;

public class BaoGuangActivity extends AppCompatActivity {
    //普通布局
    private LinearLayout tvRoot;
    private TextView tv1, tv2;
    private ImageView iv3;

    //列表
    private RecyclerView recyclerView;
    private DelegateAdapter vAdapter;
    VirtualLayoutManager vLayoutManager;
    private BaoGuangAdapter adapter;

    private List<ItemData> listData = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_guang);
        recyclerView = findViewById(R.id.rv_list);
        vLayoutManager = new VirtualLayoutManager(this);
        initVlayout();
        initData();

        linearLayoutCount();

    }


    private void linearLayoutCount() {
        tvRoot = findViewById(R.id.tv_root);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        iv3 = findViewById(R.id.iv3);
        tv1.setTag("tv1");
        tv2.setTag("tv2");
        //        iv3.setTag("iv3");
        Glide.with(this)
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554268405878&di=19b64409045ff7a777fd16100151fd01&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F15%2F68%2F59%2F71X58PICNjx_1024.jpg")
                .into(iv3);


        Window window = getWindow();
        ViewGroup decorView = (ViewGroup) window.getDecorView();
        //        new RootViewShowCountUtils().recordViewShowCount(decorView);
        treeObserverList(decorView);
    }


    private void initData() {
        for (int i = 0; i < 40; i++) {
            listData.add(new ItemData("标题" + i, "描述" + i));
        }
        adapter.setListData(listData);
        adapter.notifyDataSetChanged();

        //        ListViewShowCountUtils listViewShowCountUtils = new ListViewShowCountUtils();
        //        listViewShowCountUtils.recordViewShowCount(recyclerView);

        //        treeObserverList(recyclerView);
    }

    //列表view树监听
    private void treeObserverList(ViewGroup viewGroup) {
        ViewTreeObserver treeObserver = viewGroup.getViewTreeObserver();

        //
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            treeObserver.addOnWindowFocusChangeListener(new ViewTreeObserver.OnWindowFocusChangeListener() {
                @Override
                public void onWindowFocusChanged(boolean hasFocus) {
                    /*
                    hasFocus是我们窗口状态改变时回传回来的值
                    true： 我们的view在前台展示
                    fasle: 熄屏，onpause，后台时展示
                    我们如果在每次熄屏到亮屏也算一次曝光的话，那这里为true的时候可以做统计
                    */

                }
            });
        }

        //布局改变
        treeObserver.addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Log.i("qcl0403", "view树改变");
                    }
                });
    }

    private void initVlayout() {
        vAdapter = new DelegateAdapter(vLayoutManager);
        recyclerView.setLayoutManager(vLayoutManager);
        recyclerView.setAdapter(vAdapter);
        adapter = new BaoGuangAdapter(this);
        vAdapter.addAdapter(adapter);
    }
}
