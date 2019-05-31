package com.huanhong.mashineshop.activity

import android.content.Intent
import com.huanhong.mashineshop.BaseActivity
import com.huanhong.mashineshop.R
import com.huanhong.mashineshop.ReceiveEvent
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : BaseActivity() {
    override fun getContentViewId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        EventBus.getDefault().register(this)


//        webview.loadUrl("http://kouhong.8126f.com/mobile.php?s=/index/index/platid/1.html")
        webview.loadUrl("http://kouhong.8126f.com/mobile.php?s=/index/index/platid/1.html")


//        btn_1.setOnClickListener {
//            skipSuccess()
//        }
//
//        btn_2.setOnClickListener {
//            skipFailure()
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    /*
    * GameSuccess  | [string]  | 游戏成功
    GameFail  | [string]  | 游戏失败
    GameStart  | [string]  | 游戏开始
    * */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun receive(event: ReceiveEvent) {
        // 收到确认消息 跳转游戏
        if ("GameStart" == event.title) {
            skipSuccess(event.content)
        }
        if ("GameFail" == event.title) {
            skipFailure()
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this@MainActivity, GoodsActivity::class.java))
    }

    // 游戏成功
    fun skipSuccess(number: String) {
        startActivity(Intent(this@MainActivity, SuccessActivity::class.java))
        // todo  根据number打开箱子
        open(number)
    }

    // 游戏失败
    fun skipFailure() {
        startActivity(Intent(this@MainActivity, FailureActivity::class.java))
    }

    // 打开箱子
    private fun open(number: String) {
    }

}
