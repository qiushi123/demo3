package com.example.qcl.demo.span;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.example.qcl.demo.R;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * 图文混排控件
 */
public class ImageTextActivity extends RxAppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_text);
        tv = findViewById(R.id.tv_img);


        SpannableString sp = new SpannableString("图文混排测排测试图文混排测试图文混排测试图文混排测试图");

        //获取一张图片
        Drawable drawable = getResources().getDrawable(R.drawable.search_column_purple);
        //        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        drawable.setBounds(0, 0, 144, 48);

        //居中对齐imageSpan
        CenterAlignImageSpan imageSpan = new CenterAlignImageSpan(drawable);
        sp.setSpan(imageSpan, 0, 1, ImageSpan.ALIGN_BASELINE);

        //普通imageSpan 做对比
        //        ImageSpan imageSpan2 = new ImageSpan(drawable);
        //        sp.setSpan(imageSpan2, 3, 4, ImageSpan.ALIGN_BASELINE);

        tv.setText(sp);

    }

}
