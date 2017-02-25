package com.chailijun.mtime.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.interfaces.OnItemClickListener;
import com.chailijun.mtime.MtimeApp;
import com.chailijun.mtime.R;
import com.chailijun.mtime.moviedetail.MovieDetailActivity;
import com.chailijun.mtime.city.SearchCityActivity;
import com.chailijun.mtime.adapter.HotPlayMoviesAdapter;
import com.chailijun.mtime.db.entity.City;
import com.chailijun.mtime.db.gen.CityDao;
import com.chailijun.mtime.mvp.interf.IHotPlayMoviesView;
import com.chailijun.mtime.mvp.interf.IHotPlayMoviewsPresenter;
import com.chailijun.mtime.mvp.model.HotPlayMoviesJson;
import com.chailijun.mtime.mvp.presenter.HotPlayMoviewsPresenter;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Logger;
import com.chailijun.mtime.utils.MsgEvent;
import com.chailijun.mtime.utils.SPUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

@Deprecated
public class HotPlayMoviesFragment extends BaseFragment implements IHotPlayMoviesView<HotPlayMoviesJson> {

    @BindView(R.id.tv_location)
    TextView tv_location;
    @BindView(R.id.tv_totalHotMovie)
    TextView tv_totalHotMovie;
    @BindView(R.id.tv_totalComingMovie)
    TextView tv_totalComingMovie;
    @BindView(R.id.tv_totalCinemaCount)
    TextView tv_totalCinemaCount;
    @BindView(R.id.rv_hotplaymovie)
    RecyclerView mRecyclerView;

    private HotPlayMoviesAdapter mAdapter;
    private IHotPlayMoviewsPresenter presenter;


    public static HotPlayMoviesFragment newInstance(String param1) {
        HotPlayMoviesFragment fragment = new HotPlayMoviesFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public String getFragmentName() {
        return HotPlayMoviesFragment.class.getSimpleName();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hot_play_movies;
    }

    @Override
    protected void initViews(View rootView) {

        mAdapter = new HotPlayMoviesAdapter(getActivity(), null, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener<HotPlayMoviesJson.MoviesBean>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, HotPlayMoviesJson.MoviesBean data, int position) {
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                intent.putExtra(Constants.MOVIE_ID, data.getMovieId());
                getActivity().startActivity(intent);
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new HotPlayMoviewsPresenter(this);
        //获取当前定位的城市id
        int locationId = SPUtil.getInt(Constants.LOCATION_ID);
        presenter.getHotPlayMovies(locationId);

        City currCity = MtimeApp.getInstances().getmDaoSession()
                                .getCityDao()
                                .queryBuilder()
                                .where(CityDao.Properties.Id.eq(locationId))
                                .build()
                                .unique();
        tv_location.setText(currCity.getN());
    }

    public void onEventMainThread(MsgEvent msg) {
        if (msg.getWhat() == Constants.UPDATE_DATA) {
            //获取当前定位的城市id
            int locationId = SPUtil.getInt(Constants.LOCATION_ID);
            presenter.getHotPlayMovies(locationId);

            City currCity = MtimeApp.getInstances().getmDaoSession()
                    .getCityDao()
                    .queryBuilder()
                    .where(CityDao.Properties.Id.eq(locationId))
                    .build()
                    .unique();
            tv_location.setText(currCity.getN());
        }
    }

    @Override
    protected void lazyLoadData() {
        //懒加载：初始化
    }

    @Override
    public void addHotPlayMovies(HotPlayMoviesJson data) {
        setData(data);
    }

    private void setData(HotPlayMoviesJson data) {
        tv_totalHotMovie.setText(String.format(getActivity().getResources().getString(R.string.total_hot_movie), data.getTotalHotMovie()));
        tv_totalComingMovie.setText(String.format(getActivity().getResources().getString(R.string.total_coming_movie), data.getTotalComingMovie()));
        tv_totalCinemaCount.setText(String.format(getActivity().getResources().getString(R.string.total_cinema_count), data.getTotalCinemaCount()));

        List<HotPlayMoviesJson.MoviesBean> movies = data.getMovies();
        mAdapter.setNewData(movies);
    }


    @Override
    public void loadFailed(String msg) {

        Logger.e("TAG", "加载HotPlayMovies失败:" + msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        presenter.unsubcrible();
    }

    @OnClick({R.id.tv_totalHotMovie, R.id.tv_location, R.id.tv_totalComingMovie, R.id.tv_totalCinemaCount})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_location:
                getActivity().startActivity(new Intent(getActivity(), SearchCityActivity.class));
                break;
            case R.id.tv_totalHotMovie:
                Toast.makeText(getActivity(), "进入购票", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_totalComingMovie:
                Toast.makeText(getActivity(), "即将上映", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_totalCinemaCount:
                Toast.makeText(getActivity(), "同城影院", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
