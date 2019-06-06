package com.huanhong.mashineshop.activity


import android.content.Intent
import android.view.View
import android.widget.EditText
import com.huanhong.mashineshop.BaseActivity
import com.huanhong.mashineshop.R
import com.huanhong.mashineshop.views.ConfirmDialog
import com.tcn.latticelpstkboard.control.TcnVendIF
import kotlinx.android.synthetic.main.activity_goods_number.*


class GoodsNumberActivity : BaseActivity() {
    override fun getContentViewId(): Int {
        return R.layout.activity_goods_number
    }

    private var buffer = StringBuffer()
    private var open = false

    override fun initView() {
        super.initView()
        open = intent.getBooleanExtra("open",false)

        setEditTextNoSoftInput(ev_number)

        ev_number.isLongClickable = false

        setOnClickListner(tv_0, tv_1, tv_2, tv_3, tv_4, tv_5, tv_6, tv_7, tv_8, tv_9, tv_delete, btn_confirm)

        ev_number.requestFocus()

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

    private fun confirm() {
        if (buffer.isNotEmpty()) {
            val no = Integer.valueOf(buffer.toString())
            if (no in 1..64) {
                if(open){
                    TcnVendIF.getInstance().reqWriteDataShipTest(Integer.valueOf(buffer.toString()),Integer.valueOf(buffer.toString()))
                }else{
                    startActivity(Intent(this@GoodsNumberActivity, PhoneActivity::class.java)
                            .putExtra("box_no", buffer.toString()))
                }
            }else{
                ConfirmDialog(this,"죄송합니다. 선택하신 번호에 상품이 없습니다. 다른 번호를 선택해 주세요. ").show()
            }
        } else {
            ConfirmDialog(this,"죄송하지만 상자 번호를 입력하십시오").show()
        }
    }

    private fun changeNumber(number: Int) {
        var index = ev_number.selectionStart
        if (number != -1) { // 如果是输入
            buffer.insert(index, number)
            ev_number.setText(buffer.toString())
            ev_number.setSelection(index + 1)
        } else { // 删除
            if (index == 0) {
                return
            }
            buffer.delete(index - 1, index)
            ev_number.setText(buffer.toString())
            ev_number.setSelection(index - 1)
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


