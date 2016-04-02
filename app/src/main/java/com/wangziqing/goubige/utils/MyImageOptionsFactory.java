package com.wangziqing.goubige.utils;

import android.widget.ImageView;

import com.wangziqing.goubige.R;

import org.xutils.image.ImageOptions;

/**
 * Created by WZQ_PC on 2016/2/21 0021.
 */
public class MyImageOptionsFactory {
    private MyImageOptionsFactory(){

    }
    private static ImageOptions GoodsImageOptions;
    private static ImageOptions headerImageOptions;
    public static ImageOptions getGoodImageOption(){
        if(GoodsImageOptions ==null){
            GoodsImageOptions =new ImageOptions.Builder()
                    // 加载中或错误图片的ScaleType
                    //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                    // 默认自动适应大小
//                    setSize(...)
                    .setCrop(true) // 很多时候设置了合适的scaleType也不需要它.
                    .setIgnoreGif(false)
                    // 如果使用本地文件url, 添加这个设置可以在本地文件更新后刷新立即生效.
                    //.setUseMemCache(false)
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP).build();
        }
        return GoodsImageOptions;
    }
    public static ImageOptions getHeaderImageOptions(){
        if(headerImageOptions ==null){
            headerImageOptions =new ImageOptions.Builder()
                    // 加载中或错误图片的ScaleType
                    .setPlaceholderScaleType(ImageView.ScaleType.CENTER_CROP)
                    // 默认自动适应大小
//                    .setCrop(true) // 很多时候设置了合适的scaleType也不需要它.
                    .setIgnoreGif(true)
                    .setLoadingDrawableId(R.drawable.icon_people)
                    .setFailureDrawableId(R.drawable.icon_people)
                    //ScaleType CENTER_INSIDE
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP).build();
        }
        return headerImageOptions;
    }
}
