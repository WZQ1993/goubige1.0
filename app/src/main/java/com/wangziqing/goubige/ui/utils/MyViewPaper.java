package com.wangziqing.goubige.ui.utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.wangziqing.goubige.R;
import com.wangziqing.goubige.ui.MainActivity;

/**
 * Created by WZQ_PC on 2016/5/11 0011.
 */
public class MyViewPaper extends ViewPager{
    private View tab;
    public MyViewPaper(Context context){
        super(context);
    }
    public MyViewPaper(Context context,AttributeSet attrs){
        super(context, attrs);
    }
    public void setTab(View tab){
        this.tab=tab;
    }
    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
        if(item>=3){
            tab.setVisibility(View.GONE);
        }else{
            tab.setVisibility(View.VISIBLE);
        }
    }
}
