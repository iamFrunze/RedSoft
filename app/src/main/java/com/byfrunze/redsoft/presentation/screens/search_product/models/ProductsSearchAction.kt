package com.byfrunze.redsoft.presentation.screens.search_product.models

sealed class ProductsSearchAction {
    data class ShowSnackbar(val message: String) : ProductsSearchAction()
}