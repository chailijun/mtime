package com.chailijun.mtime.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.base.MultiBaseAdapter;
import com.chailijun.mtime.MainActivity;
import com.chailijun.mtime.R;
import com.chailijun.mtime.activity.NewsDetailActivity;
import com.chailijun.mtime.activity.SearchItemActivity;
import com.chailijun.mtime.activity.WebActivity;
import com.chailijun.mtime.customview.WithTagImageView;
import com.chailijun.mtime.fragment.HotPlayMoviesFragment;
import com.chailijun.mtime.mvp.model.HomeBaseData;
import com.chailijun.mtime.mvp.model.HomeIndexJson;
import com.chailijun.mtime.mvp.model.HomeListJson;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.DensityUtil;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.JsonIsNull;
import com.chailijun.mtime.utils.Logger;
import com.chailijun.mtime.utils.SPUtil;
import com.chailijun.mtime.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;


public class HomePageAdapter extends MultiBaseAdapter<HomeBaseData> implements View.OnClickListener {
    private static final String TAG = HomePageAdapter.class.getSimpleName();

    private Context mContext;
    private FragmentManager fragmentManager;
    private ConvenientBanner banner;
    private ConvenientBanner advBanner;

    public HomePageAdapter(Context context,
                           List<HomeBaseData> datas,
                           boolean isOpenLoadMore,
                           FragmentManager fragmentManager) {
        super(context, datas, isOpenLoadMore);
        mContext = context;
        this.fragmentManager = fragmentManager;
    }

    @Override
    protected void convert(ViewHolder holder, HomeBaseData data, int position, int viewType) {
        if (viewType == Constants.TYPE_HEAD) {
            if (data instanceof HomeIndexJson) {
                HomeIndexJson homeIndexJson = (HomeIndexJson) data;
                bindHomeIndexData(holder, homeIndexJson);
            } else {
                throw new IllegalArgumentException(
                        HomePageAdapter.class.getSimpleName() + ":the homeIndex data error!");
            }

        } else {
            if (data instanceof HomeListJson.DataBean) {
                HomeListJson.DataBean dataBean = (HomeListJson.DataBean) data;
                bindHomeListData(viewType, holder, dataBean);
            } else {
                throw new IllegalArgumentException(
                        HomePageAdapter.class.getSimpleName() + ":the homeList data error!");
            }

        }
    }

