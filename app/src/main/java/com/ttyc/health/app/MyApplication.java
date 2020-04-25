package com.ttyc.health.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.fm.openinstall.OpenInstall;

public class MyApplication extends Application {
    public static String phone="";

    @Override
    public void onCreate() {
        super.onCreate();
        if (isMainProcess()) {
            OpenInstall.init(this);
        }
    }
    public boolean isMainProcess() {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return getApplicationInfo().packageName.equals(appProcess.processName);
            }
        }
        return false;
    }
}
