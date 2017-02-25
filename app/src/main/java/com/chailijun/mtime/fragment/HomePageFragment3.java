package com.chailijun.mtime.fragment;

import android.os.Bundle;
import android.view.View;

import com.chailijun.mtime.R;

@Deprecated
public class HomePageFragment3 extends BaseFragment {


    public static HomePageFragment3 newInstance(String param1) {
        HomePageFragment3 fragment = new HomePageFragment3();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public HomePageFragment3() {

    }

    @Override
    public String getFragmentName() {
        return HomePageFragment3.class.getSimpleName();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initViews(View rootView) {

    }

    @Override
    protected void lazyLoadData() {

    }


}
