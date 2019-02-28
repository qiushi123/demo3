package com.example.qcl.demo.mianshi;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.example.qcl.demo.R;

import java.lang.ref.WeakReference;

/*
 * 面试知识点----Handler
 *
 * */
public class HandlerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
    }


    private Handler sHandler = new TestHandler(this);
    //优化一：使用静态内部类+弱引用的方式， 静态内部类不会持有外部类的的引用
    static class TestHandler extends Handler {
        private WeakReference<Activity> mActivity;
        TestHandler(Activity activity) {
            mActivity = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Activity activity = mActivity.get();
            if (activity != null) {
                //TODO:
            }
        }
    }
    //优化二：在外部类对象被销毁时，将MessageQueue中的消息清空
    @Override
    protected void onDestroy() {
        sHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
