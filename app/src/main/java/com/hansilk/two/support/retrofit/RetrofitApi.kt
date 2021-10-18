package com.hansilk.two.support.retrofit

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface RetrofitApi {

    /*
    @FormUrlEncoded
    @POST("cpa.php")
    operator fun get(@Field("pac") string: String?): Call<List<Music?>?>?
    */

    @FormUrlEncoded
    @POST("sget.php")
    fun sget(@Field("pac") str: String?): Call<ResponseBody?>?

    @FormUrlEncoded
    @POST("sset.php")
    fun sset(@Field("pac") str: String?): Call<ResponseBody?>?

    @POST("muha.php")
    @Multipart
    fun uploadToRha(@Part part: MultipartBody.Part?): Call<ResponseBody?>?

}