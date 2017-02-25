package com.chailijun.mtime.moviedetail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chailijun.mtime.MtimeApp;
import com.chailijun.mtime.R;
import com.chailijun.mtime.activity.HotLongCommentsActivity;
import com.chailijun.mtime.activity.HotMovieCommentsActivity;
import com.chailijun.mtime.activity.MovieCreditsActivity;
import com.chailijun.mtime.activity.MovieImageAllActivity;
import com.chailijun.mtime.activity.PersonalDetailActivity;
import com.chailijun.mtime.activity.ReviewDetailActivity;
import com.chailijun.mtime.activity.VideoListActivity;
import com.chailijun.mtime.base.BaseFragment;
import com.chailijun.mtime.customview.ExpandableTextView;
import com.chailijun.mtime.data.moviedetail.HotComment;
import com.chailijun.mtime.data.moviedetail.MovieDetail;
import com.chailijun.mtime.mvp.model.GoodsListBean;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.DensityUtil;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.Logger;
import com.chailijun.mtime.utils.MsgEvent;

import java.util.List;

import de.greenrobot.event.EventBus;

import static com.chailijun.mtime.R.id.film_review_detail;
import static com.chailijun.mtime.R.id.more_hotlongcomments;
import static com.chailijun.mtime.R.id.movieName;
import static com.chailijun.mtime.utils.UrlUtils.addPrefix;

public class MovieDetailBaseInfoFragment extends BaseFragment {

    public static final String ARGUMENT = "argument";

    //电影剧情
    private LinearLayout ll_story;
    private TextView tv_story;
    private ExpandableTextView movie_story;

    //导演、演员
    private RelativeLayout rl_director;
    private RecyclerView mActorListView;
    private TextView allActor;

    //视频和图片
    private LinearLayout ll_videoimage;
    private TextView video_count;
    private TextView image_count;
    private ImageView videoImg;
    private ImageView imageImg;
    private RelativeLayout rl_video;
    private RelativeLayout rl_image;

    //相关商品
    private LinearLayout ll_related;
    private RelativeLayout rl_more_goods;
    private TextView goods_count;
    private ImageView goods1_img;
    private ImageView goods2_img;
    private ImageView goods3_img;
    private TextView goods1_name;
    private TextView goods2_name;
    private TextView goods3_name;
    private TextView goods1_price;
    private TextView goods2_price;
    private TextView goods3_price;
    private TextView goods1_icontext;
    private TextView goods2_icontext;
    private TextView goods3_icontext;

    //短评
    private LinearLayout ll_hotcomment;
    private RecyclerView mHotcommentView;
    private TextView moreComment;
    private TextView all_hot_comments;
    private TextView no_hotcomment;

    //广告条
    private LinearLayout ll_advlist;
    private WebView webView;

    //影评
    private LinearLayout filmReview;
    private RelativeLayout allFilmReview1;
    private TextView filmReviewTitle;
    private ImageView userHeadImg;
    private TextView userNickname;
    private TextView userRating;
    private TextView replyCount;
    private LinearLayout filmReviewDetail;
    private TextView allHotLongComments;
    private TextView moreHotLongComments;
    private TextView no_hotlongcomments;

    private ActorListAdapter mActorListAdapter;
    private HotCommentAdapter mHotCommentAdapter;
    private int movieId;

