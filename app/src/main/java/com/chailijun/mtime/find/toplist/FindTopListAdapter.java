package com.chailijun.mtime.find.toplist;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chailijun.mtime.R;
import com.chailijun.mtime.data.find.toplist.TopListItem;

import java.util.List;


public class FindTopListAdapter extends BaseQuickAdapter<TopListItem,BaseViewHolder>{

    public FindTopListAdapter(List<TopListItem> data) {
        super(R.layout.fragment_find_top_list_comm, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, TopListItem item) {
        holder.setText(R.id.top_list_name,item.getTopListNameCn().trim());
        holder.setText(R.id.tv_summary,item.getSummary().trim());
    }
}
