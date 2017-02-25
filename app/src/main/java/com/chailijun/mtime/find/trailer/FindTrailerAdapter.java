package com.chailijun.mtime.find.trailer;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chailijun.mtime.R;
import com.chailijun.mtime.data.find.trailer.TrailerItem;
import com.chailijun.mtime.utils.Imager;

import java.util.List;

/**
 * author： Chailijun
 * date  ： 2017/1/15 17:21
 * e-mail： 1499505466@qq.com
 */

public class FindTrailerAdapter extends BaseQuickAdapter<TrailerItem,BaseViewHolder>{

    public FindTrailerAdapter(List<TrailerItem> data) {
        super(R.layout.fragment_find_trailer_comm, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, TrailerItem item) {
        Imager.load(mContext, item.getCoverImg(), (ImageView) holder.getView(R.id.iv_cover_img));
        holder.setText(R.id.tv_movie_name,item.getMovieName());
        holder.setText(R.id.tv_summary,item.getSummary());
    }
}
