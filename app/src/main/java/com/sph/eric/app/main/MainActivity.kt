package com.sph.eric.app.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sph.eric.R
import com.sph.eric.util.addFragment

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/8
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFragment(HomeFragment(), R.id.frame)
    }
}