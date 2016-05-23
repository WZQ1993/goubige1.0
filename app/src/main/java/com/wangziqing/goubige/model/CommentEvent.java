package com.wangziqing.goubige.model;

import java.util.List;

/**
 * Created by WZQ_PC on 2016/5/12 0012.
 */
public class CommentEvent {
    public List<Comment> list;
    public String tag;
    public int code;
    public Comment data;

    public CommentEvent(){

    }
    public CommentEvent list(List<Comment> list){
        this.list=list;
        return this;
    }
    public CommentEvent data(Comment data){
        this.data=data;
        return this;
    }
    public CommentEvent tag(String tag){
        this.tag=tag;
        return this;
    }
    public CommentEvent code(int code){
        this.code=code;
        return this;
    }
}
