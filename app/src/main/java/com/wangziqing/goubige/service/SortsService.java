package com.wangziqing.goubige.service;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.wangziqing.goubige.http.HttpUtils;
import com.wangziqing.goubige.http.SortsParams;
import com.wangziqing.goubige.model.InitBigSortsEvent;
import com.wangziqing.goubige.utils.EventBusFactory;

import org.xutils.common.Callback;

/**
 * Created by WZQ_PC on 2016/2/26 0026.
 */
public class SortsService  extends BaseService{
    private static final String TAG="SortsSevice";
    private SortsParams bigSortsParam;
    private Callback.CommonCallback bigCallback=new Callback.CommonCallback<String>(){
        @Override
        public void onSuccess(String result) {
            Log.d(TAG,result);
            InitBigSortsEvent initBigSortsEvent= JSON.parseObject(result,InitBigSortsEvent.class);
            EventBusFactory.getHttpEventBus().post(initBigSortsEvent);
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {

        }

        @Override
        public void onCancelled(CancelledException cex) {

        }

        @Override
        public void onFinished() {

        }
    };
    public SortsService(){
        initData();
    }
    private void initData(){
        bigSortsParam=new SortsParams();
    }
    public void getBigSorts(){
        HttpUtils.doGet(bigSortsParam,bigCallback);
    }
}
