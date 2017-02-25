package com.chailijun.mtime.cinema_movie;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chailijun.mtime.R;
import com.chailijun.mtime.data.cinema_movie.MovieShowtimes;

import java.util.List;


public class CinemaAdapter extends BaseMultiItemQuickAdapter<MovieShowtimes.CinemaItem,BaseViewHolder> {

    public CinemaAdapter(List<MovieShowtimes.CinemaItem> data) {
        super(data);
        addItemType(MovieShowtimes.CinemaItem.TYPE_RECENTLY,R.layout.fragment_payticket_cinema_item);
        addItemType(MovieShowtimes.CinemaItem.TYPE_LIKE,R.layout.fragment_payticket_cinema_item);
        addItemType(MovieShowtimes.CinemaItem.TYPE_COMM,R.layout.fragment_payticket_cinema_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, MovieShowtimes.CinemaItem item) {

        if (helper.getItemViewType() == MovieShowtimes.CinemaItem.TYPE_RECENTLY){
            helper.getView(R.id.img_tag).setVisibility(View.VISIBLE);
            helper.getView(R.id.divider).setVisibility(View.VISIBLE);
            helper.setBackgroundRes(R.id.img_tag,R.drawable.cinema_list_nearest_icon);

        }else if(helper.getItemViewType() == MovieShowtimes.CinemaItem.TYPE_LIKE){
            helper.getView(R.id.img_tag).setVisibility(View.VISIBLE);
            helper.getView(R.id.divider).setVisibility(View.VISIBLE);
            helper.setBackgroundRes(R.id.img_tag,R.drawable.cinema_list_favorite_icon);

        }else {
            helper.getView(R.id.img_tag).setVisibility(View.GONE);
            helper.getView(R.id.divider).setVisibility(View.GONE);
        }


        helper.setText(R.id.cinema_name,item.getCn());
        helper.setText(R.id.cinema_address,item.getAddress());

        //影院离定位地点的距离
        helper.setText(R.id.cinema_distance,item.getDistanceStr());

        //imax
        if (item.getFeature().getHasIMAX() == 1){
            helper.getView(R.id.img_imax).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.img_imax).setVisibility(View.GONE);
        }

        //停车
        if (item.getFeature().getHasPark() == 1){
            helper.getView(R.id.img_parking).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.img_parking).setVisibility(View.GONE);
        }

        //wifi
        if (item.getFeature().getHasWifi() == 1){
            helper.getView(R.id.img_wififree).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.img_wififree).setVisibility(View.GONE);
        }

        helper.getView(R.id.btn_sale).setVisibility(View.VISIBLE);
        if (item.isIsTicket()){
            helper.setBackgroundRes(R.id.btn_sale,R.drawable.bt_solid_orange_66);
            helper.setText(R.id.btn_sale,mContext.getString(R.string.buy_ticket));

            //minPrice,设置字体不一样大小
            helper.getView(R.id.min_price).setVisibility(View.VISIBLE);
            SpannableString string = new SpannableString(
                    String.format(mContext.getString(R.string.min_price),item.getMinPrice()/100));
            string.setSpan(new AbsoluteSizeSpan(60),1,3,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            helper.setText(R.id.min_price,string);
        }else {
            helper.setBackgroundRes(R.id.btn_sale,R.drawable.bt_solid_green_66);
            helper.setText(R.id.btn_sale,mContext.getString(R.string.search_movie_info));

            helper.getView(R.id.min_price).setVisibility(View.GONE);
        }
    }
}
