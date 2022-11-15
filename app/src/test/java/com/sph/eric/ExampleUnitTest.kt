package com.sph.eric

import org.junit.*

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    companion object {
        @BeforeClass
        @JvmStatic
        fun init() {
            println("------init()------");
        }

        @AfterClass
        @JvmStatic
        fun finish() {
            println("------finish()------");
        }

    }

    @Before
    fun setUp() {
        println("------setUp()------");
    }

    @After
    fun tearDown() {
        println("------tearDown()------");
    }

    @Test
    fun test1() {
        println("------test1()------");
    }

    @Test
    fun test2() {
        println("------test2()------");
    }
}