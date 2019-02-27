package com.example.qcl.demo.radarmap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.qcl.demo.R;

public class RadarMapActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_map);
        RadarViewRoot radarview = findViewById(R.id.radarview);
//        RadarViewNgmm radarview2 = findViewById(R.id.radarview2);

//        List<Double> datas = new ArrayList<>(5);
//        datas.add(100.0);//语文100分
//        datas.add(80.0);//数学80分
//        datas.add(90.0);//英语90分
//        datas.add(70.0);//政治70分
//        datas.add(60.0);//历史60分
//        radarview.setData(datas, true);
//
//        radarview2.setData(datas, false);
    }
}
