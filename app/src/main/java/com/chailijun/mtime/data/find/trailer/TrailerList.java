package com.chailijun.mtime.data.find.trailer;

import java.util.List;

/**
 * author： Chailijun
 * date  ： 2017/1/15 17:16
 * e-mail： 1499505466@qq.com
 */

public class TrailerList {

    /**
     * id : 64103
     * movieName : 《悟空传》首款预告片
     * coverImg : http://img5.mtime.cn/mg/2017/01/05/143020.23995254.jpg
     * movieId : 226435
     * url : http://vfx.mtime.cn/Video/2017/01/05/mp4/170105093327060871_480.mp4
     * hightUrl : http://vfx.mtime.cn/Video/2017/01/05/mp4/170105093327060871.mp4
     * videoTitle : 悟空传 预告片之我是悟空
     * videoLength : 65
     * rating : -1
     * type : ["奇幻"]
     * summary : 最叛逆悟空横空出世
     */

    private List<TrailerItem> trailers;

    public List<TrailerItem> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<TrailerItem> trailers) {
        this.trailers = trailers;
    }

}
