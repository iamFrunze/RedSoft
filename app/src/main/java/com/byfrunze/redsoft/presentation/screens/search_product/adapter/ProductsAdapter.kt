package com.byfrunze.redsoft.presentation.screens.search_product.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.byfrunze.redsoft.R
import com.byfrunze.redsoft.data.model.Product
import com.byfrunze.redsoft.databinding.CellProductBinding

interface OnLoadProducts {
    fun onLoad(load: Boolean)
}

interface OnPickProduct {
    fun onPick(product: Product, isAddToCart: Boolean)
}

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    private val data: MutableList<Product> = ArrayList()
    var loadProducts: OnLoadProducts? = null
    var onPickProduct: OnPickProduct? = null

    fun setData(model: List<Product>) {
        data.clear()
        data.addAll(model)
        notifyDataSetChanged()
    }


    fun addProducts(models: List<Product>) {
        data.addAll(models)
        notifyItemInserted(data.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemBinding = CellProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProductViewHolder(itemBinding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        if (position == data.size - 3) {
            loadProducts?.onLoad(true)
        } else loadProducts?.onLoad(false)
        holder.bind(model = data[position])
    }

    inner class ProductViewHolder(private val itemBinding: CellProductBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        var addToCart = "ADD_TO_CART"
        var removeFromCart = "REMOVE_FROM_CART"
        var flag = addToCart

        fun bind(model: Product) {
            if (model.categories.isEmpty()) itemBinding.categoryTextView.visibility = View.GONE
            else itemBinding.categoryTextView.text = model.categories[0].title

            itemBinding.priceTextView.text = "${model.price}  P"
            itemBinding.producerTextView.text = model.producer
            itemBinding.productTextView.text = model.title
            Glide.with(itemView)
                .load(model.image_url)
                .placeholder(R.drawable.placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .into(itemBinding.productImageView)
            var isAddToCart = false
            itemBinding.addCartBtn.setOnClickListener {
                flag = if (flag == addToCart) {
                    itemBinding.addCartBtn.setBackgroundResource(R.drawable.remove_from_cart_background)
                    isAddToCart = true
                    removeFromCart
                } else {
                    itemBinding.addCartBtn.setBackgroundResource(R.drawable.add_card_background)
                    isAddToCart = false
                    addToCart
                }

            }

            itemBinding.root.setOnClickListener {
                onPickProduct?.onPick(
                    product = model,
                    isAddToCart = isAddToCart
                )
            }
        }
    }
}