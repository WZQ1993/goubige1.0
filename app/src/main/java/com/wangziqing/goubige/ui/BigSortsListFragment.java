package com.wangziqing.goubige.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.eventbus.Subscribe;
import com.wangziqing.goubige.R;
import com.wangziqing.goubige.model.InitBigSortsEvent;
import com.wangziqing.goubige.model.Sort;
import com.wangziqing.goubige.service.ServiceFactory;
import com.wangziqing.goubige.service.SortsService;
import com.wangziqing.goubige.ui.utils.BigSortsListAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by WZQ_PC on 2016/2/26 0026.
 */
@ContentView(R.layout.fragment_sorts_list)
public class BigSortsListFragment extends BaseFragment{
    private static final String TAG = "BigSortsListFragment";
    @ViewInject(R.id.sort_list)
    private RecyclerView mRecyclerView;

    private List<Sort> bigSorts;
    private SortsService sortsService;
    private LinearLayoutManager linearLayoutManager;
    private BigSortsListAdapter bigSortsListAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_sorts_list, container, false);
        return mRecyclerView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sortsService= ServiceFactory.getSortsService();
        linearLayoutManager=new LinearLayoutManager(mRecyclerView.getContext());
        bigSortsListAdapter=new BigSortsListAdapter();
        initData();
    }
    private void initData(){
        sortsService.getBigSorts();
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }
    @Subscribe
    public void initBigSortsEvent(InitBigSortsEvent initBigSortsEvent) {
        mRecyclerView.setAdapter(bigSortsListAdapter.sorts(initBigSortsEvent.data));
    }
}
