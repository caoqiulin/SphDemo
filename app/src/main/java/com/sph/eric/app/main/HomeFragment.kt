package com.sph.eric.app.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.sph.eric.R
import com.sph.eric.base.baseAdapter
import com.sph.eric.base.onFailureWithLayout
import com.sph.eric.base.onResultWithLayout
import com.sph.eric.databinding.FragmentHomeBinding
import com.sph.eric.databinding.ItemMainListBinding
import com.sph.eric.http.RequestResult
import com.sph.eric.model.Record
import com.sph.eric.util.viewBinding
import com.sph.eric.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/8
 */
class HomeFragment: Fragment(R.layout.fragment_home) {
    private val TAG = "HomeFragment"
    private val viewBinding by viewBinding<FragmentHomeBinding>()
    private val viewModel : MainViewModel by viewModel()

    private val pageAdapter by lazy {
        baseAdapter<Record, ItemMainListBinding> {
            viewBinding.apply {
                textData.text = it.volume_of_mobile_data
                textYear.text = it.quarter
            }
        }.apply {
            setOnItemClickListener(object : OnItemClickListener{
                override fun onItemClick(
                    adapter: BaseQuickAdapter<*, *>,
                    view: View,
                    position: Int
                ) {
                    //TODO
                }
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.apply {
            homeRecyclerView.adapter = pageAdapter
            homeRecyclerView.layoutManager = LinearLayoutManager(requireContext())

            refreshLayout.setOnRefreshListener {
                pageAdapter.data.clear()
                viewModel.loadData()
            }
        }

        viewModel.data.observe(viewLifecycleOwner, Observer {
            // get date
            Timber.tag(TAG).d("viewModel获取数据： ${it?.success}")
            when (it.success) {
                true -> pageAdapter.onResultWithLayout(it.result.records)
                false -> pageAdapter.onFailureWithLayout(RequestResult.Error(Throwable("no data")))
            }
            if (viewBinding.refreshLayout.isRefreshing) viewBinding.refreshLayout.isRefreshing = false

        })

        viewModel.loadData()
    }

}