package com.huanhong.mashineshop.activity;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;

import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.huanhong.mashineshop.BaseActivity;
import com.huanhong.mashineshop.R;
import com.huanhong.mashineshop.ReceiveEvent;
import com.huanhong.mashineshop.utils.SharedPreferencesUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class WaitForPhoneConfirmActivity extends BaseActivity {
    @Override
    protected int getContentViewId() {
        return R.layout.activity_wait_for_phone;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initView() {
        super.initView();
        EventBus.getDefault().register(this);

        boolean b =SharedPreferencesUtils.readBooleanData("skip",false);
        View skip = findViewById(R.id.skip);
        if(b){
            skip.setVisibility(View.VISIBLE);
        }
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WaitForPhoneConfirmActivity.this, ReadyActivity.class));
            }
        });

        findViewById(R.id.iv_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(WaitForPhoneConfirmActivity.this, StartActivity.class));
                finish();
            }
        });
    }

    /*
    * GameSuccess  | [string]  | 游戏成功
    GameFail  | [string]  | 游戏失败
    GameStart  | [string]  | 游戏开始
    * */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receive(ReceiveEvent event) {
        // 收到确认消息 跳转游戏
        if ("GameStart".equals(event.title)) {
            startActivity(new Intent(WaitForPhoneConfirmActivity.this, ReadyActivity.class));
            unbind();
        }
    }


    private void unbind() {
        PushServiceFactory.getCloudPushService().unbindAccount(new CommonCallback() {
            @Override
            public void onSuccess(String s) {
            }
            @Override
            public void onFailed(String s, String s1) {

            }
        });

    }
}
