package com.qcloud.qclib.toast;


import android.content.Context;

import com.qcloud.qclib.utils.StringUtils;

/**
 * 类说明：Toast 消息提醒
 * Author: Kuzan
 * Date: 2017/5/25 15:08.
 */
public class ToastUtils {
    private static volatile Toast toast;

    public static void ToastMessage(Context cont, String msg) {
        if (cont == null || StringUtils.isEmptyString(msg)) {
            return;
        }
        // Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
        showToast(cont, msg, Toast.LENGTH_LONG);
    }

    public static void ToastMessage(Context cont, int msg) {
        if (cont == null) {
            return;
        }
        // Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
        showToast(cont, cont.getString(msg), Toast.LENGTH_LONG);
    }

    public static void ToastMessage(Context cont, String msg, int time) {
        if (cont == null || StringUtils.isEmptyString(msg)) {
            return;
        }
        // Toast.makeText(cont, msg, time).show();
        showToast(cont, msg, time);
    }

    private static void showToast(Context context, String msg, long time) {
        toast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
