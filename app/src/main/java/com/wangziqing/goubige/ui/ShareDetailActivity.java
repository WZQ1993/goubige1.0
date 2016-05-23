package com.wangziqing.goubige.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.common.eventbus.Subscribe;
import com.wangziqing.goubige.R;
import com.wangziqing.goubige.model.Comment;
import com.wangziqing.goubige.model.CommentEvent;
import com.wangziqing.goubige.model.Page;
import com.wangziqing.goubige.model.Share;
import com.wangziqing.goubige.service.CommentService;
import com.wangziqing.goubige.service.ServiceFactory;
import com.wangziqing.goubige.ui.utils.CommentListAdapter;
import com.wangziqing.goubige.ui.utils.DividerItemDecoration;
import com.wangziqing.goubige.ui.utils.OnLoadMoreListener;
import com.wangziqing.goubige.utils.CricleImageView;
import com.wangziqing.goubige.utils.MyData;
import com.wangziqing.goubige.utils.MyImageOptionsFactory;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

@ContentView(R.layout.activity_share_details)
public class ShareDetailActivity extends BaseActivity{
    private static final String TAG="ShareDetailActivity";
    @ViewInject(R.id.share_detail_tool_bar)
    private Toolbar toolbar;
    @ViewInject(R.id.share_detail_collapsing_toolbar)
    private CollapsingToolbarLayout collapsingToolbar;
    @ViewInject(R.id.share_detail_good_image)
    private ImageView goodImage;
    @ViewInject(R.id.share_detail_good_title)
    private TextView goodTitle;
    @ViewInject(R.id.share_detail_user_image)
    private CricleImageView userImage;
    @ViewInject(R.id.share_detail_user_name)
    private TextView userName;
    @ViewInject(R.id.share_detail_content)
    private TextView content;

    @ViewInject(R.id.image)
    private ImageView goodViewImage;
    @ViewInject(R.id.title)
    private TextView goodViewTitle;
    @ViewInject(R.id.priceCurrent)
    private TextView goodViewPriceCurrent;
    @ViewInject(R.id.priceOld)
    private TextView goodViewPriceOld;
    @ViewInject(R.id.from)
    private TextView goodViewFrom;
    @ViewInject(R.id.comments_container)
    private RecyclerView mRecyclerView;
    @ViewInject(R.id.comments_cardView)
    private CardView commentsView;
    @ViewInject(R.id.share_detail_comment)
    private LinearLayout comment;
    private Share share;
    private CommentService commentService;

    private Page page;
    private LinearLayoutManager myLayoutManager;
    private OnLoadMoreListener onLoadMoreListener;
    private CommentListAdapter commentListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }
    private void initView(){
        if(null==this)Log.d(TAG,"activity is null");
        setSupportActionBar(toolbar);
        collapsingToolbar.setTitle("商品详情");
        x.image().bind(goodImage,share.getGoodImage(),
                MyImageOptionsFactory.getGoodImageOption());
        goodTitle.setText(share.getGoodTitle());
        x.image().bind(userImage, MyData.getHOST() + "images/" + share.getUserImage(),
                MyImageOptionsFactory.getHeaderImageOptions());
        userName.setText(share.getUserName());
        content.setText(share.getContent());

        x.image().bind(goodViewImage,share.getGoodImage(),
                MyImageOptionsFactory.getGoodImageOption());
        goodViewTitle.setText(share.getGoodTitle());
        goodViewPriceCurrent.setText("￥"+share.getGoodPriceCurrent());
        goodViewPriceOld.setText("￥"+share.getGoodPriceOld());
        if(share.getGoodFrom()==1){
            goodViewFrom.setText("（淘宝）");
        }else if(share.getGoodFrom()==2){
            goodViewFrom.setText("（卷皮）");
        }
    }
    private void initData(){
        Intent intent = this.getIntent();
        share=(Share) intent.getSerializableExtra("share");
        page=new Page();
        commentService= ServiceFactory.getCommentService();
        commentService.getShareByPage(page,"comments_refresh",share.getID());
    }
    @Event(value = R.id.goto_bug, type = Button.OnClickListener.class)
    private void goToBuy(View view) {
        Log.d(TAG,"onclick goodView");
        Intent intent =new Intent(ShareDetailActivity.this, ItemShowActivity.class);
        intent.putExtra("url",share.getGoodUrl());
        this.startActivity(intent);
    }
    @Subscribe
    public void CommentEvent(CommentEvent event) {
        if(!event.tag.equals("comments_refresh"))return ;
        if(0==event.list.size()){
            commentsView.setVisibility(View.GONE);
        }else {
            initCommentListData(event.list);
        }
    }
    private void initCommentListData(List<Comment> commentList){
        myLayoutManager = new LinearLayoutManager(mRecyclerView.getContext());
        onLoadMoreListener = new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //只负责其中发送请求
                page.page++;
                commentService.getShareByPage(page,"comments_loadMore",share.getID());
            }
        };
        commentListAdapter=new CommentListAdapter(this,commentList);
        FrameLayout.LayoutParams layoutParams=(FrameLayout.LayoutParams)mRecyclerView.getLayoutParams();
        layoutParams.height=(commentList.size()+4)*100;
        mRecyclerView.setLayoutParams(layoutParams);
        mRecyclerView.setLayoutManager(myLayoutManager);
        //设置间隔
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, 0, 10));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnScrollListener(new OnRecyclerScrollListener());

        commentListAdapter.onLoadMoreListener(onLoadMoreListener);
        mRecyclerView.setAdapter(commentListAdapter);
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
            if (commentListAdapter != null && newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 ==
                    commentListAdapter.getItemCount()) {
                //滚动到底部了，可以进行数据加载等操作
                //包含OnloadLIstenerd的发送请求以及界面的各种控制
                commentListAdapter.loadMore();
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
        if(null!=commentListAdapter)commentListAdapter.destroy();
    }
}

