package com.hash.lib.ui.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.hash.lib.ui.R;
import com.hash.lib.ui.recyclerview.adapter.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HashWaney on 2019/8/30.
 */

public class UIBaseLayout<T> extends LinearLayout {
    public Context context;
    public RecyclerView mRecycleView;
    protected ArrayList<T> dataList;
    protected BaseQuickAdapter baseQuickAdapter;
    public UICallBack callBack;

    public UIBaseLayout(Context context) {
        this(context, null);
        this.context = context;
    }

    public UIBaseLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UIBaseLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = View.inflate(context, R.layout.ui_base_layout, null);
        mRecycleView = view.findViewById(R.id.recyclerView);
        addView(view, new LinearLayoutCompat.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

    }

    public class UIQuestionAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

        public UIQuestionAdapter(int layoutResId, List<T> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, T item) {
            if (callBack != null) {
                callBack.callBackCovert(helper, item);
            }
        }
    }


    /**
     * 设置数据源
     *
     * @param dataSource
     * @param callBack
     */
    public void setDataSource(ArrayList<T> dataSource, UICallBack callBack) {
        this.callBack = callBack;
        if (dataSource != null) {
            dataSource.clear();
            dataSource.addAll(dataSource);
        }
        baseQuickAdapter.notifyDataSetChanged();
    }

    public void setOnItemClickListener(BaseQuickAdapter.OnItemClickListener onItemClickListener) {
        if (onItemClickListener != null && baseQuickAdapter != null) {
            baseQuickAdapter.setOnItemClickListener(onItemClickListener);
        }
    }

    public void setOnItemChildClickListener(BaseQuickAdapter.OnItemChildClickListener onItemChildClickListener) {
        if (onItemChildClickListener != null && baseQuickAdapter != null) {
            baseQuickAdapter.setOnItemChildClickListener(onItemChildClickListener);
        }
    }

    public interface UICallBack<T> {
        void callBackCovert(BaseViewHolder helper, T item);
    }

    public void notifyDataSetChanged() {
        if (baseQuickAdapter != null) {
            baseQuickAdapter.notifyDataSetChanged();
        }
    }

}
