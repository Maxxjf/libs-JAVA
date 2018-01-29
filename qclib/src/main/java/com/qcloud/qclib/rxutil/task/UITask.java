package com.qcloud.qclib.rxutil.task;

/**
 * 类说明：UI线程，主线程
 * Author: Kuzan
 * Date: 2017/10/10 9:37.
 */
public interface UITask<T> {
    void doOnUIThread();
}
