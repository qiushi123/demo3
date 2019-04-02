package com.example.qcl.demo.xuexi.span;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.example.qcl.demo.R;

/**
 * 图文混排控件
 * 搜索关键字高亮显示
 * textview显示h5控件
 */
public class ImageTextActivity extends Activity {

    private TextView tv;
    private TextView tv_html;
    private TextView tv_gaoliang;//高亮显示

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_text);
        tv = findViewById(R.id.tv_img);
        tv_html = findViewById(R.id.tv_html);
        tv_gaoliang = findViewById(R.id.tv_gaoliang);

        showTvHtml();

        showGaoLiang();
        //图文混排
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

    private void showGaoLiang() {
        SpannableString spannableString = KeywordUtil.
                matcherSearchTitle(0xffFF6565, "我是通过SpannableString高亮显示的文字", "高亮");
        tv_gaoliang.setText(spannableString);
    }

    private void showTvHtml() {
        //        String sText = "我是通过html显示的<font color='red' size='24'>高亮</font>的文字";
        //        String sText = "怀孕时<font color='#3ACDDE' size='24'>宝宝</font>辅食制作";
//        String sText = "怀孕时<span style='color:#3ACDDE'>宝宝</span>辅食制作";
        String sText = "怀孕时的<span style='color:#3ACDDE;'>宝宝</span>辅食制作";
        //        String sText = "<font color='#3ACDDE' size='24'>宝宝</font color='#3ACDDE' size='24'>辅食制作";
        tv_html.setText(Html.fromHtml(sText));
    }

}
