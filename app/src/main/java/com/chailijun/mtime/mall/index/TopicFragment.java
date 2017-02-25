package com.chailijun.mtime.mall.index;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chailijun.mtime.R;
import com.chailijun.mtime.base.BaseFragment;
import com.chailijun.mtime.data.mall.MallIndex;
import com.chailijun.mtime.utils.BitmapUtil;
import com.chailijun.mtime.utils.DensityUtil;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.Logger;

import java.util.ArrayList;
import java.util.List;


public class TopicFragment extends BaseFragment{

    public static final String ARGUMENT = "argument";

    private TabLayout mTabLayout;

    private List<Fragment> mSubFragments;

    private FragmentManager mFragmentManager;

    private ArrayList<MallIndex.MallBean.TopicBean> mTopicList;

    private int tabPosition = -1;

    /**Tab图片增加的亮度*/
    private int LIGHT = 10;
    /**Tab图片减少的亮度*/
    private int DARK = -30;

    public static TopicFragment newInstance(ArrayList<MallIndex.MallBean.TopicBean> argument) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARGUMENT,argument);

        TopicFragment fragment = new TopicFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mTopicList = (ArrayList<MallIndex.MallBean.TopicBean>) arguments.getSerializable(ARGUMENT);

        mSubFragments = new ArrayList<>();
        mFragmentManager = getChildFragmentManager();
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_mall_topic;
    }

    @Override
    public void initView(View view) {
        mTabLayout = $(view, R.id.tabs_topic);

        final List<ImageView> tabList = new ArrayList<>();
        for (int i = 0; i < mTopicList.size(); i++) {
            TabLayout.Tab tab = mTabLayout.newTab();

            ImageView topicImg = new ImageView(getActivity());
            ViewGroup.LayoutParams params =
                    new ViewGroup.LayoutParams(DensityUtil.dp2px(80.0f), DensityUtil.dp2px(80.0f));
            topicImg.setLayoutParams(params);
            tab.setCustomView(topicImg);

            tabList.add(topicImg);

//            mTabLayout.addTab(tab, i == (tabPosition == -1 ? 0 : tabPosition) ? true : false);
            mTabLayout.addTab(tab, i == 0 ? true : false);
            Imager.load(getActivity(), mTopicList.get(i).getUncheckImage(), topicImg);

            //tab图片初始亮度设置
            if (i == 0){
                BitmapUtil.changeLight(tabList.get(i),LIGHT);
            }else {
                BitmapUtil.changeLight(tabList.get(i),DARK);
            }

            TopicSubListfragment topicSubListfragment =
                    (TopicSubListfragment) mFragmentManager.findFragmentById(R.id.fl_topic);
            if(topicSubListfragment == null){
                topicSubListfragment = TopicSubListfragment.newInstance(mTopicList.get(i));
            }

            mSubFragments.add(topicSubListfragment);
        }

        //默认显示第一个fragment
        if (tabPosition == -1) {
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            if (mSubFragments.get(0).isAdded()) {
                transaction.show(mSubFragments.get(0));
            } else {
                transaction.add(R.id.fl_topic, mSubFragments.get(0));
            }
            transaction.commit();
        }

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                tabPosition = position;
                Logger.e(TAG, "选中position:" + position);

                BitmapUtil.changeLight(tabList.get(position),LIGHT);

                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                if (mSubFragments.get(position).isAdded()) {
                    transaction.show(mSubFragments.get(position));
                } else {
                    transaction.add(R.id.fl_topic, mSubFragments.get(position));
                }
                transaction.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Logger.e(TAG, "解除position:" + position);

                BitmapUtil.changeLight(tabList.get(position),DARK);

                if (mSubFragments.get(position).isAdded()) {
                    FragmentTransaction transaction = mFragmentManager.beginTransaction();
                    transaction.hide(mSubFragments.get(position));
                    transaction.commit();
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void widgetClick(View v) {

    }
}
