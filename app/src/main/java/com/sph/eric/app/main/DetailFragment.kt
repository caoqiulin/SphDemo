package com.sph.eric.app.main

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.sph.eric.R
import com.sph.eric.base.baseAdapter
import com.sph.eric.base.onResultWithLayout
import com.sph.eric.databinding.FragmentDetailBinding
import com.sph.eric.databinding.ItemMainListBinding
import com.sph.eric.model.Record
import com.sph.eric.util.application
import com.sph.eric.util.viewBinding
import com.sph.eric.viewmodel.AmountViewModel
import com.sph.eric.viewmodel.AmountViewModelFactory
import timber.log.Timber

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/15
 */
class DetailFragment : Fragment(R.layout.fragment_detail) {
    private val viewBinding by viewBinding<FragmentDetailBinding>()
    private val mYear by lazy { arguments?.getString("year") ?:""}
    private var listByYear: List<Record>? = null

    private val viewModel: AmountViewModel by viewModels{
        AmountViewModelFactory(application.recordDBRepository)
    }

    private val pageAdapter by lazy {
        baseAdapter<Record, ItemMainListBinding> {
            viewBinding.apply {
                textData.text = it.volume_of_mobile_data
                textYear.text = it.quarter
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        initVM()
        initVB()
    }

    fun initData() {
        viewModel.loadDataByYear(mYear)
    }

    fun initVM() {
        viewModel.recordListByYear.observe(viewLifecycleOwner) {
            Timber.d("获取年度筛选数据 ${it?.size} 条")
            listByYear = it
            for (record in listByYear as ArrayList) {
                Timber.d("输出数据 ${record.quarter}数据是：${record.volume_of_mobile_data}")
            }
            pageAdapter.onResultWithLayout(listByYear!!)
        }
    }

    private fun initVB(){
        viewBinding.apply {
            detailRecyclerView.adapter = pageAdapter
            detailRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    companion object {
        operator fun invoke(year: String) =
            DetailFragment().apply {
                arguments = bundleOf(
                    "year" to year
                )
            }
    }
}