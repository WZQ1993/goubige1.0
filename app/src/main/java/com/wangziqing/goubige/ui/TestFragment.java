package com.wangziqing.goubige.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wangziqing.goubige.R;
import org.xutils.view.annotation.ContentView;

/**
 * Created by WZQ_PC on 2016/2/26 0026.
 */
@ContentView(R.layout.fragment_test)
public class TestFragment extends BaseFragment{
    private static final String TAG = "TestFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
