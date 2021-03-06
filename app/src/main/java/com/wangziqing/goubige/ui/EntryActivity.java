package com.wangziqing.goubige.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ImageView;

import com.wangziqing.goubige.R;
import com.wangziqing.goubige.service.GoodService;
import com.wangziqing.goubige.service.ServiceFactory;
import com.wangziqing.goubige.utils.MyData;

import java.util.Random;
//import butterknife.ButterKnife;

/**
 * Entry Activity
 *
 * @author bxbxbai
 */
public class EntryActivity extends ActionBarActivity {

    private static final int ANIMATION_DURATION = 2000;
    private static final float SCALE_END = 1.13F;
    private static final int[] SPLASH_ARRAY = {
            R.drawable.splash0,
            R.drawable.splash1,
            R.drawable.splash2,
            R.drawable.splash3,
            R.drawable.splash4,
            R.drawable.splash5,
            R.drawable.splash6,
            R.drawable.splash7,
            R.drawable.splash8,
            R.drawable.splash9,
            R.drawable.splash10,
            R.drawable.splash11,
            R.drawable.splash12,
            R.drawable.splash13,
            R.drawable.splash14,
            R.drawable.splash15,
            R.drawable.splash16,
    };

    ImageView mSplashImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        mSplashImage = (ImageView) this.findViewById(R.id.iv_entry);

        Random r = new Random(SystemClock.elapsedRealtime());
        mSplashImage.setImageResource(SPLASH_ARRAY[r.nextInt(SPLASH_ARRAY.length)]);
        //保存屏幕宽度
        MyData.setWindowManager(this);
        //设置路径
        MyData.setDataPath(this);
        MyData.getSDCARDPATH();
        animateImage();
        //toolbar.setVisibility(View.GONE);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //mTintManager.setTintColor(getResources().getColor(android.R.color.transparent));
    }

    private void animateImage() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mSplashImage, "scaleX", 1f, SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mSplashImage, "scaleY", 1f, SCALE_END);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIMATION_DURATION).play(animatorX).with(animatorY);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
//                MainActivity.start(EntryActivity.this);
                //初次进入可以考虑在此处获取数据，成功在进去
                EntryActivity.this.startActivity(new Intent(EntryActivity.this, MainActivity.class));
                EntryActivity.this.finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
