package com.sph.eric.app.main

import androidx.fragment.app.viewModels
import com.sph.eric.util.application
import com.sph.eric.viewmodel.RecordViewModel
import com.sph.eric.viewmodel.RecordViewModelFactory
import org.junit.Assert
import org.junit.Before

import org.junit.Test
import org.mockito.Mockito

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/11
 */
class HomeFragmentTest {
    @Before
    fun setUp() {

    }

    @Test
    fun testMock() {
        val mockHome : HomeFragment = Mockito.mock(HomeFragment::class.java)
        Assert.assertNotNull(mockHome)
    }


    @Test
    fun initDB() {
        val viewModel = RecordViewModelFactory(application.recordDBRepository).create(RecordViewModel::class.java)

    }
}