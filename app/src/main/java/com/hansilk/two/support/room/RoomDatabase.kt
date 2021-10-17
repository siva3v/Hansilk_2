package com.hansilk.two.support.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hansilk.two.support.template.Model
import com.hansilk.two.support.template.ModelDao

@Database(entities = [Model::class], version = 1)
abstract class RoomDatabase : RoomDatabase(){

    abstract fun modelDao(): ModelDao

}