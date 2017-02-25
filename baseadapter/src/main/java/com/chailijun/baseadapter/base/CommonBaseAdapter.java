package com.chailijun.baseadapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.interfaces.OnItemClickListener;
import com.chailijun.baseadapter.interfaces.OnSwipeMenuClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * author： Chailijun
 * date  ： 2016/11/8 16:37
 * e-mail： 1499505466@qq.com
 */
public abstract class CommonBaseAdapter<T> extends BaseAdapter<T> {
    protected OnItemClickListener<T> mItemClickListener;


    protected ArrayList<Integer> mViewId = new ArrayList<>();
    protected ArrayList<OnSwipeMenuClickListener<T>> mListener = new ArrayList<>();

    public CommonBaseAdapter(Context context, List<T> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    protected abstract void convert(ViewHolder holder, T data,int position);

    protected abstract int getItemLayoutId();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isCommonItemView(viewType)) {
            return ViewHolder.create(mContext, getItemLayoutId(), parent);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        if (isCommonItemView(viewType)) {
            bindCommonItem(holder, position);
        }
    }

    private void bindCommonItem(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        convert(viewHolder, mDatas.get(position),position);

        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(viewHolder, mDatas.get(position), position);
                }
            }
        });

        if (viewHolder.getSwipeView() != null && viewHolder.getSwipeView() instanceof ViewGroup) {
            ViewGroup swipeView = (ViewGroup) viewHolder.getSwipeView();

            for (int i = 0; i < mViewId.size(); i++) {
                final int tempI = i;
                swipeView.findViewById(mViewId.get(i)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.get(tempI).onSwipMenuClick(viewHolder, mDatas.get(position), position);
                    }
                });
            }
        }
    }

    @Override
    protected int getViewType(int position, T data) {
        return TYPE_COMMON_VIEW;
    }

    public void setOnItemClickListener(OnItemClickListener<T> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void setOnSwipMenuClickListener(int viewId, OnSwipeMenuClickListener<T> swipeMenuClickListener) {
        mViewId.add(viewId);
        mListener.add(swipeMenuClickListener);
    }

}

