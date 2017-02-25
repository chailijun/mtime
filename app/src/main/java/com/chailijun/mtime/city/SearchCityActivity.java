package com.chailijun.mtime.city;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.base.CommonBaseAdapter;
import com.chailijun.baseadapter.interfaces.OnItemClickListener;
import com.chailijun.mtime.MtimeApp;
import com.chailijun.mtime.R;
import com.chailijun.mtime.adapter.decoration.CityListDecoration;
import com.chailijun.mtime.adapter.decoration.DividerItemDecoration;
import com.chailijun.mtime.base.BaseActivity;
import com.chailijun.mtime.customview.ClearEditText;
import com.chailijun.mtime.customview.IndexBar;
import com.chailijun.mtime.db.entity.City;
import com.chailijun.mtime.db.gen.CityDao;
import com.chailijun.mtime.location.LocationService;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Logger;
import com.chailijun.mtime.utils.SPUtil;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.baidu.location.d.j.S;

/**
 * 搜索城市
 */
public class SearchCityActivity extends BaseActivity {

    //定位服务
    private LocationService locationService;

    //工具栏
    private ImageView mBack;
    private TextView mHeadTitle;

    private RecyclerView mCityListView;
    private RecyclerView mSearchResultView;

    private Button mCancelButton;
    private TextView mTvSideBarHint;
    private IndexBar mIndexBar;
    private ClearEditText mSearchEditText;
    private FrameLayout mCityListContainer;
    private FrameLayout mCitySearchContainer;
    private TextView noDataView;

    private CityListAdapter mCityListAdapter;
    private CitySearchAdapter mAdapterSearch;
    private List<CityBean> mData = new ArrayList<>();
    private LinearLayoutManager mManager;
    private List<City> allCity;

    private CityDao cityDao;

    /**
     * 定位回调监听
     */
    private BDLocationListener mListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (null != location) {

                if (location.getLocType() != BDLocation.TypeServerError &&
                        location.getCityCode() != null){

                    Logger.d(TAG,"定位城市:"+location.getCity());

                    mCityListAdapter.setLocationSuccess(getLocationCity(location));

                }else {
                    mCityListAdapter.setLocationFailed(getString(R.string.location_failed));
                }
            }
            locationService.stop();
        }
    };

    /**
     * 根据定位返回的结果查找数据库中对应城市的信息
     * @param location
     */
    private City getLocationCity(BDLocation location) {
        String cityName = location.getCity();

        //保存定位地址的经纬度
        SPUtil.save(Constants.LATITUDE,location.getLatitude()+"");
        SPUtil.save(Constants.LONGITUDE,location.getLongitude()+"");

        for (int i = 0; i < cityName.length(); i++) {
            cityName = cityName.substring(0,cityName.length()-1-i);
            City city = cityDao.queryBuilder().where(CityDao.Properties.N.eq(cityName)).build().unique();
            if (city != null){
                return city;
            }
        }

        //未找到对应城市
        return new City(null,-1,location.getCity(),null,null);
    }



