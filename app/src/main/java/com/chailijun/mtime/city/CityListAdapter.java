package com.chailijun.mtime.city;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chailijun.mtime.R;
import com.chailijun.mtime.db.entity.City;

import java.util.List;


public class CityListAdapter extends BaseMultiItemQuickAdapter<CityBean,BaseViewHolder>{

    private OnItemClickLisenter lisenter;
    private LocationLisenter mLocationLisenter;

    /**
     * 显示定位结果
     */
    private TextView locationResultView;

    /**
     * 定位城市信息
     */
//    private City locationCity;

    public CityListAdapter(List<CityBean> data) {
        super(data);
        addItemType(CityBean.TYPE_GRID, R.layout.activity_search_city_item_grid);
        addItemType(CityBean.TYPE_LINE, R.layout.activity_search_city_item_line);
        addItemType(CityBean.TYPE_LOCATION, R.layout.activity_search_city_item_location);
    }

    @Override
    protected void convert(BaseViewHolder helper, CityBean item) {
        switch (helper.getItemViewType()){
            case CityBean.TYPE_GRID:
                helper.addOnClickListener(R.id.rv_hot_city);
                bindGridTypeData(helper,item);

                break;
            case CityBean.TYPE_LINE:
                helper.setText(R.id.tvCity,item.getCityList().get(0).getN());

                break;
            case CityBean.TYPE_LOCATION:
                bindLocationData(helper);

                break;
            default:
                break;
        }
    }

    /**
     * 设置定位
     * @param helper
     */
    private void bindLocationData(final BaseViewHolder helper) {
        helper.addOnClickListener(R.id.tv_location);
        locationResultView = helper.getView(R.id.tv_location);
    }

    public void setLocationSuccess(final City result){
        if (locationResultView != null && result != null){
            locationResultView.setText(result.getN());

            if (result.getId() != null){

                locationResultView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (lisenter != null && result != null){
                            lisenter.onClickItem(result,-1);
                        }
                    }
                });
            }
        }
    }

    public void setLocationFailed(String msg){
        if (locationResultView != null){
            locationResultView.setText(msg);

            //定位失败才可以重新定位
            locationResultView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mLocationLisenter != null){
                        mLocationLisenter.onLocationClick();
                    }
                }
            });
        }
    }

    private void bindGridTypeData(BaseViewHolder holder, CityBean data) {

        HotCityAdapter adapter = new HotCityAdapter(data.getCityList());
        RecyclerView recyclerView = holder.getView(R.id.rv_hot_city);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    class HotCityAdapter extends BaseQuickAdapter<City,BaseViewHolder>{

        public HotCityAdapter(List<City> data) {
            super(R.layout.activity_search_city_item_hotcity_item, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final City item) {
            helper.setText(R.id.tvName,item.getN());

            helper.addOnClickListener(R.id.tvName);
            helper.getView(R.id.tvName).setOnClickListener(v -> {
                if (lisenter != null){
                    lisenter.onClickItem(item,helper.getLayoutPosition());
                }
            });
        }

    }

    public void setGridItemClickLisenter(OnItemClickLisenter lisenter) {
        this.lisenter = lisenter;
    }

    public void setmLocationLisenter(LocationLisenter mLocationLisenter) {
        this.mLocationLisenter = mLocationLisenter;
    }


    /**
     * grid类型的item中子item的点击监听
     */
    public interface OnItemClickLisenter{

        void onClickItem(City data, int position);
    }

    /**
     * 触发定位监听
     */
    public interface LocationLisenter{

        void onLocationClick();
    }


}
