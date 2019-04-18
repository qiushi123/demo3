package com.example.qcl.demo.xuexi.rxjava;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.qcl.demo.R;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivityRX extends RxAppCompatActivity {

    private TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_rx);
        textview = findViewById(R.id.textview);
        initData();
        //        demo1();
        demo2();
        zip();
    }

    //现成切换
    private void demo2() {
        Observable.create((e) -> {  //此处使用了lambda表达式
            e.onNext(999);
            e.onNext(9996);
            e.onNext(999666);
            //subscribeOn 用于指定 subscribe() 时所发生的线程
        }).subscribeOn(Schedulers.newThread()) //总是启用新线程，并在新线程执行操作。
                .subscribeOn(Schedulers.io()) //（注意，本次指定io线程无效）指定 subscribe() 事件发送发生在 IO 线程；Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作；
                .observeOn(AndroidSchedulers.mainThread()) //指定下游Subscriber 的回调处理发生在主线程, 指定的操作将在 Android 主线程运行
                .doOnNext((Consumer) integer -> { //接收
                    textview.setText(integer + "--main--" + Thread.currentThread().getName());
                    Log.i("qcl0419", (Integer.valueOf(integer.toString())) + "----" + Thread.currentThread().getName());
                    Schedulers.io().createWorker().schedule(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                })
                .observeOn(Schedulers.io()) //指定io流线程
                .subscribe((Consumer<Integer>) integer -> {
                    //Consumer 即消费者，用于接收单个值，BiConsumer 则是接收两个值，
                    // Function 用于变换对象，Predicate 用于判断
                    textview.setText(integer + "--io--" + Thread.currentThread().getName());
                    Log.i("qcl0419", integer + "*****" + Thread.currentThread().getName());
                });
    }

    //rx2的基本使用
    private void demo1() {
        Observable.create(new ObservableOnSubscribe<String>() { //Observable.create 是创建被观察者，
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("123");  //ObservableEmitter 是发射器，向下游发射数据
                e.onNext("456");
                e.onNext("789");
            }
        }).subscribe(new Observer<String>() { //subscribe 订阅观察者，说法有点别扭，所以，现在统称接收器
            private Disposable disposable;

            /**
             * 用于解除订阅
             * @param d
             */
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(@NonNull String string) {
                Log.i("qcl0419", string);
                textview.setText(string);
                if (string.equals("456")) {
                    disposable.dispose(); //当上游条件满足某个条件的时候，解除订阅关系
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
            }

            //注意了，onError 和 onComplete 方法是只会有一个执行，不管是哪个方法执行了，当前的订阅关系都解除终止了
            @Override
            public void onComplete() {
                Log.i("qcl0419", "完成");
            }
        });
    }

    //rxjava2的zip学习
    private void zip() {

    }

    private void initData() {
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<Long>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {
                        Log.i("接收数据", String.valueOf(aLong));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
