package com.huanhong.mashineshop.net

import io.reactivex.Observable
import retrofit2.http.*
import java.util.HashMap

interface ApiService {

    /**
     * 输入手机号码后要拨打的URL
     */
    @GET("getCellNo.do")
    fun callUrl(@QueryMap map: HashMap<String,String>): Observable<Any>

    /**
     * getScore.do?cell_no=핸드폰번호&score=8
     */
    @GET("getScore.do")
    fun getScore(@QueryMap map: HashMap<String,String>): Observable<Any>
}