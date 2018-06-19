package com.example.qcl.demo.radarmap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
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
public class RadarView extends View {

    //数据个数
    private int count = 5;
    //成绩圆点半径
    private int valueRadius = 8;
    //网格最大半径
    private float radius;
    //中心X
    private float centerX;
    //中心Y
    private float centerY;
    //雷达区画笔
    private Paint mainPaint;
    //文本画笔
    private Paint textPaint;
    //数据区画笔
    private Paint valuePaint;
    //标题文字
    private List<String> titles;
    //各维度分值
    private List<Double> data;
    private double blowUp = 1;
    //数据最大值
    private double maxValue = 100;
    //弧度
    private float angle;


    //执行扩充动画相关
    private Handler mHandler;
    private Timer timer = null;
    private TimerTask timerTask = null;
    private float progress;

    //点击事件相关
    //用来存放点击区域
    private List<TouchArea> touchAreaList = new ArrayList<TouchArea>(5);
    private boolean hasAdd;
    private int clickTextPosition;

    public RadarView(Context context) {
        this(context, null);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        //雷达区画笔初始化
        mainPaint = new Paint();
        mainPaint.setColor(Color.BLACK);
        mainPaint.setAntiAlias(true);
        mainPaint.setStrokeWidth(1);
        mainPaint.setStyle(Paint.Style.STROKE);
        //文本画笔初始化
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(30);
        textPaint.setStrokeWidth(1);
        textPaint.setAntiAlias(true);
        //数据区（分数）画笔初始化
        valuePaint = new Paint();
        valuePaint.setColor(Color.RED);
        valuePaint.setAntiAlias(true);
        valuePaint.setStyle(Paint.Style.FILL);

        titles = new ArrayList<>(5);
        titles.add("语文");
        titles.add("数学");
        titles.add("英语");
        titles.add("政治");
        titles.add("历史");
        count = titles.size();

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
        drawPolygon(canvas);//绘制蜘蛛网
        drawLines(canvas);//绘制直线
        drawTitle(canvas);//绘制标题
        drawRegion(canvas);//绘制覆盖区域
    }

    /**
     * 绘制多边形
     *
     * @param canvas
     */
    private void drawPolygon(Canvas canvas) {
        Path path = new Path();
        //1度=1*PI/180   360度=2*PI   那么我们每旋转一次的角度为2*PI/内角个数
        //中心与相邻两个内角相连的夹角角度
        angle = (float) (2 * Math.PI / count);
        //每个蛛丝之间的间距
        float r = radius / (count - 1);

        for (int i = 0; i < count; i++) {
            //当前半径
            float curR = r * i;
            path.reset();
            for (int j = 0; j < count; j++) {
                if (j == 0) {
                    float x = (float) (centerX + curR * Math.sin(angle));
                    float y = (float) (centerY - curR * Math.cos(angle));
                    path.moveTo(x, y);
                } else {
                    //根据半径，计算出蜘蛛丝上每个点的坐标
                    float x1 = (float) (centerX + curR * Math.sin(angle / 2));
                    float y1 = (float) (centerY + curR * Math.cos(angle / 2));
                    path.lineTo(x1, y1);
                    float x2 = (float) (centerX - curR * Math.sin(angle / 2));
                    float y2 = (float) (centerY + curR * Math.cos(angle / 2));
                    path.lineTo(x2, y2);
                    float x3 = (float) (centerX - curR * Math.sin(angle));
                    float y3 = (float) (centerY - curR * Math.cos(angle));
                    path.lineTo(x3, y3);
                    float x4 = centerX;
                    float y4 = centerY - curR;
                    path.lineTo(x4, y4);
                    float x = (float) (centerX + curR * Math.sin(angle));
                    float y = (float) (centerY - curR * Math.cos(angle));
                    path.lineTo(x, y);
                }
            }
            path.close();
            canvas.drawPath(path, mainPaint);
        }
    }


    /**
     * 绘制直线
     */
    private void drawLines(Canvas canvas) {
        Path path = new Path();
        path.reset();
        //直线1
        path.moveTo(centerX, centerY);
        float x1 = (float) (centerX + radius * Math.sin(angle));
        float y1 = (float) (centerY - radius * Math.cos(angle));
        path.lineTo(x1, y1);
        //直线2
        path.moveTo(centerX, centerY);
        float x2 = (float) (centerX + radius * Math.sin(angle / 2));
        float y2 = (float) (centerY + radius * Math.cos(angle / 2));
        path.lineTo(x2, y2);
        //直线3
        path.moveTo(centerX, centerY);
        float x3 = (float) (centerX - radius * Math.sin(angle / 2));
        float y3 = (float) (centerY + radius * Math.cos(angle / 2));
        path.lineTo(x3, y3);
        //直线4
        path.moveTo(centerX, centerY);
        float x4 = (float) (centerX - radius * Math.sin(angle));
        float y4 = (float) (centerY - radius * Math.cos(angle));
        path.lineTo(x4, y4);
        //直线5
        path.moveTo(centerX, centerY);
        float x5 = (float) (centerX);
        float y5 = (float) (centerY - radius);
        path.lineTo(x5, y5);
        path.close();
        canvas.drawPath(path, mainPaint);
    }

