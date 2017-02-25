package com.chailijun.baseadapter.base;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chailijun.baseadapter.Util;
import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.interfaces.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * author： Chailijun
 * date  ： 2016/11/8 16:27
 * e-mail： 1499505466@qq.com
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_COMMON_VIEW = 100001;//普通类型 Item
    public static final int TYPE_FOOTER_VIEW = 100002;//footer类型 Item
    public static final int TYPE_EMPTY_VIEW = 100003;//empty view，即初始化加载时的提示View

    private OnLoadMoreListener mLoadMoreListener;

    protected Context mContext;
    protected List<T> mDatas;
    private boolean mOpenLoadMore;//是否开启加载更多
    private boolean isAutoLoadMore = true;//是否自动加载，当数据不满一屏幕会自动加载

    private View mLoadingView;
    private View mLoadFailedView;
    private View mLoadEndView;
    private int mInitLoadingViewId;
    private int mInitLoadFailedViewId;
    private View mInitLoadingView;
    private View mInitLoadFailedView;
//    private View mEmpty;
//    private View mEmptyView;
    private RelativeLayout mEmptyLayout;
    private RelativeLayout mFooterLayout;//footer view

    protected abstract int getViewType(int position, T data);

    public BaseAdapter(Context context, List<T> datas, boolean isOpenLoadMore) {
        mContext = context;
        mDatas = datas == null ? new ArrayList<T>() : datas;
        mOpenLoadMore = isOpenLoadMore;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_FOOTER_VIEW:
                if (mFooterLayout == null) {
                    mFooterLayout = new RelativeLayout(mContext);
                }
                viewHolder = ViewHolder.create(mContext,mFooterLayout);
                break;
            case TYPE_EMPTY_VIEW:
                if (mEmptyLayout == null) {
                    mEmptyLayout = new RelativeLayout(mContext);
                    mEmptyLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                }
                viewHolder = ViewHolder.create(mContext,mEmptyLayout);
                break;
        }
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        if (mDatas.isEmpty() && mEmptyLayout != null) {
            return 1;
        }
        return mDatas.size() + getFooterViewCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas.isEmpty() && mEmptyLayout != null) {
            return TYPE_EMPTY_VIEW;
        }

        if (isFooterView(position)) {
            return TYPE_FOOTER_VIEW;
        }

        return getViewType(position, mDatas.get(position));
    }

    /**
     * 根据position得到data
     *
     * @param position
     * @return
     */
    public T getItem(int position) {
        if (mDatas.isEmpty()) {
            return null;
        }
        return mDatas.get(position);
    }

    /**
     * 是否是FooterView
     *
     * @param position
     * @return
     */
    public boolean isFooterView(int position) {
        return mOpenLoadMore && position >= getItemCount() - 1;
    }

    protected boolean isCommonItemView(int viewType) {
        return viewType != TYPE_EMPTY_VIEW && viewType != TYPE_FOOTER_VIEW;
    }

    /**
     * StaggeredGridLayoutManager模式时，FooterView可占据一行
     *
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (isFooterView(holder.getLayoutPosition())) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }

    /**
     * GridLayoutManager模式时， FooterView可占据一行，判断RecyclerView是否到达底部
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) layoutManager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (isFooterView(position)) {
                        return gridManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
        startLoadMore(recyclerView, layoutManager);
    }


    /**
     * 判断列表是否滑动到底部
     *
     * @param recyclerView
     * @param layoutManager
     */
    private void startLoadMore(RecyclerView recyclerView, final RecyclerView.LayoutManager layoutManager) {
        if (!mOpenLoadMore || mLoadMoreListener == null) {
            return;
        }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    /*if (!isAutoLoadMore && findLastVisibleItemPosition(layoutManager) + 1 == getItemCount()) {
                        scrollLoadMore();
                    }*/

                    //2016/12/14 修改
                    if (!isAutoLoadMore && isSlideToBottom(recyclerView)){
                        scrollLoadMore();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                /*if (isAutoLoadMore && findLastVisibleItemPosition(layoutManager) + 1 == getItemCount()) {
                    scrollLoadMore();
                } else if (isAutoLoadMore) {
                    isAutoLoadMore = false;
                }*/

                //2016/12/14 修改
                if (isAutoLoadMore && isSlideToBottom(recyclerView)){
                    scrollLoadMore();
                }else if (isAutoLoadMore) {
                    isAutoLoadMore = false;
                }
            }
        });
    }

    /**
     * 是否滑到底部
     * computeVerticalScrollExtent()：是当前屏幕显示的区域高度
     * computeVerticalScrollOffset()： 是当前屏幕之前滑过的距离
     * computeVerticalScrollRange()：是整个View控件的高度
     * 参考：http://www.jianshu.com/p/c138055af5d2
     * 2016/12/14 修改
     * @param recyclerView
     * @return
     */
    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange()){
            Log.i("TAG","recyclerView滑到底部了");
            return true;
        }
        return false;
    }

    /**
     * 到达底部开始刷新
     */
    private void scrollLoadMore() {
        if (mFooterLayout.getChildAt(0) == mLoadingView) {
            if (mLoadMoreListener != null) {
                mLoadMoreListener.onLoadMore(false);
            }
        }
    }

    private int findLastVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null);
            return Util.findMax(lastVisibleItemPositions);
        }
        return -1;
    }

    /**
     * 清空footer view
     */
    private void removeFooterView() {
        mFooterLayout.removeAllViews();
    }

    /**
     * 添加新的footer view
     *
     * @param footerView
     */
    private void addFooterView(View footerView) {
        if (footerView == null) {
            return;
        }

        if (mFooterLayout == null) {
            mFooterLayout = new RelativeLayout(mContext);
        }
        removeFooterView();
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mFooterLayout.addView(footerView, params);
    }

    /**
     * 清空Empty view
     */
    private void removeEmptyView() {
        mEmptyLayout.removeAllViews();
    }

    private void addEmptyView(View emptyView){
        if (emptyView == null){
            return;
        }
        if (mEmptyLayout == null){
            mEmptyLayout = new RelativeLayout(mContext);
            mEmptyLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        removeEmptyView();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mEmptyLayout.addView(emptyView,params);
    }

    /**
     * 刷新加载更多的数据
     *
     * @param datas
     */
    public void setLoadMoreData(List<T> datas) {
        int size = mDatas.size();
        mDatas.addAll(datas);
        notifyItemInserted(size);
    }

    /**
     * 下拉刷新，得到的新数据查到原数据起始
     *
     * @param datas
     */
    public void setData(List<T> datas) {
        mDatas.addAll(0, datas);
        notifyDataSetChanged();
    }

    /**
     * 初次加载、或下拉刷新要替换全部旧数据时刷新数据
     *
     * @param datas
     */
    public void setNewData(List<T> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 清空数据
     */
    public void removeAll(){
        mDatas.clear();
        notifyDataSetChanged();
    }

    public void remove(int position) {
        mDatas.remove(position);
        notifyDataSetChanged();
    }

    /**
     * 初始化加载更多布局
     *
     * @param loadingView
     */
    public void setLoadMoreView(View loadingView) {
        mLoadingView = loadingView;
        addFooterView(mLoadingView);
    }

    public void setLoadMoreView(int loadingId) {
        setLoadMoreView(Util.inflate(mContext, loadingId));
    }

    /**
     * 初次加载中布局
     * @param initLoadingView
     */
    public void setInitLoadingView(View initLoadingView){
        mInitLoadingView = initLoadingView;
        addEmptyView(mInitLoadingView);
    }

    public void setInitLoadingView(int initLoadingViewId){
        setInitLoadingView(Util.inflate(mContext,initLoadingViewId));
    }

    public void setInitLoadFailedView(View initLoadFailedView){
        mInitLoadFailedView = initLoadFailedView;
        addEmptyView(initLoadFailedView);
    }

    public void setInitLoadFailedView(int initLoadFailedViewId){
        setInitLoadFailedView(Util.inflate(mContext,initLoadFailedViewId));
    }

    /**
     * 初始加载更多失败布局
     *
     * @param loadFailedView
     */
    public void setLoadFailedView(View loadFailedView) {
        if (loadFailedView == null) {
            return;
        }
        mLoadFailedView = loadFailedView;
        addFooterView(mLoadFailedView);
        mLoadFailedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFooterView(mLoadingView);
                if (mLoadMoreListener != null) {
                    mLoadMoreListener.onLoadMore(true);
                }
            }
        });
    }

    public void setLoadFailedView(int loadFailedId) {
        setLoadFailedView(Util.inflate(mContext, loadFailedId));
    }

    /**
     * 初始化全部加载更多完成布局
     *
     * @param loadEndView
     */
    public void setLoadEndView(View loadEndView) {
        mLoadEndView = loadEndView;
        addFooterView(mLoadEndView);
    }

    public void setLoadEndView(int loadEndId) {
        setLoadEndView(Util.inflate(mContext, loadEndId));
    }


    /**
     * 返回 footer view数量
     *
     * @return
     */
    public int getFooterViewCount() {
        return mOpenLoadMore && !mDatas.isEmpty() ? 1 : 0;
    }


    public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        mLoadMoreListener = loadMoreListener;
    }

    public List<T> getmDatas() {
        return mDatas;
    }

}