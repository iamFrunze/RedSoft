package com.byfrunze.redsoft.presentation.screens.search_product

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import com.byfrunze.redsoft.data.source.remote.RedsoftApi
import com.byfrunze.redsoft.presentation.base.BaseViewModel
import com.byfrunze.redsoft.presentation.screens.search_product.models.FetchStatus
import com.byfrunze.redsoft.presentation.screens.search_product.models.SearchProductsAction
import com.byfrunze.redsoft.presentation.screens.search_product.models.SearchProductsEvent
import com.byfrunze.redsoft.presentation.screens.search_product.models.ProductsSearchViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchProductViewModel @ViewModelInject constructor(
    private val redsoftRepository: RedsoftApi
) : BaseViewModel<ProductsSearchViewState, SearchProductsAction, SearchProductsEvent>() {

    private val compositeDisposable = CompositeDisposable()

    init {
        viewState = ProductsSearchViewState(
            fetchStatus = FetchStatus.Loading,
            products = emptyList()
        )
    }

    override fun obtainEvent(viewEvent: SearchProductsEvent) {
        when (viewEvent) {
            is SearchProductsEvent.ScreenShow -> fetchData()
            is SearchProductsEvent.LoadMoreProducts -> loadMoreProducts(viewEvent.term)
            is SearchProductsEvent.SearchProduct -> searchProduct(viewEvent.term)
        }
    }

    private fun searchProduct(term: String) {
        viewState = ProductsSearchViewState(
            fetchStatus = FetchStatus.Loading,
            products = emptyList()
        )
        val data = redsoftRepository.fetchProducts(term = term)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState = viewState.copy(
                    fetchStatus = FetchStatus.Success,
                    products = it.data
                )
            }, {
                viewAction = SearchProductsAction.ShowSnackbar(
                    message = it.localizedMessage,
                )
            })
        compositeDisposable.addAll(data)
    }

    private fun loadMoreProducts(term: String) {
        viewState = viewState.copy(fetchStatus = FetchStatus.Loading)
        val data = redsoftRepository.fetchProducts(term)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i("SENDER", "${it.data}")

                viewState = viewState.copy(
                    fetchStatus = FetchStatus.LoadMoreProducts,
                    products = it.data
                )
            }, {
                Log.i("SENDER", "${it}")

                viewAction = SearchProductsAction.ShowSnackbar(
                    message = it.localizedMessage,
                )
            })
        compositeDisposable.addAll(data)

    }

    private fun fetchData() {
        Log.i("SENDER", "YES ${redsoftRepository.fetchCategories()}")

        viewState = viewState.copy(
            fetchStatus = FetchStatus.Loading,
            products = emptyList()
        )
        val data = redsoftRepository.fetchProducts(term = "")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i("SENDER", "${it.data}")

                viewState = ProductsSearchViewState(
                    products = it.data,
                    fetchStatus = FetchStatus.Success
                )
            }, {
                Log.i("SENDER", "${it}")

                viewAction = SearchProductsAction.ShowSnackbar(
                    message = it.localizedMessage,
                )
            })
        compositeDisposable.addAll(data)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}