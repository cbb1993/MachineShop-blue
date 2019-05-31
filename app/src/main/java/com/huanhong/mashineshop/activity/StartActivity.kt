package com.huanhong.mashineshop.activity

import android.content.Intent
import android.widget.MediaController
import com.bumptech.glide.Glide
import com.huanhong.mashineshop.BaseActivity
import com.huanhong.mashineshop.R
import com.tcn.latticelpstkboard.control.TcnVendIF
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity:BaseActivity(){
    override fun getContentViewId(): Int {
        return R.layout.activity_start
    }

    override fun initView() {
        super.initView()

        Glide.with(this).load(R.drawable.btn_gif).into(iv_)

        iv_.setOnClickListener{
            startActivity(Intent(this@StartActivity,GoodsNumberActivity::class.java))
        }

        btn_.setOnClickListener {
            if(ev_.length()!=0){
                TcnVendIF.getInstance().reqWriteDataShipTest(Integer.valueOf(ev_.text.toString()), Integer.valueOf(ev_.text.toString()))
            }
        }
    }
}