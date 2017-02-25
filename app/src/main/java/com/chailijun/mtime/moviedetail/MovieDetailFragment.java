package com.chailijun.mtime.moviedetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chailijun.mtime.R;
import com.chailijun.mtime.activity.VideoListActivity;
import com.chailijun.mtime.base.BaseFragment;
import com.chailijun.mtime.customview.BorderTextView;
import com.chailijun.mtime.customview.MyScrollView;
import com.chailijun.mtime.customview.MyTabLayout;
import com.chailijun.mtime.data.moviedetail.ExtendMovieDetail;
import com.chailijun.mtime.data.moviedetail.HotComment;
import com.chailijun.mtime.data.moviedetail.MovieDetail;
import com.chailijun.mtime.utils.BitmapUtil;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.Logger;
import com.chailijun.mtime.utils.MsgEvent;
import com.chailijun.mtime.utils.SPUtil;
import com.chailijun.mtime.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class MovieDetailFragment extends BaseFragment implements MovieDetailContract.View{

    public static final String ARGUMENT = "argument";

    private MyTabLayout mTabLayout;
    private MyTabLayout mTabLayout2;
    private FrameLayout mContainer;
    private MyScrollView scrollView;

    /**
     * 电影的基本信息布局
     */
    private RelativeLayout mTopView;

    private ImageView movieImg;
    private ImageView isHasVideo;
    private ImageView headBg;
    private TextView movieName;
    private TextView movieNameEn;
    private TextView movieDuration;
    private TextView movieType;
    private TextView releaseDate;
    private TextView commentSpecial;
    private TextView overallRating;
    private BorderTextView tag_dmax;
    private BorderTextView tag_imax3d;
    private BorderTextView tag_3d;
    private BorderTextView tag_imax;
    private Button btn_sale;

    //工具栏
    private LinearLayout mTitleBg;
    private TextView mHeadTitle;
    private ImageView back;
    private ImageView share;
    private ImageView favorite;

    //加载中
    private LinearLayout loading;
    //加载失败
    private LinearLayout loading_failed;

    private int movieId;
    private int locationId;

    private MovieDetailContract.Presenter mPresenter;

    public static MovieDetailFragment newInstance(int movieId) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARGUMENT, movieId);

        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        movieId = arguments.getInt(ARGUMENT,0);

        locationId = SPUtil.getInt(Constants.LOCATION_ID);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_movie_detail;
    }

    @Override
    public void initView(View view) {
        mTabLayout = $(view,R.id.tabs);
        mTabLayout2 = $(view,R.id.tabs2);
        mContainer = $(view,R.id.container);
        scrollView = $(view,R.id.scroll_view);
        mTopView = $(view,R.id.movie_detail_head);

        movieImg = $(view,R.id.iv_movie_img);
        isHasVideo = $(view,R.id.isHasVideo);
        headBg = $(view,R.id.img_bg_head);
        movieName = $(view,R.id.tv_movie_name);
        movieNameEn = $(view,R.id.tv_movie_name_en);
        movieDuration = $(view,R.id.tv_movie_duration);
        movieType = $(view,R.id.tv_movie_type);
        releaseDate = $(view,R.id.tv_release_date);
        commentSpecial = $(view,R.id.tv_comment_special);
        overallRating = $(view,R.id.tv_overall_rating);
        tag_dmax = $(view,R.id.tag_dmax);
        tag_imax3d = $(view,R.id.tag_imax3d);
        tag_3d = $(view,R.id.tag_3d);
        tag_imax = $(view,R.id.tag_imax);
        btn_sale = $(view,R.id.btn_sale);

        //工具栏
        mTitleBg = $(view,R.id.head_title_bg);
        mHeadTitle = $(view,R.id.head_title);
        back = $(view,R.id.back);
        share = $(view,R.id.share);
        favorite = $(view,R.id.favorite);

        //加载中
        loading = $(view,R.id.loading);
        //加载失败
        loading_failed = $(view,R.id.loading_failed);

        mTitleBg.setAlpha(0);

        initTabLayout();

    }

    @Override
    public void setListener() {
        super.setListener();
        back.setOnClickListener(this);
        share.setOnClickListener(this);
        favorite.setOnClickListener(this);

        loading_failed.setOnClickListener(this);
    }

    /**
     * 初始化tabLayout以及关联的fragment
     */
    private void initTabLayout() {

        List<String> titles = new ArrayList<>();
        titles.add(getString(R.string.base_info));
        titles.add(getString(R.string.pro_reading));
        List<Fragment> fragments = new ArrayList<>();

        fragments.add(MovieDetailBaseInfoFragment.newInstance(movieId));
        fragments.add(new MovieDetailProReadingFragment());

        //设置tab-->设置fragments-->设置默认显示fragment-->绑定另一个MyTablayout-->设置指示器左右边距
        mTabLayout.setTab(titles)
                .setFragments(getChildFragmentManager(), mContainer, fragments)
                .setDefaultFragment(1)  //初始化"专业解读"fragment,否则接收不到消息
                .setDefaultFragment(0)  //显示"基本资料"fragment
                .bind(mTabLayout2)
                .setIndicator(50, 50);
        mTabLayout2.setIndicator(50, 50);

        //scrollView滑动监听
        scrollView.setScrollViewListener(new MyScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(int x, int y, int oldx, int oldy) {

                if (mTopView != null) {
                    //向上滚动该位移时，mTabLayout2显示。（顶部悬停）
                    float disY = mTopView.getHeight() - getResources().getDimension(R.dimen.head_height);
                    mTabLayout2.setVisibility(y >= disY ? VISIBLE : GONE);

                    //设置搜索栏背景透明度
                    if (y < disY){
                        mTitleBg.setAlpha(1.0f/disY * (float)y);
                    }else {
                        mTitleBg.setAlpha(1.0f);
                    }
                }

            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        Logger.d(TAG,"城市:"+locationId+" 电影:"+movieId);

        mPresenter.subscribe();

        if (locationId != 0 && movieId != 0){
            mPresenter.loadMovieDetail(locationId,movieId);
            mPresenter.loadExtendMovieDetail(movieId);
            mPresenter.loadHotComment(movieId);
        }
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                getActivity().finish();
                break;
            case R.id.action_share:
                showToast("分享");
                break;
            case R.id.action_favorite:
                showToast("收藏");
                break;
            case R.id.loading_failed:
                onRefresh();
                break;
            default:
                break;
        }
    }

    private void onRefresh() {
        showLoadingError(false);
        showLoading(true);
        doBusiness(getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void showLoading(boolean active) {
        if (loading != null && loading_failed != null){
            if (active){
                //加载失败--->GONE
                if (loading_failed.getVisibility() == VISIBLE){
                    loading_failed.setVisibility(GONE);
                }
                //加载中--->VISIBLE
                if (loading.getVisibility() == GONE){
                    loading.setVisibility(VISIBLE);
                }
            }else {
                //加载中--->GONE
                if (loading.getVisibility() == VISIBLE){
                    loading.setVisibility(GONE);
                }
            }
        }
    }

    @Override
    public void showLoadingError(boolean active) {

        if (loading_failed != null && loading != null){
            if (active){
                //加载中--->GONE
                if (loading.getVisibility() == VISIBLE){
                    loading.setVisibility(GONE);
                }
                //加载失败--->VISIBLE
                if (loading_failed.getVisibility() == GONE){
                    loading_failed.setVisibility(VISIBLE);
                }
            }else {
                //加载失败--->GONE
                if (loading_failed.getVisibility() == VISIBLE){
                    loading_failed.setVisibility(GONE);
                }

            }
        }
    }

    @Override
    public void showMovieDetail(MovieDetail movieDetail) {
        Logger.d(TAG,"电影详细加载成功:"+movieDetail.getBasic().getName());

        setMovieBaseInfo(movieDetail);

        //向子fragment中传递数据
        EventBus.getDefault().post(new MsgEvent(movieDetail));
    }

    @Override
    public void showLoadingMovieDetailError(String msg) {
        Logger.e(TAG,"电影详细加载失败:"+msg);

    }

    @Override
    public void showExtendMovieDetail(ExtendMovieDetail extendMovieDetail) {
        Logger.d(TAG,"电影ExtendMovieDetail加载成功:"+extendMovieDetail.getNews().get(0).getTitle());

        //向子fragment中传递数据
        EventBus.getDefault().post(new MsgEvent(extendMovieDetail));
    }

    @Override
    public void showLoadingExtendMovieDetailError(String msg) {
        Logger.e(TAG,"电影ExtendMovieDetail加载失败:"+msg);

    }

    @Override
    public void showHotComment(HotComment hotComment) {
        Logger.d(TAG,"电影HotComment加载成功:"+hotComment.getMini().getList().get(0).getContent());

        //向子fragment中传递数据
        EventBus.getDefault().post(new MsgEvent(hotComment));
    }

    @Override
    public void showLoadingHotCommentError(String msg) {
        Logger.e(TAG,"电影HotComment加载失败:"+msg);

    }

    @Override
    public void setPresenter(MovieDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 设置电影基本信息
     * @param movieDetail
     */
    private void setMovieBaseInfo(MovieDetail movieDetail) {
        //电影海报
        Imager.load(getActivity(), movieDetail.getBasic().getImg(), movieImg);

        //设置海报的点击监听
        if (movieDetail.getBasic().getVideo().getCount() > 0 && movieId > 0) {
            isHasVideo.setVisibility(VISIBLE);
            movieImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), VideoListActivity.class);
                    intent.putExtra(Constants.MOVIE_ID, movieId);
                    startActivity(intent);
                }
            });
        } else {
            isHasVideo.setVisibility(GONE);
        }

        //毛玻璃背景
        BitmapUtil.setBlurBackground(getActivity(), movieDetail.getBasic().getImg(), headBg);
        mHeadTitle.setText(movieDetail.getBasic().getName());

        //电影名字
        movieName.setText(movieDetail.getBasic().getName());
        movieNameEn.setText(movieDetail.getBasic().getNameEn());

        //彩蛋--时长
        if (movieDetail.getBasic().isIsEggHunt()) {
            movieDuration.setText(Html.fromHtml(getString(R.string.movie_duration)));
            movieDuration.append(movieDetail.getBasic().getMins());
        } else {
            movieDuration.setText(movieDetail.getBasic().getMins());
        }

        //一句话简评
        if (!TextUtils.isEmpty(movieDetail.getBasic().getCommentSpecial())) {
            commentSpecial.setVisibility(VISIBLE);
            commentSpecial.setText(movieDetail.getBasic().getCommentSpecial());
        } else {
            commentSpecial.setVisibility(GONE);
        }

        //电影评分
        if (movieDetail.getBasic().getOverallRating() > 0) {
            overallRating.setVisibility(VISIBLE);
            overallRating.setText(movieDetail.getBasic().getOverallRating() + "");
        } else {
            overallRating.setVisibility(GONE);
        }

        //电影类型
        List<String> typeList = movieDetail.getBasic().getType();
        StringBuilder type = new StringBuilder();
        for (int i = 0; i < typeList.size(); i++) {
            type.append(typeList.get(i));
            if (i < typeList.size() - 1) {
                type.append("/");
            }
        }
        movieType.setText(type);

        //上映日期和地区
        String releaseDate = movieDetail.getBasic().getReleaseDate();
        String releaseArea = movieDetail.getBasic().getReleaseArea();
        if (!releaseDate.equals("") && !releaseArea.equals("")) {
            String year = releaseDate.substring(0, 4);
            String month = releaseDate.substring(4, 6);
            String day = releaseDate.substring(6);
            this.releaseDate.setText(String.format(getString(R.string.release_date_area), year, month, day, releaseArea));
        }

        //电影格式
        MovieDetail.BasicBean basic = movieDetail.getBasic();
        tag_dmax.setVisibility(basic.isIsDMAX() ? VISIBLE : GONE);
        tag_imax3d.setVisibility(basic.isIsIMAX3D() ? VISIBLE : GONE);
        tag_3d.setVisibility(basic.isIs3D() ? VISIBLE : GONE);
        tag_imax.setVisibility(basic.isIsIMAX() ? VISIBLE : GONE);

        //预售、选座购票或售罄
        if (basic.isIsTicket()) {
            boolean isAdvanceSale = TimeUtils.isAdvanceSale(basic.getReleaseDate(), basic.getShowDay(), "yyyyMMdd");
            if (isAdvanceSale) {
                btn_sale.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorBGYingping));
                btn_sale.setText(getString(R.string.advance_sale));
            } else {
                btn_sale.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorOrange));
                btn_sale.setText(getString(R.string.select_sale));
            }
        } else {
            btn_sale.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorEditHint));
            btn_sale.setText(getString(R.string.no_tickets));
        }
    }
}
