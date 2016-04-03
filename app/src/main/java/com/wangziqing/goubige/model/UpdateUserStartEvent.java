package com.wangziqing.goubige.model;

/**
 * Created by WZQ_PC on 2016/3/26 0026.
 */
public class UpdateUserStartEvent {
    public Users user;
    public UpdateUserStartEvent user(Users user){
        this.user=user;
        return this;
    }
}
