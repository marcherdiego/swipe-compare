package com.github.marcherdiego.swipecompare.ui.mvp.view

import android.widget.ImageView
import android.widget.ImageView.ScaleType.CENTER_CROP
import com.github.marcherdiego.swipecompare.R
import com.github.marcherdiego.swipecompare.core.CrosshairSwipeCompareLayout
import com.nerdscorner.mvplib.events.view.BaseActivityView
import com.github.marcherdiego.swipecompare.ui.activities.CrosshairSwipeActivity
import com.github.marcherdiego.swipecompare.ui.fragments.BottomLeftFragment
import com.github.marcherdiego.swipecompare.ui.fragments.BottomRightFragment
import com.github.marcherdiego.swipecompare.ui.fragments.TopLeftFragment
import com.github.marcherdiego.swipecompare.ui.fragments.TopRightFragment

class CrosshairSwipeView(activity: CrosshairSwipeActivity) : BaseActivityView(activity) {
    private val crosshairSwipeCompareLayout1: CrosshairSwipeCompareLayout = activity.findViewById(R.id.crosshair_swipe_compare_1)
    private val crosshairSwipeCompareLayout2: CrosshairSwipeCompareLayout = activity.findViewById(R.id.crosshair_swipe_compare_2)

    init {
        activity.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
        crosshairSwipeCompareLayout1.apply {
            setSliderBarColorRes(R.color.white)
            setSliderIconBackground(R.drawable.circle_background)
            val sliderIconPadding = resources.getDimensionPixelSize(R.dimen.icon_padding)
            setSliderIconPadding(sliderIconPadding, sliderIconPadding, sliderIconPadding, sliderIconPadding)
            setFragments(
                fragmentManager = activity.supportFragmentManager,
                topLeftFragment = TopLeftFragment(),
                topRightFragment = TopRightFragment(),
                bottomLeftFragment = BottomLeftFragment(),
                bottomRightFragment = BottomRightFragment()
            )
            setSliderBarHeight(resources.getDimensionPixelSize(R.dimen.bar_height))
            setSliderBarWidth(resources.getDimensionPixelSize(R.dimen.bar_width))
            setSliderIconSize(resources.getDimensionPixelSize(R.dimen.icon_size), resources.getDimensionPixelSize(R.dimen.icon_size))
        }

        crosshairSwipeCompareLayout2.apply {
            setSliderBarColorRes(R.color.white)
            setSliderIconBackground(R.drawable.circle_background)
            val sliderIconPadding = resources.getDimensionPixelSize(R.dimen.icon_padding)
            setSliderIconPadding(sliderIconPadding, sliderIconPadding, sliderIconPadding, sliderIconPadding)
            setViews(
                topLeftView = ImageView(activity).apply {
                    scaleType = CENTER_CROP
                    setImageResource(R.drawable.img1)
                },
                topRightView = ImageView(activity).apply {
                    scaleType = CENTER_CROP
                    setImageResource(R.drawable.img2)
                },
                bottomLeftView = ImageView(activity).apply {
                    scaleType = CENTER_CROP
                    setImageResource(R.drawable.ss1)
                },
                bottomRightView = ImageView(activity).apply {
                    scaleType = CENTER_CROP
                    setImageResource(R.drawable.ss2)
                }
            )
            setSliderBarHeight(resources.getDimensionPixelSize(R.dimen.bar_height))
            setSliderBarWidth(resources.getDimensionPixelSize(R.dimen.bar_width))
            setSliderIconSize(resources.getDimensionPixelSize(R.dimen.icon_size), resources.getDimensionPixelSize(R.dimen.icon_size))
        }
    }
}
