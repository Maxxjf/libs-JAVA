package com.qcloud.qclib.rxutil;


import com.qcloud.qclib.rxutil.task.IOTask;
import com.qcloud.qclib.rxutil.task.NewTask;
import com.qcloud.qclib.rxutil.task.Task;
import com.qcloud.qclib.rxutil.task.UITask;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 类说明：Rx线程
 * Author: Kuzan
 * Date: 2017/10/10 9:34.
 */
public class RxScheduler {
    /**
     * IO线程执行
     * */
    public static <T> void doOnIOThread(final IOTask<T> task) {
        Observable.just(task)
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<IOTask<T>>() {
                    @Override
                    public void accept(@NonNull IOTask<T> tioTask) throws Exception {
                        tioTask.doOnIOThread();
                    }
                });
    }

    /**
     * 新线程执行
     * */
    public static <T> void doOnNewThread(final NewTask<T> task) {
        Observable.just(task)
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<NewTask<T>>() {
                    @Override
                    public void accept(@NonNull NewTask<T> tNewTask) throws Exception {
                        tNewTask.doOnNewThread();
                    }
                });
    }

    /**
     * ui线程执行
     * */
    public static <T> void doOnUiThread(final UITask<T> task) {
        Observable.just(task)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UITask<T>>() {
                    @Override
                    public void accept(@NonNull UITask<T> tuiTask) throws Exception {
                        task.doOnUIThread();
                    }
                });
    }

    /**
     * 主线程和子线程有交互执行
     * */
    public static <T> void doTask(final Task<T> task) {
        Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> e) throws Exception {
                task.doOnIOThread();
                e.onNext(task.getT());
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<T>() {
                    @Override
                    public void accept(@NonNull T t) throws Exception {
                        task.doOnUIThread();
                    }
                });
    }
}
