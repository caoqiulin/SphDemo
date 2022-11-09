package com.sph.eric.http

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean


/**
 * @Author: 曹秋淋
 * @Date: 2022/11/9
 */
class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val mPending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        // 中间观察者
        super.observe(owner) { t -> // 只有当值未被消费过时，才通知下游观察者
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        }
    }

    override fun setValue(t: T?) {
        // 当值更新时，置标志位为 true
        mPending.set(true)
        super.setValue(t)
    }

    fun call() {
        value = null
    }
}