package com.wangziqing.goubige.model;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by WZQ_PC on 2016/5/12 0012.
 */
public class BaseEvent <T>{
    public List<Share> list;
    public String tag;
    public int code;
    public T data;

    public BaseEvent(){

    }
    public BaseEvent list(List<Share> list){
        this.list=list;
        return this;
    }
    public BaseEvent data(T data){
        this.data=data;
        return this;
    }
    public BaseEvent tag(String tag){
        this.tag=tag;
        return this;
    }
    public BaseEvent code(int code){
        this.code=code;
        return this;
    }
}
