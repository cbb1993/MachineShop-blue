package com.huanhong.mashineshop.activity

import android.content.Intent
import com.alibaba.sdk.android.push.CommonCallback
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory
import com.bumptech.glide.Glide
import com.huanhong.mashineshop.BaseActivity
import com.huanhong.mashineshop.R
import com.tcn.latticelpstkboard.control.TcnVendIF
import controller.VendService
import kotlinx.android.synthetic.main.activity_start.*
import latticelpstkdemo.MainAct

class StartActivity:BaseActivity(){
    override fun getContentViewId(): Int {
        return R.layout.activity_start
    }

    override fun initView() {
        super.initView()

        if (TcnVendIF.getInstance().isServiceRunning) {

        } else {
            startService(Intent(application, VendService::class.java))
        }

        TcnVendIF.getInstance().LoggerDebug(TAG, "MainAct onCreate()")

        Glide.with(this).load(R.drawable.btn_gif).into(iv_)

        iv_.setOnClickListener{
            startActivity(Intent(this@StartActivity,GoodsNumberActivity::class.java))
        }
    }


    override fun onResume() {
        super.onResume()
        unbind()
    }
    private fun unbind() {
        PushServiceFactory.getCloudPushService().unbindAccount(object : CommonCallback {
            override fun onSuccess(s: String) {
            }
            override fun onFailed(s: String, s1: String) {}
        })

    }
}