//    private LocationCallBack callBack;

    @Override
    public void initParms(Bundle parms) {
        cityDao = MtimeApp.getInstances().getmDaoSession().getCityDao();
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_search_city;
    }

    @Override
    public void initView(View view) {

        //工具栏
        mHeadTitle = $(R.id.head_title);
        mHeadTitle.setText(getString(R.string.select_city));
        mBack = $(R.id.back);

        mCityListView = $(R.id.rv_citylist);
        mSearchResultView = $(R.id.rv_result);

        mCancelButton = $(R.id.btn_cancel);
        mTvSideBarHint = $(R.id.tvSideBarHint);
        mIndexBar = $(R.id.indexBar);

        mSearchEditText = $(R.id.et_search);

        mCityListContainer = $(R.id.fl_city_list);
        mCitySearchContainer = $(R.id.fl_city_search);

        noDataView = $(R.id.tv_noData);

        //城市列表
        mCityListAdapter = new CityListAdapter(null);
        mCityListView.setLayoutManager(mManager = new LinearLayoutManager(this));
        mCityListView.setHasFixedSize(true);
        mCityListView.setAdapter(mCityListAdapter);

        //线性item点击
        mCityListView.addOnItemTouchListener(new com.chad.library.adapter.base.listener.OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                CityBean cityBean = (CityBean) adapter.getData().get(position);
                Long id = cityBean.getCityList().get(0).getId();
                int locationId = ((int) (long) id);

                finishWithCityId(locationId);
            }
        });

        //griditem点击
        mCityListAdapter.setGridItemClickLisenter(new CityListAdapter.OnItemClickLisenter() {
            @Override
            public void onClickItem(City data, int position) {
                Long id = data.getId();
                int locationId = ((int) (long) id);

                finishWithCityId(locationId);
            }
        });

        //搜索结果视图设置
        mAdapterSearch = new CitySearchAdapter(null);
        mSearchResultView.setHasFixedSize(true);
        mSearchResultView.setLayoutManager(new LinearLayoutManager(this));
        mSearchResultView.setAdapter(mAdapterSearch);

        mSearchResultView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mSearchResultView.addOnItemTouchListener(new com.chad.library.adapter.base.listener.OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                City city = (City) adapter.getData().get(position);
                Long id = city.getId();
                int locationId = (int) ((long) id);

                finishWithCityId(locationId);
            }
        });

        initLocationService();
        initCityData();
    }

    /**
     * 初始化定位服务
     */
    private void initLocationService() {
        locationService = ((MtimeApp) getApplication()).locationService;
        locationService.registerListener(mListener);
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationService.start();
    }

    @Override
    protected void onStop() {
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onStop();
    }

    @Override
    public void setListener() {
        mBack.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);

        //搜索框文字变化监听
        mSearchEditText.addTextChangedListener(new SearchTextWatcher());
        //搜索框获取焦点监听
        mSearchEditText.setOnFocusChangeListener(new SearchOnFocusChangeListener());
    }

    @Override
    public void doBusiness(Context mContext) {
        //定位
        mCityListAdapter.setmLocationLisenter(new CityListAdapter.LocationLisenter() {
            @Override
            public void onLocationClick() {
                locationService.start();
            }
        });
    }

    private void initCityData() {

        //所有城市
        allCity = cityDao.queryBuilder().build().list();
        Collections.sort(allCity);

        //定位城市
        List<City> locationCity = new ArrayList<>();
        CityBean locationCityBean = new CityBean(locationCity, "定位城市", CityBean.TYPE_LOCATION);
        mData.add(locationCityBean);

        //当前城市
        int currCityId = SPUtil.getInt(Constants.LOCATION_ID);
        City currCity = cityDao.queryBuilder().where(CityDao.Properties.Id.eq(currCityId)).build().unique();
        List<City> cityListTemp = new ArrayList<>();
        cityListTemp.add(currCity);
        CityBean cityBean = new CityBean(cityListTemp, "当前城市");
        mData.add(cityBean);

        //热门城市
        String[] stringArray = getResources().getStringArray(R.array.hotcity);
        List<City> hotCity = new ArrayList<>();
        for (int i = 0; i < stringArray.length; i++) {
            for (City city : allCity) {
                if (city.getN().equals(stringArray[i])) {
                    hotCity.add(city);
                    break;
                }
            }
        }
        mData.add(new CityBean(hotCity, "热门城市"));

        //一般列表城市
        for (int i = 0; i < allCity.size(); i++) {
            List<City> commCityList = new ArrayList<>();
            commCityList.add(allCity.get(i));

            CityBean bean = new CityBean(commCityList,
                    allCity.get(i).getPinyinShort().substring(0, 1).toUpperCase(),
                    CityBean.TYPE_LINE);

            mData.add(bean);
        }

        mCityListAdapter.setNewData(mData);
        mCityListView.addItemDecoration(new CityListDecoration(this, mData));
        mCityListView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        mIndexBar.setPressedShowTextView(mTvSideBarHint)
                .setSourceDatas(mData)
                .setLayoutManager(mManager);

    }

    /**
     * 保存城市id至SP，并携带城市id退出该页面
     *
     * @param locationId
     */
    private void finishWithCityId(int locationId) {

        SPUtil.saveInt(Constants.LOCATION_ID, locationId);

        Intent intent = new Intent();
        intent.putExtra(Constants.LOCATION_ID, locationId);
        setResult(RESULT_OK, intent);
        finish();
    }

    class SearchTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
//            QueryBuilder<City> qb = MtimeApp.getInstances().getmDaoSession().getCityDao().queryBuilder();
            QueryBuilder<City> builder = cityDao.queryBuilder();

            //模糊查询
            builder.whereOr(CityDao.Properties.N.like("%" + editable + "%"),
                    CityDao.Properties.PinyinShort.like("%" + editable + "%"),
                    CityDao.Properties.PinyinFull.like("%" + editable + "%"));

            List<City> list = builder.list();
            if (list != null && list.size() > 0) {
                noDataView.setVisibility(View.GONE);
                mSearchResultView.setVisibility(View.VISIBLE);
                mAdapterSearch.setHighlightStr(editable.toString());
                mAdapterSearch.setNewData(list);
            } else {
                //没有找到数据
                mSearchResultView.setVisibility(View.GONE);
                noDataView.setVisibility(View.VISIBLE);
            }
            if (editable.length() == 0) {
                mAdapterSearch.clearHighlightStr();
            }
        }
    }

    class SearchOnFocusChangeListener implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if (hasFocus) {
                // 获得焦点
                mCityListContainer.setVisibility(View.GONE);
                mCitySearchContainer.setVisibility(View.VISIBLE);
                mAdapterSearch.setNewData(allCity);
            } else {
                // 失去焦点
                mCitySearchContainer.setVisibility(View.GONE);
                mCityListContainer.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public void widgetClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_cancel:
                if (mSearchEditText.isFocusable()) {
                    mSearchEditText.setText("");
                    mSearchEditText.clearFocus();
                }
                hideSoftInput();
                break;
            default:
                break;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();

        hideSoftInput();
    }

    /**
     * 关闭软键盘
     */
    private void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mSearchEditText.getWindowToken(), 0);
    }
}
