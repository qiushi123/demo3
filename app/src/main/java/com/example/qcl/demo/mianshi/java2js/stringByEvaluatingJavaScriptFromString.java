package com.example.qcl.demo.mianshi.java2js;

import android.webkit.WebView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 2019/2/28 16:52
 * author: qcl
 * wechat:2501902696
 * 解决evaluateJavascript在安卓4.4之前没法使用的问题
 * 目前的解决方案是通过java反射机制
 * 在android.webkit包中有个BrowserFrame私有类，该类中有个Native方法：
 * public native String stringByEvaluatingJavaScriptFromString(String script)
 *
 * 使用步骤
 * 1.扩展WebView添加方法，并使用反射实现。
 * 2.将布局文件中的WebView修改为自定义的WebView
 * 3.使用新的WebView调用方法，执行js方法获取返回值
 * ---------------------
 * 作者：缘聚欣然
 * 来源：CSDN
 * 原文：https://blog.csdn.net/sy18298711239/article/details/51945478
 * 版权声明：本文为博主原创文章，转载请附上博文链接！
 */
public class stringByEvaluatingJavaScriptFromString {
    public String stringByEvaluatingJavaScriptFromString(String script) {
        try {
            //由webview取到webviewcore
            Field field_webviewcore = WebView.class.getDeclaredField("mWebViewCore");
            field_webviewcore.setAccessible(true);
            Object obj_webviewcore = field_webviewcore.get(this);
            //由webviewcore取到BrowserFrame
            Field field_BrowserFrame = obj_webviewcore.getClass().getDeclaredField("mBrowserFrame");
            field_BrowserFrame.setAccessible(true);
            Object obj_frame = field_BrowserFrame.get(obj_webviewcore);
            //获取BrowserFrame对象的stringByEvaluatingJavaScriptFromString方法
            Method method_stringByEvaluatingJavaScriptFromString = obj_frame.getClass().getMethod("stringByEvaluatingJavaScriptFromString", String.class);
            //执行stringByEvaluatingJavaScriptFromString方法
            Object obj_value = method_stringByEvaluatingJavaScriptFromString.invoke(obj_frame, script);
            //返回执行结果
            return String.valueOf(obj_value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

}
