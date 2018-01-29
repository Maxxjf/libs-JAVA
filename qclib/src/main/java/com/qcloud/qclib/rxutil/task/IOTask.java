package com.qcloud.qclib.rxutil.task;

/**
 * 类说明：IO线程
 * Author: Kuzan
 * Date: 2017/10/10 9:35.
 */
public interface IOTask<T> {
    void doOnIOThread();
}
