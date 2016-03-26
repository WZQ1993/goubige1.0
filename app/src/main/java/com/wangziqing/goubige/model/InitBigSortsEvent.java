package com.wangziqing.goubige.model;

import java.util.List;

/**
 * Created by WZQ_PC on 2016/2/26 0026.
 */
public class InitBigSortsEvent {
    public List<Sort> data;
    public InitBigSortsEvent sorts(List<Sort> data){
        this.data=data;
        return this;
    }
}