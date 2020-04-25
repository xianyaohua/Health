package com.ttyc.health.views.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ttyc.health.R;
import com.ttyc.health.views.utils.WeiboDialogUtils;

import static android.webkit.WebSettings.LOAD_NO_CACHE;

//首页，默认频道关注、推荐、慢病、常见病
public class IndexFragment extends Fragment {
    private View view;
    private WebView mWebView;
    private String url = "http://h5.eqxiu.com/ls/3yDH2jD2?eqrcode=1&share_level=1&from_user=5ed3fbb2-f775-4a1a-9794-f659ea758b03&from_id=8d824e4c-f5fe-4c61-a096-f41299b35208&share_time=1552150477851&from=singlemessage";
    private Dialog mWeiboDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view=inflater.inflate(R.layout.fragment_index,null,false);
        mWebView=(WebView) view.findViewById(R.id.wb);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadUrl(url);
    }

    @Override
    public void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    private void loadUrl(String url) {
        android.webkit.WebSettings webSettings = mWebView.getSettings();
        // 开启 DOM storage API 功能
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(LOAD_NO_CACHE);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);//关键点
        webSettings.setLoadWithOverviewMode(true);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                /*if (url!=null&&url.contains("/activity")){
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(DuiHuanActivity.this, DongTaiActivity.class);
                    String pid = uri.getQueryParameter("pid");
                    intent.putExtra("topicId",pid);
                    intent.putExtra("type","2");
                    startActivity(intent);
                    return true;
                }*/
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(null!=mWeiboDialog){
                    mWeiboDialog.dismiss();
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getContext(), "加载中");
            }
        });

        mWebView.loadUrl(url);
    }
}
