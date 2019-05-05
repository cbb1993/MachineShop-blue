package com.huanhong.mashineshop.activity

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.huanhong.mashineshop.BaseActivity
import com.huanhong.mashineshop.R
import com.huanhong.mashineshop.adapter.CommonAdapter
import com.huanhong.mashineshop.adapter.ViewHolder
import kotlinx.android.synthetic.main.activity_goods.*

class GoodsActivity:BaseActivity(){
    override fun getContentViewId(): Int {
       return R.layout.activity_goods
    }

    private val list = ArrayList<String>()
    private var selectIndex = -1


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        selectIndex  = -1
        if(recycler_goods.adapter!=null){
            recycler_goods.adapter!!.notifyDataSetChanged()
        }
    }

    override fun initView() {
        super.initView()
        list.clear()
        for(i in 1 ..24){
            list.add(""+i)
        }


        recycler_goods.layoutManager = GridLayoutManager(this,4)

        recycler_goods.adapter = object  :CommonAdapter<String>(this,list,R.layout.item_goods){



            override fun convert(holder: ViewHolder, t: MutableList<String>) {
                val ll_number = holder.getView<View>(R.id.ll_number)
                val tv_number = holder.getView<TextView>(R.id.tv_number)
                tv_number.text =t[holder.realPosition]

                ll_number.isActivated = selectIndex == holder.realPosition

                ll_number.setOnClickListener {
                    selectIndex = holder.realPosition
                    notifyDataSetChanged()
                }
            }
        }


        btn_confirm.setOnClickListener {
            if(selectIndex!=-1){
                startActivity(Intent(this@GoodsActivity,PhoneActivity::class.java)
                        .putExtra("box_no",selectIndex+1))
            }else{
                Toast.makeText(this,"请选择编号", Toast.LENGTH_SHORT).show()
            }

        }
    }
}