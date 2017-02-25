package com.chailijun.mtime.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.base.CommonBaseAdapter;
import com.chailijun.baseadapter.base.MultiBaseAdapter;
import com.chailijun.baseadapter.interfaces.OnItemClickListener;
import com.chailijun.mtime.R;
import com.chailijun.mtime.activity.GoodsListActivity;
import com.chailijun.mtime.activity.WebActivity;
import com.chailijun.mtime.fragment.TopicSubListfragment;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.mvp.model.mall.MallIndexJson;
import com.chailijun.mtime.mvp.model.mall.RecommendProductsJson;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.DensityUtil;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class MallAdapter extends MultiBaseAdapter<BaseData> {

    private static final int TYPE_HEAD = 1;//头部
    private static final int TYPE_COMM = 2;//内容

    private Context context;
    private FragmentManager fragmentManager;

    /**
     * 广告条
     */
    private ConvenientBanner banner;

    public MallAdapter(Context context, List<BaseData> datas, boolean isOpenLoadMore, FragmentManager fragmentManager) {
        super(context, datas, isOpenLoadMore);
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @Override
    protected void convert(ViewHolder holder, BaseData data, int position, int viewType) {
        if (0 == position && viewType == TYPE_HEAD) {
            if (data instanceof MallIndexJson) {
                MallIndexJson mallIndexJson = (MallIndexJson) data;
                setAdvBanner(holder, mallIndexJson.getData().getMall().getScrollImg());
                setNavigatorIcon(holder, mallIndexJson.getData().getMall().getNavigatorIcon());
                setCell(holder, mallIndexJson.getData().getMall());
                setSpecialTopicList(holder, mallIndexJson.getData().getSpecialTopicList());
                setTopic(holder, mallIndexJson.getData().getMall().getTopic());
                setCategory(holder, mallIndexJson.getData().getMall().getCategory());
            }

        } else {
            if (data instanceof RecommendProductsJson.GoodsListBean) {
                RecommendProductsJson.GoodsListBean goodsListBean = (RecommendProductsJson.GoodsListBean) data;
                Imager.load(context, goodsListBean.getImage(), (ImageView) holder.getView(R.id.goods_img));
                holder.setText(R.id.goods_title, goodsListBean.getName());
                holder.setText(R.id.goods_price, String.format(context.getString(R.string.goods_price),
                        (float) goodsListBean.getMinSalePrice() / 100.0 + ""));

                if (!TextUtils.isEmpty(goodsListBean.getIconText())) {
                    holder.getView(R.id.goods_iconText).setVisibility(View.VISIBLE);
                    holder.setBgColor(R.id.goods_iconText, Color.parseColor(goodsListBean.getBackground()));
                    holder.setText(R.id.goods_iconText, goodsListBean.getIconText());
                } else {
                    holder.getView(R.id.goods_iconText).setVisibility(View.GONE);
                }
            }
        }

    }

    /**
     * 设置顶部循环滚动的广告条
     *
     * @param scrollImg
     */
    private void setAdvBanner(ViewHolder holder, List<MallIndexJson.DataBean.MallBean.ScrollImgBean> scrollImg) {
        List<String> imageList = new ArrayList<>();
        List<String> urlList = new ArrayList<>();
        for (int i = 0; i < scrollImg.size(); i++) {
            imageList.add(scrollImg.get(i).getImage());
            urlList.add(scrollImg.get(i).getUrl());
        }

        banner = holder.getView(R.id.topAdvBanner);
        banner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, imageList).setPageIndicator(new int[]{R.drawable.dot_indicator, R.drawable.dot_indicator_selector})
                .setOnItemClickListener(new TopAdvBannerListener(urlList))
                .startTurning(4000);
    }

    class TopAdvBannerListener implements com.bigkoo.convenientbanner.listener.OnItemClickListener{
        private List<String> urlList;

        public TopAdvBannerListener(List<String> urlList) {
            this.urlList = urlList;
        }

        @Override
        public void onItemClick(int position) {
            Intent intent = new Intent(context, WebActivity.class);
            intent.putExtra(Constants.GOTO_URL,urlList.get(position));
            context.startActivity(intent);
        }
    }

    public void relaseBanner() {
        if (banner != null) {
            banner.stopTurning();
        }
    }

    /**
     * 设置分类商品
     *
     * @param holder
     * @param category
     */
    private void setCategory(ViewHolder holder, List<MallIndexJson.DataBean.MallBean.CategoryBean> category) {
        RecyclerView rv_category = holder.getView(R.id.rv_category);
        CategoryGoodsAdapter categoryGoodsAdapter = new CategoryGoodsAdapter(context, category, false);
        rv_category.setHasFixedSize(true);
        rv_category.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rv_category.setAdapter(categoryGoodsAdapter);
    }

    private int tabPosition = -1;

    /**
     * 设置主题商品
     *
     * @param holder
     * @param topic
     */
    private void setTopic(ViewHolder holder, List<MallIndexJson.DataBean.MallBean.TopicBean> topic) {
        final List<Fragment> subFragments = new ArrayList<>();
        TabLayout tabs_topic = holder.getView(R.id.tabs_topic);
        tabs_topic.removeAllTabs();
        subFragments.clear();
        for (int i = 0; i < topic.size(); i++) {
            TabLayout.Tab tab = tabs_topic.newTab();

            ImageView topicImg = (ImageView) LayoutInflater
                    .from(context)
                    .inflate(R.layout.fragment_mall_topic_tab, null);
            ViewGroup.LayoutParams params =
                    new ViewGroup.LayoutParams(DensityUtil.dp2px(80.0f), DensityUtil.dp2px(80.0f));
            topicImg.setLayoutParams(params);
            Imager.load(context, topic.get(i).getUncheckImage(), topicImg);
            tab.setCustomView(topicImg);


            tabs_topic.addTab(tab, i == (tabPosition == -1 ? 0 : tabPosition) ? true : false);
            subFragments.add(TopicSubListfragment.newInstance(topic.get(i)));
        }
        //默认显示第一个Fragment
        if (tabPosition == -1) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (subFragments.get(0).isAdded()) {
                transaction.show(subFragments.get(0));
            } else {
                transaction.add(R.id.fl_topic, subFragments.get(0));
            }
            transaction.commit();
        }

        tabs_topic.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                tabPosition = position;
                Logger.e("TAG", "选中position:" + position);

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                if (subFragments.get(position).isAdded()) {
                    transaction.show(subFragments.get(position));
                } else {
                    transaction.add(R.id.fl_topic, subFragments.get(position));
                }
                transaction.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Logger.e("TAG", "解除position:" + position);
                if (subFragments.get(position).isAdded()) {
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.hide(subFragments.get(position));
                    transaction.commit();
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * 设置特别主题商品列表
     *
     * @param holder
     * @param specialTopicList
     */
    private void setSpecialTopicList(ViewHolder holder,
                                     List<MallIndexJson.DataBean.SpecialTopicListBean> specialTopicList) {
        RecyclerView rv_specialTopicList = holder.getView(R.id.rv_specialTopicList);
        SpecialTopicListAdapter specialTopicListAdapter =
                new SpecialTopicListAdapter(context, specialTopicList, false);
        rv_specialTopicList.setHasFixedSize(true);
        rv_specialTopicList.setLayoutManager(
                new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rv_specialTopicList.setAdapter(specialTopicListAdapter);
    }

    /**
     * 设置cellA、cellB、cellC
     *
     * @param holder
     * @param mall
     */
    private void setCell(ViewHolder holder, MallIndexJson.DataBean.MallBean mall) {
        Imager.load(context, mall.getCellA().getImg(), (ImageView) holder.getView(R.id.cellA));
        Imager.load(context, mall.getCellB().getImg(), (ImageView) holder.getView(R.id.cellB));

        Imager.load(context,
                mall.getCellC().getList().get(0).getImage(),
                (ImageView) holder.getView(R.id.cellC_0));
        Imager.load(context,
                mall.getCellC().getList().get(1).getImage(),
                (ImageView) holder.getView(R.id.cellC_1));
    }

    /**
     * 设置分类导航
     *
     * @param holder
     * @param navigatorIcon
     */
    private void setNavigatorIcon(ViewHolder holder,
                                  List<MallIndexJson.DataBean.MallBean.NavigatorIconBean> navigatorIcon) {

        RecyclerView rv_navigator = holder.getView(R.id.rv_navigator);
        NavigatorAdapter navigatorAdapter = new NavigatorAdapter(context, navigatorIcon, false);
        rv_navigator.setHasFixedSize(true);
        rv_navigator.setLayoutManager(new GridLayoutManager(context, 4, LinearLayoutManager.VERTICAL, false));
        rv_navigator.setAdapter(navigatorAdapter);
        navigatorAdapter.setOnItemClickListener(new OnItemClickListener<MallIndexJson.DataBean.MallBean.NavigatorIconBean>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, MallIndexJson.DataBean.MallBean.NavigatorIconBean data, int position) {
//                Toast.makeText(mContext, data.getIconTitle(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,GoodsListActivity.class);
                intent.putExtra(Constants.GOODS_URL,data.getUrl());
                context.startActivity(intent);
            }
        });
    }

    /**
     * 判断当前item是否是HeadView
     *
     * @param position
     */
    public boolean isHeaderView(int position) {
        return 0 == position ? true : false;//此处只添加一个header，若需添加多个header此处重新实现
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        if (viewType == TYPE_HEAD) {
            return R.layout.fragment_mall_head;
        }
        return R.layout.fragment_mall_comm;
    }

    @Override
    protected int getViewType(int position, BaseData data) {
        if (0 == position) {
            return TYPE_HEAD;
        }
        return TYPE_COMM;
    }

    class NavigatorAdapter extends CommonBaseAdapter<MallIndexJson.DataBean.MallBean.NavigatorIconBean> {
        private Context context;

        public NavigatorAdapter(Context context,
                                List<MallIndexJson.DataBean.MallBean.NavigatorIconBean> datas,
                                boolean isOpenLoadMore) {
            super(context, datas, isOpenLoadMore);
            this.context = context;
        }

        @Override
        protected void convert(ViewHolder holder,
                               MallIndexJson.DataBean.MallBean.NavigatorIconBean data,
                               int position) {
            Imager.load(context, data.getImage(), (ImageView) holder.getView(R.id.navigator_img));
            holder.setText(R.id.navigator_iconTitle, data.getIconTitle());
        }

        @Override
        protected int getItemLayoutId() {
            return R.layout.fragment_mall_navigator_item;
        }
    }

    @Deprecated
    class SpecialTopicListAdapter
            extends CommonBaseAdapter<MallIndexJson.DataBean.SpecialTopicListBean> {

        private Context context;

        public SpecialTopicListAdapter(Context context,
                                       List<MallIndexJson.DataBean.SpecialTopicListBean> datas,
                                       boolean isOpenLoadMore) {
            super(context, datas, isOpenLoadMore);
            this.context = context;
        }

        @Override
        protected void convert(ViewHolder holder,
                               MallIndexJson.DataBean.SpecialTopicListBean data, int position) {

            Imager.load(context,
                    data.getSpecialTopicImg(),
                    (ImageView) holder.getView(R.id.specialTopicImg));

            RecyclerView rv_relatedGoodsList = holder.getView(R.id.rv_relatedGoodsList);
            RelatedGoodsListAdapter adapter = new RelatedGoodsListAdapter(context,
                    data.getRelatedGoodsList(), false);
            rv_relatedGoodsList.setHasFixedSize(true);
            rv_relatedGoodsList.setLayoutManager(
                    new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            rv_relatedGoodsList.setAdapter(adapter);

        }

        @Override
        protected int getItemLayoutId() {
            return R.layout.fragment_mall_special_topic;
        }
    }

    class RelatedGoodsListAdapter extends
            CommonBaseAdapter<MallIndexJson.DataBean.SpecialTopicListBean.RelatedGoodsListBean> {

        private Context context;

        public RelatedGoodsListAdapter(Context context,
                                       List<MallIndexJson.DataBean.SpecialTopicListBean.RelatedGoodsListBean> datas, boolean isOpenLoadMore) {
            super(context, datas, isOpenLoadMore);
            this.context = context;
        }

        @Override
        protected void convert(ViewHolder holder,
                               MallIndexJson.DataBean.SpecialTopicListBean.RelatedGoodsListBean data,
                               int position) {

            Imager.load(context, data.getImg(), (ImageView) holder.getView(R.id.goods_img));
            holder.setText(R.id.goods_name, data.getName());
            holder.setText(R.id.goods_price, data.getPrice());
        }

        @Override
        protected int getItemLayoutId() {
            return R.layout.fragment_mall_special_topic_item;
        }
    }

    @Deprecated
    class CategoryGoodsAdapter
            extends CommonBaseAdapter<MallIndexJson.DataBean.MallBean.CategoryBean> {

        private Context context;

        public CategoryGoodsAdapter(Context context,
                                    List<MallIndexJson.DataBean.MallBean.CategoryBean> datas,
                                    boolean isOpenLoadMore) {
            super(context, datas, isOpenLoadMore);
            this.context = context;
        }

        @Override
        protected void convert(ViewHolder holder,
                               MallIndexJson.DataBean.MallBean.CategoryBean data,
                               int position) {

            holder.setBgColor(R.id.tag, Color.parseColor(data.getColorValue()));
            holder.setText(R.id.category_name, data.getName());

            Imager.load(context, data.getImage(), (ImageView) holder.getView(R.id.category_img));

            //subList
            List<MallIndexJson.DataBean.MallBean.CategoryBean.SubListBean> subList = data.getSubList();
            if (subList != null && subList.size() >= 3) {
                holder.getView(R.id.ll_subList).setVisibility(View.VISIBLE);
                Imager.load(context,
                        subList.get(0).getImage(),
                        (ImageView) holder.getView(R.id.goods1_img));
                holder.setText(R.id.goods1_title, subList.get(0).getTitle());

                Imager.load(context,
                        subList.get(1).getImage(),
                        (ImageView) holder.getView(R.id.goods2_img));
                holder.setText(R.id.goods2_title, subList.get(1).getTitle());

                Imager.load(context,
                        subList.get(2).getImage(),
                        (ImageView) holder.getView(R.id.goods3_img));
                holder.setText(R.id.goods4_title, subList.get(2).getTitle());
            } else {
                holder.getView(R.id.ll_subList).setVisibility(View.GONE);
            }
        }

        @Override
        protected int getItemLayoutId() {
            return R.layout.fragment_mall_category_item;
        }
    }
}
