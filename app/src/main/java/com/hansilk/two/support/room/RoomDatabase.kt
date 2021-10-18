package com.hansilk.two.support.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hansilk.two.blocks.common.comData.dra.Dra
import com.hansilk.two.blocks.common.comData.dra.DraDao
import com.hansilk.two.blocks.uploads.data.Product
import com.hansilk.two.blocks.uploads.data.ProductDao
import com.hansilk.two.blocks.uploads.data.Upload
import com.hansilk.two.blocks.uploads.data.UploadDao
import com.hansilk.two.support.template.Model
import com.hansilk.two.support.template.ModelDao

@Database(entities = [Model::class, Dra::class, Upload::class, Product::class], version = 1)
abstract class RoomDatabase : RoomDatabase(){

    abstract fun modelDao(): ModelDao

    abstract fun draDao(): DraDao

    abstract fun uploadDao(): UploadDao

    abstract fun productDao(): ProductDao

}