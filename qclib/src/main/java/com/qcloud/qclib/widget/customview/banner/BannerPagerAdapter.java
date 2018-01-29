package com.qcloud.qclib.widget.customview.banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.util.List;

/**
 * 类说明：自定义轮播图
 * Author: Kuzan
 * Date: 2017/6/17 13:59.
 */
public class BannerPagerAdapter<T> extends PagerAdapter {

    private Context mContext;
    private List<T> mList;
    private CustomBanner.ViewCreator mCreator;
    private CustomBanner.OnPageClickListener mOnPageClickListener;

    private SparseArray<View> views = new SparseArray<>();

    public BannerPagerAdapter(Context context, CustomBanner.ViewCreator<T> creator) {
        this.mContext = context;
        this.mCreator = creator;
    }

    @Override
    public int getCount() {
        return mList == null || mList.isEmpty() ? 0 : mList.size() + 2;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //Warning：不要在这里调用removeView
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = views.get(position);

        if (view == null) {
            view = mCreator.createView(mContext, position);
            views.put(position, view);
        }

        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp = view.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(view);
        }

        final int item = getActualPosition(position);

        final T t = mList.get(item);

        mCreator.UpdateUI(mContext, view, item, mList.get(item));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnPageClickListener != null) {
                    mOnPageClickListener.onPageClick(item, t);
                }
            }
        });

        container.addView(view);
        return view;
    }

    /**
     * 获取当前position
     * */
    private int getActualPosition(int position) {
        if (position == 0) {
            return mList.size() - 1;
        } else if (position == getCount() - 1) {
            return 0;
        } else {
            return position - 1;
        }
    }

    /**
     * 替换数据
     * */
    public void replaceList(List<T> list) {
        if (list != null) {
            mList = list;
        } else {
            mList.clear();
        }

        notifyDataSetChanged();
    }

    /**
     * 自定义点击事件
     * */
    public void setOnPageClickListener(CustomBanner.OnPageClickListener l) {
        mOnPageClickListener = l;
    }

    public CustomBanner.OnPageClickListener getOnPageClickListener() {
        return mOnPageClickListener;
    }
}
