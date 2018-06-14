package com.example.qcl.demo.utils;

import android.content.Context;

public class UiUtils {
    //dip2px
    public static int dip2px(Context context, int dip) {
        //获取设备密度
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f);
    }


}
