package com.wangziqing.goubige.ui.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.common.eventbus.Subscribe;
import com.wangziqing.goubige.R;
import com.wangziqing.goubige.model.GetUsersEvent;
import com.wangziqing.goubige.model.Page;
import com.wangziqing.goubige.model.Share;
import com.wangziqing.goubige.model.ShareEvent;
import com.wangziqing.goubige.model.Users;
import com.wangziqing.goubige.service.CommentService;
import com.wangziqing.goubige.service.ServiceFactory;
import com.wangziqing.goubige.ui.ItemShowActivity;
import com.wangziqing.goubige.ui.ShareDetailActivity;
import com.wangziqing.goubige.utils.CricleImageView;
import com.wangziqing.goubige.utils.EventBusFactory;
import com.wangziqing.goubige.utils.MyData;
import com.wangziqing.goubige.utils.MyImageOptionsFactory;

import org.w3c.dom.Text;
import org.xutils.x;

import java.io.LineNumberReader;
import java.util.List;

/**
 * Created by WZQ_PC on 2016/2/17 0017.
 */
public class ShareListAdapter extends RecyclerView.Adapter<ViewHolder> {
    private static final String TAG = "ShareListAdapter";
    private List<Share> shares;
    private Page page;
    private ProgressBar pbLoading;
    private TextView tvLoadMore;
    private boolean loading = false;
    private OnLoadMoreListener onLoadMoreListener;
    private Context mContext;

    public ShareListAdapter(Context mContext) {
        super();
        this.mContext = mContext;
        page = new Page();
        EventBusFactory.getHttpEventBus().register(this);
    }

    public void destroy() {
        EventBusFactory.getHttpEventBus().unregister(this);
    }

    public ShareListAdapter page(Page page) {
        this.page = page;
        return this;
    }

    public ShareListAdapter onLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
        return this;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.shares_item, parent, false));

    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Share share=shares.get(position);
        ((ContentHolder) holder).view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent();
                    intent.setClass(mContext, ShareDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("share", share);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
            }
        });
        x.image().bind(((ContentHolder) holder).goodImage,share.getGoodImage(),
                MyImageOptionsFactory.getGoodImageOption());
        x.image().bind(((ContentHolder) holder).userImage, MyData.getHOST() + "images/" + share.getUserImage(),
                MyImageOptionsFactory.getHeaderImageOptions());
        ((ContentHolder) holder).userName.setText(share.getUserName());
        ((ContentHolder) holder).content.setText("\u3000\u3000"+share.getContent());
        ((ContentHolder) holder).goodTitle.setText(share.getGoodTitle());
        //commentNumImage点击事件
//        ((ContentHolder) holder).commentNumImage.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                if(comment.getId()==R.id.share_list_comment)Log.d(TAG,"comment id is right");
//                comment.setVisibility(View.VISIBLE);
//                comment.setFocusable(true);
//                comment.setFocusableInTouchMode(true);
//                comment.requestFocus();
//                InputMethodManager inputManager =
//                        (InputMethodManager) comment.getContext().getSystemService(mContext.INPUT_METHOD_SERVICE);
//                inputManager.showSoftInput(comment, 0);
//            }
//        });
        ((ContentHolder) holder).commentNum.setText(String.valueOf(share.getCommentNum()));
        //supportNumImage
        ((ContentHolder) holder).supportNum.setText(String.valueOf(share.getSupportNum()));
    }
    @Override
    public int getItemCount() {
        if (shares == null) return 0;
        else return getListSize();
    }
    /**
     * 获取数据集的大小
     */
    public int getListSize() {
        return shares.size();
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
    private void ShareEvent(ShareEvent event) {
        if (!event.tag.equals("shares_loadMore")) return;
        loading = false;
        showLoadMore();
        this.notifyItemInserted(getListSize());
        addData(event.list);
        //通知添加数据
        this.notifyDataSetChanged();
    }

    private void addData(List<Share> list) {
        for (Share Share : list) {
            shares.add(Share);
        }
    }

    public static class ContentHolder extends ViewHolder {
        public ImageView goodImage;
        public CricleImageView userImage;
        public TextView userName;
        public TextView content;
        public TextView goodTitle;
        public ImageView commentNumImage;
        public TextView commentNum;
        public ImageView supportNumImage;
        public TextView supportNum;
        public LinearLayout view;
        public ContentHolder(View itemview) {
            super(itemview);
            this.view=(LinearLayout) itemview;
            goodImage = (ImageView) itemview.findViewById(R.id.share_item_good_image);
            userImage = (CricleImageView) itemview.findViewById(R.id.share_item_user_image);
            userName = (TextView) itemview.findViewById(R.id.share_item_user_name);
            content = (TextView) itemview.findViewById(R.id.share_item_content);
            goodTitle = (TextView) itemview.findViewById(R.id.share_item_good_title);
            commentNumImage = (ImageView) itemview.findViewById(R.id.share_item_commentNum_png);
            commentNum = (TextView) itemview.findViewById(R.id.share_item_commentNum);
            supportNumImage = (ImageView) itemview.findViewById(R.id.share_item_supportNum_png);
            supportNum = (TextView) itemview.findViewById(R.id.share_item_supportNum);
        }
    }

    public List<Share> getShares() {
        return shares;
    }

    public void setShares(List<Share> shares) {
        this.shares = shares;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

}
