package com.chailijun.mtime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chailijun.mtime.R;
import com.chailijun.mtime.customview.BorderTextView;
import com.chailijun.mtime.customview.MyScrollView;
import com.chailijun.mtime.customview.MyTabLayout;
import com.chailijun.mtime.fragment.MovieDetailBaseInfoFragment;
import com.chailijun.mtime.fragment.MovieDetailProReadingFragment;
import com.chailijun.mtime.mvp.interf.IMoviePresenter;
import com.chailijun.mtime.mvp.interf.IMovieView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.mvp.model.movie.ExtendMovieDetailJson;
import com.chailijun.mtime.mvp.model.movie.HotCommentJson;
import com.chailijun.mtime.mvp.model.movie.MovieDetailJson;
import com.chailijun.mtime.mvp.presenter.MoviePresenter;
import com.chailijun.mtime.utils.BitmapUtil;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.Logger;
import com.chailijun.mtime.utils.MsgEvent;
import com.chailijun.mtime.utils.SPUtil;
import com.chailijun.mtime.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

@Deprecated
public class MovieDetailActivity extends BaseActivity implements IMovieView<BaseData> {

//    @BindView(R.id.toolbar)
//    Toolbar toolbar;

//    @BindView(R.id.collapsingToolbar)
//    CollapsingToolbarLayout collapsingToolbar;
//    @BindView(R.id.rl_toolbar)
    RelativeLayout rl_toolbar;
    @BindView(R.id.tabs)
    MyTabLayout mTabLayout;
    @BindView(R.id.tabs2)
    MyTabLayout mTabLayout2;
    @BindView(R.id.container)
    FrameLayout mContainer;
    @BindView(R.id.scroll_view)
    MyScrollView scrollView;
    @BindView(R.id.movie_detail_head)
    RelativeLayout mTopView;

    @BindView(R.id.head_title)
    TextView headTitle;
    @BindView(R.id.iv_movie_img)
    ImageView movieImg;
    @BindView(R.id.isHasVideo)
    ImageView isHasVideo;
    @BindView(R.id.img_bg_head)
    ImageView headBg;
    @BindView(R.id.tv_movie_name)
    TextView movieName;
    @BindView(R.id.tv_movie_name_en)
    TextView movieNameEn;
    @BindView(R.id.tv_movie_duration)
    TextView movieDuration;
    @BindView(R.id.tv_movie_type)
    TextView movieType;
    @BindView(R.id.tv_release_date)
    TextView releaseDate;
    @BindView(R.id.tv_comment_special)
    TextView commentSpecial;
    @BindView(R.id.tv_overall_rating)
    TextView overallRating;
    @BindView(R.id.tag_dmax)
    BorderTextView tag_dmax;
    @BindView(R.id.tag_imax3d)
    BorderTextView tag_imax3d;
    @BindView(R.id.tag_3d)
    BorderTextView tag_3d;
    @BindView(R.id.tag_imax)
    BorderTextView tag_imax;
    @BindView(R.id.btn_sale)
    Button btn_sale;

    private IMoviePresenter presenter;
    private int locationId;
    private int movieId;
    private ActionBar actionBar;
    private MovieDetailJson detailJson;
    private ExtendMovieDetailJson extendDetailJson;

    @Override
    protected int getLayoutId() {

        return R.layout.activity_movie_detail1;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
//        initAppBar();
        getData();
        initTabLayout();

        requestData();


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    private void requestData() {
        presenter = new MoviePresenter(this);
        if (locationId != 0 && movieId != 0) {
            presenter.getMovieDetail((int)locationId, movieId);
            presenter.getHotComment(movieId);
            presenter.getExtendMovieDetail(movieId);
        }
    }

    private void getData() {
        locationId = SPUtil.getInt(Constants.LOCATION_ID);

        Intent intent = getIntent();
        movieId = intent.getIntExtra(Constants.MOVIE_ID, 0);
    }


    private void initTabLayout() {

        List<String> titles = new ArrayList<>();
        titles.add(getString(R.string.base_info));
        titles.add(getString(R.string.pro_reading));
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(MovieDetailBaseInfoFragment.newInstance(movieId));
        fragments.add(MovieDetailProReadingFragment.newInstance("ProReading"));

        //设置tab-->设置fragments-->设置默认显示fragment-->绑定另一个MyTablayout-->设置指示器左右边距
        mTabLayout.setTab(titles)
                .setFragments(getSupportFragmentManager(),mContainer,fragments)
                .setDefaultFragment(0)
                .bind(mTabLayout2)
                .setIndicator(50,50);
        mTabLayout2.setIndicator(50,50);

        //scrollView滑动监听
        scrollView.setScrollViewListener(new MyScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(int x, int y, int oldx, int oldy) {

                if (mTopView != null) {
                    mTabLayout2.setVisibility(y >= mTopView.getHeight()?View.VISIBLE:View.GONE);
                }
            }
        });
    }

    @OnClick({R.id.back,R.id.share,R.id.favorite})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.action_share:
                Toast.makeText(MovieDetailActivity.this, "分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_favorite:
                Toast.makeText(MovieDetailActivity.this, "收藏", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void addMovieDetail(BaseData data) {
        if (data instanceof MovieDetailJson) {
            detailJson = (MovieDetailJson) data;

            Imager.load(this, detailJson.getData().getBasic().getImg(), movieImg);
            if (detailJson.getData().getBasic().getVideo().getCount() > 0 && movieId > 0){
                isHasVideo.setVisibility(View.VISIBLE);
                movieImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MovieDetailActivity.this,VideoListActivity.class);
                        intent.putExtra(Constants.MOVIE_ID,movieId);
                        startActivity(intent);
                    }
                });
            }else {
                isHasVideo.setVisibility(View.GONE);
            }

