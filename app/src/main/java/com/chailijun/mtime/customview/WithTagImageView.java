package com.chailijun.mtime.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.chailijun.mtime.R;
import com.chailijun.mtime.utils.DensityUtil;

public class WithTagImageView extends ImageView {

    private Context mContext;
    private int tagColor = -1;
    private String tagText = "";

    private int width;
    private int height;
    private TextPaint textPaint;

    private String rating = "";
    //电影格式，IMAX3D、IMAX2D、HOT、Filter、DMAX、3D
    private boolean isIMAX3D;
    private boolean isIMAX;
    private boolean isHot;
    private boolean isFilter;
    private boolean isDMAX;

    private boolean is3D;

    public WithTagImageView(Context context) {
        this(context,null);
    }

    public WithTagImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WithTagImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.WithTagImageView);
        tagColor = ta.getColor(R.styleable.WithTagImageView_TagColor,-1);
        tagText = ta.getString(R.styleable.WithTagImageView_TagText);
        ta.recycle();

        init();
    }

    private void init() {
        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setStyle(Paint.Style.STROKE); // 设置填充方式为描边
        textPaint.setTypeface(Typeface.DEFAULT);
        textPaint.setTextScaleX(1.0f); // 文字在水平方向的缩放比例(文字高度不会变)
        textPaint.setUnderlineText(false); // 设置下划线
        textPaint.setSubpixelText(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        height = getHeight();

        if (tagColor != -1 && !tagText.equals("")){
            Paint paint = new Paint();
            paint.setColor(tagColor);// 设置画笔颜色
            paint.setStyle(Paint.Style.FILL);// 设置填充类型
            Path path = new Path();
            path.reset();
            path.moveTo(0, 0);// 开始坐标 也就是三角形的顶点
            path.lineTo(DensityUtil.dp2px(20.0f), 0);
            path.lineTo(0, DensityUtil.dp2px(20.0f));
            path.close();
            //画左上角的倒三角形
            canvas.drawPath(path, paint);

            //画左上角的倒三角形中的文字
            textPaint.setTextSize(DensityUtil.dp2px(10.0f));// 设置字体大小
            canvas.drawText(tagText, DensityUtil.dp2px(4.0f), DensityUtil.dp2px(10.0f), textPaint);
        }
        if (!rating.equals("")&&!rating.equals("0.0")){
            try {
                float rate = Float.parseFloat(rating);
                if (rate < 0)return;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            textPaint.setTextSize(DensityUtil.dp2px(14.0f));// 设置字体大小

            Rect rect = new Rect();
            textPaint.getTextBounds(rating,0,rating.length(),rect);
            int w = rect.width();//文本的宽度
            int h = rect.height();//文本的高度

            int padding = DensityUtil.dp2px(6.0f);//文字与背景的左右内边距 = padding/2
            int padding2 = DensityUtil.dp2px(10.0f);//文字与背景的上下内边距 = padding2/2

            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(0xFF629D0A);// 设置画笔颜色
            //画右下角正方形
            RectF rectF = new RectF(width - w-padding, height - h-padding2, width, height);
            canvas.drawRect(rectF,paint);

            //画右下角的正方形中的文字
            canvas.drawText(rating,width - w-padding/2, height-padding2/2,textPaint);
        }

        drawCategory(canvas);


    }

    private void drawCategory(Canvas canvas) {
        if (isIMAX3D){
            drawCategory(canvas,"IMAX 3D",0xFF3BB0FF);
            return;
        }else if (isIMAX){
            drawCategory(canvas,"IMAX 2D",0xFF0AC3CC);
            return;
        }else if (is3D){
            drawCategory(canvas,"3D",0xFF4EA2FF);
            return;
        }else if (isHot){
            drawCategory(canvas,"HOT",0xFF0AC3CC);
            return;
        }else if (isFilter){
            drawCategory(canvas,"滤镜",0xFF0AC3CC);
            return;
        }else if (isDMAX){
            drawCategory(canvas,"DMAX",0xFF0AC3CC);
            return;
        }

    }

    private void drawCategory(Canvas canvas,String category,int bgColor) {
        textPaint.setTextSize(DensityUtil.dp2px(10.0f));// 设置字体大小
        Rect rect = new Rect();
        textPaint.getTextBounds(category,0,category.length(),rect);
        int w = rect.width();//文本的宽度
        int h = rect.height();//文本的高度

        int padding = DensityUtil.dp2px(6.0f);//文字与背景的左右内边距 = padding/2

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(bgColor);// 设置画笔颜色
        //画右上角矩形
        RectF rectF = new RectF(width - w-padding, 0, width, h+padding);
        canvas.drawRect(rectF,paint);

        //画右上角矩形中的文字
        canvas.drawText(category,width - w-padding/2, h+padding/2,textPaint);
    }

    public void setIMAX3D(boolean IMAX3D) {
        isIMAX3D = IMAX3D;
        invalidate();
    }

    public void setIs3D(boolean is3D) {
        this.is3D = is3D;
        invalidate();
    }

    public void setDMAX(boolean DMAX) {
        isDMAX = DMAX;
        invalidate();
    }

    public void setFilter(boolean filter) {
        isFilter = filter;
        invalidate();
    }

    public void setHot(boolean hot) {
        isHot = hot;
        invalidate();
    }

    public void setIMAX(boolean IMAX) {
        isIMAX = IMAX;
        invalidate();
    }

    public void setRating(String rating){
        this.rating = rating;
        invalidate();
    }
}
