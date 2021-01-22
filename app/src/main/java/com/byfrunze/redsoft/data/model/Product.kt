package com.byfrunze.redsoft.data.model

data class Data(
    var data: Product
)

data class Product(
    var id: Int,
    var title: String,
    var short_description: String,
    var image_url: String,
    var amount: Int,
    var price: Float,
    var producer: String,
    var catergories: List<Categories>
)

data class Categories(
    var id: Int,
    var title: String,
    var parent_id: Int
)