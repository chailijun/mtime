package com.chailijun.mtime.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.base.CommonBaseAdapter;
import com.chailijun.mtime.R;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.mvp.model.TopListDetails;
import com.chailijun.mtime.cinema_movie.PayticketActivity;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Imager;

import java.util.List;


public class BoxOfficeAdapter extends CommonBaseAdapter<BaseData>{

    private Context mContext;

    public BoxOfficeAdapter(Context context, List<BaseData> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
        mContext = context;
    }

    @Override
    protected void convert(ViewHolder holder, BaseData data, int position) {
        if (data instanceof TopListDetails.MoviesBean){
            final TopListDetails.MoviesBean moviesBean = (TopListDetails.MoviesBean) data;

            Imager.load(mContext,moviesBean.getPosterUrl(), (ImageView) holder.getView(R.id.movie_img));

            holder.setText(R.id.rankNum,moviesBean.getRankNum()+"");
            if (moviesBean.getRankNum() == 1){//票房第一名
                holder.getView(R.id.rankNum).setBackgroundResource(R.drawable.shape_circle_orange);
            }else if(moviesBean.getRankNum() == 2){//票房第二名
                holder.getView(R.id.rankNum).setBackgroundResource(R.drawable.shape_circle_green);
            }else if(moviesBean.getRankNum() == 3){//票房第三名
                holder.getView(R.id.rankNum).setBackgroundResource(R.drawable.shape_circle_blue);
            }else {
                holder.getView(R.id.rankNum).setBackgroundResource(R.drawable.shape_circle_gray);
            }

            holder.setText(R.id.movie_name,moviesBean.getName());

            if (moviesBean.getRating() > 0){
                holder.getView(R.id.rating).setVisibility(View.VISIBLE);
                holder.setText(R.id.rating,moviesBean.getRating()+"");
            }else {
                holder.getView(R.id.rating).setVisibility(View.GONE);
            }

            holder.setText(R.id.nameEn,moviesBean.getNameEn());
            holder.setText(R.id.weekBoxOffice,moviesBean.getWeekBoxOffice().replace("\n"," "));
            holder.setText(R.id.totalBoxOffice,moviesBean.getTotalBoxOffice().replace("\n"," "));

            if (moviesBean.isIsTicket()){
                holder.getView(R.id.btn_sale).setVisibility(View.VISIBLE);
                holder.setOnClickListener(R.id.btn_sale, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext,PayticketActivity.class);
                        intent.putExtra(Constants.MOVIE_ID,moviesBean.getId());
                        mContext.startActivity(intent);
                    }
                });
            }else {
                holder.getView(R.id.btn_sale).setVisibility(View.GONE);
            }

        }
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.fragment_boxoffice_item;
    }
}
