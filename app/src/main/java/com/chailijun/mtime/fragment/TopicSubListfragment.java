package com.chailijun.mtime.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.base.CommonBaseAdapter;
import com.chailijun.mtime.R;
import com.chailijun.mtime.mvp.model.mall.MallIndexJson;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.Logger;

import java.util.List;

import butterknife.BindView;

@Deprecated
public class TopicSubListfragment extends BaseFragment{

    @BindView(R.id.backgroundImage)
    ImageView backgroundImage;
    @BindView(R.id.titleEn)
    TextView titleEn;
    @BindView(R.id.titleCn)
    TextView titleCn;

    @BindView(R.id.rv_topic_sublist)
    RecyclerView rv_topic_sublist;

    private TopicSubListAdapter adapter;

    public static TopicSubListfragment newInstance(MallIndexJson.DataBean.MallBean.TopicBean param) {
        TopicSubListfragment fragment = new TopicSubListfragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.TOPIC_GOODS,param);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public String getFragmentName() {
        return TopicSubListfragment.class.getSimpleName();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mall_topic_sublist;
    }

    @Override
    protected void initViews(View rootView) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle arguments = getArguments();
        MallIndexJson.DataBean.MallBean.TopicBean topicBean = (MallIndexJson.DataBean.MallBean.TopicBean) arguments.getSerializable(Constants.TOPIC_GOODS);
        Logger.e("TAG","111====="+topicBean.getTitleCn());


        Imager.load(getActivity(),topicBean.getBackgroupImage(), backgroundImage);
        titleEn.setText(topicBean.getTitleEn());
        titleCn.setText(topicBean.getTitleCn());

        adapter = new TopicSubListAdapter(getActivity(),topicBean.getSubList(),false);
        rv_topic_sublist.setLayoutManager(new GridLayoutManager(getActivity(),3));
        rv_topic_sublist.setAdapter(adapter);
    }

    @Override
    protected void lazyLoadData() {

    }

    class TopicSubListAdapter extends CommonBaseAdapter<MallIndexJson.DataBean.MallBean.TopicBean.SubListBean>{

        private Context context;
        public TopicSubListAdapter(Context context, List<MallIndexJson.DataBean.MallBean.TopicBean.SubListBean> datas, boolean isOpenLoadMore) {
            super(context, datas, isOpenLoadMore);
            this.context = context;
        }

        @Override
        protected void convert(ViewHolder holder, MallIndexJson.DataBean.MallBean.TopicBean.SubListBean data, int position) {
            Imager.load(context,data.getImage(), (ImageView) holder.getView(R.id.goods_img));
            holder.setText(R.id.goods_title,data.getTitle());
            holder.setText(R.id.goods_title,data.getTitle());
        }

        @Override
        protected int getItemLayoutId() {
            return R.layout.fragment_mall_topic_sublist_item;
        }
    }
}
