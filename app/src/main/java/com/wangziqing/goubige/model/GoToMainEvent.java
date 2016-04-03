package com.wangziqing.goubige.model;

/**
 * Created by WZQ_PC on 2016/4/3 0003.
 */
public class GoToMainEvent {
    public Users user;
    public GoToMainEvent user(Users user){
        this.user=user;
        return this;
    }
}
