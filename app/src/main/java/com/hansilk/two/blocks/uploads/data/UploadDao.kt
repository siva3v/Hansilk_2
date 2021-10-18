package com.hansilk.two.blocks.uploads.data

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.hansilk.two.blocks.uploads.data.Product

@Dao
interface UploadDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(upload: Upload) : Long

    @Insert(onConflict = REPLACE)
    suspend fun insertList(objects: List<Upload>)

    @Update
    suspend fun update(upload: Upload)

    @Delete
    suspend fun delete(upload: Upload)

    @Query("SELECT * FROM uploads")
    fun getAll(): LiveData<List<Upload?>?>?

}