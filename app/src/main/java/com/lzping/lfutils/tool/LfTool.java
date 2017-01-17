package com.lzping.lfutils.tool;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Looper;

/**
 * Created by user on 2017/1/11.
 */

public class LfTool {
    /**
     * 返回当前屏幕是否为竖屏。
     * @param context
     * @return 当且仅当当前屏幕为竖屏时返回true,否则返回false。
     */
    public static boolean isScreenOriatationPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }
    //检查是不是ui线程
    public static boolean checkUiThread(){
        if (Looper.myLooper() == Looper.getMainLooper()) { // UI主线程
            return true;
        } else { // 非UI主线程
            return false;
        }
    }
}
