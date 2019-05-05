package com.huanhong.mashineshop.activity

import android.content.Intent
import com.huanhong.mashineshop.BaseActivity
import com.huanhong.mashineshop.R
import com.huanhong.mashineshop.utils.SharedPreferencesUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun getContentViewId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        webview.loadUrl("http://kouhong.8126f.com/mobile.php?s=/index/index/platid/1.html")


        btn_1.setOnClickListener {
            skipSuccess()
        }

        btn_2.setOnClickListener {
            skipFailure()
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this@MainActivity,GoodsActivity::class.java))
    }

    // 游戏成功
    fun skipSuccess(){
        startActivity(Intent(this@MainActivity,SuccessActivity::class.java))
    }
    // 游戏失败
    fun skipFailure(){
        startActivity(Intent(this@MainActivity,FailureActivity::class.java))
    }

    // 上传游戏结果
    //https://if.vetnim.com/kiosk/lipstic/getScore.do?cell_no=핸드폰번호&score=80
    //cell_no : 在贩卖机上输入的手机号码 (ex : 01023459878)
    // score : 游戏结果及获取商品的框号码
    fun send(){
        val cell_no = SharedPreferencesUtils.readData("cell_no")  // 电话号码
        val box_no = SharedPreferencesUtils.readData("box_no")    // 商品编号

    }
}
