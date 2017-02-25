package com.chailijun.mtime.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.base.CommonBaseAdapter;
import com.chailijun.baseadapter.base.MultiBaseAdapter;
import com.chailijun.baseadapter.interfaces.OnItemClickListener;
import com.chailijun.mtime.R;
import com.chailijun.mtime.moviedetail.MovieDetailActivity;
import com.chailijun.mtime.activity.VideoListActivity;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.mvp.model.movie.MovieComingNewJson;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Imager;

import java.util.List;

/**
 * “即将上映”的Adapter
 */
@Deprecated
public class MovieComingsAdapter extends MultiBaseAdapter<BaseData> {

    private static final int TYPE_HEAD = 1;//头部
    private static final int TYPE_COMM = 2;

    private Context mContext;

    public MovieComingsAdapter(Context context, List<BaseData> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
        mContext = context;
    }

    @Override
    protected void convert(ViewHolder holder, BaseData baseData, int position, int viewType) {
        if (viewType == TYPE_HEAD){
            if (baseData.getObj() != null){
                List<MovieComingNewJson.AttentionBean> list = (List<MovieComingNewJson.AttentionBean>) baseData.getObj();
                int comingMovieCount = (int) baseData.getObj1();
                bindAttentionData(holder, list,comingMovieCount);
            }
        }else {
            if (baseData instanceof MovieComingNewJson.MoviecomingsBean){
                MovieComingNewJson.MoviecomingsBean data = (MovieComingNewJson.MoviecomingsBean) baseData;
                bindMovieComingsData(holder,data);
            }
        }
    }

    /**
     * 设置最受关注电影数据
     * @param holder
     * @param list
     */
    private void bindAttentionData(ViewHolder holder, List<MovieComingNewJson.AttentionBean> list,int comingMovieCount) {
        //即将上映电影总数
        holder.setText(R.id.comingMovieCount,String.format(mContext.getString(R.string.coming_movie_count),comingMovieCount));

        MostAttentionAdapter adapter = new MostAttentionAdapter(mContext,list,false);
        RecyclerView recyclerView = holder.getView(R.id.rv_attention);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener<MovieComingNewJson.AttentionBean>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, MovieComingNewJson.AttentionBean data, int position) {
                Intent intent = new Intent(mContext, MovieDetailActivity.class);
                intent.putExtra(Constants.MOVIE_ID,data.getId());
                mContext.startActivity(intent);
            }
        });
    }

    /**
     * 设置即将上映电影数据
     * @param holder
     * @param data
     */
    private void bindMovieComingsData(final ViewHolder holder, final MovieComingNewJson.MoviecomingsBean data) {
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
            holder.setBgRes(R.id.btn_sale,R.drawable.bt_solid_green_66);
            holder.setText(R.id.btn_sale,mContext.getString(R.string.advance_sale2));
            holder.setTextColor(R.id.btn_sale, ContextCompat.getColor(mContext,R.color.white));
            holder.getView(R.id.btn_sale).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "超前预售", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            //上映提醒
            holder.setBgRes(R.id.btn_sale,R.drawable.bt_line_green_66);
            holder.setText(R.id.btn_sale,mContext.getString(R.string.remind_release));
            holder.setTextColor(R.id.btn_sale, ContextCompat.getColor(mContext,R.color.colorBGYingping));
            holder.getView(R.id.btn_sale).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "上映提醒", Toast.LENGTH_SHORT).show();
                }
            });
        }

        //预告片
        if (data.isIsVideo()){
            holder.getView(R.id.btn_trailer).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, VideoListActivity.class);
                    intent.putExtra(Constants.MOVIE_ID,data.getId());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        if(viewType == TYPE_HEAD){
            return R.layout.fragment_payticket_movie_coming_movie_item_head;
        }
        return R.layout.fragment_payticket_movie_coming_movie_item;
    }

    @Override
    protected int getViewType(int position, BaseData data) {
        if (0 == position){
            return TYPE_HEAD;
        }
        return TYPE_COMM;
    }

    //“最受关注”的Adapter
    class MostAttentionAdapter extends CommonBaseAdapter<MovieComingNewJson.AttentionBean>{

        public MostAttentionAdapter(Context context, List<MovieComingNewJson.AttentionBean> datas, boolean isOpenLoadMore) {
            super(context, datas, isOpenLoadMore);
        }

        @Override
        protected void convert(ViewHolder holder, final MovieComingNewJson.AttentionBean data, int position) {
            holder.setText(R.id.show_date,String.format(mContext.getString(R.string.show_date),data.getRMonth(),data.getRDay()));
            Imager.load(mContext,data.getImage(),(ImageView) holder.getView(R.id.movie_img));
            holder.setText(R.id.movie_name,data.getTitle());

            //“860人想观看-剧情/动作/悬疑/中国”
            String type = String.format(mContext.getString(R.string.wantseen_count2), data.getWantedCount(), data.getType() + " / " + data.getLocationName());
            ((TextView) holder.getView(R.id.want_seen)).setText(Html.fromHtml(type));

            //导演、演员
            holder.setText(R.id.director,String.format(mContext.getString(R.string.director2),data.getDirector()));
            holder.setText(R.id.actor,String.format(mContext.getString(R.string.actor2),data.getActor1(),data.getActor2()));

            if (data.isIsTicket()){
                //超前预售
                holder.setBgRes(R.id.btn_sale,R.drawable.bt_solid_green_66);
                holder.setText(R.id.btn_sale,mContext.getString(R.string.advance_sale2));
                holder.setTextColor(R.id.btn_sale, ContextCompat.getColor(mContext,R.color.white));
                holder.getView(R.id.btn_sale).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext, "超前预售", Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                //上映提醒
                holder.setBgRes(R.id.btn_sale,R.drawable.bt_line_green_66);
                holder.setText(R.id.btn_sale,mContext.getString(R.string.remind_release));
                holder.setTextColor(R.id.btn_sale, ContextCompat.getColor(mContext,R.color.colorBGYingping));
                holder.getView(R.id.btn_sale).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext, "上映提醒", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            //预告片
            if (data.isIsVideo()){
                holder.getView(R.id.btn_trailer).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, VideoListActivity.class);
                        intent.putExtra(Constants.MOVIE_ID,data.getId());
                        mContext.startActivity(intent);
                    }
                });
            }
        }

        @Override
        protected int getItemLayoutId() {
            return R.layout.fragment_payticket_movie_coming_movie_most_attention_item;
        }
    }
}
