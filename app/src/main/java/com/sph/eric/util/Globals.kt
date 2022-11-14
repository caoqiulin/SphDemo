package com.sph.eric.util

import android.annotation.SuppressLint
import android.content.Context
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