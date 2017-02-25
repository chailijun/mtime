package com.chailijun.mtime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chailijun.mtime.R;
import com.chailijun.mtime.api.ApiConstants;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Logger;

import butterknife.BindView;

public class GoodsListActivity extends BaseActivity {

    private String url;
    private WebView webView;

    @BindView(R.id.rl_main)
    RelativeLayout rl_main;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_goods_list;
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

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Logger.e("TAG","加载进度："+newProgress);
            }
        });


        webView.loadUrl(url);
        rl_main.addView(webView);

    }

    private void getData() {
        Intent intent = getIntent();
        url = intent.getStringExtra(Constants.GOODS_URL);
        if (!url.startsWith("https://") && !url.startsWith("http://")){
           url = ApiConstants.HOST + url;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getData();
    }

    @Override
    protected void onDestroy() {
        //webview内存泄露
        if (webView != null) {
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
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
