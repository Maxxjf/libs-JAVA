package com.qcloud.qclib.pullrefresh;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qcloud.qclib.R;


/**
 * 类说明：上接加载更多尾部View
 * Author: Kuzan
 * Date: 2017/6/17 10:24.
 */
public class TailView extends LinearLayout implements PullRefreshView.OnTailStateListener {

    ImageView ivHeaderDownArrow;
    ImageView ivHeaderLoading;
    TextView textView;

    AnimationDrawable animationDrawable;

    private boolean isReach = false;
    private boolean isMore = true;

    public TailView(Context context) {
        super(context);
        animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.progress_round);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.layout_refresh_view, this, false);
        this.addView(layout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        initView(layout);
        restore();
        this.setPadding(0, 20, 0, 30);
    }

    private void initView(View view){
        ivHeaderDownArrow = (ImageView)view.findViewById(R.id.iv_header_down_arrow);
        ivHeaderLoading = (ImageView)view.findViewById(R.id.iv_header_loading);
        textView = (TextView)view.findViewById(R.id.tv_header_state);
    }

    @Override
    public void onScrollChange(View tail, int scrollOffset, int scrollRatio) {
        if (isMore) {
            if (scrollRatio == 100 && !isReach) {
                textView.setText(R.string.load_by_loosen);
                ivHeaderDownArrow.setRotation(0);
                isReach = true;
            } else if (scrollRatio != 100 && isReach) {
                textView.setText(R.string.load_by_pull_up);
                isReach = false;
                ivHeaderDownArrow.setRotation(180);
            }
        }
    }

    @Override
    public void onRefreshTail(View tail) {
        if (isMore) {
            ivHeaderLoading.setVisibility(VISIBLE);
            ivHeaderDownArrow.setVisibility(GONE);
            ivHeaderLoading.setImageDrawable(animationDrawable);
//            AnimationDrawable animationDrawable = (AnimationDrawable) head.getBackground();
            animationDrawable.start();
            textView.setText(R.string.loading);
        }
    }

    @Override
    public void onRetractTail(View tail) {
//            animationDrawable = (AnimationDrawable) head.getBackground();
        if (isMore) {
            restore();
            animationDrawable.stop();
            isReach = false;
        }
    }

    @Override
    public void onNotMore(View tail) {
        ivHeaderLoading.setVisibility(GONE);
        ivHeaderDownArrow.setVisibility(GONE);
        textView.setText(R.string.has_loaded_finish);
        isMore = false;
    }

    @Override
    public void onHasMore(View tail) {
        ivHeaderLoading.setVisibility(GONE);
        ivHeaderDownArrow.setVisibility(VISIBLE);
        textView.setText(R.string.load_by_pull_up);
        isMore = true;
    }

    private void restore() {
        ivHeaderLoading.setVisibility(GONE);
        ivHeaderDownArrow.setVisibility(VISIBLE);
        ivHeaderLoading.setImageResource(R.drawable.loading1);
        ivHeaderDownArrow.setImageResource(R.drawable.icon_down_arrow);
        ivHeaderDownArrow.setRotation(180);
        textView.setText(R.string.load_by_pull_up);
    }
}
