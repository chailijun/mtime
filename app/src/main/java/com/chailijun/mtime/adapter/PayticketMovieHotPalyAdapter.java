package com.chailijun.mtime.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.base.CommonBaseAdapter;
import com.chailijun.mtime.R;
import com.chailijun.mtime.mvp.model.movie.LocationMoviesJson;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.TimeUtils;

import java.util.List;


public class PayticketMovieHotPalyAdapter extends CommonBaseAdapter<LocationMoviesJson.MsBean> {

    private Context mContext;

    public PayticketMovieHotPalyAdapter(Context context, List<LocationMoviesJson.MsBean> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
        mContext = context;
    }

    @Override
    protected void convert(ViewHolder holder, LocationMoviesJson.MsBean data, int position) {
        Imager.load(mContext, data.getImg(), (ImageView) holder.getView(R.id.movie_img));
        holder.setText(R.id.movie_name, data.getT());

        //评分
        if (data.getR() > 0) {
            holder.getView(R.id.rating).setVisibility(View.VISIBLE);
            holder.setText(R.id.rating, data.getR() + "");
        } else {
            holder.getView(R.id.rating).setVisibility(View.GONE);
        }

        //特别点评
        if (!TextUtils.isEmpty(data.getCommonSpecial())) {
            holder.getView(R.id.want_seen).setVisibility(View.GONE);
            holder.getView(R.id.comment_special).setVisibility(View.VISIBLE);
            holder.setText(R.id.comment_special, data.getCommonSpecial());
        } else {
            holder.getView(R.id.comment_special).setVisibility(View.INVISIBLE);
            holder.getView(R.id.want_seen).setVisibility(View.VISIBLE);
            holder.setText(R.id.want_seen, String.format(mContext.getString(R.string.wantseen_count), data.getWantedCount()) +
                    "-" + data.getMovieType());
        }

        //上映日期
        if (data.getRd().length() == 8){//eg:20161102
//            String year = data.getRd().substring(0,4);
            String month = data.getRd().substring(4,6);
            String day = data.getRd().substring(6);
            holder.setText(R.id.show_date,String.format(mContext.getString(R.string.release_date),month,day));
        }

        //上映日期、影院数量和场次
        String showDate = TimeUtils.timeStamp2MonthAndDay(data.getNearestDay());
        holder.setText(R.id.show_cinema_time_count,String.format(mContext.getString(R.string.showdate_cinema_showtime_count),
                showDate,data.getNearestCinemaCount(),data.getNearestShowtimeCount()));

        //电影版本，3D、IMAX、中国巨幕
        if (data.getVersions().size() > 0){
            for (int i = 0; i < data.getVersions().size(); i++) {
                String version = data.getVersions().get(i).getVersion();
                if ("3D".equalsIgnoreCase(version)){
                    holder.getView(R.id.versions_3d).setVisibility(View.VISIBLE);
                }
                if ("IMAX".equalsIgnoreCase(version)){
                    holder.getView(R.id.versions_imax).setVisibility(View.VISIBLE);
                }
                if ("中国巨幕".equalsIgnoreCase(version)){
                    holder.getView(R.id.versions_huge_screen).setVisibility(View.VISIBLE);
                }
            }
        }

        //购票、预售、查影讯
        if (data.isIsTicket()){
            if (TimeUtils.isAdvanceSale(data.getRd(),data.getNearestDay(),"yyyyMMdd")){
                //预售
                holder.setBgRes(R.id.btn_sale,R.drawable.bt_solid_green_66);
                holder.setTextColor(R.id.btn_sale, Color.WHITE);
                holder.setText(R.id.btn_sale,mContext.getString(R.string.advance_sale));
            }else {
                //购票
                holder.setBgRes(R.id.btn_sale,R.drawable.bt_solid_orange_66);
                holder.setTextColor(R.id.btn_sale, Color.WHITE);
                holder.setText(R.id.btn_sale,mContext.getString(R.string.buy_ticket));
            }
        }else {
            //查影讯
            holder.setBgRes(R.id.btn_sale,R.drawable.bt_line_green_66);
            holder.setTextColor(R.id.btn_sale, ContextCompat.getColor(mContext,R.color.colorBGYingping));
            holder.setText(R.id.btn_sale,mContext.getString(R.string.search_movie_info));
        }

    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.fragment_payticket_movie_hot_play_item;
    }
}
