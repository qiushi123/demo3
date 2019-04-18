package com.example.qcl.demo;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.SparseArray;
import android.view.View;

import com.example.qcl.demo.mianshi.java2js.JavaH5Activity;
import com.example.qcl.demo.radarmap.RadarMapActivity;
import com.example.qcl.demo.view.MainActivityView;
import com.example.qcl.demo.view.distanceview.DistanceActivity;
import com.example.qcl.demo.xuexi.baoguang.BaoGuangActivity;
import com.example.qcl.demo.xuexi.rxjava.MainActivityRX;
import com.example.qcl.demo.xuexi.sharehaibao.ShareHaiBaoActivity;
import com.example.qcl.demo.xuexi.span.ImageTextActivity;
import com.example.qcl.demo.xuexi.vlayout.VlayoutActivity;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public class MainActivity extends RxAppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        SparseArray hashMap = new SparseArray();
        hashMap.append(1, "2");
        hashMap.get(2);
    }

    private void initview() {
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ImageTextActivity.class));
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DistanceActivity.class));
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, VlayoutActivity.class));
            }
        });
        //自定义view
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivityView.class));
            }
        });
        //        雷达图-蜘蛛网图
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RadarMapActivity.class));
            }
        });

        //生成海报，并显示出来
        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ShareHaiBaoActivity.class));
            }
        });
        //java和h5的js交互
        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, JavaH5Activity.class));
            }
        });
        //曝光量
        findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BaoGuangActivity.class));
            }
        });
        //rxjava2学习
        findViewById(R.id.button9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivityRX.class));
            }
        });
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
