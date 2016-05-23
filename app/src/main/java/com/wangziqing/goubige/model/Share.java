package com.wangziqing.goubige.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by WZQ_PC on 2016/5/16 0016.
 */
public class Share implements Serializable{
    private int ID;
    private String goodID;
    private int userID;
    private String content;
    private int supportNum;
    private int commentNum;
    private Timestamp creatTime;
    //增加
    private String goodImage;
    private String goodTitle;

    private String userImage;
    private String userName;

    private String goodUrl;
    private String goodPriceCurrent;
    private String goodPriceOld;
    private int goodFrom;

    public int getSupportNum() {
        return supportNum;
    }

    public void setSupportNum(int supportNum) {
        this.supportNum = supportNum;
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

    public String getGoodID() {
        return goodID;
    }

    public void setGoodID(String goodID) {
        this.goodID = goodID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public Timestamp getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Timestamp creatTime) {
        this.creatTime = creatTime;
    }

    public String getGoodImage() {
        return goodImage;
    }

    public void setGoodImage(String goodImage) {
        this.goodImage = goodImage;
    }

    public String getGoodTitle() {
        return goodTitle;
    }

    public void setGoodTitle(String goodTitle) {
        this.goodTitle = goodTitle;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGoodUrl() {
        return goodUrl;
    }

    public void setGoodUrl(String goodUrl) {
        this.goodUrl = goodUrl;
    }

    public String getGoodPriceCurrent() {
        return goodPriceCurrent;
    }

    public void setGoodPriceCurrent(String goodPriceCurrent) {
        this.goodPriceCurrent = goodPriceCurrent;
    }

    public String getGoodPriceOld() {
        return goodPriceOld;
    }

    public void setGoodPriceOld(String goodPriceOld) {
        this.goodPriceOld = goodPriceOld;
    }

    public int getGoodFrom() {
        return goodFrom;
    }

    public void setGoodFrom(int goodFrom) {
        this.goodFrom = goodFrom;
    }

    @Override
    public String toString() {
        return "Share{" +
                "ID=" + ID +
                ", goodID='" + goodID + '\'' +
                ", userID=" + userID +
                ", content='" + content + '\'' +
                ", supportNum=" + supportNum +
                ", commentNum=" + commentNum +
                ", creatTime=" + creatTime +
                '}';
    }
}
