package com.ttyc.health.utils;

import android.util.Log;

public class LogUtil {
    private static final boolean DEBUG_FLAG=true;//为true时，表示显示debug信息
    public static void e(String tag,String msg){
        if(!DEBUG_FLAG||tag==null||msg==null) return;
        Log.e(tag,msg);
    }

}
