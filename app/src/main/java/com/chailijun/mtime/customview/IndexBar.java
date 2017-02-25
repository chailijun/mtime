package com.chailijun.mtime.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.chailijun.mtime.R;
import com.chailijun.mtime.adapter.decoration.ISuspensionInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IndexBar extends View{

    //索引数据源
    private List<String> mIndexDatas;
    private Paint mPaint;
    //手指按下时的背景色
    private int mPressedBackground;

    //View的宽高
    private int mWidth, mHeight;
    //每个index区域的高度
    private int mGapHeight;
    private int indexTextColor;

    //以下边变量是外部set进来的
    private TextView mPressedShowTextView;//用于特写显示正在被触摸的index值
    private List<? extends ISuspensionInterface> mSourceDatas;//Adapter的数据源
    private LinearLayoutManager mLayoutManager;

    //#在最后面（默认的数据源）
    public static String[] INDEX_STRING = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};

    public IndexBar(Context context) {
        this(context,null);
    }

    public IndexBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public IndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }


    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        int textSize = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics());//默认的TextSize
        mPressedBackground = Color.BLACK;//默认按下是纯黑色
        indexTextColor = Color.BLACK;

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.IndexBar, defStyleAttr, 0);
        textSize = typedArray.getDimensionPixelSize(R.styleable.IndexBar_indexBarTextSize,textSize);
        mPressedBackground = typedArray.getColor(R.styleable.IndexBar_indexBarPressBackground,mPressedBackground);
        indexTextColor = typedArray.getColor(R.styleable.IndexBar_indexTextColor,indexTextColor);
        typedArray.recycle();

//        mIndexDatas = Arrays.asList(INDEX_STRING);
//        initIndexDatas();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(textSize);
        mPaint.setColor(indexTextColor);

        //设置index触摸监听器
        setOnIndexPressedListener(new OnIndexPressedListener() {
            @Override
            public void onIndexPressed(int index, String text) {
                if (mPressedShowTextView != null) { //显示hintTexView
                    mPressedShowTextView.setVisibility(View.VISIBLE);
                    mPressedShowTextView.setText(text);
                }
                //滚动RecyclerView
                if (mLayoutManager != null){
                    int position = getPosByTag(text);
                    if (position != -1){
                        mLayoutManager.scrollToPositionWithOffset(position, 0);
                    }
                }

            }

            @Override
            public void onMotionEventEnd() {
                //隐藏hintTextView
                if (mPressedShowTextView != null) {
                    mPressedShowTextView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initIndexDatas() {
        mIndexDatas = new ArrayList<>();
        for (int i = 0; i < mSourceDatas.size(); i++) {
            //取SuspensionTag中的第一个字符作为索引字符
            String tag = mSourceDatas.get(i).getSuspensionTag().substring(0, 1).toUpperCase();
            boolean isAdd = false;
            for (int j = 0; j < mIndexDatas.size(); j++) {
                if (tag.equalsIgnoreCase(mIndexDatas.get(j))){
                    isAdd = true;
                    break;
                }
            }
            if (!isAdd){
                mIndexDatas.add(tag);
            }
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        if (null == mIndexDatas || mIndexDatas.isEmpty()) {
            return;
        }
        computeGapHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int t = getPaddingTop();//top的基准点(支持padding)
        String index;//每个要绘制的index内容
        for (int i = 0; i < mIndexDatas.size(); i++) {
            index = mIndexDatas.get(i);
            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();//获得画笔的FontMetrics，用来计算baseLine。因为drawText的y坐标，代表的是绘制的文字的baseLine的位置
            int baseline = (int) ((mGapHeight - fontMetrics.bottom - fontMetrics.top) / 2);//计算出在每格index区域，竖直居中的baseLine值
            canvas.drawText(index, mWidth / 2 - mPaint.measureText(index) / 2, t + mGapHeight * i + baseline, mPaint);//调用drawText，居中显示绘制index
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                setBackgroundColor(mPressedBackground);//手指按下时背景变色
                //注意这里没有break，因为down时，也要计算落点 回调监听器
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                int pressI = (int) ((y - getPaddingTop()) / mGapHeight);
                //边界处理（在手指move时，有可能已经移出边界，防止越界）
                if (pressI < 0) {
                    pressI = 0;
                } else if (pressI >= mIndexDatas.size()) {
                    pressI = mIndexDatas.size() - 1;
                }
                //回调监听器
                if (null != mOnIndexPressedListener && pressI > -1 && pressI < mIndexDatas.size()) {
                    mOnIndexPressedListener.onIndexPressed(pressI, mIndexDatas.get(pressI));
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                setBackgroundResource(android.R.color.transparent);//手指抬起时背景恢复透明
                //回调监听器
                if (null != mOnIndexPressedListener) {
                    mOnIndexPressedListener.onMotionEventEnd();
                }
                break;
        }
        return true;
    }

    private OnIndexPressedListener mOnIndexPressedListener;
    /**
     * 当前被按下的index的监听器
     */
    public interface OnIndexPressedListener {
        void onIndexPressed(int index, String text);//当某个Index被按下

        void onMotionEventEnd();//当触摸事件结束（UP CANCEL）
    }

    public void setOnIndexPressedListener(OnIndexPressedListener mOnIndexPressedListener) {
        this.mOnIndexPressedListener = mOnIndexPressedListener;
    }

    /**
     * 显示当前被按下的index的TextView
     *
     * @return
     */

    public IndexBar setPressedShowTextView(TextView mPressedShowTextView) {
        this.mPressedShowTextView = mPressedShowTextView;
        return this;
    }

    /**
     * 滚动RecyclerView时通过其LayoutManager实现
     * @param mLayoutManager
     * @return
     */
    public IndexBar setLayoutManager(LinearLayoutManager mLayoutManager) {
        this.mLayoutManager = mLayoutManager;
        return this;
    }

    /**
     *
     * @param mSourceDatas 需为有序列表，若为无需则需先排序再传入
     * @return
     */
    public IndexBar setSourceDatas(List<? extends ISuspensionInterface> mSourceDatas) {
        this.mSourceDatas = mSourceDatas;
        initIndexDatas();
        invalidate();
        return this;
    }

    /**
     * 计算每个index的高
     * 以下情况调用：
     * 1 在数据源改变
     * 2 控件size改变时
     */
    private void computeGapHeight() {
        mGapHeight = (mHeight - getPaddingTop() - getPaddingBottom()) / mIndexDatas.size();
    }


    /**
     * 根据传入的tag返回position
     * @param tag
     * @return
     */
    private int getPosByTag(String tag) {
        if (null == mSourceDatas || mSourceDatas.isEmpty()) {
            return -1;
        }
        if (TextUtils.isEmpty(tag)) {
            return -1;
        }
        for (int i = 0; i < mSourceDatas.size(); i++) {
            if (tag.equalsIgnoreCase(mSourceDatas.get(i).getSuspensionTag().substring(0,1))) {
                return i;
            }
        }
        return -1;
    }

}
