package com.byfrunze.redsoft.presentation.screens.search_product

import androidx.hilt.lifecycle.ViewModelInject
import com.byfrunze.redsoft.data.source.remote.RedsoftApi
import com.byfrunze.redsoft.presentation.base.BaseViewModel
import com.byfrunze.redsoft.presentation.screens.search_product.models.FetchStatus
import com.byfrunze.redsoft.presentation.screens.search_product.models.ProductsSearchAction
import com.byfrunze.redsoft.presentation.screens.search_product.models.ProductsSearchEvent
import com.byfrunze.redsoft.presentation.screens.search_product.models.ProductsSearchViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchProductViewModel @ViewModelInject constructor(
    private val redsoftRepository: RedsoftApi
) : BaseViewModel<ProductsSearchViewState, ProductsSearchAction, ProductsSearchEvent>() {

    private val compositeDisposable = CompositeDisposable()

    init {
        viewState = ProductsSearchViewState(
            fetchStatus = FetchStatus.Loading,
            products = null
        )
    }

    override fun obtainEvent(viewEvent: ProductsSearchEvent) {
        when (viewEvent) {
            is ProductsSearchEvent.ScreenShow -> fetchData()
        }
    }

    private fun fetchData() {
        viewState = viewState.copy(
            fetchStatus = FetchStatus.Loading,
            products = null
        )
        val data = redsoftRepository.fetchProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState = ProductsSearchViewState(
                    products = it,
                    fetchStatus = FetchStatus.Success
                )
            }, {
                viewAction = ProductsSearchAction.ShowSnackbar(
                    message = it.localizedMessage,
                )
            })
        compositeDisposable.addAll(data)
    }
}