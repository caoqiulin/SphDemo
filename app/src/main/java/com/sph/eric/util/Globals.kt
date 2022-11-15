package com.sph.eric.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.google.gson.*
import com.sph.eric.base.MyApplication
import timber.log.Timber
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/9
 * 全局变量
 */

/**
 * ApplicationContext
 */
@SuppressLint("StaticFieldLeak")
lateinit var GlobalContext: Context

lateinit var application: MyApplication

inline fun <reified T : Activity> Activity.startActivity(
    vararg extras: Pair<String, Any>,
    intent: (Intent) -> Unit = {}
) = startActivity(Intent(this, T::class.java).putExtras(bundleOf(*extras)).apply(intent))

inline fun <reified T : Activity> Fragment.startActivity(
    vararg extras: Pair<String, Any>,
    intent: (Intent) -> Unit = {}
) = startActivity(Intent(this.activity, T::class.java).putExtras(bundleOf(*extras)).apply(intent))

inline fun <reified T : Activity> View.startActivity(
    vararg extras: Pair<String, Any>,
    intent: (Intent) -> Unit = {}
) = context.startActivity(Intent(context, T::class.java).putExtras(bundleOf(*extras)).apply(intent))

inline fun <reified T : Activity> Context.startActivity(
    vararg extras: Pair<String, Any>,
    intent: (Intent) -> Unit = {}
) = startActivity(Intent(this, T::class.java).putExtras(bundleOf(*extras)).apply(intent))