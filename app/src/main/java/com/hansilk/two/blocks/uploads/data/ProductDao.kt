package com.hansilk.two.blocks.uploads.data

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.hansilk.two.blocks.uploads.data.Product

@Dao
interface ProductDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(product: Product) : Long

    @Insert(onConflict = REPLACE)
    suspend fun insertList(objects: List<Product>)

    @Update
    suspend fun update(product: Product)

    @Delete
    suspend fun delete(product: Product)

    @Query("SELECT * FROM products")
    fun getAll(): LiveData<List<Product?>?>?

}