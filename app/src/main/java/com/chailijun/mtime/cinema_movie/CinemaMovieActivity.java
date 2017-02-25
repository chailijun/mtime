package com.chailijun.mtime.cinema_movie;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.chailijun.mtime.R;
import com.chailijun.mtime.base.BaseActivity;
import com.chailijun.mtime.utils.Constants;

/**
 * 影院放映的所有电影
 */
public class CinemaMovieActivity extends BaseActivity {

    private int cinemaId;

    @Override
    public void initParms(Bundle parms) {
        Intent intent = getIntent();
        cinemaId = intent.getIntExtra(Constants.CINEMA_ID,0);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_cinema_movie;
    }

    @Override
    public void initView(View view) {

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
