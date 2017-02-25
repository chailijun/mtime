package com.chailijun.mtime.moviedetail;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chailijun.mtime.R;

import java.util.List;

import static android.R.attr.data;

/**
 * 电影详细--专业解读
 * "媒体评论"、"制作发行"、"幕后制作"、"更多资料"等的adapter
 */

public class DataBankEntryAdapter extends BaseQuickAdapter<MovieDetailProReadingFragment.Bean,BaseViewHolder>{

    public DataBankEntryAdapter(List<MovieDetailProReadingFragment.Bean> data) {
        super(R.layout.fragment_movie_detail_pro_reading_other_item, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, MovieDetailProReadingFragment.Bean data) {
        holder.setBackgroundRes(R.id.iv_icon,data.getImageResId());
        holder.setText(R.id.tv_desc,data.getDesc());
        holder.setText(R.id.tv_desc2,data.getDesc2());
    }
}
