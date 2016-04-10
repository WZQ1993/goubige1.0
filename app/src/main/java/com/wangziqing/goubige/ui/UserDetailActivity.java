package com.wangziqing.goubige.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangziqing.goubige.R;
import com.wangziqing.goubige.model.Users;
import com.wangziqing.goubige.utils.CricleImageView;
import com.wangziqing.goubige.utils.FilesUtils;
import com.wangziqing.goubige.utils.MyImageOptionsFactory;
import com.wangziqing.goubige.utils.SharedPerferencesUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

/**
 * Created by WZQ_PC on 2016/3/1 0001.
 */
@ContentView(R.layout.activity_user_detail)
public class UserDetailActivity extends BaseActivity{
    private Users user;
    @ViewInject(R.id.detail_userimg)
    private CricleImageView userImg;
    @ViewInject(R.id.detail_username)
    private TextView userName;
    @ViewInject(R.id.detail_frients)
    private TextView frients;
    @ViewInject(R.id.detail_fans)
    private TextView fans;
    @ViewInject(R.id.detail_myShares)
    private TextView myShares;
    @ViewInject(R.id.detail_myStars)
    private TextView myStars;
    @ViewInject(R.id.detail_myFoots)
    private TextView myFoots;
    @ViewInject(R.id.detail_logout)
    private TextView logout;
    @ViewInject(R.id.detail_collapsing)
    private CollapsingToolbarLayout collapsingToolbarLayout;
    @ViewInject(R.id.detail_toolbar)
    private Toolbar toolbar;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initdata();
        initView();
    }
    private void initdata(){
        Intent intent=getIntent();
        user=(Users)intent.getSerializableExtra("user");
        actionBar = getSupportActionBar();
    }
    private void initView(){
        File image= FilesUtils.getFileutils().getImage(user.getID());
        if(null==image){
            //从网络加载
            x.image().bind(userImg,user.getUserImg(), MyImageOptionsFactory.getHeaderImageOptions());
        }else{
            //从本地加载
            x.image().bind(userImg,image.getPath(), MyImageOptionsFactory.getHeaderImageOptions());
        }
        userName.setText(user.getUserName());
        collapsingToolbarLayout.setTitle(user.getUserName());
        collapsingToolbarLayout.setTitleEnabled(false);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Event(value = R.id.detail_userimg,type= CricleImageView.OnClickListener.class)
    private void showUserDetails(View view){
        Intent intent=new Intent(this,RegsterAvtivity.class);
        intent.putExtra("isFromDetail",true);
        intent.putExtra("user",user);
        startActivity(intent);
    }
    @Event(value =R.id.detail_logout,type = TextView.OnClickListener.class)
    private void logout(View view){
        SharedPerferencesUtil.getInstance().setIsLogined(false);
        startActivity(new Intent(this,MainActivity.class));
    }
}
