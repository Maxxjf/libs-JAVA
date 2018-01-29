package com.qcloud.qclib.widget.indicator.transition;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.qcloud.qclib.utils.ColorGradientUtil;
import com.qcloud.qclib.widget.indicator.Indicator;

/**
 * 类说明：tab滑动变化的转换监听，图标
 * Author: Kuzan
 * Date: 2017/11/17 16:45.
 */
public class OnTransitionImageListener implements Indicator.OnTransitionListener {
    private float selectSize = -1;
    private float unSelectSize = -1;
    private ColorGradientUtil gradient;
    private float dFontFize = -1;

    private int selectImgColor = Color.BLACK;
    private int unSelectImgColor = Color.WHITE;

    private boolean isPxSize = false;

    public OnTransitionImageListener() {
        super();
    }

    public OnTransitionImageListener(int selectImgColor, int unSelectImgColor) {
        super();
        setColor(selectImgColor, unSelectImgColor);
    }

    public final OnTransitionImageListener setColorId(Context context, int selectColorId, int unSelectColorId) {
        Resources res = context.getResources();
        setColor(res.getColor(selectColorId), res.getColor(unSelectColorId));
        return this;
    }

    public final OnTransitionImageListener setColor(int selectColor, int unSelectColor) {
        gradient = new ColorGradientUtil(unSelectColor, selectColor, 100);
        return this;
    }

    /**
     * 如果tabItemView 不是目标的ImageView，那么你可以重写该方法返回实际要变化的ImageView
     *
     * @param tabItemView
     *            Indicator的每一项的view
     * @return
     */
    public ImageView getImageView(View tabItemView) {
        return (ImageView) tabItemView;
    }

    @Override
    public void onTransition(View view, int position, float selectPercent) {
        ImageView selectImageView = getImageView(view);
        selectImageView.setColorFilter(gradient.getColor((int) (selectPercent * 100)));
    }
}
