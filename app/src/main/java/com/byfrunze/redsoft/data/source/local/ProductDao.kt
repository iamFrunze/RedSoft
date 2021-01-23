package com.byfrunze.redsoft.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.byfrunze.redsoft.data.model.Data
import com.byfrunze.redsoft.data.model.Product
import io.reactivex.*

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertToDB(productCacheEntity: ProductCacheEntity): Completable

    @Query("SELECT * FROM products")
    fun fetchProductsFormDB(): Flowable<List<ProductCacheEntity>>
}