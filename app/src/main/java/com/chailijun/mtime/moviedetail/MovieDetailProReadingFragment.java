package com.chailijun.mtime.moviedetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chailijun.mtime.R;
import com.chailijun.mtime.activity.NewsDetailActivity;
import com.chailijun.mtime.base.BaseFragment;
import com.chailijun.mtime.data.moviedetail.ExtendMovieDetail;
import com.chailijun.mtime.data.moviedetail.MovieDetail;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.Logger;
import com.chailijun.mtime.utils.MsgEvent;
import com.chailijun.mtime.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


public class MovieDetailProReadingFragment extends BaseFragment{

    //票房
    private LinearLayout ll_box_office;
    private LinearLayout ll_showing_box_office;
    private TextView total_boxOffice;
    private TextView ranking;
    private LinearLayout llRating;
    private TextView todayBoxDes;
    private TextView todayBoxDesUnit;
    private LinearLayout llTodayBox;
    private TextView totalBoxDes;
    private TextView totalBoxUnit;
    private LinearLayout llTotalBox;
    private TextView totalAward;
    private TextView videoCount;
    private LinearLayout llAward;

    //电影解读
    private LinearLayout ll_news;
    private RelativeLayout moreVideo;
    private ImageView imageScreenshot;
    private TextView newsTitle;
    private TextView newsPublishtime;
    private LinearLayout llMovieNews;

    //电影相关事件
    private TextView eventTitle;
    private TextView eventContent;
    private LinearLayout llEvent;

    //其它
    private RecyclerView recyclerviewOther;
    private TextView moreInfo;

    private DataBankEntryAdapter mDataBankAdapter;


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

