package com.chailijun.mtime.city;

import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chailijun.mtime.R;
import com.chailijun.mtime.db.entity.City;

import java.util.List;

import static android.R.attr.data;

/**
 * 城市搜索结果列表的Adapter
 */

public class CitySearchAdapter extends BaseQuickAdapter<City,BaseViewHolder>{

    //搜索框里面的字符
    private String highlightStr;

    public CitySearchAdapter(List<City> data) {
        super(R.layout.activity_search_city_item_line, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, City data) {
        holder.setText(R.id.tvCity, data.getN());
        if (!TextUtils.isEmpty(highlightStr)) {
            int start = -1;
            int end = -1;
            if (data.getN().contains(highlightStr)) {
                start = data.getN().indexOf(highlightStr);
                end = start + highlightStr.length();

                //设置textView中的文字颜色不一样
                SpannableStringBuilder spanBuilder = new SpannableStringBuilder(data.getN());
                int color = ContextCompat.getColor(mContext, R.color.colorOrange);
                spanBuilder.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                ((TextView) holder.getView(R.id.tvCity)).setText(spanBuilder);
            } else {
                holder.setText(R.id.tvCity, data.getN());
            }
        }
    }

    public void setHighlightStr(String highlightStr) {
        this.highlightStr = highlightStr;
    }

    public void clearHighlightStr() {
        this.highlightStr = "";
    }
}
