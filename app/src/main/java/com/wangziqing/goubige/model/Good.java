package com.wangziqing.goubige.model;

import java.sql.Timestamp;

/**
    * goods 实体类
    * Mon Jan 18 14:31:50 CST 2016 王梓青
    */ 


public class Good{
	private String ID;
	private String title;
	private String url;
	private String pic;
	private int sold;
	private int priceCurrent;
	private int priceOld;
	private byte discount;
	private int from;
	private String placeOfDeliver;
	private String express;
	private int fare;
	private int brand;
	private int blockId;
	private int tagId;
	private int commentsNum;
	private int isGood;
	private int isGeneral;
	private int isBad;
	private Timestamp updateTime;
	public void setID(String ID){
	this.ID=ID;
	}
	public String getID(){
		return ID;
	}
	public void setTitle(String title){
	this.title=title;
	}
	public String getTitle(){
		return title;
	}
	public void setUrl(String url){
	this.url=url;
	}
	public String getUrl(){
		return url;
	}
	public void setPic(String pic){
	this.pic=pic;
	}
	public String getPic(){
		return pic;
	}
	public void setSold(int sold){
	this.sold=sold;
	}
	public int getSold(){
		return sold;
	}
	public void setPriceCurrent(int priceCurrent){
	this.priceCurrent=priceCurrent;
	}
	public int getPriceCurrent(){
		return priceCurrent;
	}
	public void setPriceOld(int priceOld){
	this.priceOld=priceOld;
	}
	public int getPriceOld(){
		return priceOld;
	}
	public void setDiscount(byte discount){
	this.discount=discount;
	}
	public byte getDiscount(){
		return discount;
	}
	public void setFrom(int from){
	this.from=from;
	}
	public int getFrom(){
		return from;
	}
	public void setPlaceOfDeliver(String placeOfDeliver){
	this.placeOfDeliver=placeOfDeliver;
	}
	public String getPlaceOfDeliver(){
		return placeOfDeliver;
	}
	public void setExpress(String express){
	this.express=express;
	}
	public String getExpress(){
		return express;
	}
	public void setFare(int fare){
	this.fare=fare;
	}
	public int getFare(){
		return fare;
	}
	public void setBrand(int brand){
	this.brand=brand;
	}
	public int getBrand(){
		return brand;
	}
	public void setBlockId(int blockId){
	this.blockId=blockId;
	}
	public int getBlockId(){
		return blockId;
	}
	public void setTagId(int tagId){
	this.tagId=tagId;
	}
	public int getTagId(){
		return tagId;
	}
	public void setCommentsNum(int commentsNum){
	this.commentsNum=commentsNum;
	}
	public int getCommentsNum(){
		return commentsNum;
	}
	public void setIsGood(int isGood){
	this.isGood=isGood;
	}
	public int getIsGood(){
		return isGood;
	}
	public void setIsGeneral(int isGeneral){
	this.isGeneral=isGeneral;
	}
	public int getIsGeneral(){
		return isGeneral;
	}
	public void setIsBad(int isBad){
	this.isBad=isBad;
	}
	public int getIsBad(){
		return isBad;
	}
	public void setUpdateTime(Timestamp updateTime){
	this.updateTime=updateTime;
	}
	public Timestamp getUpdateTime(){
		return updateTime;
	}
	@Override
	public String toString(){
		return "ID="+ID+"/n"+
				 "title="+title+"/n"+
				 "url="+url+"/n";
	}
}

