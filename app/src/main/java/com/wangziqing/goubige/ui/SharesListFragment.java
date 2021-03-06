package com.wangziqing.goubige.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.common.eventbus.Subscribe;
import com.wangziqing.goubige.R;
import com.wangziqing.goubige.model.Page;
import com.wangziqing.goubige.model.Share;
import com.wangziqing.goubige.model.ShareEvent;
import com.wangziqing.goubige.service.ServiceFactory;
import com.wangziqing.goubige.service.ShareService;
import com.wangziqing.goubige.ui.utils.DividerItemDecoration;
import com.wangziqing.goubige.ui.utils.OnLoadMoreListener;
import com.wangziqing.goubige.ui.utils.ShareListAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

@ContentView(R.layout.fragment_share_list)
public class SharesListFragment extends BaseFragment {
    private static final String TAG = "SharesListFragment";
    @ViewInject(R.id.shares_container)
    private RecyclerView mRecyclerView;
    @ViewInject(R.id.refresh_layout)
    private SwipeRefreshLayout mSwipeRefreshWidget;

    private ShareService shareService;
    private ShareListAdapter shareListAdapter;

    private Page page;
    private LinearLayoutManager myLayoutManager;
    private OnLoadMoreListener onLoadMoreListener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return (LinearLayout) inflater.inflate(R.layout.fragment_share_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        shareService = ServiceFactory.getShareService();
        myLayoutManager = new LinearLayoutManager(mRecyclerView.getContext());
        onLoadMoreListener = new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //只负责其中发送请求
                page.page++;
                shareService.getShareByPage(page, "shares_loadMore");
            }
        };
        shareListAdapter = new ShareListAdapter(getActivity());
        initData();
    }

    //初始化数据
    private void initData() {
        page = new Page();
        mSwipeRefreshWidget.setColorSchemeColors(R.color.white, R.color.background, R.color.black);
        //初次
        mSwipeRefreshWidget.setRefreshing(true);
        mRecyclerView.setLayoutManager(myLayoutManager);
        //设置间隔
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), 0, 10));
        mRecyclerView.addOnScrollListener(new OnRecyclerScrollListener());

        shareListAdapter.onLoadMoreListener(onLoadMoreListener);
        mRecyclerView.setAdapter(shareListAdapter);
        //刷新数据
        refreshAction();
    }

    /**
     * 刷新动作
     */
    @Event(value = R.id.refresh_layout, type = SwipeRefreshLayout.OnRefreshListener.class)
    private void refreshAction() {
        page.page = 1;
        //只刷新数据
        mSwipeRefreshWidget.setRefreshing(true);
        shareService.getShareByPage(page, "shares_refresh");
    }

    /**
     * 刷新数据成功返回
     *
     * @param event
     */
    @Subscribe
    public void ShareEvent(ShareEvent event) {
        if (!event.tag.equals("shares_refresh")) return;
        mSwipeRefreshWidget.setRefreshing(false);
        refreshView(event.list);
    }

    //刷新商品界面
    public void refreshView(List<Share> shares) {
        shareListAdapter.setShares(shares);
        shareListAdapter.notifyDataSetChanged();
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
            if (shareListAdapter != null && newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 ==
                    shareListAdapter.getItemCount()) {
                //滚动到底部了，可以进行数据加载等操作
                //包含OnloadLIstenerd的发送请求以及界面的各种控制
                shareListAdapter.loadMore();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = myLayoutManager.findLastVisibleItemPosition();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        shareListAdapter.destroy();
    }
}
