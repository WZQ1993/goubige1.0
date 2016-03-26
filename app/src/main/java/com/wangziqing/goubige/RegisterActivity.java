//package com.wangziqing.goubige;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import com.running.together.R;
//
//import com.google.gson.Gson;
//
//import com.running.together.AppConstant.AppConstant;
//import com.running.together.app.MyApplication;
//import com.running.together.module.UserInfo;
//import com.running.together.util.HttpCallback;
//import com.running.together.util.MyDialog;
//import com.running.together.util.MyToast;
//import com.running.together.util.SharedPerferencesUtil;
//import com.running.together.util.HttpCallback.MyCallback;
//import com.running.together.util.UploadUtil;
//
//import android.app.Dialog;
//import android.content.Intent;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//public class RegisterActivity extends BaseActivity implements MyCallback{
//	private ImageView header;
//	private EditText nackname;
//	private EditText sex;
//	private EditText age;
//	private EditText school;
//	private EditText hobby;
//	private EditText personal;
//	private TextView tiaoguo;
//	private Button submit;
//	private final int RESULT_CODE = 0X111;
//	private MyApplication myApplication;
//	private SharedPerferencesUtil spUtil;
//	private Dialog myDialog;
//	//注册用户成功传输过来的用户名
//	private String username;
//	private String picturePath;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.activity_register);
//		// 隐藏弹出来的输入法
//		getWindow().setSoftInputMode(
//				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
//						| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//		initView();
//		initData();
//	}
//
//	private void initData() {
//		myApplication = MyApplication.getInstance();
//		spUtil = myApplication.getSpUtil();
//		String header_url = spUtil.getLocalHeader();
//		if (null != (header_url)) {
//			header.setImageBitmap(BitmapFactory.decodeFile(header_url));
//		}
//		//或取注册用户成功传输过来的用户名
//		Intent intent=getIntent();
//		username=intent.getStringExtra("username");
//		Log.d("Register", "username:"+username);
//	}
//
//	private void initView() {
//		header = (ImageView) findViewById(R.id.uploadHead);
//
//		nackname = (EditText) findViewById(R.id.et_nickname);
//		age = (EditText) findViewById(R.id.et_age);
//		sex = (EditText) findViewById(R.id.et_sex);
//		school = (EditText) findViewById(R.id.et_school);
//		hobby = (EditText) findViewById(R.id.et_hobby);
//		personal=(EditText)findViewById(R.id.signature);
//		tiaoguo=(TextView)findViewById(R.id.re_tiaoguo);
//		submit = (Button) findViewById(R.id.register_sumbit);
//		submit.setOnClickListener(new SubmitListener());
//		header.setOnClickListener(new SubmitListener());
//		tiaoguo.setOnClickListener(new SubmitListener());
//	}
//
//	private class SubmitListener implements OnClickListener {
//
//		@Override
//		public void onClick(View v) {
//			switch (v.getId()) {
//			case R.id.uploadHead:
//				// 上传头像信息
//				uploadHeader();
//				break;
//			case R.id.register_sumbit:
//				// 提交注册信息
//				submit_info();
//				break;
//			case R.id.re_tiaoguo:
//				// 跳过完善信息
//				Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
//				intent.putExtra("username", username);
//				startActivity(intent);
//				finish();
//				break;
//			}
//		}
//
//	}
//
//	private void submit_info() {
//		String userNickName = nackname.getText().toString();
//		String userSex = sex.getText().toString();
//		String userAge = age.getText().toString();
//		String userSchool = school.getText().toString();
//		String userHobby = hobby.getText().toString();
//		String userPersonal = personal.getText().toString();
//		UserInfo info = new UserInfo(username,userNickName,userSex,userAge,userSchool,userHobby,userPersonal);
//		Gson gson = new Gson();
//		String register_info = gson.toJson(info);
//		//提交注册数据到服务器
//		new HttpCallback(AppConstant.REQUEST_URL, this, register_info).execute();
//		myDialog = MyDialog.LoginDialog(this, null);
//		myDialog.show();
//	}
//
//	private void uploadHeader() {
//		// 调用系统相册
//		Intent intent = new Intent(Intent.ACTION_PICK,
//				MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//		intent.setType("image/*");
//		startActivityForResult(intent, RESULT_CODE);
//	}
//
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		if (requestCode == RESULT_CODE && resultCode == RESULT_OK
//				&& null != data) {
//			Uri selectedImage = data.getData();
//			String[] filePathColumn = { MediaStore.Images.Media.DATA };
//
//			Cursor cursor = getContentResolver().query(selectedImage,
//					filePathColumn, null, null, null);
//			cursor.moveToFirst();
//
//			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//			picturePath = cursor.getString(columnIndex);
//			cursor.close();
//			Bitmap imageBitmap=compressImage(BitmapFactory.decodeFile(picturePath));
//			header.setImageBitmap(imageBitmap);
//			spUtil.setLocalHeader(picturePath);
//			//把图片上传到服务器
//			Log.d("uploadfile", picturePath);
//			new Thread(new Runnable() {
//				File image=new File(picturePath);
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					Log.d("regiester", username);
//					UploadUtil.uploadFile(image, AppConstant.UPLOAD+"?"+"username="+username);
//				}
//			}).start();
//		}
//	}
////	提交数据到服务器的回调接口
//	@Override
//	public void callback(String msg) {
//		if("400".equals(msg)){
//			MyToast.showShort(this, "提交数据失败！");
//		}else if("500".equals(msg)){
//			MyToast.showShort(this, "请求出现异常！");
//		}else{
//			myDialog.dismiss();
//			MyDialog.alertDialog(this, msg);
//			Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
//			intent.putExtra("username", username);
//			startActivity(intent);
//			finish();
//		}
//	}
//	//图片压缩
//	private Bitmap compressImage(Bitmap image) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
//        int options = 100;
//        while ( baos.toByteArray().length / 1024>100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
//            baos.reset();//重置baos即清空baos
//            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
//            options -= 10;//每次都减少10
//        }
//        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
//        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
//        return bitmap;
//    }
//
//}
