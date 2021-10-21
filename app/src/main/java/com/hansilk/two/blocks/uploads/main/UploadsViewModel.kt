package com.hansilk.two.blocks.uploads.main

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.hansilk.two.blocks.common.comData.dra.Dra
import com.hansilk.two.blocks.common.comUtils.DatabaseUtils
import com.hansilk.two.blocks.common.comUtils.RetrofitUtils
import com.hansilk.two.blocks.uploads.data.Upload
import com.hansilk.two.support.MyApplication
import com.hansilk.two.support.retrofit.RetrofitApi
import com.hansilk.two.support.room.RoomDatabase
import com.hansilk.two.support.utils.fileUtils.FileBitmap
import com.hansilk.two.support.utils.imageUtils.ImageColors
import com.hansilk.two.support.utils.listUtils.ListUtils
import com.hansilk.two.support.utils.uidUtils.UidUtils
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class UploadsViewModel @Inject constructor(application: Application): AndroidViewModel(application) {

    @Inject lateinit var retrofit: Retrofit
    @Inject lateinit var dbInstance: RoomDatabase

    private var repo: UploadsRepository

    var uploadFragStep = MutableLiveData<Int>()
    var uploadFragModel = JSONObject()
    var uploadAllDraSet = JSONObject()
    lateinit var collGridImage: Bitmap

    var uploadFragAttrSet = arrayOf("bb", "cd", "ce", "cf", "ci", "cn")

    init {
        (application as MyApplication).netComponent?.inject(this)

        repo = UploadsRepository(dbInstance)

    }

    fun syncDra(){
        val query = "SELECT aa,ac,ba,bb,bc,bd,be FROM `dra`"
        val api = retrofit.create(RetrofitApi::class.java)
        api.sget(query)?.enqueue(object :
            Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                if (response.isSuccessful && response.body()!=null) {
                    val got = response.body()!!.string()
                    val js = JSONArray(got)

                    deleteAllDra()

                    for (x in 0 until js.length()){
                        val dra: Dra = Gson().fromJson(js.getString(x), Dra::class.java)
                        insertDra(dra)
                    }
                }
            }
            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                println("onFailure $query :: ${t.message}")
            }
        })
    }

    private fun insertDra(dra: Dra){
        viewModelScope.launch {
            repo.insertDra(dra)
        }
    }

    private fun deleteAllDra(){
        viewModelScope.launch {
            repo.deleteAllDra()
        }
    }

    fun getAllDra() = repo.allDra

    fun getFilteredDra(ba: String) = repo.getFilteredDra(ba)


    fun addProductsToQueue(cu : String){
        viewModelScope.launch {

            val jj = uploadFragModel
            uploadFragModel = JSONObject()

            val pathString = if (jj.has("path")) jj.getString("path") else ""
            val list = ListUtils.StringToArrayListWith___(pathString)

            jj.put("bc", 0)
            jj.put("cu", cu)
            jj.put("iy", list.size)

            for (path in list) {

                val ab = UidUtils.getUidMillies()

                val colorList = ImageColors.getPossibleColorsFromBitmap(FileBitmap.getBitmapFromPath(path))
                val cg = ListUtils.ArrayListToString(colorList)

                jj.put("ab", ab)
                jj.put("cg", cg)

                uploadProductsToDb(jj)

                val upload = Upload()
                upload.path = path
                upload.state = 0
                upload.ab = ab
                upload.bc = 1
                insertUpload(upload)

            }

        }
    }

    fun uploadProductsToDb(jj: JSONObject?){
        val query = jj?.let {
            RetrofitUtils.getInsertQueryFromJSONObj(DatabaseUtils.filterOutExtrasReo(it), "reo")
        }
        val api = retrofit.create(RetrofitApi::class.java)
        api.sset(query)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                if (response.isSuccessful && response.body()!=null) {

                }
            }
            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                uploadProductsToDb(jj)
            }
        })
    }

    fun getAllUpload() = repo.allUpload

    fun insertUpload (upload: Upload){
        viewModelScope.launch {
            repo.insertUpload(upload)
        }
    }

    fun updateUpload (upload: Upload){
        viewModelScope.launch {
            repo.updateUpload(upload)
        }
    }

    fun deleteUpload (upload: Upload){
        viewModelScope.launch {
            repo.deleteUpload(upload)
        }
    }


}