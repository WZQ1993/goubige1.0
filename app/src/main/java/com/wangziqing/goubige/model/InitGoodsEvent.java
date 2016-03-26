package com.wangziqing.goubige.model;

import com.wangziqing.goubige.http.GoodsResponse;

/**
 * Created by WZQ_PC on 2016/2/21 0021.
 */
public class InitGoodsEvent {
    public String type;
    public InitGoodsEvent(GoodsResponse goodsResponse){
        this.goodsResponse=goodsResponse;
    }
    public GoodsResponse goodsResponse;
    public InitGoodsEvent type(String type){
        this.type=type;
        return this;
    }
}