    /**
     * 绘制标题文字
     *
     * @param canvas
     */
    private void drawTitle(Canvas canvas) {
        if (count != titles.size()) {
            return;
        }
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;//标题高度
        //绘制文字1
        float x1 = centerX;
        float y1 = centerY - radius;
        Log.d("qclqcl", "x1:" + x1 + ",y1" + y1);
        if (clickTextPosition == 1) {
            textPaint.setColor(Color.RED);
        } else {
            textPaint.setColor(Color.BLACK);
        }
        canvas.drawText(titles.get(0), x1, y1 - fontHeight / 5, textPaint);
        //绘制文字2
        float x2 = (float) (centerX + radius * Math.sin(angle));
        float y2 = (float) (centerY - radius * Math.cos(angle));
        float dis = textPaint.measureText(titles.get(1));//标题一半的宽度
        if (clickTextPosition == 2) {
            textPaint.setColor(Color.RED);
        } else {
            textPaint.setColor(Color.BLACK);
        }
        canvas.drawText(titles.get(1), x2 + dis, y2 + fontHeight / 5, textPaint);
        //绘制文字3
        float x3 = (float) (centerX + radius * Math.sin(angle / 2));
        float y3 = (float) (centerY + radius * Math.cos(angle / 2));
        if (clickTextPosition == 3) {
            textPaint.setColor(Color.RED);
        } else {
            textPaint.setColor(Color.BLACK);
        }
        canvas.drawText(titles.get(2), x3, y3 + fontHeight, textPaint);
        //绘制文字4
        float x4 = (float) (centerX - radius * Math.sin(angle / 2));
        float y4 = (float) (centerY + radius * Math.cos(angle / 2));
        if (clickTextPosition == 4) {
            textPaint.setColor(Color.RED);
        } else {
            textPaint.setColor(Color.BLACK);
        }
        canvas.drawText(titles.get(3), x4, y4 + fontHeight, textPaint);
        //绘制文字5
        float x5 = (float) (centerX - radius * Math.sin(angle));
        float y5 = (float) (centerY - radius * Math.cos(angle));
        float dis5 = textPaint.measureText(titles.get(1));//标题的宽度
        if (clickTextPosition == 5) {
            textPaint.setColor(Color.RED);
        } else {
            textPaint.setColor(Color.BLACK);
        }
        canvas.drawText(titles.get(4), x5 - dis5, y5 - fontHeight / 5, textPaint);

        if (!hasAdd) {
            TouchArea touchArea1 = new TouchArea();
            touchArea1.setL(x1 - radius / 3);
            touchArea1.setR(x1 + radius / 3);
            touchArea1.setT(y1 - radius / 3);
            touchArea1.setB(y1 + radius / 3);
            touchAreaList.add(touchArea1);

            TouchArea touchArea2 = new TouchArea();
            touchArea2.setL(x2 - radius / 3);
            touchArea2.setR(x2 + radius / 3);
            touchArea2.setT(y2 - radius / 3);
            touchArea2.setB(y2 + radius / 3);
            touchAreaList.add(touchArea2);

            TouchArea touchArea3 = new TouchArea();
            touchArea3.setL(x3 - radius / 3);
            touchArea3.setR(x3 + radius / 3);
            touchArea3.setT(y3 - radius / 3);
            touchArea3.setB(y3 + radius / 3);
            touchAreaList.add(touchArea3);

            TouchArea touchArea4 = new TouchArea();
            touchArea4.setL(x4 - radius / 3);
            touchArea4.setR(x4 + radius / 3);
            touchArea4.setT(y4 - radius / 3);
            touchArea4.setB(y4 + radius / 3);
            touchAreaList.add(touchArea4);

            TouchArea touchArea5 = new TouchArea();
            touchArea5.setL(x5 - radius / 3);
            touchArea5.setR(x5 + radius / 3);
            touchArea5.setT(y5 - radius / 3);
            touchArea5.setB(y5 + radius / 3);
            touchAreaList.add(touchArea5);

            hasAdd = true;
        }

    }

    /**
     * 绘制覆盖区域
     */
    private void drawRegion(Canvas canvas) {
        valuePaint.setAlpha(255);
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
        canvas.drawCircle(x1, y1, valueRadius, valuePaint);
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
        canvas.drawCircle(x2, y2, valueRadius, valuePaint);
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
        canvas.drawCircle(x3, y3, valueRadius, valuePaint);
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
        canvas.drawCircle(x4, y4, valueRadius, valuePaint);
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
        canvas.drawCircle(x5, y5, valueRadius, valuePaint);

        path.close();
        valuePaint.setStyle(Paint.Style.STROKE);
        //绘制覆盖区域外的连线
        canvas.drawPath(path, valuePaint);
        //填充覆盖区域
        valuePaint.setAlpha(128);
        valuePaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, valuePaint);
    }


    //设置蜘蛛网颜色
    public void setMainPaint(Paint mainPaint) {
        this.mainPaint = mainPaint;
        postInvalidate();
    }


    //设置覆盖局域颜色
    public void setValuePaint(Paint valuePaint) {
        this.valuePaint = valuePaint;
        postInvalidate();
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

    //设置满分分数，默认是100分满分
    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    /*
     * 获取点击区域相关
     *
     * */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                int x = (int) event.getX();
                int y = (int) event.getY();
                for (int i = 0; i < touchAreaList.size(); i++) {
                    if (x < touchAreaList.get(i).getR() && touchAreaList.get(i).getL() < x && y < touchAreaList.get(i).getB() && touchAreaList.get(i).getT() < y) {
                        Log.d("qclqcl", "点击了 " + titles.get(i));
                        clickTextPosition = i + 1;
                        postInvalidate();//重新绘制
                    }
                }
                break;
            default:
                break;
        }
        return true;
    }

    //用来存放点击区域
    class TouchArea {
        float l;
        float t;
        float r;
        float b;

        public float getL() {
            return l;
        }

        public void setL(float l) {
            this.l = l;
        }

        public float getT() {
            return t;
        }

        public void setT(float t) {
            this.t = t;
        }

        public float getR() {
            return r;
        }

        public void setR(float r) {
            this.r = r;
        }

        public float getB() {
            return b;
        }

        public void setB(float b) {
            this.b = b;
        }
    }
}