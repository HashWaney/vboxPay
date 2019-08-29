package com.hash.lib.ui.recycleritem;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hash.lib.ui.R;


/**
 * 作者：cq on 2016/7/6 11:16
 * 邮箱：chen6889168@126.com
 */
public class RecycleViewDivider extends RecyclerView.ItemDecoration {

    private Drawable mDivider;

    private int mOrientation;

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    // TODO: 2017/7/22 是否显示最后一根线
    private boolean isShowLastLine=false;

    // TODO: 2017/7/22 是否显示第一条分割线 
    private boolean isShowTopLine=true;
    
    public RecycleViewDivider(Context context, int orientation) {
        this(context, orientation, R.drawable.base_list_divider_drawable);
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    public RecycleViewDivider(Context context, int orientation, boolean isShowLastLine) {
        setOrientation(orientation);
        mDivider = ContextCompat.getDrawable(context, R.drawable.base_list_divider_drawable_new);
        this.isShowLastLine=isShowLastLine;
    }

    /**
     * 自定义分割线
     *建议使用 R.drawable.shape_recycleview_divier_delegat
     * @param context
     * @param orientation 列表方向
     * @param drawableId  分割线图片
     */
    public RecycleViewDivider(Context context, int orientation, int drawableId) {
        setOrientation(orientation);
        mDivider = ContextCompat.getDrawable(context, drawableId);
    }

    //获取分割线尺寸
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }

    public void setShowTopLine(boolean showTopLine) {
        isShowTopLine = showTopLine;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent) {
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        int childCount =0;
        if (!isShowLastLine){
            childCount=parent.getChildCount()-1;
        }else{
            childCount=parent.getChildCount();
        }
        for (int i = 0; i < childCount; i++) {
            if (i==0&&!isShowTopLine){
                continue;
            }
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount =0;
        if (!isShowLastLine){
            childCount=parent.getChildCount()-1;
        }else{
            childCount=parent.getChildCount();
        }
        for (int i = 0; i < childCount; i++) {
            if (i==0&&!isShowTopLine){
                continue;
            }
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}
