package com.ttyc.health.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.qiniu.android.storage.UpProgressHandler;
import com.ttyc.health.R;
import com.ttyc.health.utils.LogUtil;
import com.ttyc.health.utils.QiNiuUploadFileUtil;
import com.ttyc.health.utils.SharedPreferencesUtils;
import com.ttyc.health.views.zhihu.ZhihuPhotoUtil;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.IOException;
import java.util.ArrayList;

//启动界面
public class SplashActivity extends Activity {
    private static final int SELECT_PHOTO=1500;//选择照片
    private static int time=1500;//等待时间
    private static  String token="WaHCsVXPDiSRTxhaQwE1e-7kktrw3g_s8TT3c6aw:U3uXEk2O0yuSpU_bmbpgVU_B9-0=:eyJzY29wZSI6InR0eWMtaGVhbHRoIiwiZGVhZGxpbmUiOjE1NTcxNTY3NTh9";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Handler handler = new Handler();

        //当计时结束时，跳转至主界面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean flag= SharedPreferencesUtils.getGuidFlag(SplashActivity.this);
                if(flag){
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                }else{
                    startActivity(new Intent(SplashActivity.this, WelcomActivity.class));
                }
                SplashActivity.this.finish();
            }
        }, time);
    }


}
