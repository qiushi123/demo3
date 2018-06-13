package com.example.qcl.demo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by qcl on 2017/12/5.
 */

@SuppressLint("AppCompatCustomView")
public class MyImageView extends ImageView {
    Paint paint = new Paint();


    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("qcl333", "宽高" + this.getWidth() + "x" + this.getHeight());
        super.onDraw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);

        paint.setTextSize(64);
        paint.setColor(Color.GREEN);
        canvas.drawText("我是前置", 150, 250, paint);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        paint.setTextSize(64);
        paint.setColor(Color.BLUE);
        canvas.drawText("我是我是draw", 150, 250, paint);
    }
}
