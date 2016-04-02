package com.wangziqing.goubige.service;

import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wangziqing.goubige.http.HttpUtils;
import com.wangziqing.goubige.http.RequestParamsFactory;
import com.wangziqing.goubige.http.UploadHeaderParams;
import com.wangziqing.goubige.http.UserUpdateParams;
import com.wangziqing.goubige.http.UsersRegisterParams;
import com.wangziqing.goubige.model.StartRegisterEvent;
import com.wangziqing.goubige.model.Users;
import com.wangziqing.goubige.utils.EventBusFactory;
import com.wangziqing.goubige.utils.SharedPerferencesUtil;
import com.wangziqing.goubige.utils.UploadHeader;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.x;

import java.io.File;

/**
 * Created by WZQ_PC on 2016/3/6 0006.
 */
public class UsersService extends BaseService {
    private final static String TAG = "UsersService";
    private UsersRegisterParams params = new UsersRegisterParams();
    private Callback.CommonCallback uploadCallBack = new Callback.CommonCallback<String>() {
        @Override
        public void onSuccess(String result) {
            Log.d(TAG, result);
            Toast.makeText(x.app(),"头像上传成功",Toast.LENGTH_SHORT).show();
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
    private Callback.CommonCallback registerCallBack = new Callback.CommonCallback<String>() {
        @Override
        public void onSuccess(String result) {
            Log.d(TAG, result);
            //更多信息设置事件
            JSONObject jsonObject = JSON.parseObject(result);
            Users user = JSON.parseObject(jsonObject.get("user").toString(),
                    Users.class);
            SharedPerferencesUtil.getInstance().setIsLogined(true);
            EventBusFactory.getHttpEventBus().post(new StartRegisterEvent().user(user));
            Log.d(TAG,"提交事件");
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
            JSONObject jsonObject = JSON.parseObject(result);
            String userJson=jsonObject.get("user").toString();
            Users user = JSON.parseObject(userJson,Users.class);
            //将其存进本地数据库-转到首页
            SharedPerferencesUtil.getInstance().setUserJson(userJson);
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
    public void uploadHeader(UploadHeaderParams uploadHeaderParams){
        //success
        HttpUtils.doPost(uploadHeaderParams,uploadCallBack);
    }
}
