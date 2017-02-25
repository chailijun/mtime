package com.chailijun.mtime.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.base.MultiBaseAdapter;
import com.chailijun.mtime.R;
import com.chailijun.mtime.customview.WithTagImageView;
import com.chailijun.mtime.mvp.model.NewsDetailJson;
import com.chailijun.mtime.utils.Imager;

import java.util.List;


public class RelationsInfoAdapter extends MultiBaseAdapter<NewsDetailJson.RelationsBean> {

    private static final int TYPE_MOVIE = 1;//电影类型
    private static final int TYPE_ACTOR = 2;//演员类型

    public RelationsInfoAdapter(Context context, List<NewsDetailJson.RelationsBean> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, NewsDetailJson.RelationsBean data,int position, int viewType) {
        if (viewType == TYPE_MOVIE){
            Imager.load(mContext,data.getImage(),(ImageView) holder.getView(R.id.image));
            ((WithTagImageView)holder.getView(R.id.image)).setRating(data.getRating() != -1?data.getRating()+"":"");
            holder.setText(R.id.tv_movie_name,data.getName());
            holder.setText(R.id.tv_movie_year,"（"+data.getYear()+"）");

        }else if(viewType == TYPE_ACTOR){
            Imager.load(mContext,data.getImage(),(ImageView) holder.getView(R.id.image));
            holder.setText(R.id.tv_actor,data.getName());
            holder.getView(R.id.tv_rating).setVisibility(data.getRating()>0? View.VISIBLE:View.GONE);
            holder.setText(R.id.tv_rating,(data.getRating()*10)+"%");
        }else if (viewType == -1){
            try {
                throw new Exception(RelationsInfoAdapter.class.getSimpleName()+":\"相关资料\"的类型错误，请检查！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected int getItemLayoutId(int viewType) {

        if (viewType == TYPE_MOVIE){
            return R.layout.activity_news_detail_item_relations_info_movie;
        }else if(viewType == TYPE_ACTOR){
            return R.layout.activity_news_detail_item_relations_info_actor;
        }else if (viewType == -1){
            try {
                throw new Exception(RelationsInfoAdapter.class.getSimpleName()+":\"相关资料\"有新类型了，需要添加相应的布局文件！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    @Override
    protected int getViewType(int position, NewsDetailJson.RelationsBean data) {
        if(data.getType() == TYPE_MOVIE){
            return TYPE_MOVIE;
        }else if (data.getType() == TYPE_ACTOR){
            return TYPE_ACTOR;
        }
        return -1;
    }
}
