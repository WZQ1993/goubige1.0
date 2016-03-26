package com.wangziqing.goubige.ui;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.eventbus.Subscribe;
import com.wangziqing.goubige.AgendaFragment;
import com.wangziqing.goubige.FragmentAdapter;
import com.wangziqing.goubige.InfoDetailsFragment;
import com.wangziqing.goubige.R;
import com.wangziqing.goubige.model.LoginEvent;
import com.wangziqing.goubige.utils.EventBusFactory;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个中文版Demo App搞定所有Android的Support Library新增所有兼容控件
 * 支持最新2015 Google I/O大会Android Design Support Library
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    //将ToolBar与TabLayout结合放入AppBarLayout
    @ViewInject(R.id.tool_bar)
    private Toolbar mToolbar;
    //DrawerLayout中的左侧菜单控件
    @ViewInject(R.id.navigation_view)
    private NavigationView mNavigationView;
    //DrawerLayout控件
    @ViewInject(R.id.drawer_layout)
    private DrawerLayout mDrawerLayout;
    //v4中的ViewPager控件
    @ViewInject(R.id.view_pager)
    private ViewPager mViewPager;
    private View headerView;
    private ImageView userImage;

    private GoodsListFragment goodsListFragment;
    private BigSortsListFragment bigSortsListFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件及布局
        initView();
    }

    private void initView() {
        //MainActivity的布局文件中的主要控件初始化
        mToolbar = (Toolbar) this.findViewById(R.id.tool_bar);
        mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) this.findViewById(R.id.navigation_view);
        headerView=mNavigationView.getHeaderView(0);
        mViewPager = (ViewPager) this.findViewById(R.id.view_pager);
        userImage=(ImageView)headerView.findViewById(R.id.header_userimg);
        //初始化ToolBar
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.all);

        //对NavigationView添加item的监听事件
        mNavigationView.setNavigationItemSelectedListener(naviListener);
        //开启应用默认打开DrawerLayout
        //mDrawerLayout.openDrawer(mNavigationView);
        //初始化Fragment
        goodsListFragment =new GoodsListFragment();
        bigSortsListFragment=new BigSortsListFragment();
        //初始化TabLayout的title数据集
        List<String> titles = new ArrayList<>();
        titles.add("首页");
        titles.add("分类");
        titles.add("发现");
        //初始化ViewPager的数据集
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(goodsListFragment);
        fragments.add(bigSortsListFragment);
        fragments.add(new InfoDetailsFragment());
        //创建ViewPager的adapter
        //对应的fragment和title，viewpaper依据setCurrentItem(0);切换页面
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(adapter);
        //个人头像点击事件
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBusFactory.getHttpEventBus().post(new LoginEvent());
            }
        });
    }

    private NavigationView.OnNavigationItemSelectedListener naviListener =
            new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            //点击NavigationView中定义的menu item时触发反应
            switch (menuItem.getItemId()) {
                case R.id.menu_info_details:
                    mViewPager.setCurrentItem(0);
                    break;
                case R.id.menu_share:
                    mViewPager.setCurrentItem(1);
                    break;
                case R.id.menu_agenda:
                    mViewPager.setCurrentItem(2);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_info_details:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.menu_share:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.menu_agenda:
                mViewPager.setCurrentItem(2);
                break;
            case android.R.id.home:
                //主界面左上角的icon点击反应
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Subscribe
    private void LoginEvent(LoginEvent loginEvent) {
        this.startActivity(new Intent(this,LoginActivity.class));
    }
    public static void start(Activity activity) {
        //从Activity启动MainActivity
        activity.startActivity(new Intent(activity, MainActivity.class));
    }
}
