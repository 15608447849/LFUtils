package com.lzping.lfutils.tool;

import android.util.Log;

/**
 * Created by user on 2017/1/10.
 */

public class Fprint {
    public static boolean isPrint = true;
    private static String TAGS = "_碎片工具日志_";
    public static void D(String var){
        if (isPrint){
            Log.d(TAGS," [ "+ var+" ]");
        }
    }
    public static void E(String var){
        if (isPrint){
            Log.e(TAGS," [ "+ var+" ]");
        }
    }
    public static void I(String TAG,String var){
        if (isPrint){
            Log.i(TAGS," [ "+ TAG+" ]>>>[ "+ var +" ]");
        }
    }
}
