package com.chailijun.mtime.moviedetail;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chailijun.mtime.R;
import com.chailijun.mtime.base.BaseActivity;
import com.chailijun.mtime.utils.ActivityUtils;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Logger;

public class MovieDetailActivity extends BaseActivity{

    private int movieId;

    private MovieDetailPresenter mPresenter;

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
        return R.layout.activity_movie_detail;
    }

    @Override
    public void initView(View view) {

        MovieDetailFragment movieDetailFragment =
                (MovieDetailFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (movieDetailFragment == null){
            Logger.e(TAG,"电影:"+movieId);
            // Create the fragment
            movieDetailFragment = MovieDetailFragment.newInstance(movieId);
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),movieDetailFragment,R.id.contentFrame);
        }

        // Create the presenter
        mPresenter = new MovieDetailPresenter(movieDetailFragment);
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
