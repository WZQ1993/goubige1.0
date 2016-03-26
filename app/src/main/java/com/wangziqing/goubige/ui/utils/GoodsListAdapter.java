package com.wangziqing.goubige.ui.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
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
import com.wangziqing.goubige.http.GoodsParams;
import com.wangziqing.goubige.model.Good;
import com.wangziqing.goubige.model.LoadedMoreEvent;
import com.wangziqing.goubige.ui.ItemShowActivity;
import com.wangziqing.goubige.utils.EventBusFactory;
import com.wangziqing.goubige.utils.MyData;
import com.wangziqing.goubige.utils.MyImageOptionsFactory;

import org.xutils.x;

import java.util.List;

/**
 * Created by WZQ_PC on 2016/2/17 0017.
 */
public class GoodsListAdapter extends RecyclerView.Adapter<ViewHolder> {
    private static final int TYPE_CONTENT = 0;
    private static final int TYPE_FOOTER = 1;
    private static final String TAG = "GoodsListAdapter";
    public GoodsParams goodsParams;
    private List<Good> goods;
    private ProgressBar pbLoading;
    private TextView tvLoadMore;
    private boolean loading=false;
    private OnLoadMoreListener onLoadMoreListener;
    private Context mContext;

    public GoodsListAdapter(Context mContext) {
        super();
        this.mContext=mContext;
        EventBusFactory.getHttpEventBus().register(this);
    }
    public void destroy(){
        EventBusFactory.getHttpEventBus().unregister(this);
    }

    public GoodsListAdapter goods(List<Good> goods) {
        this.goods = goods;
        return this;
    }
    public GoodsListAdapter goodsParams(GoodsParams goodsParams){
        this.goodsParams=goodsParams;
        return this;
    }
    public GoodsListAdapter onLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
        return this;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CONTENT) {
            CardView view = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_item, parent, false);
            return new ContentHolder(view);
        }
        if (viewType == TYPE_FOOTER) {
            return new FooterViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_footer, parent, false)
            );
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == TYPE_CONTENT) {
            ((ContentHolder) holder).position = position;
            final Good good = goods.get(position);
            x.image().bind(((ContentHolder) holder).pic, good.getPic(),
                    MyImageOptionsFactory.getGoodImageOption());
            if(good.getTitle().length()>12){
                good.setTitle(good.getTitle().substring(0,12)+"..");
            }
            ((ContentHolder) holder).title.setText(good.getTitle());
            ((ContentHolder) holder).priceCurrent.setText("￥" + good.getPriceCurrent());
            ((ContentHolder) holder).priveOld.setText("￥" + good.getPriceOld());
            //点击事件监听
            ((ContentHolder) holder).pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(mContext, ItemShowActivity.class);
                    intent.putExtra("url",good.getUrl());
                    mContext.startActivity(intent);
                }
            });

        } else if (type == TYPE_FOOTER) {
            pbLoading = ((FooterViewHolder) holder).pbLoading;
            tvLoadMore = ((FooterViewHolder) holder).tvLoadMore;
        }

    }

    @Override
    public int getItemCount() {
        return goods.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_CONTENT;
        }
    }

    /**
     * 获取数据集的大小
     */
    public int getListSize() {
        return goods.size();
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
        if(!loading){
            loading=true;
            showLoading();
            onLoadMoreListener.onLoadMore();
        }
    }

    /**
     * 加载事件结果处理
     */
    @Subscribe
    private void loadedMoreEvent(LoadedMoreEvent loadedMoreEvent) {
        loading=false;
        showLoadMore();
        if (loadedMoreEvent.isSuccess) {
            //通知添加数据
            this.notifyItemInserted(getListSize());
            addData(loadedMoreEvent.goodsResponse.getData());
            this.notifyDataSetChanged();
        } else {

        }
    }
    private void addData(List<Good> list){
        for (Good good:list
             ) {
            goods.add(good);
        }
    }
    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        private TextView tvLoadMore;
        private ProgressBar pbLoading;

        public FooterViewHolder(View itemView) {
            super(itemView);
            tvLoadMore = (TextView) itemView.findViewById(R.id.tv_item_footer_load_more);
            pbLoading = (ProgressBar) itemView.findViewById(R.id.pb_item_footer_loading);
        }
    }

    public static class ContentHolder extends RecyclerView.ViewHolder {
        public CardView item;
        public ImageView pic;
        public TextView title;
        public TextView priceCurrent;
        public TextView priveOld;
        public int position;
        public static final int width = (int) Math.round(MyData.getWidth() * 0.4);
        public static final int height = (int) Math.round(width * 1.5);

        public ContentHolder(CardView itemview) {
            super(itemview);
            item = itemview;
            item.setLayoutParams(new LinearLayout.LayoutParams(width, height));
            pic = (ImageView) itemview.findViewById(R.id.item_pic);
            pic.setMaxWidth(width);
            pic.setMaxHeight(width);
            title = (TextView) itemview.findViewById(R.id.item_title);
            priceCurrent = (TextView) itemview.findViewById(R.id.item_priceCurrent);
            priveOld = (TextView) itemview.findViewById(R.id.item_priceOld);
            priveOld.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}
