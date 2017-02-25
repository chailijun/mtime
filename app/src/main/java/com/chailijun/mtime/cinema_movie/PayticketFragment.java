package com.chailijun.mtime.cinema_movie;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chailijun.mtime.R;
import com.chailijun.mtime.base.BaseFragment;
import com.chailijun.mtime.customview.BasePopupWindow;
import com.chailijun.mtime.customview.ClearEditText;
import com.chailijun.mtime.data.cinema_movie.MovieShowtimes;
import com.chailijun.mtime.data.cinema_movie.ShowtimeDates;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Logger;
import com.chailijun.mtime.utils.SPUtil;
import com.chailijun.mtime.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class PayticketFragment extends BaseFragment implements PayticketContract.View {


    public static final String ARGUMENT = "argument";

    private TextView mMovieTitle;
    private ImageView mBack;
    private ImageView mSearch;
    private TabLayout mTabLayout;

    private LinearLayout ll_search;
    private Button btn_search;
    private ClearEditText mEditText;

    private RadioGroup rg_filter;
    private RadioButton filter_cinema;

    /**
     * 加载中
     */
    private LinearLayout loading;
    /**
     * 加载失败
     */
    private LinearLayout loading_failed;

    //影院
    private RecyclerView mRecyclerView;
    private CinemaAdapter mAdapter;

    /**
     * 影院缓存
     */
    private List<MovieShowtimes.CinemaItem> mCinema;

    /**
     * 存放放映日期的字符串--格式：20161108
     */
    private List<String> mDateList = new ArrayList<>();

    private PayticketContract.Presenter mPresenter;

    private int movieId;
    private int locationId;

    /**
     * 定位地点的经度
     */
    private double longitude;
    /**
     * 定位地点的纬度
     */
    private double latitude;

    /**
     * 影院筛选的PopupWindow
     */
    private PopupWindow mPopupWindow;

    public static PayticketFragment newInstance(int movieId) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARGUMENT, movieId);

        PayticketFragment fragment = new PayticketFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        movieId = bundle.getInt(ARGUMENT, 0);

        //城市id
        locationId = SPUtil.getInt(Constants.LOCATION_ID);

        String longitude = SPUtil.get(Constants.LONGITUDE, null);
        String latitude = SPUtil.get(Constants.LATITUDE, null);

        this.longitude = Double.parseDouble(longitude);
        this.latitude = Double.parseDouble(latitude);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_payticket2;
    }

    @Override
    public void initView(View view) {
        mMovieTitle = $(view, R.id.movie_title);
        mBack = $(view, R.id.back);
        mSearch = $(view, R.id.search);

        mTabLayout = $(view, R.id.tabs);

        ll_search = $(view, R.id.ll_search);
        btn_search = $(view, R.id.btn_search);
        mEditText = $(view, R.id.cet_cinema);

        rg_filter = $(view, R.id.rg_filter);
        filter_cinema = $(view, R.id.filter_cinema);

        //加载中
        loading = $(view, R.id.loading);
        //加载失败
        loading_failed = $(view, R.id.loading_failed);

        mRecyclerView = $(view, R.id.rv_cinema);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new CinemaAdapter(null);
        mRecyclerView.setAdapter(mAdapter);

//        mRecyclerView.addItemDecoration(
//                new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                MovieShowtimes.CinemaItem cinemaItem =
                        (MovieShowtimes.CinemaItem) adapter.getData().get(position);

                Intent intent = new Intent(getActivity(), CinemaMovieActivity.class);
                intent.putExtra(Constants.CINEMA_ID, cinemaItem.getCid());
                startActivity(intent);
            }
        });
    }

    @Override
    public void setListener() {
        super.setListener();

        mBack.setOnClickListener(this);
        mSearch.setOnClickListener(this);

        btn_search.setOnClickListener(this);

        loading_failed.setOnClickListener(this);

        //编辑栏内容变化监听
        mEditText.addTextChangedListener(new CinemaSearchTextWatcher());

        //对影院进行分类排序
        rg_filter.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.all_cinema:
                    mPresenter.getAllCinema(mCinema);
                    break;
                case R.id.price_cinema:
                    mPresenter.getOrderByPrice(mCinema);
                    break;
                case R.id.distance_cinema:
                    mPresenter.getOrderByDistance(mCinema);
                    break;
                case R.id.filter_cinema:
//                    showFilteringView();
                    break;
                default:
                    break;
            }
        });
        filter_cinema.setOnClickListener(v -> showFilteringView());
        //设置默认选中[全部]
        rg_filter.check(R.id.all_cinema);
    }

    private void showFilteringView() {

        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.popupwindow_cinema_filter, null);
        initPopTabLayout(view);
        PopupWindow popupWindow = new PopupWindow(view,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        //设置这两个属性后，点击空白处则可隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());

        //设置动画
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);

        //设置显示的位置
        popupWindow.showAtLocation(mRecyclerView, Gravity.BOTTOM, 0, 0);
        //设置空白背景半透明
        backgroundAlpha(0.5f);

        $(view,R.id.colse).setOnClickListener(v -> popupWindow.dismiss());

        //pop窗口消失恢复空白处透明度
        popupWindow.setOnDismissListener(() -> backgroundAlpha(1.0f));
    }

    /**
     * 初始化PopupWindow中的TabLayout
     * @param view
     */
    private void initPopTabLayout(View view) {
        List<String> titles = new ArrayList<>();
        titles.add("特色");
        titles.add("商圈");
        titles.add("地区");
        titles.add("地铁");

        TabLayout tabLayout = $(view,R.id.tabs);
        GridView gridView = $(view,R.id.gv_filter);

        for (int i = 0; i < titles.size(); i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setText(titles.get(i));
            tabLayout.addTab(tab, i == 0 ? true : false);
        }

        List<String> features = new ArrayList<>();
        features.add("全部");
        features.add("2D版");
        features.add("3D版");
        features.add("IMAX版");
        features.add("中国巨幕");
        features.add("停车场");
        features.add("WIFI");

        ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,features);
        gridView.setAdapter(adapter);
    }

    /**
     * 改变背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    @Override
    public void doBusiness(Context mContext) {
        if (locationId != 0 && movieId != 0) {
            mPresenter.loadShowtimeDates(locationId, movieId);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                getActivity().finish();
                break;
            case R.id.search:
                if (ll_search.getVisibility() == View.GONE) {
                    ll_search.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btn_search:
                if (ll_search.getVisibility() == View.VISIBLE) {
                    ll_search.setVisibility(View.GONE);
                    mEditText.setText("");
                }
                break;
            case R.id.loading_failed:
                onRefresh();
                break;
            default:
                break;
        }
    }

    private void onRefresh() {
        showLoading(true);
        doBusiness(getActivity());
    }

    @Override
    public void showLoading(boolean active) {
        if (loading != null && loading_failed != null) {
            if (active) {
                //加载失败--->GONE
                if (loading_failed.getVisibility() == VISIBLE) {
                    loading_failed.setVisibility(GONE);
                }
                //加载中--->VISIBLE
                if (loading.getVisibility() == GONE) {
                    loading.setVisibility(VISIBLE);
                }
            } else {
                //加载中--->GONE
                if (loading.getVisibility() == VISIBLE) {
                    loading.setVisibility(GONE);
                }
            }
        }
    }

    @Override
    public void showtimeDates(ShowtimeDates dates) {
        Logger.d(TAG, "加载 放映日期 成功:" + dates.getMovie().getNameCN());

        mMovieTitle.setText(dates.getMovie().getNameCN());

        initTabLayout(dates.getShowtimeDates());

        for (int i = 0; i < dates.getShowtimeDates().size(); i++) {
            String date = dates.getShowtimeDates().get(i).getDateValue().replace("-", "");
            mDateList.add(date);
        }

        //获取放映日期后再加载对应的影院
        mPresenter.loadAllCinemas(locationId, movieId, true, mDateList.get(0));

    }

    /**
     * 初始化TabLayout
     *
     * @param showtimeDates
     */
    private void initTabLayout(List<ShowtimeDates.ShowtimeDatesBean> showtimeDates) {

        for (int i = 0; i < showtimeDates.size(); i++) {
            TabLayout.Tab tab = mTabLayout.newTab();
            tab.setText(TimeUtils.whichDay(showtimeDates.get(i).getDateValue()));
            mTabLayout.addTab(tab, i == 0 ? true : false);
        }

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                mPresenter.loadAllCinemas(locationId, movieId, true, mDateList.get(position));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    public void showLoadShowTimeDatesError(String msg) {
        Logger.e(TAG, "加载 放映日期 失败:" + msg);

        if (loading_failed != null && loading != null) {
            //加载中--->GONE
            if (loading.getVisibility() == VISIBLE) {
                loading.setVisibility(GONE);
            }
            //加载失败--->VISIBLE
            if (loading_failed.getVisibility() == GONE) {
                loading_failed.setVisibility(VISIBLE);
            }
        }
    }

    @Override
    public void showMovieShowtimes(List<MovieShowtimes.CinemaItem> cinemaItems) {
        Logger.d(TAG, "加载 电影放映影院 成功:" + cinemaItems.size());

        //添加影院离定位地点的距离
        cinemaItems = mPresenter.getRecentlyCinema(cinemaItems);

        //将影院数据缓存下来
        mCinema = cinemaItems;


        mAdapter.setNewData(cinemaItems);
    }

    @Override
    public void showLoadMovieShowtimesError(String msg) {
        Logger.d(TAG, "加载 电影放映影院 失败:" + msg);
    }

    @Override
    public void showAllCinema(List<MovieShowtimes.CinemaItem> cinemaItems) {
        if (cinemaItems != null && cinemaItems.size() > 0) {
            mAdapter.setNewData(cinemaItems);
        }
    }

    @Override
    public void showOrderByPrice(List<MovieShowtimes.CinemaItem> cinemaItems) {
        mAdapter.setNewData(cinemaItems);
    }

    @Override
    public void showOrderByDistance(List<MovieShowtimes.CinemaItem> cinemaItems) {
        mAdapter.setNewData(cinemaItems);
    }

    @Override
    public void setPresenter(PayticketContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 影院搜索栏内容变化监听
     */
    class CinemaSearchTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            List<MovieShowtimes.CinemaItem> cinemas = new ArrayList<>();
            if (!TextUtils.isEmpty(s)) {
                for (int i = 0; i < mCinema.size(); i++) {
                    if (mCinema.get(i).getCn().contains(s)) {
                        cinemas.add(mCinema.get(i));
                    }
                }
                mAdapter.setNewData(cinemas);
            } else {
                mAdapter.setNewData(mCinema);
            }
        }
    }
}