    private void bindHomeListData(int viewType, ViewHolder holder, HomeListJson.DataBean dataBean) {
        switch (viewType) {
            case Constants.TYPE_LIST_Images:
                holder.setText(R.id.tv_title, JsonIsNull.strIsNull(dataBean.getTitle()));
                holder.setText(R.id.tv_content, JsonIsNull.strIsNull(dataBean.getContent()));

                Imager.load(mContext,
                        dataBean.getImages().get(0).getUrl1(),
                        (ImageView) holder.getView(R.id.img_1));
                Imager.load(mContext,
                        dataBean.getImages().get(1).getUrl1(),
                        (ImageView) holder.getView(R.id.img_2));
                Imager.load(mContext,
                        dataBean.getImages().get(2).getUrl1(),
                        (ImageView) holder.getView(R.id.img_3));

                //publishTime and commentCount
                ((TextView) holder.getView(R.id.tv_publish_time))
                        .setText(String.format(mContext.getResources().getString(R.string.publish_time),
                                TimeUtils.getDistanceTime(dataBean.getTime()),
                                dataBean.getCommentCount()));
                break;

            case Constants.TYPE_LIST_FilmReview:
                holder.setText(R.id.tv_title, JsonIsNull.strIsNull(dataBean.getTitle()));
                holder.setText(R.id.tv_summary_info, JsonIsNull.strIsNull(dataBean.getSummaryInfo()));

                //评论的用户图像、用户名和电影名
                Imager.loadCircleImage(mContext,
                        dataBean.getUserImage(),
                        (ImageView) holder.getView(R.id.user_image),
                        DensityUtil.dp2px(10.0f));
                ((TextView) holder.getView(R.id.tv_user_name))
                        .setText(String.format(mContext.getResources().getString(R.string.user_name_comment), dataBean.getNickname()));
                ((TextView) holder.getView(R.id.tv_comment_title))
                        .setText(String.format(mContext.getResources().getString(R.string.comment_title), dataBean.getRelatedObj().getTitle()));

                //电影评分
                holder.setText(R.id.tv_rating, dataBean.getRating() + "");
                holder.getView(R.id.tv_rating)
                        .setVisibility(((String) dataBean.getRating()).equals("0.0") ?
                                View.GONE : View.VISIBLE);

                Imager.load(mContext,
                        dataBean.getRelatedObj().getImage(),
                        (ImageView) holder.getView(R.id.image));
                break;

            case Constants.TYPE_LIST_NewsWithVideo:
                holder.setText(R.id.tv_title, JsonIsNull.strIsNull(dataBean.getTitle()));
                if (dataBean.getDataType() == 1) {
                    holder.setText(R.id.tv_titlesmall, JsonIsNull.strIsNull(dataBean.getTitlesmall()));
                } else {
                    holder.setText(R.id.tv_titlesmall, JsonIsNull.strIsNull(dataBean.getContent()));
                }
                Imager.load(mContext, dataBean.getImg1(), (ImageView) holder.getView(R.id.image));
                holder.getView(R.id.iv_is_movie)
                        .setVisibility(dataBean.getDataType() == 2 ? View.VISIBLE : View.GONE);

                //publishTime and commentCount
                ((TextView) holder.getView(R.id.tv_publish_time))
                        .setText(String.format(mContext.getResources().getString(R.string.publish_time),
                                TimeUtils.getDistanceTime(dataBean.getTime()),
                                dataBean.getCommentCount()));
                break;
            case Constants.TYPE_LIST_URL:
                holder.setText(R.id.tv_title, JsonIsNull.strIsNull(dataBean.getTitle()));
                holder.setText(R.id.tv_memo, JsonIsNull.strIsNull(dataBean.getMemo()));
                Imager.load(mContext, dataBean.getPic1Url(), (ImageView) holder.getView(R.id.image));
                break;
            case Constants.TYPE_LIST_NewMovie:
                holder.setText(R.id.tv_title, JsonIsNull.strIsNull(dataBean.getTitleCn()));
                holder.setText(R.id.tv_titlesmall, JsonIsNull.strIsNull(dataBean.getTitleEn()));
                holder.setText(R.id.tv_content, JsonIsNull.strIsNull(dataBean.getContent()));

                Imager.load(mContext, dataBean.getImage(), (ImageView) holder.getView(R.id.image));
                ((WithTagImageView) holder.getView(R.id.image)).setRating(dataBean.getRating() + "");
                break;
            case Constants.TYPE_LIST_Top:
                holder.setText(R.id.tv_title, JsonIsNull.strIsNull(dataBean.getTitle()));
                holder.setText(R.id.tv_memo, JsonIsNull.strIsNull(dataBean.getMemo_movie()));

                ((WithTagImageView) holder.getView(R.id.iv_movie1_img))
                        .setRating(dataBean.getMovies().get(0).getRating() + "");
                ((WithTagImageView) holder.getView(R.id.iv_movie2_img))
                        .setRating(dataBean.getMovies().get(1).getRating() + "");
                ((WithTagImageView) holder.getView(R.id.iv_movie3_img))
                        .setRating(dataBean.getMovies().get(2).getRating() + "");

                holder.setText(R.id.tv_movie1_name,
                        JsonIsNull.strIsNull(dataBean.getMovies().get(0).getName()));
                holder.setText(R.id.tv_movie2_name,
                        JsonIsNull.strIsNull(dataBean.getMovies().get(1).getName()));
                holder.setText(R.id.tv_movie3_name,
                        JsonIsNull.strIsNull(dataBean.getMovies().get(2).getName()));

                holder.setText(R.id.tv_movie1_decade,
                        JsonIsNull.strIsNull("(" + dataBean.getMovies().get(0).getDecade() + ")"));
                holder.setText(R.id.tv_movie2_decade,
                        JsonIsNull.strIsNull("(" + dataBean.getMovies().get(1).getDecade() + ")"));
                holder.setText(R.id.tv_movie3_decade,
                        JsonIsNull.strIsNull("(" + dataBean.getMovies().get(2).getDecade() + ")"));

                Imager.load(mContext,
                        dataBean.getMovies().get(0).getPosterUrl(),
                        (ImageView) holder.getView(R.id.iv_movie1_img));
                Imager.load(mContext,
                        dataBean.getMovies().get(1).getPosterUrl(),
                        (ImageView) holder.getView(R.id.iv_movie2_img));
                Imager.load(mContext,
                        dataBean.getMovies().get(2).getPosterUrl(),
                        (ImageView) holder.getView(R.id.iv_movie3_img));
                break;
            case Constants.TYPE_LIST_Vote:
                break;
            default:
                break;
        }
        holder.setText(R.id.tv_tag, JsonIsNull.strIsNull(dataBean.getTag()));
    }

