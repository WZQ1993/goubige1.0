package com.wangziqing.goubige.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.common.eventbus.Subscribe;
import com.wangziqing.goubige.R;
import com.wangziqing.goubige.model.GoToMainEvent;
import com.wangziqing.goubige.model.InitNavigationViewDataEvent;
import com.wangziqing.goubige.model.UpdateUserStartEvent;
import com.wangziqing.goubige.model.Users;
import com.wangziqing.goubige.service.ServiceFactory;
import com.wangziqing.goubige.service.UsersService;
import com.wangziqing.goubige.utils.EventBusFactory;
import com.wangziqing.goubige.utils.MD5Utils;
import com.wangziqing.goubige.utils.RegexUtils;
import com.wangziqing.goubige.utils.ToastUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by WZQ_PC on 2016/3/1 0001.
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity{
    private static final String TAG="LoginActivity";
    @ViewInject(R.id.login_register)
    private Button register;
    @ViewInject(R.id.login_user_name)
    private EditText phoneOrEmail;
    @ViewInject(R.id.login_user_pass)
    private EditText password;
    private String phoneOrEmailStr;
    private String passwordStr;
    private boolean isPhone=true;
    private UsersService usersService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }
    private void initData(){
        usersService= ServiceFactory.getUsersService();
    }
    @Event(value = R.id.login_register,type = View.OnClickListener.class)
    private void registerAction(View view){
        Log.d(TAG,"点击注册");
        if (isUsable()){
            isPhone=RegexUtils.isPhone(phoneOrEmailStr);
            Users user=new Users();
            if(isPhone)
                user.setTelePhone(phoneOrEmailStr);
            else user.setEmail(phoneOrEmailStr);
            user.setPassWord(MD5Utils.GetMD5Code(passwordStr));
            usersService.register(user);
        }
    }
    @Event(value = R.id.login_login,type = View.OnClickListener.class)
    private void loginAction(View view){
        Log.d(TAG,"点击登录");
        if (isUsable()){
            isPhone=RegexUtils.isPhone(phoneOrEmailStr);
            Users user=new Users();
            if(isPhone)
                user.setTelePhone(phoneOrEmailStr);
            else user.setEmail(phoneOrEmailStr);
            user.setPassWord(MD5Utils.GetMD5Code(passwordStr));
            usersService.login(user);
        }
    }
    @Subscribe
    public void RegisterEvent(UpdateUserStartEvent updateUserStartEvent) {
        Log.d(TAG,"接收到注册成功事件");
        Intent intent=new Intent(this,RegsterAvtivity.class);
        intent.putExtra("user", updateUserStartEvent.user);
        this.startActivity(intent);
    }
    @Subscribe
    private void GoToMainEvent(GoToMainEvent event){
        EventBusFactory.getHttpEventBus().post(new InitNavigationViewDataEvent().user(event.user));
        this.startActivity(new Intent(this,MainActivity.class));
    }
    private boolean isUsable(){
        phoneOrEmailStr=phoneOrEmail.getText().toString().trim();
        passwordStr=password.getText().toString().trim();
        if((RegexUtils.isPhone(phoneOrEmailStr)||RegexUtils.isEmail(phoneOrEmailStr))
                &&RegexUtils.isPassword(passwordStr)){
            return true;
        }
        ToastUtils.showToast("账户名或密码格式错误");
        return false;
    }


}
