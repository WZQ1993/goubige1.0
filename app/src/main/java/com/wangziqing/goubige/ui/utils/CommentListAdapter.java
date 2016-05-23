package com.wangziqing.goubige.ui.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.common.eventbus.Subscribe;
import com.wangziqing.goubige.R;
import com.wangziqing.goubige.model.Comment;
import com.wangziqing.goubige.model.CommentEvent;
import com.wangziqing.goubige.model.Page;
import com.wangziqing.goubige.model.Share;
import com.wangziqing.goubige.model.ShareEvent;
import com.wangziqing.goubige.ui.ShareDetailActivity;
import com.wangziqing.goubige.utils.CricleImageView;
import com.wangziqing.goubige.utils.EventBusFactory;
import com.wangziqing.goubige.utils.MyData;
import com.wangziqing.goubige.utils.MyImageOptionsFactory;

import org.xutils.x;

import java.util.List;

/**
 * Created by WZQ_PC on 2016/2/17 0017.
 */
public class CommentListAdapter extends RecyclerView.Adapter<ViewHolder> {
    private static final String TAG = "CommentListAdapter";
    private List<Comment> comments;
    private Page page;
    private ProgressBar pbLoading;
    private TextView tvLoadMore;
    private boolean loading = false;
    private OnLoadMoreListener onLoadMoreListener;
    private Context mContext;
    public CommentListAdapter(Context mContext,List<Comment> comments) {
        super();
        this.mContext = mContext;
        this.comments=comments;
        page = new Page();
        EventBusFactory.getHttpEventBus().register(this);
    }

    public void destroy() {
        EventBusFactory.getHttpEventBus().unregister(this);
    }

    public CommentListAdapter page(Page page) {
        this.page = page;
        return this;
    }

    public CommentListAdapter onLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
        return this;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false));

    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Comment comment=comments.get(position);
        x.image().bind(((ContentHolder) holder).userImage, MyData.getHOST() + "images/" + comment.getUserImage(),
                MyImageOptionsFactory.getHeaderImageOptions());
        ((ContentHolder) holder).userName.setText(comment.getUserName());
        ((ContentHolder) holder).content.setText("\u3000\u3000"+comment.getContent());
        ((ContentHolder) holder).time.setText(comment.getCreatTime().toString());
    }

    @Override
    public int getItemCount() {
        if (comments == null) return 0;
        else return getListSize();
    }
    /**
     * 获取数据集的大小
     */
    public int getListSize() {
        return comments.size();
    }

    /**
     * 显示正在加载的进度条，滑动到底部时，调用该方法，上拉就显示进度条，隐藏"上拉加载更多"
     * 加载前界面的的处理
     */
    private void showLoading() {
        if (pbLoading != null && tvLoadMore != null) {
            pbLoading.setVisibility(View.VISIBLE);
            tvLoadMore.setVisibility(View.GONE);
        }
    }

    /**
     * 显示上拉加载的文字，当数据加载完毕，调用该方法，隐藏进度条，显示“上拉加载更多”
     * 加载后界面的的处理
     */
    private void showLoadMore() {
        if (pbLoading != null && tvLoadMore != null) {
            pbLoading.setVisibility(View.GONE);
            tvLoadMore.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 加载更多动作
     */
    public void loadMore() {
        if (!loading) {
            loading = true;
            showLoading();
            onLoadMoreListener.onLoadMore();
        }
    }

    /**
     * 加载事件结果处理
     */
    @Subscribe
    private void CommentEvent(CommentEvent event) {
        if (!event.tag.equals("comments_loadMore")) return;
        loading = false;
        showLoadMore();
        this.notifyItemInserted(getListSize());
        addData(event.list);
        //通知添加数据
        this.notifyDataSetChanged();
    }

    private void addData(List<Comment> list) {
        for (Comment Share : list) {
            comments.add(Share);
        }
    }

    public static class ContentHolder extends ViewHolder {
        public CricleImageView userImage;
        public TextView userName;
        public TextView content;
        public TextView time;
        public ContentHolder(View itemview) {
            super(itemview);
            userImage = (CricleImageView) itemview.findViewById(R.id.comment_item_userimg);
            userName = (TextView) itemview.findViewById(R.id.comment_item_username);
            content = (TextView) itemview.findViewById(R.id.comment_item_content);
            time = (TextView) itemview.findViewById(R.id.comment_item_time);
        }
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setShares(List<Comment> comments) {
        this.comments = comments;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

}
