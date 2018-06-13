package com.example.qcl.demo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.example.qcl.demo.R;

/**
 * Created by qcl on 2017/12/4.
 */

public class View1 extends View {
    Paint paint = new Paint();
    Path path = new Path(); // 初始化 Path 对象

    public View1(Context context) {
        super(context);
    }

    public View1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public View1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View1(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void init() {
        //        path.addArc(200, 200, 400, 400, -225, 225);
        //        path.arcTo(400, 200, 600, 400, -180, 225, false);
        //        path.lineTo(400, 542);

        //        paint.setColor(Color.parseColor("#ff99ff99"));
        //        paint.setStyle(Paint.Style.FILL);
        //        path.lineTo(100, 100);
        //        path.addArc(100, 100, 300, 300, -90, 90);
        //        Shader shader = new LinearGradient(60, 10, 500, 500,
        //                Color.parseColor("#E91E63"),
        //                Color.parseColor("#2196F3"),
        //                Shader.TileMode.REPEAT);
        //        paint.setShader(shader);


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //        canvas.drawPath(path, paint);
        paint.setTextSize(64);
        //        paint.setUnderlineText(true);//是否添加下划线
        //        paint.setTextSkewX(-0.5f);//正向左，负向右倾斜
        //        paint.setLetterSpacing(0.2f);//设置字体间距

        //        String text = "你";
        //        canvas.drawText(text, 100, 100, paint);
        //
        //        float textWidth = paint.measureText(text);
        //        Log.d("qcl123", "textWidth" + textWidth);


        //        ColorFilter colorFilter = new LightingColorFilter(0xffffff, 0x004400);
        //        paint.setColorFilter(colorFilter);

        //                paint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.NORMAL));
        Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.qcl)).getBitmap();
        canvas.save();
        canvas.clipRect(150, 150, 550, 550);
        canvas.drawBitmap(bitmap, 50, 50, paint);        //绘制图像
        canvas.restore();


    }
}
