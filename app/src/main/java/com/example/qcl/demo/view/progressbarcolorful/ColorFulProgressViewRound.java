package com.example.qcl.demo.view.progressbarcolorful;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 2018/6/15 11:09
 * Created by qcl
 * 多彩的水平圆柱形
 */
public class ColorFulProgressViewRound extends View {
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

    public ColorFulProgressViewRound(Context context) {
        this(context, null);
    }

    public ColorFulProgressViewRound(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorFulProgressViewRound(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        //背景画笔初始化
        bgPaint = new Paint();
        bgPaint.setColor(0xff666666);
        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.FILL);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

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
        drawRegion(canvas);//绘制覆盖区域

    }

    private void drawBg(Canvas canvas) {
        RectF rect = new RectF(0, 0, with, height);
        int radius = 30;
        canvas.drawRoundRect(rect, radius, radius, bgPaint);
    }


    /**
     * 绘制覆盖区域
     */
    private void drawRegion(Canvas canvas) {
        if (percentage < 0.6f) {//红色不及格
            mPaint.setColor(0xffFE8468);
        } else if (percentage < 0.8f) {//黄色中等
            mPaint.setColor(0xffFFDC41);
        } else {//绿色优秀
            mPaint.setColor(0xff7ED321);
        }

        RectF rect;
        if (fromLeft) {
            rect = new RectF(0, 0, (float) (with * percentage), height);
        } else {
            rect = new RectF((float) (with * (1 - percentage)), 0, with, height);
        }

        int radius = 30;
        canvas.drawRoundRect(rect, radius, radius, mPaint);
    }

    /**
     * @param socre    分数，满分是100
     * @param fromLeft 从左边开始画，还是从右边开始画
     */
    public void setSocre(double socre, boolean fromLeft) {
        percentage = socre / 100;
        this.fromLeft = fromLeft;
    }


}