package com.hansilk.two.support.dagger.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hansilk.two.blocks.uploads.main.UploadsViewModel
import com.hansilk.two.support.dagger.viewModelSet.DaggerViewModelFactory
import com.hansilk.two.support.dagger.viewModelSet.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: DaggerViewModelFactory?): ViewModelProvider.Factory?

    @Binds
    @IntoMap
    @ViewModelKey(UploadsViewModel::class)
    abstract fun provideMainViewModel(viewModel: UploadsViewModel): ViewModel?

    /*
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun provideMainViewModel(viewModel: MainViewModel): ViewModel?
    */



}