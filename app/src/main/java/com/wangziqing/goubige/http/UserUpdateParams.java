package com.wangziqing.goubige.http;

import com.wangziqing.goubige.model.Users;

import org.xutils.http.RequestParams;

/**
 * Created by WZQ_PC on 2016/3/26 0026.
 */
public class UserUpdateParams extends RequestParams {
    UserUpdateParams(String url){
        super(url);
        this.setAsJsonContent(true);
    }
    public Users user;
    public UserUpdateParams user(Users user){
        this.user=user;
        return this;
    }
}
