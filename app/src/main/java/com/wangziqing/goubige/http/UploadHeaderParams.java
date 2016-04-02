package com.wangziqing.goubige.http;

import com.wangziqing.goubige.utils.UploadHeader;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

import java.io.File;

/**
 * Created by WZQ_PC on 2016/3/6 0006.
 */
@HttpRequest(
        //host表示主机域名或IP地址
        host = "http://192.168.1.20:8080/goubige",
        //path表示请求的资源
        path = "/user/userImg"
)
public class UploadHeaderParams extends RequestParams {
    private static final String USERIMG="userImg";
    private String telePhone;
    private String email;
    public UploadHeaderParams(){
        super();
        this.setMultipart(true);
    }
    public UploadHeaderParams telePhone(String telePhone){
        this.telePhone=telePhone;
        return this;
    }
    public UploadHeaderParams email(String email){
        this.email=email;
        return this;
    }
    public UploadHeaderParams ID(int ID){
        this.addParameter("ID",ID);
        return this;
    }
    public UploadHeaderParams file(File file){
        this.addBodyParameter(
                USERIMG,
                file,
                null); // 如果文件没有扩展名, 最好设置contentType参数.
        return this;
    }
}
