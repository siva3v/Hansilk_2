package com.hansilk.two.support.retrofit

import android.content.Context
import com.hansilk.two.support.template.ConstData.Companion.REMOTE_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private var INSTANCE: Retrofit? = null

    fun getInstance(context: Context): Retrofit {
        if (INSTANCE == null) {
            synchronized(Retrofit::class) {
                buildInstance(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildInstance(context: Context) = Retrofit.Builder()
            .baseUrl(REMOTE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}