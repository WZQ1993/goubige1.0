package com.wangziqing.goubige.model;

import java.util.List;

/**
 * Created by WZQ_PC on 2016/5/11 0011.
 */
public class GetUsersEvent {
    public List<Users> users;
    public int pageNum;
    public int pageSize;
    public String method;
    public String tag;
    public GetUsersEvent(){
    }
}
