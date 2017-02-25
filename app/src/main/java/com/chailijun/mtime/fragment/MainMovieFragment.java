package com.chailijun.mtime.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.base.CommonBaseAdapter;
import com.chailijun.baseadapter.interfaces.OnItemClickListener;
import com.chailijun.mtime.R;
import com.chailijun.mtime.moviedetail.MovieDetailActivity;
import com.chailijun.mtime.mvp.interf.IPersonalDetailPresenter;
import com.chailijun.mtime.mvp.interf.IPersonalDetailView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.mvp.model.movie.PersonMovieJson;
import com.chailijun.mtime.mvp.presenter.PersonalDetailPresenter;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.Logger;

import java.util.List;

import butterknife.BindView;

public class MainMovieFragment extends BaseFragment implements IPersonalDetailView<BaseData> {

    @BindView(R.id.recyclerview_main_movie)
    RecyclerView recyclerview;

    private MainMovieAdapter mAdapter;
    private IPersonalDetailPresenter presenter;
    private int personId;
    private int pageIndex = 1;

    @Override
    public String getFragmentName() {
        return MainMovieFragment.class.getSimpleName();
    }

    public static MainMovieFragment newInstance(int personId) {
        MainMovieFragment fragment = new MainMovieFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.PERSON_ID,personId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        personId = bundle.getInt(Constants.PERSON_ID);
        Logger.e("TAG","personId====="+personId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_actor_detail_item_head_main_movie;
    }

    @Override
    protected void initViews(View rootView) {
        mAdapter = new MainMovieAdapter(getActivity(),null,true);
        /*//设置加载更多触发的事件监听
        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                loadMore();
            }
        });*/
//        mAdapter.setOnItemClickListener(new MyOnItemClickListener());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener<PersonMovieJson>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, PersonMovieJson data, int position) {
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                intent.putExtra(Constants.MOVIE_ID,data.getId());
//                Logger.e("TAG","MainMovieFragment--->电影id:"+data.getId());
                getActivity().startActivity(intent);
            }
        });

        requestData();
    }

    /*private void loadMore() {
        Logger.e("TAG","加载更多。。。");
        if (personId != 0){
            presenter.getPersonMovie(personId,0,pageIndex++);
        }
    }*/

    private void requestData() {
        presenter = new PersonalDetailPresenter(this);
        if (personId != 0){
            presenter.getPersonMovie(personId,0,pageIndex++);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unsubcrible();
    }

    @Override
    protected void lazyLoadData() {
        Logger.e("TAG","MainMovieFragment 可见");

    }

    @Override
    public void addPersonMovie(List<PersonMovieJson> datas) {
        Logger.e("TAG","personMovie====="+datas.size());
        /*if (data instanceof PersonMovieJson){
            PersonMovieJson personMovie = (PersonMovieJson) data;
            Logger.e("TAG","personMovie====="+personMovie);
            Logger.e("TAG","电影名字====="+personMovie.getName());
        }*/
        if (datas != null && datas.size() > 0){
            mAdapter.setLoadMoreData(datas);
        }


    }

    @Override
    public void addPersonDetail(BaseData data) {

    }

    @Override
    public void addPersonComment(BaseData data) {

    }

    @Override
    public void loadFailed(String msg) {
        Logger.e("TAG","主要作品加载失败====="+msg);
    }

    class MainMovieAdapter extends CommonBaseAdapter<PersonMovieJson>{

        private Context mContext;
        public MainMovieAdapter(Context context, List<PersonMovieJson> datas, boolean isOpenLoadMore) {
            super(context, datas, isOpenLoadMore);
            mContext = context;
        }

        @Override
        protected void convert(ViewHolder holder, PersonMovieJson data, int position) {

            Imager.load(getActivity(),data.getImage(),((ImageView) holder.getView(R.id.image)));
            holder.setText(R.id.tv_movie_name,data.getName());
            holder.setTextColor(R.id.tv_movie_year, ContextCompat.getColor(mContext,R.color.colorTitle3));
            holder.setText(R.id.tv_movie_year,"("+data.getYear()+")");
        }

        @Override
        protected int getItemLayoutId() {
            return R.layout.activity_news_detail_item_relations_info_movie;
        }
    }
}
