package com.hansilk.two.support.dagger.component

import com.hansilk.two.MainActivity
import com.hansilk.two.blocks.common.comData.dra.SyncDraService
import com.hansilk.two.blocks.uploads.frags.UploadsFragment
import com.hansilk.two.blocks.uploads.main.UploadsActivity
import com.hansilk.two.blocks.uploads.main.UploadsViewModel
import com.hansilk.two.support.dagger.modules.AppModule
import com.hansilk.two.support.dagger.modules.ViewModelModule
import com.hansilk.two.support.retrofit.RetrofitApiModule
import com.hansilk.two.support.room.RoomApiModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RetrofitApiModule::class, RoomApiModule::class, ViewModelModule::class])
interface AppComponent {

    //Activities
    fun inject(activity: MainActivity?)
    fun inject(activity: UploadsActivity?)

    //Services
    fun inject(service: SyncDraService?)

    //Fragments
    fun inject(fragment: UploadsFragment?)

    //VieModels
    fun inject(viewModel: UploadsViewModel?)

}