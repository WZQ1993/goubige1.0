package com.wangziqing.goubige.model;

import java.util.List;

/**
 * Created by WZQ_PC on 2016/5/12 0012.
 */
public class GoodEvent{
    public List<Good> list;
    public String tag;
    public int code;
    public Good data;

    public GoodEvent(){

    }
    public GoodEvent list(List<Good> list){
        this.list=list;
        return this;
    }
    public GoodEvent data(Good data){
        this.data=data;
        return this;
    }
    public GoodEvent tag(String tag){
        this.tag=tag;
        return this;
    }
    public GoodEvent code(int code){
        this.code=code;
        return this;
    }
}
