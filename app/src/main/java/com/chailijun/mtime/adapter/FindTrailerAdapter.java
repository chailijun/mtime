package com.chailijun.mtime.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.base.MultiBaseAdapter;
import com.chailijun.mtime.R;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.mvp.model.movie.TrailerList;
import com.chailijun.mtime.mvp.model.news.IndexInfo;
import com.chailijun.mtime.utils.Imager;

import java.util.List;


@Deprecated
public class FindTrailerAdapter extends MultiBaseAdapter<BaseData>{

    private static final int TYPE_INDEX = 1000;
    private static final int TYPE_COMM = 1001;

    private Context mContext;

    public FindTrailerAdapter(Context context, List<BaseData> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
        mContext = context;
    }

    @Override
    protected void convert(ViewHolder holder, BaseData data, int position, int viewType) {
        if (0 == position && viewType == TYPE_INDEX){
            if (data instanceof IndexInfo.TrailerBean){
                IndexInfo.TrailerBean trailerBean = (IndexInfo.TrailerBean) data;
                bindTrailerIndex(holder,trailerBean);
            }
        }else {
            if (data instanceof TrailerList.TrailersBean){
                TrailerList.TrailersBean trailersBean = (TrailerList.TrailersBean) data;
                bindTrailerComm(holder,trailersBean);
            }
        }

    }

    private void bindTrailerComm(ViewHolder holder, TrailerList.TrailersBean trailersBean) {

        Imager.load(mContext, trailersBean.getCoverImg(), (ImageView) holder.getView(R.id.iv_cover_img));
        holder.setText(R.id.tv_movie_name,trailersBean.getMovieName());
        holder.setText(R.id.tv_summary,trailersBean.getSummary());
    }

    private void bindTrailerIndex(ViewHolder holder, IndexInfo.TrailerBean trailerBean) {

        Imager.load(mContext,trailerBean.getImageUrl(), (ImageView) holder.getView(R.id.iv_img));
        holder.setText(R.id.tv_title,trailerBean.getTitle());
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        if (viewType == TYPE_INDEX){
            return R.layout.fragment_find_trailer_index;
        }
        return R.layout.fragment_find_trailer_comm;
    }

    @Override
    protected int getViewType(int position, BaseData data) {
        if (0 == position){
            return TYPE_INDEX;
        }
        return TYPE_COMM;
    }
}
