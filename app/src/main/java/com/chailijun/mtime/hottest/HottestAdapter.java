package com.chailijun.mtime.hottest;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chailijun.mtime.R;
import com.chailijun.mtime.data.hottest.Hottest;
import com.chailijun.mtime.cinema_movie.PayticketActivity;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Imager;

import java.util.List;


public class HottestAdapter extends BaseQuickAdapter<Hottest.ListBean, BaseViewHolder> {

    public HottestAdapter(List<Hottest.ListBean> data) {
        super(R.layout.fragment_hottest_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Hottest.ListBean item) {

        Imager.load(mContext, item.getPosterUrl(), (ImageView) helper.getView(R.id.movie_img));

        //电影名字
        helper.setText(R.id.movie_name, item.getName());
        helper.setText(R.id.nameEn, item.getNameEn());

        //评分
        if (!TextUtils.isEmpty(item.getRating()) && Float.parseFloat(item.getRating()) > 0) {
            helper.getView(R.id.rating).setVisibility(View.VISIBLE);
            helper.setText(R.id.rating, item.getRating());
        } else {
            helper.getView(R.id.rating).setVisibility(View.GONE);
        }

        //xx人想看
        int wantCount = Integer.parseInt(item.getWantCount());
        if (wantCount > 0) {
            helper.getView(R.id.wantCount).setVisibility(View.VISIBLE);
            helper.setText(R.id.wantCount,
                    String.format(mContext.getString(R.string.wantseen_count), wantCount));
        } else {
            helper.getView(R.id.wantCount).setVisibility(View.GONE);
        }

        //导演
        String director = null;
        if (TextUtils.isEmpty(item.getDirector()) && TextUtils.isEmpty(item.getDirector2())){
            director = "--";
        }else {
            director = item.getDirector() + " " + item.getDirector2();
        }
        helper.setText(R.id.director,
                String.format(mContext.getString(R.string.director2),director));

        //主演
        String actor = null;
        if (TextUtils.isEmpty(item.getActor1()) && TextUtils.isEmpty(item.getActor2())){
            actor = "--";
        }else {
            actor = item.getActor1() + " " + item.getActor2();
        }
        helper.setText(R.id.mainActor,
                String.format(mContext.getString(R.string.main_actor),actor));

        //上映时间、地区
        helper.setText(R.id.releaseDate_Area,
                String.format(mContext.getString(R.string.releaseDate_area),
                        TextUtils.isEmpty(item.getReleaseDate()) ? "--" : item.getReleaseDate(),
                        TextUtils.isEmpty(item.getReleaseArea()) ? "--" : item.getReleaseArea()));

        //是否有票、预售
        if (item.isIsTicket()) {

            if (item.isIsPresell()){

                //预售
                helper.getView(R.id.btn_sale).setVisibility(View.VISIBLE);
                helper.getView(R.id.btn_sale).setBackgroundResource(R.drawable.bt_solid_green_66);
                helper.setText(R.id.btn_sale, mContext.getString(R.string.advance_sale));
            }else {

                //购票
                helper.getView(R.id.btn_sale).setVisibility(View.VISIBLE);
                helper.getView(R.id.btn_sale).setBackgroundResource(R.drawable.bt_solid_orange_66);
                helper.setText(R.id.btn_sale, mContext.getString(R.string.buy_ticket));
            }

            //添加点击,点击进入购票
            helper.addOnClickListener(R.id.btn_sale);
            final int movieId = item.getId();
            helper.getView(R.id.btn_sale).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext,PayticketActivity.class);
                    intent.putExtra(Constants.MOVIE_ID,movieId);
                    mContext.startActivity(intent);
                }
            });

        } else {
            helper.getView(R.id.btn_sale).setVisibility(View.GONE);
        }

        if (helper.getLayoutPosition() == 0) {//热度第一名
            helper.setBackgroundRes(R.id.rankNum, R.drawable.shape_circle_orange);
            helper.setText(R.id.rankNum, "1");
        } else if (helper.getLayoutPosition() == 1) {//热度第二名
            helper.setBackgroundRes(R.id.rankNum, R.drawable.shape_circle_green);
            helper.setText(R.id.rankNum, "2");
        } else if (helper.getLayoutPosition() == 2) {//热度第三名
            helper.setBackgroundRes(R.id.rankNum, R.drawable.shape_circle_blue);
            helper.setText(R.id.rankNum, "3");
        } else {//其它名次
            helper.setBackgroundRes(R.id.rankNum, R.drawable.shape_circle_gray);
            helper.setText(R.id.rankNum, helper.getLayoutPosition() + 1 + "");
        }
    }
}
