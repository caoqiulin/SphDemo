package com.sph.eric.app.main

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sph.eric.R
import com.sph.eric.util.addFragment
import com.sph.eric.util.startActivity

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/14
 */
class TabActivity: AppCompatActivity() {

    private val mYear by lazy { intent?.getStringExtra("year") ?:""}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFragment(AmountFragment(mYear), R.id.frame)
    }

    companion object {
        @JvmStatic
        fun start(
            context: Context,
            year: String
        ) {
            context.startActivity<TabActivity>(
                "year" to year
            )
        }
    }
}