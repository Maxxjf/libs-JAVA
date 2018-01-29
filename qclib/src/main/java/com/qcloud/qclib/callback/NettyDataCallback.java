package com.qcloud.qclib.callback;

/**
 * 类说明：数据解析回调
 * Author: Kuzan
 * Date: 2017/6/19 11:36.
 */
public interface NettyDataCallback<T> {
    void onSuccess(T t, String uuid);

    void onError(int status, String errMsg);
}
