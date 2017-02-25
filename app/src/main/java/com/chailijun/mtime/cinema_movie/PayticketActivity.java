package com.chailijun.mtime.cinema_movie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chailijun.mtime.R;
import com.chailijun.mtime.base.BaseActivity;
import com.chailijun.mtime.utils.ActivityUtils;
import com.chailijun.mtime.utils.Constants;

/**
 * 此页面显示电影对应的影院
 */
public class PayticketActivity extends BaseActivity {

    private PayticketPresenter mPresenter;

    private int movieId;

    @Override
    public void initParms(Bundle parms) {
        Intent intent = getIntent();
        movieId = intent.getIntExtra(Constants.MOVIE_ID,0);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_payticket;
    }

    @Override
    public void initView(View view) {

        PayticketFragment payticketFragment =
                (PayticketFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (payticketFragment == null){
            // Create the fragment
            payticketFragment = PayticketFragment.newInstance(movieId);
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),payticketFragment,R.id.contentFrame);

            // Create the presenter
            mPresenter = new PayticketPresenter(payticketFragment);
        }


    }

    @Override
    public void setListener() {

    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public void doBusiness(Context mContext) {

    }
}
