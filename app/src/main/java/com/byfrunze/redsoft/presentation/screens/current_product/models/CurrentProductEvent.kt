package com.byfrunze.redsoft.presentation.screens.current_product.models


sealed class CurrentProductEvent {
    class ScreenShow(val isAddToCart: Boolean) : CurrentProductEvent()
    class AddToCart(val count: Int) : CurrentProductEvent()
    class RemoveFromCart(val count: Int) : CurrentProductEvent()

}