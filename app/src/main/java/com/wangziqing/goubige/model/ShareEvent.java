package com.wangziqing.goubige.model;

import java.util.List;

/**
 * Created by WZQ_PC on 2016/5/12 0012.
 */
public class ShareEvent{
    public List<Share> list;
    public String tag;
    public int code;
    public Share data;

    public ShareEvent(){

    }
    public ShareEvent list(List<Share> list){
        this.list=list;
        return this;
    }
    public ShareEvent data(Share data){
        this.data=data;
        return this;
    }
    public ShareEvent tag(String tag){
        this.tag=tag;
        return this;
    }
    public ShareEvent code(int code){
        this.code=code;
        return this;
    }
}
