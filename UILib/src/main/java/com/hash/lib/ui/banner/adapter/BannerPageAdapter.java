package com.hash.lib.ui.banner.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hash.lib.ui.banner.helper.BannerPageAdapterHelper;
import com.hash.lib.ui.banner.holder.BannerItemHolderCreator;
import com.hash.lib.ui.banner.holder.Holder;
import com.hash.lib.ui.banner.listener.OnItemClickListener;

import java.util.List;

/**
 * Created by HashWaney on 2019/8/29.
 */

public class BannerPageAdapter<T> extends RecyclerView.Adapter<Holder> {
    protected List<T> dataSource;
    private BannerItemHolderCreator creator;
    private BannerPageAdapterHelper helper;
    private boolean canLooper;
    private OnItemClickListener onItemClickListener;


    public BannerPageAdapter(BannerItemHolderCreator creator, List<T> data, boolean canLooper) {
        this.creator = creator;
        this.dataSource = data;
        this.canLooper = canLooper;
        helper = new BannerPageAdapterHelper();

    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = creator.getLayoutId();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        helper.onCreateViewHolder(parent, itemView);
        return creator.createHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        helper.onBindViewHolder(holder.itemView, position, getItemCount());
        int realPosition = position % dataSource.size();
        Log.e(this.getClass().getSimpleName(),"dataSource:"+dataSource);
        holder.updateItem(dataSource.get(realPosition));
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new OnPageItemClickListener(realPosition));
        }

    }

    @Override
    public int getItemCount() {
        if (dataSource.size() == 0) return 0;
        return canLooper ? 3 * dataSource.size() : dataSource.size();
    }

    public void setCanLoop(boolean canLooper) {
        this.canLooper = canLooper;
    }

    public boolean isCanLooper() {
        return canLooper;
    }


    public int getRealItemCount() {
        return dataSource != null ? dataSource.size() : 0;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    class OnPageItemClickListener implements View.OnClickListener {
        private int clickPosition;

        public OnPageItemClickListener(int clickPosition) {
            this.clickPosition = clickPosition;
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(clickPosition);
            }
        }

        public int getPosition() {
            return clickPosition;
        }

        public void setPosition(int position) {
            this.clickPosition = position;


        }
    }
}
