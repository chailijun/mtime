package com.chailijun.mtime.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.base.CommonBaseAdapter;
import com.chailijun.mtime.R;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.mvp.model.GetSearchItem;

import java.util.List;

/**
 * 电影搜索--分类筛选--“地区、类型、年代”的适配器
 */
public class MovieFilterAdapter extends CommonBaseAdapter<BaseData> {

    private Context mContext;

    /**被选中的item的位置*/
    private int mSelectedPos = -1;

    public MovieFilterAdapter(Context context, List<BaseData> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);

        mContext = context;
    }

    @Override
    protected void convert(ViewHolder holder, BaseData data, int position) {

        if (data instanceof GetSearchItem.DataBean.AreaBean) {
            GetSearchItem.DataBean.AreaBean areaBean = (GetSearchItem.DataBean.AreaBean) data;
            holder.setText(R.id.tv_area, areaBean.getName());

            if (areaBean.isSelected()) {
                mSelectedPos = position;
                holder.getView(R.id.tv_area).setBackgroundResource(R.drawable.choose_movie_bg_shape);
                holder.setTextColor(R.id.tv_area, ContextCompat.getColor(mContext, R.color.choose_movie_border));
            } else {
                holder.getView(R.id.tv_area).setBackgroundResource(android.R.color.transparent);
                holder.setTextColor(R.id.tv_area, ContextCompat.getColor(mContext, R.color.colorTitle2));
            }
        }
        if (data instanceof GetSearchItem.DataBean.GenreTypesBean) {
            GetSearchItem.DataBean.GenreTypesBean genreTypesBean = (GetSearchItem.DataBean.GenreTypesBean) data;
            holder.setText(R.id.tv_area, genreTypesBean.getName());

            if (genreTypesBean.isSelected()) {
                mSelectedPos = position;
                holder.getView(R.id.tv_area).setBackgroundResource(R.drawable.choose_movie_bg_shape);
                holder.setTextColor(R.id.tv_area, ContextCompat.getColor(mContext, R.color.choose_movie_border));
            } else {
                holder.getView(R.id.tv_area).setBackgroundResource(android.R.color.transparent);
                holder.setTextColor(R.id.tv_area, ContextCompat.getColor(mContext, R.color.colorTitle2));
            }
        }
        if (data instanceof GetSearchItem.DataBean.YearsBean) {
            GetSearchItem.DataBean.YearsBean yearsBean = (GetSearchItem.DataBean.YearsBean) data;
            holder.setText(R.id.tv_area, yearsBean.getName());

            if (yearsBean.isSelected()) {
                mSelectedPos = position;
                holder.getView(R.id.tv_area).setBackgroundResource(R.drawable.choose_movie_bg_shape);
                holder.setTextColor(R.id.tv_area, ContextCompat.getColor(mContext, R.color.choose_movie_border));
            } else {
                holder.getView(R.id.tv_area).setBackgroundResource(android.R.color.transparent);
                holder.setTextColor(R.id.tv_area, ContextCompat.getColor(mContext, R.color.colorTitle2));
            }
        }


    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.activity_search_movie_choose_item;
    }

    public int getSelectedPos() {
        return mSelectedPos;
    }

    public void setSelectedPos(int mSelectedPos) {
        this.mSelectedPos = mSelectedPos;
    }
}
