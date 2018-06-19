package com.example.qcl.demo.shadowline;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 2018/6/19 15:27
 * author: qcl
 * desc: 用Paint画出带阴影带阴影的直线
 * wechat:2501902696
 */
public class ShadowLineView extends View {
    private Paint mPaint;
    //网格最大半径
    private float radius;
    //中心X
    private float centerX;
    //中心Y
    private float centerY;
    //弧度
    private float angle;


    public ShadowLineView(Context context) {
        this(context, null);
    }

    public ShadowLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        // 创建画笔
        mPaint = new Paint();
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setShadowLayer(10, 39, 39, Color.RED);
        angle = (float) (2 * Math.PI / 5);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        radius = Math.min(w, h) / 2 * 0.8f;
        centerX = w / 2;
        centerY = h / 2;
        //一旦size发生改变，重新绘制
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLine(canvas);
    }

    //画带阴影带阴影带直线
    private void drawLine(Canvas canvas) {
        Path path = new Path();
        path.reset();
        //直线1
        float x1 = (float) (centerX + radius * Math.sin(angle));
        float y1 = (float) (centerY - radius * Math.cos(angle));
        //直线2
        float x2 = (float) (centerX + radius * Math.sin(angle / 2));
        float y2 = (float) (centerY + radius * Math.cos(angle / 2));
        //直线3
        float x3 = (float) (centerX - radius * Math.sin(angle / 2));
        float y3 = (float) (centerY + radius * Math.cos(angle / 2));
        //直线4
        float x4 = (float) (centerX - radius * Math.sin(angle));
        float y4 = (float) (centerY - radius * Math.cos(angle));
        //直线5
        float x5 = (float) (centerX);
        float y5 = (float) (centerY - radius);
        mPaint.setColor(Color.GRAY);
        canvas.drawLine(x1, y1, x2, y2, mPaint);
        mPaint.setColor(Color.GREEN);


        canvas.drawLine(x2, y2, x3, y3, mPaint);
        mPaint.setColor(Color.RED);

        canvas.drawLine(x3, y3, x4, y4, mPaint);
        mPaint.setColor(Color.YELLOW);
        canvas.drawLine(x4, y4, x5, y5, mPaint);


        Paint mPaint5 = new Paint();
        mPaint5.setColor(Color.MAGENTA);
        mPaint5.setStrokeWidth(10);
        LinearGradient linearGradient = new LinearGradient(x5, y5, x1, y1, Color.GREEN, Color.BLUE, Shader.TileMode.CLAMP);
        mPaint5.setShader(linearGradient);
        canvas.drawLine(x5, y5, x1, y1, mPaint5);

    }
}
