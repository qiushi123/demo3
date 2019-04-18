package com.example.qcl.demo.aspect;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

//import com.hujiang.common.util.ToastUtils;
//import com.hujiang.framework.app.RunTimeManager;

/**
 * activity aspect
 *
 * @author simon
 * @version 1.0.0
 * @since 2016-03-04
 */
@Aspect
public class ActivityAspect {

    //    @Pointcut("execution(* android.app.Activity.onCreate(..))")
    @After("execution(* com.example.qcl.demo.xuexi.baoguang.BaoGuangActivity.**())")
    public void onCreateCutPoint(JoinPoint joinPoint) {

        //获取方法信息对象
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //获取当前对象
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        Log.i("helloAOP", "className:" + className + ",methodName:" + methodName);

        //获取当前对象，通过反射获取类别详细信息
        //String className2 = joinPoint.getThis().getClass().getName();
        //调用原方法的执行。

    }

    //
    //    @Pointcut("execution(* android.app.Activity.onResume())")
    //    public void onResumeCutPoint() {
    //
    //    }
    //
    @After("execution(* android.app.Activity.onPause(..))")
    //    @Pointcut("execution(* android.app.Activity.onPause(..))")
    public void onPauseCutPoint() {
        Log.i("helloAOP", "onpause执行");
    }
    //
    //    @Pointcut("execution(* android.app.Activity.onStart(..))")
    //    public void onStartCutPoint() {
    //
    //    }
    //
    //    @Pointcut("execution(* android.app.Activity.onStop(..))")
    //    public void onStopCutPoint() {
    //
    //    }
    //
    //    @Pointcut("execution(* android.app.Activity.onDestroy(..))")
    //    public void onDestroyCutPoint() {
    //
    //    }


    //    @After("execution(* android.app.Activity.on**(..))")
    //    public void onResumeMethod(JoinPoint joinPoint) throws Throwable {
    //        Log.i("helloAOP", "aspect:::" + joinPoint.getSignature());
    //    }

    //    ///////////
    //    @After("execution(* com.hujiang.library.demo.Greeter.**())")
    //    public void greeterAdvice(JoinPoint joinPoint) throws Throwable {
    //        Log.i("helloAOP", "aspect:::" + joinPoint.getSignature());
    //    }
    //
    //    @After("execution(* android.support.v4.app.Fragment.on**(..))")
    //    public void fragmentMethod(JoinPoint joinPoint) throws Throwable {
    //        Log.i("helloAOP", "aspect:::" + joinPoint.getSignature());
    //    }
    //
    //    @After("call(* com.hujiang.library.demo.AspectJavaDemo.work())")
    //    public void aspectJavaDemoAdvice(JoinPoint joinPoint) throws Throwable {
    //        Log.i("helloAOP", "aspect:::" + joinPoint.getSignature());
    //    }
    //
    //    @After("execution(* com.nostra13.universalimageloader.core.ImageLoader.displayImage(..))")
    //    public void aspectImageLoader(JoinPoint joinPoint) throws Throwable {
    //        Log.i("helloAOP", "aspect:::" + joinPoint.getSignature());
    //    }
    //
    //    @After("execution(* com.hujiang.library.demo.NormalClass.**(..))")
    //    public void aspectNormalClass(JoinPoint joinPoint) throws Throwable {
    //        Log.i("helloAOP", "aspect:::" + joinPoint.getSignature());
    //    }
    //
    //    @Around("execution( * com.hujiang.library.demo.AOPActivity.onCreate(..))")
    //    public void aopActivityAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
    //        joinPoint.proceed();
    //
    //        Log.i("helloAOP", "aspect:::" + "------------>>>>>AOPActivity.onCreate");
    //    }
}