package com.qcloud.qclib.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 类说明：打开或关闭软键盘
 * Author: Kuzan
 * Date: 2017/5/25 15:13.
 */
public class KeyBoardUtils {

    /**
     * 打卡软键盘
     *
     * @param context  上下文
     * @param editText 输入框
     */
    public static void showKeybord(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param context  上下文
     * @param editText 输入框
     */
    public static void hideKeybord(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * 获取输入框高度
     * */
    public static int getKeyboardHeight(Activity paramActivity) {
        int height = ScreenUtils.getScreenHeight(paramActivity) - SystemBarUtil.getStatusBarHeight(paramActivity)
                - ScreenUtils.getAppHeight(paramActivity);
        if (height == 0) {
            height = ConstantUtil.getInt("KeyboardHeight", 787);//787为默认软键盘高度 基本差不离
        } else {
            ConstantUtil.writeInt("KeyboardHeight", height);
        }
        return height;
    }

    /**
     * 键盘是否在显示
     * */
    public static boolean isKeyBoardShow(Activity paramActivity) {
        int height = ScreenUtils.getScreenHeight(paramActivity) - SystemBarUtil.getStatusBarHeight(paramActivity)
                - ScreenUtils.getAppHeight(paramActivity);
        return height != 0;
    }
}
