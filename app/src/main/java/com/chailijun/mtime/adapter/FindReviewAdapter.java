package com.chailijun.mtime.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.base.MultiBaseAdapter;
import com.chailijun.mtime.R;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.data.find.review.Review;
import com.chailijun.mtime.mvp.model.news.IndexInfo;
import com.chailijun.mtime.utils.DensityUtil;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.StringUtils;

import java.util.List;


@Deprecated
public class FindReviewAdapter extends MultiBaseAdapter<BaseData> {

    private static final int TYPE_INDEX = 10000;
    private static final int TYPE_COMM = 10001;

    private Context mContext;

    public FindReviewAdapter(Context context, List<BaseData> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);

        mContext = context;
    }

    @Override
    protected void convert(ViewHolder holder, BaseData data, int position, int viewType) {
        if (0 == position && viewType == TYPE_INDEX) {
            if (data instanceof IndexInfo.ReviewBean) {
                IndexInfo.ReviewBean reviewBean = (IndexInfo.ReviewBean) data;
                bindReviewIndex(holder, reviewBean);
            }
        } else if (viewType == TYPE_COMM) {
            if (data instanceof Review) {
                Review review = (Review) data;
                bindReviewComm(holder, review);
            }
        }
    }

    private void bindReviewComm(ViewHolder holder, Review review) {

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

    private void bindReviewIndex(ViewHolder holder, IndexInfo.ReviewBean reviewBean) {

        Imager.load(mContext, reviewBean.getImageUrl(), (ImageView) holder.getView(R.id.iv_img));
        Imager.load(mContext, reviewBean.getPosterUrl(), (ImageView) holder.getView(R.id.movie_img));

        holder.setText(R.id.movieName, reviewBean.getMovieName().trim());
        holder.setText(R.id.tv_title, reviewBean.getTitle().trim());
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        if (viewType == TYPE_INDEX) {
            return R.layout.fragment_find_review_index;
        }
        return R.layout.fragment_find_review_comm;
    }

    @Override
    protected int getViewType(int position, BaseData data) {
        if (0 == position) {
            return TYPE_INDEX;
        }
        return TYPE_COMM;
    }
}
