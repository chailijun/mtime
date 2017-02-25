package com.chailijun.mtime.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.chailijun.mtime.R;
import com.chailijun.mtime.utils.DensityUtil;

import java.util.List;

/**
 * ListView的Adapter
 */
public class SearchHistoryAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mData;
    /**listView的根布局*/
    private View mRootView;

    public SearchHistoryAdapter(Context context,List<String> mData,View rootView) {
        this.mContext = context;
        this.mData = mData;
        this.mRootView = rootView;
    }

    public void clearData(){
        mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if (mData == null || mData.size() == 0){
            mRootView.setVisibility(View.GONE);
        }else {
            mRootView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getCount() {
        return mData == null ? 0 :mData.size();
    }

    @Override
    public Object getItem(int positon) {
        return mData.get(positon);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int positon, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            view = LayoutInflater
                    .from(mContext)
                    .inflate(R.layout.activity_search_movie_history_item,viewGroup,false);
            holder = new ViewHolder();
            holder.textView = (TextView) view.findViewById(R.id.tv_history);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        holder.textView.setText(mData.get(positon));
        return view;
    }

    public void addFooter(final ListView listView, final FooterOnClickLisenter lisenter){

        //有且仅有一个footer
        if(listView.getFooterViewsCount()>0){
            return;
        }

        TextView clearTextView = new TextView(mContext);
        clearTextView.setText(mContext.getString(R.string.click_clear_history));
        clearTextView.setTextColor(ContextCompat.getColor(mContext,R.color.choose_movie_border));
        AbsListView.LayoutParams params =
                new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        DensityUtil.dp2px(50.0f));
        clearTextView.setLayoutParams(params);
        clearTextView.setGravity(Gravity.CENTER);

        listView.addFooterView(clearTextView);

        clearTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lisenter != null) lisenter.onClick();
            }
        });
    }

    private static class ViewHolder{
        TextView textView;
    }

    public interface FooterOnClickLisenter{

        void onClick();
    }
}
