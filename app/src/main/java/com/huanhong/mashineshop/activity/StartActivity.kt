package com.huanhong.mashineshop.activity

import android.content.Intent
import android.support.v4.app.ActivityCompat
import com.bumptech.glide.Glide
import com.huanhong.mashineshop.BaseActivity
import com.huanhong.mashineshop.R
import com.tcn.latticelpstkboard.control.TcnVendEventID
import com.tcn.latticelpstkboard.control.TcnVendEventResultID
import com.tcn.latticelpstkboard.control.TcnVendIF
import com.tcn.latticelpstkboard.control.VendEventInfo
import controller.VendService
import kotlinx.android.synthetic.main.activity_start.*
import latticelpstkdemo.MainAct
import latticelpstkdemo.TcnUtilityUI

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
            startActivity(Intent(this@StartActivity,MainAct::class.java))
        }

        btn_.setOnClickListener {
            if(ev_.length()!=0){
                TcnVendIF.getInstance().reqWriteDataShipTest(Integer.valueOf(ev_.text.toString()), Integer.valueOf(ev_.text.toString()))
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
                TcnVendEventID.COMMAND_SYSTEM_BUSY -> TcnUtilityUI.getToast(this@StartActivity, cEventInfo.m_lParam4, 20).show()

                TcnVendEventID.SERIAL_PORT_CONFIG_ERROR -> {
                    TcnUtilityUI.getToast(this@StartActivity, "打开串口错误")
                }
                TcnVendEventID.SERIAL_PORT_SECURITY_ERROR -> {
                    TcnUtilityUI.getToast(this@StartActivity, "打开串口错误")
                }
                TcnVendEventID.SERIAL_PORT_UNKNOWN_ERROR -> {
                    TcnUtilityUI.getToast(this@StartActivity, "打开串口错误")
                }
                TcnVendEventID.COMMAND_SELECT_GOODS  //选货成功
                -> TcnUtilityUI.getToast(this@StartActivity, "选货成功")
                TcnVendEventID.COMMAND_INVALID_SLOTNO -> TcnUtilityUI.getToast(this@StartActivity, getString(R.string.ui_base_notify_invalid_slot), 22).show()
                TcnVendEventID.COMMAND_SOLD_OUT -> if (cEventInfo.m_lParam1 > 0) {
                    TcnUtilityUI.getToast(this@StartActivity, getString(R.string.ui_base_aisle_name) + cEventInfo.m_lParam1 + getString(R.string.ui_base_notify_sold_out))
                } else {
                    TcnUtilityUI.getToast(this@StartActivity, getString(R.string.ui_base_notify_sold_out))
                }
                TcnVendEventID.COMMAND_FAULT_SLOTNO -> TcnUtilityUI.getToast(this@StartActivity, cEventInfo.m_lParam4)
                TcnVendEventID.COMMAND_SHIPPING    //正在出货
                -> {
                    TcnUtilityUI.getToast(this@StartActivity, "正在出货")
                }

                TcnVendEventID.COMMAND_SHIPMENT_SUCCESS    //出货成功
                -> {
                    TcnUtilityUI.getToast(this@StartActivity, "出货成功")
                }
                TcnVendEventID.COMMAND_SHIPMENT_FAILURE    //出货失败
                -> {
                    TcnUtilityUI.getToast(this@StartActivity, "出货失败")
                }
                TcnVendEventID.PROMPT_INFO -> TcnUtilityUI.getToast(this@StartActivity, cEventInfo.m_lParam4)

                TcnVendEventID.CMD_TEST_SLOT -> {
                    TcnVendIF.getInstance().LoggerDebug(TAG, "VendListener CMD_TEST_SLOT m_lParam3: " + cEventInfo.m_lParam3)

                }
                TcnVendEventID.CMD_SCAN_LIGHT_SET -> TcnUtilityUI.getToast(this@StartActivity, getString(R.string.background_lat_set_success))
                TcnVendEventID.CMD_CABINETNO_OFF -> if (cEventInfo.m_lParam1 == TcnVendEventResultID.OFF_CLOSING) {

                } else if (cEventInfo.m_lParam1 == TcnVendEventResultID.OFF_SUCCESS) {

                } else {

                }
                TcnVendEventID.CMD_REQ_PERMISSION     //授权访问文件夹
                -> {
                    TcnUtilityUI.getToast(this@StartActivity, "请选择确定")
                    ActivityCompat.requestPermissions(this@StartActivity, TcnVendIF.getInstance().getPermission(cEventInfo.m_lParam4), 126)
                }
                else -> {
                }
            }

        }
    }

}