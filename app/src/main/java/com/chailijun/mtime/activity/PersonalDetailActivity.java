package com.chailijun.mtime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chailijun.baseadapter.interfaces.OnLoadMoreListener;
import com.chailijun.mtime.R;
import com.chailijun.mtime.adapter.PersonalDetailAdapter;
import com.chailijun.mtime.mvp.interf.IPersonalDetailPresenter;
import com.chailijun.mtime.mvp.interf.IPersonalDetailView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.mvp.model.movie.PersonCommentJson;
import com.chailijun.mtime.mvp.model.movie.PersonDetailJson;
import com.chailijun.mtime.mvp.model.movie.PersonMovieJson;
import com.chailijun.mtime.mvp.presenter.PersonalDetailPresenter;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PersonalDetailActivity extends BaseActivity implements IPersonalDetailView<BaseData> {

    @BindView(R.id.recyclerview_actor)
    RecyclerView recyclerView;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.share)
    ImageView share;
    @BindView(R.id.favorite)
    ImageView favorite;
    @BindView(R.id.head_title)
    TextView headTitle;

    private int cityId;
    private int personId;
    private PersonalDetailAdapter mAdapter;
    private IPersonalDetailPresenter presenter;
    private View initLoadFailedView;
    private int pageIndex = 1;


    @Override
    protected int getLayoutId() {

        return R.layout.activity_actor_detail;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        getData();
        mAdapter = new PersonalDetailAdapter(this, null, true,getSupportFragmentManager(),personId);
        //设置第一次加载中的布局
        mAdapter.setInitLoadingView(R.layout.loading);
        //初始化第一次加载失败的布局
        initLoadFailedView = LayoutInflater.from(this).inflate(R.layout.loading_failed, (ViewGroup) recyclerView.getParent(), false);
        initLoadFailedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRefresh();
            }
        });
        //初始化加载更多布局
        mAdapter.setLoadMoreView(R.layout.loading_more);
        //设置加载更多触发的事件监听
        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                loadMore();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        requestData();
    }

    @Override
    protected void onDestroy() {
        presenter.unsubcrible();
        mAdapter.releaseWebView();
        super.onDestroy();
    }

    private void loadMore() {
        presenter.getPersonComment(personId, pageIndex++);
    }

    private void onRefresh() {
        mAdapter.setInitLoadingView(R.layout.loading);
//        presenter.getVideoList(movieId,1);
    }

    private void requestData() {
        presenter = new PersonalDetailPresenter(this);
        if (personId != 0 && cityId != 0) {
            presenter.getPersonDetail(personId, (int)cityId);
        }
    }

    private void getData() {
        Intent intent = getIntent();
        personId = intent.getIntExtra(Constants.PERSON_ID, 0);

        cityId = SPUtil.getInt(Constants.LOCATION_ID);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getData();
    }

    @Override
    public void addPersonDetail(BaseData data) {
        if (data instanceof PersonDetailJson) {
            PersonDetailJson personDetail = (PersonDetailJson) data;
            List<BaseData> datas = new ArrayList<>();
            datas.add(personDetail);
            mAdapter.setNewData(datas);

            headTitle.setText(personDetail.getData().getBackground().getNameCn());
        }
    }

    @Override
    public void addPersonMovie(List<PersonMovieJson> datas) {

    }

    @Override
    public void addPersonComment(BaseData data) {
        if (data instanceof PersonCommentJson) {
            PersonCommentJson personComment = (PersonCommentJson) data;
            List<PersonCommentJson.ListBean> list = personComment.getList();
            if (list.size() > 0){
                List<BaseData> datas = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    PersonCommentJson.ListBean listBean = list.get(i);
                    listBean.setCount(personComment.getCount());

                    datas.add(listBean);
                }
                mAdapter.setLoadMoreData(datas);
            }

        }
    }

    @Override
    public void loadFailed(String msg) {

    }


    @OnClick({R.id.back, R.id.share, R.id.favorite})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.share:
                break;
            case R.id.favorite:
                break;
        }
    }
}
