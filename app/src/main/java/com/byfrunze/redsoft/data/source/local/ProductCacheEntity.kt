package com.byfrunze.redsoft.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductCacheEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "title")
    var title: Int,

    @ColumnInfo(name = "short_description")
    var short_description: String,

    @ColumnInfo(name = "image_url")
    var image_url: String,

    @ColumnInfo(name = "amount")
    var amount: Int,

    @ColumnInfo(name = "price")
    var price: Float,

    @ColumnInfo(name = "producer")
    var producer: Int,

    @ColumnInfo(name = "categories")
    var catergories: String,
)

@Entity(tableName = "categories")
data class CategoriesCacheEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "parent_id")
    var parent_id: Int,
)