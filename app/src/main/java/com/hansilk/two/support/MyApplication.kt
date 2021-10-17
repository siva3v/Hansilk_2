package com.hansilk.two.support

import android.app.Application
import com.hansilk.two.support.dagger.component.AppComponent
import com.hansilk.two.support.dagger.component.DaggerAppComponent
import com.hansilk.two.support.dagger.modules.AppModule
import com.hansilk.two.support.retrofit.RetrofitApiModule
import com.hansilk.two.support.room.RoomApiModule
import com.hansilk.two.support.template.ConstData.Companion.REMOTE_BASE_URL

class MyApplication : Application() {
    var netComponent: AppComponent? = null
        private set

    override fun onCreate() {
        super.onCreate()

        initializeDagger()
    }

    private fun initializeDagger() {
        netComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .retrofitApiModule(RetrofitApiModule(REMOTE_BASE_URL))
            .roomApiModule(RoomApiModule(this))
            .build()
    }


}