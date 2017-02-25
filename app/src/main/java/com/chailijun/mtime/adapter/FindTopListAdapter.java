package com.chailijun.mtime.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.base.MultiBaseAdapter;
import com.chailijun.mtime.R;
import com.chailijun.mtime.activity.BoxOfficeActivity;
import com.chailijun.mtime.activity.TopListActivity;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.mvp.model.TopListOfAll;
import com.chailijun.mtime.mvp.model.news.IndexInfo;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Imager;
import com.chailijun.mtime.utils.SPUtil;

import java.util.List;


@Deprecated
public class FindTopListAdapter extends MultiBaseAdapter<BaseData> {

    public static final int TYPR_INDEX = 10000;
    public static final int TYPE_COMM = 10001;

    private Context mContext;

    public FindTopListAdapter(Context context, List<BaseData> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);

        mContext = context;
    }

    @Override
    protected void convert(ViewHolder holder, BaseData data, int position, int viewType) {

        if (0 == position && viewType == TYPR_INDEX){
            if (data instanceof IndexInfo.TopListBean){
                IndexInfo.TopListBean topListBean = (IndexInfo.TopListBean) data;
                bindTopListIndex(holder,topListBean);
            }
        }else if (viewType == TYPE_COMM){
            if (data instanceof TopListOfAll.TopListsBean){
                TopListOfAll.TopListsBean topListsBean = (TopListOfAll.TopListsBean) data;
                bindTopListComm(holder,topListsBean);
            }
        }

    }

    private void bindTopListComm(ViewHolder holder, TopListOfAll.TopListsBean topListsBean) {

        holder.setText(R.id.top_list_name,topListsBean.getTopListNameCn().trim());
        holder.setText(R.id.tv_summary,topListsBean.getSummary().trim());
    }

    private void bindTopListIndex(ViewHolder holder, final IndexInfo.TopListBean topListBean) {

        Imager.load(mContext,topListBean.getImageUrl(), (ImageView) holder.getView(R.id.iv_img));
        holder.setText(R.id.tv_title,topListBean.getTitle());

        //index部分的点击事件
        holder.setOnClickListener(R.id.iv_img, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterTopList(topListBean.getId());
            }
        });

        //时光Top100
        holder.setOnClickListener(R.id.mtime_top100, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, TopListActivity.class);
                String areaID = SPUtil.get(Constants.MTIME_TOP100_AreaId, "2065");
                intent.putExtra(Constants.PAGESUB_AREA_ID, Integer.parseInt(areaID));
                mContext.startActivity(intent);
            }
        });

        //华语Top100
        holder.setOnClickListener(R.id.Chinese_movie_top100, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, TopListActivity.class);
                String areaID = SPUtil.get(Constants.Chinese_Language_TOP100_AreaId, "2066");
                intent.putExtra(Constants.PAGESUB_AREA_ID, Integer.parseInt(areaID));
                mContext.startActivity(intent);
            }
        });

        //进入票房榜（全球）
        holder.setOnClickListener(R.id.global_boxoffice, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, BoxOfficeActivity.class);
                intent.putExtra(Constants.AREA,Constants.GLOBAL);
                mContext.startActivity(intent);
            }
        });
    }

    /**
     * 进入排行榜页面
     * @param topListID
     */
    private void enterTopList(int topListID) {
        Intent intent = new Intent(mContext, TopListActivity.class);
        intent.putExtra(Constants.TOPLIST_ID,topListID);
        mContext.startActivity(intent);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        if (viewType == TYPR_INDEX){
            return R.layout.fragment_find_top_list_index;
        }
        return R.layout.fragment_find_top_list_comm;
    }

    @Override
    protected int getViewType(int position, BaseData data) {
        if (0 == position){
            return TYPR_INDEX;
        }
        return TYPE_COMM;
    }
}
