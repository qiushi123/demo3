package com.example.qcl.demo.mianshi.java2js;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.qcl.demo.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Set;

/*
 * java和h5的交互
 * 1，java调用h5代码，获取h5数据
 * */
public class JavaH5Activity extends AppCompatActivity {

    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_h5);
        webview = findViewById(R.id.webview);
        String url = "file:///android_asset/h5demo2.html";
        webview.loadUrl(url);
        //1，js调用安卓
        webview.getSettings().setJavaScriptEnabled(true);//这里必须开启
        //把当前JavaH5Activity对象作为androidObject别名传递给js
        //js通过window.androidObject.androidMethod()就可以直接调用安卓的androidMethod方法
        webview.addJavascriptInterface(JavaH5Activity.this, "androidObject");

        //2，loadUrl实现安卓调用js;  3,evaluateJavascript实现安卓调用js
        getH5ValueDemo();

        //4,通过shouldOverrideUrlLoading拦截url，获取js传递给安卓的值
        lanjieUrl();
    }

    //通过shouldOverrideUrlLoading拦截url，获取js传递给安卓的值
    private void lanjieUrl() {
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i("qcl0228", "拦截到的url：" + url);
                if (url.startsWith("qiushi")) {
                    Uri uriRequest = Uri.parse(url);
                    String scheme = uriRequest.getScheme();
                    String action = uriRequest.getHost();
                    String query = uriRequest.getQuery();
                    if ("qiushi".equals(scheme)) {
                        if (!TextUtils.isEmpty(query)) {
                            //把url携带的参数存到一个map里
                            HashMap maps = new HashMap();
                            Set<String> names = uriRequest.getQueryParameterNames();
                            for (String name : names) {
                                maps.put(name, uriRequest.getQueryParameter(name));
                            }
                            JSONObject jsonObject = new JSONObject(maps);
                            if ("setH5Info".equals(action)) {
                                if (jsonObject != null && jsonObject.has("params")) {
                                    String h5InfoParams = jsonObject.optString("params");
                                    Log.i("qcl0228", "拦截到的参数：" + h5InfoParams);
                                }
                            }
                        }
                    }
                } else {
                    view.loadUrl(url);
                }
                return true;
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webview.loadUrl("javascript:chongdingxiang()");
            }
        });

    }

    //js调用安卓，必须加@JavascriptInterface注释的方法才可以被js调用
    @JavascriptInterface
    public String androidMethod() {
        Log.i("qcl0228", "js调用了安卓的方法");
        return "我是js调用安卓获取的数据";
    }


    //安卓调用h5的js方法
    private void getH5ValueDemo() {

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /*
               //2，loadUrl实现安卓调用js
                String jsonParams = "123456";
                //String url = "javascript:jsMethod()";//不拼接参数，直接调用js的jsMethod函数
                String url = "javascript:jsMethod(" + jsonParams + ")";//拼接参数，就可以把数据传递给js
                webview.loadUrl(url);
              */

                //3,evaluateJavascript实现安卓调用js
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    String jsonParams = "123456";
                    //String method = "jsMethod()";//不拼接参数，直接调用js的jsMethod函数
                    String method = "jsMethod(" + jsonParams + ")";//拼接参数，就可以把数据传递给js
                    webview.evaluateJavascript(method, new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            Log.i("qcl0228", "js返回的数据" + value);
                        }
                    });
                }
            }
        });
    }
}
