package com.wangziqing.goubige.ui.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import com.wangziqing.goubige.R;
import com.wangziqing.goubige.http.GoodsParams;
import com.wangziqing.goubige.model.GetUsersEvent;
import com.wangziqing.goubige.model.Good;
import com.wangziqing.goubige.model.LoadedMoreEvent;
import com.wangziqing.goubige.model.Page;
import com.wangziqing.goubige.model.Users;
import com.wangziqing.goubige.ui.ItemShowActivity;
import com.wangziqing.goubige.utils.CricleImageView;
import com.wangziqing.goubige.utils.EventBusFactory;
import com.wangziqing.goubige.utils.MyData;
import com.wangziqing.goubige.utils.MyImageOptionsFactory;

import org.xutils.x;

import java.util.List;

/**
 * Created by WZQ_PC on 2016/2/17 0017.
 */
public class UsersListAdapter extends RecyclerView.Adapter<ViewHolder> {
    private static final int TYPE_CONTENT = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_HOT = 2;
    private static final String TAG = "UsersListAdapter";
    public List<Users> users;
    public Page page;
    private ProgressBar pbLoading;
    private TextView tvLoadMore;
    private boolean loading = false;
    private OnLoadMoreListener onLoadMoreListener;
    private Context mContext;

    public UsersListAdapter(Context mContext) {
        super();
        this.mContext = mContext;
        page=new Page();
        EventBusFactory.getHttpEventBus().register(this);
    }

    public void destroy() {
        EventBusFactory.getHttpEventBus().unregister(this);
    }

    public UsersListAdapter page(Page page){
        this.page=page;
        return this;
    }
    public UsersListAdapter onLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
        return this;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CONTENT) {
            return new ContentHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.users_item, parent, false)
            );
        } else if (viewType == TYPE_FOOTER) {
            return new FooterViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_footer, parent, false)
            );
        } else if (viewType == TYPE_HOT) {
            return new HotViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.users_hot, parent, false)
            );
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == TYPE_CONTENT) {
            final Users user=users.get(position-1);
            if(user==null)Log.d(TAG,"user is null");
            if(((ContentHolder) holder).userName==null)Log.d(TAG,"holder is null");
            x.image().bind(((ContentHolder) holder).userImage,MyData.getHOST()+"images/"+user.getUserImg(),
                    MyImageOptionsFactory.getHeaderImageOptions());
            ((ContentHolder) holder).userName.setText(user.getUserName());
            ((ContentHolder) holder).userInfo.setText(user.getInfo());
        } else if (type == TYPE_HOT) {

        } else if (type == TYPE_FOOTER) {
            pbLoading = ((FooterViewHolder) holder).pbLoading;
            tvLoadMore = ((FooterViewHolder) holder).tvLoadMore;
        }

    }

    @Override
    public int getItemCount() {
        if(users==null)return 0;
        else return users.size()+2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HOT;
        } else if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_CONTENT;
        }
    }

    /**
     * 获取数据集的大小
     */
    public int getListSize() {
        return users.size();
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
    private void loadedMoreEvent(GetUsersEvent event) {
        if(!event.tag.equals("loadMore"))return ;
        loading = false;
        showLoadMore();
        this.notifyItemInserted(getListSize());
        addData(event.users);
        //通知添加数据
        this.notifyDataSetChanged();
    }

    private void addData(List<Users> list) {
        for (Users user : list) {
            users.add(user);
        }
    }

    public static class HotViewHolder extends ViewHolder {
        public CricleImageView user1, user2, user3, user4;

        public HotViewHolder(View itemView) {
            super(itemView);
            user1 = (CricleImageView) itemView.findViewById(R.id.hot_user1);
            user2 = (CricleImageView) itemView.findViewById(R.id.hot_user2);
            user3 = (CricleImageView) itemView.findViewById(R.id.hot_user3);
            user4 = (CricleImageView) itemView.findViewById(R.id.hot_user4);
        }
    }

    public static class FooterViewHolder extends ViewHolder {
        private TextView tvLoadMore;
        private ProgressBar pbLoading;

        public FooterViewHolder(View itemView) {
            super(itemView);
            tvLoadMore = (TextView) itemView.findViewById(R.id.tv_item_footer_load_more);
            pbLoading = (ProgressBar) itemView.findViewById(R.id.pb_item_footer_loading);
        }
    }

    public static class ContentHolder extends ViewHolder {
        public CricleImageView userImage;
        public TextView userName;
        public TextView userInfo;

        public ContentHolder(View itemview) {
            super(itemview);
            userImage = (CricleImageView) itemview.findViewById(R.id.item_userimg);
            userName = (TextView) itemview.findViewById(R.id.item_username);
            userInfo = (TextView) itemview.findViewById(R.id.item_userinfo);
        }
    }
}
