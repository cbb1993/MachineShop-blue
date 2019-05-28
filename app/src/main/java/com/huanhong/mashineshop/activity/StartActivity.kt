package com.huanhong.mashineshop.activity

import android.content.Intent
import com.bumptech.glide.Glide
import com.huanhong.mashineshop.BaseActivity
import com.huanhong.mashineshop.R
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
    }
}