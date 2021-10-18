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

    @Query("SELECT * FROM uploads WHERE state=0 ORDER BY id ASC")
    fun getAll(): LiveData<List<Upload?>?>?

    @Query("SELECT * FROM uploads WHERE state=0 LIMIT 1")
    suspend fun getOne(): List<Upload>

    @Query("SELECT COUNT(*) FROM uploads WHERE state=0")
    suspend fun getCount(): Int

}