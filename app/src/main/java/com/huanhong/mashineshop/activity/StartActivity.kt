package com.huanhong.mashineshop.activity

import android.content.Intent
import com.alibaba.sdk.android.push.CommonCallback
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory
import com.huanhong.mashineshop.AppApplication
import com.huanhong.mashineshop.BaseActivity
import com.huanhong.mashineshop.views.PasswordDialog
import com.tcn.latticelpstkboard.control.TcnVendIF
import controller.VendService
import kotlinx.android.synthetic.main.activity_start.*
import com.huanhong.mashineshop.AppApplication.mediaPlayer
import com.huanhong.mashineshop.R


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


        iv_start.setOnClickListener{
            startActivity(Intent(this@StartActivity,GoodsNumberActivity::class.java))
        }

        view.setOnLongClickListener {
            PasswordDialog(this@StartActivity).show()

            false
        }

        AppApplication.mediaPlayer.isLooping = true
    }


    override fun onResume() {
        super.onResume()
        mediaPlayer.start()

        unbind()
    }
    private fun unbind() {
        PushServiceFactory.getCloudPushService().unbindAccount(object : CommonCallback {
            override fun onSuccess(s: String) {
            }
            override fun onFailed(s: String, s1: String) {}
        })

    }

    override fun onBackPressed() {

    }
}