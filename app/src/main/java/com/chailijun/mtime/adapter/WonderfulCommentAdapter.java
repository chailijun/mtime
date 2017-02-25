package com.chailijun.mtime.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.base.CommonBaseAdapter;
import com.chailijun.mtime.R;
import com.chailijun.mtime.mvp.model.NewsDetailFristJson.DataBean.NewsDetailBean.CommentsBean;
import com.chailijun.mtime.utils.TimeUtils;
import com.chailijun.mtime.utils.DensityUtil;
import com.chailijun.mtime.utils.Imager;

import java.util.List;


public class WonderfulCommentAdapter extends CommonBaseAdapter<CommentsBean> {

    public WonderfulCommentAdapter(Context context,
                                   List<CommentsBean> datas,
                                   boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, CommentsBean data,int position) {
        holder.setText(R.id.nickname,data.getNickname());
        holder.setText(R.id.content,data.getContent());
        holder.setText(R.id.timestamp, TimeUtils.getDistanceTime(data.getDate()));

        Imager.loadCircleImage(mContext,
                data.getUserImage(),
                (ImageView) holder.getView(R.id.userImage),
                DensityUtil.dp2px(20.0f));

    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.activity_news_detail_item_comments_item;
    }
}