        //订阅EventBus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //取消订阅EventBus
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_movie_detail_pro_reading;
    }

    @Override
    public void initView(View view) {
        //票房
        ll_box_office = $(view,R.id.ll_box_office);
        ll_showing_box_office = $(view,R.id.ll_showing_box_office);
        total_boxOffice = $(view,R.id.total_boxOffice);
        ranking = $(view,R.id.ranking);
        llRating = $(view,R.id.ll_rating);
        todayBoxDes = $(view,R.id.todayBoxDes);
        todayBoxDesUnit = $(view,R.id.todayBoxDesUnit);
        llTodayBox = $(view,R.id.ll_todayBox);
        totalBoxDes = $(view,R.id.totalBoxDes);
        totalBoxUnit = $(view,R.id.totalBoxUnit);
        llTotalBox = $(view,R.id.ll_totalBox);
        totalAward = $(view,R.id.total_award);
        videoCount = $(view,R.id.video_count);
        llAward = $(view,R.id.ll_award);

        //电影解读
        ll_news = $(view,R.id.ll_news);
        moreVideo = $(view,R.id.more_video);
        imageScreenshot = $(view,R.id.image_screenshot);
        newsTitle = $(view,R.id.news_title);
        newsPublishtime = $(view,R.id.news_publishtime);
        llMovieNews = $(view,R.id.ll_movie_news);

        //电影相关事件
        eventTitle = $(view,R.id.event_title);
        eventContent = $(view,R.id.event_content);
        llEvent = $(view,R.id.ll_event);

        //其它
        recyclerviewOther = $(view,R.id.recyclerview_other);
        moreInfo = $(view,R.id.more_info);

        initOtherView();
    }

    /**
     * 初始化dataBankEntry对应的视图
     */
    private void initOtherView() {
        mDataBankAdapter = new DataBankEntryAdapter(null);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerviewOther.setHasFixedSize(true);
        recyclerviewOther.setLayoutManager(gridLayoutManager);
        recyclerviewOther.setAdapter(mDataBankAdapter);
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
            Logger.d(TAG, "专业解读:接收消息===电影详细===" + movieDetail.getBasic().getName());

            setBoxOfficeInfo(movieDetail);
        }

        if (msg.getMsg() instanceof ExtendMovieDetail){
            ExtendMovieDetail extendMovieDetail = (ExtendMovieDetail) msg.getMsg();
            Logger.d(TAG, "专业解读:接收消息===ExtendMovieDetail===" + extendMovieDetail.getNews().size());

            setMovieNews(extendMovieDetail.getNews());
            setMovieEvents(extendMovieDetail.getEvents());
            setOtherInfo(extendMovieDetail.getDataBankEntry());
        }
    }

    /**
     * 电影解读
     * @param news
     */
    private void setMovieNews(final List<ExtendMovieDetail.NewsBean> news) {
        if (news != null && news.size() > 0) {
            ll_news.setVisibility(View.VISIBLE);

            Imager.load(getActivity(), news.get(0).getImage(), imageScreenshot);
            newsTitle.setText(news.get(0).getTitle());
            newsPublishtime.setText(TimeUtils.getDistanceTime(news.get(0).getPublishTime()));

            llMovieNews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                    intent.putExtra(Constants.NEWS_ID,news.get(0).getId());
                    startActivity(intent);
                }
            });
        }else {
            ll_news.setVisibility(View.GONE);
        }
    }

    /**
     * 该片你该了解的n件事
     * @param events
     */
    private void setMovieEvents(ExtendMovieDetail.EventsBean events) {
        if (events.getEventCount() > 0){
            llEvent.setVisibility(View.VISIBLE);
            eventTitle.setText(events.getTitle());
            eventContent.setText(events.getList().get(0));
        }else {
            llEvent.setVisibility(View.GONE);
        }
    }

    /**
     * 制作幕后等相关信息
     * @param dataBankEntry
     */
    private void setOtherInfo(ExtendMovieDetail.DataBankEntryBean dataBankEntry) {

        List<Bean> datas = new ArrayList<>();

        //媒体评论
        if (dataBankEntry.isIsMediaReview()){
            Bean bean = new Bean();
            bean.setImageResId(R.drawable.relation_icon_02);
            bean.setDesc(getString(R.string.media_review));
            bean.setDesc2(String.format(getString(R.string.media_review_count),dataBankEntry.getMediaReviewCount()));
            datas.add(bean);
        }
        if (dataBankEntry.isIsCompany()){
            Bean bean = new Bean();
            bean.setImageResId(R.drawable.relation_icon_04);
            bean.setDesc(getString(R.string.production_issue));
            bean.setDesc2(String.format(getString(R.string.company_count),dataBankEntry.getCompanyCount()));
            datas.add(bean);
        }
        if (dataBankEntry.isIsBehind()){
            Bean bean = new Bean();
            bean.setImageResId(R.drawable.relation_icon_05);
            bean.setDesc(getString(R.string.behind));
            bean.setDesc2(getString(R.string.media_reports));
            datas.add(bean);
        }
        if (datas.size()%2 != 0){
            moreInfo.setVisibility(View.GONE);
            Bean bean = new Bean();
            bean.setImageResId(R.drawable.relation_icon_06);
            bean.setDesc(getString(R.string.more_info));
            bean.setDesc2(getString(R.string.other_more_info));
            datas.add(bean);
        }else {
            moreInfo.setVisibility(View.VISIBLE);
        }
        mDataBankAdapter.setNewData(datas);
    }

    /**
     * 设置票房信息
     * @param movieDetail
     */
    private void setBoxOfficeInfo(MovieDetail movieDetail) {
        MovieDetail.BasicBean.AwardBean award = movieDetail.getBasic().getAward();
        //电影提名次数
        int totalNominateAward = award.getTotalNominateAward();
        //电影获奖次数
        int totalWinAward = award.getTotalWinAward();
        if (totalNominateAward > 0 || totalWinAward > 0) {
            llAward.setVisibility(View.VISIBLE);

            if (totalNominateAward != 0 && totalWinAward == 0){
                //共获提名%1$d次
                totalAward.setText(String.format(getString(R.string.totalNominateAward), totalNominateAward));
            }else if (totalNominateAward == 0 && totalWinAward != 0){
                //共获奖%1$d次
                totalAward.setText(String.format(getString(R.string.totalWinAward), totalNominateAward));
            }else {
                //共获奖%1$d次，提名%2$d次
                totalAward.setText(String.format(getString(R.string.award_list), totalWinAward, totalNominateAward));
            }
        }else {
            llAward.setVisibility(View.GONE);
        }

        MovieDetail.BoxOfficeBean boxOffice = movieDetail.getBoxOffice();
        if (boxOffice.getRanking() == 0 && boxOffice.getTodayBox() == 0 &&boxOffice.getTotalBox() == 0){
            //未上映的电影隐藏票房模块
            ll_box_office.setVisibility(View.GONE);
        }else if (boxOffice.getRanking() != 0 && boxOffice.getTodayBox() != 0){
            //正在上映的电影
            ll_box_office.setVisibility(View.VISIBLE);
            ll_showing_box_office.setVisibility(View.VISIBLE);
            total_boxOffice.setVisibility(View.GONE);

            ranking.setText(boxOffice.getRanking() + "");
            todayBoxDes.setText(boxOffice.getTodayBoxDes());
            todayBoxDesUnit.setText(boxOffice.getTodayBoxDesUnit());
            totalBoxDes.setText(boxOffice.getTotalBoxDes());
            totalBoxUnit.setText(boxOffice.getTotalBoxUnit());
        }else {
            //已过上映期的电影
            ll_box_office.setVisibility(View.VISIBLE);
            ll_showing_box_office.setVisibility(View.GONE);
            total_boxOffice.setVisibility(View.VISIBLE);

            total_boxOffice.setText(String.format(getString(R.string.total_boxOffice),boxOffice.getTotalBoxDes()));
        }
    }

    class Bean{

        int imageResId;
        String desc;
        String desc2;

        public String getDesc2() {
            return desc2;
        }

        public void setDesc2(String desc2) {
            this.desc2 = desc2;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getImageResId() {
            return imageResId;
        }

        public void setImageResId(int imageResId) {
            this.imageResId = imageResId;
        }
    }


}
