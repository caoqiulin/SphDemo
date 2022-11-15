package com.sph.eric.util

import android.animation.ValueAnimator
import android.util.TypedValue
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sph.eric.R

fun TabLayout.scaledTabLayoutMediator(
    viewPager: ViewPager2,
    textSize: Float = 15F,
    scaledTextSize: Float = 16F,
    autoRefresh: Boolean = true,
    smoothScroll: Boolean = true,
    onTabSelectedChanged: (old: TextView?, new: TextView?) -> Unit = { _, _ -> },
    tabConfigurationStrategy: (tab: TextView, position: Int) -> Unit,
) = CustomTabLayoutMediator(
    this,
    viewPager,
    autoRefresh,
    smoothScroll,
    onTabSelectedChanged = { old, new ->
        val oldView = old?.customView as? TextView
        val newView = new?.customView as? TextView
        
        val scale = scaledTextSize / textSize
        val animator = ValueAnimator.ofFloat(1F, scale)
        animator.duration = 100
        animator.addUpdateListener {
            (scale + 1 - it.animatedValue as Float).also { scale ->
                oldView?.scaleX = scale
                oldView?.scaleY = scale
            }
            (it.animatedValue as Float).also { scale ->
                newView?.scaleX = scale
                newView?.scaleY = scale
            }
        }
        animator.start()
        onTabSelectedChanged(oldView, newView)
    },
    tabConfigurationStrategy = { tab, position ->
        tab.setCustomView(R.layout.layout_tablayout_custom_view)
        (tab.customView as TextView).also {
            it.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
            it.setTextColor(tabTextColors)
            tabConfigurationStrategy(it, position)
        }
    }
)

class CustomTabLayoutMediator(
    private val tabLayout: TabLayout,
    private val viewPager: ViewPager2,
    autoRefresh: Boolean = true,
    smoothScroll: Boolean = true,
    private val onTabSelectedChanged: (old: TabLayout.Tab?, new: TabLayout.Tab?) -> Unit = { _, _ -> },
    tabConfigurationStrategy: (tab: TabLayout.Tab, position: Int) -> Unit,
) : TabLayout.OnTabSelectedListener {

    private val tabLayoutMediator =
        TabLayoutMediator(tabLayout, viewPager, autoRefresh, smoothScroll) { tab, position ->
            tabConfigurationStrategy(tab, position)
        }
    private var currentSelectedTab: TabLayout.Tab? = null

    fun attach() {
        tabLayoutMediator.attach()
        tabLayout.addOnTabSelectedListener(this)
        onTabSelected(tabLayout.getTabAt(viewPager.currentItem))
    }

    fun detach() {
        tabLayoutMediator.detach()
        tabLayout.removeOnTabSelectedListener(this)
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        onTabSelectedChanged(currentSelectedTab, tab)
        currentSelectedTab = tab
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }
}