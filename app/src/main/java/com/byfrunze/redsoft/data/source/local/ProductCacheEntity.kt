package com.byfrunze.redsoft.data.source.local

import androidx.room.*
import com.byfrunze.redsoft.data.model.Categories

@Entity(tableName = "products")
data class ProductCacheEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "title")
    var title: String = "0",

    @ColumnInfo(name = "short_description")
    var short_description: String = "",

    @ColumnInfo(name = "image_url")
    var image_url: String = "",

    @ColumnInfo(name = "amount")
    var amount: Int = 0,

    @ColumnInfo(name = "price")
    var price: Float = 0f,

    @ColumnInfo(name = "producer")
    var producer: String = "",

    @Embedded(prefix = "category_")
    var catergories: List<Categories> = emptyList(),
)