            BitmapUtil.setBlurBackground(this,detailJson.getData().getBasic().getImg(),headBg);
            headTitle.setText(detailJson.getData().getBasic().getName());
            movieName.setText(detailJson.getData().getBasic().getName());
            movieNameEn.setText(detailJson.getData().getBasic().getNameEn());

            if(detailJson.getData().getBasic().isIsEggHunt()){
//                movieDuration.setText(Html.fromHtml(getString(R.string.movie_duration))+detailJson.getData().getBasic().getMins());
//
//                String type = String.format(getString(R.string.wantseen_count2), data.getWantedCount(), data.getType() + " / " + data.getLocationName());
                movieDuration.setText(Html.fromHtml(getString(R.string.movie_duration)));
                movieDuration.append(detailJson.getData().getBasic().getMins());
            }else {
                movieDuration.setText(detailJson.getData().getBasic().getMins());
            }

            if (!TextUtils.isEmpty(detailJson.getData().getBasic().getCommentSpecial())){
                commentSpecial.setVisibility(View.VISIBLE);
                commentSpecial.setText(detailJson.getData().getBasic().getCommentSpecial());
            }else {
                commentSpecial.setVisibility(View.GONE);
            }

            if (detailJson.getData().getBasic().getOverallRating() > 0){
                overallRating.setVisibility(View.VISIBLE);
                overallRating.setText(detailJson.getData().getBasic().getOverallRating()+"");
            }else {
                overallRating.setVisibility(View.GONE);
            }

            List<String> typeList = detailJson.getData().getBasic().getType();
            StringBuilder type = new StringBuilder();
            for (int i = 0; i < typeList.size(); i++) {
                type.append(typeList.get(i));
                if (i < typeList.size() - 1){
                    type.append("/");
                }
            }
            movieType.setText(type);

            String releaseDate = detailJson.getData().getBasic().getReleaseDate();
            String releaseArea = detailJson.getData().getBasic().getReleaseArea();
            if (!releaseDate.equals("")&&!releaseArea.equals("")){
                String year = releaseDate.substring(0, 4);
                String month = releaseDate.substring(4, 6);
                String day = releaseDate.substring(6);
                this.releaseDate.setText(String.format(getString(R.string.release_date_area),year,month,day,releaseArea));
            }

            MovieDetailJson.DataBean.BasicBean basic = detailJson.getData().getBasic();
            tag_dmax.setVisibility(basic.isIsDMAX()?View.VISIBLE:View.GONE);
            tag_imax3d.setVisibility(basic.isIsIMAX3D()?View.VISIBLE:View.GONE);
            tag_3d.setVisibility(basic.isIs3D()?View.VISIBLE:View.GONE);
            tag_imax.setVisibility(basic.isIsIMAX()?View.VISIBLE:View.GONE);

            //预售、选座购票或售罄
            if (basic.isIsTicket()){
                boolean isAdvanceSale = TimeUtils.isAdvanceSale(basic.getReleaseDate(),basic.getShowDay(),"yyyyMMdd");
                if (isAdvanceSale){
                    btn_sale.setBackgroundColor(ContextCompat.getColor(this,R.color.colorBGYingping));
                    btn_sale.setText(getString(R.string.advance_sale));
                }else {
                    btn_sale.setBackgroundColor(ContextCompat.getColor(this,R.color.colorOrange));
                    btn_sale.setText(getString(R.string.select_sale));
                }
            }else {
                btn_sale.setBackgroundColor(ContextCompat.getColor(this,R.color.colorEditHint));
                btn_sale.setText(getString(R.string.no_tickets));
            }

            //发布消息-->向fragment中传递数据
            EventBus.getDefault().post(new MsgEvent(detailJson));
        }
    }

    //接收EventBus主程序发送的消息
    public void onEventMainThread(MsgEvent msg) {
        if(msg.getWhat() == Constants.MSG_WHAT){
            msg.setWhat(0);
            Logger.e("TAG","MovieDetailActivity==重新发送消息");
            //重新发送消息
            MsgEvent msgEvent = new MsgEvent(detailJson.getData().getBoxOffice());
            msgEvent.setMsg1(detailJson.getData().getBasic().getAward().getTotalNominateAward());//提名次数
            msgEvent.setMsg2(detailJson.getData().getBasic().getAward().getTotalWinAward());//获奖次数

            EventBus.getDefault().post(msgEvent);

            EventBus.getDefault().post(new MsgEvent(extendDetailJson));
        }
    }

    @Override
    public void addExtendMovieDetail(BaseData data) {
        if (data instanceof ExtendMovieDetailJson){
            extendDetailJson = (ExtendMovieDetailJson) data;
        }
    }

    @Override
    public void addHotComment(BaseData data) {
        if (data instanceof HotCommentJson){
            HotCommentJson hotCommentJson = (HotCommentJson) data;
            EventBus.getDefault().post(new MsgEvent(hotCommentJson));
        }
    }

    @Override
    public void loadFailed(String msg) {
        if (msg.equals(Constants.MOVIE_DETAIL)) {
            Logger.e("TAG", "电影详细加载失败");
        }
        if (msg.equals(Constants.MOVIE_HOT_COMMENT)) {
            Logger.e("TAG", "电影详细--短评--加载失败");
        }
        if (msg.equals(Constants.EXTEND_MOVIE_DETAIL)) {
            Logger.e("TAG", "电影详细--extendMovieDetail--加载失败");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unsubcrible();

    }
}
