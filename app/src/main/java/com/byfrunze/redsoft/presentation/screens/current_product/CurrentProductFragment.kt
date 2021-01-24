package com.byfrunze.redsoft.presentation.screens.current_product

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.byfrunze.redsoft.R
import com.byfrunze.redsoft.data.model.Product
import com.byfrunze.redsoft.databinding.CurrentProductFragmentBinding
import com.byfrunze.redsoft.presentation.helpers.showMessage
import com.byfrunze.redsoft.presentation.screens.current_product.models.CurrentProductAction
import com.byfrunze.redsoft.presentation.screens.current_product.models.CurrentProductEvent
import com.byfrunze.redsoft.presentation.screens.current_product.models.CurrentProductViewState
import com.byfrunze.redsoft.presentation.screens.current_product.models.FetchStatusProduct
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar

class CurrentProductFragment : Fragment(R.layout.current_product_fragment) {


    private val viewModel: CurrentProductViewModel by viewModels()
    lateinit var toolbar: MaterialToolbar
    lateinit var titleTextView: TextView
    lateinit var producerTextView: TextView
    lateinit var imageImageView: ImageView
    lateinit var descriptionTextView: TextView
    lateinit var categoriesTextView: TextView
    lateinit var priceTextView: TextView
    lateinit var addToCartBtn: Button
    lateinit var plusProductBtn: ImageButton
    lateinit var minusProductBtn: ImageButton
    lateinit var counterProductTextView: TextView
    lateinit var counterProductLinearLayout: LinearLayout
    lateinit var loader: ProgressBar
    lateinit var content: ConstraintLayout

    lateinit var binding: CurrentProductFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingScreen()
        val isAddToCart = arguments?.get("isAddToCart") as Boolean

        viewModel.apply {
            viewStates().observe(viewLifecycleOwner, { bindViewState(it) })
            viewAction().observe(viewLifecycleOwner, { bindViewAction(it) })
            obtainEvent(CurrentProductEvent.ScreenShow(isAddToCart))
        }
        var count = 0
        addToCartBtn.setOnClickListener {
            viewModel.obtainEvent(CurrentProductEvent.AddToCart(count = count++))
        }
        plusProductBtn.setOnClickListener {
            viewModel.obtainEvent(CurrentProductEvent.AddToCart(count = count++))
        }
        minusProductBtn.setOnClickListener {
            viewModel.obtainEvent(CurrentProductEvent.RemoveFromCart(count = count--))
        }
        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.searchProductFragment)
        }
        return binding.root
    }

    private fun bindingScreen() {
        binding = CurrentProductFragmentBinding.inflate(layoutInflater)
        toolbar = binding.toolbar
        requireActivity().actionBar?.customView = toolbar
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)

        titleTextView = binding.titleTextView
        producerTextView = binding.producerTextView
        imageImageView = binding.productImageView
        descriptionTextView = binding.descriptionTextView
        categoriesTextView = binding.categoryTextView
        priceTextView = binding.priceTextView
        addToCartBtn = binding.addToCartBtn
        plusProductBtn = binding.addToCartCounterBtn
        minusProductBtn = binding.removeFromCartBtn
        counterProductTextView = binding.counterProductTextView
        counterProductLinearLayout = binding.counterProduct
        loader = binding.loader
        content = binding.content
    }

    private fun setupInfo(isAddToCart: Boolean, count: Int) {
        val product = arguments?.get("product") as Product
        toolbar.title = product.title
        toolbar.setTitleTextColor(Color.WHITE)
        if (isAddToCart) {
            counterProductLinearLayout.visibility = View.VISIBLE
            addToCartBtn.visibility = View.GONE
            counterProductTextView.text = "$count шт"
        } else {
            counterProductLinearLayout.visibility = View.GONE
            addToCartBtn.visibility = View.VISIBLE
        }
        titleTextView.text = product.title
        producerTextView.text = product.producer
        Glide.with(binding.productImageView)
            .load(product.image_url)
            .placeholder(R.drawable.placeholder)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .into(binding.productImageView)
        descriptionTextView.text = product.short_description
        var categoryBuffer = ""
        for (category in product.categories) categoryBuffer += category.title + '\n'
        categoriesTextView.text = categoryBuffer
        priceTextView.text = "${product.price} P"
    }

    private fun bindViewState(viewState: CurrentProductViewState) {
        when (viewState.fetchStatus) {
            is FetchStatusProduct.Loading -> {
                loader.visibility = View.VISIBLE
                content.visibility = View.GONE
            }
            is FetchStatusProduct.Success -> {
                loader.visibility = View.GONE
                showContent(viewState.count)
            }
            is FetchStatusProduct.Error -> {
                showMessage(
                    message = viewState.fetchStatus.message,
                    length = Snackbar.LENGTH_SHORT
                )
            }
        }
    }

    private fun bindViewAction(viewAction: CurrentProductAction) {
        when (viewAction) {
            is CurrentProductAction.ShowSnackbar -> {
                Snackbar.make(
                    requireView(),
                    viewAction.message,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showContent(count: Int) {
        content.visibility = View.VISIBLE
        if (count == 0)
            setupInfo(false, count)
        else setupInfo(true, count)
    }
}