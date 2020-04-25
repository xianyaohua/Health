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
import com.ttyc.health.app.MyApplication;
import com.ttyc.health.config.Constants;
import com.ttyc.health.utils.http.HttpsTool;
import com.ttyc.health.views.utils.WeiboDialogUtils;

import org.json.JSONObject;

public class DealCoinActivity extends BaseActivity {
    private static String TAG=DealCoinActivity.class.getSimpleName();
    private static String HMST="HMST";
    private Dialog mLoadingDialog;
    private int coinCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_coin);
        coinCount=getIntent().getIntExtra("coin",0);
        initView();
    }

    private void initView(){
        setTitle("HMST交易");
    }
    public void deal(View view){
        final String uid = ((EditText) findViewById(R.id.uid_tv)).getText().toString().trim();
        final  String pwd = ((EditText) findViewById(R.id.pwd_tv)).getText().toString().trim();
        final  String coinCount = ((EditText) findViewById(R.id.coin_count_et)).getText().toString().trim();
        if (TextUtils.isEmpty(uid)) {
            showToast("请输入正确的uid号");
            return;
        }

        if (TextUtils.isEmpty(coinCount)) {
            showToast("请输入HMST交易数量");
            return;
        }
        int coinCountTemp=Integer.parseInt(coinCount);
        Log.e(TAG,"coin:"+this.coinCount+"deal coin:"+coinCountTemp);
        if(coinCountTemp<=0||coinCountTemp>this.coinCount){
            showToast("请输入正确的HMST数量");
            return;
        }

        if (TextUtils.isEmpty(pwd)||pwd.length()>20||pwd.length()<6) {
            showToast("请输入正确的密码");
            return;
        }
        mLoadingDialog = WeiboDialogUtils.createLoadingDialog(this, "正在交易");
        mLoadingDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String urls = Constants.URL_DEAL_COIN;
                String param = "phone=" + MyApplication.phone + "&pwd=" + pwd+"&uid="+uid+"&coin="+coinCount; // 请求参数
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

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                mLoadingDialog.dismiss();
                try {
                    JSONObject jo = new JSONObject((String) msg.obj);
                    if (jo.getInt("code") == 0) {
                        showToast("交易成功");
                        DealCoinActivity.this.finish();
                    } else {
                        showToast("交易失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
