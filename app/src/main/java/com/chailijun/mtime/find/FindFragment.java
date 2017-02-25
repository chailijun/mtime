package com.chailijun.mtime.find;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.chailijun.mtime.R;
import com.chailijun.mtime.adapter.MyFragmentPagerAdapter;
import com.chailijun.mtime.base.BaseFragment;
import com.chailijun.mtime.data.find.Index;
import com.chailijun.mtime.find.news.FindNewsFragment;
import com.chailijun.mtime.find.news.FindNewsPresenter;
import com.chailijun.mtime.find.review.FindReviewFragment;
import com.chailijun.mtime.find.review.FindReviewPresenter;
import com.chailijun.mtime.find.toplist.FindTopListFragment;
import com.chailijun.mtime.find.toplist.FindTopListPresenter;
import com.chailijun.mtime.find.trailer.FindTrailerPresenter;
import com.chailijun.mtime.find.trailer.FindTrailerFragment;
import com.chailijun.mtime.user.UserFragment;
import com.chailijun.mtime.utils.Logger;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class FindFragment extends BaseFragment implements FindContract.View {

    public static final String ARGUMENT = "argument";

    private TabLayout mTabs;
    private ViewPager mViewPager;

    private LinearLayout loading;
    private LinearLayout loading_failed;

    private List<String> mTitles;
    private List<Fragment> mFragments;
    //    private FragmentAdapter mAdapter;
    private MyFragmentPagerAdapter mAdapter;
//    private MyFragmentStatePagerAdapter mAdapter;

    private FindContract.Presenter mPresenter;

    //子页面的Presenter
    private FindNewsPresenter mFindNewsPresenter;
    private FindTrailerPresenter mFindTrailerPresenter;
    private FindTopListPresenter mFindTopListPresenter;
    private FindReviewPresenter mFindReviewPresenter;

    public static FindFragment newInstance(String argument) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, argument);

        FindFragment fragment = new FindFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTitles = new ArrayList<>();
        mTitles.add(getString(R.string.news));
        mTitles.add(getString(R.string.trailer));
        mTitles.add(getString(R.string.top_list));
        mTitles.add(getString(R.string.review));
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_find;
    }

    @Override
    public void initView(View view) {

        mTabs = $(view,R.id.tabs);
        mViewPager = $(view,R.id.viewpager);

        loading = $(view,R.id.loading);
        loading_failed = $(view,R.id.loading_failed);

        for (int i = 0; i < mTitles.size(); i++) {
            TabLayout.Tab tab = mTabs.newTab();
            tab.setText(mTitles.get(i));
            mTabs.addTab(tab, i == 0 ? true : false);
        }
    }

    @Override
    public void setListener() {
        super.setListener();

        loading_failed.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        mPresenter.subscribe();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.loading_failed:
                mPresenter.subscribe();
                break;
            default:
                break;
        }
    }


    @Override
    public void showIndex(Index index) {
        Logger.d(TAG,"发现index加载成功:"+index.toString());

        mFragments = new ArrayList<>();
        FindNewsFragment findNewsFragment = FindNewsFragment.newInstance(index.getNews());
//        FindNewsFragment findNewsFragment = new FindNewsFragment();
        FindTrailerFragment findTrailerFragment = FindTrailerFragment.newInstance(index.getTrailer());
        FindTopListFragment findTopListFragment = FindTopListFragment.newInstance(index.getTopList());
        FindReviewFragment findReviewFragment = FindReviewFragment.newInstance(index.getReview());

        mFragments.add(findNewsFragment);
        mFragments.add(findTrailerFragment);
        mFragments.add(findTopListFragment);
//        mFragments.add(new UserFragment());
        mFragments.add(findReviewFragment);

        //将fragment与presenter绑定
        mFindNewsPresenter = new FindNewsPresenter(findNewsFragment);
        mFindTrailerPresenter = new FindTrailerPresenter(findTrailerFragment);
        mFindTopListPresenter = new FindTopListPresenter(findTopListFragment);
        mFindReviewPresenter = new FindReviewPresenter(findReviewFragment);

        mAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(),mFragments,mTitles);
        mViewPager.setAdapter(mAdapter);
        mTabs.setupWithViewPager(mViewPager);
    }

    @Override
    public void showLoading(boolean active) {
        if (loading != null && loading_failed != null){
            if (active){
                //加载失败--->GONE
                if (loading_failed.getVisibility() == VISIBLE){
                    loading_failed.setVisibility(GONE);
                }
                //加载中--->VISIBLE
                if (loading.getVisibility() == GONE){
                    loading.setVisibility(VISIBLE);
                }
            }else {
                //加载中--->GONE
                if (loading.getVisibility() == VISIBLE){
                    loading.setVisibility(GONE);
                }
            }
        }
    }

    @Override
    public void showLoadingIndexError(String msg) {
        Logger.e(TAG,"发现index加载失败:"+msg);

        if (loading_failed != null && loading != null){
            //加载中--->GONE
            if (loading.getVisibility() == VISIBLE){
                loading.setVisibility(GONE);
            }
            //加载失败--->VISIBLE
            if (loading_failed.getVisibility() == GONE){
                loading_failed.setVisibility(VISIBLE);
            }
        }
    }

    @Override
    public void setPresenter(FindContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
