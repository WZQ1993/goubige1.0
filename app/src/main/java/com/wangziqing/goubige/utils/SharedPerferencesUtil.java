package com.wangziqing.goubige.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.xutils.x;

public class SharedPerferencesUtil {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private static final String LOCALHEADER = "LOCALHEADER";
    private static SharedPerferencesUtil sharedPerferencesUtil;

    private SharedPerferencesUtil(Context context, String fileName) {
        sp = context.getSharedPreferences(fileName, context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public static SharedPerferencesUtil getInstance() {
        if (null == sharedPerferencesUtil)
            sharedPerferencesUtil = new SharedPerferencesUtil(x.app(), String.valueOf("goubige"));
        return sharedPerferencesUtil;
    }

    //示例
//	public void setFriendID(String FID){
//		editor.putString("FID", FID);
//		editor.commit();
//	}
//	public String getFriendID(){
//		return sp.getString("FID", null);
//	}
    public void setUserJson(String userJson) {
        editor.putString("userJson", userJson);
        editor.commit();
    }

    public String getUserJson() {
        return sp.getString("userJson", null);
    }
    public void setIsLogined(boolean isLogined) {
        editor.putBoolean("isLogined", isLogined);
        editor.commit();
    }

    public boolean getIsLogined() {
        return sp.getBoolean("isLogined", false);
    }

    public void setLocalheader(String FID) {
        editor.putString(LOCALHEADER, FID);
        editor.commit();
    }

    public String getLocalheader() {
        return sp.getString(LOCALHEADER, null);
    }


}
