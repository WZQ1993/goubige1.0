package com.wangziqing.goubige.utils;

import android.widget.Toast;

import org.xutils.x;

/**
 * Created by WZQ_PC on 2016/3/13 0013.
 */
public class ToastUtils {
    public static void showToast(String str){
        Toast.makeText(x.app(), str, Toast.LENGTH_LONG).show();
    }
}
