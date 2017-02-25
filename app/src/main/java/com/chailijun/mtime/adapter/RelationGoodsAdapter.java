package com.chailijun.mtime.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.base.CommonBaseAdapter;
import com.chailijun.mtime.R;
import com.chailijun.mtime.mvp.model.GoodsListBean;
import com.chailijun.mtime.utils.Imager;

import java.util.List;


public class RelationGoodsAdapter extends CommonBaseAdapter<GoodsListBean> {

    public RelationGoodsAdapter(Context context, List<GoodsListBean> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, GoodsListBean data,int position) {
        Imager.load(mContext,data.getImage(), (ImageView) holder.getView(R.id.iv_goods_image));


    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.activity_news_detail_item_relations_goods_item;
    }
}
