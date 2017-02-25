package com.chailijun.mtime.mall;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chailijun.mtime.R;
import com.chailijun.mtime.data.mall.RecommendProducts;
import com.chailijun.mtime.utils.Imager;

import java.util.List;


public class MallAdapter extends BaseQuickAdapter<RecommendProducts.GoodsListBean,BaseViewHolder>{

    public MallAdapter(List<RecommendProducts.GoodsListBean> data) {
        super(R.layout.fragment_mall_recommend_products, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, RecommendProducts.GoodsListBean goodsListBean) {

        //图片url
        String imgUrl = goodsListBean.getImage();
        if(!goodsListBean.getImage().startsWith("http://") && goodsListBean.getImage().startsWith("//img")){
            imgUrl = "http:"+goodsListBean.getImage();
        }

        Imager.load(mContext, imgUrl, (ImageView) holder.getView(R.id.goods_img));
        holder.setText(R.id.goods_title, goodsListBean.getName());
        holder.setText(R.id.goods_price, String.format(mContext.getString(R.string.goods_price),
                (float) goodsListBean.getMinSalePrice() / 100.0 + ""));

        if (!TextUtils.isEmpty(goodsListBean.getIconText())) {
            holder.getView(R.id.goods_iconText).setVisibility(View.VISIBLE);
            holder.setBackgroundColor(R.id.goods_iconText, Color.parseColor(goodsListBean.getBackground()));
            holder.setText(R.id.goods_iconText, goodsListBean.getIconText());
        } else {
            holder.getView(R.id.goods_iconText).setVisibility(View.GONE);
        }
    }
}
