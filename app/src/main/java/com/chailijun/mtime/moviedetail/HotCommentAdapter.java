package com.chailijun.mtime.moviedetail;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chailijun.mtime.R;
import com.chailijun.mtime.data.moviedetail.HotComment;
import com.chailijun.mtime.utils.DensityUtil;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.Logger;
import com.chailijun.mtime.utils.TimeUtils;
import com.chailijun.mtime.utils.UrlUtils;

import java.util.List;

import static com.chailijun.mtime.utils.UrlUtils.addPrefix;

/**
 * 电影详细--短评adapter
 */

public class HotCommentAdapter extends BaseQuickAdapter<HotComment.MiniBean.ListBean,BaseViewHolder>{

    public HotCommentAdapter(List<HotComment.MiniBean.ListBean> data) {
        super(R.layout.fragment_movie_detail_base_info_item_hotcomment_item, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, HotComment.MiniBean.ListBean data) {
        Imager.loadCircleImage(mContext, addPrefix(data.getHeadImg()),
                (ImageView) holder.getView(R.id.user_headImg), DensityUtil.dp2px(20.0f));

        holder.setText(R.id.user_nickname,data.getNickname());
        holder.setText(R.id.user_rating,data.getRating()>0?"评"+data.getRating():"");
        holder.setText(R.id.user_content,data.getContent());
        holder.setText(R.id.user_commentDate, TimeUtils.getDistanceTime(data.getCommentDate()));
        holder.setText(R.id.user_reply,data.getReplyCount()+"");
        holder.setText(R.id.user_praiseCount,data.getPraiseCount()+"");
    }
}
