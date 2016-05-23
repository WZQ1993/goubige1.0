package com.wangziqing.goubige.model;

import java.sql.Timestamp;

/**
 * Created by WZQ_PC on 2016/5/16 0016.
 */
public class Support {
    private int ID;
    private int shareID;
    private int userID;
    private Timestamp creatTime;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getShareID() {
        return shareID;
    }

    public void setShareID(int shareID) {
        this.shareID = shareID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Timestamp getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Timestamp creatTime) {
        this.creatTime = creatTime;
    }

    @Override
    public String toString() {
        return "Support{" +
                "ID=" + ID +
                ", shareID=" + shareID +
                ", userID=" + userID +
                ", creatTime=" + creatTime +
                '}';
    }
}
