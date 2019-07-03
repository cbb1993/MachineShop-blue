package com.huanhong.mashineshop.activity;

import android.content.Intent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;

import com.huanhong.mashineshop.BaseActivity;
import com.huanhong.mashineshop.R;
import com.huanhong.mashineshop.utils.SharedPreferencesUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SuccessActivity extends BaseActivity{
    @Override
    protected int getContentViewId() {
        return R.layout.activity_success;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SuccessActivity.this,StartActivity.class));
    }

    @Override
    protected void initView() {
        super.initView();
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(8000);
                    startActivity(new Intent(SuccessActivity.this,StartActivity.class));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
