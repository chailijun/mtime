package com.chailijun.mtime.homepage;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chailijun.mtime.R;
import com.chailijun.mtime.customview.WithTagImageView;
import com.chailijun.mtime.data.home.HotPlayMovies;
import com.chailijun.mtime.cinema_movie.PayticketActivity;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.TimeUtils;

import java.util.List;


public class HotPlayAdapter extends BaseQuickAdapter<HotPlayMovies.MoviesBean,BaseViewHolder>{

    public HotPlayAdapter(List<HotPlayMovies.MoviesBean> data) {
        super(R.layout.fragment_hot_play_movies_item, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final HotPlayMovies.MoviesBean data) {
        holder.getView(R.id.isNew).setVisibility(data.isIsNew()? View.VISIBLE:View.GONE);
        Imager.load(mContext,data.getImg(), (ImageView) holder.getView(R.id.iv_movie_img));

        //电影格式标签，如：IMAX3D、3D等
        ((WithTagImageView)holder.getView(R.id.iv_movie_img)).setRating(data.getRatingFinal()+"");
        ((WithTagImageView)holder.getView(R.id.iv_movie_img)).setIMAX3D(data.isIsIMAX3D());
        ((WithTagImageView)holder.getView(R.id.iv_movie_img)).setIMAX(data.isIsIMAX());
//        ((WithTagImageView)holder.getView(R.id.iv_movie_img)).setHot(data.isIsHot());
//        ((WithTagImageView)holder.getView(R.id.iv_movie_img)).setFilter(data.isIsFilter());
//        ((WithTagImageView)holder.getView(R.id.iv_movie_img)).setDMAX(data.isIsDMAX());
        ((WithTagImageView)holder.getView(R.id.iv_movie_img)).setIs3D(data.isIs3D());

        //电影中文名
        holder.setText(R.id.tv_titleCn,data.getTitleCn());


        //预售、购票或查影讯
        if (data.getNearestShowtime().isIsTicket()){
            String showTime = data.getRYear() + "-" +data.getRMonth() + "-" +data.getRDay();
            boolean isAdvanceSale = TimeUtils.isAdvanceSale(showTime,data.getNearestShowtime().getNearestShowDay(),"");
            holder.setBackgroundRes(R.id.btn_sale,isAdvanceSale? R.drawable.bt_solid_green_66: R.drawable.bt_solid_orange_66);
            holder.setText(R.id.btn_sale, isAdvanceSale?mContext.getString(R.string.advance_sale):mContext.getString(R.string.buy_ticket));
        }else {
            holder.setBackgroundRes(R.id.btn_sale, R.drawable.bt_solid_green_66);
            holder.setText(R.id.btn_sale,mContext.getString(R.string.search_movie_info));
        }

        //添加点击,点击进入购票
        holder.addOnClickListener(R.id.btn_sale);
        holder.getView(R.id.btn_sale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,PayticketActivity.class);
                intent.putExtra(Constants.MOVIE_ID,data.getMovieId());
                mContext.startActivity(intent);
            }
        });
    }
}
