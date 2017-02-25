package com.chailijun.mtime.payticket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chailijun.mtime.MtimeApp;
import com.chailijun.mtime.R;
import com.chailijun.mtime.city.SearchCityActivity;
import com.chailijun.mtime.activity.SearchItemActivity;
import com.chailijun.mtime.base.BaseFragment;
import com.chailijun.mtime.customview.SwitchButton;
import com.chailijun.mtime.db.entity.City;
import com.chailijun.mtime.db.gen.CityDao;
import com.chailijun.mtime.payticket.cinema.PayticketCinemaFragment;
import com.chailijun.mtime.payticket.movie.PayticketMovieFragment;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Logger;
import com.chailijun.mtime.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class PayticketFragment extends BaseFragment{

    public static final String ARGUMENT = "argument";
    public static final int REQUSET_PAY = 2;

    private TextView tv_location;
    private SwitchButton mSwitchButton;
    private ImageView mSearch;
    private ImageView mBack;

    private RelativeLayout rl_head;
    private LinearLayout ll_search;

    private List<Fragment> mFragments;
    private CityDao cityDao;
    private int locationId;

    public static PayticketFragment newInstance(String argument) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, argument);

        PayticketFragment fragment = new PayticketFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_payticket;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.e("TAG","--->onActivityResult()");
        if (requestCode == REQUSET_PAY && resultCode == RESULT_OK){

            locationId = data.getIntExtra(Constants.LOCATION_ID, 0);
            if (locationId != 0){
                setCityName(locationId);
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragments = new ArrayList<>();
        mFragments.add(new PayticketMovieFragment());
        mFragments.add(new PayticketCinemaFragment());

        //设置默认显示的模块
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.container, mFragments.get(0));
        transaction.commit();
    }

    @Override
    public void initView(View view) {

        tv_location = $(view,R.id.tv_location);
        mSwitchButton = $(view,R.id.switch_button);

        rl_head = $(view,R.id.rl_head);
        ll_search = $(view,R.id.ll_search);

        mSearch = $(view,R.id.search);
        mBack = $(view,R.id.back);

    }

    @Override
    public void setListener() {
        super.setListener();

        mSwitchButton.setSelectLisenter(new SwitchButton.OnSelectLisenter() {
            @Override
            public void onSelectLeft() {
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

        mSearch.setOnClickListener(this);
        mBack.setOnClickListener(this);
        tv_location.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        int currCityId = SPUtil.getInt(Constants.LOCATION_ID);
        setCityName(currCityId);
    }

    private void setCityName(int currCityId) {

        cityDao = MtimeApp.getInstances().getmDaoSession().getCityDao();
        City currCity = cityDao.queryBuilder()
                .where(CityDao.Properties.Id.eq(currCityId))
                .build()
                .unique();
        tv_location.setText(currCity.getN());
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.search:
                if (mSwitchButton.getStatus() == SwitchButton.STATUS_LEFT) {
                    startActivity(new Intent(getActivity(), SearchItemActivity.class));
                }
                if (mSwitchButton.getStatus() == SwitchButton.STATUS_RIGHT) {
                    rl_head.setVisibility(View.GONE);
                    ll_search.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.back:
                if (rl_head != null && ll_search != null) {
                    rl_head.setVisibility(View.VISIBLE);
                    ll_search.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_location:
                Intent intent = new Intent(getActivity(), SearchCityActivity.class);
                startActivityForResult(intent,REQUSET_PAY);
                break;
            default:
                break;
        }
    }
}
