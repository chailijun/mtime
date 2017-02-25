package com.chailijun.mtime.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.chailijun.mtime.R;
import com.chailijun.mtime.utils.DensityUtil;

public class SwitchButton extends View{

    public static final int STATUS_LEFT = 1;
    public static final int STATUS_RIGHT = 2;

    private Context mContext;
    private String textLeft = "LEFT";
    private String textRight = "RIGHT";
    private float textSize;
    private Bitmap drawableSwitch;
    private TextPaint textPaint;

    private float width;
    private float height;

    private OnSelectLisenter lisenter;
    private int textLeftColor;
    private int textRightColor;

    /**switch图标的位置*/
    private float left;
    private float top;
    private float right;
    private float bottom;
    /**switch图标与背景之间的间隙*/
    private float gap;

    private float textLeftX;
    private float textLeftY;
    private float textRightX;
    private float textRightY;

    private int status = STATUS_LEFT;

    public SwitchButton(Context context) {
        this(context,null);
    }

    public SwitchButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SwitchButton);

        textLeft = ta.getString(R.styleable.SwitchButton_textLeft);
        textRight = ta.getString(R.styleable.SwitchButton_textRight);

        textSize = ta.getDimensionPixelSize(
                R.styleable.SwitchButton_textSize, DensityUtil.dp2px(14.0f));
        int resourceId = ta.getResourceId(
                R.styleable.SwitchButton_drawable_switch, R.drawable.bk_titlebar_payticket_item);

        drawableSwitch = BitmapFactory.decodeResource(context.getResources(), resourceId);

        ta.recycle();

        initPaint();

    }

    private void initPaint() {
        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(textSize);

        gap = DensityUtil.dp2px(1.0f);
        textLeftColor = Color.WHITE;
        textRightColor = Color.GRAY;
        setTextPosition();
    }

    /**
     * 获取当前选中项
     * @return
     */
    public int getStatus() {
        return status;
    }

    public void setSelectLisenter(OnSelectLisenter lisenter) {
        this.lisenter = lisenter;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //默认switch图标显示在左边
        if (left==0&&top==0&&right==0&&bottom==0){
            left = gap;
            top = gap;
            right = width/2;
            bottom = height-gap;
        }

        setTextPosition();

        RectF dst = new RectF(left,top,right,bottom);
        canvas.drawBitmap(drawableSwitch,null,dst,null);
        textPaint.setColor(textLeftColor);
        canvas.drawText(textLeft,textLeftX,textLeftY,textPaint);
        textPaint.setColor(textRightColor);
        canvas.drawText(textRight,textRightX,textRightY,textPaint);
    }

    private void setTextPosition() {
        Rect rect = new Rect();
        textPaint.getTextBounds(textLeft,0,textLeft.length(),rect);
        int w = rect.width();//文本的宽度
        int h = rect.height();//文本的高度
        if (w < width/2 && h < height){
            textLeftX = (width-2*w)/4;
            textLeftY = (height+h)/2;
        }

        Rect rect2 = new Rect();
        textPaint.getTextBounds(textRight,0,textRight.length(),rect2);
        int w2 = rect2.width();//文本的宽度
        int h2 = rect2.height();//文本的高度
        if (w2 < width/2 && h2 < height){
            textRightX = (3*width-2*w2)/4;
            textRightY = (height+h2)/2;
        }
    }

    float startX;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (status != STATUS_LEFT && startX > 0 && startX < width/2){
                    //左边
                    status = STATUS_LEFT;

                    left = gap;
                    top = gap;
                    right = width/2;
                    bottom = height-gap;

                    if (lisenter != null){
                        lisenter.onSelectLeft();
                    }
                    textLeftColor = Color.WHITE;
                    textRightColor = Color.GRAY;
                    invalidate();
                }else if(status != STATUS_RIGHT && startX >= width/2 && startX < width ){
                    //右边
                    status = STATUS_RIGHT;

                    left = width/2+gap;
                    top = gap;
                    right = width-gap;
                    bottom = height-gap;

                    if (lisenter != null){
                        lisenter.onSelectRight();
                    }
                    textLeftColor = Color.GRAY;
                    textRightColor = Color.WHITE;
                    invalidate();
                }
                break;
            default:
                break;
        }
        return true;
    }

    public interface OnSelectLisenter{
        void onSelectLeft();
        void onSelectRight();
    }
}
