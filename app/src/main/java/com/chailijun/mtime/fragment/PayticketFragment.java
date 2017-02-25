package com.chailijun.mtime.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chailijun.mtime.MtimeApp;
import com.chailijun.mtime.R;
import com.chailijun.mtime.activity.SearchActivity;
import com.chailijun.mtime.city.SearchCityActivity;
import com.chailijun.mtime.customview.SwitchButton;
import com.chailijun.mtime.db.entity.City;
import com.chailijun.mtime.db.gen.CityDao;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Logger;
import com.chailijun.mtime.utils.MsgEvent;
import com.chailijun.mtime.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

@Deprecated
public class PayticketFragment extends BaseFragment {

    @BindView(R.id.rl_head)
    RelativeLayout rl_head;
    @BindView(R.id.ll_search)
    LinearLayout ll_search;
    @BindView(R.id.switch_button)
    SwitchButton switchButton;
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.tv_location)
    TextView tv_location;

    @BindView(R.id.container)
    FrameLayout container;
    private List<Fragment> mFragments;
    private CityDao cityDao;

    public static PayticketFragment newInstance(String param1) {
        PayticketFragment fragment = new PayticketFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public String getFragmentName() {
        return PayticketFragment.class.getSimpleName();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        String string = bundle.getString("agrs1");
        Logger.e("CLJ",string+" 初始化");

        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_payticket;
    }

    @Override
    protected void initViews(View rootView) {
        setCityName();

        mFragments = new ArrayList<>();
        mFragments.add(new PayticketMovieFragment());
        mFragments.add(new PayticketCinemaFragment());

        //设置默认显示的模块
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.container, mFragments.get(0));
        transaction.commit();

        switchButton.setSelectLisenter(new SwitchButton.OnSelectLisenter() {
            @Override
            public void onSelectLeft() {
//                Toast.makeText(getActivity(), "left", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                if (mFragments.get(0).isAdded()) {
                    transaction.show(mFragments.get(0));
                } else {
                    transaction.add(R.id.container, mFragments.get(0));
                }
                if (mFragments.get(1).isAdded()) {
                    transaction.hide(mFragments.get(1));
                }
                transaction.commit();

            }

            @Override
            public void onSelectRight() {
//                Toast.makeText(getActivity(), "right", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                if (mFragments.get(1).isAdded()) {
                    transaction.show(mFragments.get(1));
                } else {
                    transaction.add(R.id.container, mFragments.get(1));
                }
                if (mFragments.get(0).isAdded()) {
                    transaction.hide(mFragments.get(0));
                }
                transaction.commit();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchButton.getStatus() == SwitchButton.STATUS_LEFT) {
                    startActivity(new Intent(getActivity(), SearchActivity.class));
                }
                if (switchButton.getStatus() == SwitchButton.STATUS_RIGHT) {
                    rl_head.setVisibility(View.GONE);
                    ll_search.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * 更新页面
     * @param msg
     */
    public void onEventMainThread(MsgEvent msg) {
        if (msg.getWhat() == Constants.UPDATE_DATA){
            setCityName();
        }
    }

    private void setCityName() {
        int currCityId = SPUtil.getInt(Constants.LOCATION_ID);
        cityDao = MtimeApp.getInstances().getmDaoSession().getCityDao();
        City currCity = cityDao.queryBuilder()
                .where(CityDao.Properties.Id.eq(currCityId))
                .build()
                .unique();
        tv_location.setText(currCity.getN());
    }

    @Override
    protected void lazyLoadData() {

    }

    @OnClick({R.id.back, R.id.tv_location})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                if (rl_head != null && ll_search != null) {
                    rl_head.setVisibility(View.VISIBLE);
                    ll_search.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_location:
                getActivity().startActivity(new Intent(getActivity(), SearchCityActivity.class));
                break;
            default:
                break;
        }
    }


}
