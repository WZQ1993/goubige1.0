package com.wangziqing.goubige.service;

import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wangziqing.goubige.http.HttpUtils;
import com.wangziqing.goubige.http.RequestParamsFactory;
import com.wangziqing.goubige.http.UploadHeaderParams;
import com.wangziqing.goubige.http.UserUpdateParams;
import com.wangziqing.goubige.http.UsersLoginParams;
import com.wangziqing.goubige.http.UsersRegisterParams;
import com.wangziqing.goubige.model.UpdateUserStartEvent;
import com.wangziqing.goubige.model.GoToMainEvent;
import com.wangziqing.goubige.model.Users;
import com.wangziqing.goubige.utils.EventBusFactory;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.x;

/**
 * Created by WZQ_PC on 2016/3/6 0006.
 */
public class UsersService extends BaseService {
    private final static String TAG = "UsersService";
    private Callback.CommonCallback uploadCallBack = new Callback.CommonCallback<String>() {
        @Override
        public void onSuccess(String result) {
            Log.d(TAG, result);
            Toast.makeText(x.app(), "头像上传成功", Toast.LENGTH_SHORT).show();
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
            Users user = (Users)jsonObject.get("user");
            if (null == user) {
                //已存在
                if (null == jsonObject.get("method"))
                    Toast.makeText(x.app(), "账号已存在，请直接登陆", Toast.LENGTH_SHORT).show();
                else Toast.makeText(x.app(), "账号密码错误", Toast.LENGTH_SHORT).show();
            } else {
                if (null == jsonObject.get("method")){
                    EventBusFactory.getHttpEventBus().post(new UpdateUserStartEvent().user(user));
                }else{
                    EventBusFactory.getHttpEventBus().post(new GoToMainEvent().user(user));
                }
            }
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
            String userJson = jsonObject.get("user").toString();
            Users user = JSON.parseObject(userJson, Users.class);
            //将其存进本地数据库-转到首页
            EventBusFactory.getHttpEventBus().post(new GoToMainEvent().user(user));
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

    public void login(Users user) {
        Log.d(TAG,user.toString());
        UsersLoginParams params= new UsersLoginParams();
        params.user(user);
        params.addParameter("method", "authorizations");
        HttpUtils.doPost(params, registerCallBack);
    }

    public void register(Users user) {
        Log.d(TAG, "用户ID=" + user.getID());
        UsersRegisterParams params= new UsersRegisterParams();
        params.user(user);
        params.addParameter("method", "register");
        HttpUtils.doPost(params, registerCallBack);
    }

    public void update(Users user) {
        Log.d(TAG, "用户ID=" + user.getID());
        UserUpdateParams params = RequestParamsFactory.getUserUpdateParams(user.getID());
        params.user(user);
        HttpUtils.doPost(params, updateCallBack);
    }

    public void uploadHeader(UploadHeaderParams uploadHeaderParams) {
        //success
        HttpUtils.doPost(uploadHeaderParams, uploadCallBack);
    }
}
