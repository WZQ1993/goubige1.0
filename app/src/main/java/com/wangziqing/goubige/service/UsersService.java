package com.wangziqing.goubige.service;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangziqing.goubige.http.HttpUtils;
import com.wangziqing.goubige.http.RequestParamsFactory;
import com.wangziqing.goubige.http.UploadHeaderParams;
import com.wangziqing.goubige.http.UserUpdateParams;
import com.wangziqing.goubige.http.UsersLoginParams;
import com.wangziqing.goubige.http.UsersRegisterParams;
import com.wangziqing.goubige.model.EventWithUser;
import com.wangziqing.goubige.model.GetUsersEvent;
import com.wangziqing.goubige.model.InitNavigationViewDataEvent;
import com.wangziqing.goubige.model.Page;
import com.wangziqing.goubige.model.UpdateUserStartEvent;
import com.wangziqing.goubige.model.GoToMainEvent;
import com.wangziqing.goubige.model.Users;
import com.wangziqing.goubige.ui.UserDetailActivity;
import com.wangziqing.goubige.utils.EventBusFactory;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

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
            Users user = jsonObject.getObject("user",Users.class);
            String method=jsonObject.getString("method");
            boolean IsSuccess=jsonObject.getBoolean("IsSuccess");
            if (!IsSuccess) {
                //已存在
                if ("register".equals(method))
                    Toast.makeText(x.app(), "账号已存在，请直接登陆", Toast.LENGTH_SHORT).show();
                else Toast.makeText(x.app(), "账号密码错误", Toast.LENGTH_SHORT).show();
            } else {
                if ("register".equals(method)){
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
    private Callback.CommonCallback BaseCallback = new Callback.CommonCallback<String>() {
        @Override
        public void onSuccess(String result) {
            Log.d(TAG, result);
            JSONObject jsonObject = JSON.parseObject(result);
            String method=jsonObject.getString("method");
            switch (method){
                //在服务器写入
                case "getUserDetailsByID":
                    Users user=jsonObject.getObject("user",Users.class);
                    //跳转到信息展示
                    EventBusFactory.getHttpEventBus().post(
                            new EventWithUser()
                                    .user(user)
                                    .method("showUserDetailEvent"));
                    break;
                case "getUsers":
                    EventBusFactory.getHttpEventBus().post(JSON.parseObject(result,GetUsersEvent.class));
                    break;
                default:
                    break;
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
    public void getUserDetailsByID(int ID){
        RequestParams params=RequestParamsFactory.getUserDetailsByIDParams(ID);
        HttpUtils.doGet(params,BaseCallback);
    }

    /**
     * 异步获取用户列表
     * @param page
     */
    public void getUsers(Page page,String tag){
        Log.d(TAG,page.page+"-"+page.pageSize);
        RequestParams params=RequestParamsFactory.getUsersByPage(page,tag);
        HttpUtils.doGet(params,BaseCallback);
    }
}
