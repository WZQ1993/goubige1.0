package com.wangziqing.goubige.service;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wangziqing.goubige.http.HttpUtils;
import com.wangziqing.goubige.http.RequestParamsFactory;
import com.wangziqing.goubige.http.UserUpdateParams;
import com.wangziqing.goubige.http.UsersRegisterParams;
import com.wangziqing.goubige.model.StartRegisterEvent;
import com.wangziqing.goubige.model.Users;
import com.wangziqing.goubige.utils.EventBusFactory;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;

/**
 * Created by WZQ_PC on 2016/3/6 0006.
 */
public class UsersService extends BaseService {
    private final static String TAG = "UsersService";
    private UsersRegisterParams params = new UsersRegisterParams();
    private Callback.CommonCallback registerCallBack = new Callback.CommonCallback<String>() {
        @Override
        public void onSuccess(String result) {
            Log.d(TAG, result);
            //更多信息设置事件
            JSONObject jsonObject = JSON.parseObject(result);
            Users user = JSON.parseObject(jsonObject.get("users").toString(),
                    Users.class);
            EventBusFactory.getHttpEventBus().post(new StartRegisterEvent().user(user));
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            if (ex instanceof HttpException) {
                HttpException httpException = (HttpException) ex;
                int code = httpException.getCode();
                String message = httpException.getMessage();
                String result = httpException.getResult();
                Log.d(TAG, "Code=" + code);
                Log.d(TAG, "message=" + message);
                Log.d(TAG, "result=" + result);
            } else {

            }
        }

        @Override
        public void onCancelled(CancelledException cex) {

        }

        @Override
        public void onFinished() {

        }
    };
    private Callback.CommonCallback updateCallBack = new Callback.CommonCallback<String>() {
        @Override
        public void onSuccess(String result) {
            Log.d(TAG, result);
//            Users users =JSON.parseObject(result,Users.class);
            //将其存进本地数据库-转到首页
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            if (ex instanceof HttpException) {
                HttpException httpException = (HttpException) ex;
                int code = httpException.getCode();
                String message = httpException.getMessage();
                String result = httpException.getResult();
                Log.d(TAG, "Code=" + code);
                Log.d(TAG, "message=" + message);
                Log.d(TAG, "result=" + result);
            } else {

            }
        }

        @Override
        public void onCancelled(CancelledException cex) {

        }

        @Override
        public void onFinished() {

        }
    };
    public void register(Users user) {
        Log.d(TAG, "用户ID=" + user.getID());
        params.user(user);
        HttpUtils.doPost(params, registerCallBack);
    }
    public void update(Users user) {
        Log.d(TAG, "用户ID=" + user.getID());
        UserUpdateParams params=RequestParamsFactory.getUserUpdateParams(user.getID());
        params.user(user);
        HttpUtils.doPost(params, updateCallBack);
    }
}
