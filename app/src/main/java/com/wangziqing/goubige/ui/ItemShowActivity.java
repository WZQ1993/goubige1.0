package com.wangziqing.goubige.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.wangziqing.goubige.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by WZQ_PC on 2016/3/1 0001.
 */
@ContentView(R.layout.activity_goods_show)
public class ItemShowActivity extends BaseActivity{
    private final static String TAG="ItemShowActivity";
    @ViewInject(R.id.goods_show_webview)
    private WebView webView;
    @ViewInject(R.id.goods_show_share)
    private FloatingActionButton shareItem;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
//        setContentView(R.layout.activity_goods_show);
        init(extras.getString("url"));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    private void init(String url){
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if( url.startsWith("http:") || url.startsWith("https:") ) {
                    return false;
                }
                // Otherwise allow the OS to handle things like tel, mailto, etc.
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                startActivity( intent );
                return true;
            }
        });
        webView.loadUrl(url);
        shareItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"分享");
            }
        });
    }
//    @Event(value = R.id.goods_show_share,type = ImageView.OnClickListener.class)
//    private void shareItem(){
//        Log.d(TAG,"分享");
//    }
}
