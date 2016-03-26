package com.wangziqing.goubige.utils;

/**
 * Created by WZQ_PC on 2016/3/13 0013.
 */
public enum RegexType {
    PHONE("^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$"),
    EMAIL("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    private String content;
    RegexType(String content){
        this.content=content;
    }

    @Override
    public String toString() {
        return content;
    }
}
