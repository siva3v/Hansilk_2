package com.hansilk.two.support.room

import android.content.Context
import androidx.room.Room

object RoomDatabaseBuilder {

    private var INSTANCE: RoomDatabase? = null

    fun getInstance(context: Context): RoomDatabase {
        if (INSTANCE == null) {
            synchronized(RoomDatabase::class) {
                buildInstance(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildInstance(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            RoomDatabase::class.java,
            context.packageName
        ).build()

}