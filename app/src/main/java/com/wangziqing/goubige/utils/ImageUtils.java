package com.wangziqing.goubige.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by WZQ_PC on 2016/3/20 0020.
 */
public class ImageUtils {
    private final static String TAG="ImageUtils";
    //图片压缩
    public static Bitmap compressImage(String imgPath) {
        BitmapFactory.Options op = new BitmapFactory.Options();
        //inJustDecodeBounds
        //If set to true, the decoder will return null (no bitmap), but the out…
        op.inJustDecodeBounds = true;
        Bitmap image = BitmapFactory.decodeFile(imgPath, op); //获取尺寸信息
        //获取比例大小
        int wRatio = (int)Math.ceil(op.outWidth/MyData.getWidth());
        int hRatio = (int)Math.ceil(op.outHeight/MyData.getHeight());
        //如果超出指定大小，则缩小相应的比例
        if(wRatio > 1 && hRatio > 1){
            if(wRatio > hRatio){
                op.inSampleSize = wRatio;
            }else{
                op.inSampleSize = hRatio;
            }
        }
        op.inJustDecodeBounds = false;
        image=BitmapFactory.decodeFile(imgPath, op);
        int quality =100;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, quality, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        while ( baos.toByteArray().length/1024>100&&quality>0) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            Log.d(TAG,"质量"+quality);
            Log.d(TAG, "大小:"+baos.toByteArray().length/1024+"kb");
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, quality, baos);//这里压缩options%，把压缩后的数据存放到baos中
            if(quality>10)quality -= 10;//每次都减少10
            else quality-=2;
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }
}
