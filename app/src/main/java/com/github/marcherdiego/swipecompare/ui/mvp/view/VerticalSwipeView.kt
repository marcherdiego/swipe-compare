package com.github.marcherdiego.swipecompare.ui.mvp.view

import com.github.marcherdiego.swipecompare.R
import com.github.marcherdiego.swipecompare.core.VerticalSwipeCompareLayout
import com.nerdscorner.mvplib.events.view.BaseActivityView
import com.github.marcherdiego.swipecompare.ui.activities.VerticalSwipeActivity
import com.github.marcherdiego.swipecompare.ui.fragments.LeftFragment
import com.github.marcherdiego.swipecompare.ui.fragments.RightFragment

class VerticalSwipeView(activity: VerticalSwipeActivity) : BaseActivityView(activity) {
    private val verticalSwipeCompareLayout: VerticalSwipeCompareLayout = activity.findViewById(R.id.vertical_swipe_compare)

    init {
        verticalSwipeCompareLayout.apply {
            setSliderBarColorRes(R.color.white)
            setSliderIconBackground(R.drawable.circle_background)
            val sliderIconPadding = resources.getDimensionPixelSize(R.dimen.icon_padding)
            setSliderIconPadding(sliderIconPadding, sliderIconPadding, sliderIconPadding, sliderIconPadding)
            setFragments(fragmentManager = activity.supportFragmentManager, topFragment = LeftFragment(), bottomFragment = RightFragment())
            setSliderBarHeight(resources.getDimensionPixelSize(R.dimen.bar_height))
            setSliderIconSize(resources.getDimensionPixelSize(R.dimen.icon_size), resources.getDimensionPixelSize(R.dimen.icon_size))
        }
    }
}
