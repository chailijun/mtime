package com.chailijun.mtime.fragment;

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
import android.widget.Toast;

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.base.CommonBaseAdapter;
import com.chailijun.mtime.R;
import com.chailijun.mtime.activity.NewsDetailActivity;
import com.chailijun.mtime.mvp.model.movie.ExtendMovieDetailJson;
import com.chailijun.mtime.mvp.model.movie.MovieDetailJson;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Logger;
import com.chailijun.mtime.utils.TimeUtils;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.MsgEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

@Deprecated
public class MovieDetailProReadingFragment extends BaseFragment {

    //票房
    @BindView(R.id.ll_box_office)
    LinearLayout ll_box_office;
    @BindView(R.id.ll_showing_box_office)
    LinearLayout ll_showing_box_office;
    @BindView(R.id.total_boxOffice)
    TextView total_boxOffice;
    @BindView(R.id.ranking)
    TextView ranking;
    @BindView(R.id.ll_rating)
    LinearLayout llRating;
    @BindView(R.id.todayBoxDes)
    TextView todayBoxDes;
    @BindView(R.id.todayBoxDesUnit)
    TextView todayBoxDesUnit;
    @BindView(R.id.ll_todayBox)
    LinearLayout llTodayBox;
    @BindView(R.id.totalBoxDes)
    TextView totalBoxDes;
    @BindView(R.id.totalBoxUnit)
    TextView totalBoxUnit;
    @BindView(R.id.ll_totalBox)
    LinearLayout llTotalBox;
    @BindView(R.id.total_award)
    TextView totalAward;
    @BindView(R.id.video_count)
    TextView videoCount;
    @BindView(R.id.ll_award)
    LinearLayout llAward;

    //电影解读
    @BindView(R.id.ll_news)
    LinearLayout ll_news;
    @BindView(R.id.more_video)
    RelativeLayout moreVideo;
    @BindView(R.id.image_screenshot)
    ImageView imageScreenshot;
    @BindView(R.id.news_title)
    TextView newsTitle;
    @BindView(R.id.news_publishtime)
    TextView newsPublishtime;
    @BindView(R.id.ll_movie_news)
    LinearLayout llMovieNews;

    //电影相关事件
    @BindView(R.id.event_title)
    TextView eventTitle;
    @BindView(R.id.event_content)
    TextView eventContent;
    @BindView(R.id.ll_event)
    LinearLayout llEvent;

    //其它
    @BindView(R.id.recyclerview_other)
    RecyclerView recyclerviewOther;
    @BindView(R.id.more_info)
    TextView moreInfo;


