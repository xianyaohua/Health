package com.ttyc.health.utils;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.ttyc.health.config.Constants;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

public class QiNiuUploadFileUtil {
    private static String TAG=QiNiuUploadFileUtil.class.getSimpleName();
    public static UploadManager uploadManager;

    //补全七牛云下载链接
    public static String MixQiniuDownloadUrl(String key){
        return Constants.HTTP_QINIU_URL_DIFF+key;
    }
    public static  void upload(String uptoken, ArrayList<String> data, UpProgressHandler progressHandler,UpCompletionHandler upCompletionHandler){
        if(uploadManager==null){
            uploadManager=new UploadManager();
        }
        for(int i=0;i<data.size();i++){
            String[] expectKeys=data.get(i).split("/");
            String expectKey=expectKeys[expectKeys.length-1];//文件名称
            LogUtil.e(TAG,"expectKey="+expectKey);
            uploadManager.put(data.get(i), expectKey, uptoken, upCompletionHandler, new UploadOptions(null, null, true, progressHandler, null));
        }
    }
}
