package com.chailijun.mtime.mall.index;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chailijun.mtime.R;
import com.chailijun.mtime.data.mall.MallIndex;
import com.chailijun.mtime.mvp.model.mall.MallIndexJson;
import com.chailijun.mtime.utils.Imager;

import java.util.List;

import static android.R.attr.data;

public class CategoryGoodsAdapter extends BaseQuickAdapter<MallIndex.MallBean.CategoryBean,BaseViewHolder>{

    public CategoryGoodsAdapter(List<MallIndex.MallBean.CategoryBean> data) {
        super(R.layout.fragment_mall_category_item, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, MallIndex.MallBean.CategoryBean data) {
        holder.setBackgroundColor(R.id.tag, Color.parseColor(data.getColorValue()));
        holder.setText(R.id.category_name, data.getName());

        Imager.load(mContext, data.getImage(), (ImageView) holder.getView(R.id.category_img));

        //subList
        List<MallIndex.MallBean.CategoryBean.SubListBean> subList = data.getSubList();
        if (subList != null && subList.size() >= 3) {
            holder.getView(R.id.ll_subList).setVisibility(View.VISIBLE);
            Imager.load(mContext,
                    subList.get(0).getImage(),
                    (ImageView) holder.getView(R.id.goods1_img));
            holder.setText(R.id.goods1_title, subList.get(0).getTitle());

            Imager.load(mContext,
                    subList.get(1).getImage(),
                    (ImageView) holder.getView(R.id.goods2_img));
            holder.setText(R.id.goods2_title, subList.get(1).getTitle());

            Imager.load(mContext,
                    subList.get(2).getImage(),
                    (ImageView) holder.getView(R.id.goods3_img));
            holder.setText(R.id.goods4_title, subList.get(2).getTitle());
        } else {
            holder.getView(R.id.ll_subList).setVisibility(View.GONE);
        }
    }
}
