package com.huanhong.mashineshop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;


/**
 * Created by 坎坎 on 2018/5/4.
 */

public class X5WebView extends WebView {
    private WebViewClient client = new WebViewClient() {
        // 防止加载网页时调起系统浏览器
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };

    public X5WebView(Context arg0) {
        super(arg0);
        setBackgroundColor(85621);
        this.setWebViewClient(client);
        // this.setWebChromeClient(chromeClient);
        // WebStorage webStorage = WebStorage.getInstance();
        initWebViewSettings(getSettings());
        this.getView().setClickable(true);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public X5WebView(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);

        this.setWebViewClient(client);
        // this.setWebChromeClient(chromeClient);
        // WebStorage webStorage = WebStorage.getInstance();
        initWebViewSettings(getSettings());
        this.getView().setClickable(true);
    }


    public void initWebViewSettings(WebSettings webSetting) {
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        webSetting.setSupportZoom(true);
//        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
//        webSetting.setSupportMultipleWindows(true);
        webSetting.setAppCacheEnabled(true);
         webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webSetting.setDomStorageEnabled(true); // 开启 DOM storage API 功能
        webSetting.setDatabaseEnabled(true);   //开启 database storage API 功能
        webSetting.setAppCacheEnabled(true);//开启 Application Caches 功能

//        webSetting.setAllowFileAccess(true);
//        webSetting.setAllowFileAccessFromFileURLs(true);
//        webSetting.setAllowUniversalAccessFromFileURLs(true);

    }

}
