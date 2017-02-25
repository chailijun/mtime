package com.chailijun.mtime.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chailijun.mtime.R;
import com.chailijun.mtime.moviedetail.MovieDetailActivity;
import com.chailijun.mtime.mvp.interf.IReviewDetailPresenter;
import com.chailijun.mtime.mvp.interf.IReviewDetailView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.mvp.model.ReviewDetailJson;
import com.chailijun.mtime.mvp.presenter.ReviewDetailPresenter;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.DensityUtil;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.Logger;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 影评详细
 */
public class ReviewDetailActivity extends BaseActivity implements IReviewDetailView<BaseData> {

    private int reviewId;
    private IReviewDetailPresenter presenter;
    @BindView(R.id.fl_review_detail)
    LinearLayout fl_review_detail;
    private WebView webView;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.user_headImg)
    ImageView user_headImg;
    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.movie_name)
    TextView movie_name;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.movie_image)
    ImageView movie_image;
    @BindView(R.id.movie_rating)
    TextView movie_rating;

    @Override
    protected int getLayoutId() {

        return R.layout.activity_review_detail;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        getData();

        requestData();
    }

    private void requestData() {
        presenter = new ReviewDetailPresenter(this);
        if (reviewId != 0){
            presenter.getReviewDetail(reviewId);
        }
    }

    public void getData(){
        Intent intent = getIntent();
        reviewId = intent.getIntExtra(Constants.REVIEW_ID,0);
    }

    @OnClick({R.id.back, R.id.share, R.id.favorite})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.share:
                break;
            case R.id.favorite:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //webview内存泄露
        if (webView != null){
            ((ViewGroup)webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
    }

    @Override
    public void addReviewDetail(BaseData data) {
        if (data instanceof ReviewDetailJson){
            ReviewDetailJson reviewDetailJson = (ReviewDetailJson) data;

            setReviewTitle(reviewDetailJson);
            setReviewDetail(reviewDetailJson);
        }

    }

    /**
     * 设置影评标题等相关信息
     * @param reviewDetailJson
     */
    private void setReviewTitle(final ReviewDetailJson reviewDetailJson) {
        Imager.loadCircleImage(this,reviewDetailJson.getUserImage(),user_headImg, DensityUtil.dp2px(20.0f));
        Imager.load(this,reviewDetailJson.getRelatedObj().getImage(),movie_image);
        title.setText(reviewDetailJson.getTitle());
        user_name.setText(reviewDetailJson.getNickname());
        movie_name.setText(String.format(getString(R.string.review_movie),reviewDetailJson.getRelatedObj().getName()));
        time.setText(reviewDetailJson.getTime());

        if (reviewDetailJson.getRelatedObj().getRating() > 0){
            movie_rating.setVisibility(View.VISIBLE);
            movie_rating.setText(reviewDetailJson.getRelatedObj().getRating()+"");
        }else {
            movie_rating.setVisibility(View.GONE);
        }

        movie_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReviewDetailActivity.this,MovieDetailActivity.class);
                intent.putExtra(Constants.MOVIE_ID,reviewDetailJson.getRelatedObj().getId());
                startActivity(intent);
            }
        });
    }

    /**
     * 设置影评详细
     * @param reviewDetailJson
     */
    private void setReviewDetail(ReviewDetailJson reviewDetailJson) {
        webView = new WebView(this);
        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        webView.setLayoutParams(params);

//        webView.requestFocus();//解决第一次进入WebView时点击图片滚到最顶端的bug

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

        String css = "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/Player/reviewStyle.css\"/>";
        String js  = "<script type=\"text/javascript\" src=\"file:///android_asset/Player/review.js\"></script>";

        String content = "<div class=content>"+reviewDetailJson.getContent()+"</div>";
        String html = "<html><head>" + css + "</head><body class=article>" + js  + content + "</body></html>";
        webView.loadDataWithBaseURL("file://android_asset/Player/", html,"text/html", "utf-8", null);
        fl_review_detail.addView(webView,1);
    }

    @Override
    public void loadFailed(String msg) {
        Logger.e("TAG","影评详细加载失败："+msg);
    }



    private class MyJavascriptInterface {

        @JavascriptInterface
        public void playVideo(String viedoUrl){
            Intent intent = new Intent();
            intent.setDataAndType(Uri.parse(viedoUrl),"video");
            startActivity(intent);
        }

        @JavascriptInterface
        public void showImage(String imageUrl){
            Toast.makeText(ReviewDetailActivity.this, "图片:"+imageUrl, Toast.LENGTH_SHORT).show();
        }
    }
}
