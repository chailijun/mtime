package com.chailijun.mtime.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chailijun.mtime.MtimeApp;
import com.chailijun.mtime.utils.Logger;

import butterknife.ButterKnife;
import butterknife.Unbinder;

@Deprecated
public abstract class BaseFragment extends Fragment {

    private Unbinder unbinder;

    protected View rootView;

    private boolean isVisible;
    private boolean isPrepared;
    private boolean isFirst = true;

    private String tag;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tag = getFragmentName();
        Logger.e(tag,"--->onCreateView()");
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);
            unbinder = ButterKnife.bind(this, rootView);
            initViews(rootView);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.e(tag,"--->onActivityCreated()");

        isPrepared = true;
        lazyLoad();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.e(tag,"--->onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.e(tag,"--->onDestroy()");

        unbinder.unbind();

//        RefWatcher refWatcher = MtimeApp.getRefWatcher(getActivity());
//        refWatcher.watch(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Logger.e(tag,"--->onDetach()");
    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        Logger.e(BaseFragment.class.getSimpleName(),printTag+"--->setUserVisibleHint()");
        if (getUserVisibleHint()){
            isVisible = true;
            onVisible();
        }else {
            isVisible = false;
            onInvisible();
        }
    }



    /**
     * 可见
     */
    private void onVisible() {
//        Logger.e(BaseFragment.class.getSimpleName(),printTag+":onVisible():"+this);
        lazyLoad();
    }

    /**
     * 不可见
     */
    protected void onInvisible() {
//        Logger.e(BaseFragment.class.getSimpleName(),printTag+":onInvisible():"+this);
    }



    /**
     * 懒加载
     */
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirst) {
            return;
        }
        lazyLoadData();
        isFirst = false;
    }

    protected void lazyLoadData(){};

    protected abstract int getLayoutId();

    protected abstract void initViews(View rootView);


    public abstract String getFragmentName();
}
