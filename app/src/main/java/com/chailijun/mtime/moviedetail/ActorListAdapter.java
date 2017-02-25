package com.chailijun.mtime.moviedetail;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chailijun.mtime.R;
import com.chailijun.mtime.data.moviedetail.MovieDetail;
import com.chailijun.mtime.utils.Imager;

import java.util.List;

/**
 * 电影详细--导演和演员的adapter
 */

public class ActorListAdapter extends BaseQuickAdapter<MovieDetail.BasicBean.ActorsBean,BaseViewHolder>{

    public ActorListAdapter(List<MovieDetail.BasicBean.ActorsBean> data) {
        super(R.layout.fragment_movie_detail_base_info_item_actors_item, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, MovieDetail.BasicBean.ActorsBean data) {

        Imager.load(mContext, data.getImg(), (ImageView) holder.getView(R.id.img));
        holder.setText(R.id.director_name, data.getName());
        holder.setText(R.id.director_name_en, data.getNameEn());

        int position = holder.getLayoutPosition();
        holder.getView(R.id.director_actor)
                .setVisibility(position < 2 ? View.VISIBLE : View.INVISIBLE);
        if (0 != position){
            holder.setText(R.id.role_name,
                    data.getRoleName().equals("") ? "饰：--" : "饰：" + data.getRoleName());
        }
        if (0 == position) {
            holder.setText(R.id.director_actor, mContext.getString(R.string.director));
        } else if (1 == position) {
            holder.setText(R.id.director_actor, mContext.getString(R.string.actor));
        }
    }
}
