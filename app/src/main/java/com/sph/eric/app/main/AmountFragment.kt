package com.sph.eric.app.main

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sph.eric.R
import com.sph.eric.databinding.FragmentAmountBinding
import com.sph.eric.model.Record
import com.sph.eric.util.application
import com.sph.eric.util.scaledTabLayoutMediator
import com.sph.eric.util.viewBinding
import com.sph.eric.viewmodel.AmountViewModel
import com.sph.eric.viewmodel.AmountViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/14
 */
class AmountFragment: Fragment(R.layout.fragment_amount) {
    private val viewBinding by viewBinding<FragmentAmountBinding>()
    private val viewModel: AmountViewModel by viewModels{
        AmountViewModelFactory(application.recordDBRepository)
    }

    private val mYear by lazy { arguments?.getString("year") ?:""}

    private var yearList: MutableList<String>? = null

    private var tabs : MutableList<String>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        initVM()
    }

    /**
     * 按年查询数据
     */
    fun initData() {
        viewModel.loadData()
        viewModel.loadDataByYear(mYear)
    }

    fun initVM() {
        viewModel.yearList.observe(viewLifecycleOwner) {
            Timber.d("获取年度筛选数据 ${it?.size} 条")
            yearList = it as ArrayList
            tabs = ArrayList()
            for (tab in yearList as java.util.ArrayList<String>) {
                (tabs as ArrayList<String>).add(tab)
            }

            requireActivity().runOnUiThread { viewBinding.setupPager() }
        }
    }

    @Suppress("UNUSED_EXPRESSION")
    private fun FragmentAmountBinding.setupPager() {
        viewPager.offscreenPageLimit = 1
        if (viewPager.adapter != null) return
        viewPager.adapter = object : FragmentStateAdapter(this@AmountFragment) {
            override fun getItemCount() = tabs!!.size
            override fun createFragment(position: Int) = DetailFragment(year = yearList?.get(position) ?: "")
        }
        tabLayout.scaledTabLayoutMediator(viewPager) { tab, position ->
            tab.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
            tab.text = tabs!![position]
        }.attach()
    }

    companion object {
        operator fun invoke(year: String) =
            AmountFragment().apply {
                arguments = bundleOf(
                    "year" to year
                )
            }
    }
}