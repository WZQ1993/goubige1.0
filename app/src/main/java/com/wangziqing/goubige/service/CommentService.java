package com.wangziqing.goubige.service;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wangziqing.goubige.http.HttpUtils;
import com.wangziqing.goubige.http.RequestParamsFactory;
import com.wangziqing.goubige.model.CommentEvent;
import com.wangziqing.goubige.model.Page;
import com.wangziqing.goubige.model.ShareEvent;
import com.wangziqing.goubige.utils.EventBusFactory;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;

/**
 * Created by WZQ_PC on 2016/5/16 0016.
 */
public class CommentService extends BaseService {
    private final static String TAG = "CommentService";
    private Callback.CommonCallback BaseCallback = new Callback.CommonCallback<String>() {
        @Override
        public void onSuccess(String result) {
            Log.d(TAG, result);
            JSONObject jsonObject = JSON.parseObject(result);
            String tag = jsonObject.getString("tag");
            switch (tag) {
                //在服务器写入
                case "comments_refresh":
                    EventBusFactory.getHttpEventBus().post(JSON.parseObject(result, CommentEvent.class));
                    break;
                case "comments_loadMore":
                    EventBusFactory.getHttpEventBus().post(JSON.parseObject(result, CommentEvent.class));
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
    public void getShareByID(int ID){

    }
    public void getShareByPage(Page page, String tag,int shareID){
        RequestParams params= RequestParamsFactory.getCommentByPage(page,tag,shareID);
        HttpUtils.doGet(params,BaseCallback);
    }
}

