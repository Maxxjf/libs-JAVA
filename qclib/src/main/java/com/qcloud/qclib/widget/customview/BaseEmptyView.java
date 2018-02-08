package com.qcloud.qclib.widget.customview;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qcloud.qclib.R;
import com.qcloud.qclib.base.BaseLinearLayout;
import com.qcloud.qclib.utils.StringUtils;

/**
 * 类说明：加载列表为空的默认布局
 * Author: Kuzan
 * Date: 2017/8/7 11:40.
 */
public abstract class BaseEmptyView extends BaseLinearLayout {

    private ImageView mImageIcon;
    private TextView mTvTip;
    private TextView mBtnRefresh;
    private LinearLayout mLayoutEmpty;

    private OnRefreshListener mListener;

    public BaseEmptyView(Context context) {
        this(context, null);
    }

    public BaseEmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseEmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_base_empty;
    }

    @Override
    protected void initViewAndData() {
        mImageIcon = (ImageView) mView.findViewById(R.id.image_icon);
        mTvTip = (TextView) mView.findViewById(R.id.tv_tip);
        mBtnRefresh = (TextView) mView.findViewById(R.id.btn_refresh);
        mLayoutEmpty = (LinearLayout) mView.findViewById(R.id.layout_empty);

        mImageIcon.setImageResource(setIcon());
        mBtnRefresh.setBackgroundResource(setBtnBg());
        mBtnRefresh.setTextColor(setBtnColor());
        mBtnRefresh.setVisibility(showBtn() ? VISIBLE : GONE);
        mBtnRefresh.setText(setBtnName());
        mBtnRefresh.setWidth((int) setBtnWidth());

        mTvTip.setText(setDefaultTip());
        mTvTip.setTextColor(setTipColor());
        mTvTip.setVisibility(showTip() ? VISIBLE : GONE);

        mLayoutEmpty.setBackgroundColor(setBgColor());
        mLayoutEmpty.setPadding(paddingLeft(), paddingTop(), paddingRight(), paddingBottom());

        mImageIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onRefresh();
                }
            }
        });

        mBtnRefresh.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onRefresh();
                }
            }
        });

    }

    /**
     * 没有数据
     */
    public void noData(String tip) {
        if (mTvTip != null) {
            if (StringUtils.isNotEmptyString(tip)) {
                mTvTip.setText(tip);
            } else {
                mTvTip.setText(R.string.tip_no_data);
            }
        }
    }

    public void noData(int res) {
        if (mTvTip != null) {
            if (res > 0) {
                mTvTip.setText(res);
            } else {
                mTvTip.setText(R.string.tip_no_data);
            }
        }
    }

    /**
     * 没有网络了
     */
    public void noNetWork() {
        if (mTvTip != null && mImageIcon != null) {
            mImageIcon.setImageResource(R.drawable.icon_no_network);
            mTvTip.setText(R.string.tip_no_net);
        }
    }

    /**
     * 有网络了
     */
    public void hasNetWork() {
        if (mTvTip != null && mImageIcon != null) {
            mImageIcon.setImageResource(setIcon());
            mTvTip.setText(setDefaultTip());
        }
    }


    /**
     * 设置图标
     */
    protected int setIcon() {
        return R.drawable.icon_no_data;
    }

    /**
     * 设置按钮背景
     */
    protected int setBtnBg() {
        return R.drawable.btn_green_radius_selector;
    }

    /**
     * 设置按钮背景
     */
    protected float setBtnWidth() {
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    /**
     * 设置是否显示按钮
     */
    protected boolean showBtn() {
        return false;
    }

    /**
     * 设置按钮名称
     */
    protected int setBtnName() {
        return R.string.btn_refresh;
    }

    /**
     * 按钮字体颜色
     */
    protected int setBtnColor() {
        return ContextCompat.getColor(mContext, R.color.white);
    }

    /**
     * 提示文字
     */
    protected int setDefaultTip() {
        return R.string.tip_no_data;
    }

    /**
     * 提示字体颜色
     */
    protected int setTipColor() {
        return ContextCompat.getColor(mContext, R.color.colorDark);
    }

    /**
     * 设置是否显示提示文字
     */
    protected boolean showTip() {
        return true;
    }

    /**
     * 设置背景颜色
     */
    protected int setBgColor() {
        return ContextCompat.getColor(mContext, R.color.transparent);
    }

    /**
     * 设置偏移
     */
    protected int paddingLeft() {
        return 0;
    }

    /**
     * 设置偏移
     */
    protected int paddingRight() {
        return 0;
    }

    /**
     * 设置偏移
     */
    protected int paddingTop() {
        return 0;
    }

    /**
     * 设置偏移
     */
    protected int paddingBottom() {
        return 0;
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        mListener = listener;
    }

    public interface OnRefreshListener {
        void onRefresh();
    }
}
