package com.wangziqing.goubige.http;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyouflf on 15/11/5.
 */
public class GoodsResponseParser implements ResponseParser {

    @Override
    public void checkResponse(UriRequest request) throws Throwable {
        // custom check ?
        // get headers ?
    }

    /**
     * 转换result为resultType类型的对象
     *
     * @param resultType  返回值类型(可能带有泛型信息)
     * @param resultClass 返回值类型
     * @param result      字符串数据
     * @return
     * @throws Throwable
     */
    @Override
    public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable {
        // TODO: json to java bean
        if (resultClass == List.class) {
            // 这里只是个示例, 不做json转换.
            List<GoodsResponse> list = Lists.newArrayList();
            GoodsResponse goodsResponse = new GoodsResponse();
            return list;
        } else {
            GoodsResponse goodsResponse=transferToGoodsResponse(result);
            goodsResponse=JSON.parseObject(result,GoodsResponse.class);
            return goodsResponse;
        }

    }
    private static GoodsResponse transferToGoodsResponse(String result){
        return JSON.parseObject(result,GoodsResponse.class);
    }
}
