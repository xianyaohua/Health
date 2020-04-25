package com.ttyc.health.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.ttyc.health.R;
import com.ttyc.health.utils.LogUtil;
import com.ttyc.health.utils.QiNiuUploadFileUtil;
import com.ttyc.health.views.zhihu.ZhihuPhotoUtil;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import org.json.JSONObject;

import java.util.ArrayList;

//发布界面
public class PublishActivity extends Activity {
    private Dialog mLoadingDialog;
    private static final int SELECT_PHOTO=1500;//选择照片
    private static  String token="WaHCsVXPDiSRTxhaQwE1e-7kktrw3g_s8TT3c6aw:U3uXEk2O0yuSpU_bmbpgVU_B9-0=:eyJzY29wZSI6InR0eWMtaGVhbHRoIiwiZGVhZGxpbmUiOjE1NTcxNTY3NTh9";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        Handler handler = new Handler();

    }

    public void selectPic(View view){
        ZhihuPhotoUtil.selectPhoto(this,SELECT_PHOTO, MimeType.ofImage());
    }
    public void publish(View view){

    }
    private void upLoadImg(ArrayList<String> pathlist){

        //ArrayList<String> pathlist=new ArrayList<>();

        QiNiuUploadFileUtil.upload(token, pathlist, new UpProgressHandler() {
            @Override
            public void progress(String s, double v) {
                LogUtil.e("SplashActivity", "s=" + s + "v=" + v);
            }
        }, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK&&requestCode==SELECT_PHOTO){
            ArrayList<String>  dataList=(ArrayList<String>) Matisse.obtainPathResult(data);
            upLoadImg(dataList);
        }
    }
}
