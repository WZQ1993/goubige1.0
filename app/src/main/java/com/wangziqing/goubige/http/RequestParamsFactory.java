package com.wangziqing.goubige.http;

import com.android.volley.Request;
import com.wangziqing.goubige.model.Page;

import org.xutils.http.RequestParams;

/**
 * Created by WZQ_PC on 2016/3/26 0026.
 */
public class RequestParamsFactory {
    private static final String host="http://192.168.0.107:8080/goubige";

    /**
     *
     * @param path 示例：/user/id
     * @return
     */
    public static RequestParams build(String path){
        return new RequestParams(host+path);
    }
    public static UserUpdateParams getUserUpdateParams(int ID){
        return new UserUpdateParams(host+"/user/"+String.valueOf(ID));
    }

    public static RequestParams getUserDetailsByIDParams(int ID){
        return new RequestParams(host+"/user/"+ID);
    }

    public static RequestParams getUsersByPage(Page page,String tag){
        return new RequestParams(host+"/user?"+"pageNum="+page.page+"&&pageSize="+page.pageSize+"&&tag="+tag);
    }

    public static RequestParams getShareByPage(Page page,String tag){
        return new RequestParams(host+"/share?"+"pageNum="+page.page+"&&pageSize="+page.pageSize+"&&tag="+tag);
    }
    public static RequestParams getCommentByPage(Page page,String tag,int shareID){
        return new RequestParams(host+"/comment?"+"pageNum="+page.page+"&&pageSize="+page.pageSize+"&&tag="+tag+"&&shareID="+shareID);
    }
    public static RequestParams getGoodByPage(Page page,String tag){
        return new RequestParams(host+"/good?"+"pageNum="+page.page+"&&pageSize="+page.pageSize+"&&tag="+tag);
//        return new RequestParams("http://baidu.com");
    }
}
