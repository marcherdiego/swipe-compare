package com.github.marcherdiego.swipecompare.ui.mvp.view

import android.widget.ImageView
import android.widget.ImageView.ScaleType.CENTER_CROP
import com.github.marcherdiego.swipecompare.R
import com.github.marcherdiego.swipecompare.core.VerticalSwipeCompareLayout
import com.nerdscorner.mvplib.events.view.BaseActivityView
import com.github.marcherdiego.swipecompare.ui.activities.VerticalSwipeActivity
import com.github.marcherdiego.swipecompare.ui.fragments.LeftFragment
import com.github.marcherdiego.swipecompare.ui.fragments.RightFragment

class VerticalSwipeView(activity: VerticalSwipeActivity) : BaseActivityView(activity) {
    private val verticalSwipeCompareLayout1: VerticalSwipeCompareLayout = activity.findViewById(R.id.vertical_swipe_compare_1)
    private val verticalSwipeCompareLayout2: VerticalSwipeCompareLayout = activity.findViewById(R.id.vertical_swipe_compare_2)

    init {
        activity.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
        verticalSwipeCompareLayout1.apply {
            setSliderBarColorRes(R.color.white)
            setSliderIconBackground(R.drawable.circle_background)
            val sliderIconPadding = resources.getDimensionPixelSize(R.dimen.icon_padding)
            setSliderIconPadding(sliderIconPadding, sliderIconPadding, sliderIconPadding, sliderIconPadding)
            setFragments(fragmentManager = activity.supportFragmentManager, topFragment = LeftFragment(), bottomFragment = RightFragment())
            setSliderBarHeight(resources.getDimensionPixelSize(R.dimen.bar_height))
            setSliderIconSize(resources.getDimensionPixelSize(R.dimen.icon_size), resources.getDimensionPixelSize(R.dimen.icon_size))
        }

        verticalSwipeCompareLayout2.apply {
            setSliderBarColorRes(R.color.white)
            setSliderIconBackground(R.drawable.circle_background)
            val sliderIconPadding = resources.getDimensionPixelSize(R.dimen.icon_padding)
            setSliderIconPadding(sliderIconPadding, sliderIconPadding, sliderIconPadding, sliderIconPadding)
            setViews(
                topView = ImageView(activity).apply {
                    scaleType = CENTER_CROP
                    setImageResource(R.drawable.img1)
                },
                bottomView = ImageView(activity).apply {
                    scaleType = CENTER_CROP
                    setImageResource(R.drawable.img2)
                }
            )
            setSliderBarHeight(resources.getDimensionPixelSize(R.dimen.bar_height))
            setSliderIconSize(resources.getDimensionPixelSize(R.dimen.icon_size), resources.getDimensionPixelSize(R.dimen.icon_size))
        }
    }
}
