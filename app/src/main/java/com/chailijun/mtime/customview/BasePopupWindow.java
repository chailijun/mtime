package com.chailijun.mtime.customview;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;


public class BasePopupWindow extends PopupWindow{

    private Context mContext;
    private float mShowAlpha = 0.88f;

    public BasePopupWindow(Context context) {
        super(context);
        this.mContext = context;

        initBasePopupWindow();
    }

    public BasePopupWindow(View contentView, int width, int height, Context context) {
        super(contentView, width, height);
        this.mContext = context;

        initBasePopupWindow();
    }

    /**
     * 初始化BasePopupWindow的一些信息
     */
    private void initBasePopupWindow() {

        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);  //默认设置outside点击无响应
        setFocusable(true);

    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        showAnimator().start();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        dismissAnimator().start();
    }

    private ValueAnimator showAnimator() {
        ValueAnimator animator = ValueAnimator.ofFloat(1.0f, mShowAlpha);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                setWindowBackgroundAlpha(alpha);
            }
        });
        animator.setDuration(360);
        return animator;

//        float curTranslationX = textview.getTranslationX();
//        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "translationX", -500f, 0);
//        animator.setDuration(5000);
//
//        return animator;
    }

    private ValueAnimator dismissAnimator() {
        ValueAnimator animator = ValueAnimator.ofFloat(mShowAlpha, 1.0f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                setWindowBackgroundAlpha(alpha);
            }
        });
        animator.setDuration(320);
        return animator;
    }

    /**
     * 控制窗口背景的不透明度
     * */
    private void setWindowBackgroundAlpha(float alpha) {
        Window window = ((Activity)getContext()).getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.alpha = alpha;
        window.setAttributes(layoutParams);
    }
}