    public void relaseBanner() {
        if (banner != null) {
            banner.stopTurning();
        }
        if (advBanner != null) {
            advBanner.stopTurning();
        }
    }

    private void bindHomeIndexData(ViewHolder holder, HomeIndexJson homeIndexJson) {
        if (homeIndexJson == null) {
            Logger.e(TAG, "homeIndexJson == null");
            return;
        }

        //添加热播电影fragment
        ViewGroup view = holder.getView(R.id.hot_play_movies);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (view.getChildCount() == 0) {
            transaction.add(R.id.hot_play_movies, HotPlayMoviesFragment.newInstance("HotPlayMovie"));
        } else {
            transaction.show(HotPlayMoviesFragment.newInstance("HotPlayMovie"));
        }
        transaction.commit();

        //顶部轮播广告条
        if (homeIndexJson.getData().getTopPosters() != null &&
                homeIndexJson.getData().getTopPosters().size() > 0) {

            int size = homeIndexJson.getData().getTopPosters().size();
            List<String> list = new ArrayList<>();
            List<HomeIndexJson.DataBean.TopPostersBean.GotoPageBean> gotoPage = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                list.add(homeIndexJson.getData().getTopPosters().get(i).getImg());
                gotoPage.add(homeIndexJson.getData().getTopPosters().get(i).getGotoPage());
            }
            banner = holder.getView(R.id.topAdvBanner);
            int margin = DensityUtil.dp2px(10.0f);
            banner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                @Override
                public NetworkImageHolderView createHolder() {
                    return new NetworkImageHolderView();
                }
            }, list).setPageIndicator(new int[]{R.drawable.dot_indicator,
                    R.drawable.dot_indicator_selector}, margin / 2, 0, margin / 2, 3 * margin)
                    .setOnItemClickListener(new TopAdvBannerListener(gotoPage))
                    .startTurning(4000);
        }


        //头部模块的中间广告条
        if (homeIndexJson.getData().getAdvList() != null && homeIndexJson.getData().getAdvList().size() > 0) {
            int advSize = homeIndexJson.getData().getAdvList().size();
            List<String> advList = new ArrayList<>();
            List<HomeIndexJson.DataBean.AdvListBean.GotoPageBean> gotoPage2 = new ArrayList<>();
            for (int i = 0; i < advSize; i++) {
                advList.add(homeIndexJson.getData().getAdvList().get(i).getImg());
                gotoPage2.add(homeIndexJson.getData().getAdvList().get(i).getGotoPage());
            }
            advBanner = holder.getView(R.id.advBanner);
            advBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                @Override
                public NetworkImageHolderView createHolder() {
                    return new NetworkImageHolderView();
                }
            }, advList).setPageIndicator(new int[]{R.drawable.dot_indicator,
                    R.drawable.dot_indicator_selector})
                    .setOnItemClickListener(new MiddleAdvBannerListener(gotoPage2))
                    .startTurning(4000);
        }

        //搜索栏
