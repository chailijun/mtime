package com.chailijun.mtime.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chailijun.mtime.MtimeApp;
import com.chailijun.mtime.utils.Logger;


public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    private View mContextView = null;

    protected final String TAG = this.getClass().getSimpleName();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Logger.d(TAG,"-->onAttach()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d(TAG,"-->onCreate()");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Logger.d(TAG,"-->onCreateView()");
        View mView = bindView();
        if (null == mView) {
            mContextView = inflater.inflate(bindLayout(), container, false);
        } else {
            mContextView = mView;
        }
        initView(mContextView);
        setListener();
        return mContextView;
    }

    public void setListener() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.d(TAG,"-->onActivityCreated()");
        doBusiness(getActivity());
    }


    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(getActivity(),clz));
    }

    /**
     * [绑定视图]
     *
     * @return
     */
    public abstract View bindView();

    /**
     * [绑定布局]
     *
     * @return
     */
    public abstract int bindLayout();

    /**
     * [初始化控件]
     *
     * @param view
     */
    public abstract void initView(View view);

    /**
     * [业务操作]
     *
     * @param mContext
     */
    public abstract void doBusiness(Context mContext);

    /**
     * [View点击]
     *
     * @param v
     */
    public abstract void widgetClick(View v);

    @Override
    public void onClick(View view) {
        if (fastClick())
            widgetClick(view);
    }

    /**
     * [绑定控件]
     *
     * @param view
     * @param resId
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T $(View view, int resId) {
        return (T) view.findViewById(resId);
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [防止快速点击]
     * @return
     */
    private boolean fastClick() {
        long lastClick = 0;
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    /**
     * [简化Toast]
     * @param msg
     */
    protected void showToast(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        Logger.d(TAG,"-->onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d(TAG,"-->onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.d(TAG,"-->onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Logger.d(TAG,"-->onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.d(TAG,"-->onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d(TAG,"-->onDestroy()");

//        RefWatcher refWatcher = MtimeApp.getRefWatcher(getActivity());
//        refWatcher.watch(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Logger.d(TAG,"-->onDetach()");
    }
}
