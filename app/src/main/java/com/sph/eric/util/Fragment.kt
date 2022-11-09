package com.sph.eric.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/9
 */

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun AppCompatActivity.addFragment(fragment: Fragment, fragmentId: Int) {
    supportFragmentManager.inTransaction { add(fragmentId, fragment) }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, fragmentId: Int) {
    supportFragmentManager.inTransaction { replace(fragmentId, fragment) }
}