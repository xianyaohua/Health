package com.ttyc.health.views;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ttyc.health.R;
import com.ttyc.health.config.Constants;
import com.ttyc.health.utils.http.HttpsTool;
import com.ttyc.health.views.utils.WeiboDialogUtils;

import org.json.JSONObject;
import org.w3c.dom.Text;

public class RegisterActivity extends BaseActivity {
    private Dialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        setTitle("注册");
    }

    public void onClickRegister(View view) {
        String phone = ((EditText) findViewById(R.id.phone_et)).getText().toString().trim();
        String pwd = ((EditText) findViewById(R.id.pwd_tv)).getText().toString().trim();
        String code = ((EditText) findViewById(R.id.code_tv)).getText().toString().trim();
        if (TextUtils.isEmpty(phone) || phone.length()!=11) {
            showToast("请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(code) || code.length()!=4) {
            showToast("请输入正确的验证码");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            showToast("请输入正确的密码");
            return;
        }else{
            if(pwd.length()<6){
                showToast("密码不能少于6位");
                return;
            }
            if(pwd.length()>20){
                showToast("密码不能大于20位");
                return;
            }
        }
        mLoadingDialog = WeiboDialogUtils.createLoadingDialog(this, "正在注册");
        mLoadingDialog.show();
        register(phone,pwd,code);
    }

    public void onClickCode(View view){
        String phone = ((EditText) findViewById(R.id.phone_et)).getText().toString().trim();
        if(TextUtils.isEmpty(phone)||phone.length()!=11){
            showToast("请输入正确的手机号");
            return;
        }
        mLoadingDialog = WeiboDialogUtils.createLoadingDialog(this, "获取验证码");
        mLoadingDialog.show();
        getCode(phone);
    }

    private void getCode(final String phone){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String urls = Constants.URL_GET_CHECK_CODE;
                String param = "phone=" + phone; // 请求参数
                String data; // 返回结果
                Message msg; // 信息类
                data = HttpsTool.getInstance().runGet(
                        urls, param, "");
                msg = new Message();
                msg.obj = data;
                msg.what = 1;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    private void register(final String phone, final String pwd, final String code) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String urls = Constants.URL_REGISTER;
                String param = "phone=" + phone + "&pwd=" + pwd+"&code="+code; // 请求参数
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

    private static String TAG=RegisterActivity.class.getSimpleName();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e(TAG,"result="+(String) msg.obj);
            mLoadingDialog.dismiss();
            if (msg.what == 0) {
                //mLoadingDialog.dismiss();
                try {
                    JSONObject jo = new JSONObject((String) msg.obj);
                    if (jo.getInt("code") == 0) {
                        showToast("注册成功");
                        RegisterActivity.this.finish();
                    } else {
                        showToast("注册失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(msg.what == 1){
                //mLoadingDialog.dismiss();
                try {
                    JSONObject jo = new JSONObject((String) msg.obj);
                    if (jo.getInt("code") == 0) {
                        showToast("获取验证码成功");
                        ((TextView)findViewById(R.id.getCode_tv)).setText("验证码已发送");
                        findViewById(R.id.getCode_tv).setClickable(false);
                    } else {
                        showToast("获取验证码失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
}

