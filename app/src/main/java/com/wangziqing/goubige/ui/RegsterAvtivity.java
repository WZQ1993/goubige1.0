package com.wangziqing.goubige.ui;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.google.common.eventbus.Subscribe;
import com.wangziqing.goubige.R;
import com.wangziqing.goubige.http.UploadHeaderParams;
import com.wangziqing.goubige.model.InitNavigationViewDataEvent;
import com.wangziqing.goubige.model.GoToMainEvent;
import com.wangziqing.goubige.model.Users;
import com.wangziqing.goubige.service.ServiceFactory;
import com.wangziqing.goubige.service.UsersService;
import com.wangziqing.goubige.utils.EventBusFactory;
import com.wangziqing.goubige.utils.FilesUtils;
import com.wangziqing.goubige.utils.ImageUtils;
import com.wangziqing.goubige.utils.MyImageOptionsFactory;
import com.wangziqing.goubige.utils.ToastUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.lang.reflect.Type;

/**
 * Created by WZQ_PC on 2016/3/7 0007.
 */
@ContentView(R.layout.activity_register)
public class RegsterAvtivity extends BaseActivity {
    private static final String TAG = "RegsterAvtivity";
    private final int RESULT_CODE = 0X111;

    private UsersService usersService;
    @ViewInject(R.id.register_sumbit)
    private Button submit;
    @ViewInject(R.id.register_nickname)
    private EditText register_nickname;
    @ViewInject(R.id.register_realname)
    private EditText register_realname;
    @ViewInject(R.id.register_info)
    private EditText register_info;
    @ViewInject(R.id.register_age)
    private EditText register_age;
    @ViewInject(R.id.register_sex)
    private RadioGroup register_sex;
    @ViewInject(R.id.register_img)
    private ImageView register_img;

    private Users user;
    private boolean isFromDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initdata();
        initview();
    }

    private void initdata() {
        usersService = ServiceFactory.getUsersService();
        Intent intent = getIntent();
        user=(Users) intent.getSerializableExtra("user");
        isFromDetail=intent.getBooleanExtra("isFromDetail",false);
    }
    private void initview(){
        if(isFromDetail){
            File image= FilesUtils.getFileutils().getImage(user.getID());
            if(null==image){
                //从网络加载
                x.image().bind(register_img,user.getUserImg(), MyImageOptionsFactory.getHeaderImageOptions());
            }else{
                //从本地加载
                x.image().bind(register_img,image.getPath(), MyImageOptionsFactory.getHeaderImageOptions());
            }
            register_nickname.setText(user.getUserName());
            register_realname.setText(user.getRealName());
            if(user.getGender()==(byte)0)register_sex.check(R.id.man);
            else register_sex.check(R.id.women);
            register_age.setText(String.valueOf(user.getAge()));
            register_info.setText(user.getInfo());
        }
    }
    @Event(value = R.id.register_sumbit, type = Button.OnClickListener.class)
    private void submit(View view) {
        Log.d(TAG, "修改提交");
        initUser();
        Log.d(TAG,user.toString());
        if(isUseable()){
            usersService.update(user);
        }
    }
    @Event(value = R.id.register_img, type = ImageView.OnClickListener.class)
    private void uploadImage(View view){
        // 调用系统相册
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_CODE);
    }
    /*
    上传头像返回
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String imagePath;
        if (requestCode == RESULT_CODE && resultCode == RESULT_OK
                && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imagePath = cursor.getString(columnIndex);
            cursor.close();
            //获取图片-并缓存
            Bitmap imageBitmap= ImageUtils.
                    compressImage(imagePath);
            register_img.setImageBitmap(imageBitmap);
            //把图片上传到服务器
            UploadHeaderParams params=new UploadHeaderParams();
//            params=isPhone?
//                    params.telePhone(user.getTelePhone()):params.email(user.getEmail());
            params.ID(user.getID());
            File imageFile=FilesUtils.getFileutils().saveImage(imageBitmap,user.getID()+".png");
            params.file(imageFile);
            usersService.uploadHeader(params);
            user.setUserImg(user.getID()+".png");
        }
    }

    private void initUser() {
        user.setUserName(register_nickname.getText().toString());
        user.setRealName(register_realname.getText().toString());
        if(R.id.man==register_sex.getCheckedRadioButtonId()){
            user.setGender((byte)0);
        }else{
            user.setGender((byte)1);
        }
        //年龄限制没处理
        user.setAge(Byte.valueOf(register_age.getText().toString()));
        //头像
        user.setInfo(register_info.getText().toString());
    }

    private boolean isUseable() {
        if(0==user.getAge()||null==user.getUserName()){
            ToastUtils.showToast("请确认输入正确");
            return false;
        }
        return true;
    }
    @Subscribe
    private void GoToMainEvent(GoToMainEvent event){
        EventBusFactory.getHttpEventBus().post(new InitNavigationViewDataEvent().user(event.user));
        this.startActivity(new Intent(this,MainActivity.class));
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        user=null;
    }
}
