package com.chailijun.mtime.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.chailijun.mtime.R;

/**
 * 指示器（结合ViewPager一起使用）
 */
public class Indicator extends View{
    /**未被选中时颜色*/
    private int unSelectColor;
    /**选中时颜色*/
    private int onSelectColor;
    /**
     * 控件的宽度
     */
    private int width;

    /**
     * 控件的高度
     */
    private int height;
    /**
     * 指示器的个数，即viewPager页数
     */
    private int indicatorCount;

    private Paint mPaint;
    private float mX;
    private float mY;
    /**
     * 移动的圆的x坐标
     */
    private float moveX;
    /**
     * 圆半径
     */
    private float mRadius;
    /**
     * 绘制的圆心间距
     */
    private float mRadiusSpace;

    public Indicator(Context context) {
        this(context,null);
    }

    public Indicator(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Indicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(context,attrs);
        init();
    }

    /**
     * 获取自定义属性
     * @param attrs
     */
    private void getAttrs(Context context,AttributeSet attrs) {

        //默认颜色
        unSelectColor = Color.GRAY;
        onSelectColor = Color.WHITE;
        //默认半径、圆心间距
        mRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,5,getResources().getDisplayMetrics());
        mRadiusSpace = 2*mRadius;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Indicator);
        for (int i = 0; i < ta.getIndexCount(); i++) {
            int index = ta.getIndex(i);

            switch (index){
                case R.styleable.Indicator_unSelectColor:
                    unSelectColor = ta.getColor(index,unSelectColor);
                    break;
                case R.styleable.Indicator_onSelectColor:
                    onSelectColor = ta.getColor(index,onSelectColor);
                    break;
                case R.styleable.Indicator_radius:
                    mRadius = ta.getDimension(index,mRadius);
                    break;
                case R.styleable.Indicator_space:
                    mRadiusSpace = ta.getDimension(index,0) + mRadiusSpace;
                    break;
                default:
                    break;
            }
        }
        ta.recycle();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(unSelectColor);//默认颜色
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (indicatorCount == 0){
            return;
        }
        mX = width - ((indicatorCount - 1) * mRadiusSpace);
        mX /= 2.0;
        mY = height-mRadius;
        mPaint.setColor(unSelectColor);
        for (int i = 0; i < indicatorCount; i++) {
            canvas.drawCircle(mX + i * mRadiusSpace, mY, mRadius, mPaint);
        }
        //绘制移动的指示器
        mPaint.setColor(onSelectColor);
        canvas.drawCircle(mX + moveX, mY, mRadius, mPaint);

    }

    /**
     * 指示器滑动
     * @param position
     * @param offset
     */
    public void scroll(int position, float offset) {
        moveX = (int) ((position + offset) * mRadiusSpace);
        invalidate();
    }

    public void setIndicatorCount(int indicatorCount) {
        this.indicatorCount = indicatorCount;
        invalidate();
    }
}
