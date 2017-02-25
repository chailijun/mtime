package com.chailijun.mtime.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.base.CommonBaseAdapter;
import com.chailijun.mtime.R;
import com.chailijun.mtime.activity.NewsDetailActivity;
import com.chailijun.mtime.mvp.model.NewsDetailSecondJson.DataBean.NewsDetailBean.ReadsBean;
import com.chailijun.mtime.utils.Constants;

import java.util.List;

public class RelationReadsAdapter extends CommonBaseAdapter<ReadsBean> {
    public RelationReadsAdapter(Context context, List<ReadsBean> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, final ReadsBean data,int position) {
        holder.setText(R.id.title,data.getTitle());
        holder.setText(R.id.subTitle,data.getSubTitle());

        holder.getView(R.id.ll_relation_reads).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, NewsDetailActivity.class);
                intent.putExtra(Constants.NEWS_ID,data.getNewsId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.activity_news_detail_item_relations_reads_item;
    }
}
