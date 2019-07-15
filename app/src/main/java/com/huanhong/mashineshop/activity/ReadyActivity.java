package com.huanhong.mashineshop.activity;

import android.content.Intent;
import android.view.View;

import com.huanhong.mashineshop.AppApplication;
import com.huanhong.mashineshop.BaseActivity;
import com.huanhong.mashineshop.R;

public class ReadyActivity extends BaseActivity{
    @Override
    protected int getContentViewId() {
        return R.layout.activity_ready;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ReadyActivity.this,StartActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppApplication.mediaPlayer.start();
    }

    @Override
    protected void initView() {
        findViewById(R.id.iv_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReadyActivity.this,MainActivity.class));
            }
        });
    }
}
