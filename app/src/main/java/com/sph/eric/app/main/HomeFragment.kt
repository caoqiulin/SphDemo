package com.sph.eric.app.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.sph.eric.R
import com.sph.eric.databinding.FragmentHomeBinding
import com.sph.eric.util.viewBinding
import com.sph.eric.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/8
 */
class HomeFragment: Fragment(R.layout.fragment_home) {
    private val viewBinding by viewBinding<FragmentHomeBinding>()
    private val viewModel : MainViewModel by viewModel()

    private val pageAdapter by lazy {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.dataList.observe(viewLifecycleOwner, Observer {
            // get date
        })

        viewModel.loadData()
    }

}