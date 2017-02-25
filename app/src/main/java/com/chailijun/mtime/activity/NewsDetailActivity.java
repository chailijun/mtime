package com.chailijun.mtime.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.chailijun.mtime.R;
import com.chailijun.mtime.adapter.NewsDetailAdapter;
import com.chailijun.mtime.mvp.interf.INewsDetailPresenter;
import com.chailijun.mtime.mvp.interf.INewsDetailView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.mvp.model.NewsDetailFristJson;
import com.chailijun.mtime.mvp.model.NewsDetailJson;
import com.chailijun.mtime.mvp.model.NewsDetailSecondJson;
import com.chailijun.mtime.mvp.presenter.NewsDetailPresenter;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Logger;
import com.chailijun.mtime.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class NewsDetailActivity extends BaseActivity implements INewsDetailView<BaseData> {

    @BindView(R.id.tv_comment_count)
    TextView tv_comment_count;
//    @BindView(R.id.ll_news_detail)
//    LinearLayout ll_news_detail;
    @BindView(R.id.recyclerview_news_detail)
    RecyclerView mRecyclerView;
    private WebView webView;

    private NewsDetailAdapter mAdapter;
    private INewsDetailPresenter presenter;
    private int newsId;
    private int locationId;
    private int flag;

    private View initLoadFailedView;
    private List<BaseData> mDatas = new ArrayList<>();

    @Override
    protected int getLayoutId() {

        return R.layout.activity_news_detail;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        initLoadFailedView = LayoutInflater.from(this).inflate(R.layout.loading_failed, (ViewGroup) mRecyclerView.getParent(), false);
        initLoadFailedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRefresh();
            }
        });
        mAdapter = new NewsDetailAdapter(this, null, false);
        mAdapter.setInitLoadingView(R.layout.loading);//设置初始加载时的布局
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        getData();
        requestData();
    }

    private void onRefresh() {
        mAdapter.setInitLoadingView(R.layout.loading);
        requestData();
    }

    private void requestData() {
        presenter = new NewsDetailPresenter(this);
        if (newsId != 0) {
            presenter.getNewsDetail(newsId);
            Logger.e("TAG", "newsId:" + newsId);
        }
    }

    private void getData() {
        Intent intent = getIntent();
        newsId = intent.getIntExtra(Constants.NEWS_ID,0);
        locationId = SPUtil.getInt(Constants.LOCATION_ID);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getData();
    }

    @OnClick({R.id.back,R.id.favorite,R.id.share})
    public void back(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.favorite:
                Toast.makeText(NewsDetailActivity.this, "收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.share:
                Toast.makeText(NewsDetailActivity.this, "分享", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

    }

    @Override
    public void addNewsDetail(BaseData data) {
        if (data instanceof NewsDetailJson) {
            List<BaseData> datas = new ArrayList<>();
            NewsDetailJson newsDetailJson = (NewsDetailJson) data;
            tv_comment_count.setVisibility(newsDetailJson.getCommentCount() > 0 ? View.VISIBLE : View.GONE);
            //该条新闻评论总数
            if (newsDetailJson.getCommentCount() > 0) {
                tv_comment_count.setText(newsDetailJson.getCommentCount() + "");
            }
            //新闻数据中带有"画廊"数据
            if (newsDetailJson.getImages() != null && newsDetailJson.getImages().size() > 0) {
                NewsDetailJson tempData = new NewsDetailJson();
                tempData.setImages(((NewsDetailJson) data).getImages());
                tempData.setItemType(Constants.TYPE_NEWS_GALLERY);
                datas.add(tempData);
            }
            //新//闻详细
//            setNewsDetail(newsDetailJson);
            data.setItemType(Constants.TYPE_NEWS_DETAIL);
            datas.add(data);
            //新闻分享
            NewsDetailJson tempData = new NewsDetailJson();
            tempData.setUrl(((NewsDetailJson) data).getUrl());
            tempData.setItemType(Constants.TYPE_NEWS_SHARE);
            datas.add(tempData);
            //新闻数据中带有"相关资料"数据
            if (newsDetailJson.getRelations() != null && newsDetailJson.getRelations().size() > 0) {
                tempData = new NewsDetailJson();
                tempData.setRelations(((NewsDetailJson) data).getRelations());
                tempData.setItemType(Constants.TYPE_NEWS_RELATIONS_INFO);
                datas.add(tempData);
            }
            mAdapter.setNewData(datas);
        }

        //（如果有）请求新闻详细下方的相关商品、相关阅读、精彩评论、广告条
        if (locationId != 0 && newsId != 0) {
            presenter.getNewsDetailSecond((int)locationId, newsId);
            presenter.getNewsDetailFrist((int)locationId, newsId);
        }
    }


    @Override
    public void addNewsDetailFrist(BaseData data) {
        if (data instanceof NewsDetailFristJson) {
            NewsDetailFristJson newsDetailFristJson = (NewsDetailFristJson) data;
            List<NewsDetailFristJson.DataBean.NewsDetailBean> newsDetail = newsDetailFristJson.getData().getNewsDetail();
            if (newsDetail != null && newsDetail.size() > 0) {
//                List<BaseData> mDatas = new ArrayList<>();
                //精彩评论
                for (int i = 0; i < newsDetail.size(); i++) {
                    if (newsDetail.get(i).getComments() != null && newsDetail.get(i).getComments().size() > 0) {
                        NewsDetailFristJson.DataBean.NewsDetailBean bean = newsDetail.get(i);
                        bean.setItemType(Constants.TYPE_NEWS_COMMENT);
                        mDatas.add(bean);
                    }
                }
                //广告条
                for (int i = 0; i < newsDetail.size(); i++) {
                    if (newsDetail.get(i).getAdvertisement().getTypeName() != null) {
                        NewsDetailFristJson.DataBean.NewsDetailBean bean = newsDetail.get(i);
                        bean.setItemType(Constants.TYPE_NEWS_ADV_BANNER);
                        mDatas.add(bean);
                    }
                }
//                mAdapter.setLoadMoreData(mDatas);
//                mAdapter.updateData(mDatas);
                flag++;
                update();

            }
        }
    }

    @Override
    public void addNewsDetailSecond(BaseData data) {
        if (data instanceof NewsDetailSecondJson) {
            NewsDetailSecondJson newsDetailSecondJson = (NewsDetailSecondJson) data;
            List<NewsDetailSecondJson.DataBean.NewsDetailBean> newsDetail = newsDetailSecondJson.getData().getNewsDetail();
            if (newsDetail != null && newsDetail.size() > 0) {

                //相关商品
                for (int i = 0; i < newsDetail.size(); i++) {
                    if (newsDetail.get(i).getRelatedGoods().getRelatedUrl() != null) {
                        NewsDetailSecondJson.DataBean.NewsDetailBean bean = newsDetail.get(i);
                        bean.setItemType(Constants.TYPE_NEWS_RELATIONS_GOODS);
                        mDatas.add(bean);
                    }
                }
                //相关阅读
                for (int i = 0; i < newsDetail.size(); i++) {
                    if (newsDetail.get(i).getReads().size() > 0) {
                        NewsDetailSecondJson.DataBean.NewsDetailBean bean = newsDetail.get(i);
                        bean.setItemType(Constants.TYPE_NEWS_RELATIONS_READS);
                        mDatas.add(bean);
                    }
                }
//                mAdapter.setLoadMoreData(datas);
//                mAdapter.updateData(datas);
                flag++;
                update();
            }
        }
    }

    private void update() {
        if (flag == 2){
            flag = 0;
            mAdapter.updateData(mDatas);
        }
    }

    @Override
    public void loadFailed(String msg) {
        mAdapter.setInitLoadFailedView(initLoadFailedView);
    }

    @Override
    protected void onDestroy() {
        presenter.unsubcrible();
        /*//webview内存泄露
        if (webView != null){
            ((ViewGroup)webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }*/
//        mAdapter.releaseWebView();
        super.onDestroy();
        System.exit(0);
    }



    private class MyJavascriptInterface {
        @JavascriptInterface
        public void playVideo(String viedoUrl){
            Intent intent = new Intent();
            intent.setDataAndType(Uri.parse(viedoUrl),"video/*");
            startActivity(intent);
        }
        @JavascriptInterface
        public void showImage(String imageUrl){
            Toast.makeText(NewsDetailActivity.this, "图片:"+imageUrl, Toast.LENGTH_SHORT).show();
        }
    }
}
