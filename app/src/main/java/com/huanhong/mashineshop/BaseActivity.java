package com.huanhong.mashineshop;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;

import java.util.List;


public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {

    protected final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

//        ScreenUtils.setTranslucentStatus(this, getResources().getColor(R.color.lib_main));

        if (getContentViewId() != 0) {
            setContentView(getContentViewId());
        }

        initView();
        initListener();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }



    protected boolean isStatusBar() {
        return true;
    }

    protected abstract int getContentViewId();

    protected void initView() {

    }

    protected void initData() {

    }

    protected void initListener() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        if (fragmentList == null) {
            return;
        }
        for (Fragment fragment : fragmentList) {
            if (fragment != null) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        if (fragmentList == null) {
            return;
        }
        for (Fragment fragment : fragmentList) {
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onClick(View v) {

    }

    @ColorInt
    public int getColorInt(@ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getColor(color);
        } else {
            return getResources().getColor(color);
        }
    }


    public void setOnClickListner(View ...view){
        for (int i = 0; i <view.length ; i++) {
            view[i].setOnClickListener(this);
        }
    }
}
