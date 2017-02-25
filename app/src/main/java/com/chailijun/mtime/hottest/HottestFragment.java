package com.chailijun.mtime.hottest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chailijun.mtime.R;
import com.chailijun.mtime.base.BaseFragment;
import com.chailijun.mtime.data.hottest.Hottest;
import com.chailijun.mtime.moviedetail.MovieDetailActivity;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.SPUtil;

import static com.chailijun.mtime.R.id.hottest;


public class HottestFragment extends BaseFragment implements HottestContract.View{

    private RecyclerView mRecylerView;

    private HottestContract.Presenter mPresenter;

    private HottestAdapter mAdapter;

    private int locationId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationId = SPUtil.getInt(Constants.LOCATION_ID);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_hottest;
    }

    @Override
    public void initView(View view) {
        mRecylerView = $(view,R.id.rv_hottest);
        mRecylerView.setHasFixedSize(true);
        mRecylerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new HottestAdapter(null);
        mRecylerView.setAdapter(mAdapter);

        mRecylerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Hottest.ListBean listBean = (Hottest.ListBean) adapter.getData().get(position);

                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                intent.putExtra(Constants.MOVIE_ID,listBean.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        if (locationId != 0){
            mPresenter.loadHotest(locationId,1);//默认只取第一页数据
        }
    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public void showHotest(Hottest hottest) {
        mAdapter.setNewData(hottest.getList());
    }

    @Override
    public void showLoadingHotestError(String msg) {

    }

    @Override
    public void showNoData() {

    }

    @Override
    public void setPresenter(HottestContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
