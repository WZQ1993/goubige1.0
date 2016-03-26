package com.wangziqing.goubige.service;


import android.util.Log;
import android.widget.Toast;

import com.wangziqing.goubige.http.GoodsParams;
import com.wangziqing.goubige.http.GoodsResponse;
import com.wangziqing.goubige.model.InitGoodsEvent;
import com.wangziqing.goubige.model.LoadedMoreEvent;
import com.wangziqing.goubige.utils.EventBusFactory;

import org.xutils.common.Callback;

import org.xutils.ex.HttpException;
import org.xutils.x;

/**
 * Created by WZQ_PC on 2016/2/17 0017.
 */
public class GoodService extends BaseService{
    private static String TAG = "GoodService";
    private static String INIT_EVENT = "init";
    private static String REFRESH_EVENT = "refresh";
    private static GoodsParams initgoodsParams;

    public GoodService() {
        initData();
    }
    private void initData(){
        initgoodsParams = new GoodsParams().pageNum(1).pageSize(10);
    }
    /*同步获取商品信息*/
    public GoodsResponse getGoodsSync(GoodsParams goodsParams) {
        GoodsResponse goodsResponse = null;
        // 默认缓存存活时间, 单位:毫秒.(如果服务没有返回有效的max-age或Expires)
        goodsParams.setCacheMaxAge(1000 * 60);
        try {
            goodsResponse = x.http().getSync(goodsParams, GoodsResponse.class);
        } catch (Throwable e) {
            Log.v(TAG, "message" + e.getMessage());
        }
        return goodsResponse;
    }

    //异步获取信息
    /*
    @param event标识事件类型
    0为加载更多
     */
    public void getGoods(final GoodsParams goodsParams, final int event) {
        //此处目测由于xutils框架内部对params做了缓存，所以需要新建一个
        GoodsParams newGoodsParam=GoodsParams.copyFrom(goodsParams);
        Callback.Cancelable cancelable
                // 使用CacheCallback, xUtils将为该请求缓存数据.
                = x.http().get(newGoodsParam, new Callback.CommonCallback<GoodsResponse>() {
            private boolean hasError = false;
            @Override
            public void onSuccess(GoodsResponse goodsResponse) {
                // 注意: 如果服务返回304或 onCache 选择了信任缓存, 这里将不会被调用,
                // 但是 onFinished 总会被调用.
                switch (event) {
                    case 0:
                        EventBusFactory.getHttpEventBus().post(new LoadedMoreEvent().goodsResponse(goodsResponse));
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                hasError = true;
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
                if (ex instanceof HttpException) { // 网络错误
                    HttpException httpEx = (HttpException) ex;
                    int responseCode = httpEx.getCode();
                    String responseMsg = httpEx.getMessage();
                    String errorResult = httpEx.getResult();
                    // ...
                } else { // 其他错误
                    // ...
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {
                hasError = true;
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }
            /**
             * 总会被调用，不管成功与否，都必须post事件
             */
            @Override
            public void onFinished() {
                if (hasError) {
                    switch (event) {
                        case 0:
                            EventBusFactory.getHttpEventBus().post(new LoadedMoreEvent().isSuccess(false));
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    public void initGoodsData(final int event) {
        Callback.Cancelable cancelable
                // 使用CacheCallback, xUtils将为该请求缓存数据.
                = x.http().get(initgoodsParams, new Callback.CommonCallback<GoodsResponse>() {

            private boolean hasError = false;
            private String result = null;

            @Override
            public void onSuccess(GoodsResponse goodsResponse) {
                // 注意: 如果服务返回304或 onCache 选择了信任缓存, 这里将不会被调用,
                // 但是 onFinished 总会被调用.
                //使用事件总线发布事件
                switch (event) {
                    case 0:
                        EventBusFactory.getHttpEventBus().post(new InitGoodsEvent(goodsResponse).type(INIT_EVENT));
                        break;
                    case 1:
                        EventBusFactory.getHttpEventBus().post(new InitGoodsEvent(goodsResponse).type(REFRESH_EVENT));
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                hasError = true;
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
                if (ex instanceof HttpException) { // 网络错误
                    HttpException httpEx = (HttpException) ex;
                    int responseCode = httpEx.getCode();
                    String responseMsg = httpEx.getMessage();
                    String errorResult = httpEx.getResult();
                    // ...
                } else { // 其他错误
                    // ...
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {
                if (!hasError && result != null) {
                    // 成功获取数据
                }
            }
        });
    }
}
