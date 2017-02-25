package com.chailijun.mtime.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chailijun.mtime.R;


public class ExpandableTextView extends LinearLayout {

    TextView mTextView;
    ImageView mOpenBtn;

    //允许显示最大行数
    int maxExpandLines = 0;
    //动画执行时间
    int duration = 0;

    //是否发生过文字变动
    boolean isChange = false;
    //文本框真实高度
    int realTextViewHeigt = 0;
    //默认处于收起状态
    boolean isCollapsed = true;
    //收起时候的整体高度
    int collapsedHeight = 0;
    //剩余点击按钮的高度
    int lastHeight = 0;
    //是否正在执行动画
    boolean isAnimate = false;

    OnExpandStateChangeListener listener;

    public ExpandableTextView(Context context) {
        this(context, null);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(VERTICAL);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextViewAttr);
        maxExpandLines = array.getInteger(R.styleable.ExpandableTextViewAttr_maxExpandLines, 3);
        duration = array.getInteger(R.styleable.ExpandableTextViewAttr_duration, 300);
        array.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (mTextView == null || mOpenBtn == null) {
            mTextView = (TextView) getChildAt(0);
            mOpenBtn = (ImageView) getChildAt(1);
        }

        mOpenBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpandCollapseAnimation animation;

                isCollapsed = !isCollapsed;
                if (isCollapsed) {
                    mOpenBtn.setImageResource(R.drawable.arrow_down);

                    if (listener != null) {
                        listener.onExpandStateChanged(true);
                    }
                    animation = new ExpandCollapseAnimation(getHeight(), collapsedHeight);
                } else {
                    mOpenBtn.setImageResource(R.drawable.arrow_up);
                    if (listener != null) {
                        listener.onExpandStateChanged(false);
                    }
                    animation = new ExpandCollapseAnimation(getHeight(), realTextViewHeigt + lastHeight);
                }
                animation.setFillAfter(true);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        isAnimate = true;
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        clearAnimation();
                        isAnimate = false;
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                clearAnimation();
                startAnimation(animation);
                //不带动画的处理方式
//                isChange=true;
//                requestLayout();
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //执行动画的过程中屏蔽事件
        return isAnimate;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //如果隐藏控件或者textview的值没有发生改变，那么不进行测量
        if (getVisibility() == GONE || !isChange) {
            return;
        }
        isChange = false;

        //初始化默认状态，即正常显示文本
        mOpenBtn.setVisibility(GONE);
        mTextView.setMaxLines(Integer.MAX_VALUE);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //如果本身没有达到收起展开的限定要求，则不进行处理
        if (mTextView.getLineCount() <= maxExpandLines) {
            return;
        }

        //初始化高度赋值，为后续动画事件准备数据
        realTextViewHeigt = getRealTextViewHeight(mTextView);

        //如果处于收缩状态，则设置最多显示行数
        if (isCollapsed) {
            mTextView.setLines(maxExpandLines);
        }
        mOpenBtn.setVisibility(VISIBLE);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (isCollapsed) {
            mTextView.post(new Runnable() {
                @Override
                public void run() {
                    lastHeight = getHeight() - mTextView.getHeight();
                    collapsedHeight = getMeasuredHeight();
                }
            });
        }
    }

    /**
     * 获取textview的真实高度
     *
     * @param textView
     * @return
     */
    private int getRealTextViewHeight(TextView textView) {
        //getLineTop返回值是一个根据行数而形成等差序列，如果参数为行数，则值即为文本的高度
        int textHeight = textView.getLayout().getLineTop(textView.getLineCount());
        return textHeight + textView.getCompoundPaddingBottom() + textView.getCompoundPaddingTop();
    }

    public void setText(String text) {
        isChange = true;
        mTextView.setText(text);
    }

    public void setText(String text, boolean isCollapsed) {
        this.isCollapsed = isCollapsed;
        if (isCollapsed) {
//            mOpenBtn.setText("展开");
            mOpenBtn.setImageResource(R.drawable.arrow_down);
        } else {
//            mOpenBtn.setText("收起");
            mOpenBtn.setImageResource(R.drawable.arrow_up);
        }
        clearAnimation();
        setText(text);
        getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    public void setListener(OnExpandStateChangeListener listener) {
        this.listener = listener;
    }

    private class ExpandCollapseAnimation extends Animation {
        int startValue = 0;
        int endValue = 0;

        public ExpandCollapseAnimation(int startValue, int endValue) {
            setDuration(duration);
            this.startValue = startValue;
            this.endValue = endValue;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            int height = (int) ((endValue - startValue) * interpolatedTime + startValue);
            mTextView.setMaxHeight(height - lastHeight);
            ExpandableTextView.this.getLayoutParams().height = height;
            ExpandableTextView.this.requestLayout();
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }

    public interface OnExpandStateChangeListener {
        void onExpandStateChanged(boolean isExpanded);
    }
}