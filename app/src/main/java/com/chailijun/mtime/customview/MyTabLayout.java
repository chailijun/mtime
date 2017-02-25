package com.chailijun.mtime.customview;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chailijun.mtime.utils.DensityUtil;
import com.chailijun.mtime.utils.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;

public class MyTabLayout extends TabLayout {

    private List<String> mTitle;

    private MyAdapter adapter;

    private ViewGroup mContainer;

    /**被绑定的TabLayout*/
    private MyTabLayout tabLayout2;

    private MyOnTabSelectedListener myOnTabSelectedListener;

    public MyTabLayout(Context context) {
        this(context, null);
    }

    public MyTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        myOnTabSelectedListener = new MyOnTabSelectedListener();
    }

    /**
     * 传入字符串作为Tab名称
     *
     * @param mTitle
     */
    public MyTabLayout setTab(List<String> mTitle) {
        this.mTitle = mTitle;
        if (mTitle != null) {
            for (int i = 0; i < mTitle.size(); i++) {
                TabLayout.Tab tab = this.newTab();
                tab.setText(mTitle.get(i));
                this.addTab(tab, i == 0 ? true : false);
            }
        }
        return this;
    }

    /**
     * 设置tab对应的fragment
     *
     * @param container
     * @param fragments
     * @return
     */
    public MyTabLayout setFragments(FragmentManager fm, final ViewGroup container, List<Fragment> fragments) {
        adapter = new MyAdapter(fm, fragments);
        mContainer = container;
        this.addOnTabSelectedListener(myOnTabSelectedListener);

        return this;
    }

    /**
     * 设置默认被选中的tab以及对应的fragment
     *
     * @param position
     * @return
     */
    public MyTabLayout setDefaultFragment(int position) {
        if (mContainer != null) {
            Fragment fragment = (Fragment) adapter.instantiateItem(mContainer, position);
            adapter.setPrimaryItem(mContainer, position, fragment);
            adapter.finishUpdate(mContainer);
        }
        return this;
    }

    /**
     * 与其它的MyTabLayout绑定，tab选中的位置同步
     * @param tabLayout
     */
    public MyTabLayout bind(MyTabLayout tabLayout){
        tabLayout2 = tabLayout;
        tabLayout.setTab(mTitle);
        tabLayout2.addOnTabSelectedListener(myOnTabSelectedListener);
        return this;
    }

    /**
     * 修改底部指示器的长短
     * @param leftDip
     * @param rightDip
     * @return
     */
    public MyTabLayout setIndicator(int leftDip, int rightDip) {
        Field tabStrip = ReflectionUtils.getDeclaredField(this, "mTabStrip") ;
        tabStrip.setAccessible(true);
        LinearLayout ll_tab= null;
        try {
            ll_tab = (LinearLayout) tabStrip.get(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < ll_tab.getChildCount(); i++) {
            View child = ll_tab.getChildAt(i);
            child.setPadding(0,0,0,0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT,1);
            params.setMargins(DensityUtil.dp2px(leftDip),0,DensityUtil.dp2px(rightDip),0);

            child.setLayoutParams(params);
            child.invalidate();
        }

        return this;
    }

    class MyOnTabSelectedListener implements TabLayout.OnTabSelectedListener{
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            int position = tab.getPosition();

            Fragment fragment = (Fragment) adapter.instantiateItem(mContainer,position);
            adapter.setPrimaryItem(mContainer,position,fragment);
            adapter.finishUpdate(mContainer);

            getTabAt(position).select();
            if (tabLayout2 != null){
                tabLayout2.getTabAt(position).select();
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    }


    public class MyAdapter extends FragmentPagerAdapter {

        private List<Fragment> views;

        public MyAdapter(FragmentManager fm, List<Fragment> views) {
            super(fm);
            this.views = views;
        }

        @Override
        public Fragment getItem(int position) {
            return views.get(position);
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }


}
