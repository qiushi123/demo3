package com.example.qcl.demo.view;

import android.os.Bundle;

import com.example.qcl.demo.R;
import com.example.qcl.demo.floorview.FloorView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;

public class MainActivityView extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        FloorView floorView = findViewById(R.id.floor);
        ArrayList<String> tabsName = new ArrayList<>();
        tabsName.add("标题1");
        tabsName.add("标题2222");
        tabsName.add("标题3");
        tabsName.add("标题4");
        tabsName.add("标题5555");
        tabsName.add("标题6");
        tabsName.add("标题7777");
        tabsName.add("标题8");
        floorView.addDisplayItem(tabsName,1);
    }


}
