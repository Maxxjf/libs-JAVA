package com.qcloud.qclib.base;

import com.qcloud.qclib.rxbus.Bus;
import com.qcloud.qclib.rxbus.BusProvider;

import timber.log.Timber;

/**
 * 类说明：Presenter基类
 * Author: Kuzan
 * Date: 2017/8/1 9:34.
 */
public abstract class BasePresenter<T> {
    protected Bus mEventBus = BusProvider.getInstance();

    public T mView;

    public void attach(T mView) {
        this.mView = mView;
        try {
            mEventBus.register(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void detach() {
        mView = null;
        onDestroy();
    }

    public void onDestroy() {
        try {
            if (mEventBus != null) {
                mEventBus.unregister(this);
                mEventBus = null;
            }
        }catch (Exception e){
            Timber.e(e.toString());
        }

    }
}
