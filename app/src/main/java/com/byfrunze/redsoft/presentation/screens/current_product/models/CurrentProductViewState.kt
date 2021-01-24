package com.byfrunze.redsoft.presentation.screens.current_product.models

data class CurrentProductViewState(
    val fetchStatus: FetchStatusProduct,
    val count: Int
)

sealed class FetchStatusProduct {
    object Loading : FetchStatusProduct()
    object Success : FetchStatusProduct()
    data class Error(val message: String) : FetchStatusProduct()
}