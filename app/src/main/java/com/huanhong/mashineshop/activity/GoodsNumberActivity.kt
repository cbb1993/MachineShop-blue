package com.huanhong.mashineshop.activity


import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.View
import android.widget.EditText
import com.huanhong.mashineshop.BaseActivity
import com.huanhong.mashineshop.R
import com.huanhong.mashineshop.views.ConfirmDialog
import com.huanhong.mashineshop.views.ConfirmPopwindow
import com.tcn.latticelpstkboard.control.TcnVendEventID
import com.tcn.latticelpstkboard.control.TcnVendEventResultID
import com.tcn.latticelpstkboard.control.TcnVendIF
import com.tcn.latticelpstkboard.control.VendEventInfo
import kotlinx.android.synthetic.main.activity_goods_number.*
import latticelpstkdemo.TcnUtilityUI


class GoodsNumberActivity : BaseActivity() {
    override fun getContentViewId(): Int {
        return R.layout.activity_goods_number
    }

    private var buffer = StringBuffer()
    private var open = false

    override fun initView() {
        super.initView()
        open = intent.getBooleanExtra("open",false)

        setOnClickListner(tv_0, tv_1, tv_2, tv_3, tv_4, tv_5, tv_6, tv_7, tv_8, tv_9, tv_delete, btn_confirm)

        iv_home.setOnClickListener {
//            startActivity(Intent(this@GoodsNumberActivity, StartActivity::class.java))
            finish()
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
            if (no in 1..65) {
                if(open){
                    TcnVendIF.getInstance().reqWriteDataShipTest(Integer.valueOf(buffer.toString()),Integer.valueOf(buffer.toString()))
                }else{
                    startActivity(Intent(this@GoodsNumberActivity, PhoneActivity::class.java)
                            .putExtra("box_no", buffer.toString()))
                }
            }else{
                ConfirmPopwindow(this,btn_confirm,R.mipmap.popup_font_empty,R.mipmap.popup_btn_selectagain,null)
            }
        } else {
            ConfirmPopwindow(this,btn_confirm,R.mipmap.popup_font_empty,R.mipmap.popup_btn_selectagain,null)
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
            if(buffer.length == 1){
                iv_input_2.visibility =View.GONE
            }else if(buffer.isEmpty()){
                iv_input_1.visibility =View.GONE
            }

        }else{ //添加
            if(buffer.length >=2){
                return
            }
            buffer.append(number)
            if(buffer.length==1){
                iv_input_1.setImageResource(res)
                iv_input_1.visibility =View.VISIBLE
            }else if(buffer.length==2){
                iv_input_2.setImageResource(res)
                iv_input_2.visibility =View.VISIBLE
            }
        }
    }
    override fun onResume() {
        super.onResume()
        TcnVendIF.getInstance().registerListener(m_vendListener)
    }

    override fun onPause() {
        super.onPause()
        TcnVendIF.getInstance().unregisterListener(m_vendListener)
    }
    private var m_vendListener: VendListener? = VendListener()

    private inner class VendListener : TcnVendIF.VendEventListener {
        override fun VendEvent(cEventInfo: VendEventInfo?) {
            if (null == cEventInfo) {
                TcnVendIF.getInstance().LoggerError(TAG, "VendListener cEventInfo is null")
                return
            }
            when (cEventInfo.m_iEventID) {
                TcnVendEventID.COMMAND_SYSTEM_BUSY -> TcnUtilityUI.getToast(this@GoodsNumberActivity, cEventInfo.m_lParam4, 20).show()

                TcnVendEventID.SERIAL_PORT_CONFIG_ERROR -> {
                }
                TcnVendEventID.SERIAL_PORT_SECURITY_ERROR -> {
                }
                TcnVendEventID.SERIAL_PORT_UNKNOWN_ERROR -> {
                }
                TcnVendEventID.COMMAND_SELECT_GOODS  //选货成功
                -> TcnUtilityUI.getToast(this@GoodsNumberActivity, "选货成功")
                TcnVendEventID.COMMAND_INVALID_SLOTNO -> TcnUtilityUI.getToast(this@GoodsNumberActivity, getString(R.string.ui_base_notify_invalid_slot), 22).show()
                TcnVendEventID.COMMAND_SOLD_OUT -> if (cEventInfo.m_lParam1 > 0) {
                    TcnUtilityUI.getToast(this@GoodsNumberActivity, getString(R.string.ui_base_aisle_name) + cEventInfo.m_lParam1 + getString(R.string.ui_base_notify_sold_out))
                } else {
                    TcnUtilityUI.getToast(this@GoodsNumberActivity, getString(R.string.ui_base_notify_sold_out))
                }
                TcnVendEventID.COMMAND_FAULT_SLOTNO -> TcnUtilityUI.getToast(this@GoodsNumberActivity, cEventInfo.m_lParam4)
                TcnVendEventID.COMMAND_SHIPPING    //正在出货
                -> {
                    TcnUtilityUI.getToast(this@GoodsNumberActivity, "正在出货")
                }

                TcnVendEventID.COMMAND_SHIPMENT_SUCCESS    //出货成功
                -> {
                    TcnUtilityUI.getToast(this@GoodsNumberActivity, "出货成功")
                }
                TcnVendEventID.COMMAND_SHIPMENT_FAILURE    //出货失败
                -> {
                    TcnUtilityUI.getToast(this@GoodsNumberActivity, "出货失败")
                }
                TcnVendEventID.PROMPT_INFO -> TcnUtilityUI.getToast(this@GoodsNumberActivity, cEventInfo.m_lParam4)

                TcnVendEventID.CMD_TEST_SLOT -> {
                    TcnVendIF.getInstance().LoggerDebug(TAG, "VendListener CMD_TEST_SLOT m_lParam3: " + cEventInfo.m_lParam3)

                }
                TcnVendEventID.CMD_SCAN_LIGHT_SET -> TcnUtilityUI.getToast(this@GoodsNumberActivity, getString(R.string.background_lat_set_success))
                TcnVendEventID.CMD_CABINETNO_OFF -> if (cEventInfo.m_lParam1 == TcnVendEventResultID.OFF_CLOSING) {

                } else if (cEventInfo.m_lParam1 == TcnVendEventResultID.OFF_SUCCESS) {

                } else {

                }
                TcnVendEventID.CMD_REQ_PERMISSION     //授权访问文件夹
                -> {
                    TcnUtilityUI.getToast(this@GoodsNumberActivity, "请选择确定")
                    ActivityCompat.requestPermissions(this@GoodsNumberActivity, TcnVendIF.getInstance().getPermission(cEventInfo.m_lParam4), 126)
                }
                else -> {
                }
            }//TcnUtilityUI.getToast(m_MainActivity, getString(R.string.error_seriport));

        }
    }
}


