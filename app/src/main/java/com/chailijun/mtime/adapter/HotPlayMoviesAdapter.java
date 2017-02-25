package com.chailijun.mtime.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.base.CommonBaseAdapter;
import com.chailijun.mtime.R;
import com.chailijun.mtime.customview.WithTagImageView;
import com.chailijun.mtime.mvp.model.HotPlayMoviesJson;
import com.chailijun.mtime.utils.TimeUtils;
import com.chailijun.mtime.utils.Imager;

import java.util.List;


public class HotPlayMoviesAdapter extends CommonBaseAdapter<HotPlayMoviesJson.MoviesBean> {

    public HotPlayMoviesAdapter(Context context, List<HotPlayMoviesJson.MoviesBean> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, HotPlayMoviesJson.MoviesBean data,int position) {
        holder.getView(R.id.isNew).setVisibility(data.isIsNew()? View.VISIBLE:View.GONE);
        Imager.load(mContext,data.getImg(), (ImageView) holder.getView(R.id.iv_movie_img));

        //电影格式标签，如：IMAX3D、3D等
        ((WithTagImageView)holder.getView(R.id.iv_movie_img)).setRating(data.getRatingFinal()+"");
        ((WithTagImageView)holder.getView(R.id.iv_movie_img)).setIMAX3D(data.isIsIMAX3D());
        ((WithTagImageView)holder.getView(R.id.iv_movie_img)).setIMAX(data.isIsIMAX());
        ((WithTagImageView)holder.getView(R.id.iv_movie_img)).setHot(data.isIsHot());
        ((WithTagImageView)holder.getView(R.id.iv_movie_img)).setFilter(data.isIsFilter());
        ((WithTagImageView)holder.getView(R.id.iv_movie_img)).setDMAX(data.isIsDMAX());
        ((WithTagImageView)holder.getView(R.id.iv_movie_img)).setIs3D(data.isIs3D());

        //电影中文名
        holder.setText(R.id.tv_titleCn,data.getTitleCn());

        //预售、购票或查影讯
        if (data.getNearestShowtime().isIsTicket()){
            String showTime = data.getRYear() + "-" +data.getRMonth() + "-" +data.getRDay();
            boolean isAdvanceSale = TimeUtils.isAdvanceSale(showTime,data.getNearestShowtime().getNearestShowDay(),"");
            holder.setBgRes(R.id.btn_sale,isAdvanceSale? R.drawable.bt_solid_green_66: R.drawable.bt_solid_orange_66);
            holder.setText(R.id.btn_sale, isAdvanceSale?mContext.getString(R.string.advance_sale):mContext.getString(R.string.buy_ticket));
        }else {
            holder.setBgRes(R.id.btn_sale, R.drawable.bt_solid_green_66);
            holder.setText(R.id.btn_sale,mContext.getString(R.string.search_movie_info));
        }
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.fragment_hot_play_movies_item;
    }
}
