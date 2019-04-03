package com.example.qcl.demo.xuexi.baoguang;

import android.graphics.Rect;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.SingleRequest;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 2019/4/2 13:31
 * author: qcl
 * desc: 安卓曝光量统计工具类，适用场景如下
 * 1，LinearLayout等viewgroup
 * <p>
 * 获取view绑定的数据，有两种方法
 * 一，通过setTag，然后getTag方法获取
 * 二，获取到view以后，再去判断view类型，
 * 如果是textview就直接getText方法获取textview里的值
 * 如果是imageview的话，可以借助glide，因为glide默认在我们的imageview里使用了setTag，
 * 我们在imageview调用getTag时，获取到的是一个SingleRequest对象，而这个对象里的model存储的就是我们图片加载的url
 * 但是这个model是私有的，SingleRequest也没给我们暴漏或者这个值的方法，所以我们这里可以通过反射来获取这个值。
 * </p>
 * wechat:2501902696
 */
public class RootViewShowCountUtils<T> {


    //用于统计曝光量的map
    private ConcurrentHashMap<String, Integer> hashMap = new ConcurrentHashMap<String, Integer>();


    /*
     * 统计RecyclerView里当前屏幕可见子view的曝光量
     *
     * */
    void recordViewShowCount(ViewGroup root) {
        hashMap.clear();
        if (root == null || root.getVisibility() != View.VISIBLE) {
            return;
        }
        treeObserverLinearLayout(root);

    }

    private void treeObserverLinearLayout(final ViewGroup root) {
        ViewTreeObserver treeObserver = root.getViewTreeObserver();
        //
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            treeObserver.addOnWindowFocusChangeListener(new ViewTreeObserver.OnWindowFocusChangeListener() {
                @Override
                public void onWindowFocusChanged(boolean hasFocus) {
                    Log.i("qcl0403", "onWindowFocusChanged");
                    //只有在前台时才统计。
                    if (hasFocus) {
                        int childCount = root.getChildCount();
                        Log.i("qcl0403", "childCount" + childCount);
                        for (int i = 0; i < childCount; i++) {
                            View view = root.getChildAt(i);
                            Log.i("qcl0403", view.getClass().getName());
                            recordViewCount(view);
                        }
                    }
                }

            });
        }
    }


    //获取view绑定的数据
    private void recordViewCount(View view) {
        if (view == null || view.getVisibility() != View.VISIBLE ||
                !view.isShown() || !view.getGlobalVisibleRect(new Rect())) {
            return;
        }

        Rect rect = new Rect();
        boolean cover = view.getGlobalVisibleRect(rect);
        if (!cover) {
            return;
        }
        if (rect.height() < view.getMeasuredHeight() / 2) {
            //大于一半被覆盖,就不统计
            return;
        }
        //        if (rect.width() >= view.getMeasuredWidth() && rect.height() >= view.getMeasuredHeight()) {
        //            //没有被覆盖
        //        }

        //这里获取的是我们view绑定的数据，相应的你要去在你的view里setTag，只有set了，才能get

        String key = null;

        /*
        //1，通过tag传值
        T t = (T) view.getTag();
        if (t instanceof ItemData) {
            ItemData data = (ItemData) t;
            key = data.toString();
        } else if (t instanceof String) {
            key = (String) t;
        }
        */
        //2，直接从view拿值
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            key = textView.getText().toString();
        } else if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            SingleRequest bean = (SingleRequest) imageView.getTag();
            if (!(bean instanceof SingleRequest)) {
                return;
            }
            //反射获取singlerequest的model值
            // 得到类对象
            Class userCla = (Class) bean.getClass();
            Field[] fs = userCla.getDeclaredFields();
            for (int i = 0; i < fs.length; i++) {
                Field f = fs[i];
                try {
                    String name = f.getName();
                    //SingleRequest中的model属性就是我们imageview加载的图片链接
                    if (name.toLowerCase().contains("model")) {
                        f.setAccessible(true); // 设置些属性是可以访问的
                    }
                    Object val = f.get(bean); // 得到model属性的值
                    //Log.i("qcl0403", "url:" + val.toString());
                    key = val.toString();
                    break;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }

        if (TextUtils.isEmpty(key)) {
            return;
        }
        hashMap.put(key, !hashMap.containsKey(key) ? 1 : (hashMap.get(key) + 1));
        Log.i("qcl0402", key + "----曝光次数：" + hashMap.get(key));
    }


}
