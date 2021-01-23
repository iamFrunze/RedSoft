package com.byfrunze.redsoft.presentation.screens.search_product.models

import com.byfrunze.redsoft.data.model.Data
import java.util.*

data class ProductsSearchViewState(
    val fetchStatus: FetchStatus,
    val products: Data?
)

sealed class FetchStatus {
    object Loading : FetchStatus()
    object Success : FetchStatus()
    data class Error(val message: String) : FetchStatus()
}