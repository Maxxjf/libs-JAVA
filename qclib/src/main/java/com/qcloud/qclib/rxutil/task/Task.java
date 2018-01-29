package com.qcloud.qclib.rxutil.task;

/**
 * 类说明：主线程和子线程有交互执行
 * Author: Kuzan
 * Date: 2017/10/10 9:39.
 */
public abstract class Task<T> {
    // T表示子线程和主线程需要调用的对象
    private T t;

    public Task(T t) {
        this.t = t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public T getT() {
        return t;
    }

    public abstract void doOnUIThread();

    public abstract void doOnIOThread();
}
