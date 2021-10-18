package com.hansilk.two.blocks.common.comData.dra

import android.app.*
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.gson.Gson
import com.hansilk.two.MainActivity
import com.hansilk.two.support.MyApplication
import com.hansilk.two.support.retrofit.RetrofitApi
import com.hansilk.two.support.room.RoomDatabase
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class SyncDraService() : Service() {

    @Inject lateinit var retrofit: Retrofit
    @Inject lateinit var dbInstance: RoomDatabase

    override fun onCreate() {
        super.onCreate()

        (application as MyApplication).netComponent?.inject(this)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        syncDra()
        return START_NOT_STICKY
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
                    for (x in 0 until js.length()){
                        val dra: Dra = Gson().fromJson(js.getString(x), Dra::class.java)
                        //insertDra(dra)
                    }
                }
            }
            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                println("onFailure $query :: ${t.message}")
            }
        })
    }


    private val NOTIFICATION_ID = 123
    private val CHANNEL_ID = "123"

    private fun showNotification() {

        val notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        //val icon = BitmapFactory.decodeResource(resources, R.drawable.ic_logo_dark)
        val notification: Notification = NotificationCompat.Builder(this)
            .setContentTitle("TutorialsFace Music Player")
            .setTicker("TutorialsFace Music Player")
            .setContentText("My song")
            //.setSmallIcon(R.drawable.ic_logo_dark)
            //.setLargeIcon(icon)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) startMyOwnForeground()
        else startForeground(NOTIFICATION_ID, notification)

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun startMyOwnForeground() {

        //val intentMainLanding = Intent(this, AddProductsActivity::class.java)
        //val pendingIntent = PendingIntent.getActivity(this, 0, intentMainLanding, 0)

        val channelName = "My Background Service"
        val chan = NotificationChannel(
            CHANNEL_ID,
            channelName,
            NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val manager = (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
        manager.createNotificationChannel(chan)
        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, CHANNEL_ID)
        val notification: Notification = notificationBuilder.setOngoing(true)
            //.setSmallIcon(R.drawable.ic_logo_dark)
            .setContentTitle("Uploading Products")
            .setContentText("Click here to see status")
            //.setContentIntent(pendingIntent)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        startForeground(NOTIFICATION_ID, notification)
    }

}

