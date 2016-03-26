package com.wangziqing.goubige.model;

import java.sql.Timestamp;

/**
    * sorts 实体类
    * Mon Jan 18 14:35:34 CST 2016 王梓青
    */ 


public class Sort{
	private int ID;
	private int upperId;
	private String content;
	private Timestamp creatTime;
	/*
	 * java取得当前时间
	 * 方法1： Timestamp d = new Timestamp(System.currentTimeMillis()); 
	 * 方法2： 
	 * Date date = new Date();     
	 * Timestamp nousedate = new Timestamp(date.getTime());
	 */
	public Sort() {
		
	}
	public Sort(int ID,int upperId,String content) {
		// TODO Auto-generated constructor stub
		this.ID=ID;
		this.upperId=upperId;
		this.content=content;
		this.creatTime=new Timestamp(System.currentTimeMillis()); 
	}
	public void setID(int ID){
	this.ID=ID;
	}
	public int getID(){
		return ID;
	}
	public void setUpperId(int upperId){
	this.upperId=upperId;
	}
	public int getUpperId(){
		return upperId;
	}
	public void setContent(String content){
	this.content=content;
	}
	public String getContent(){
		return content;
	}
	public void setCreatTime(Timestamp creatTime){
	this.creatTime=creatTime;
	}
	public Timestamp getCreatTime(){
		return creatTime;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder str=new StringBuilder();
		str.append("ID="+this.ID);
		str.append("upperID="+this.upperId);
		str.append("content="+this.content);
		return str.toString();
	}
}

