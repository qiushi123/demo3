package com.example.qcl.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.qcl.demo.view.distanceview.DistanceActivity;
import com.example.qcl.demo.radarmap.RadarMapActivity;
import com.example.qcl.demo.xuexi.sharehaibao.ShareHaiBaoActivity;
import com.example.qcl.demo.xuexi.span.ImageTextActivity;
import com.example.qcl.demo.view.MainActivityView;
import com.example.qcl.demo.xuexi.vlayout.VlayoutActivity;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public class MainActivity extends RxAppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
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
    }

}
