package com.wangziqing.goubige.ui.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wangziqing.goubige.R;
import com.wangziqing.goubige.model.Sort;
import com.wangziqing.goubige.utils.MyData;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by WZQ_PC on 2016/2/26 0026.
 */
public class BigSortsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private static final String TAG = "BigSortsListAdapter";
        private List<Sort> sorts;
        public BigSortsListAdapter sorts(List<Sort> sorts){
            this.sorts=sorts;
            return this;
        }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((SortHolder) holder).content.setText(sorts.get(position).getContent());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView content = (TextView)LayoutInflater.from(parent.getContext()).inflate(R.layout.sort_item, parent, false);
        return new SortHolder(content);
    }

    @Override
    public int getItemCount() {
        return sorts.size();
    }
    public static class SortHolder extends RecyclerView.ViewHolder {
        private static final int WIDTH= MyData.getWidth();
        private static final int HEIGHT=(int)Math.round(WIDTH*0.5);
        public TextView content;
        SortHolder(TextView content){
            super(content);
            this.content=content;
            content.setLayoutParams(new LinearLayout.LayoutParams(WIDTH, HEIGHT));
        }
    }
}
