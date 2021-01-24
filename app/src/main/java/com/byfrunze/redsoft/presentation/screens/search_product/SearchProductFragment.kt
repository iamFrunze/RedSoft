package com.byfrunze.redsoft.presentation.screens.search_product

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.byfrunze.redsoft.R
import com.byfrunze.redsoft.data.model.Product
import com.byfrunze.redsoft.databinding.SearchProductFragmentBinding
import com.byfrunze.redsoft.presentation.helpers.showMessage
import com.byfrunze.redsoft.presentation.screens.search_product.adapter.OnLoadProducts
import com.byfrunze.redsoft.presentation.screens.search_product.adapter.OnPickProduct
import com.byfrunze.redsoft.presentation.screens.search_product.adapter.ProductsAdapter
import com.byfrunze.redsoft.presentation.screens.search_product.models.FetchStatus
import com.byfrunze.redsoft.presentation.screens.search_product.models.SearchProductsAction
import com.byfrunze.redsoft.presentation.screens.search_product.models.SearchProductsEvent
import com.byfrunze.redsoft.presentation.screens.search_product.models.ProductsSearchViewState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class SearchProductFragment : Fragment(R.layout.search_product_fragment) {


    private val viewModel: SearchProductViewModel by viewModels()

    lateinit var binding: SearchProductFragmentBinding
    lateinit var recyclerViewProduct: RecyclerView
    lateinit var loader: ProgressBar
    lateinit var searchEditText: EditText
    lateinit var searchBtn: ImageView
    lateinit var backBtn: ImageView

    private val productsAdapter = ProductsAdapter()
    private var term = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchProductFragmentBinding.inflate(layoutInflater)

        recyclerViewProduct = binding.itemsView
        loader = binding.loader
        searchEditText = binding.searchEditText
        searchBtn = binding.searchBtn
        backBtn = binding.backBtn

        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.apply {
            viewStates().observe(viewLifecycleOwner, { bindViewState(it) })
            viewAction().observe(viewLifecycleOwner, { bindViewAction(it) })
            obtainEvent(SearchProductsEvent.ScreenShow)
            setupRecyclerViewAdapter()
        }

        productsAdapter.loadProducts = object : OnLoadProducts {
            override fun onLoad(load: Boolean) {
                if (load) viewModel.obtainEvent(
                    SearchProductsEvent.LoadMoreProducts(term = term)
                )
            }
        }

        Observable.create(ObservableOnSubscribe<String> { subscriber ->
            searchEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    term = s.toString()
                    subscriber.onNext(term)
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })
        })
            .debounce(250, TimeUnit.MILLISECONDS)
            .subscribe {
                viewModel.obtainEvent(
                    SearchProductsEvent.SearchProduct(
                        term = it
                    )
                )
            }

        backBtn.setOnClickListener {
            searchEditText.text.clear()
            viewModel.obtainEvent(
                SearchProductsEvent.ScreenShow
            )
        }

        productsAdapter.onPickProduct = object : OnPickProduct {
            override fun onPick(product: Product, isAddToCart: Boolean) {
                val bundle = bundleOf("product" to product, "isAddToCart" to isAddToCart)
                findNavController().navigate(
                    R.id.action_searchProductFragment_to_currentProductFragment,
                    bundle
                )
            }
        }
    }

    private fun bindViewState(viewState: ProductsSearchViewState) {
        when (viewState.fetchStatus) {
            is FetchStatus.Loading -> {
                loader.visibility = View.VISIBLE
            }
            is FetchStatus.Success -> {
                loader.visibility = View.GONE
                showContent(data = viewState.products)
            }
            is FetchStatus.Error -> {
                showMessage(
                    message = viewState.fetchStatus.message,
                    length = Snackbar.LENGTH_SHORT
                )
            }
            is FetchStatus.LoadMoreProducts ->
                addMoreProducts(viewState.products)
        }
    }

    private fun addMoreProducts(list: List<Product>) {
        loader.visibility = View.GONE
        productsAdapter.addProducts(list)
    }

    private fun setupRecyclerViewAdapter() {
        recyclerViewProduct.adapter = productsAdapter
        recyclerViewProduct.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun bindViewAction(viewAction: SearchProductsAction) {
        when (viewAction) {
            is SearchProductsAction.ShowSnackbar -> {
                Snackbar.make(
                    requireView(),
                    viewAction.message,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showContent(data: List<Product>) {
        productsAdapter.setData(data)
    }

}