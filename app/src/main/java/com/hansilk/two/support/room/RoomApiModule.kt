package com.hansilk.two.support.room

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomApiModule constructor(private val context: Context) {

    @Provides
    @Singleton
    fun providesDatabase(): RoomDatabase {
        return Room.databaseBuilder(
            context,
            RoomDatabase::class.java,
            context.packageName)
            .build()
    }

}