    public static MovieDetailBaseInfoFragment newInstance(int movieId) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARGUMENT, movieId);

        MovieDetailBaseInfoFragment fragment = new MovieDetailBaseInfoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    //FragmentPagerAdapter通过setPrimaryItem()控制显示哪个fragment时，此方法必须实现
    //缘由：可查看源码
    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null) {
            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        movieId = bundle.getInt(ARGUMENT);
    }

    @Override
    public void onResume() {
        super.onResume();
        //订阅EventBus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //取消订阅EventBus
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {

        if (webView != null){
            ((ViewGroup)webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_movie_detail_base_info;
    }

    @Override
    public void initView(View view) {

        //电影剧情
        ll_story = $(view, R.id.ll_story);
        tv_story = $(view, R.id.tv_story);
        movie_story = $(view, R.id.movie_story);

        //导演、演员
        rl_director = $(view, R.id.rl_director);
        mActorListView = $(view, R.id.recyclerview_director);
        allActor = $(view, R.id.tv_all_actor);

        //视频和图片
        ll_videoimage = $(view, R.id.ll_videoimage);
        video_count = $(view, R.id.video_count);
        image_count = $(view, R.id.image_count);
        videoImg = $(view, R.id.iv_video_img);
        imageImg = $(view, R.id.iv_image_img);
        rl_video = $(view, R.id.rl_video);
        rl_image = $(view, R.id.rl_image);

        //相关商品
        ll_related = $(view, R.id.ll_related);
        rl_more_goods = $(view, R.id.rl_more_goods);
        goods_count = $(view, R.id.goods_count);
        goods1_img = $(view, R.id.goods1_img);
        goods2_img = $(view, R.id.goods2_img);
        goods3_img = $(view, R.id.goods3_img);
        goods1_name = $(view, R.id.tv_goods1_name);
        goods2_name = $(view, R.id.tv_goods2_name);
        goods3_name = $(view, R.id.tv_goods3_name);
        goods1_price = $(view, R.id.tv_goods1_price);
        goods2_price = $(view, R.id.tv_goods2_price);
        goods3_price = $(view, R.id.tv_goods3_price);
        goods1_icontext = $(view, R.id.goods1_icontext);
        goods2_icontext = $(view, R.id.goods2_icontext);
        goods3_icontext = $(view, R.id.goods3_icontext);

        //短评
        ll_hotcomment = $(view, R.id.ll_hotcomment);
        mHotcommentView = $(view, R.id.recyclerview_hotcomment);
        moreComment = $(view, R.id.more_comment);
        all_hot_comments = $(view, R.id.all_hot_comments);
        no_hotcomment = $(view,R.id.no_hotcomment);

        //广告条
        ll_advlist = $(view, R.id.ll_advlist);

        //影评
        filmReview = $(view, R.id.film_review);
        allFilmReview1 = $(view, R.id.all_film_review1);
        filmReviewTitle = $(view, R.id.film_review_title);
        userHeadImg = $(view, R.id.user_headImg);
        userNickname = $(view, R.id.user_nickname);
        userRating = $(view, R.id.user_rating);
        replyCount = $(view, R.id.replyCount);
        filmReviewDetail = $(view, film_review_detail);
        allHotLongComments = $(view, R.id.all_hotlongcomments);
        moreHotLongComments = $(view, more_hotlongcomments);
        no_hotlongcomments = $(view, R.id.no_hotlongcomments);

        initActorListView();
        initHotCommentView();
    }

    /**
     * 初始化短评布局
     */
    private void initHotCommentView() {

        LinearLayoutManager manager =
                new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mHotCommentAdapter = new HotCommentAdapter(null);
        mHotcommentView.setHasFixedSize(true);
        mHotcommentView.setLayoutManager(manager);
        mHotcommentView.setAdapter(mHotCommentAdapter);
    }

    /**
     * 初始化演员列表视图
     */
    private void initActorListView() {
        LinearLayoutManager manager =
                new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        mActorListAdapter = new ActorListAdapter(null);
        mActorListView.setHasFixedSize(true);
        mActorListView.setLayoutManager(manager);
        mActorListView.setAdapter(mActorListAdapter);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void widgetClick(View v) {

    }

    //接收来自MovieDetailFragment的主线程发送的消息
    public void onEventMainThread(MsgEvent msg) {
        if (msg.getMsg() instanceof MovieDetail) {
            MovieDetail movieDetail = (MovieDetail) msg.getMsg();
            Logger.d(TAG, "基本资料:接收消息===电影详细===" + movieDetail.getBasic().getName());

            setMovieStory(movieDetail.getBasic().getStory());
            setActorList(movieDetail.getBasic().getDirector(),movieDetail.getBasic().getActors());
            setVideoAndImage(movieDetail.getBasic().getVideo(),movieDetail.getBasic().getStageImg());
            setMovieRelatedGoods(movieDetail.getRelated());
            setWebView(movieDetail.getAdvertisement());//广告条
        }

        if (msg.getMsg() instanceof HotComment) {
            HotComment hotComment = (HotComment) msg.getMsg();
            Logger.d(TAG, "基本资料:接收消息===短评总数===" + hotComment.getMini().getTotal());

            setHotComment(hotComment.getMini());
            setFilmReview(hotComment.getPlus());
        }
    }

    /**
     * 设置"影评"
     * @param plus
     */
    private void setFilmReview(HotComment.PlusBean plus) {
        List<HotComment.PlusBean.ListBean> list = plus.getList();
        if (list != null && list.size() > 0) {
            filmReviewDetail.setVisibility(View.VISIBLE);
            moreHotLongComments.setVisibility(View.VISIBLE);
            allHotLongComments.setVisibility(View.VISIBLE);
            no_hotlongcomments.setVisibility(View.GONE);

            HotComment.PlusBean.ListBean listBean = list.get(0);

            filmReviewTitle.setText(listBean.getTitle());
            Imager.loadCircleImage(getActivity(),
                    addPrefix(listBean.getHeadImg()), userHeadImg, DensityUtil.dp2px(10.0f));
            userNickname.setText(listBean.getNickname());

            if (listBean.getRating() > 0) {
                userRating.setVisibility(View.VISIBLE);
                userRating.setText(String.format(getString(R.string.rating), listBean.getRating() + ""));
            } else {
                userRating.setVisibility(View.GONE);
            }
            replyCount.setText(String.format(getString(R.string.reply_count), listBean.getReplyCount()));
            moreHotLongComments.setText(String.format(getString(R.string.all_film_review), plus.getTotal()));

            allHotLongComments.setOnClickListener(new FilmReviewLisenter(plus));
            moreHotLongComments.setOnClickListener(new FilmReviewLisenter(plus));
            filmReviewDetail.setOnClickListener(new FilmReviewLisenter(plus));
        }else {
            filmReviewDetail.setVisibility(View.GONE);
            moreHotLongComments.setVisibility(View.GONE);
            allHotLongComments.setVisibility(View.GONE);
            no_hotlongcomments.setVisibility(View.VISIBLE);
        }
    }

    class FilmReviewLisenter implements View.OnClickListener{

        private HotComment.PlusBean plus;

        public FilmReviewLisenter(HotComment.PlusBean plus) {
            this.plus = plus;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.all_hotlongcomments:
                case R.id.more_hotlongcomments:
                    if (movieId > 0){
                        Intent intent = new Intent(getActivity(),HotLongCommentsActivity.class);
                        intent.putExtra(Constants.MOVIE_ID,movieId);
                        getActivity().startActivity(intent);
                    }
                    break;
                case R.id.film_review_detail:
                    Intent intent = new Intent(getActivity(), ReviewDetailActivity.class);
                    intent.putExtra(Constants.REVIEW_ID,plus.getList().get(0).getCommentId());
                    getActivity().startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 设置"短评"
     * @param mini
     */
    private void setHotComment(HotComment.MiniBean mini) {
        List<HotComment.MiniBean.ListBean> list = mini.getList();
        if (list != null && list.size() > 0){
            no_hotcomment.setVisibility(View.GONE);
            mHotcommentView.setVisibility(View.VISIBLE);
            moreComment.setVisibility(View.VISIBLE);
            all_hot_comments.setVisibility(View.VISIBLE);

            moreComment.setText(String.format(getString(R.string.all_comment),mini.getTotal()));
            mHotCommentAdapter.setNewData(list);

            if (mini.getTotal() < 4){
                moreComment.setVisibility(View.GONE);
            }else {
                moreComment.setVisibility(View.VISIBLE);
                //设置进入电影短评页面监听
                int total = mini.getTotal();
                moreComment.setOnClickListener(new HotMovieCommentsLisenter(total));
                all_hot_comments.setOnClickListener(new HotMovieCommentsLisenter(total));
            }

        }else {
            no_hotcomment.setVisibility(View.VISIBLE);
            mHotcommentView.setVisibility(View.GONE);
            moreComment.setVisibility(View.GONE);
            all_hot_comments.setVisibility(View.GONE);

        }
    }

    class HotMovieCommentsLisenter implements View.OnClickListener{
        private int total;

        public HotMovieCommentsLisenter(int total) {
            this.total = total;
        }

        @Override
        public void onClick(View view) {
            if (movieId > 0){
                Intent intent = new Intent(getActivity(),HotMovieCommentsActivity.class);
                intent.putExtra(Constants.MOVIE_ID,movieId);
                intent.putExtra(Constants.COMMENT_TOTAL,total);
                getActivity().startActivity(intent);
            }
        }
    }

    /**
     * 设置广告条内容
     * @param advertisement
     */
    private void setWebView(MovieDetail.AdvertisementBean advertisement) {
        if (advertisement.getCount() > 0){
            ll_advlist.setVisibility(View.VISIBLE);
            if (ll_advlist.getChildCount() < 2){//防止重复添加
                webView = new WebView(getActivity());
                LinearLayout.LayoutParams params =
                        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                webView.setLayoutParams(params);
                WebSettings webSettings = webView.getSettings();

                webSettings.setJavaScriptEnabled(true);
                webSettings.setDomStorageEnabled(true);

                webView.loadUrl(advertisement.getAdvList().get(0).getUrl());
                ll_advlist.addView(webView, 1);
            }
        }else {
            ll_advlist.setVisibility(View.GONE);
        }
    }

    /**
     * 设置电影相关商品
     * @param related
     */
    private void setMovieRelatedGoods(MovieDetail.RelatedBean related) {
        List<GoodsListBean> goodsList = related.getGoodsList();
        if (goodsList != null && goodsList.size() >= 3) {
            ll_related.setVisibility(View.VISIBLE);

            goods_count.setText(String.format(getString(R.string.movie_relation), related.getGoodsCount()));

            Imager.load(getActivity(), goodsList.get(0).getImage(), goods1_img);
            Imager.load(getActivity(), goodsList.get(1).getImage(), goods2_img);
            Imager.load(getActivity(), goodsList.get(2).getImage(), goods3_img);

            goods1_name.setText(goodsList.get(0).getName());
            goods2_name.setText(goodsList.get(1).getName());
            goods3_name.setText(goodsList.get(2).getName());

            goods1_price.setText(String.format(getString(R.string.goods_price), goodsList.get(0).getMinSalePriceFormat()));
            goods2_price.setText(String.format(getString(R.string.goods_price), goodsList.get(1).getMinSalePriceFormat()));
            goods3_price.setText(String.format(getString(R.string.goods_price), goodsList.get(2).getMinSalePriceFormat()));

            setGoodsIconText(goods1_icontext, goodsList.get(0).getIconText(), goodsList.get(0).getBackground());
            setGoodsIconText(goods2_icontext, goodsList.get(1).getIconText(), goodsList.get(1).getBackground());
            setGoodsIconText(goods3_icontext, goodsList.get(2).getIconText(), goodsList.get(2).getBackground());

        } else {
            ll_related.setVisibility(View.GONE);
        }
    }

    /**
     * 设置商品图片左上角的图标文字
     *
     * @param iconText
     * @param text
     * @param color
     */
    private void setGoodsIconText(TextView iconText, String text, String color) {
        if (!TextUtils.isEmpty(text)) {
            iconText.setVisibility(View.VISIBLE);
            iconText.setText(text);
            iconText.setBackgroundColor(Color.parseColor(color));
        } else {
            iconText.setVisibility(View.GONE);
        }
    }

    /**
     * 设置电影相关的视频和图片
     * @param video
     * @param stageImg
     */
    private void setVideoAndImage(MovieDetail.BasicBean.VideoBean video,
                                  MovieDetail.BasicBean.StageImgBean stageImg) {
        if (video.getCount() > 0 || stageImg.getCount() > 0){
            ll_videoimage.setVisibility(View.VISIBLE);

            video_count.setText(video.getCount() + "");
            image_count.setText(stageImg.getCount() + "");
            Imager.load(getActivity(), video.getImg(), videoImg);
            Imager.load(getActivity(), stageImg.getList().get(0).getImgUrl(), imageImg);

            //点击进入电影拍摄幕后视频列表页面
            if (video.getCount() > 0){
                rl_video.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (MovieDetailBaseInfoFragment.this.movieId > 0){
                            Intent intent = new Intent(getActivity(),VideoListActivity.class);
                            intent.putExtra(Constants.MOVIE_ID,movieId);
                            startActivity(intent);
                        }
                    }
                });
            }

            //点击进入电影所有图片页面
            if (stageImg.getCount() > 0){
                rl_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (MovieDetailBaseInfoFragment.this.movieId > 0){
                            Intent intent = new Intent(getActivity(),MovieImageAllActivity.class);
                            intent.putExtra(Constants.MOVIE_ID, movieId);
                            intent.putExtra(Constants.MOVIE_NAME,movieName);
                            startActivity(intent);
                        }
                    }
                });
            }

        }else {
            ll_videoimage.setVisibility(View.GONE);
        }
    }

    /**
     * 设置导演和演员列表信息
     * @param director
     * @param actors
     */
    private void setActorList(MovieDetail.BasicBean.DirectorBean director,
                              List<MovieDetail.BasicBean.ActorsBean> actors) {
        if (director.getDirectorId() != 0 || (actors != null && actors.size() > 0)){
            rl_director.setVisibility(View.VISIBLE);

            //导演
            MovieDetail.BasicBean.ActorsBean direct = new MovieDetail.BasicBean.ActorsBean();
            direct.setName(director.getName());
            direct.setActorId(director.getDirectorId());
            direct.setImg(director.getImg());
            direct.setNameEn(director.getNameEn());
            direct.setRoleName("");
            actors.add(0, direct);
            mActorListAdapter.setNewData(actors);

            //演员列表item点击
            mActorListView.addOnItemTouchListener(new OnItemClickListener() {
                @Override
                public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                    MovieDetail.BasicBean.ActorsBean actor =
                            (MovieDetail.BasicBean.ActorsBean) adapter.getData().get(position);

                    //进入人物详细界面
                    Intent intent = new Intent(getActivity(),PersonalDetailActivity.class);
                    intent.putExtra(Constants.PERSON_ID,actor.getActorId());
                    startActivity(intent);
                }
            });

            allActor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (movieId > 0){
                        Intent intent = new Intent(getActivity(),MovieCreditsActivity.class);
                        intent.putExtra(Constants.MOVIE_ID,movieId);
                        startActivity(intent);
                    }
                }
            });

        }else {
            rl_director.setVisibility(View.GONE);
        }
    }

    /**
     * 设置电影剧情
     * @param story
     */
    private void setMovieStory(String story) {
        if (!TextUtils.isEmpty(story)){
            ll_story.setVisibility(View.VISIBLE);
            movie_story.setText(getString(R.string.story) + story);
        }else {
            ll_story.setVisibility(View.GONE);
        }
    }
}
