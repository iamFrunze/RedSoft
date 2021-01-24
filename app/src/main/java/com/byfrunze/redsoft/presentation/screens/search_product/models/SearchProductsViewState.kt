package com.byfrunze.redsoft.presentation.screens.search_product.models

import com.byfrunze.redsoft.data.model.Data
import com.byfrunze.redsoft.data.model.Product
import java.util.*

data class ProductsSearchViewState(
    val fetchStatus: FetchStatus,
    val products: List<Product>
)

sealed class FetchStatus {
    object Loading : FetchStatus()
    object Success : FetchStatus()
    object LoadMoreProducts: FetchStatus()
    data class Error(val message: String) : FetchStatus()
}