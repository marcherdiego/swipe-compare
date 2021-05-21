package com.github.marcherdiego.swipecompare.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.marcherdiego.swipecompare.R
import com.github.marcherdiego.swipecompare.core.CrosshairSwipeCompareLayout
import com.github.marcherdiego.swipecompare.core.HorizontalSwipeCompareLayout
import com.github.marcherdiego.swipecompare.core.VerticalSwipeCompareLayout
import com.github.marcherdiego.swipecompare.ui.fragments.BottomLeftFragment
import com.github.marcherdiego.swipecompare.ui.fragments.BottomRightFragment
import com.github.marcherdiego.swipecompare.ui.fragments.LeftFragment
import com.github.marcherdiego.swipecompare.ui.fragments.RightFragment
import com.github.marcherdiego.swipecompare.ui.fragments.TopLeftFragment
import com.github.marcherdiego.swipecompare.ui.fragments.TopRightFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        findViewById<HorizontalSwipeCompareLayout>(R.id.horizontal_swipe_compare).apply {
            setSliderBarColorRes(R.color.white)
            setSliderIconBackground(R.drawable.circle_background)
            val sliderIconPadding = resources.getDimensionPixelSize(R.dimen.icon_padding)
            setSliderIconPadding(sliderIconPadding, sliderIconPadding, sliderIconPadding, sliderIconPadding)
            setFragments(fragmentManager = supportFragmentManager, leftFragment = LeftFragment(), rightFragment = RightFragment())
            setSliderBarWidth(resources.getDimensionPixelSize(R.dimen.bar_width))
            setSliderIconSize(resources.getDimensionPixelSize(R.dimen.icon_size), resources.getDimensionPixelSize(R.dimen.icon_size))
        }

        findViewById<VerticalSwipeCompareLayout>(R.id.vertical_swipe_compare).apply {
            setSliderBarColorRes(R.color.white)
            setSliderIconBackground(R.drawable.circle_background)
            val sliderIconPadding = resources.getDimensionPixelSize(R.dimen.icon_padding)
            setSliderIconPadding(sliderIconPadding, sliderIconPadding, sliderIconPadding, sliderIconPadding)
            setFragments(fragmentManager = supportFragmentManager, topFragment = LeftFragment(), bottomFragment = RightFragment())
            setSliderBarHeight(resources.getDimensionPixelSize(R.dimen.bar_height))
            setSliderIconSize(resources.getDimensionPixelSize(R.dimen.icon_size), resources.getDimensionPixelSize(R.dimen.icon_size))
        }
        */

        findViewById<CrosshairSwipeCompareLayout>(R.id.crosshair_swipe_compare).apply {
            setSliderBarColorRes(R.color.white)
            setSliderIconBackground(R.drawable.circle_background)
            val sliderIconPadding = resources.getDimensionPixelSize(R.dimen.icon_padding)
            setSliderIconPadding(sliderIconPadding, sliderIconPadding, sliderIconPadding, sliderIconPadding)
            setFragments(
                fragmentManager = supportFragmentManager,
                topLeftFragment = TopLeftFragment(),
                topRightFragment = TopRightFragment(),
                bottomLeftFragment = BottomLeftFragment(),
                bottomRightFragment = BottomRightFragment()
            )
            setSliderBarHeight(resources.getDimensionPixelSize(R.dimen.bar_height))
            setSliderBarWidth(resources.getDimensionPixelSize(R.dimen.bar_width))
            setSliderIconSize(resources.getDimensionPixelSize(R.dimen.icon_size), resources.getDimensionPixelSize(R.dimen.icon_size))
        }
    }
}
