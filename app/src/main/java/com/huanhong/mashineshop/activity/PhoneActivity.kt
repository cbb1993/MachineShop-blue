package com.huanhong.mashineshop.activity


import android.app.AlertDialog
import android.content.Intent
import android.view.View
import android.widget.EditText
import com.alibaba.sdk.android.push.CommonCallback
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory
import com.huanhong.mashineshop.BaseActivity
import com.huanhong.mashineshop.R
import com.huanhong.mashineshop.net.HttpHelper
import com.huanhong.mashineshop.net.rx.RxSchedulers
import com.huanhong.mashineshop.net.rx.RxSubscriber
import com.huanhong.mashineshop.utils.SharedPreferencesUtils
import com.huanhong.mashineshop.views.ConfirmDialog
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

        setEditTextNoSoftInput(ev_1)
        setEditTextNoSoftInput(ev_2)

        ev_1.isLongClickable = false
        ev_2.isLongClickable = false

        setOnClickListner(tv_0, tv_1, tv_2, tv_3, tv_4, tv_5, tv_6, tv_7, tv_8, tv_9, tv_delete, btn_confirm)

        ev_1.requestFocus()

        back.setOnClickListener {
            onBackPressed()
        }
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
                            ConfirmDialog(this@PhoneActivity,R.string.alert_input_phone_error).show()
                        }
                    })
        } else {
            ConfirmDialog(this@PhoneActivity,R.string.alert_input_phone).show()
        }
    }

    private fun changeNumber(number: Int) {
        if (number != -1) { // 如果是输入
            if (buffer.isEmpty()) { // 字符为空 ev_1获取焦点
                ev_1.requestFocus()
            } else { //  如果焦点不在ev_1末尾 则不改变  其他让 ev_2 获取焦点
                if (buffer.length > 3) {
                    if (ev_1.isFocused && ev_1.selectionStart < 4) {

                    } else {
                        ev_2.requestFocus()
                    }
                }
            }
        } else { // 删除
            if (buffer.isEmpty()) {
                return
            }
            if (ev_2.isFocused) {
                if (ev_2.selectionStart == 0) {  // 如果位于 ev_2 开始 自动跳到 ev_1末尾
                    ev_1.requestFocus()
                    ev_1.setSelection(ev_1.length())
                }
            } else if (ev_1.isFocused && ev_1.selectionStart == 0) {
                return
            }
        }
        if (ev_1.isFocused) {   // 第一段输入框
            // 找出当前光标位置
            var index = ev_1.selectionStart
            if (number == -1 && index > 0) {
                // 删除
                buffer.delete(index - 1, index)
                setInput()
                ev_1.setSelection(index - 1)
            } else {
                // 输入
                // 长度不够 可以继续输入
                if (buffer.length < 8) {
                    var index = ev_1.selectionStart
                    buffer.insert(index, number)
                    setInput()
                    ev_1.setSelection(index + 1)
                }
            }

        } else if (ev_2.isFocused) {         // 第二段输入框
            // 找出当前光标位置
            var index = ev_2.selectionStart
            if (number == -1 && index > 0) {
                // 删除
                buffer.delete(3 + index, 4 + index)
                setInput()
                ev_2.setSelection(index - 1)
            } else {
                // 输入
                if (buffer.length < 4) { // 焦点在ev_2 但是长度不够 先填充ev_1
                    ev_1.requestFocus()
                    ev_1.setSelection(ev_1.length())
                    var index = ev_1.selectionStart
                    buffer.insert(index, number)
                    setInput()
                    ev_1.setSelection(index + 1)

                } else if (buffer.length < 8) {
                    var index = ev_2.selectionStart
                    buffer.insert(4 + index, number)
                    setInput()
                    ev_2.setSelection(index + 1)
                }
            }
        }
    }


    private fun setInput() {
        ev_1.setText("")
        ev_2.setText("")
        if (buffer.length < 5) {
            ev_1.setText(buffer.toString())
        } else if (buffer.length >= 5) {
            ev_1.setText(buffer.substring(0, 4))
            ev_2.setText(buffer.substring(4, buffer.length))
        }
    }

    // 利用反射 修改 showSoftInputOnFocus
    private fun setEditTextNoSoftInput(editText: EditText) {

        val editClass = editText.javaClass.superclass
        try {
            val method = editClass.getMethod("setShowSoftInputOnFocus", Boolean::class.javaPrimitiveType)
            method.isAccessible = true
            method.invoke(editText, false)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}


