package com.ttyc.health.views;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.ttyc.health.R;
import com.ttyc.health.app.MyApplication;
import com.ttyc.health.config.Constants;
import com.ttyc.health.utils.http.HttpsTool;
import com.ttyc.health.views.utils.WeiboDialogUtils;

import org.json.JSONObject;

public class UserInfoActivity extends BaseActivity {
    private Dialog mLoadingDialog;
    private int coinCount;
    private int cards;
    private SwipeRefreshLayout refreshRl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        coinCount=0;
        cards=0;
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    private void refreshData(){
        getUserInfo(MyApplication.phone);
    }

    private void getUserInfo(final String phone){
        mLoadingDialog = WeiboDialogUtils.createLoadingDialog(this, "");
        mLoadingDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String urls = Constants.URL_GET_USER_INFO;
                String param = "phone=" + phone; // 请求参数
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
    public void onClickDeal(View view){
        Intent intent=new Intent(this,DealCoinActivity.class);
        intent.putExtra("coin",coinCount);
        startActivity(intent);
    }

    public void onClickDealCards(View view){
        Intent intent=new Intent(this,DealCardsActivity.class);
        intent.putExtra("cards",cards);
        startActivity(intent);
    }

    private void initView(){
        setTitle("用户信息");
        setMoreMsg("设置");
        ((TextView)findViewById(R.id.phone_tv)).setText("手机号："+ MyApplication.phone);
        refreshRl=(SwipeRefreshLayout) findViewById(R.id.refresh);
        refreshRl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshRl.setRefreshing(true);
                refreshData();
            }
        });
    }
    public void onClickMore(View view){
        //startActivity(new Intent(this,SettingActivity.class));
    }

    private void refreshView(int coin,int cards,int uid){
        coinCount=coin;
        this.cards=cards;
        ((TextView)findViewById(R.id.uid_tv)).setText("uid："+uid);
        ((TextView)findViewById(R.id.coin_tv)).setText(coin+"个");
        ((TextView)findViewById(R.id.cards_tv)).setText(cards+"张");
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            refreshRl.setRefreshing(false);
            if (msg.what == 0) {
                mLoadingDialog.dismiss();
                try {
                    JSONObject jo = new JSONObject((String) msg.obj);
                    if (jo.getInt("code") == 0) {
                        //showToast("刷新数据成功");
                        JSONObject data=jo.getJSONObject("data");
                        refreshView(data.getInt("coin"),data.getInt("cards"),data.getInt("id"));
                    } else {
                        //showToast("刷新数据失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (isTaskRoot()) {
                moveTaskToBack(false);
                return true;
            } else {
                return super.onKeyDown(keyCode, event);

            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
