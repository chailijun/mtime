package com.chailijun.mtime.mall.index;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chailijun.mtime.R;
import com.chailijun.mtime.data.mall.MallIndex;
import com.chailijun.mtime.mvp.model.mall.MallIndexJson;
import com.chailijun.mtime.utils.Imager;

import java.util.List;

import static android.R.attr.data;


public class SpecialTopicListAdapter extends BaseQuickAdapter<MallIndex.SpecialTopicListBean,BaseViewHolder>{

    public SpecialTopicListAdapter(List<MallIndex.SpecialTopicListBean> data) {
        super(R.layout.fragment_mall_special_topic, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, MallIndex.SpecialTopicListBean item) {
        Imager.load(mContext,
                item.getSpecialTopicImg(),
                (ImageView) holder.getView(R.id.specialTopicImg));

        RecyclerView rv_relatedGoodsList = holder.getView(R.id.rv_relatedGoodsList);
        rv_relatedGoodsList.setHasFixedSize(true);
        RelatedGoodsListAdapter adapter = new RelatedGoodsListAdapter(item.getRelatedGoodsList());
        rv_relatedGoodsList.setLayoutManager(
                new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rv_relatedGoodsList.setAdapter(adapter);
    }

    class RelatedGoodsListAdapter extends
            BaseQuickAdapter<MallIndex.SpecialTopicListBean.RelatedGoodsListBean,BaseViewHolder>{

        public RelatedGoodsListAdapter(List<MallIndex.SpecialTopicListBean.RelatedGoodsListBean> data) {
            super(R.layout.fragment_mall_special_topic_item, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, MallIndex.SpecialTopicListBean.RelatedGoodsListBean data) {
            Imager.load(mContext, data.getImg(), (ImageView) holder.getView(R.id.goods_img));
            holder.setText(R.id.goods_name, data.getName());
            holder.setText(R.id.goods_price, data.getPrice());
        }
    }
}
