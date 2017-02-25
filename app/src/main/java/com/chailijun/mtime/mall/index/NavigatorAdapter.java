package com.chailijun.mtime.mall.index;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chailijun.mtime.R;
import com.chailijun.mtime.data.mall.MallIndex;
import com.chailijun.mtime.utils.Imager;

import java.util.List;

import static android.R.attr.data;

public class NavigatorAdapter extends BaseQuickAdapter<MallIndex.MallBean.NavigatorIconBean,BaseViewHolder>{

    public NavigatorAdapter(List<MallIndex.MallBean.NavigatorIconBean> data) {
        super(R.layout.fragment_mall_navigator_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MallIndex.MallBean.NavigatorIconBean item) {
        Imager.load(mContext, item.getImage(), (ImageView) helper.getView(R.id.navigator_img));
        helper.setText(R.id.navigator_iconTitle, item.getIconTitle());
    }
}
