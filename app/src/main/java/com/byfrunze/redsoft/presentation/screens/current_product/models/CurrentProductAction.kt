package com.byfrunze.redsoft.presentation.screens.current_product.models

sealed class CurrentProductAction {
    data class ShowSnackbar(val message: String) : CurrentProductAction()

}