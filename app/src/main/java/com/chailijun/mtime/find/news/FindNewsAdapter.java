package com.chailijun.mtime.find.news;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chailijun.mtime.R;
import com.chailijun.mtime.data.find.news.NewsItem;
import com.chailijun.mtime.data.find.trailer.TrailerItem;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.TimeUtils;

import java.util.List;

public class FindNewsAdapter extends BaseMultiItemQuickAdapter<NewsItem,BaseViewHolder>{


    public FindNewsAdapter(List<NewsItem> data) {
        super(data);
        addItemType(NewsItem.TYPE_0, R.layout.fragment_find_news_type0);
        addItemType(NewsItem.TYPE_1, R.layout.fragment_find_news_type1);
        addItemType(NewsItem.TYPE_2, R.layout.fragment_find_news_type0);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsItem item) {
        switch (helper.getItemViewType()) {
            case NewsItem.TYPE_0:
            case NewsItem.TYPE_2:
                bindType0AndType2Data(helper,item,helper.getItemViewType());
                break;
            case NewsItem.TYPE_1:
                bindType1Data(helper,item);
                break;
            default:
                break;
        }
    }

    private void bindType1Data(BaseViewHolder holder, NewsItem newsListBean) {
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
            Log.e(FindNewsAdapter.class.getSimpleName(),
                    "the NewsListData's ImagesBean size has error!");
        }

        holder.setText(R.id.tv_publish_time,
                TimeUtils.getDistanceTime(newsListBean.getPublishTime()) + "  评论" +
                        newsListBean.getCommentCount());
    }

    private void bindType0AndType2Data(BaseViewHolder holder, NewsItem newsListBean, int viewType) {
        if (!TextUtils.isEmpty(newsListBean.getTag()) && !newsListBean.getTag().equals("无")){
            holder.getView(R.id.tv_tag).setVisibility(View.VISIBLE);
            holder.setText(R.id.tv_tag,newsListBean.getTag());
        }else {
            holder.getView(R.id.tv_tag).setVisibility(View.GONE);
        }

        if (viewType == NewsItem.TYPE_2){
            holder.getView(R.id.is_movie).setVisibility(View.VISIBLE);
        }

        holder.setText(R.id.tv_title,newsListBean.getTitle());
        holder.setText(R.id.tv_title2,newsListBean.getTitle2());
        holder.setText(R.id.tv_publish_time,
                TimeUtils.getDistanceTime(newsListBean.getPublishTime()) + "  评论" +
                        newsListBean.getCommentCount());

        Imager.load(mContext,newsListBean.getImage(), (ImageView) holder.getView(R.id.iv_img));
    }
}