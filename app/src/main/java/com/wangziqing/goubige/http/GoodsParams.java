package com.wangziqing.goubige.http;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/**
 * Created by wyouflf on 15/11/4.
 */@HttpRequest(
        //host表示主机域名或IP地址
        host = "http://192.168.0.107:8080/goubige",
        //path表示请求的资源
        path = "/goods"
        /*builder = DefaultParamsBuilder.class可选参数, 控制参数构建过程, 定义参数签名, SSL证书等*/)

public class GoodsParams extends RequestParams {
    //作为请求参数wd=?
    public int pageSize;
    public int pageNum;
    public String requestID;

    public GoodsParams() {
        // this.setMultipart(true); // 使用multipart表单
        // this.setAsJsonContent(true); // 请求body将参数转换为json形式发送
    }
    public GoodsParams pageNum(int pageNum){
        this.pageNum=pageNum;
        return this;
    }
    public GoodsParams pageSize(int pageSize){
        this.pageSize=pageSize;
        return this;
    }
    public GoodsParams requestID(String requestID){
        this.requestID=requestID;
        return this;
    }
    public static GoodsParams copyFrom(GoodsParams oldGoodsParams){
        return new GoodsParams()
                .pageSize(oldGoodsParams.pageSize)
                .pageNum(oldGoodsParams.pageNum)
                .requestID(oldGoodsParams.requestID);
    }
    @Override
    public String toString() {
        return "GoodsParams{" +
                "pageSize=" + pageSize +
                ", pageNum=" + pageNum +
                ", requestID='" + requestID + '\'' +
                '}';
    }
    //public long timestamp = System.currentTimeMillis();
    //public File uploadFile; // 上传文件
    //public List<File> files; // 上传文件数组
}
