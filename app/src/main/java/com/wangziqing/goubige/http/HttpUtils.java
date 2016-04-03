package com.wangziqing.goubige.http;


import android.util.Log;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by WZQ_PC on 2016/2/26 0026.
 */
public class HttpUtils {
    private static final String TAG = "HttpUtils";

    private HttpUtils() {

    }

    public static <T> Callback.Cancelable doGet(RequestParams requestParams, final Callback.CommonCallback<T> callback) {
        Callback.Cancelable cancelable
                = x.http().get(requestParams, callback);
        return cancelable;
    }
    public static <T> Callback.Cancelable doPost(RequestParams requestParams, final Callback.CommonCallback<T> callback) {
        Callback.Cancelable cancelable
                = x.http().post(requestParams, callback);
        return cancelable;
    }
}
