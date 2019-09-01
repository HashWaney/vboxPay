package com.hash.lib.ui.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

import com.hash.lib.ui.R;
import com.hash.lib.ui.recycleritem.GridSpacingItemDecoration;
import com.hash.lib.ui.utils.PixelUtil;

import java.util.ArrayList;

/**
 * Created by HashWaney on 2019/8/30.
 */

public class UIGridLayout extends UIBaseLayout {

    public UIGridLayout(Context context) {
        this(context, null);
    }

    public UIGridLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UIGridLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGridAttr(attrs);
    }

    private void initGridAttr(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.question_layoutId);
        int spacing = typedArray.getInt(R.styleable.question_layoutId_spacing, 0);
        int spanCount = typedArray.getInteger(R.styleable.question_layoutId_spanCount, 2);
        int layoutId = typedArray.getResourceId(R.styleable.question_layoutId_layoutId, -1);
        // TODO: 2018/1/18 1为垂直,0为水平
        int orientation = typedArray.getInteger(R.styleable.question_layoutId_orient, GridLayoutManager.VERTICAL);
        boolean includeEdge = typedArray.getBoolean(R.styleable.question_layoutId_includeEdge, true);
        dataList = new ArrayList<>();
        mRecycleView.setLayoutManager(new GridLayoutManager(getContext(), spanCount, orientation, false));
        mRecycleView.addItemDecoration(new GridSpacingItemDecoration(spanCount, PixelUtil.dip2px(getContext(), spacing), includeEdge));
        mRecycleView.setHasFixedSize(true);
        baseQuickAdapter = new UIBaseLayout.UIQuestionAdapter(layoutId, dataList);
        mRecycleView.setAdapter(baseQuickAdapter);
        typedArray.recycle();

    }
}
