package com.byfrunze.redsoft.presentation.screens.search_product.models

sealed class SearchProductsEvent {
    object ScreenShow : SearchProductsEvent()
    class LoadMoreProducts(val term: String)  : SearchProductsEvent()
    class SearchProduct(val term: String) : SearchProductsEvent()
}