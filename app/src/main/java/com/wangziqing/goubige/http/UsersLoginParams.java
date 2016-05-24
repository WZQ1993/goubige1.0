package com.wangziqing.goubige.http;

import com.wangziqing.goubige.model.Users;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/**
 * Created by WZQ_PC on 2016/3/6 0006.
 */
@HttpRequest(
        //host表示主机域名或IP地址
        host = "http://192.168.0.107:8080/goubige",
        //path表示请求的资源
        path = "user/authorizations"
//        builder =UsersParamsBuilder.class
)
public class UsersLoginParams extends RequestParams {
    public Users user;
    public UsersLoginParams(){
        this.setAsJsonContent(true);
    }
    public UsersLoginParams user(Users user) {
        this.user = user;
        return this;
    }
//    public UsersRegisterParams buildUserID(int ID){
//        this.set
//    }

}
