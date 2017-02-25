package com.chailijun.mtime.find.toplist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.interfaces.OnLoadMoreListener;
import com.chailijun.baseadapter.interfaces.OnMultiItemClickListeners;
import com.chailijun.mtime.R;
import com.chailijun.mtime.activity.TopListActivity;
import com.chailijun.mtime.adapter.FindTopListAdapter;
import com.chailijun.mtime.base.BaseFragment;
import com.chailijun.mtime.data.find.Index;
import com.chailijun.mtime.mvp.interf.ITopListPresenter;
import com.chailijun.mtime.mvp.interf.ITopListView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.mvp.model.TopListFixedNew;
import com.chailijun.mtime.mvp.model.TopListOfAll;
import com.chailijun.mtime.mvp.model.news.IndexInfo;
import com.chailijun.mtime.mvp.presenter.TopListPresenter;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class FindTopListFragment1 extends BaseFragment implements ITopListView<BaseData> {


    private static final String TOPLIST = "toplist";

    RecyclerView mRecyclerView;

    private ITopListPresenter mPresenter;

    private IndexInfo.TopListBean mTopListIndex;
    private FindTopListAdapter mAdapter;

    private int pageIndex = 1;
    private boolean isAddedIndex;
    private View initLoadFailedView;

    public static FindTopListFragment1 newInstance(Index.TopListBean topListBean) {
        FindTopListFragment1 fragment = new FindTopListFragment1();
        Bundle args = new Bundle();
        args.putSerializable(TOPLIST, topListBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mTopListIndex = (IndexInfo.TopListBean) arguments.getSerializable(TOPLIST);

        mPresenter = new TopListPresenter(this);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_find_recyclerview;
    }

    @Override
    public void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_find);

        mAdapter = new FindTopListAdapter(getActivity(), null, true);

        mAdapter.setInitLoadingView(R.layout.loading);
        mAdapter.setLoadMoreView(R.layout.loading_more);
        initLoadFailedView = LayoutInflater.from(getActivity())
                .inflate(R.layout.loading_failed, (ViewGroup) mRecyclerView.getParent(), false);
        initLoadFailedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRefresh();
            }
        });

        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                loadMore();
            }
        });

        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnMultiItemClickListener(new OnMultiItemClickListeners<BaseData>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, BaseData data, int position, int viewType) {
                if (position != 0 &&
                        viewType == FindTopListAdapter.TYPE_COMM &&
                        data instanceof TopListOfAll.TopListsBean){
                    TopListOfAll.TopListsBean topListsBean = (TopListOfAll.TopListsBean) data;

                    Intent intent = new Intent(getActivity(), TopListActivity.class);
                    intent.putExtra(Constants.TOPLIST_ID,topListsBean.getId());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        mPresenter.getTopListFixedNew();
    }

    @Override
    public void widgetClick(View v) {

    }

    private void onRefresh() {
        mAdapter.setInitLoadingView(R.layout.loading);
        mPresenter.getTopList(pageIndex++);
    }

    private void loadMore() {
        mPresenter.getTopList(pageIndex++);
    }


    @Override
    public void addTopListFixedNew(BaseData data) {
        if (data instanceof TopListFixedNew){
            TopListFixedNew topListFixedNew = (TopListFixedNew) data;
            //保存pageSubAreaId
            if (topListFixedNew.getTopList() != null && topListFixedNew.getTopList().size() >= 2){
                //保存时光Top100 pageSubAreaId
                SPUtil.save(Constants.MTIME_TOP100_AreaId,
                        topListFixedNew.getTopList().get(0).getPageSubAreaId());
                //保存华语Top100 pageSubAreaId
                SPUtil.save(Constants.Chinese_Language_TOP100_AreaId,
                        topListFixedNew.getTopList().get(1).getPageSubAreaId());
            }
        }
    }

    @Override
    public void addTopList(BaseData data) {
        if (data instanceof TopListOfAll) {

            if (mTopListIndex != null && !isAddedIndex){
                List<BaseData> list = new ArrayList<>();
                isAddedIndex = true;
                list.add(mTopListIndex);
                mAdapter.setNewData(list);
            }

            TopListOfAll topListOfAll = (TopListOfAll) data;
            if (topListOfAll.getTopLists() != null && topListOfAll.getTopLists().size() > 0) {
                List<BaseData> list = new ArrayList<>();
                for (int i = 0; i < topListOfAll.getTopLists().size(); i++) {
                    list.add(topListOfAll.getTopLists().get(i));
                }
                mAdapter.setLoadMoreData(list);

            } else {
                mAdapter.setLoadEndView(R.layout.load_end_no_more);
            }
        }

    }

    @Override
    public void loadFailed(String msg) {

        if (mAdapter.getItemCount() > 1){
            mAdapter.setLoadFailedView(R.layout.load_failed_layout);
        }else {
            mAdapter.setInitLoadFailedView(initLoadFailedView);
        }

        pageIndex = (pageIndex--) <= 0 ? 1 : pageIndex;
    }
}
