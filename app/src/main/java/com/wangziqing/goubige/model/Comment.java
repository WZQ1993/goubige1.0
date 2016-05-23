package com.wangziqing.goubige.model;

import java.sql.Timestamp;

/**
 * Created by WZQ_PC on 2016/5/16 0016.
 */
public class Comment {
    private int ID;
    private int shareID;
    private int userID;
    private String content;
    private Timestamp creatTime;

    //自增
    private String userName;
    private String userImage;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Timestamp getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Timestamp creatTime) {
        this.creatTime = creatTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getShareID() {
        return shareID;
    }

    public void setShareID(int shareID) {
        this.shareID = shareID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "ID=" + ID +
                ", shareID=" + shareID +
                ", userID=" + userID +
                ", content='" + content + '\'' +
                ", creatTime=" + creatTime +
                '}';
    }
}
