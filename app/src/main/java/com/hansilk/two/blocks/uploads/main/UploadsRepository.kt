package com.hansilk.two.blocks.uploads.main

import androidx.lifecycle.LiveData
import com.hansilk.two.blocks.common.comData.dra.Dra
import com.hansilk.two.blocks.uploads.data.Upload
import com.hansilk.two.support.room.RoomDatabase

class UploadsRepository(private val db: RoomDatabase) {

    var allDra: LiveData<List<Dra?>?>? = null
    var allUpload: LiveData<List<Upload?>?>? = null

    init {
        allDra = db.draDao().getAll()
        allUpload = db.uploadDao().getAll()
    }

    suspend fun insertDra (dra: Dra) = db.draDao().insert(dra)

    suspend fun deleteAllDra () = db.draDao().deleteAll()

    fun getFilteredDra(ba: String): LiveData<List<Dra?>?>? {
        return db.draDao().getFilteredDra(ba)
    }


    suspend fun insertUpload (upload: Upload) = db.uploadDao().insert(upload)


}