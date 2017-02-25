package com.chailijun.mtime.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.base.MultiBaseAdapter;
import com.chailijun.mtime.R;
import com.chailijun.mtime.customview.ExpandableTextView;
import com.chailijun.mtime.fragment.MainMovieFragment;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.mvp.model.movie.PersonCommentJson;
import com.chailijun.mtime.mvp.model.movie.PersonDetailJson;
import com.chailijun.mtime.utils.BitmapUtil;
import com.chailijun.mtime.utils.DensityUtil;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.TimeUtils;

import java.util.List;


public class PersonalDetailAdapter extends MultiBaseAdapter<BaseData> {

    private int TYPA_HEAD = 1;
    private int TYPA_COMMENT = 2;

    private Context mContext;
    private WebView webView;
    FragmentManager fragmentManager;
    int personId;

    public PersonalDetailAdapter(Context context, List<BaseData> datas, boolean isOpenLoadMore,FragmentManager fm,int personId) {
        super(context, datas, isOpenLoadMore);
        mContext = context;
        //添加MainMovieFragment(主要作品)时需要
        fragmentManager = fm;
        this.personId = personId;
    }

    @Override
    protected void convert(ViewHolder holder, BaseData data, int position, int viewType) {
        if (data instanceof PersonDetailJson && 0 == position) {
            PersonDetailJson personDetailJson = (PersonDetailJson) data;

            bindActorBackgroundData(holder, personDetailJson);
        }
        if (data instanceof PersonCommentJson.ListBean) {
            PersonCommentJson.ListBean list = (PersonCommentJson.ListBean) data;

            bindPersonCommentData(holder, list);
            if (1 == position) {
                holder.getView(R.id.ll_title_tag).setVisibility(View.VISIBLE);
                holder.setText(R.id.user_comment, String.format(mContext.getString(R.string.user_comment), list.getCount()));
            } else {
                holder.getView(R.id.ll_title_tag).setVisibility(View.GONE);
            }
        }


    }

    /**
     * 设置用户评论
     *
     * @param holder
     * @param list
     */
    private void bindPersonCommentData(ViewHolder holder, PersonCommentJson.ListBean list) {

        Imager.loadCircleImage(mContext,
                list.getUserImage(),
                ((ImageView) holder.getView(R.id.userImage)),
                DensityUtil.dp2px(20.0f));
        holder.setText(R.id.user_nickname, list.getNickname());
        holder.setText(R.id.user_content, list.getContent());
        holder.setText(R.id.user_commentDate, TimeUtils.getDistanceTime(list.getStampTime()));
        holder.setText(R.id.user_praiseCount, list.getCommentCount() + "");
    }

