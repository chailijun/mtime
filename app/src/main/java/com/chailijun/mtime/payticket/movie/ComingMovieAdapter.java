package com.chailijun.mtime.payticket.movie;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chailijun.mtime.R;
import com.chailijun.mtime.activity.VideoListActivity;
import com.chailijun.mtime.data.payticket.MovieComingNew;
import com.chailijun.mtime.cinema_movie.PayticketActivity;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Imager;

import java.util.List;

/**
 * author： Chailijun
 * date  ： 2017/1/10 10:58
 * e-mail： 1499505466@qq.com
 */

public class ComingMovieAdapter extends BaseQuickAdapter<MovieComingNew.MoviecomingsBean,BaseViewHolder>{

    public ComingMovieAdapter(List<MovieComingNew.MoviecomingsBean> data) {
        super(R.layout.fragment_payticket_movie_coming_movie_item, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final MovieComingNew.MoviecomingsBean data) {
        holder.setText(R.id.showtime_day,String.format(mContext.getString(R.string.day),data.getRDay()));
        Imager.load(mContext,data.getImage(),(ImageView) holder.getView(R.id.movie_img));
        holder.setText(R.id.movie_name,data.getTitle());

        //“860人想观看-剧情/动作/悬疑/中国”
        String type = String.format(mContext.getString(R.string.wantseen_count2), data.getWantedCount(), data.getType() + " / " + data.getLocationName());
        ((TextView) holder.getView(R.id.want_seen)).setText(Html.fromHtml(type));

        //导演
        holder.setText(R.id.director,String.format(mContext.getString(R.string.director2),data.getDirector()));

        if (data.isIsTicket()){
            //超前预售
            holder.setBackgroundRes(R.id.btn_sale,R.drawable.bt_solid_green_66);
            holder.setText(R.id.btn_sale,mContext.getString(R.string.advance_sale2));
            holder.setTextColor(R.id.btn_sale, ContextCompat.getColor(mContext,R.color.white));
//            holder.addOnClickListener(R.id.btn_sale);
//            holder.getView(R.id.btn_sale).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(mContext, "超前预售", Toast.LENGTH_SHORT).show();
//                }
//            });

            //添加点击,点击进入购票
            holder.addOnClickListener(R.id.btn_sale);
            final int movieId = data.getId();
            holder.getView(R.id.btn_sale).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext,PayticketActivity.class);
                    intent.putExtra(Constants.MOVIE_ID,movieId);
                    mContext.startActivity(intent);
                }
            });
        }else {
            //上映提醒
            holder.setBackgroundRes(R.id.btn_sale,R.drawable.bt_line_green_66);
            holder.setText(R.id.btn_sale,mContext.getString(R.string.remind_release));
            holder.setTextColor(R.id.btn_sale, ContextCompat.getColor(mContext,R.color.colorBGYingping));
            holder.addOnClickListener(R.id.btn_sale);
            holder.getView(R.id.btn_sale).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "上映提醒", Toast.LENGTH_SHORT).show();
                }
            });
        }

        //预告片
        if (data.isIsVideo()){
            holder.getView(R.id.btn_trailer).setVisibility(View.VISIBLE);
            holder.addOnClickListener(R.id.btn_trailer);
            holder.getView(R.id.btn_trailer).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, VideoListActivity.class);
                    intent.putExtra(Constants.MOVIE_ID,data.getId());
                    mContext.startActivity(intent);
                }
            });
        }else {
            holder.getView(R.id.btn_trailer).setVisibility(View.GONE);
        }
    }
}
