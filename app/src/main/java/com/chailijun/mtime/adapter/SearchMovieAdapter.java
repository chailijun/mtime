package com.chailijun.mtime.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chailijun.baseadapter.ViewHolder;
import com.chailijun.baseadapter.base.MultiBaseAdapter;
import com.chailijun.mtime.R;
import com.chailijun.mtime.customview.WithTagImageView;
import com.chailijun.mtime.mvp.model.movie.SearchMovie;
import com.chailijun.mtime.mvp.model.movie.SearchMovie2;
import com.chailijun.mtime.utils.Imager;

import java.util.List;

import static com.chailijun.mtime.utils.UrlUtils.addPrefix;


public class SearchMovieAdapter extends MultiBaseAdapter<SearchMovie.MovieModelListBean> {

    public static final int TYPE_HEAD = 1000;
    public static final int TYPE_COMM = 1001;

    private Context mContext;

    public SearchMovieAdapter(Context context, List<SearchMovie.MovieModelListBean> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
        mContext = context;
    }

    @Override
    protected void convert(ViewHolder holder,
                           SearchMovie.MovieModelListBean data,
                           int position, int viewType) {
        if (0 != position && viewType == TYPE_COMM){
            Imager.load(mContext,addPrefix(data.getImg()), (ImageView) holder.getView(R.id.image));

            ((WithTagImageView) holder.getView(R.id.image)).setRating(data.getRatingFinal()+"");

            if (TextUtils.isEmpty(data.getTitleCn())){
                holder.setText(R.id.tv_movie_name,data.getTitleEn());
            }else {
                holder.setText(R.id.tv_movie_name,data.getTitleCn());
            }

            if (data.getRYear() > 0){
                holder.getView(R.id.tv_movie_year).setVisibility(View.VISIBLE);
                holder.setText(R.id.tv_movie_year,"("+data.getRYear()+")");
            }else {
                holder.getView(R.id.tv_movie_year).setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        if (viewType == TYPE_HEAD){
            return R.layout.activity_search_movie_fake;
        }
        return R.layout.activity_search_movie_item;
    }

    @Override
    protected int getViewType(int position, SearchMovie.MovieModelListBean data) {
        if (0 == position && data.getMovieId() == -1){
            return TYPE_HEAD;
        }
        return TYPE_COMM;
    }

    public boolean isHeaderView(int position) {
        return 0 == position ? true : false;//此处只添加一个header，若需添加多个header此处重新实现
    }
}