    /**
     * 设置演员背景资料
     *
     * @param holder
     * @param personDetailJson
     */
    private void bindActorBackgroundData(ViewHolder holder, PersonDetailJson personDetailJson) {

        final PersonDetailJson.DataBean.BackgroundBean background =
                personDetailJson.getData().getBackground();

        BitmapUtil.setBlurBackground(mContext,
                background.getImage(), ((ImageView) holder.getView(R.id.img_bg_head)));
        Imager.load(mContext, background.getImage(), ((ImageView) holder.getView(R.id.iv_actor_img)));
        holder.setText(R.id.tv_actor_name, background.getNameCn());
        holder.setText(R.id.tv_actor_name_en, background.getNameEn());
        holder.setText(R.id.tv_profession, background.getProfession());

        StringBuilder birthdayAddress = new StringBuilder();
        birthdayAddress.append(background.getBirthYear() == 0 ? "-" : background.getBirthYear() + "-");
        birthdayAddress.append(background.getBirthMonth() == 0 ? "" : background.getBirthMonth() + "-");
        birthdayAddress.append(background.getBirthDay() == 0 ? "" : background.getBirthDay() + " ");
        birthdayAddress.append(background.getAddress());
        holder.setText(R.id.tv_birthday_address, birthdayAddress);

        holder.setText(R.id.tv_actor_ratingfinal, String.format(mContext.getString(R.string.like_percent),
                ((int) (Float.parseFloat(background.getRatingFinal()) * 10)) + "%"));

        if (!TextUtils.isEmpty(background.getContent())) {
            holder.getView(R.id.actor_introduce).setVisibility(View.VISIBLE);

            ((ExpandableTextView) holder.getView(R.id.actor_introduce))
                    .setText(background.getContent(), background.isCollapsed());
            ((ExpandableTextView) holder.getView(R.id.actor_introduce)).setListener(new ExpandableTextView.OnExpandStateChangeListener() {
                @Override
                public void onExpandStateChanged(boolean isExpanded) {
                    background.setCollapsed(isExpanded);
                }
            });

        } else {
            holder.getView(R.id.actor_introduce).setVisibility(View.GONE);
        }

        //正在热映
        if (background.getHotMovie().getMovieId() != 0) {
            holder.getView(R.id.ll_hot_movie).setVisibility(View.VISIBLE);
            Imager.load(mContext,
                    background.getHotMovie().getMovieCover(),
                    ((ImageView) holder.getView(R.id.hot_play_movie_img)));
            holder.setText(R.id.tv_movie_title, background.getHotMovie().getMovieTitleCn());
            if (background.getHotMovie().getRatingFinal() > 0) {
                holder.getView(R.id.tv_rating_final).setVisibility(View.VISIBLE);
                holder.setText(R.id.tv_rating_final, background.getHotMovie().getRatingFinal() + "");
            } else {
                holder.getView(R.id.tv_rating_final).setVisibility(View.GONE);
            }
            holder.setText(R.id.tv_movie_type, background.getHotMovie().getType());
            if (!TextUtils.isEmpty(background.getHotMovie().getRoleName())) {
                holder.setText(R.id.tv_role_name,
                        String.format(mContext.getString(R.string.portray), background.getHotMovie().getRoleName()));
            }
            holder.setText(R.id.tv_ticket_price, background.getHotMovie().getTicketPrice() / 100 + "");
        } else {
            holder.getView(R.id.ll_hot_movie).setVisibility(View.GONE);
        }

        //设置主要作品
        ViewGroup container = holder.getView(R.id.main_movie_container);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (container.getChildCount() == 0) {
            transaction.add(R.id.main_movie_container, MainMovieFragment.newInstance(personId));
        }else {
            transaction.show(MainMovieFragment.newInstance(personId));
        }
        transaction.commit();

        //提名、获奖
        if (background.getTotalNominateAward() != 0 || background.getTotalWinAward() != 0) {
            holder.getView(R.id.rl_awards).setVisibility(View.VISIBLE);
            holder.setText(R.id.tv_awards, String.format(mContext.getString(R.string.award_list),
                    background.getTotalNominateAward(), background.getTotalWinAward()));
        } else {
            holder.getView(R.id.rl_awards).setVisibility(View.GONE);
        }

        //影人图集
        if (background.getImages() != null && background.getImages().size() >= 3) {
            holder.getView(R.id.ll_actor_images).setVisibility(View.VISIBLE);

            Imager.load(mContext,
                    background.getImages().get(0).getImage(),
                    ((ImageView) holder.getView(R.id.img1)));
            Imager.load(mContext,
                    background.getImages().get(1).getImage(),
                    ((ImageView) holder.getView(R.id.img2)));
            Imager.load(mContext,
                    background.getImages().get(2).getImage(),
                    ((ImageView) holder.getView(R.id.img3)));

            if (background.getImages().size() > 3){

                Imager.load(mContext,
                        background.getImages().get(3).getImage(),
                        ((ImageView) holder.getView(R.id.img4)));
            }else {
                holder.getView(R.id.img4).setVisibility(View.INVISIBLE);
            }
        } else {
            holder.getView(R.id.ll_actor_images).setVisibility(View.GONE);
        }

        //演艺经历
        if (background.getExpriences() != null && background.getExpriences().size() > 0) {
            holder.getView(R.id.ll_actor_expriences).setVisibility(View.VISIBLE);

            Imager.load(mContext,
                    background.getExpriences().get(0).getImg(),
                    ((ImageView) holder.getView(R.id.expriences_img)));

            holder.setText(R.id.expriences_title, background.getExpriences().get(0).getTitle());
            holder.setText(R.id.expriences_content, background.getExpriences().get(0).getContent());
        } else {
            holder.getView(R.id.ll_actor_expriences).setVisibility(View.GONE);
        }

        //广告条
        if (personDetailJson.getData().getAdvertisement().getAdvList().size() > 0) {
            holder.getView(R.id.ll_advlist).setVisibility(View.VISIBLE);
            ViewGroup advView = holder.getView(R.id.ll_advlist);
            if (advView.getChildCount() < 2) {//防止重复添加
                webView = new WebView(mContext);
                LinearLayout.LayoutParams params =
                        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                webView.setLayoutParams(params);
                WebSettings webSettings = webView.getSettings();

                webSettings.setJavaScriptEnabled(true);
                webSettings.setDomStorageEnabled(true);

                webView.loadUrl(personDetailJson.getData().getAdvertisement().getAdvList().get(0).getUrl());
                advView.addView(webView, 1);
            }
        } else {
            holder.getView(R.id.ll_advlist).setVisibility(View.GONE);
        }

    }

    public void releaseWebView() {
        if (webView != null) {
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        if (viewType == TYPA_HEAD) {
            return R.layout.activity_actor_detail_item_head;
        }
        return R.layout.activity_actor_detail_item_user_comment;
    }

    @Override
    protected int getViewType(int position, BaseData data) {
        if (0 == position) {
            return TYPA_HEAD;
        }
        return TYPA_COMMENT;
    }
}