    public static MovieDetailProReadingFragment newInstance(String param1) {
        MovieDetailProReadingFragment fragment = new MovieDetailProReadingFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public String getFragmentName() {
        return "电影详细--专业解读";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.e("CLJ","ProRead--->onCreate()");
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_movie_detail_pro_reading;
    }

    @Override
    protected void initViews(View rootView) {

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
        //fragment可见时，向MovieDetailActivit请求发送消息
        MsgEvent msgEvent = new MsgEvent();
        msgEvent.setWhat(Constants.MSG_WHAT);
        EventBus.getDefault().post(msgEvent);
    }

    //接收来自MovieDetailActivity的主线程发送的消息
    public void onEventMainThread(MsgEvent msg) {
        if (msg.getMsg() instanceof MovieDetailJson.DataBean.BoxOfficeBean) {
            Logger.e("TAG","ProRead==收到==BoxOfficeBean");

            MovieDetailJson.DataBean.BoxOfficeBean boxOfficeBean = (MovieDetailJson.DataBean.BoxOfficeBean) msg.getMsg();

            setBoxOfficeInfo(msg,boxOfficeBean);
        }
        if (msg.getMsg() instanceof ExtendMovieDetailJson) {
            Logger.e("TAG","ProRead==收到==ExtendMovieDetailJson");
            ExtendMovieDetailJson extendMovieDetailJson = (ExtendMovieDetailJson) msg.getMsg();
            setMovieReading(extendMovieDetailJson);
            setEvents(extendMovieDetailJson);
            setOtherInfo(extendMovieDetailJson);
        }
    }

    /**
     * "媒体评论"、"制作发行"、"幕后制作"、"更多资料"
     * @param extendMovieDetailJson
     */
    private void setOtherInfo(ExtendMovieDetailJson extendMovieDetailJson) {

        List<Bean> datas = new ArrayList<>();
        if (extendMovieDetailJson.getDataBankEntry().isIsMediaReview()){
            Bean bean = new Bean();
            bean.setImageResId(R.drawable.relation_icon_02);
            bean.setDesc(getString(R.string.media_review));
            bean.setDesc2(String.format(getString(R.string.media_review_count),extendMovieDetailJson.getDataBankEntry().getMediaReviewCount()));
            datas.add(bean);
        }
        if (extendMovieDetailJson.getDataBankEntry().isIsCompany()){
            Bean bean = new Bean();
            bean.setImageResId(R.drawable.relation_icon_04);
            bean.setDesc(getString(R.string.production_issue));
            bean.setDesc2(String.format(getString(R.string.company_count),extendMovieDetailJson.getDataBankEntry().getCompanyCount()));
            datas.add(bean);
        }
        if (extendMovieDetailJson.getDataBankEntry().isIsBehind()){
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
        MyAdapter adapter = new MyAdapter(getActivity(),datas,false);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerviewOther.setLayoutManager(gridLayoutManager);
        recyclerviewOther.setAdapter(adapter);
    }

    /**
     * 设置电影相关事件信息
     *
     * @param extendMovieDetailJson
     */
    private void setEvents(ExtendMovieDetailJson extendMovieDetailJson) {
        if (extendMovieDetailJson.getEvents().getEventCount() > 0){
            llEvent.setVisibility(View.VISIBLE);
            eventTitle.setText(extendMovieDetailJson.getEvents().getTitle());
            eventContent.setText(extendMovieDetailJson.getEvents().getList().get(0));
        }else {
            llEvent.setVisibility(View.GONE);
        }
    }

    /**
     * 设置电影解读
     *
     * @param extendMovieDetailJson
     */
    private void setMovieReading(final ExtendMovieDetailJson extendMovieDetailJson) {
        if (extendMovieDetailJson.getNews() != null && extendMovieDetailJson.getNews().size() > 0) {
            ll_news.setVisibility(View.VISIBLE);
            Imager.load(getActivity(), extendMovieDetailJson.getNews().get(0).getImage(), imageScreenshot);
            newsTitle.setText(extendMovieDetailJson.getNews().get(0).getTitle());
            newsPublishtime.setText(TimeUtils.getDistanceTime(extendMovieDetailJson.getNews().get(0).getPublishTime()));

            llMovieNews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                    intent.putExtra(Constants.NEWS_ID,extendMovieDetailJson.getNews().get(0).getId());
                    getActivity().startActivity(intent);
                }
            });
        }else {
            ll_news.setVisibility(View.GONE);
        }
    }

    /**
     * 设置票房信息
     *
     * @param boxOffice
     */
    private void setBoxOfficeInfo(MsgEvent msg,MovieDetailJson.DataBean.BoxOfficeBean boxOffice) {
        //提名、获奖次数
        int totalNominateAward = (int) msg.getMsg1();
        int totalWinAward = (int) msg.getMsg2();
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
        } else {
            llAward.setVisibility(View.GONE);
        }

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


    @OnClick({R.id.ll_rating, R.id.ll_todayBox, R.id.ll_totalBox, R.id.ll_award,
            R.id.more_video, R.id.ll_movie_news,R.id.ll_event,R.id.more_info})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_rating:
                break;
            case R.id.ll_todayBox:
                break;
            case R.id.ll_totalBox:
                break;
            case R.id.ll_award:
                break;
            case R.id.more_video:
                break;
            case R.id.ll_movie_news:
                break;
            case R.id.ll_event:
                break;
            case R.id.more_info:
                Toast.makeText(getActivity(), "更多内容", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    class MyAdapter extends CommonBaseAdapter<Bean>{

        public MyAdapter(Context context, List<Bean> datas, boolean isOpenLoadMore) {
            super(context, datas, isOpenLoadMore);
        }

        @Override
        protected void convert(ViewHolder holder, Bean data, int position) {
            holder.setBgRes(R.id.iv_icon,data.getImageResId());
            holder.setText(R.id.tv_desc,data.getDesc());
            holder.setText(R.id.tv_desc2,data.getDesc2());
        }

        @Override
        protected int getItemLayoutId() {
            return R.layout.fragment_movie_detail_pro_reading_other_item;
        }
    }

    public static class Bean{

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
