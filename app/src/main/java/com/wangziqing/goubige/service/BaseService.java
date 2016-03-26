package com.wangziqing.goubige.service;

import com.wangziqing.goubige.utils.EventBusFactory;

/**
 * Created by WZQ_PC on 2016/3/26 0026.
 */
public abstract class BaseService {
    BaseService() {
        EventBusFactory.getHttpEventBus().register(this);
    }
}
