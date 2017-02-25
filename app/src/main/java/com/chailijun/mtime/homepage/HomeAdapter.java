package com.chailijun.mtime.homepage;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chailijun.mtime.R;
import com.chailijun.mtime.customview.WithTagImageView;
import com.chailijun.mtime.data.home.HomeListSuper;
import com.chailijun.mtime.data.home.list_item.FilmReview;
import com.chailijun.mtime.data.home.list_item.GuessMovie;
import com.chailijun.mtime.data.home.list_item.ImageList_51_1;
import com.chailijun.mtime.data.home.list_item.NewMovie;
import com.chailijun.mtime.data.home.list_item.ShortNews_51_0;
import com.chailijun.mtime.data.home.list_item.ShortNews_51_2;
import com.chailijun.mtime.data.home.list_item.TopList;
import com.chailijun.mtime.utils.DensityUtil;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.TimeUtils;

import java.util.List;


public class HomeAdapter extends BaseMultiItemQuickAdapter<HomeListSuper,BaseViewHolder>{


    public HomeAdapter(List<HomeListSuper> data) {
        super(data);
        addItemType(HomeListSuper.TYPE_LIST_IMAGES, R.layout.fragment_home_list_images);
        addItemType(HomeListSuper.TYPE_FILM_REVIEW,R.layout.fragment_home_film_review);
        addItemType(HomeListSuper.TYPE_SHORT_NEWS0,R.layout.fragment_home_news_with_video);
        addItemType(HomeListSuper.TYPE_SHORT_NEWS2,R.layout.fragment_home_news_with_video);
        addItemType(HomeListSuper.TYPE_LIST_URL,R.layout.fragment_home_list_url);
        addItemType(HomeListSuper.TYPE_NEW_MOVIE,R.layout.fragment_home_new_movie);
        addItemType(HomeListSuper.TYPE_TOP_LIST,R.layout.fragment_home_top_list);
        addItemType(HomeListSuper.TYPE_UNKNOW,R.layout.fragment_home_unknow);
//        addItemType(HomeListSuper.TYPE_LIST_VOTE,R.layout.fragment_home_list_vote);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeListSuper item) {
        switch (helper.getItemViewType()) {
            case HomeListSuper.TYPE_LIST_IMAGES:
                bindImages(helper,item);
                break;
            case HomeListSuper.TYPE_FILM_REVIEW:
                bindFilmReview(helper,item);
                break;
            case HomeListSuper.TYPE_SHORT_NEWS0:
            case HomeListSuper.TYPE_SHORT_NEWS2:
                bindShortNews(helper,item);
                break;
            case HomeListSuper.TYPE_LIST_URL:
                bindUrlData(helper,item);
                break;
            case HomeListSuper.TYPE_NEW_MOVIE:
                bindNewMovie(helper,item);
                break;
            case HomeListSuper.TYPE_TOP_LIST:
                bindTopList(helper,item);
                break;
            case HomeListSuper.TYPE_LIST_VOTE:
                break;
            default:
                break;
        }

    }

    /**
     * 图集类型数据
     * @param holder
     * @param superItem
     */
    private void bindImages(BaseViewHolder holder, HomeListSuper superItem) {
        if (superItem instanceof ImageList_51_1){
            ImageList_51_1 item = (ImageList_51_1) superItem;

            Imager.load(mContext,item.getImages().get(0).getUrl1(),(ImageView) holder.getView(R.id.img_1));
            Imager.load(mContext,item.getImages().get(1).getUrl1(),(ImageView) holder.getView(R.id.img_2));
            Imager.load(mContext,item.getImages().get(2).getUrl1(),(ImageView) holder.getView(R.id.img_3));

            holder.setText(R.id.tv_tag, item.getTag());
            holder.setText(R.id.tv_title, item.getTitle());
            holder.setText(R.id.tv_content, item.getContent());

            ((TextView) holder.getView(R.id.tv_publish_time))
                    .setText(String.format(mContext.getResources().getString(R.string.publish_time),
                            TimeUtils.getDistanceTime(item.getTime()),
                            item.getCommentCount()));
        }
    }

    /**
     * 影评类型数据
     * @param holder
     * @param superItem
     */
    private void bindFilmReview(BaseViewHolder holder, HomeListSuper superItem) {
        if (superItem instanceof FilmReview){
            FilmReview item = (FilmReview) superItem;

            holder.setText(R.id.tv_tag, item.getTag());
            holder.setText(R.id.tv_title, item.getTitle());
            holder.setText(R.id.tv_summary_info, item.getSummaryInfo());

            //评论的用户图像、用户名和电影名
            Imager.loadCircleImage(mContext,
                    item.getUserImage(),
                    (ImageView) holder.getView(R.id.user_image),
                    DensityUtil.dp2px(10.0f));
            ((TextView) holder.getView(R.id.tv_user_name))
                    .setText(String.format(mContext.getResources().getString(R.string.user_name_comment), item.getNickname()));
            ((TextView) holder.getView(R.id.tv_comment_title))
                    .setText(String.format(mContext.getResources().getString(R.string.comment_title), item.getRelatedObj().getTitle()));

            //电影评分
            holder.setText(R.id.tv_rating, item.getRating() + "");
            holder.getView(R.id.tv_rating)
                    .setVisibility(((String) item.getRating()).equals("0.0") ?
                            View.GONE : View.VISIBLE);

            Imager.load(mContext,
                    item.getRelatedObj().getImage(),
                    (ImageView) holder.getView(R.id.image));
        }

    }

