package com.example.qcl.demo.view;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.qcl.demo.R;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public class MainActivityView extends RxAppCompatActivity {
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);


    }

    private void test() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("qclqcl", "Hi");
                handler.postDelayed(this, 1000);
            }
        }, 6000);

    }

}
