package com.byfrunze.redsoft.presentation.screens.search_product.models

sealed class SearchProductsAction {
    data class ShowSnackbar(val message: String) : SearchProductsAction()
}