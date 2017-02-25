package com.chailijun.mtime.find.review;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chailijun.mtime.R;
import com.chailijun.mtime.data.find.review.Review;
import com.chailijun.mtime.data.find.trailer.TrailerItem;
import com.chailijun.mtime.utils.DensityUtil;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.StringUtils;

import java.util.List;


public class FindReviewAdapter extends BaseQuickAdapter<Review,BaseViewHolder>{

    public FindReviewAdapter(List<Review> data) {
        super(R.layout.fragment_find_review_comm, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, Review review) {
        Imager.load(mContext, review.getRelatedObj().getImage(),
                (ImageView) holder.getView(R.id.img));
        Imager.loadCircleImage(mContext, review.getUserImage(),
                (ImageView) holder.getView(R.id.user_image),
                DensityUtil.dp2px(20.0f));

        holder.setText(R.id.tv_title, review.getTitle().trim());
        holder.setText(R.id.tv_summary, StringUtils.myTrim(review.getSummary()));
        holder.setText(R.id.tv_user_name, review.getNickname() + "-评");
        holder.setText(R.id.tv_comment_title,
                String.format(mContext.getString(R.string.comment_title),
                        review.getRelatedObj().getTitle()));

        String rating = review.getRelatedObj().getRating();
        if (!TextUtils.isEmpty(rating) &&
                !rating.equals("0.0") &&
                !rating.equals("0") &&
                !rating.equals("-1") &&
                !rating.equals("-1.0")){
            holder.getView(R.id.tv_rating).setVisibility(View.VISIBLE);
            holder.setText(R.id.tv_rating,rating);
        }else {
            holder.getView(R.id.tv_rating).setVisibility(View.GONE);
        }
    }
}
