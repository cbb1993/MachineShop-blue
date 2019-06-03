package com.huanhong.mashineshop.activity;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;

import com.huanhong.mashineshop.BaseActivity;
import com.huanhong.mashineshop.R;
import com.huanhong.mashineshop.ReceiveEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class WaitForPhoneConfirmActivity extends BaseActivity{
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

        findViewById(R.id.skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WaitForPhoneConfirmActivity.this,StartGameActivity.class));
            }
        });
    }
/*
* GameSuccess  | [string]  | 游戏成功
GameFail  | [string]  | 游戏失败
GameStart  | [string]  | 游戏开始
* */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receive(ReceiveEvent event){
        // 收到确认消息 跳转游戏
        if("GameStart".equals(event.title)){
            startActivity(new Intent(WaitForPhoneConfirmActivity.this,StartGameActivity.class));
        }
    }
}
