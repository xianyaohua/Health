package com.ttyc.health.views;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ttyc.health.R;

public class BaseActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setStatusBarColor(getResources().getColor(R.color.MainBlue));
        super.onCreate(savedInstanceState);
    }

    public void onClickBack(View view){
        BaseActivity.this.finish();
    }
    public void setTitle(String title){
        if(TextUtils.isEmpty(title)) return;
        ((TextView)findViewById(R.id.title_tv)).setText(title);
    }
    public void showToast(String text){
        if(TextUtils.isEmpty(text)) return;
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }
    public void setMoreMsg(String text){
        if(TextUtils.isEmpty(text)) return;
        ((TextView)findViewById(R.id.more)).setText(text);
    }
    public void onClickMore(View view){

    }
}
