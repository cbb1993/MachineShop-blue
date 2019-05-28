package com.huanhong.mashineshop.activity;

import android.content.Intent;

import com.huanhong.mashineshop.BaseActivity;
import com.huanhong.mashineshop.R;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class WaitForPhoneConfirmActivity extends BaseActivity{
    @Override
    protected int getContentViewId() {
        return R.layout.activity_wait_for_phone;
    }


    @Override
    protected void initView() {
        super.initView();
        /*
        * 假装收到通知
        * */
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    // 收到确认消息 跳转游戏
                    startActivity(new Intent(WaitForPhoneConfirmActivity.this,StartGameActivity.class));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receive(String msg){
        // 收到确认消息 跳转游戏
        startActivity(new Intent(WaitForPhoneConfirmActivity.this,StartGameActivity.class));
    }
}
