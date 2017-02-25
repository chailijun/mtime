package com.chailijun.mtime.customview;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.chailijun.mtime.R;

/**
 * 逐帧动画的ImageView
 */
public class DrawableImageView extends ImageView{
    public DrawableImageView(Context context) {
        this(context,null);
    }

    public DrawableImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DrawableImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //播放逐帧动画
        setImageResource(R.drawable.loading_anim);
        AnimationDrawable animationDrawable = (AnimationDrawable) getDrawable();
        animationDrawable.start();
    }


}
