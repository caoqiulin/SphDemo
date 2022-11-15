package com.sph.eric.viewmodel

import com.sph.eric.db.RecordRoomDatabase
import com.sph.eric.db.record.RecordDAO
import com.sph.eric.db.record.RecordDBRepository
import com.sph.eric.model.Record
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/15
 */
@RunWith(JUnit4::class)
class AmountViewModelTest {
    private var recordDAO: RecordDAO? =null
    private var recordDBRepository: RecordDBRepository? = null
    private var viewModel: AmountViewModel? = null

    @Before
    fun setUp() {
        recordDAO = RecordRoomDatabase.getDatabase().recordDao()
        assertNotNull(recordDAO)

        recordDBRepository = RecordDBRepository(recordDAO!!)
        assertNotNull(recordDBRepository)

        viewModel = AmountViewModel(recordDBRepository!!)
        assertNotNull(viewModel)
    }

    @Test
    fun loadData() {
        viewModel!!.loadData()
    }

    @Test
    fun getYearList() {
        val list = viewModel!!.yearList
        assertNotNull(list)
    }

    @Test
    fun loadDataByYear() {
        viewModel!!.loadDataByYear("2010")
    }

    @Test
    fun getRecordListByYear() {
        val list = viewModel!!.recordListByYear
        assertNotNull(list)
    }


}