package com.chailijun.mtime.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.base.MultiBaseAdapter;
import com.chailijun.mtime.R;
import com.chailijun.mtime.activity.BoxOfficeActivity;
import com.chailijun.mtime.activity.NewsDetailActivity;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.mvp.model.news.IndexInfo;
import com.chailijun.mtime.mvp.model.news.NewsList;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.TimeUtils;

import java.util.List;


@Deprecated
public class FindNewsAdapter extends MultiBaseAdapter<BaseData>{

    private static final int TYPE_INDEX = 1000;
    private static final int TYPE_ADV   = 1001;//广告条
    private static final int TYPE_0 = 1002;
    private static final int TYPE_1 = 1003;
    private static final int TYPE_2 = 1004;

    private Context mContext;

    public FindNewsAdapter(Context context, List<BaseData> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
        mContext = context;
    }

    @Override
    protected void convert(ViewHolder holder, BaseData data, int position, int viewType) {
        if (0 == position && viewType == TYPE_INDEX){
            if (data instanceof IndexInfo.NewsBean){
                IndexInfo.NewsBean newsBean = (IndexInfo.NewsBean) data;
                bindIndex(holder,newsBean);
            }
        }else if (viewType == TYPE_0 || viewType == TYPE_2){
            if (data instanceof NewsList.NewsListBean){
                NewsList.NewsListBean newsListBean = (NewsList.NewsListBean) data;
                bindType0AndType2Data(holder,newsListBean,viewType);
            }
        }else if(viewType == TYPE_1){
            if (data instanceof NewsList.NewsListBean){
                NewsList.NewsListBean newsListBean = (NewsList.NewsListBean) data;
                bindType1Data(holder,newsListBean);
            }
        }

    }

    private void bindType1Data(ViewHolder holder, NewsList.NewsListBean newsListBean) {

        holder.setText(R.id.tv_title,newsListBean.getTitle());

        if (newsListBean.getImages() != null && newsListBean.getImages().size() >= 3){
            Imager.load(mContext,
                    newsListBean.getImages().get(0).getUrl1(),
                    (ImageView) holder.getView(R.id.image1));
            Imager.load(mContext,
                    newsListBean.getImages().get(1).getUrl1(),
                    (ImageView) holder.getView(R.id.image2));
            Imager.load(mContext,
                    newsListBean.getImages().get(2).getUrl1(),
                    (ImageView) holder.getView(R.id.image3));
        }else {
            Log.i(FindNewsAdapter.class.getSimpleName(),
                    "the NewsListData's ImagesBean size has error!");
        }

        holder.setText(R.id.tv_publish_time,
                TimeUtils.getDistanceTime(newsListBean.getPublishTime()) + "  评论" +
                        newsListBean.getCommentCount());
    }

    private void bindType0AndType2Data(ViewHolder holder,
                                       NewsList.NewsListBean newsListBean,
                                       int viewType) {
        if (!TextUtils.isEmpty(newsListBean.getTag()) && !newsListBean.getTag().equals("无")){
            holder.getView(R.id.tv_tag).setVisibility(View.VISIBLE);
            holder.setText(R.id.tv_tag,newsListBean.getTag());
        }else {
            holder.getView(R.id.tv_tag).setVisibility(View.GONE);
        }

        if (viewType == TYPE_2){
            holder.getView(R.id.is_movie).setVisibility(View.VISIBLE);
        }

        holder.setText(R.id.tv_title,newsListBean.getTitle());
        holder.setText(R.id.tv_title2,newsListBean.getTitle2());
        holder.setText(R.id.tv_publish_time,
                TimeUtils.getDistanceTime(newsListBean.getPublishTime()) + "  评论" +
                newsListBean.getCommentCount());

        Imager.load(mContext,newsListBean.getImage(), (ImageView) holder.getView(R.id.iv_img));
    }

    /**
     * 设置头布局信息
     * @param holder
     * @param newsBean
     */
    private void bindIndex(ViewHolder holder, final IndexInfo.NewsBean newsBean) {
        Imager.load(mContext,newsBean.getImageUrl(), (ImageView) holder.getView(R.id.iv_img));
        holder.setText(R.id.tv_title,newsBean.getTitle());

        //index部分的点击事件
        holder.setOnClickListener(R.id.iv_img, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, NewsDetailActivity.class);
                intent.putExtra(Constants.NEWS_ID,newsBean.getNewsID());
                mContext.startActivity(intent);
            }
        });

        //进入票房榜（内地）
        holder.setOnClickListener(R.id.mainland_boxoffice, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, BoxOfficeActivity.class);
                intent.putExtra(Constants.AREA,Constants.MAINLAND);
                mContext.startActivity(intent);
            }
        });

        //进入票房榜（全球）
        holder.setOnClickListener(R.id.gloable_boxoffice, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, BoxOfficeActivity.class);
                intent.putExtra(Constants.AREA,Constants.GLOBAL);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        switch (viewType){
            case TYPE_INDEX:
                return R.layout.fragment_find_news_index;
            case TYPE_ADV:
                return R.layout.fragment_find_news_adv;
            case TYPE_0:
                return R.layout.fragment_find_news_type0;
            case TYPE_1:
                return R.layout.fragment_find_news_type1;
            case TYPE_2:
                return R.layout.fragment_find_news_type0;
            default:
                try {
                    throw new Exception("the viewType of FindNewsAdapter error!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return -1;
    }

    @Override
    protected int getViewType(int position, BaseData data) {
        if (0 == position){
            return TYPE_INDEX;
        }else {
            if (data.getItemType() == TYPE_ADV){
                return TYPE_ADV;
            }
            if (data instanceof NewsList.NewsListBean){
                NewsList.NewsListBean newsListBean = (NewsList.NewsListBean) data;
                if (newsListBean.getType() == 0){
                    return TYPE_0;
                }else if(newsListBean.getType() == 1){
                    return TYPE_1;
                }else if(newsListBean.getType() == 2){
                    return TYPE_2;
                }
            }
        }
        return -1;
    }
}
