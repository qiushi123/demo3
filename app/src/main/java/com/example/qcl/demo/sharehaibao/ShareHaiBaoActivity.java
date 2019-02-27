package com.example.qcl.demo.sharehaibao;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.qcl.demo.R;

/*
 * 生成海报并保存在本地
 * */
public class ShareHaiBaoActivity extends AppCompatActivity {

    private LinearLayout rootView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_hai_bao);
        rootView = findViewById(R.id.root_view);
        imageView = findViewById(R.id.img_poster);
        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPoster();

            }
        });
    }

    private void createPoster() {
        int width = rootView.getMeasuredWidth();
        int height = rootView.getMeasuredHeight();
        /*
         * Config.RGB_565:每个像素2字节（byte）
         * ARGB_4444：2字节（已过时）
         * ARGB_8888:4字节
         * RGBA_F16：8字节
         * */
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        rootView.draw(canvas);

        //上面生成的bitmap就是我们所需要的海报
        imageView.setImageBitmap(bitmap);
        Log.i("qcl0227", "宽*高=" + bitmap.getWidth() * bitmap.getHeight());//宽*高=486720
        Log.i("qcl0227", "bitmap大小=" + bitmap.getByteCount());//bitmap大小=973440
    }
}
