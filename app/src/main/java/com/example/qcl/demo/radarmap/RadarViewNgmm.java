package com.example.qcl.demo.radarmap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 2018/6/15 11:09
 * Created by qcl
 * wechat：2501902696
 */
public class RadarViewNgmm extends View {

    //数据个数
    private int count = 6;
    //覆盖区域边框宽度
    private int valueWith = 12;
    //网格最大半径
    private float radius;
    //中心X
    private float centerX;
    //中心Y
    private float centerY;
    //雷达区画笔
    private Paint mainPaint;
    //虚直线画笔
    private Paint linePaint;
    //文本画笔
    private Paint textPaint;
    //数据区画笔
    private Paint valuePaint;
    //各维度分值
    private List<Double> data;
    //覆盖区缩放比例
    private double blowUp = 1;
    //数据最大值
    private double maxValue = 100;
    //弧度
    private double angle;
    //分值
    private List<String> scores;


    //执行扩充动画相关
    private Handler mHandler;
    private Timer timer = null;
    private TimerTask timerTask = null;
    private float progress;


    public RadarViewNgmm(Context context) {
        this(context, null);
    }

    public RadarViewNgmm(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarViewNgmm(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        //雷达区画笔初始化
        mainPaint = new Paint();
        mainPaint.setColor(Color.BLACK);
        mainPaint.setAntiAlias(true);
        mainPaint.setStrokeWidth(2);
        //        mainPaint.setStyle(Paint.Style.STROKE);
        //虚直线画笔初始化
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(0xFF95FDFF);
        linePaint.setPathEffect(new DashPathEffect(new float[]{4, 5}, 0));
        linePaint.setStrokeWidth(3);
        linePaint.setStyle(Paint.Style.STROKE);
        //文本画笔初始化
        textPaint = new Paint();
        textPaint.setColor(0xFF95FDFF);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(20);
        textPaint.setStrokeWidth(1);
        textPaint.setAntiAlias(true);
        //数据区（分数）画笔初始化
        valuePaint = new Paint();
        valuePaint.setColor(0xffD6F2F8);
        valuePaint.setAntiAlias(true);
        valuePaint.setStrokeWidth(valueWith);
        valuePaint.setStrokeJoin(Paint.Join.ROUND);

        //1度=1*PI/180   360度=2*PI   那么我们每旋转一次的角度为2*PI/内角个数
        //中心与相邻两个内角相连的夹角角度
        angle = (float) (2 * Math.PI / 5);

        scores = new ArrayList<>(6);
        scores.add("0");
        scores.add("20");
        scores.add("40");
        scores.add("60");
        scores.add("80");
        scores.add("100");

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        radius = Math.min(w, h) / 2 * 0.85f;
        centerX = w / 2;
        centerY = h / 2;
        //一旦size发生改变，重新绘制
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawCircle(canvas);//绘制背景圆
        drawPolygon(canvas);//绘制蜘蛛网
        drawLines(canvas);//绘制直线
        drawTitle(canvas);//绘制分值
        if (data != null && data.size() > 0) {
            drawRegion(canvas);//绘制覆盖区域
        }
    }


    /**
     * 画背景圆
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        mainPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mainPaint.setColor(0x55FFFFFF);
        canvas.drawCircle(centerX, centerY, radius, mainPaint);
    }

    /**
     * 绘制雷达圆形
     *
     * @param canvas
     */
    private void drawPolygon(Canvas canvas) {
        Path path = new Path();
        mainPaint.setStyle(Paint.Style.STROKE);
        mainPaint.setColor(0xFF95FDFF);
        //每个蛛丝之间的间距
        float r = radius / (count - 1);
        for (int i = 1; i < count; i++) {
            //当前半径
            float curR = r * i;
            path.reset();
            if (i == count - 1) {
                mainPaint.setColor(0x0095FDFF);
            }
            canvas.drawCircle(centerX, centerY, curR, mainPaint);

        }
    }


    /**
     * 绘制直虚线
     */
    private void drawLines(Canvas canvas) {
        Path path = new Path();
        path.reset();
        double addradius = radius + 35f;
        //直线1
        path.moveTo(centerX, centerY);
        float x1 = centerX;
        float y1 = (float) (centerY - addradius);
        path.lineTo(x1, y1);
        //直线2
        path.moveTo(centerX, centerY);
        float x2 = (float) (centerX + addradius * Math.sin(angle));
        float y2 = (float) (centerY - addradius * Math.cos(angle));
        path.lineTo(x2, y2);
        //直线3
        path.moveTo(centerX, centerY);
        float x3 = (float) (centerX + addradius * Math.sin(angle / 2));
        float y3 = (float) (centerY + addradius * Math.cos(angle / 2));
        path.lineTo(x3, y3);
        //直线4
        path.moveTo(centerX, centerY);
        float x4 = (float) (centerX - addradius * Math.sin(angle / 2));
        float y4 = (float) (centerY + addradius * Math.cos(angle / 2));
        path.lineTo(x4, y4);
        //直线5
        path.moveTo(centerX, centerY);
        float x5 = (float) (centerX - addradius * Math.sin(angle));
        float y5 = (float) (centerY - addradius * Math.cos(angle));
        path.lineTo(x5, y5);
        path.close();
        canvas.drawPath(path, linePaint);
    }


    /**
     * 绘制覆盖区域
     */
    private void drawRegion(Canvas canvas) {
        Path path = new Path();
        double dataValue;
        double percent;
        //绘制圆点1
        dataValue = data.get(0) * blowUp;
        if (dataValue != maxValue) {
            percent = dataValue / maxValue;
        } else {
            percent = 1;
        }
        float x1 = centerX;
        float y1 = (float) (centerY - radius * percent);
        path.moveTo(x1, y1);
        //绘制圆点2
        dataValue = data.get(1) * blowUp;
        if (dataValue != maxValue) {
            percent = dataValue / maxValue;
        } else {
            percent = 1;
        }
        float x2 = (float) (centerX + radius * percent * Math.sin(angle));
        float y2 = (float) (centerY - radius * percent * Math.cos(angle));
        path.lineTo(x2, y2);
        //绘制圆点3
        dataValue = data.get(2) * blowUp;
        if (dataValue != maxValue) {
            percent = dataValue / maxValue;
        } else {
            percent = 1;
        }
        float x3 = (float) (centerX + radius * percent * Math.sin(angle / 2));
        float y3 = (float) (centerY + radius * percent * Math.cos(angle / 2));
        path.lineTo(x3, y3);
        //绘制圆点4
        dataValue = data.get(3) * blowUp;
        if (dataValue != maxValue) {
            percent = dataValue / maxValue;
        } else {
            percent = 1;
        }
        float x4 = (float) (centerX - radius * percent * Math.sin(angle / 2));
        float y4 = (float) (centerY + radius * percent * Math.cos(angle / 2));
        path.lineTo(x4, y4);
        //绘制圆点5
        dataValue = data.get(4) * blowUp;
        if (dataValue != maxValue) {
            percent = dataValue / maxValue;
        } else {
            percent = 1;
        }
        float x5 = (float) (centerX - radius * percent * Math.sin(angle));
        float y5 = (float) (centerY - radius * percent * Math.cos(angle));
        path.lineTo(x5, y5);
        path.close();

        //绘制覆盖区域外的连线
        valuePaint.setAlpha(255);
        valuePaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, valuePaint);

        //填充覆盖区域
        valuePaint.setAlpha(128);
        valuePaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, valuePaint);

    }

    /**
     * 绘制分值文字
     *
     * @param canvas
     */
    private void drawTitle(Canvas canvas) {
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;//标题高度
        //每个蛛丝之间的间距
        float r = radius / (count - 1);
        for (int i = 0; i < scores.size(); i++) {
            //当前半径
            float curR = r * i;
            if (i == scores.size() - 1) {
                canvas.drawText(scores.get(i), centerX - 20, centerY - curR - fontHeight / 2,
                        textPaint);
            } else {
                canvas.drawText(scores.get(i), centerX - 15, centerY - curR - fontHeight / 2,
                        textPaint);
            }
        }
    }


    //设置各门得分
    public void setData(List<Double> data, boolean isShowAnimation) {
        if (data == null || data.size() != 5) {
            return;
        }
        this.data = data;
        if (isShowAnimation) {
            setBlowUp(0);
            initHandler();
            startTimer();
        } else {
            setBlowUp(1);
            postInvalidate();
        }

    }

    //设置放大
    private void setBlowUp(double blowUp) {
        this.blowUp = blowUp;
        postInvalidate();
    }


    @SuppressLint("HandlerLeak")
    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        if (progress >= 100f) {
                            stopTimer();
                            return;
                        }
                        setBlowUp(progress / 100f);
                        break;
                }
            }
        };
    }

    private void startTimer() {
        stopTimer();
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                progress++;//这边也是子线程，用于发消息更新UI
                Message message = new Message();
                message.what = 0;
                mHandler.sendMessage(message);

            }
        };
        timer.schedule(timerTask, 0, 20);//第三个参数代表我们之前定义的休眠的1秒，即我们定义的重复周期
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }

}