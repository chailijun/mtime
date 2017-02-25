package com.chailijun.mtime.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chailijun.mtime.R;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Logger;

import butterknife.BindView;
import butterknife.OnClick;

public class WebActivity extends BaseActivity {

    private static final String TAG = WebActivity.class.getSimpleName();

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.ll_main)
    LinearLayout ll_main;
    private String url;
    private WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        getData();
        webView = new WebView(getApplicationContext());
        webView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        webView.clearCache(true);
        webView.clearHistory();
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                webView.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Logger.e(TAG,"加载进度："+newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                tv_title.setText(title);
            }
        });
        webView.clearFormData();
        webView.clearMatches();
        webView.loadUrl(url);
        ll_main.addView(webView,1);
    }

    @OnClick(R.id.back)
    public void close(){
        finish();
    }

    @Override
    protected void onPause() {
        webView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        //webview内存泄露
        if (webView != null){
            ((ViewGroup)webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
        System.exit(0);
    }

    private void getData() {
        Intent intent = getIntent();

        String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)){
            Uri uri = intent.getData();
            if (uri != null){
                String url = getIntent().getData().toString();
                url = url.replace("gotourl:","");
                Logger.e(TAG,"url===="+url);
                this.url = url;
            }
        }else {
            url = intent.getStringExtra(Constants.GOTO_URL);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}
