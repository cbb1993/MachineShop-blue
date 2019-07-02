package com.huanhong.mashineshop.activity;

import android.content.Intent;
import android.view.View;

import com.huanhong.mashineshop.BaseActivity;
import com.huanhong.mashineshop.R;

public class FailureActivity extends BaseActivity {
    @Override
    protected int getContentViewId() {
        return R.layout.activity_failure;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,StartActivity.class));
    }


    @Override
    protected void initView() {
        super.initView();

        findViewById(R.id.rl_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FailureActivity.this,StartActivity.class));
            }
        });


        findViewById(R.id.rl_again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FailureActivity.this,MainActivity.class));
            }
        });
    }
}
