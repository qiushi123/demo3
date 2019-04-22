package com.example.qcl.demo.xuexi.rxjava;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.qcl.demo.R;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 学习rxjava2
 * <p>
 * 一，线程切换相关
 * Schedulers.newThread(): 总是启用新线程，并在新线程执行操作
 * 二，Disposable
 * 用来切断被观察者和观察者之间的连接
 * 三，背压（异步，不同线程下才会有背压问题）
 * Backpressure(背压)。所谓背压，即生产者的速度大于消费者的速度带来的问题。
 * Flowable是一个被观察者，与Subscriber(观察者)配合使用，解决Backpressure问题。
 */
public class RxActivity extends RxAppCompatActivity {

    private TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_rx);
        textview = findViewById(R.id.textview);
        List<Integer> listInt = new ArrayList<>();
        listInt.add(1);
        listInt.add(2);
        listInt.add(3);
        List<String> listStr = new ArrayList<>();
        listStr.add("a");
        listStr.add("b");
        listStr.add("c");

        //=======================简单使用=====================
        //        initData();
        //        demo1();
        //        demo2();
        //         take(listStr);
        //        schedulers();


        //=======================中级使用=====================
        //        map();
        //        flatMap();
        //        concatMap();
        //        filter(listInt);


        //=======================高级使用=====================
        zip();
        //        backpressure();//背压
    }


    //=======================简单使用=====================
    //简单的使用
    private void demo1() {
        Observable observable = null;
        //1,正常创建observable的方式
        //        observable = Observable.create((ObservableOnSubscribe<String>) e -> {
        //            e.onNext("我是obserable发射的数据");
        //        });

        //2，just快速创建
        //        observable = Observable.just("我是obserable发射的数据");


        //3,range传递整数序列,从0开始递增，发送20个整数
        observable = Observable.range(0, 20);

        //4，interval定时发送,延时10秒执行，然后每隔2秒执行一次
        //        observable = Observable.interval(10, 2, TimeUnit.SECONDS);


        observable.subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long s) {
                Log.i("qcl0419", "" + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    //rx2的基本使用
    private void demo2() {
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


    //=======================中级使用=====================

    /**
     * 线程切换相关
     * subscribeOn：指定被观察者所在线程。
     * observeOn：指定观察者所在线程
     */
    private void schedulers() {
        Log.i("qcl0419", "线程：" + Thread.currentThread().getName());
        Disposable subscribe = Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        Log.i("qcl0419", "被观察者运行的线程：" + Thread.currentThread().getName());
                        e.onNext(1);
                    }
                })
                .subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())//这里如果不指定就和被观察者一个线程
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i("qcl0419", "观察者运行的线程：" + Thread.currentThread().getName());
                    }
                });
    }

    //take:只拿前几个，takeLast：只拿最后几个
    private void take(List<String> list) {

        Observable.just(list)
                .flatMap(new Function<List<String>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(List<String> stringList) throws Exception {
                        Log.i("qcl0419", "flatMap:" + stringList.toString());
                        return Observable.fromIterable(stringList);
                    }
                })
                .takeLast(1)
                .subscribe(stringList -> Log.i("qcl0419", stringList.toString()))
                .dispose();


        //        Observable.just(1, 2, 3)
        //                .takeLast(1)//只拿最后1个
        //                //.take(2)//只拿前2个
        //                .subscribe(new Consumer<Integer>() {
        //                    @Override
        //                    public void accept(Integer integer) throws Exception {
        //                        Log.i("qcl0419", "" + integer);
        //                    }
        //                })
        //                .dispose();
    }

    //filter:筛选数据
    private void filter(List<Integer> list) {

        Observable.just(list)
                //map，flatmap数据转换
                .flatMap((Function<List<Integer>, ObservableSource<Integer>>) integers -> {
                    integers.add(4);
                    integers.add(5);
                    integers.add(6);
                    return Observable.fromIterable(integers);
                })
                //filter 数据过滤
                .filter(integer -> {
                    Log.i("qcl0419", "filter接受到的：" + integer);
                    if (integer == 3 || integer == 6) {
                        return true;
                    }
                    return false;
                })
                .map(integer -> "包含能被3整除的：" + integer)
                .subscribe(result -> {
                    Log.i("qcl0419", result);
                }).dispose();//执行完要结束
    }

    /**
     * map操作符---数据转换
     * 通过Map, 可以将上游发来的事件转换为任意的类型, 可以是一个Object, 也可以是一个集合
     */
    private void map() {
        Disposable disposable = Observable.just(1, 2, 3).map(integer -> {
            if (integer == 1) {
                return "一";
            } else if (integer == 2) {
                return "二";
            } else if (integer == 3) {
                return "三";
            }
            return null;
        }).subscribe(s -> {
            Log.i("qcl0419", s);
        });
    }

    /*
     *flatMap操作符的作用就是把一个Observable转换为另一个Observable
     * 这里需要注意的是, flatMap并不保证事件的顺序
     * */
    private void flatMap() {
        Disposable disposable = Observable
                .create((ObservableOnSubscribe<Integer>) emitter -> {
                    emitter.onNext(1);
                    emitter.onNext(2);
                    emitter.onNext(3);
                }).flatMap((Function<Integer, ObservableSource<String>>) integer -> {
                    final List<String> list = new ArrayList<>();
                    for (int i = 0; i < 3; i++) {
                        list.add("flatMap----第" + integer + "次：" + integer);
                    }
                    return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS);
                }).subscribe(s -> Log.i("qcl0422", s));
    }

    /*
     * concatMap:它和flatMap的作用几乎一模一样, 只是它的结果是严格按照上游发送的顺序来发送的
     * */
    private void concatMap() {
        Disposable disposable = Observable
                .create((ObservableOnSubscribe<Integer>) emitter -> {
                    emitter.onNext(1);
                    emitter.onNext(2);
                    emitter.onNext(3);
                }).concatMap((Function<Integer, ObservableSource<String>>) integer -> {
                    final List<String> list = new ArrayList<>();
                    for (int i = 0; i < 3; i++) {
                        list.add("concatMap***第" + integer + "次：" + integer);
                    }
                    return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS);
                }).subscribe(s -> Log.i("qcl0422", s));
    }


    //=======================高级使用=====================


    /*
     * Zip通过一个函数将多个Observable发送的事件结合到一起，然后发送这些组合到一起的事件.
     * 它按照严格的顺序应用这个函数.
     * 它只发射与发射数据项最少的那个Observable一样多的数据。
     *
     * */
    private void zip() {
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
                emitter.onComplete();
            }
        });

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("A");
                emitter.onNext("B");
                emitter.onNext("C");
                emitter.onComplete();
            }
        });

        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("qcl0422", "onSubscribe");
            }

            @Override
            public void onNext(String value) {
                Log.d("qcl0422", "onNext: " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("qcl0422", "onError");
            }

            @Override
            public void onComplete() {
                Log.d("qcl0422", "onComplete");
            }
        });
    }


    //背压的使用
    Subscription mSubscription = null;

    private void backpressure() {
        Flowable
                .create(new FlowableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                        for (int i = 0; i < 1238; i++) {
                            Thread.sleep(10);
                            e.onNext(i);
                        }
                    }
                }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                //                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        mSubscription = s;
                        mSubscription.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i("qcl0419", "" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.i("qcl0419", "onError:", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.i("qcl0419", "onComplete");
                    }
                });
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
