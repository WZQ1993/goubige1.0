package com.wangziqing.goubige.http;

import com.google.common.collect.Lists;
import com.wangziqing.goubige.model.Sort;

import java.util.List;

/**
 * Created by WZQ_PC on 2016/2/26 0026.
 */
public class SortsResponse {
    List<Sort> data= Lists.newArrayList();
    public SortsResponse data(List<Sort> sorts){
        this.data=sorts;
        return this;
    }
}
