package com.wangziqing.goubige.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.common.eventbus.Subscribe;
import com.wangziqing.goubige.FragmentAdapter;
import com.wangziqing.goubige.R;
import com.wangziqing.goubige.RecyclerViewAdapter;
import com.wangziqing.goubige.model.EventWithUser;
import com.wangziqing.goubige.model.InitNavigationViewDataEvent;
import com.wangziqing.goubige.model.Users;
import com.wangziqing.goubige.service.ServiceFactory;
import com.wangziqing.goubige.service.UsersService;
import com.wangziqing.goubige.utils.CricleImageView;
import com.wangziqing.goubige.utils.FilesUtils;
import com.wangziqing.goubige.utils.MyImageOptionsFactory;
import com.wangziqing.goubige.utils.SharedPerferencesUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 一个中文版Demo App搞定所有Android的Support Library新增所有兼容控件
 * 支持最新2015 Google I/O大会Android Design Support Library
 */
@ContentView(R.layout.activity_share)
public class ShareActivity extends BaseActivity {
    private static final String TAG="MainActivity";
    private static Users user;
    //将ToolBar与TabLayout结合放入AppBarLayout
    @ViewInject(R.id.tool_bar)
    private Toolbar mToolbar;
    //DrawerLayout中的左侧菜单控件
    @ViewInject(R.id.navigation_view)
    private NavigationView mNavigationView;
    //DrawerLayout控件
    @ViewInject(R.id.drawer_layout)
    private DrawerLayout mDrawerLayout;
    @ViewInject(R.id.recycler_view)
    private RecyclerView mRecyclerView;
    private View headerView;
    private CricleImageView header_userimg;
    private TextView header_username;

    private UsersService usersService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件及布局
        initData();
        initView();
    }
    private void initData(){
        usersService= ServiceFactory.getUsersService();
    }

    private void initView() {
        //MainActivity的布局文件中的主要控件初始化
        mToolbar = (Toolbar) this.findViewById(R.id.tool_bar);
        mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) this.findViewById(R.id.navigation_view);
        headerView=mNavigationView.getHeaderView(0);
        header_userimg=(CricleImageView)headerView.findViewById(R.id.header_userimg);
        header_username=(TextView)headerView.findViewById(R.id.header_username);
        //初始化ToolBar
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.all);
        //对NavigationView添加item的监听事件
        mNavigationView.setNavigationItemSelectedListener(naviListener);
        if(SharedPerferencesUtil.getInstance().getIsLogined()){
            //已登陆
            //个人数据加载
            user= JSON.parseObject(
                    SharedPerferencesUtil.getInstance().getUserJson(),
                    Users.class
            );
            Log.d(TAG,user.toString());
            initNavigationViewData(user);

            header_userimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //获取当前的用户ID，发送请求获取最新资料
                    usersService.getUserDetailsByID(user.getID());
                }
            });
        }
        else
            header_userimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(x.app(), LoginActivity.class));
                }
            });
        //列表
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setAdapter(new RecyclerViewAdapter(this));
    }
    //左侧侧边栏
    private NavigationView.OnNavigationItemSelectedListener naviListener =
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    //点击NavigationView中定义的menu item时触发反应
                    switch (menuItem.getItemId()) {
                        case R.id.menu_main:
                            startActivity(new Intent(x.app(),MainActivity.class));
                            break;
                        case R.id.menu_share:
                            break;
                        case R.id.menu_talentUser:
                            startActivity(new Intent(x.app(),StartUserActivity.class));
                            break;
                    }
                    //关闭DrawerLayout回到主界面选中的tab的fragment页
                    mDrawerLayout.closeDrawer(mNavigationView);
                    return false;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //主界面右上角的menu菜单
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    //右侧设置菜单
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main:
                startActivity(new Intent(x.app(),MainActivity.class));
                break;
            case R.id.menu_share:
                break;
            case R.id.menu_talentUser:
                startActivity(new Intent(x.app(),StartUserActivity.class));
                break;
            case android.R.id.home:
                //主界面左上角的icon点击反应
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //侧边栏个人信息
    private void initNavigationViewData(Users user){
        File userimg=FilesUtils.getFileutils().getImage(user.getID());
        if(null==userimg){
            //从网络加载
            x.image().bind(header_userimg,user.getUserImg(), MyImageOptionsFactory.getHeaderImageOptions());
        }else{
            //从本地加载
            x.image().bind(header_userimg,userimg.getPath(), MyImageOptionsFactory.getHeaderImageOptions());
        }
        header_username.setText(user.getUserName());
    }
    @Subscribe
    private void initNavigationViewEvent(InitNavigationViewDataEvent event){
        //缓存当前登录的用户
        SharedPerferencesUtil.getInstance().setUserJson(JSON.toJSONString(event.user));
        SharedPerferencesUtil.getInstance().setIsLogined(true);
        user=event.user;
        initNavigationViewData(event.user);
    }
    @Subscribe
    private void EventWithUser(EventWithUser event){
        switch (event.method){
            case "showUserDetailEvent":
                //缓存当前登录的用户
                SharedPerferencesUtil.getInstance().setUserJson(JSON.toJSONString(event.user));
                SharedPerferencesUtil.getInstance().setIsLogined(true);
                user=event.user;
                Intent intent=new Intent(this,UserDetailActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
                break;
        }

    }
    public static void start(Activity activity) {
        //从Activity启动MainActivity
        activity.startActivity(new Intent(activity, MainActivity.class));
    }
}