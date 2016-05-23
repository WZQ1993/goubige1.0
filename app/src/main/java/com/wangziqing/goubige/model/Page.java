package com.wangziqing.goubige.model;

/**
 * Created by WZQ_PC on 2016/5/11 0011.
 */
public class Page {
    public int page=1;
    public int pageSize=10;
    public int total;

    public Page page(int page){
        this.page=page;
        return this;
    }
    public Page pageSize(int pageSize){
        this.pageSize=pageSize;
        return this;
    }
    public Page total(int total){
        this.total=total;
        return this;
    }
}
