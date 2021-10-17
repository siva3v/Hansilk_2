package com.hansilk.two.support.template

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface ModelDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(it: Model)

    @Update
    suspend fun update(it: Model)

    @Delete
    suspend fun delete(it: Model)

    @Query("SELECT * FROM models")
    fun getAll(): LiveData<List<Model?>?>?

}