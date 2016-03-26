package com.wangziqing.goubige.model;

import java.io.Serializable;
import java.sql.Timestamp;

   /**
    * users 实体类
    * Sun Mar 06 18:56:01 CST 2016 王梓青
    */ 


public class Users implements Serializable{
	private int ID;
	private String userName;
	private String realName;
	private String passWord;
	private String telePhone;
	private String email;
	private byte gender;
	private byte age;
	private String userImg;
	private String info;
	private byte isDelete;
	private Timestamp createdTime;
	private Timestamp updateTime;
	public void setID(int ID){
	this.ID=ID;
	}
	public int getID(){
		return ID;
	}
	public void setUserName(String userName){
	this.userName=userName;
	}
	public String getUserName(){
		return userName;
	}
	public void setRealName(String realName){
	this.realName=realName;
	}
	public String getRealName(){
		return realName;
	}
	public void setPassWord(String passWord){
	this.passWord=passWord;
	}
	public String getPassWord(){
		return passWord;
	}
	public void setTelePhone(String telePhone){
	this.telePhone=telePhone;
	}
	public String getTelePhone(){
		return telePhone;
	}
	public void setEmail(String email){
	this.email=email;
	}
	public String getEmail(){
		return email;
	}
	public void setGender(byte gender){
	this.gender=gender;
	}
	public byte getGender(){
		return gender;
	}
	public void setAge(byte age){
	this.age=age;
	}
	public byte getAge(){
		return age;
	}
	public void setUserImg(String userImg){
	this.userImg=userImg;
	}
	public String getUserImg(){
		return userImg;
	}
	public void setInfo(String info){
	this.info=info;
	}
	public String getInfo(){
		return info;
	}
	public void setIsDelete(byte isDelete){
	this.isDelete=isDelete;
	}
	public byte getIsDelete(){
		return isDelete;
	}
	public void setCreatedTime(Timestamp createdTime){
	this.createdTime=createdTime;
	}
	public Timestamp getCreatedTime(){
		return createdTime;
	}
	public void setUpdateTime(Timestamp updateTime){
	this.updateTime=updateTime;
	}
	public Timestamp getUpdateTime(){
		return updateTime;
	}

	   @Override
	   public String toString() {
		   return "Users{" +
				   "ID=" + ID +
				   ", userName='" + userName + '\'' +
				   ", realName='" + realName + '\'' +
				   ", passWord='" + passWord + '\'' +
				   ", telePhone='" + telePhone + '\'' +
				   ", email='" + email + '\'' +
				   ", gender=" + gender +
				   ", age=" + age +
				   ", userImg='" + userImg + '\'' +
				   ", info='" + info + '\'' +
				   ", isDelete=" + isDelete +
				   ", createdTime=" + createdTime +
				   ", updateTime=" + updateTime +
				   '}';
	   }
   }

