package com.wangziqing.goubige.model;

import com.wangziqing.goubige.http.GoodsResponse;

/**
 * Created by WZQ_PC on 2016/2/23 0023.
 */
public class LoadedMoreEvent {
    public boolean isSuccess=true;
    public GoodsResponse goodsResponse;
    public LoadedMoreEvent goodsResponse(GoodsResponse goodsResponse){
        this.goodsResponse=goodsResponse;
        return this;
    }
    public LoadedMoreEvent isSuccess(boolean isSuccess){
        this.isSuccess=isSuccess;
        return this;
    }
}
