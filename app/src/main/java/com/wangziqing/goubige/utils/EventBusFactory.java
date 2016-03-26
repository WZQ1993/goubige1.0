package com.wangziqing.goubige.utils;

import com.google.common.eventbus.EventBus;

/**
 * Created by WZQ_PC on 2016/2/19 0019.
 */
public  class EventBusFactory {
    private EventBusFactory(){

    }
    private static EventBus httpEventBus;
    public  static EventBus getHttpEventBus(){
        if(httpEventBus==null){
            httpEventBus=new EventBus();
        }
        return httpEventBus;
    }

}
