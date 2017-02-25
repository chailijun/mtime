package com.chailijun.mtime.find.toplist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chailijun.mtime.R;
import com.chailijun.mtime.activity.BoxOfficeActivity;
import com.chailijun.mtime.activity.TopListActivity;
import com.chailijun.mtime.base.BaseFragment;
import com.chailijun.mtime.data.find.Index;
import com.chailijun.mtime.data.find.toplist.TopListItem;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.Logger;
import com.chailijun.mtime.utils.SPUtil;

import java.util.List;

public class FindTopListFragment extends BaseFragment implements FindTopListContract.View, BaseQuickAdapter.RequestLoadMoreListener {

    public static final String ARGUMENT = "argument";

    private RecyclerView mRecyclerView;

    private FindTopListContract.Presenter mPresenter;

    private FindTopListAdapter mAdapter;

    //头部数据
    private Index.TopListBean topListBean;

    private int pageIndex = 1;

    private View mHeadview;

    public static FindTopListFragment newInstance(Index.TopListBean topListBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARGUMENT, topListBean);

        FindTopListFragment fragment = new FindTopListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        topListBean = (Index.TopListBean) bundle.getSerializable(ARGUMENT);
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

        //若界面重构，则数据重新从第一页开始请求
        pageIndex = 1;

        mRecyclerView = $(view,R.id.rv_find);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //头布局
        mHeadview = getActivity().getLayoutInflater()
                .inflate(R.layout.fragment_find_top_list_index, (ViewGroup) mRecyclerView.getParent(), false);
        initHeadData();

        mAdapter = new FindTopListAdapter(null);
        mAdapter.addHeaderView(mHeadview);
        mAdapter.setHeaderAndEmpty(false);
        mAdapter.setOnLoadMoreListener(this);

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                TopListItem listItem = (TopListItem) adapter.getData().get(position);

                enterTopList(listItem.getId());
            }
        });
    }



    @Override
    public void doBusiness(Context mContext) {
        mPresenter.loadTopList(pageIndex++);
    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public void showTopList(List<TopListItem> topLists) {
        Logger.d(TAG,"发现--排行榜 加载成功:"+topLists.size());

        mAdapter.addData(topLists);
        mAdapter.loadMoreComplete();
    }

    @Override
    public void showLoadingTopListError(String msg) {
        Logger.e(TAG,"发现--排行榜 加载失败:"+msg);

        mAdapter.loadMoreFail();
        pageIndex = pageIndex-- < 0 ? 1 : pageIndex;
    }

    @Override
    public void showNoTopList() {
        mAdapter.loadMoreEnd();
    }

    @Override
    public void setPresenter(FindTopListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadTopList(pageIndex++);
    }

    /**
     * 头部数据初始化
     */
    private void initHeadData() {
        if (topListBean == null){
            return;
        }

        ImageView headImg = $(mHeadview,R.id.iv_img);
        TextView headTitle = $(mHeadview,R.id.tv_title);

        Imager.load(getActivity(),topListBean.getImageUrl(), headImg);
        headTitle.setText(topListBean.getTitle());

        headImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterTopList(topListBean.getId());
            }
        });

        //时光Top100
        $(mHeadview,R.id.mtime_top100).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), TopListActivity.class);
                String areaID = SPUtil.get(Constants.MTIME_TOP100_AreaId, "2065");
                intent.putExtra(Constants.PAGESUB_AREA_ID, Integer.parseInt(areaID));
                startActivity(intent);
            }
        });

        //华语Top100
        $(mHeadview,R.id.Chinese_movie_top100).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), TopListActivity.class);
                String areaID = SPUtil.get(Constants.Chinese_Language_TOP100_AreaId, "2066");
                intent.putExtra(Constants.PAGESUB_AREA_ID, Integer.parseInt(areaID));
                startActivity(intent);
            }
        });

        //进入票房榜（全球）
        $(mHeadview,R.id.global_boxoffice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), BoxOfficeActivity.class);
                intent.putExtra(Constants.AREA,Constants.GLOBAL);
                startActivity(intent);
            }
        });
    }

    /**
     * 进入排行榜页面
     * @param topListID
     */
    private void enterTopList(int topListID) {
        Intent intent = new Intent(getActivity(), TopListActivity.class);
        intent.putExtra(Constants.TOPLIST_ID,topListID);
        startActivity(intent);
    }
}
