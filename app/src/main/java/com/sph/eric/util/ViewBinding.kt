package com.sph.eric.util

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/9
 */
inline fun <reified VB : ViewBinding> Fragment.viewBinding(
    noinline viewBindingFactory: (Fragment) -> VB? = { fragment ->
        ViewBindings.reflectBind(fragment.requireView())
    }
) = ViewBindingLazy<Fragment, VB> { fragment, property ->
    viewBindingFactory(fragment)
        ?: error("Can't create ViewBinding [${VB::class.simpleName}] for '${property.name}' on Fragment [${fragment.javaClass.simpleName}].")
}

class ViewBindingLazy<T, V>(private val initializer: (T, KProperty<*>) -> V) :
    ReadOnlyProperty<T, V> {

    private val mainHandler = Handler(Looper.getMainLooper())
    private var _value: V? = null

    @MainThread
    override fun getValue(thisRef: T, property: KProperty<*>): V {
        val cached = _value

        if (cached != null) return cached

        return initializer(thisRef, property).also {
            _value = it
            thisRef.bindToLifecycle()
        }
    }

    private fun T.getLifecycleOwner(): LifecycleOwner? {
        return when (this) {
            is Fragment -> viewLifecycleOwner
            is LifecycleOwner -> this
            else -> null
        }
    }

    private fun T.bindToLifecycle() {
        val lifecycle = getLifecycleOwner()?.lifecycle ?: return
        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                owner.lifecycle.removeObserver(this)
                mainHandler.post { _value = null }
            }
        })
    }
}

object ViewBindings {

    inline fun <reified VB : ViewBinding> reflectBind(it: View) =
        VB::class.java.getMethod("bind", View::class.java).invoke(null, it) as VB

    inline fun <reified VB : ViewBinding> reflectInflate(
        parent: ViewGroup, attachToParent: Boolean = false
    ) = reflectInflate<VB>(LayoutInflater.from(parent.context), parent, attachToParent)

    inline fun <reified VB : ViewBinding> reflectInflate(
        inflater: LayoutInflater, parent: ViewGroup? = null, attachToParent: Boolean = false
    ): VB {
        val method = VB::class.java.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        return method.invoke(null, inflater, parent, attachToParent) as VB
    }
}