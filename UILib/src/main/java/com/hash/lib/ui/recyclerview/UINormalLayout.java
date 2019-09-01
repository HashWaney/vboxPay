package com.hash.lib.ui.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

import com.hash.lib.ui.R;
import com.hash.lib.ui.recycleritem.RecycleViewDivider;

import java.util.ArrayList;

/**
 * Created by HashWaney on 2019/8/30.
 */

public class UINormalLayout extends UIBaseLayout {
    public UINormalLayout(Context context) {
        this(context, null);
    }

    public UINormalLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UINormalLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initQuestionAttr(attrs);
    }

    private void initQuestionAttr(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.question_layoutId);
        dataList = new ArrayList<>();
        mRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycleView.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL));
        mRecycleView.setHasFixedSize(true);
        baseQuickAdapter = new UIBaseLayout.UIQuestionAdapter(typedArray.getResourceId(R.styleable.question_layoutId_layoutId, -1), dataList);
        mRecycleView.setAdapter(baseQuickAdapter);
        typedArray.recycle();
    }
}
