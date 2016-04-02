package com.wangziqing.goubige.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.google.common.collect.Maps;
import com.wangziqing.goubige.http.HttpUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Created by WZQ_PC on 2016/3/6 0006.
 */
public class FilesUtils {
    private static final String TAG="FilesUtils";
    private static final String IMAGEDIR=MyData.getDATAPATH()+"/image";
    private FilesUtils(){
        creatDir(IMAGEDIR);
        EventBusFactory.getHttpEventBus().register(this);
    }

    public static FilesUtils getFileutils() {
        if(null==fileutils){
            fileutils=new FilesUtils();
        }
        return fileutils;
    }

    private static FilesUtils fileutils;

    public <T> Callback.Cancelable uploadFile
            (RequestParams requestParams,final Callback.CommonCallback<T> callback) {
        return HttpUtils.doPost(requestParams,callback);
    }
    public File getImage(int ID){
        File file=new File(IMAGEDIR,"/"+ID+".png");
        Log.d(TAG,file.getPath());
        if(file.exists())return file;
        else return null;
    }
    public File saveImage(Bitmap bitmap,String imageName){
        File imgFile=creatFile(imageName,IMAGEDIR);
        Log.d(TAG,"保存到："+IMAGEDIR);
        FileOutputStream Fout=null;
        try{
            Fout=new FileOutputStream(imgFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, Fout);
        }catch (Exception e){
            Log.d(TAG,e.getMessage()+"保存图片出错");
        }finally{
            try{
            Fout.flush();;
            Fout.close();
            } catch (IOException e) {
                Log.d(TAG,e.getMessage()+"保存图片出错");
            }
        }
        SharedPerferencesUtil.getInstance().setLocalheader(imgFile.getPath());
        return imgFile;
    }
    private static File creatDir(String dir){
        File f=new File(dir);
        if(!f.exists()){
            f.mkdir();
        }
        return f;
    }
    private static File creatFile(String filename,String dir){
        File f=new File(dir,filename);
        try{
            if(!f.exists()){
                f.createNewFile();
            }
        }catch (IOException io){
            Log.d(TAG,"创建文件失败");
        }
        return f;
    }
}