//        holder.setOnClickListener(R.id.et_search, this);
//        ((EditText) holder.getView(R.id.et_search))
//                .setHint(homeIndexJson.getData().getSearchBarDescribe());
//        SPUtil.save(Constants.MOVIE_EDIT_HINT,homeIndexJson.getData().getSearchBarDescribe());


        //精彩直播
        holder.getView(R.id.ll_live)
                .setVisibility(
                        homeIndexJson.getData().getLive().getLiveId() == 0 ?
                                View.GONE : View.VISIBLE);
        if (homeIndexJson.getData().getLive().getLiveId() != 0) {

            Imager.load(mContext,
                    homeIndexJson.getData().getLive().getImg(),
                    (ImageView) holder.getView(R.id.iv_live));
            holder.getView(R.id.iv_live).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "精彩直播", Toast.LENGTH_SHORT).show();
                }
            });
        }

        //商场推荐设置
        List<HomeIndexJson.DataBean.MallRecommendsBean> mallRecommends =
                homeIndexJson.getData().getMallRecommends();
        if (mallRecommends != null && mallRecommends.size() > 0) {
            holder.setText(R.id.goods1_title, mallRecommends.get(0).getTitle());
            holder.setText(R.id.goods1_title_small, mallRecommends.get(0).getTitleSmall());
            holder.setTextColor(R.id.goods1_title,
                    Color.parseColor(mallRecommends.get(0).getTitleColor()));
            Imager.load(mContext,
                    mallRecommends.get(0).getImage(),
                    (ImageView) holder.getView(R.id.iv_goods1));

            Imager.load(mContext,
                    mallRecommends.get(1).getImage(),
                    (ImageView) holder.getView(R.id.iv_goods3));

            holder.setText(R.id.goods4_title, mallRecommends.get(2).getTitle());
            holder.setText(R.id.goods4_title_small, mallRecommends.get(2).getTitleSmall());
            holder.setTextColor(R.id.goods4_title,
                    Color.parseColor(mallRecommends.get(2).getTitleColor()));
            Imager.load(mContext,
                    mallRecommends.get(2).getImage(),
                    (ImageView) holder.getView(R.id.iv_goods4));

            holder.setText(R.id.goods5_title, mallRecommends.get(3).getTitle());
            holder.setText(R.id.goods5_title_small, mallRecommends.get(3).getTitleSmall());
            holder.setTextColor(R.id.goods5_title,
                    Color.parseColor(mallRecommends.get(3).getTitleColor()));
            Imager.load(mContext,
                    mallRecommends.get(3).getImage(),
                    (ImageView) holder.getView(R.id.iv_goods5));

            holder.setText(R.id.goods2_title,
                    mallRecommends.get(4).getTitle());
            holder.setText(R.id.goods2_title_small,
                    mallRecommends.get(4).getTitleSmall());
            holder.setTextColor(R.id.goods2_title,
                    Color.parseColor(mallRecommends.get(4).getTitleColor()));
            Imager.load(mContext,
                    mallRecommends.get(4).getImage(),
                    (ImageView) holder.getView(R.id.iv_goods2));

            holder.setText(R.id.goods6_title, mallRecommends.get(5).getTitle());
            holder.setText(R.id.goods6_title_small, mallRecommends.get(5).getTitleSmall());
            holder.setTextColor(R.id.goods6_title,
                    Color.parseColor(mallRecommends.get(5).getTitleColor()));
            Imager.load(mContext,
                    mallRecommends.get(5).getImage(),
                    (ImageView) holder.getView(R.id.iv_goods6));
        }

        //切换至“发现”模块
        final BottomNavigationBar bnb =
                (BottomNavigationBar) ((MainActivity) mContext)
                        .findViewById(R.id.nav_bar);
        holder.getView(R.id.to_discover).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bnb.selectTab(3);
            }
        });
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        int layoutId = -1;
        switch (viewType) {
            case Constants.TYPE_HEAD:
                layoutId = R.layout.home_page_head;
                break;
            case Constants.TYPE_LIST_Images:
                layoutId = R.layout.fragment_home_list_images;
                break;
            case Constants.TYPE_LIST_FilmReview:
                layoutId = R.layout.fragment_home_film_review;
                break;
            case Constants.TYPE_LIST_NewsWithVideo:
                layoutId = R.layout.fragment_home_news_with_video;
                break;
            case Constants.TYPE_LIST_URL:
                layoutId = R.layout.fragment_home_list_url;
                break;
            case Constants.TYPE_LIST_NewMovie:
                layoutId = R.layout.fragment_home_new_movie;
                break;
            case Constants.TYPE_LIST_Top:
                layoutId = R.layout.fragment_home_top_list;
                break;
            case Constants.TYPE_LIST_Vote:
                layoutId = R.layout.fragment_home_list_vote;
                break;
            default:
//                Logger.e(HomePageAdapter.class.getSimpleName(),"布局类型:"+viewType);
                break;
        }
        return layoutId;
    }

    @Override
    protected int getViewType(int position, HomeBaseData data) {
        int viewType = -1;
        if (0 == position) {
            return Constants.TYPE_HEAD;
        } else {
            if (data instanceof HomeListJson.DataBean) {
                HomeListJson.DataBean dataBean = (HomeListJson.DataBean) data;
                int type = dataBean.getType();
                switch (type) {
                    case -1:
                        viewType = Constants.TYPE_LIST_NewMovie;//欧美新片
                        break;
                    case 51:
                        if (dataBean.getDataType() == 1) {
                            viewType = Constants.TYPE_LIST_Images;//图集
                        } else {
                            viewType = Constants.TYPE_LIST_NewsWithVideo;//简讯
                        }
                        break;
                    case 64:
                        viewType = Constants.TYPE_LIST_URL;//猜电影、免费观影
                        break;
                    case 336:
                        viewType = Constants.TYPE_LIST_FilmReview;//影评
                        break;
                    case 90:
                        viewType = Constants.TYPE_LIST_Top;//榜单
                        break;
                    case 44:
                        viewType = Constants.TYPE_LIST_Vote;//投票
                        break;
                    default:
                        throw new IllegalArgumentException(HomePageAdapter.class.getSimpleName()
                                + ":HomeList中有新类型增加！");
                }
            } else {
                throw new IllegalArgumentException(HomePageAdapter.class.getSimpleName()
                        + ":the HomeListJson.DataBean data error!");
            }

        }
        return viewType;
    }

    class TopAdvBannerListener implements OnItemClickListener {
        List<HomeIndexJson.DataBean.TopPostersBean.GotoPageBean> list;

        public TopAdvBannerListener(List<HomeIndexJson.DataBean.TopPostersBean.GotoPageBean> list) {
            this.list = list;
        }

        @Override
        public void onItemClick(int position) {
            if (!list.get(position).isIsGoH5()) {
                if (list.get(position).getGotoType().equals("gotonews")) {
                    //进入新闻详细页面
                    Intent intent = new Intent(mContext, NewsDetailActivity.class);
                    int newsId = 0;
                    if (list.get(position).getParameters().getNewId() > 0) {
                        newsId = list.get(position).getParameters().getNewId();
                    } else {
                        newsId = list.get(position).getRelatedId();
                    }
                    intent.putExtra(Constants.NEWS_ID, newsId);
                    /*Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.GOTO_PAGE,list.get(position));
                    intent.putExtras(bundle);*/
                    mContext.startActivity(intent);
                } else if (list.get(position).getGotoType().equals("gotourl")) {
                    //加载网页
                    Intent intent = new Intent(mContext, WebActivity.class);
                    intent.putExtra(Constants.GOTO_URL, list.get(position).getUrl());
                    mContext.startActivity(intent);
                } else if (list.get(position).getGotoType().equals("gotoliveinfo")) {
                    //进入直播间
                } else if (list.get(position).getGotoType().equals("gotomovie")) {
                    //。。
                }

            } else {
                //进入H5页面
            }
        }
    }

    class MiddleAdvBannerListener implements OnItemClickListener {
        List<HomeIndexJson.DataBean.AdvListBean.GotoPageBean> list;

        public MiddleAdvBannerListener(List<HomeIndexJson.DataBean.AdvListBean.GotoPageBean> list) {
            this.list = list;
        }

        @Override
        public void onItemClick(int position) {
            Toast.makeText(mContext, "Mid点击了第" + position + "个:" + list.get(position).getUrl(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_search:
                mContext.startActivity(new Intent(mContext, SearchItemActivity.class));
                break;
            default:
                break;
        }
    }


}
