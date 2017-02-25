package com.chailijun.mtime.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.chailijun.mtime.R;
import com.chailijun.mtime.adapter.MyFragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@Deprecated
public class PayticketMovieFragment extends BaseFragment{

    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    private List<String> mTitles;
    private List<Fragment> mFragments;
    private MyFragmentStatePagerAdapter mAdapter;

    @Override
    public String getFragmentName() {
        return PayticketMovieFragment.class.getSimpleName();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_payticket_movie;
    }

    @Override
    protected void initViews(View rootView) {
        mTitles = new ArrayList<>();
        mTitles.add(getString(R.string.hot_play));
        mTitles.add(getString(R.string.coming_movie));

        mFragments = new ArrayList<>();
        mFragments.add(new PayticketMovieHotPalyFragment());
        mFragments.add(new PayticketMovieComingMovieFragment());

        mAdapter = new MyFragmentStatePagerAdapter(getChildFragmentManager(),mFragments,mTitles);
        mViewpager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewpager);

    }

    @Override
    protected void lazyLoadData() {

    }
}
