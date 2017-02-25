package com.chailijun.mtime.mvp.interf;



public interface ISearchMoviePresenter extends IPresenter{

    void getSearchMovie(int sortType,
                        String areas,
                        String genreTypes,
                        int sortMethod,
                        String years,
                        int pageIndex);
}
