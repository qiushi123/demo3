package com.example.qcl.demo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by qcl on 2017/12/5.
 */

public class AnimationView extends View {
    public AnimationView(Context context) {
        super(context);
    }

    public AnimationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void bigAnimation() {
        //        this.animate().scaleX(3f);
        //        this.animate().scaleY(3f);
        this.animate()
                .scaleX(2)
                .scaleY(3)
                .scaleX(5)
                .scaleY(5);

    }

    public void smallAnimation() {
        this.animate().scaleX(1f);
        this.animate().scaleY(1f);
    }


}