    private void bindShortNews(BaseViewHolder holder, HomeListSuper superItem) {

        //有视频标识
        if (superItem instanceof ShortNews_51_2){
            ShortNews_51_2 item = (ShortNews_51_2) superItem;

            holder.setText(R.id.tv_tag, item.getTag());
            holder.setText(R.id.tv_title, item.getTitle());
            if (item.getDataType() == 1) {
                holder.setText(R.id.tv_titlesmall, item.getTitlesmall());
            } else {
                holder.setText(R.id.tv_titlesmall, item.getContent());
            }
            Imager.load(mContext, item.getImg1(), (ImageView) holder.getView(R.id.image));
            holder.getView(R.id.iv_is_movie)
                    .setVisibility(item.getDataType() == 2 ? View.VISIBLE : View.GONE);

            //publishTime and commentCount
            ((TextView) holder.getView(R.id.tv_publish_time))
                    .setText(String.format(mContext.getResources().getString(R.string.publish_time),
                            TimeUtils.getDistanceTime(item.getTime()),
                            item.getCommentCount()));
        }
        if (superItem instanceof ShortNews_51_0){
            ShortNews_51_0 item = (ShortNews_51_0) superItem;

            holder.setText(R.id.tv_tag, item.getTag());
            holder.setText(R.id.tv_title, item.getTitle());
            if (item.getDataType() == 1) {
                holder.setText(R.id.tv_titlesmall, item.getTitlesmall());
            } else {
                holder.setText(R.id.tv_titlesmall, item.getContent());
            }
            Imager.load(mContext, item.getImg1(), (ImageView) holder.getView(R.id.image));
            holder.getView(R.id.iv_is_movie)
                    .setVisibility(item.getDataType() == 2 ? View.VISIBLE : View.GONE);

            //publishTime and commentCount
            ((TextView) holder.getView(R.id.tv_publish_time))
                    .setText(String.format(mContext.getResources().getString(R.string.publish_time),
                            TimeUtils.getDistanceTime(item.getTime()),
                            item.getCommentCount()));

        }
    }

    private void bindUrlData(BaseViewHolder holder, HomeListSuper superItem) {
        if (superItem instanceof GuessMovie){
            GuessMovie item = (GuessMovie) superItem;

            holder.setText(R.id.tv_tag, item.getTag());
            holder.setText(R.id.tv_title, item.getTitle());
            holder.setText(R.id.tv_memo, item.getMemo());
            Imager.load(mContext, item.getPic1Url(), (ImageView) holder.getView(R.id.image));
        }
    }

    private void bindNewMovie(BaseViewHolder holder, HomeListSuper superItem) {
        if (superItem instanceof NewMovie){
            NewMovie item = (NewMovie) superItem;

            holder.setText(R.id.tv_tag, item.getTag());
            holder.setText(R.id.tv_title, item.getTitleCn());
            holder.setText(R.id.tv_titlesmall, item.getTitleEn());
            holder.setText(R.id.tv_content, item.getContent());

            Imager.load(mContext, item.getImage(), (ImageView) holder.getView(R.id.image));
            ((WithTagImageView) holder.getView(R.id.image)).setRating(item.getRating() + "");
        }
    }

    private void bindTopList(BaseViewHolder holder, HomeListSuper superItem) {
        if (superItem instanceof TopList){
            TopList item = (TopList) superItem;

            holder.setText(R.id.tv_tag, item.getTag());
            holder.setText(R.id.tv_title, item.getTitle());
            holder.setText(R.id.tv_memo, item.getMemo());

            ((WithTagImageView) holder.getView(R.id.iv_movie1_img))
                    .setRating(item.getMovies().get(0).getRating() + "");
            ((WithTagImageView) holder.getView(R.id.iv_movie2_img))
                    .setRating(item.getMovies().get(1).getRating() + "");
            ((WithTagImageView) holder.getView(R.id.iv_movie3_img))
                    .setRating(item.getMovies().get(2).getRating() + "");

            holder.setText(R.id.tv_movie1_name,item.getMovies().get(0).getName());
            holder.setText(R.id.tv_movie2_name,item.getMovies().get(1).getName());
            holder.setText(R.id.tv_movie3_name,item.getMovies().get(2).getName());

            holder.setText(R.id.tv_movie1_decade,"(" + item.getMovies().get(0).getDecade() + ")");
            holder.setText(R.id.tv_movie2_decade,"(" + item.getMovies().get(1).getDecade() + ")");
            holder.setText(R.id.tv_movie3_decade,"(" + item.getMovies().get(2).getDecade() + ")");

            Imager.load(mContext,item.getMovies().get(0).getPosterUrl(),
                    (ImageView) holder.getView(R.id.iv_movie1_img));
            Imager.load(mContext,
                    item.getMovies().get(1).getPosterUrl(),
                    (ImageView) holder.getView(R.id.iv_movie2_img));
            Imager.load(mContext,
                    item.getMovies().get(2).getPosterUrl(),
                    (ImageView) holder.getView(R.id.iv_movie3_img));
        }
    }
}
