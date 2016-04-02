package com.wangziqing.goubige.model;

/**
 * Created by WZQ_PC on 2016/4/2 0002.
 */
public class InitNavigationViewDataEvent {
    public Users user;
    public InitNavigationViewDataEvent user(Users user){
        this.user=user;
        return this;
    }
}
