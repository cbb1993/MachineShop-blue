package com.huanhong.mashineshop.activity

import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.util.Log
import com.alibaba.sdk.android.push.CommonCallback
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory
import com.huanhong.mashineshop.AppApplication
import com.huanhong.mashineshop.BaseActivity
import com.huanhong.mashineshop.HomeKeyBroadcastReceiver
import com.huanhong.mashineshop.views.PasswordDialog
import com.tcn.latticelpstkboard.control.TcnVendIF
import controller.VendService
import kotlinx.android.synthetic.main.activity_start.*
import com.huanhong.mashineshop.R
import com.huanhong.mashineshop.utils.SharedPreferencesUtils


class StartActivity : BaseActivity() {
    override fun getContentViewId(): Int {
        return R.layout.activity_start
    }

    override fun initView() {
        super.initView()
        registerHomeKeyReceiver()

        if (TcnVendIF.getInstance().isServiceRunning) {

        } else {
            startService(Intent(application, VendService::class.java))
        }

        TcnVendIF.getInstance().LoggerDebug(TAG, "MainAct onCreate()")


        iv_start.setOnClickListener {
            if(AppApplication.mediaPlayer!=null){
                AppApplication.mediaPlayer.reset()
                AppApplication.mediaPlayer.release()
            }
            AppApplication.mediaPlayer = MediaPlayer.create(applicationContext, R.raw.bg_30s)
            AppApplication.mediaPlayer.isLooping = true
            AppApplication.mediaPlayer.start()
            startActivity(Intent(this@StartActivity, GoodsNumberActivity::class.java))
        }

        view.setOnLongClickListener {
            PasswordDialog(this@StartActivity).show()

            false
        }


        if(SharedPreferencesUtils.readData("device_no")!=null&&SharedPreferencesUtils.readData("device_no")!=""){
            PushServiceFactory.getCloudPushService().addAlias(SharedPreferencesUtils.readData("device_no"), object : CommonCallback {
                override fun onSuccess(s: String) {}
                override fun onFailed(s: String, s1: String) {}
            })
        }
    }

    override fun onStart() {
        super.onStart()
        if(AppApplication.mediaPlayer!=null){
            AppApplication.mediaPlayer.pause()
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

    override fun onBackPressed() {
        System.exit(0)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterHomeKeyReceiver()
    }

    //自定义的广播接收者
    private var mHomeKeyReceiver : HomeKeyBroadcastReceiver?= null

    //注册广播接收者，监听Home键
    private fun registerHomeKeyReceiver() {
        mHomeKeyReceiver = HomeKeyBroadcastReceiver()
        val homeFilter = IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
        registerReceiver(mHomeKeyReceiver, homeFilter)
    }

    //取消监听广播接收者
    private fun unregisterHomeKeyReceiver() {
        if (null != mHomeKeyReceiver) {
           unregisterReceiver(mHomeKeyReceiver)
        }
    }

}