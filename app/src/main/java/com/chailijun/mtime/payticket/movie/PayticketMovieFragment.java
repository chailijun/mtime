package com.chailijun.mtime.payticket.movie;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.chailijun.mtime.R;
import com.chailijun.mtime.adapter.MyFragmentStatePagerAdapter;
import com.chailijun.mtime.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * author： Chailijun
 * date  ： 2017/1/9 22:17
 * e-mail： 1499505466@qq.com
 */

public class PayticketMovieFragment extends BaseFragment{

    private TabLayout mTabLayout;
    private ViewPager mViewpager;

    private List<String> mTitles;
    private List<Fragment> mFragments;
    private MyFragmentStatePagerAdapter mAdapter;

    private HotPlayPresenter mHotPlayPresenter;
    private ComingMoviePresenter mComingMoviePresenter;

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_payticket_movie;
    }

    @Override
    public void initView(View view) {

        mTabLayout = $(view,R.id.tabs);
        mViewpager = $(view,R.id.viewpager);

        mTitles = new ArrayList<>();
        mTitles.add(getString(R.string.hot_play));
        mTitles.add(getString(R.string.coming_movie));

        mFragments = new ArrayList<>();
        HotPalyFragment hotPalyFragment
                = HotPalyFragment.newInstance(getString(R.string.hot_play));
        ComingMovieFragment comingMovieFragment
                = ComingMovieFragment.newInstance(getString(R.string.coming_movie));
        mFragments.add(hotPalyFragment);
        mFragments.add(comingMovieFragment);

        //将fragment与presenter绑定
        mHotPlayPresenter = new HotPlayPresenter(hotPalyFragment);
        mComingMoviePresenter = new ComingMoviePresenter(comingMovieFragment);

        mAdapter = new MyFragmentStatePagerAdapter(getChildFragmentManager(),mFragments,mTitles);
        mViewpager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewpager);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void widgetClick(View v) {

    }
}
