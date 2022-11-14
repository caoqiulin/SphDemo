package com.sph.eric.app.main

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
    fun initVB() {
    }

    @Test
    fun initVM() {
    }

    @Test
    fun initRefreshLayout() {
    }

    @Test
    fun testMock() {
        val mockHome : HomeFragment = Mockito.mock(HomeFragment::class.java)
        Assert.assertNotNull(mockHome)
    }
}