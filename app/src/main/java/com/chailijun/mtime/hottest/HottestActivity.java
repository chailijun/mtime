package com.chailijun.mtime.hottest;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chailijun.mtime.R;
import com.chailijun.mtime.base.BaseActivity;
import com.chailijun.mtime.utils.ActivityUtils;

/**
 * 时光热度榜
 */
public class HottestActivity extends BaseActivity {

    private ImageView back;

    private HottestPresenter mPresenter;

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_hottest;
    }

    @Override
    public void initView(View view) {
        back = $(R.id.back);
        //标题
        TextView head_title = $(R.id.head_title);
        head_title.setText(getString(R.string.mtime_hotTopList));


        HottestFragment hottestFragment =
                (HottestFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (hottestFragment == null){
            // Create the fragment
            hottestFragment = new HottestFragment();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),hottestFragment,R.id.contentFrame);
        }

        // Create the presenter
        mPresenter = new HottestPresenter(hottestFragment);
    }

    @Override
    public void setListener() {
        back.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {

    }
}
