package com.byfrunze.redsoft.presentation.screens.search_product

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.byfrunze.redsoft.R
import com.byfrunze.redsoft.data.model.Data
import com.byfrunze.redsoft.databinding.SearchProductFragmentBinding
import com.byfrunze.redsoft.presentation.helpers.injectViewModel
import com.byfrunze.redsoft.presentation.helpers.showMessage
import com.byfrunze.redsoft.presentation.screens.search_product.adapter.ProductsAdapter
import com.byfrunze.redsoft.presentation.screens.search_product.models.FetchStatus
import com.byfrunze.redsoft.presentation.screens.search_product.models.ProductsSearchAction
import com.byfrunze.redsoft.presentation.screens.search_product.models.ProductsSearchEvent
import com.byfrunze.redsoft.presentation.screens.search_product.models.ProductsSearchViewState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SearchProductFragment : Fragment(R.layout.search_product_fragment) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: SearchProductViewModel by viewModels()

    lateinit var binding: SearchProductFragmentBinding
    lateinit var recyclerViewProduct: RecyclerView
    lateinit var loader: ProgressBar
    lateinit var searchEditText: EditText
    lateinit var searchBtn: ImageView
    lateinit var backBtn: ImageView

    private val productsAdapter = ProductsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerViewProduct = binding.itemsView
        loader = binding.loader
        searchEditText = binding.searchEditText
        searchBtn = binding.searchBtn
        backBtn = binding.backBtn
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.apply {
            viewStates().observe(viewLifecycleOwner, Observer { bindViewState(it) })
            viewAction().observe(viewLifecycleOwner, Observer { bindViewAction(it) })
            obtainEvent(ProductsSearchEvent.ScreenShow)
        }
    }

    private fun bindViewState(viewState: ProductsSearchViewState) {
        when (viewState.fetchStatus) {
            is FetchStatus.Loading -> {
                loader.visibility = View.VISIBLE
                recyclerViewProduct.visibility = View.GONE
            }
            is FetchStatus.Success -> {
                loader.visibility = View.GONE
                recyclerViewProduct.visibility = View.VISIBLE
            }
            is FetchStatus.Error -> {
                showMessage(
                    message = viewState.fetchStatus.message,
                    length = Snackbar.LENGTH_SHORT
                )
            }

        }
    }

    private fun bindViewAction(viewAction: ProductsSearchAction) {
        when (viewAction) {

        }
    }

    private fun showContent(data: List<Data>) =
        productsAdapter.setData(data)

}