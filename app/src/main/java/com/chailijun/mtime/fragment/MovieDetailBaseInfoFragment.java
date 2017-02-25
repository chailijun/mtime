package com.chailijun.mtime.fragment;

import android.content.Context;
import android.content.Intent;
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

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.base.CommonBaseAdapter;
import com.chailijun.baseadapter.interfaces.OnItemClickListener;
import com.chailijun.mtime.R;
import com.chailijun.mtime.activity.HotLongCommentsActivity;
import com.chailijun.mtime.activity.HotMovieCommentsActivity;
import com.chailijun.mtime.activity.MovieCreditsActivity;
import com.chailijun.mtime.activity.MovieImageAllActivity;
import com.chailijun.mtime.activity.PersonalDetailActivity;
import com.chailijun.mtime.activity.ReviewDetailActivity;
import com.chailijun.mtime.activity.VideoListActivity;
import com.chailijun.mtime.customview.ExpandableTextView;
import com.chailijun.mtime.mvp.model.GoodsListBean;
import com.chailijun.mtime.mvp.model.movie.HotCommentJson;
import com.chailijun.mtime.mvp.model.movie.MovieDetailJson;
import com.chailijun.mtime.mvp.model.movie.MovieDetailJson.DataBean.BasicBean.ActorsBean;
import com.chailijun.mtime.utils.ColorUtils;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Logger;
import com.chailijun.mtime.utils.TimeUtils;
import com.chailijun.mtime.utils.DensityUtil;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.MsgEvent;

import java.util.List;

import butterknife.BindView;
import de.greenrobot.event.EventBus;

@Deprecated
public class MovieDetailBaseInfoFragment extends BaseFragment {

    //电影剧情
    @BindView(R.id.ll_story)
    LinearLayout ll_story;
    @BindView(R.id.tv_story)
    TextView tv_story;
    @BindView(R.id.movie_story)
    ExpandableTextView movie_story;

    //导演、演员
    @BindView(R.id.rl_director)
    RelativeLayout rl_director;
    @BindView(R.id.recyclerview_director)
    RecyclerView recyclerview_director;
    @BindView(R.id.tv_all_actor)
    TextView allActor;

    //视频和图片
    @BindView(R.id.ll_videoimage)
    LinearLayout ll_videoimage;
    @BindView(R.id.video_count)
    TextView video_count;
    @BindView(R.id.image_count)
    TextView image_count;
    @BindView(R.id.iv_video_img)
    ImageView videoImg;
    @BindView(R.id.iv_image_img)
    ImageView imageImg;
    @BindView(R.id.rl_video)
    RelativeLayout rl_video;
    @BindView(R.id.rl_image)
    RelativeLayout rl_image;

    //相关商品
    @BindView(R.id.ll_related)
    LinearLayout ll_related;
    @BindView(R.id.rl_more_goods)
    RelativeLayout rl_more_goods;
    @BindView(R.id.goods_count)
    TextView goods_count;
    @BindView(R.id.goods1_img)
    ImageView goods1_img;
    @BindView(R.id.goods2_img)
    ImageView goods2_img;
    @BindView(R.id.goods3_img)
    ImageView goods3_img;
    @BindView(R.id.tv_goods1_name)
    TextView goods1_name;
    @BindView(R.id.tv_goods2_name)
    TextView goods2_name;
    @BindView(R.id.tv_goods3_name)
    TextView goods3_name;
    @BindView(R.id.tv_goods1_price)
    TextView goods1_price;
    @BindView(R.id.tv_goods2_price)
    TextView goods2_price;
    @BindView(R.id.tv_goods3_price)
    TextView goods3_price;
    @BindView(R.id.goods1_icontext)
    TextView goods1_icontext;
    @BindView(R.id.goods2_icontext)
    TextView goods2_icontext;
    @BindView(R.id.goods3_icontext)
    TextView goods3_icontext;

    //短评
    @BindView(R.id.ll_hotcomment)
    LinearLayout ll_hotcomment;
    @BindView(R.id.recyclerview_hotcomment)
    RecyclerView recyclerview_hotcomment;
    @BindView(R.id.more_comment)
    TextView moreComment;
    @BindView(R.id.all_hot_comments)
    TextView all_hot_comments;

