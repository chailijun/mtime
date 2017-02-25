package com.chailijun.mtime.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.TextView;

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.base.CommonBaseAdapter;
import com.chailijun.mtime.R;
import com.chailijun.mtime.adapter.decoration.DividerItemDecoration;
import com.chailijun.mtime.mvp.interf.ICinemasByCityPresenter;
import com.chailijun.mtime.mvp.interf.ICinemasByCityView;
import com.chailijun.mtime.mvp.model.BaseData;
import com.chailijun.mtime.mvp.model.CinemasByCityJson;
import com.chailijun.mtime.mvp.presenter.CinemasByCityPresenter;
import com.chailijun.mtime.utils.Constants;
import com.chailijun.mtime.utils.Logger;
import com.chailijun.mtime.utils.MsgEvent;
import com.chailijun.mtime.utils.SPUtil;

import java.util.List;

import butterknife.BindView;
import de.greenrobot.event.EventBus;

public class PayticketCinemaFragment extends BaseFragment implements ICinemasByCityView<BaseData> {

    @BindView(R.id.recyclerview_cinema)
    RecyclerView recyclerview_cinema;
    private int locationId;

    private CinemaAdapter mAdapter;
    private ICinemasByCityPresenter presenter;

    @Override
    public String getFragmentName() {
        return PayticketCinemaFragment.class.getSimpleName();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_payticket_cinema;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        presenter.unsubcrible();
    }

    @Override
    protected void initViews(View rootView) {
        getData();

        mAdapter = new CinemaAdapter(getActivity(),null,false);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview_cinema.setLayoutManager(manager);
        recyclerview_cinema.setHasFixedSize(true);
        recyclerview_cinema.setAdapter(mAdapter);
        recyclerview_cinema.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new CinemasByCityPresenter(this);
        if (locationId != 0){
            presenter.getCinemasByCity(locationId);
        }
    }

    private void getData() {
        locationId = SPUtil.getInt(Constants.LOCATION_ID);
    }

    @Override
    protected void lazyLoadData() {

    }

    public void onEventMainThread(MsgEvent msg) {
        if (msg.getWhat() == Constants.UPDATE_DATA) {
            locationId = SPUtil.getInt(Constants.LOCATION_ID);
            if (presenter != null && locationId != 0){
                mAdapter.removeAll();
                mAdapter.setInitLoadingView(R.layout.loading);
                presenter.getCinemasByCity(locationId);
            }
        }
    }

    @Override
    public void addCinemasByCity(List<CinemasByCityJson> data) {
        mAdapter.setNewData(data);

    }

    @Override
    public void loadFailed(String msg) {
        Logger.e("TAG","加载影院失败："+msg);

    }

    class CinemaAdapter extends CommonBaseAdapter<CinemasByCityJson>{

        private Context mContext;
        public CinemaAdapter(Context context, List<CinemasByCityJson> datas, boolean isOpenLoadMore) {
            super(context, datas, isOpenLoadMore);
            mContext = context;
        }

        @Override
        protected void convert(ViewHolder holder, CinemasByCityJson data, int position) {
            holder.setText(R.id.cinema_name,data.getCinameName());
            holder.setText(R.id.cinema_address,data.getAddress());
            if (data.getMinPrice() > 0){
                holder.getView(R.id.min_price).setVisibility(View.VISIBLE);
                String minPrice = String.format(getString(R.string.min_price), data.getMinPrice() / 100);

                //设置textView中的文字大小不一样
                SpannableStringBuilder spanBuilder = new SpannableStringBuilder(minPrice);
                spanBuilder.setSpan(new TextAppearanceSpan(null, 0, 60, null, null), 1, 3, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                ((TextView) holder.getView(R.id.min_price)).setText(spanBuilder);
            }else {
                holder.getView(R.id.min_price).setVisibility(View.GONE);
            }


            holder.getView(R.id.img_vip).setVisibility(data.getFeature().getHasVIP() > 0?View.VISIBLE:View.GONE);
            holder.getView(R.id.img_imax).setVisibility(data.getFeature().getHasIMAX() > 0?View.VISIBLE:View.GONE);
            holder.getView(R.id.img_parking).setVisibility(data.getFeature().getHasPark() > 0?View.VISIBLE:View.GONE);
            holder.getView(R.id.img_wififree).setVisibility(data.getFeature().getHasWifi() > 0?View.VISIBLE:View.GONE);


            /*ViewGroup cinema_feature = holder.getView(R.id.cinema_feature);
            Logger.e("TAG","孩子总数："+cinema_feature.getChildCount());
            if (data.getCouponActivityList().size()>0){
                List<CinemasByCityJson.CouponActivityListBean> couponActivityList = data.getCouponActivityList();
                for (int i = 0; i < couponActivityList.size(); i++) {
                    BorderTextView textView = new BorderTextView(mContext);
                    textView.setText(couponActivityList.get(i).getTag());
                    textView.setTextColor(0xFFFF532C);
                    textView.setTextSize(12);
                    textView.setCornerRadius(5);
                    cinema_feature.addView(textView);
                }
            }*/
            if (data.getCouponActivityList().size()>0){
                holder.getView(R.id.coupon).setVisibility(View.VISIBLE);
                holder.setText(R.id.coupon,data.getCouponActivityList().get(0).getTag());
            }else {
                holder.getView(R.id.coupon).setVisibility(View.GONE);
            }

        }

        @Override
        protected int getItemLayoutId() {
            return R.layout.fragment_payticket_cinema_item;
        }
    }
}
