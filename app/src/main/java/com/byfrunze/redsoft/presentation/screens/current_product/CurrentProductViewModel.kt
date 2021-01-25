package com.byfrunze.redsoft.presentation.screens.current_product

import androidx.hilt.lifecycle.ViewModelInject
import com.byfrunze.redsoft.presentation.base.BaseViewModel
import com.byfrunze.redsoft.presentation.screens.current_product.models.CurrentProductAction
import com.byfrunze.redsoft.presentation.screens.current_product.models.CurrentProductEvent
import com.byfrunze.redsoft.presentation.screens.current_product.models.CurrentProductViewState
import com.byfrunze.redsoft.presentation.screens.current_product.models.FetchStatusProduct

class CurrentProductViewModel @ViewModelInject constructor(
) : BaseViewModel<CurrentProductViewState, CurrentProductAction, CurrentProductEvent>() {

    init {
        viewState = CurrentProductViewState(
            fetchStatus = FetchStatusProduct.Loading,
            count = 0
        )
    }

    override fun obtainEvent(viewEvent: CurrentProductEvent) {
        when (viewEvent) {
            is CurrentProductEvent.ScreenShow -> screenShow(viewEvent.isAddToCart)
            is CurrentProductEvent.AddToCart -> addToCart(viewEvent.count, viewEvent.amount)
            is CurrentProductEvent.RemoveFromCart -> removeFromCart(viewEvent.count)
        }
    }

    private fun screenShow(isAddToCart: Boolean) {
        viewState = if (isAddToCart) {
            viewState.copy(
                fetchStatus = FetchStatusProduct.Success,
                count = 1
            )
        } else viewState.copy(
            fetchStatus = FetchStatusProduct.Success,
            count = 0
        )
    }

    private fun addToCart(count: Int, amount: Int) {
        if (count < amount) {
            val newCount = count + 1
            viewState = viewState.copy(count = newCount)
        }
    }

    private fun removeFromCart(count: Int) {
        val newCount = count - 1
        viewState = viewState.copy(count = newCount)
    }
}