package com.huanhong.mashineshop.activity

import android.content.Intent
import com.bumptech.glide.Glide
import com.huanhong.mashineshop.BaseActivity
import com.huanhong.mashineshop.R
import kotlinx.android.synthetic.main.activity_game_intro.*

class GameIntroActivity:BaseActivity(){
    override fun getContentViewId(): Int {
       return R.layout.activity_game_intro
    }
    var index  = 1

    override fun initView() {
        super.initView()

        btn_next.setOnClickListener {
            if(index==3){
                startActivity(Intent(this@GameIntroActivity,StartGameActivity::class.java))
            }else{
                if(index==1){
                    Glide.with(this).load(R.mipmap.intro_bg2).into(iv_bg)
                    Glide.with(this).load(R.mipmap.step2_big).into(iv_step)
                }
                if(index==2){
                    Glide.with(this).load(R.mipmap.intro_bg3).into(iv_bg)
                    Glide.with(this).load(R.mipmap.step3_big).into(iv_step)
                }

                index ++
            }
        }
    }
}