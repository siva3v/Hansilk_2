package com.hansilk.two.blocks.common.comData.dra

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.hansilk.two.blocks.common.comData.dra.Dra

@Dao
interface DraDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(dra: Dra)

    @Insert(onConflict = REPLACE)
    suspend fun insertList(objects: List<Dra?>?)

    @Update
    suspend fun update(dra: Dra)

    @Delete
    suspend fun delete(dra: Dra)

    @Query("DELETE FROM dra")
    suspend fun deleteAll()

    @Query("SELECT * FROM dra")
    fun getAll():LiveData<List<Dra?>?>?

    @Query("SELECT * FROM dra WHERE ba=:ba")
    fun getFilteredDra(ba:String):LiveData<List<Dra?>?>?

}