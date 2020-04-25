package com.ttyc.health.views;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ttyc.health.R;
import com.ttyc.health.app.MyApplication;
import com.ttyc.health.config.Constants;
import com.ttyc.health.utils.http.HttpsTool;
import com.ttyc.health.views.utils.WeiboDialogUtils;

import org.json.JSONObject;

public class LoginActivity extends BaseActivity {
    private Dialog mLoadingDialog;
    private String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getWindow().setStatusBarColor(getResources().getColor(R.color.MainBlue));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView(){
        setTitle("登录");
        setMoreMsg("注册");
    }
    public void onClickMore(View view){
        startActivity(new Intent(this,RegisterActivity.class));
    }
    //登录
    public void onClickLogin(View view){
        String phone=((EditText)findViewById(R.id.phone_num_tv)).getText().toString().trim();
        String pwd=((EditText)findViewById(R.id.pwd_tv)).getText().toString().trim();
        if(TextUtils.isEmpty(phone)||phone.length()!=11){
            showToast("请输入正确的手机号");
            return;
        }
        if(TextUtils.isEmpty(pwd)){
            showToast("请输入正确的密码");
            return;
        }
        this.phone=phone;
        mLoadingDialog= WeiboDialogUtils.createLoadingDialog(this,"正在登录");
        mLoadingDialog.show();
        login(phone,pwd);
    }
    private void login(final String phone,final String pwd) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String urls = Constants.URL_LOGIN;
                String param = "phone="+phone+"&pwd="+pwd; // 请求参数
                String data; // 返回结果
                Message msg; // 信息类
                data = HttpsTool.getInstance().runGet(
                        urls, param, "");
                msg = new Message();
                msg.obj = data;
                msg.what = 0;
                mHandler.sendMessage(msg);
            }
        }).start();
    }
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                mLoadingDialog.dismiss();
                try {
                    JSONObject jo=new JSONObject((String)msg.obj);
                    if(jo.getInt("code")==0&&jo.getString("msg").equals("登录成功")){
                        showToast("登录成功");
                        MyApplication.phone=phone;
                        startActivity(new Intent(LoginActivity.this,UserInfoActivity.class));
                        LoginActivity.this.finish();
                    }else {
                        showToast("登录失败");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    };
}
