package com.chailijun.mtime.mvp.interf;



public interface ITopListDetailsPresenter extends IPresenter{

    void getTopListDetailsByRecommend(int locationId, int pageSubAreaID, int pageIndex);

    void getTopListDetails(int topListId,int pageIndex);
}
