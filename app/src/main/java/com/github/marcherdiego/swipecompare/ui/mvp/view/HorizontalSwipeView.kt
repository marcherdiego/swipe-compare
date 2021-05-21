package com.github.marcherdiego.swipecompare.ui.mvp.view

import com.github.marcherdiego.swipecompare.R
import com.github.marcherdiego.swipecompare.core.HorizontalSwipeCompareLayout
import com.nerdscorner.mvplib.events.view.BaseActivityView
import com.github.marcherdiego.swipecompare.ui.activities.HorizontalSwipeActivity
import com.github.marcherdiego.swipecompare.ui.fragments.LeftFragment
import com.github.marcherdiego.swipecompare.ui.fragments.RightFragment

class HorizontalSwipeView(activity: HorizontalSwipeActivity) : BaseActivityView(activity) {
    private val horizontalSwipeCompareLayout: HorizontalSwipeCompareLayout = activity.findViewById(R.id.horizontal_swipe_compare)

    init {
        horizontalSwipeCompareLayout.apply {
            setSliderBarColorRes(R.color.white)
            setSliderIconBackground(R.drawable.circle_background)
            val sliderIconPadding = resources.getDimensionPixelSize(R.dimen.icon_padding)
            setSliderIconPadding(sliderIconPadding, sliderIconPadding, sliderIconPadding, sliderIconPadding)
            setFragments(fragmentManager = activity.supportFragmentManager, leftFragment = LeftFragment(), rightFragment = RightFragment())
            setSliderBarWidth(resources.getDimensionPixelSize(R.dimen.bar_width))
            setSliderIconSize(resources.getDimensionPixelSize(R.dimen.icon_size), resources.getDimensionPixelSize(R.dimen.icon_size))
        }
    }
}
