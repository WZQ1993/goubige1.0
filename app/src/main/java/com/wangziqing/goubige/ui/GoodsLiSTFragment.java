package com.wangziqing.goubige.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.eventbus.Subscribe;
import com.wangziqing.goubige.FragmentAdapter;
import com.wangziqing.goubige.InfoDetailsFragment;
import com.wangziqing.goubige.R;
import com.wangziqing.goubige.http.GoodsParams;
import com.wangziqing.goubige.model.Good;
import com.wangziqing.goubige.model.InitGoodsEvent;
import com.wangziqing.goubige.service.GoodService;
import com.wangziqing.goubige.service.ServiceFactory;
import com.wangziqing.goubige.ui.utils.DividerItemDecoration;
import com.wangziqing.goubige.ui.utils.GoodsListAdapter;
import com.wangziqing.goubige.ui.utils.OnLoadMoreListener;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WZQ_PC on 2016/2/17 0017.
 */
@ContentView(R.layout.fragment_goods_list)
public class GoodsListFragment extends BaseFragment {
    private static final String TAG = "GoodsListFragment";
    //代替findviewbyID（）
    @ViewInject(R.id.goods_container)
    private RecyclerView mRecyclerView;
    @ViewInject(R.id.refresh_layout)
    private SwipeRefreshLayout mSwipeRefreshWidget;
    //Tab菜单，主界面上面的tab切换菜单
    @ViewInject(R.id.tab_layout)
    private TabLayout mTabLayout;

    private GoodService goodService;
    private List<Good> goods;
    private StaggeredGridLayoutManager myLayoutManager;
    private GoodsListAdapter myListAdapter;
    private OnLoadMoreListener onLoadMoreListener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSwipeRefreshWidget = (SwipeRefreshLayout) inflater.inflate(R.layout.fragment_goods_list, container, false);
        return mSwipeRefreshWidget;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        goodService = ServiceFactory.getGoodService();
        myLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        onLoadMoreListener = new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //只负责其中发送请求
                myListAdapter.goodsParams.pageNum(++myListAdapter.goodsParams.pageNum);
                goodService.getGoods(myListAdapter.goodsParams, 0);
            }
        };
        myListAdapter = new GoodsListAdapter(getActivity());
        myListAdapter.goodsParams(new GoodsParams());
        initData();
    }

    //初始化数据
    private void initData() {
        mSwipeRefreshWidget.setColorSchemeColors(R.color.white, R.color.background, R.color.black);
        //初次
        mSwipeRefreshWidget.setRefreshing(true);
        mRecyclerView.setLayoutManager(myLayoutManager);
        //设置间隔
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), 10, 10));
        mRecyclerView.addOnScrollListener(new OnRecyclerScrollListener());
        goodService.initGoodsData(0);
    }

    @Event(value = R.id.refresh_layout, type = SwipeRefreshLayout.OnRefreshListener.class)
    private void refreshAction() {
        //只刷新数据
        mSwipeRefreshWidget.setRefreshing(true);
        goodService.initGoodsData(1);
    }

    @Subscribe
    public void GoodsEvent(InitGoodsEvent initGoodsEvent) {
        myListAdapter.goodsParams.pageNum(1).pageSize(10);
        goods = initGoodsEvent.goodsResponse.getData();
        switch (initGoodsEvent.type) {
            case "init":
                mSwipeRefreshWidget.setRefreshing(false);
                mRecyclerView.setAdapter(myListAdapter
                        .goods(goods)
                        .onLoadMoreListener(onLoadMoreListener)
                );
                break;
            case "refresh":
                mSwipeRefreshWidget.setRefreshing(false);
                refreshView(goods);
                break;
            default:
                break;
        }

    }

    //刷新商品界面
    public void refreshView(List<Good> goods) {
        myListAdapter.goods(goods);
        myListAdapter.notifyDataSetChanged();
    }

    /**
     * 用于上拉加载更多
     */
    public class OnRecyclerScrollListener extends RecyclerView.OnScrollListener {
        int lastVisibleItem = 0;

        /*
        onScrollStateChanged在listview状态改变时被调用，可以用来获取当前listview的状态：
        空闲SCROLL_STATE_IDLE 、滑动SCROLL_STATE_TOUCH_SCROLL和惯性滑动SCROLL_STATE_FLING
         */
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (myListAdapter != null && newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 ==
                    myListAdapter.getItemCount()) {
                //滚动到底部了，可以进行数据加载等操作
                //包含OnloadLIstenerd的发送请求以及界面的各种控制
                myListAdapter.loadMore();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int[] visibleItems = myLayoutManager.findLastVisibleItemPositions(null);
            lastVisibleItem = Math.max(visibleItems[0], visibleItems[1]);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myListAdapter.destroy();
    }
}
