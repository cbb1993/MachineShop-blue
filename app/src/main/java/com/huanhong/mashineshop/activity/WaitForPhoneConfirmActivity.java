package com.huanhong.mashineshop.activity;

import android.content.Intent;

import com.huanhong.mashineshop.BaseActivity;
import com.huanhong.mashineshop.R;

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
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receive(String title){
        // 收到确认消息 跳转游戏
        if("startGame".equals(title)){
            startActivity(new Intent(WaitForPhoneConfirmActivity.this,StartGameActivity.class));
        }
    }
}
