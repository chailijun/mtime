package com.chailijun.mtime.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chailijun.mtime.R;

public class ToolsBar extends RelativeLayout implements View.OnClickListener {

    private View view;
    private TextView mTitle;
//    private TextView mTitleEn;

    //返回
    private ImageView back;
    //分享
    private ImageView share;
    //收藏
    private ImageView favorite;
    //背景
    private LinearLayout titleBG;

    private Context mContext;

    private ClickLisenter lisenter;

    public ToolsBar(Context context) {
        this(context,null);
    }

    public ToolsBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ToolsBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);
    }

    private void initView(Context context) {
        mContext = context;

        view = LayoutInflater.from(context).inflate(R.layout.head_with_favorite_share, null);
        mTitle = (TextView) view.findViewById(R.id.head_title);
//        mTitleEn = (TextView) view.findViewById(R.id.head_title_en);
        back = (ImageView) view.findViewById(R.id.back);
        share = (ImageView) view.findViewById(R.id.share);
        favorite = (ImageView) view.findViewById(R.id.favorite);
        titleBG = (LinearLayout) findViewById(R.id.head_title_bg);

        back.setOnClickListener(this);
    }

    public void setTitle(String title){
        mTitle.setText(title);
    }

    public void setLisenter(ClickLisenter lisenter) {
        this.lisenter = lisenter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                if (lisenter != null){
                    lisenter.back();
                }
                break;
            case R.id.share:
                if (lisenter != null){
                    lisenter.share();
                }
                break;
            case R.id.favorite:
                if (lisenter != null){
                    lisenter.favorite();
                }
                break;
            default:
                break;
        }
    }

    interface ClickLisenter{

        void back();
        void share();
        void favorite();
    }
}
