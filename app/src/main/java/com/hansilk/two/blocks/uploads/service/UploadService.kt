package com.hansilk.two.blocks.uploads.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.Nullable
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.hansilk.two.MainActivity
import com.hansilk.two.R
import com.hansilk.two.blocks.uploads.data.Upload
import com.hansilk.two.support.MyApplication
import com.hansilk.two.support.room.RoomDatabase
import com.hansilk.two.support.utils.serviceUtils.ServiceUtils
import retrofit2.Retrofit
import java.util.*
import javax.inject.Inject
import androidx.appcompat.app.AppCompatActivity

import android.widget.Toast
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.hansilk.two.blocks.common.comUtils.DatabaseUtils
import com.hansilk.two.blocks.common.comUtils.RetrofitUtils
import com.hansilk.two.support.retrofit.RetrofitApi
import com.hansilk.two.support.utils.fileUtils.FileIO
import com.hansilk.two.support.utils.fileUtils.FileSize
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import id.zelory.compressor.constraint.size
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import kotlin.collections.ArrayList
import android.app.NotificationManager





class UploadService : LifecycleService() {
    private val CHANNEL_ID = "UploadService"
    private val NOTIFICATION_ID = 1

    @Inject lateinit var db: RoomDatabase
    @Inject lateinit var retrofit: Retrofit

    private var isUploading = false

    companion object {
        fun checkAndStartService(context: Context){
            if (!ServiceUtils.isThisServiceRunning(context, UploadService::class.java)){
                startService(context,"")
            }
        }
        fun startService(context: Context, message: String) {
            val startIntent = Intent(context, UploadService::class.java)
            startIntent.putExtra("inputExtra", message)
            ContextCompat.startForegroundService(context, startIntent)
        }
        fun stopService(context: Context) {
            val stopIntent = Intent(context, UploadService::class.java)
            context.stopService(stopIntent)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        if (!isUploading) startNext()

        val text = intent?.getStringExtra("inputExtra").toString()
        showNotification(text)

        //startNext()
        //stopSelf();

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }

    //lateinit var notification: Notification
    private fun showNotification(text: String){
        //val input = intent?.getStringExtra("inputExtra")
        createNotificationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Uploading Products")
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_logo_dark)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }

    private fun cancelNotification(id: Int){
        val notifManager: NotificationManager = this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notifManager.cancel(id)
    }

    override fun onCreate() {
        super.onCreate()

        (application as MyApplication).netComponent?.inject(this)
    }

    private fun startNext(){
        lifecycleScope.launch {
            coroutineScope {
                val uploads = db.uploadDao().getOne()
                val remaining = db.uploadDao().getCount()

                if (uploads.isNotEmpty()){
                    processFile(uploads[0], remaining)
                } else {
                    //Toast.makeText(this, "Uploads Completed", Toast.LENGTH_LONG).show()
                    stopSelf()
                }
            }
        }
    }

    private fun processFile(upload: Upload, remaining: Int){

        showNotification("$remaining remaining")

        isUploading = true
        val originalFile = File(upload.path)

        val context = this
        lifecycleScope.launch {
            coroutineScope {
                val compressedFile = Compressor.compress(context, originalFile) {
                    resolution(1280, 720)
                    quality(80)
                    size(2)
                }
                uploadFile(upload, compressedFile)
                println("Size :: originalFile ${FileSize.fileSizeInKB(originalFile)}")
                println("Size :: compressedFile ${FileSize.fileSizeInKB(compressedFile)}")
            }
        }

    }

    private fun uploadFile(upload: Upload, file: File){

        val name = "${upload.ab}.jpg"

        val payload = MultipartBody.Part.createFormData(
            "uploaded_file", name,
            RequestBody.create(MediaType.parse("multipart/form-data"), file)
        )

        val api = retrofit.create(RetrofitApi::class.java)
        api.uploadToRha(payload)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                if (response.isSuccessful && response.body()!=null) {
                    upload.ab?.let { updateProductInReo(it) }
                    updateUploadLocally(upload)
                    isUploading = false
                    cancelNotification(NOTIFICATION_ID)
                }
            }
            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                uploadFile(upload, file)
            }
        })
    }

    fun updateUploadLocally(upload: Upload){
        upload.state = 1
        lifecycleScope.launch {
            db.uploadDao().update(upload)
            startNext()
        }
    }

    fun updateProductInReo(ab: Long){
        val query = RetrofitUtils.getUpdateQueryFromTableRows("reo","ac=3","ab=$ab")
        val api = retrofit.create(RetrofitApi::class.java)
        api.sset(query)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                if (response.isSuccessful && response.body()!=null) {

                }
            }
            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                println("onFailure $query :: ${t.message}")
                updateProductInReo(ab)
            }
        })
    }


}