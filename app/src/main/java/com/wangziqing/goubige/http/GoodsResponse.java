package com.wangziqing.goubige.http;

import com.wangziqing.goubige.model.Good;

import org.xutils.http.annotation.HttpResponse;

import java.util.List;

/**
 * Created by wyouflf on 15/11/5.
 * json 返回值示例, 如果它作为Callback的泛型,
 * 那么xUtils将自动调用JsonResponseParser将字符串转换为BaiduResponse.
 *
 * @HttpResponse 注解 和 ResponseParser接口仅适合做json, xml等文本类型数据的解析,
 * 如果需要其他类型的解析可参考:
 * {@link org.xutils.http.loader.LoaderFactory}
 * 和 {@link org.xutils.common.Callback.PrepareCallback}.
 * LoaderFactory提供PrepareCallback第一个泛型参数类型的自动转换,
 * 第二个泛型参数需要在prepare方法中实现.
 * (LoaderFactory中已经默认提供了部分常用类型的转换实现, 其他类型需要自己注册.)
 */
//表示自动调用parser将结果转换为次response
@HttpResponse(parser = GoodsResponseParser.class)
public class GoodsResponse {

    public List<Good> getData() {
        return data;
    }

    public void setData(List<Good> data) {
        this.data = data;
    }

    // some properties
    private List<Good> data;
    private int pageSize;
    private int pageNum;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getBlockID() {
        return blockID;
    }

    public void setBlockID(int blockID) {
        this.blockID = blockID;
    }

    public int getTagID() {
        return tagID;
    }

    public void setTagID(int tagID) {
        this.tagID = tagID;
    }

    private int blockID;
    private int tagID;
}
