package com.chailijun.mtime.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ViewSwitcher;


public class MyViewSwitcher extends ViewSwitcher {

    /**
     * 真正的头布局
     */
    private View firstChildView;
    /**
     * 高度为0的空布局
     */
    private View secondChildView;

    private static final int DELTA = 400;//根据具体情况设置值的大小

    /**
     * y方向偏移量总和
     */
    private int DY = 0;

    public MyViewSwitcher(Context context) {
        this(context, null);
    }

    public MyViewSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        firstChildView = getChildAt(0);
        secondChildView = getChildAt(1);
    }

    /**
     * 根据外部传入的y值来设置显示哪个子View
     *
     * @param delta
     */
    public void setMyVisibility(int delta) {
        DY += delta;

        //向上滑动
        //当y偏移量总和大于DELTA，且第一个子View显示时，设置第二个子View显示
        if (DY > DELTA && firstChildView.getVisibility() == VISIBLE) {
            DY = 0;
            setDisplayedChild(1);
        } else if (DY > 0 && secondChildView.getVisibility() == VISIBLE) {
            //继续向上滑动，且第二个子View一直处于显示状态，则将偏移量总和置零
            DY = 0;
        }

        //向下滑动
        //当y偏移量总和大于DELTA（即小于[-DELTA]），且第二个子View显示时，设置第一个子View显示
        if (DY < -DELTA && secondChildView.getVisibility() == VISIBLE) {
            DY = 0;
            setDisplayedChild(0);
        } else if (DY < 0 && firstChildView.getVisibility() == VISIBLE) {
            //继续向下滑动，且第一个子View一直处于显示状态，则将偏移量总和置零
            DY = 0;
        }
    }
}
