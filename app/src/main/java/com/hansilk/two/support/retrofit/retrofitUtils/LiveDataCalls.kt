package com.hansilk.two.support.retrofit.retrofitUtils

import androidx.lifecycle.MutableLiveData
import com.hansilk.two.support.retrofit.RetrofitApi
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class LiveDataCalls {
    companion object {

        fun livaDataCallTest(retrofit: Retrofit, query: String) : MutableLiveData<JSONArray> {
            val liveJs = MutableLiveData<JSONArray>()

            val api = retrofit.create(RetrofitApi::class.java)
            api.sget(query)?.enqueue(object :
                Callback<ResponseBody?> {
                override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                    if (response.isSuccessful && response.body()!=null) {
                        val got = response.body()!!.string()
                        val js = JSONArray(got)
                        liveJs.value = js
                    }
                }
                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    println("onFailure $query :: ${t.message}")
                }
            })

            return liveJs
        }
    }
}