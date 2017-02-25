package com.chailijun.mtime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.chailijun.mtime.R;
import com.chailijun.mtime.adapter.MyFragmentPagerAdapter;
import com.chailijun.mtime.api.ApiConstants;
import com.chailijun.mtime.fragment.BoxOfficeFragment;
import com.chailijun.mtime.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class BoxOfficeActivity extends BaseActivity {

    @BindView(R.id.head_title)
    TextView head_title;
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    private MyFragmentPagerAdapter mAdapter;

    private int tabIndex;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_box_office;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {

        getData();

        head_title.setText(getString(R.string.global_boxofice));

        List<String> titles = new ArrayList<>();
        titles.add(getString(R.string.mainland));
        titles.add(getString(R.string.NorthAmerica));
        titles.add(getString(R.string.global));

        List<Fragment> fragments = new ArrayList<>();
        //内地/北美/全球
        fragments.add(BoxOfficeFragment.newInstance(ApiConstants.AreaID_MAINLAND));
        fragments.add(BoxOfficeFragment.newInstance(ApiConstants.AreaID_NORTH_AMERICA));
        fragments.add(BoxOfficeFragment.newInstance(ApiConstants.AreaID_GLOBAL));

        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        if (tabIndex == Constants.NORTH_AMERICA){
            mViewPager.setCurrentItem(1);
        }else if (tabIndex == Constants.GLOBAL){
            mViewPager.setCurrentItem(2);
        }else {
            mViewPager.setCurrentItem(0);
        }
    }

    private void getData() {
        Intent intent = getIntent();
        tabIndex = intent.getIntExtra(Constants.AREA, 0);
    }

    @OnClick(R.id.back)
    public void OnClick(View view) {
        finish();
    }
}