    //广告条
    @BindView(R.id.ll_advlist)
    LinearLayout ll_advlist;
//    @BindView(R.id.webView_advlist)
    WebView webView;

    //影评
    @BindView(R.id.film_review)
    LinearLayout filmReview;
    @BindView(R.id.all_film_review1)
    RelativeLayout allFilmReview1;
    @BindView(R.id.film_review_title)
    TextView filmReviewTitle;
    @BindView(R.id.user_headImg)
    ImageView userHeadImg;
    @BindView(R.id.user_nickname)
    TextView userNickname;
    @BindView(R.id.user_rating)
    TextView userRating;
    @BindView(R.id.replyCount)
    TextView replyCount;
    @BindView(R.id.film_review_detail)
    LinearLayout filmReviewDetail;
    @BindView(R.id.all_hotlongcomments)
    TextView allHotLongComments;
    @BindView(R.id.more_hotlongcomments)
    TextView moreHotLongComments;

    private DirectorItemAdapter directorAdapter;
    private HotCommentAdapter hotCommentAdapter;
    private int movieId;

    public static MovieDetailBaseInfoFragment newInstance(int movieId) {
        MovieDetailBaseInfoFragment fragment = new MovieDetailBaseInfoFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.MOVIE_ID, movieId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public String getFragmentName() {
        return "电影详细--基本资料";
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Logger.e("CLJ","BaseInfo--->onAttach()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.e("CLJ","BaseInfo--->onCreate()");

        Bundle bundle = getArguments();
        movieId = bundle.getInt(Constants.MOVIE_ID);

    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.e("CLJ","BaseInfo--->onActivityCreated()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Logger.e("CLJ","BaseInfo--->onStart()");
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.e("TAG","--->onPause()");
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
    protected int getLayoutId() {
        return R.layout.fragment_movie_detail_base_info;
    }

    @Override
    protected void initViews(View rootView) {
//        adapter = new MovieDetailBaseInfoAdapter(getActivity(),null,false);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setAdapter(adapter);

        //初始化导演、演员布局
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        directorAdapter = new DirectorItemAdapter(getActivity(), null, false);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview_director.setLayoutManager(manager);
        recyclerview_director.setHasFixedSize(true);
        recyclerview_director.setAdapter(directorAdapter);

        //初始化短评布局
        LinearLayoutManager manager2 = new LinearLayoutManager(getActivity());
        hotCommentAdapter = new HotCommentAdapter(getActivity(),null,false);
        manager2.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview_hotcomment.setLayoutManager(manager2);
        recyclerview_hotcomment.setHasFixedSize(true);
        recyclerview_hotcomment.setAdapter(hotCommentAdapter);
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
    protected void lazyLoadData() {

    }

    //接收来自MovieDetailActivity的主线程发送的消息
    public void onEventMainThread(MsgEvent msg) {
        if (msg.getMsg() instanceof MovieDetailJson) {
//            Logger.e("TAG","BaseInfo==收到==MovieDetailJson");

            MovieDetailJson detailJson = (MovieDetailJson) msg.getMsg();

            setMovieStory(detailJson);
            setDirectorAnaActorInfo(detailJson);
            setVideoAndImage(detailJson);
            setMovieRelatedGoods(detailJson);
            setWebView(detailJson);//广告条
        }
        if (msg.getMsg() instanceof HotCommentJson) {
//            Logger.e("TAG","BaseInfo==收到==HotCommentJson");

            HotCommentJson hotCommentJson = (HotCommentJson) msg.getMsg();
            setHotComment(hotCommentJson);
            setFilmReview(hotCommentJson);
        }
    }


    /**
     * 设置广告条内容
     *
     * @param detailJson
     */
    private void setWebView(MovieDetailJson detailJson) {
        if (detailJson.getData().getAdvertisement().getCount() > 0){
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

                webView.loadUrl(detailJson.getData().getAdvertisement().getAdvList().get(0).getUrl());
                ll_advlist.addView(webView,1);
            }
        }else {
            ll_advlist.setVisibility(View.GONE);
        }
    }

    /**
     * 设置"影评"
     *
     * @param hotCommentJson
     */
    private void setFilmReview(HotCommentJson hotCommentJson) {
        List<HotCommentJson.DataBean.PlusBean.ListBean> list
                = hotCommentJson.getData().getPlus().getList();
        if (list != null && list.size() > 0) {
            filmReview.setVisibility(View.VISIBLE);
            HotCommentJson.DataBean.PlusBean.ListBean listBean = list.get(0);
            filmReviewTitle.setText(listBean.getTitle());
            Imager.loadCircleImage(getActivity(),
                    listBean.getHeadImg(), userHeadImg, DensityUtil.dp2px(10.0f));
            userNickname.setText(listBean.getNickname());

            if (listBean.getRating() > 0) {
                userRating.setVisibility(View.VISIBLE);
                userRating.setText(String.format(getString(R.string.rating), listBean.getRating() + ""));
            } else {
                userRating.setVisibility(View.GONE);
            }
            replyCount.setText(String.format(getString(R.string.reply_count), listBean.getReplyCount()));
            moreHotLongComments.setText(String.format(getString(R.string.all_film_review), hotCommentJson.getData().getPlus().getTotal()));

            allHotLongComments.setOnClickListener(new FilmReviewLisenter(hotCommentJson));
            moreHotLongComments.setOnClickListener(new FilmReviewLisenter(hotCommentJson));
            filmReviewDetail.setOnClickListener(new FilmReviewLisenter(hotCommentJson));
        } else {
            filmReview.setVisibility(View.GONE);
        }
    }

    class FilmReviewLisenter implements View.OnClickListener{

        private HotCommentJson hotCommentJson;

        public FilmReviewLisenter(HotCommentJson hotCommentJson) {
            this.hotCommentJson = hotCommentJson;
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
                    intent.putExtra(Constants.REVIEW_ID,hotCommentJson.getData().getPlus().getList().get(0).getCommentId());
                    getActivity().startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 设置"短评"
     *
     * @param hotCommentJson
     */
    private void setHotComment(HotCommentJson hotCommentJson) {

        List<HotCommentJson.DataBean.MiniBean.ListBean> list = hotCommentJson.getData().getMini().getList();
        if (list != null && list.size() > 0){
            ll_hotcomment.setVisibility(View.VISIBLE);
            moreComment.setText(String.format(getString(R.string.all_comment),hotCommentJson.getData().getMini().getTotal()));
            hotCommentAdapter.setNewData(list);

            if (hotCommentJson.getData().getMini().getTotal() < 4){
                moreComment.setVisibility(View.GONE);
            }else {
                moreComment.setVisibility(View.VISIBLE);
                //设置进入电影短评页面监听
                int total = hotCommentJson.getData().getMini().getTotal();
                moreComment.setOnClickListener(new HotMovieCommentsLisenter(total));
                all_hot_comments.setOnClickListener(new HotMovieCommentsLisenter(total));
            }
        }else {
            ll_hotcomment.setVisibility(View.GONE);
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
     * 设置电影相关商品
     *
     * @param detailJson
     */
    private void setMovieRelatedGoods(MovieDetailJson detailJson) {
        List<GoodsListBean> goodsList = detailJson.getData().getRelated().getGoodsList();
        if (goodsList != null && goodsList.size() >= 3) {
            ll_related.setVisibility(View.VISIBLE);

            goods_count.setText(String.format(getString(R.string.movie_relation), detailJson.getData().getRelated().getGoodsCount()));

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
            iconText.setBackgroundColor(ColorUtils.getTitleColor(color));
        } else {
            iconText.setVisibility(View.GONE);
        }
    }

    /**
     * 设置电影相关的视频和图片
     *
     * @param detailJson
     */
    private void setVideoAndImage(final MovieDetailJson detailJson) {
        if (detailJson.getData().getBasic().getVideo().getCount() > 0 ||
                detailJson.getData().getBasic().getStageImg().getCount() > 0){
            ll_videoimage.setVisibility(View.VISIBLE);
            video_count.setText(detailJson.getData().getBasic().getVideo().getCount() + "");
            image_count.setText(detailJson.getData().getBasic().getStageImg().getCount() + "");
            Imager.load(getActivity(), detailJson.getData().getBasic().getVideo().getImg(), videoImg);
            Imager.load(getActivity(), detailJson.getData().getBasic().getStageImg().getList().get(0).getImgUrl(), imageImg);

            final int movieId = detailJson.getData().getBoxOffice().getMovieId();
            //点击进入电影拍摄幕后视频列表页面
            if (detailJson.getData().getBasic().getVideo().getCount() > 0){
                rl_video.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (MovieDetailBaseInfoFragment.this.movieId > 0){
                            Intent intent = new Intent(getActivity(),VideoListActivity.class);
                            intent.putExtra(Constants.MOVIE_ID,movieId);
                            getActivity().startActivity(intent);
                        }
                    }
                });
            }

            final String movieName = detailJson.getData().getBasic().getName();
            //点击进入电影所有图片页面
            if (detailJson.getData().getBasic().getStageImg().getCount() > 0){
                rl_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (MovieDetailBaseInfoFragment.this.movieId > 0){
                            Intent intent = new Intent(getActivity(),MovieImageAllActivity.class);
                            intent.putExtra(Constants.MOVIE_ID, movieId);
                            intent.putExtra(Constants.MOVIE_NAME,movieName);
                            getActivity().startActivity(intent);
                        }
                    }
                });
            }
        }else {
            ll_videoimage.setVisibility(View.GONE);
        }
    }

    /**
     * 设置导演和演员信息
     *
     * @param detailJson
     */
    private void setDirectorAnaActorInfo(MovieDetailJson detailJson) {
        List<ActorsBean> actors = detailJson.getData().getBasic().getActors();

        if (actors != null && actors.size() > 0 ||
                detailJson.getData().getBasic().getDirector().getDirectorId() != 0){
            rl_director.setVisibility(View.VISIBLE);
            ActorsBean director = new ActorsBean();
            director.setName(detailJson.getData().getBasic().getDirector().getName());
            director.setActorId(detailJson.getData().getBasic().getDirector().getDirectorId());
            director.setImg(detailJson.getData().getBasic().getDirector().getImg());
            director.setNameEn(detailJson.getData().getBasic().getDirector().getNameEn());
            director.setRoleName("");
            actors.add(0, director);
            directorAdapter.setNewData(actors);
            directorAdapter.setOnItemClickListener(new OnItemClickListener<ActorsBean>() {
                @Override
                public void onItemClick(ViewHolder viewHolder, ActorsBean data, int position) {
                    Intent intent = new Intent(getActivity(),PersonalDetailActivity.class);
                    intent.putExtra(Constants.PERSON_ID,data.getActorId());
                    getActivity().startActivity(intent);
                }
            });
            allActor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (movieId > 0){
                        Intent intent = new Intent(getActivity(),MovieCreditsActivity.class);
                        intent.putExtra(Constants.MOVIE_ID,movieId);
                        getActivity().startActivity(intent);
                    }
                }
            });
        }else {
            rl_director.setVisibility(View.GONE);
        }
    }

    /**
     * 设置电影剧情
     *
     * @param detailJson
     */
    private void setMovieStory(MovieDetailJson detailJson) {
        if (!TextUtils.isEmpty(detailJson.getData().getBasic().getStory())){
            ll_story.setVisibility(View.VISIBLE);
            movie_story.setText(getString(R.string.story) + detailJson.getData().getBasic().getStory());
            Logger.e("TAG","设置剧情");
        }else {
            ll_story.setVisibility(View.GONE);
        }
    }

    /*@OnClick({R.id.tv_all_actor, R.id.rl_video, R.id.rl_image, R.id.rl_more_goods,
             R.id.user1_reply,
            R.id.user1_praiseCount,  R.id.all_comment1, R.id.all_comment2,
            R.id.all_film_review1, R.id.film_review_detail, R.id.all_film_review2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_all_actor:
                getActivity().startActivity(new Intent(getActivity(), EndCreditsActivity.class));
                break;
            case R.id.rl_video:
                Toast.makeText(getActivity(), "视频列表", Toast.LENGTH_SHORT).show();
                *//*if (movieId > 0){
                    Intent intent = new Intent(MovieDetailBaseInfoFragment.this,VideoListActivity.class);
                    intent.putExtra(Constants.MOVIE_ID,movieId);
                    startActivity(intent);
                }*//*
                break;
            case R.id.rl_image:
                Toast.makeText(getActivity(), "图片列表", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_more_goods:
                Toast.makeText(getActivity(), "更多商品", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.first_comment:
//                Toast.makeText(getActivity(), "first_comment", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.second_comment:
//                Toast.makeText(getActivity(), "second_comment", Toast.LENGTH_SHORT).show();
//                break;
            case R.id.all_comment1:
            case R.id.all_comment2:
                Toast.makeText(getActivity(), "all_comment", Toast.LENGTH_SHORT).show();
                break;
            case R.id.user1_reply:
                Toast.makeText(getActivity(), "user1_reply", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.user2_reply:
//                Toast.makeText(getActivity(), "user2_reply", Toast.LENGTH_SHORT).show();
//                break;
            case R.id.user1_praiseCount:
                Toast.makeText(getActivity(), "user1_praiseCount", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.user2_praiseCount:
//                Toast.makeText(getActivity(), "user2_praiseCount", Toast.LENGTH_SHORT).show();
//                break;
            case R.id.all_film_review1:
            case R.id.all_film_review2:
                Toast.makeText(getActivity(), "全部影评", Toast.LENGTH_SHORT).show();
                break;
            case R.id.film_review_detail:
                Toast.makeText(getActivity(), "影评详细", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }*/


    private class DirectorItemAdapter extends CommonBaseAdapter<ActorsBean> {

        public DirectorItemAdapter(Context context, List<ActorsBean> datas, boolean isOpenLoadMore) {
            super(context, datas, isOpenLoadMore);
        }

        @Override
        protected void convert(ViewHolder holder, ActorsBean data, int position) {
            Imager.load(getActivity(), data.getImg(), (ImageView) holder.getView(R.id.img));
            holder.setText(R.id.director_name, data.getName());
            holder.setText(R.id.director_name_en, data.getNameEn());

            if (0 != position){
                holder.setText(R.id.role_name,
                        data.getRoleName().equals("") ? "饰：--" : "饰：" + data.getRoleName());
            }
            holder.getView(R.id.director_actor).setVisibility(position < 2 ? View.VISIBLE : View.INVISIBLE);
            if (0 == position) {
                holder.setText(R.id.director_actor, getString(R.string.director));
            } else if (1 == position) {
                holder.setText(R.id.director_actor, getString(R.string.actor));
            }
        }

        @Override
        protected int getItemLayoutId() {
            return R.layout.fragment_movie_detail_base_info_item_actors_item;//基本资料--导演、演员
        }
    }

    @Deprecated
    class HotCommentAdapter extends CommonBaseAdapter<HotCommentJson.DataBean.MiniBean.ListBean>{


        public HotCommentAdapter(Context context,
                                 List<HotCommentJson.DataBean.MiniBean.ListBean> datas,
                                 boolean isOpenLoadMore) {
            super(context, datas, isOpenLoadMore);
        }

        @Override
        protected void convert(ViewHolder holder,
                               HotCommentJson.DataBean.MiniBean.ListBean data,
                               int position) {

            Imager.loadCircleImage(getActivity(),data.getHeadImg(),
                    (ImageView) holder.getView(R.id.user_headImg),DensityUtil.dp2px(20.0f));
            holder.setText(R.id.user_nickname,data.getNickname());
            holder.setText(R.id.user_rating,data.getRating()>0?"评"+data.getRating():"");
            holder.setText(R.id.user_content,data.getContent());
            holder.setText(R.id.user_commentDate,TimeUtils.getDistanceTime(data.getCommentDate()));
            holder.setText(R.id.user_reply,data.getReplyCount()+"");
            holder.setText(R.id.user_praiseCount,data.getPraiseCount()+"");

        }

        @Override
        protected int getItemLayoutId() {
            return R.layout.fragment_movie_detail_base_info_item_hotcomment_item;
        }
    }

}
