package com.chailijun.mtime.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.base.MultiBaseAdapter;
import com.chailijun.baseadapter.interfaces.OnMultiItemClickListeners;
import com.chailijun.mtime.R;
import com.chailijun.mtime.activity.PersonalDetailActivity;
import com.chailijun.mtime.activity.SystemVideoPlayerActivity;
import com.chailijun.mtime.moviedetail.MovieDetailActivity;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.mvp.model.GoodsListBean;
import com.chailijun.mtime.mvp.model.NewsDetailFristJson;
import com.chailijun.mtime.mvp.model.NewsDetailJson;
import com.chailijun.mtime.mvp.model.NewsDetailSecondJson;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.Logger;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NewsDetailAdapter extends MultiBaseAdapter<BaseData> {


    private WebView webView;
    private Context mContext;

    public NewsDetailAdapter(Context context, List<BaseData> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
        mContext = context;
    }

    public void updateData(List<BaseData> datas){
        //更新数据之前进行排序，保证各个类型的item的顺序不变
        Collections.sort(datas, new Comparator<BaseData>() {
            @Override
            public int compare(BaseData data, BaseData data1) {
                if (data!=null&&data1!=null){
                    if(data.getItemType() > data1.getItemType()){
                        return 1;
                    }else {
                        return -1;
                    }
                }
                return 0;
            }
        });

        super.setLoadMoreData(datas);
    }

    @Override
    protected void convert(ViewHolder holder, BaseData data,int position, int viewType) {
        switch (viewType){
            case Constants.TYPE_NEWS_GALLERY:
                //新闻顶部的画廊数据绑定
                break;
            case Constants.TYPE_NEWS_DETAIL:
                if (data instanceof NewsDetailJson) {
                    NewsDetailJson dataBean = (NewsDetailJson) data;
                    bindNewsDetailData(holder,dataBean);
                } else {
                    throw new IllegalArgumentException(NewsDetailAdapter.class.getSimpleName() + ":the newsDetail data error!");
                }
                break;
            case Constants.TYPE_NEWS_SHARE:
                if (data instanceof NewsDetailJson) {
                    NewsDetailJson dataBean = (NewsDetailJson) data;
                    bindShareData(holder,dataBean);
                } else {
                    throw new IllegalArgumentException(NewsDetailAdapter.class.getSimpleName() + ":the newsDetail data error!");
                }
                break;
            case Constants.TYPE_NEWS_RELATIONS_INFO:
                if (data instanceof NewsDetailJson) {
                    NewsDetailJson dataBean = (NewsDetailJson) data;
                    bindRelationsData(holder,dataBean);
                } else {
                    throw new IllegalArgumentException(NewsDetailAdapter.class.getSimpleName() + ":the newsDetail data error!");
                }
                break;
            case Constants.TYPE_NEWS_RELATIONS_GOODS:
                bindGoodsData(holder,data);
                break;
            case Constants.TYPE_NEWS_RELATIONS_READS:
                if (data instanceof NewsDetailSecondJson.DataBean.NewsDetailBean){
                    NewsDetailSecondJson.DataBean.NewsDetailBean newsDetailBean = (NewsDetailSecondJson.DataBean.NewsDetailBean) data;
                    bindReadsData(holder,newsDetailBean);
                }else {
                    throw new IllegalArgumentException(NewsDetailAdapter.class.getSimpleName() + ":the relationReads data error!");
                }

                break;
            case Constants.TYPE_NEWS_COMMENT:
                if (data instanceof NewsDetailFristJson.DataBean.NewsDetailBean) {
                    NewsDetailFristJson.DataBean.NewsDetailBean dataBean = (NewsDetailFristJson.DataBean.NewsDetailBean) data;
                    bindNewsCommentData(holder,dataBean);
                } else {
                    throw new IllegalArgumentException(NewsDetailAdapter.class.getSimpleName() + ":the newsComment data error!");
                }
                break;
            case Constants.TYPE_NEWS_ADV_BANNER:

                break;
            default:
                break;
        }
    }

    private void bindGoodsData(ViewHolder holder, final BaseData data) {
        if (data instanceof NewsDetailSecondJson.DataBean.NewsDetailBean){
            final NewsDetailSecondJson.DataBean.NewsDetailBean goodsData = (NewsDetailSecondJson.DataBean.NewsDetailBean) data;
            List<GoodsListBean> goodsList = goodsData.getRelatedGoods().getGoodsList();
            if (goodsList.size() >= 3){
                Imager.load(mContext,goodsList.get(0).getImage(), (ImageView) holder.getView(R.id.iv_goods1_image));
                holder.setText(R.id.tv_goods1_name,goodsList.get(0).getName());

                Imager.load(mContext,goodsList.get(1).getImage(), (ImageView) holder.getView(R.id.iv_goods2_image));
                holder.setText(R.id.tv_goods2_name,goodsList.get(1).getName());

                Imager.load(mContext,goodsList.get(2).getImage(), (ImageView) holder.getView(R.id.iv_goods3_image));
                holder.setText(R.id.tv_goods3_name,goodsList.get(2).getName());
            }else {
                Logger.e(NewsDetailAdapter.class.getSimpleName()+":相关商品的数量小于2！");
            }
//            if (goodsList.size() > 0){
//                holder.getView(R.id.ll_goods1).setVisibility(View.VISIBLE);
//                Imager.load(mContext,goodsList.get(0).getImage(), (ImageView) holder.getView(R.id.iv_goods1_image));
//                holder.setText(R.id.tv_goods1_name,goodsList.get(0).getName());
//            }
//            for (int i = 0; i < goodsList.size(); i++) {
//                Logger.e("TAG","相关商品:"+goodsData.getRelatedGoods().getGoodsList().get(i));
//            }

//            RecyclerView recyclerView = holder.getView(R.id.recyclerview_relations_goods);
//            recyclerView.setHasFixedSize(true);
//            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
//            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//            recyclerView.setLayoutManager(layoutManager);
//            RelationGoodsAdapter adapter = new RelationGoodsAdapter(mContext,goodsData.getRelatedGoods().getGoodsList(),false);
//            recyclerView.setAdapter(adapter);
//

            holder.setOnClickListener(R.id.all_goods, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "更多商品:"+goodsData.getRelatedGoods().getRelatedUrl(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void bindNewsDetailData(final ViewHolder holder, NewsDetailJson data) {

        webView = holder.getView(R.id.webView);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        /*WebView webView = new WebView(MtimeApp.mContext);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        webView.setLayoutParams(lp);
        ((LinearLayout)holder.getView(R.id.ll_webView)).removeAllViews();
        ((LinearLayout)holder.getView(R.id.ll_webView)).addView(webView);*/

        webView.requestFocus();//解决第一次进入WebView时点击图片滚到最顶端的bug
        //初始化WebView参数
        WebSettings webSettings = webView.getSettings();
        //设置文字大小
        //webSettings.setTextZoom(180);
//        webSettings.setUseWideViewPort(true);造成文字太小
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");

        //添加javascript接口
        webView.addJavascriptInterface(new MyJavascriptInterface(),"android");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                webView.loadUrl(url);
                return true;
            }
        });
//        webView.setWebChromeClient(new WebChromeClient());

        String css = "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/Player/style.css\"/>";
        String js  = "<script type=\"text/javascript\" src=\"file:///android_asset/Player/iphone.js\"></script>";
        String title = "<div class=title>" +
                "<h1>"+(data.getTitle()==null?"":data.getTitle())+"</h1>" +
                "<h3>"+(data.getTitle2()==null?"":data.getTitle2())+"</h3>" +
                "<p>"+(data.getTime()==null?"":data.getTime())+"&nbsp;"+(data.getSource()==null?"":data.getSource())+"</p>" +
                "</div>";
        StringBuffer author = new StringBuffer();
        if(data.getAuthor()!=null&&data.getEditor()!=null){
            author.append(data.getAuthor().equals("")||data.getAuthor()==null?"（":"（作者："+data.getAuthor());
            author.append(data.getEditor().equals("")?"）":" 编辑："+data.getEditor()+"）");
        }
        String content = "<div class=content>"+data.getContent()+author+"</div>";
        String html = "<html><head>" + css + "</head><body class=article>" + js + title + content + "</body></html>";
        webView.loadDataWithBaseURL("file://android_asset/Player/", html,"text/html", "utf-8", null);
        webView.setLayoutParams(lp);
        Logger.e("TAG","webView高度："+webView.getHeight());
    }

    private void bindShareData(ViewHolder holder, NewsDetailJson data) {
        holder.setOnClickListener(R.id.share_wxfriend,new ShareOnClickListener(data));
        holder.setOnClickListener(R.id.share_wxcircle,new ShareOnClickListener(data));
        holder.setOnClickListener(R.id.share_xlweibo,new ShareOnClickListener(data));
        holder.setOnClickListener(R.id.share_qqfriend,new ShareOnClickListener(data));
    }

    private void bindRelationsData(ViewHolder holder, NewsDetailJson dataBean) {
        RecyclerView recyclerView = holder.getView(R.id.recyclerview_relations);
        recyclerView.setHasFixedSize(true);
        RelationsInfoAdapter adapter = new RelationsInfoAdapter(mContext,dataBean.getRelations(),false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnMultiItemClickListener(new OnMultiItemClickListeners<NewsDetailJson.RelationsBean>() {
            @Override
            public void onItemClick(ViewHolder viewHolder,
                                    NewsDetailJson.RelationsBean data,
                                    int position,
                                    int viewType) {
                Logger.e("NewsDetailAdapter","viewType==="+viewType);
                if (viewType == 1){//电影
                    Intent intent = new Intent(mContext, MovieDetailActivity.class);
                    intent.putExtra(Constants.MOVIE_ID,data.getId());
                    mContext.startActivity(intent);

                }else if(viewType == 2){//人物
                    Intent intent = new Intent(mContext, PersonalDetailActivity.class);
                    intent.putExtra(Constants.PERSON_ID,data.getId());
                    mContext.startActivity(intent);
                }
            }
        });
    }

    private void bindNewsCommentData(ViewHolder holder, NewsDetailFristJson.DataBean.NewsDetailBean dataBean) {
        RecyclerView recyclerView = holder.getView(R.id.recyclerview_comment);
        recyclerView.setHasFixedSize(true);
        WonderfulCommentAdapter adapter = new WonderfulCommentAdapter(mContext,dataBean.getComments(),false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        holder.setText(R.id.all_comment,String.format(mContext.getString(R.string.all_comment),dataBean.getComments().get(0).getTotalCount()));
        holder.getView(R.id.all_comment).setVisibility(dataBean.getComments().size() < 3?View.GONE:View.VISIBLE);
        holder.getView(R.id.all_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "查看全部评论", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bindReadsData(ViewHolder holder, NewsDetailSecondJson.DataBean.NewsDetailBean newsDetailBean) {
        RecyclerView recyclerView = holder.getView(R.id.recyclerview_reads);
        recyclerView.setHasFixedSize(true);
        RelationReadsAdapter adapter = new RelationReadsAdapter(mContext,newsDetailBean.getReads(),false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        switch (viewType){
            case Constants.TYPE_NEWS_GALLERY:
                return R.layout.activity_news_detail_item_gallery;
            case Constants.TYPE_NEWS_DETAIL:
                return R.layout.activity_news_detail_item_detail;
            case Constants.TYPE_NEWS_SHARE:
                return R.layout.activity_news_detail_item_share;
            case Constants.TYPE_NEWS_RELATIONS_INFO:
                return R.layout.activity_news_detail_item_relations_info;
            case Constants.TYPE_NEWS_RELATIONS_GOODS:
                return R.layout.activity_news_detail_item_relations_goods;
            case Constants.TYPE_NEWS_RELATIONS_READS:
                return R.layout.activity_news_detail_item_relations_reads;
            case Constants.TYPE_NEWS_COMMENT:
                return R.layout.activity_news_detail_item_comments;
            case Constants.TYPE_NEWS_ADV_BANNER:
                return R.layout.activity_news_detail_item_bottom_advbanner;
            default:
                try {
                    throw new Exception(NewsDetailAdapter.class.getSimpleName()+":新闻详细页面传入数据有误，请检查");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

        return -1;
    }

    @Override
    protected int getViewType(int position, BaseData data) {

        return data.getItemType();

    }

    class ShareOnClickListener implements View.OnClickListener{

        NewsDetailJson mData;

        public ShareOnClickListener(NewsDetailJson mData) {
            this.mData = mData;
        }

        @Override
        public void onClick(View view) {
            String shareUrl = mData.getUrl();
            switch (view.getId()){
                case R.id.share_wxfriend:
                    Logger.e("TAG","微信朋友："+shareUrl);
                    Toast.makeText(mContext, "微信朋友："+shareUrl, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.share_wxcircle:
                    Toast.makeText(mContext, "朋友圈："+shareUrl, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.share_xlweibo:
                    Toast.makeText(mContext, "新浪微博："+shareUrl, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.share_qqfriend:
                    Toast.makeText(mContext, "QQ好友："+shareUrl, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }

    private class MyJavascriptInterface {
        @JavascriptInterface
        public void playVideo(String viedoUrl){
            Intent intent = new Intent(mContext, SystemVideoPlayerActivity.class);
            intent.setDataAndType(Uri.parse(viedoUrl),"video/*");
            mContext.startActivity(intent);
        }
        @JavascriptInterface
        public void showImage(String imageUrl){
            Toast.makeText(mContext, "图片:"+imageUrl, Toast.LENGTH_SHORT).show();
        }
    }
}
