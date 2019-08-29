package com.ydtl.uboxpay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ydtl.uboxpay.R;
import com.ydtl.uboxpay.tool.GlideUtil;

import java.util.List;

public class ViceAdvertPageAdapter extends ViewPagerAdapter<String> {

    private int size;
    private OnButtonOnClickListener onButtonOnClickListener;

    public ViceAdvertPageAdapter(Context context, List<String> list, int resId) {
        super(context, list, resId);
        size = list.size();
    }

    @Override
    public void removeItem(ViewGroup viewGroup, int position, Object object) {
        viewGroup.removeView((View) object);
    }

    @Override
    public View dealView(final Context context, List<String> list,
                         int resId, int position, ViewGroup viewGroup, View view) {
        view = LayoutInflater.from(context).inflate(resId, viewGroup, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.vice_advert_viewpager_item_img);
        GlideUtil.setImageUrl(context, imageView, list.get(position), R.drawable.welcome_bg
                , R.drawable.welcome_bg);
//        imageView.setImageResource(list.get(position));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonOnClickListener.setOnclick();
            }
        });
        viewGroup.addView(view, 0);
        view.setId(position);
        return view;
    }

    public interface OnButtonOnClickListener {
        void setOnclick();
    }

    public void setOnButtonOnClickListener(OnButtonOnClickListener onButtonOnClickListener) {
        this.onButtonOnClickListener = onButtonOnClickListener;
    }
}
