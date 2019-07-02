package com.huanhong.mashineshop.activity


import android.app.AlertDialog
import android.content.Intent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.alibaba.sdk.android.push.CommonCallback
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory
import com.huanhong.mashineshop.BaseActivity
import com.huanhong.mashineshop.R
import com.huanhong.mashineshop.net.HttpHelper
import com.huanhong.mashineshop.net.rx.RxSchedulers
import com.huanhong.mashineshop.net.rx.RxSubscriber
import com.huanhong.mashineshop.utils.SharedPreferencesUtils
import com.huanhong.mashineshop.views.ConfirmDialog
import com.huanhong.mashineshop.views.ConfirmPopwindow
import kotlinx.android.synthetic.main.activity_phone.*


class PhoneActivity : BaseActivity() {
    override fun getContentViewId(): Int {
        return R.layout.activity_phone
    }

    private var buffer = StringBuffer()
    private var box_no = ""

    override fun initView() {
        super.initView()

        box_no = intent.getStringExtra("box_no")


        setOnClickListner(tv_0, tv_1, tv_2, tv_3, tv_4, tv_5, tv_6, tv_7, tv_8, tv_9, tv_delete, btn_confirm)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_0 -> changeNumber(0)
            R.id.tv_1 -> changeNumber(1)
            R.id.tv_2 -> changeNumber(2)
            R.id.tv_3 -> changeNumber(3)
            R.id.tv_4 -> changeNumber(4)
            R.id.tv_5 -> changeNumber(5)
            R.id.tv_6 -> changeNumber(6)
            R.id.tv_7 -> changeNumber(7)
            R.id.tv_8 -> changeNumber(8)
            R.id.tv_9 -> changeNumber(9)
            R.id.tv_delete -> changeNumber(-1)
            R.id.btn_confirm -> confirm()
        }
    }

    // 输入手机号码后要拨打的URL
    // https://if.vetnim.com/kiosk/lipstic/getCellNo.do?cell_no=핸드폰번호&box_no=28
    // cell_no : 手机号码必须仅限号码 (ex : 01023459878) box_no : 口红挑战的选择框号码
    private fun confirm() {
        if (buffer.length == 8) {
            val map = HashMap<String, String>()
            map["cell_no"] = "010$buffer"
            map["box_no"] = "" + box_no
            HttpHelper.getInstance().createApiSerivce().callUrl(map)
                    .compose(RxSchedulers.io_main())
                    .subscribeWith(object : RxSubscriber<Any>() {
                        override fun onSuccess(t: Any?) {
                            PushServiceFactory.getCloudPushService().bindAccount("010" + buffer.toString(), object : CommonCallback {
                                override fun onSuccess(p0: String?) {
                                    SharedPreferencesUtils.addData("cell_no", "010" + buffer.toString())
                                    SharedPreferencesUtils.addData("box_no", box_no)
                                    startActivity(Intent(this@PhoneActivity, WaitForPhoneConfirmActivity::class.java))
                                }
                                override fun onFailed(p0: String?, p1: String?) {
                                }
                            })
                        }
                        override fun onFailure(msg: String?) {
                             ConfirmPopwindow(this@PhoneActivity,btn_confirm,R.mipmap.popup_font_empty,R.mipmap.popup_btn_selectagain,null)
                        }
                    })
        } else {
            ConfirmPopwindow(this@PhoneActivity,btn_confirm,R.mipmap.popup_font_empty,R.mipmap.popup_btn_selectagain,null)
        }
    }

    private fun changeNumber(number: Int) {
        when(number){
            0 -> {change(number,R.mipmap.num_font_0)}
            1 -> {change(number,R.mipmap.num_font_1)}
            2 -> {change(number,R.mipmap.num_font_2)}
            3 -> {change(number,R.mipmap.num_font_3)}
            4 -> {change(number,R.mipmap.num_font_4)}
            5 -> {change(number,R.mipmap.num_font_5)}
            6 -> {change(number,R.mipmap.num_font_6)}
            7 -> {change(number,R.mipmap.num_font_7)}
            8 -> {change(number,R.mipmap.num_font_8)}
            9 -> { change(number,R.mipmap.num_font_9)}
            -1 -> {
                change(number,0)
            }
        }
    }

    private fun change(number: Int,res :Int){
        if(number == -1){ //删除
            if(buffer.isNotEmpty()){
                buffer.deleteCharAt(buffer.length-1)
            }
            when(buffer.length){
                7 ->  iv_8.visibility =View.GONE
                6 ->  iv_7.visibility =View.GONE
                5 ->  iv_6.visibility =View.GONE
                4 ->  iv_5.visibility =View.GONE
                3 ->  iv_4.visibility =View.GONE
                2 ->  iv_3.visibility =View.GONE
                1 ->  iv_2.visibility =View.GONE
                0 ->  iv_1.visibility =View.GONE
            }

        }else{ //添加
            if(buffer.length >=8){
                return
            }
            buffer.append(number)

            when(buffer.length){
                1 ->  setImage(iv_1,res)
                2 ->  setImage(iv_2,res)
                3 ->  setImage(iv_3,res)
                4 ->  setImage(iv_4,res)
                5 ->  setImage(iv_5,res)
                6 ->  setImage(iv_6,res)
                7 ->  setImage(iv_7,res)
                8 ->  setImage(iv_8,res)
            }

        }
    }

    private fun setImage(view:ImageView,res: Int){
        view.setImageResource(res)
        view.visibility =View.VISIBLE
    }

}


