package com.sph.eric.app.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.sph.eric.R
import com.sph.eric.base.*
import com.sph.eric.databinding.FragmentHomeBinding
import com.sph.eric.databinding.ItemMainListBinding
import com.sph.eric.http.RequestResult
import com.sph.eric.model.MainDataModel
import com.sph.eric.model.Record
import com.sph.eric.util.application
import com.sph.eric.util.viewBinding
import com.sph.eric.viewmodel.MainViewModel
import com.sph.eric.viewmodel.RecordViewModel
import com.sph.eric.viewmodel.RecordViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/8
 */
class HomeFragment: Fragment(R.layout.fragment_home) {
    private val viewBinding by viewBinding<FragmentHomeBinding>()
    private val viewModel : MainViewModel by viewModel()

    private val recordViewModel: RecordViewModel by viewModels {
        RecordViewModelFactory(application.recordDBRepository)
    }

    private var recordList: List<Record>? = null

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
                    val item = getItemData<Record>(position) ?: return
                    TabActivity.start(
                        context = requireActivity(),
                        year = item.year
                    )
                }
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initVB()
        initVM()
        initDB()
        initRefreshLayout()
        loadData()
    }

    private fun initVB(){
        viewBinding.apply {
            homeRecyclerView.adapter = pageAdapter
            homeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun initVM() {
        viewModel.data.observe(viewLifecycleOwner, Observer {
            // get date
            Timber.tag(TAG).d("viewModel获取数据： ${it?.success}")
            if (viewBinding.refreshLayout.isRefreshing) viewBinding.refreshLayout.isRefreshing = false
            getData(it)
        })
    }

    private fun initDB() {
        CoroutineScope(Dispatchers.IO).launch {
            recordList = recordViewModel.recordList
            if (recordList?.isNotEmpty() == true) {
                activity?.runOnUiThread {
                    pageAdapter.onResultWithLayout(recordList!!)
                    Timber.tag(TAG).d("从数据库加载缓存数据成功，读取缓存${recordList?.size}条")
                }
            }
        }
    }

    private fun initRefreshLayout() {
        viewBinding.refreshLayout.setOnRefreshListener {
            pageAdapter.data.clear()
            viewModel.loadData()
        }
    }

    private fun getData(data : BaseModel<MainDataModel>) : Boolean{
        when (data.success) {
            true -> {
                pageAdapter.onResultWithLayout(data.result.records)
                CoroutineScope(Dispatchers.IO).launch { recordViewModel.insertList(data.result.records) }
            }
            false -> pageAdapter.onFailureWithLayout(RequestResult.Error(Throwable("no data")))
        }
        return data.success
    }

    private fun loadData() {
        viewModel.loadData()
    }

    companion object {
        private const val TAG = "HomeFragment"
    }

}