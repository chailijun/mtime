package com.chailijun.mtime.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.interfaces.OnMultiItemClickListeners;
import com.chailijun.mtime.R;
import com.chailijun.mtime.moviedetail.MovieDetailActivity;
import com.chailijun.mtime.adapter.MovieComingsAdapter;
import com.chailijun.mtime.adapter.decoration.SuspensionDecoration;
import com.chailijun.mtime.mvp.interf.IMovieComingNewPresenter;
import com.chailijun.mtime.mvp.interf.IMovieComingNewView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.mvp.model.movie.MovieComingNewJson;
import com.chailijun.mtime.mvp.presenter.MovieComingNewPresenter;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Logger;
import com.chailijun.mtime.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@Deprecated
public class PayticketMovieComingMovieFragment extends BaseFragment implements IMovieComingNewView<BaseData> {

    @BindView(R.id.rv_coming)
    RecyclerView mRecyclerview;

    private int locationId;
    private IMovieComingNewPresenter presenter;
    private MovieComingsAdapter mAdapter;

    @Override
    public String getFragmentName() {
        return PayticketCinemaFragment.class.getSimpleName();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_payticket_movie_coming_movie;
    }

    @Override
    protected void initViews(View rootView) {
        getData();

        //即将上映
        mAdapter = new MovieComingsAdapter(getActivity(),null,false);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(manager);
        mRecyclerview.setAdapter(mAdapter);

        mAdapter.setOnMultiItemClickListener(new OnMultiItemClickListeners<BaseData>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, BaseData data, int position, int viewType) {
                if (data instanceof MovieComingNewJson.MoviecomingsBean){
                    MovieComingNewJson.MoviecomingsBean moviecomingsBean = (MovieComingNewJson.MoviecomingsBean) data;
                    Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                    intent.putExtra(Constants.MOVIE_ID,moviecomingsBean.getId());
                    getActivity().startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void getData() {
        locationId = SPUtil.getInt(Constants.LOCATION_ID);
    }

    @Override
    protected void lazyLoadData() {
        presenter = new MovieComingNewPresenter(this);
        if (locationId != 0){
            presenter.getMovieComingNew(locationId);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null){
            presenter.unsubcrible();
        }
    }

    @Override
    public void addMovieComingNew(BaseData data) {
        if (data instanceof MovieComingNewJson){
            MovieComingNewJson movieComingNewJson = (MovieComingNewJson) data;
            //“最受关注”部分的数据
            List<BaseData> datas = new ArrayList<>();
            BaseData baseData = new BaseData();
            baseData.setObj(movieComingNewJson.getAttention());
            baseData.setObj1(movieComingNewJson.getMoviecomings().size());//将“即将上映”的电影总数传入适配器
            datas.add(baseData);

            //“即将上映”部分的数据
            for (int i = 0; i < movieComingNewJson.getMoviecomings().size(); i++) {
                datas.add(movieComingNewJson.getMoviecomings().get(i));
            }
            mAdapter.setNewData(datas);

            //设置“即将上映”RecyclerView的ItemDecoration，达到分类标题、悬停顶部的效果
            mRecyclerview.addItemDecoration(new SuspensionDecoration(getActivity()
                    ,movieComingNewJson.getMoviecomings()).setHeaderViewCount(1));
        }
    }

    @Override
    public void loadFailed(String msg) {
        Logger.e("TAG","加载失败:"+msg);
    }
}