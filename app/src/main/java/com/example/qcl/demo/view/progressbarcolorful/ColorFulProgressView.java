package com.example.qcl.demo.view.progressbarcolorful;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 2018/6/15 11:09
 * Created by qcl
 * wechat：2501902696
 */
public class ColorFulProgressView extends View {
    //中心X
    private float with;
    //中心Y
    private float height;
    //背景画笔
    private Paint bgPaint;
    private Paint mPaint;
    //百分比，默认50分
    private double percentage = 0.5f;
    private boolean fromLeft = true;

    public ColorFulProgressView(Context context) {
        this(context, null);
    }

    public ColorFulProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorFulProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        //背景画笔初始化
        bgPaint = new Paint();
        bgPaint.setColor(0xff666666);
        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.FILL);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        with = w;
        height = h;
        //一旦size发生改变，重新绘制
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBg(canvas);//画背景柱形图

    }

    private void drawBg(Canvas canvas) {
        //设置颜色渐变
        Shader shader = new LinearGradient(with / 2, 0, with / 2, height, Color.parseColor
                ("#E91E63"),
                Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
        bgPaint.setShader(shader);

        int radius = (int) with/9;
        RectF rect = new RectF(0, 0, with, height - radius);
        canvas.drawRect(rect, bgPaint);
        rect = new RectF(0, 0, with, height);
        canvas.drawRoundRect(rect, radius, radius, bgPaint);
    }


}