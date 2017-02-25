package com.chailijun.mtime.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.chailijun.mtime.MainActivity;
import com.chailijun.mtime.R;
import com.chailijun.mtime.base.*;
import com.chailijun.mtime.moviedetail.MovieDetailActivity;
import com.chailijun.mtime.mvp.model.Advertisement;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.SPUtil;
import com.google.gson.Gson;

import java.io.File;
import java.util.List;

public class StartAdvActivity extends com.chailijun.mtime.base.BaseActivity {

    private Button btn_skip;

    private ImageView img_adv;

    private int mDelayTime = 3;// 广告3秒倒计时

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mDelayTime--;
            if (mDelayTime < 0) {
                startActivity(new Intent(StartAdvActivity.this, MainActivity.class));
                finish();
            } else {
                btn_skip.setText("跳过 " + mDelayTime);
                sendEmptyMessageDelayed(1, 1000);
            }

        }
    };
    private List<Advertisement.AdvListBean> mAdvList;


    @Override
    public void initParms(Bundle parms) {
        setAllowFullScreen(true);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_start_adv;
    }

    @Override
    public void initView(View view) {
        btn_skip = (Button) findViewById(R.id.btn_skip);
        img_adv = (ImageView) findViewById(R.id.img_adv);

        btn_skip.setText("跳过 " + mDelayTime);
        btn_skip.setOnClickListener(new AdvOnClickListener(null));
    }

    @Override
    public void setListener() {

    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public void doBusiness(Context mContext) {
        setAdvImage();
    }

    private void setAdvImage() {

        String advAll = SPUtil.get(Constants.ADV_ALL, "");
        if (TextUtils.isEmpty(advAll)) {
            startActivity(new Intent(StartAdvActivity.this,MainActivity.class));
            finish();
        }else {
            Advertisement advertisement = new Gson().fromJson(advAll, Advertisement.class);
            mAdvList = advertisement.getAdvList();
            if (mAdvList == null || mAdvList.size() == 0) {
                startActivity(new Intent(StartAdvActivity.this,MainActivity.class));
                finish();
            }else {
                for (int i = 0; i < mAdvList.size(); i++) {
                    if (mAdvList.get(i).getType().equals("100")) {
                        long startDate = mAdvList.get(i).getStartDate() * 1000;
                        long endDate = mAdvList.get(i).getEndDate() * 1000;
                        long currTimeMillis = System.currentTimeMillis();

//                        Logger.e("TAG","startDate========"+startDate);
//                        Logger.e("TAG","endDate=========="+endDate);
//                        Logger.e("TAG","currTimeMillis==="+currTimeMillis);

                        if (currTimeMillis > startDate && currTimeMillis < endDate) {
                            String url = mAdvList.get(i).getUrl();
                            File imagePath = Imager.getImagePath(url);
                            if (imagePath.exists()) {
                                Bitmap bm = BitmapFactory.decodeFile(imagePath.getPath());
                                img_adv.setImageBitmap(bm);
                                img_adv.setOnClickListener(
                                        new AdvOnClickListener(mAdvList.get(i).getGotoPage()));

                            }else {
                                Imager.deleteFile(imagePath);
                            }
                            mHandler.sendEmptyMessageDelayed(1, 1000);
                            break;
                        }else {
                            Imager.deleteFile(getString(R.string.app_cache));
                        }
                    }
                }
            }
        }
    }

    /**
     * 设置广告图片的点击事件
     * @param gotoPage
     */
    private void setAdvImageOnClick(Advertisement.AdvListBean.GotoPageBean gotoPage) {
        if (gotoPage == null){
            return;
        }
        String type = gotoPage.getGotoType();
        Intent intent = null;
        switch (type){
            case Constants.GOTO_TYPE_URL:
                intent = new Intent(StartAdvActivity.this,WebActivity.class);
                intent.putExtra(Constants.GOTO_URL,gotoPage.getUrl());
                startActivity(intent);
                finish();
                break;
            case Constants.GOTO_TYPE_MOVIE:
                intent = new Intent(StartAdvActivity.this,MovieDetailActivity.class);
                intent.putExtra(Constants.MOVIE_ID,gotoPage.getParameters().getMovieId());
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

    class AdvOnClickListener implements View.OnClickListener {

        private Object obj;

        public AdvOnClickListener(Object obj) {
            this.obj = obj;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_skip:
                    startActivity(new Intent(StartAdvActivity.this, MainActivity.class));
                    finish();
                    break;
                case R.id.img_adv:
                    Advertisement.AdvListBean.GotoPageBean gotoPage =
                            (Advertisement.AdvListBean.GotoPageBean) this.obj;
                    setAdvImageOnClick(gotoPage);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
