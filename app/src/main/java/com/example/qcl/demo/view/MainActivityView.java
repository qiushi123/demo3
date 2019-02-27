package com.example.qcl.demo.view;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

import com.example.qcl.demo.R;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public class MainActivityView extends RxAppCompatActivity {

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        ScrollView scrollView = findViewById(R.id.scrollView);


        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.d("qclqcl", "v.gettop" + v.getTop());
                Log.d("qclqcl", "v.getRotationY" + v.getRotationY());
                Log.d("qclqcl", "v.getY" + v.getY());
                Log.d("qclqcl", "v.getTranslationY" + v.getTranslationY());
                Log.d("qclqcl", "v.getPivotY" + v.getPivotY());
                Log.d("qclqcl", "v.getScrollY" + v.getScrollY());
                Log.d("qclqcl", ",scrollY" + scrollY);
            }
        });
        //        right.setSocre(80.8, false);
        //楼层导航
        //        FloorView floorView = findViewById(R.id.floor);
        //        ArrayList<String> tabsName = new ArrayList<>();
        //        tabsName.add("标题1");
        //        tabsName.add("标题2222");
        //        tabsName.add("标题3");
        //        tabsName.add("标题4");
        //        tabsName.add("标题5555");
        //        tabsName.add("标题6");
        //        tabsName.add("标题7777");
        //        tabsName.add("标题8");
        //        floorView.addDisplayItem(tabsName,1);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("问题：")
                .setMessage("请问你满十八岁了吗?")
                .setIcon(R.mipmap.ic_launcher_round)
                .show();

    }

}
