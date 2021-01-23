package com.byfrunze.redsoft.presentation.screens.current_product

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.byfrunze.redsoft.R

class CurrentProductFragment : Fragment() {

    companion object {
        fun newInstance() = CurrentProductFragment()
    }

    private lateinit var viewModel: CurrentProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_product_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CurrentProductViewModel::class.java)
        // TODO: Use the ViewModel
    }

}