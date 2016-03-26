package com.wangziqing.goubige.utils;

import android.content.Context;
import android.os.Environment;
import android.view.WindowManager;

/**
 * Created by WZQ_PC on 2016/2/21 0021.
 */
public class MyData {
    private MyData(){

    }

    public static String getDATAPATH() {
        return DATAPATH;
    }

    public static void setDataPath(Context context){
        DATAPATH= context.getFilesDir().getParentFile().getPath();
    }

    public static String getSDCARDPATH() {
        return SDCARDPATH;
    }

    public static void setSDCARDPATH() {
        MyData.SDCARDPATH = Environment.getExternalStorageDirectory().getParent();
    }

    private static String DATAPATH;
    private static String SDCARDPATH;
    private static WindowManager wm ;
    //获取屏幕宽度
    public static int getWidth(){
        return wm.getDefaultDisplay().getWidth();
    }
    //获取屏幕高度
    public static int getHeight(){
        return wm.getDefaultDisplay().getHeight();
    }

    public static void setWindowManager(Context context){
        wm=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
    }

}
