package com.wangziqing.goubige.http;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/**
 * Created by WZQ_PC on 2016/2/26 0026.
 */
@HttpRequest(
        //host表示主机域名或IP地址
        host = "http://192.168.0.107:8080/goubige",
        //path表示请求的资源
        path = "/sorts"
        /*builder = DefaultParamsBuilder.class可选参数, 控制参数构建过程, 定义参数签名, SSL证书等*/)
public class SortsParams extends RequestParams {
    @Override
    public String toString() {
        return "SortsParams{}"+super.toString();
    }
}
