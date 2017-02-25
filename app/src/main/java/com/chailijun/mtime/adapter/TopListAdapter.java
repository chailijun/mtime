package com.chailijun.mtime.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.base.MultiBaseAdapter;
import com.chailijun.mtime.R;
import com.chailijun.mtime.customview.ExpandableTextView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.mvp.model.TopListDetails;
import com.chailijun.mtime.utils.Imager;

import java.text.DecimalFormat;
import java.util.List;


public class TopListAdapter extends MultiBaseAdapter<BaseData>{

    private static final int TYPE_HEAD = 100;
    private static final int TYPE_COMM = 101;

    private Context mContext;

    public TopListAdapter(Context context, List<BaseData> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);

        mContext = context;
    }

    @Override
    protected void convert(ViewHolder holder, BaseData data, int position, int viewType) {
        if (0 == position && viewType == TYPE_HEAD){
            if (data instanceof TopListDetails.TopListBean){
                TopListDetails.TopListBean topListBean = (TopListDetails.TopListBean) data;
                bindHeadData(holder,topListBean);
            }
        }else if (viewType == TYPE_COMM){
            if (data instanceof TopListDetails.MoviesBean){
                TopListDetails.MoviesBean moviesBean = (TopListDetails.MoviesBean) data;
                bindCommData(holder,moviesBean);
            }
        }
    }

    private void bindCommData(ViewHolder holder, TopListDetails.MoviesBean moviesBean) {
        Imager.load(mContext,moviesBean.getPosterUrl(), (ImageView) holder.getView(R.id.movie_img));

        if (moviesBean.getRankNum() == 1){
            holder.getView(R.id.rankNum).setBackgroundResource(R.drawable.shape_circle_orange);
        }else if(moviesBean.getRankNum() == 2){
            holder.getView(R.id.rankNum).setBackgroundResource(R.drawable.shape_circle_green);
        }else if(moviesBean.getRankNum() == 3){
            holder.getView(R.id.rankNum).setBackgroundResource(R.drawable.shape_circle_blue);
        }else {
            holder.getView(R.id.rankNum).setBackgroundResource(R.drawable.shape_circle_gray);
        }
        holder.setText(R.id.rankNum,new DecimalFormat("00").format(moviesBean.getRankNum()));

        holder.setText(R.id.movie_name,moviesBean.getName());

        if (moviesBean.getRating() > 0){
            holder.getView(R.id.rating).setVisibility(View.VISIBLE);
            holder.setText(R.id.rating,moviesBean.getRating()+"");
        }else {
            holder.getView(R.id.rating).setVisibility(View.GONE);
        }

        holder.setText(R.id.nameEn,moviesBean.getNameEn());
        holder.setText(R.id.tv_director,String.format(
                mContext.getString(R.string.director2),moviesBean.getDirector()));
        holder.setText(R.id.tv_actor,String.format(
                mContext.getString(R.string.main_actor),moviesBean.getActor()));
        holder.setText(R.id.releaseDate,String.format(
                mContext.getString(R.string.releaseDate),moviesBean.getReleaseDate(),
                moviesBean.getReleaseLocation()));

        ((ExpandableTextView)holder.getView(R.id.etv_remark)).setText(moviesBean.getRemark(),true);
    }

    private void bindHeadData(ViewHolder holder, TopListDetails.TopListBean topListBean) {

        holder.setText(R.id.tv_name,topListBean.getName());
        ((ExpandableTextView) holder.getView(R.id.etv_summary)).setText(topListBean.getSummary(),true);

        //设置广告内容
        setAdvData(holder);
    }

    private void setAdvData(ViewHolder holder) {

    }

    @Override
    protected int getItemLayoutId(int viewType) {
        if (viewType == TYPE_HEAD){
            return R.layout.activity_top_list_head;
        }
        return R.layout.activity_top_list_comm;
    }

    @Override
    protected int getViewType(int position, BaseData data) {
        if (0 == position){
            return TYPE_HEAD;
        }
        return TYPE_COMM;
    }
}
