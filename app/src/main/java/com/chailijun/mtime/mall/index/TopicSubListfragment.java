package com.chailijun.mtime.mall.index;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chailijun.mtime.R;
import com.chailijun.mtime.base.BaseFragment;
import com.chailijun.mtime.data.mall.MallIndex;
import com.chailijun.mtime.mvp.model.mall.MallIndexJson;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Imager;

import java.util.List;

import static android.R.attr.data;
import static com.chailijun.mtime.R.id.backgroundImage;
import static com.chailijun.mtime.R.id.rv_topic_sublist;
import static com.chailijun.mtime.R.id.titleCn;
import static com.chailijun.mtime.R.id.titleEn;


public class TopicSubListfragment extends BaseFragment{

    public static final String ARGUMENT = "argument";

    private ImageView mBackgroupImage;
    private TextView mTitleEn;
    private TextView mTitleCn;

    private RecyclerView rvTopicSublist;

    private MallIndex.MallBean.TopicBean mTopicBean;

    public static TopicSubListfragment newInstance(MallIndex.MallBean.TopicBean argument) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARGUMENT,argument);

        TopicSubListfragment fragment = new TopicSubListfragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mTopicBean = (MallIndex.MallBean.TopicBean) arguments.getSerializable(ARGUMENT);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_mall_topic_sublist;
    }

    @Override
    public void initView(View view) {
        mBackgroupImage = $(view, backgroundImage);
        mTitleEn = $(view,R.id.titleEn);
        mTitleCn = $(view,R.id.titleCn);
        rvTopicSublist = $(view,R.id.rv_topic_sublist);

        Imager.load(getActivity(),mTopicBean.getBackgroupImage(), mBackgroupImage);
        mTitleEn.setText(mTopicBean.getTitleEn());
        mTitleCn.setText(mTopicBean.getTitleCn());

        TopicSubListAdapter subListAdapter = new TopicSubListAdapter(mTopicBean.getSubList());
        rvTopicSublist.setHasFixedSize(true);
        rvTopicSublist.setLayoutManager(new GridLayoutManager(getActivity(),3));
        rvTopicSublist.setAdapter(subListAdapter);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void widgetClick(View v) {

    }

    class TopicSubListAdapter extends BaseQuickAdapter<MallIndex.MallBean.TopicBean.SubListBean,BaseViewHolder>{

        public TopicSubListAdapter(List<MallIndex.MallBean.TopicBean.SubListBean> data) {
            super(R.layout.fragment_mall_topic_sublist_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MallIndex.MallBean.TopicBean.SubListBean data) {
            Imager.load(mContext,data.getImage(), (ImageView) helper.getView(R.id.goods_img));
            helper.setText(R.id.goods_title,data.getTitle());
            helper.setText(R.id.goods_title,data.getTitle());
        }
    }
}
