package com.wangziqing.goubige.model;

/**
 * Created by WZQ_PC on 2016/3/26 0026.
 */
public class StartRegisterEvent {
    public Users user;
    public StartRegisterEvent user(Users user){
        this.user=user;
        return this;
    }
}
