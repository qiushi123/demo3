package com.example.qcl.demo.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class UiUtils {
    //dip2px
    public static int dip2px(Context context, int dip) {
        //获取设备密度
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f);
    }


    //获取状态栏的高度
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        if (result <= 0) {
            result = dip2px(context, 24);//如果获取不到状态栏高度，就给个默认24dp（通常状态栏高度也就是24dp）
        }
        return result;
    }

    public static int getScreenHeight(Activity activity) {
        //不包含底部虚拟键的高度
        WindowManager manager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int screenHeight = outMetrics.heightPixels;
        return screenHeight;
    }

}
