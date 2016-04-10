package com.wangziqing.goubige.model;

/**
 * Created by WZQ_PC on 2016/4/10 0010.
 */
public class EventWithUser {
    public Users user;
    public String method;
    public EventWithUser method(String method){
        this.method=method;
        return this;
    }
    public EventWithUser user(Users user){
        this.user=user;
        return this;
    }
}
