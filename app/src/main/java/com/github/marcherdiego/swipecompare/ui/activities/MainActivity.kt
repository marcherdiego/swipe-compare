package com.github.marcherdiego.swipecompare.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.marcherdiego.swipecompare.R
import com.github.marcherdiego.swipecompare.core.SwipeCompareLayout
import com.github.marcherdiego.swipecompare.ui.fragments.LeftFragment
import com.github.marcherdiego.swipecompare.ui.fragments.RightFragment

class MainActivity : AppCompatActivity() {
    private lateinit var swipeCompareLayout: SwipeCompareLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeCompareLayout = findViewById<SwipeCompareLayout>(R.id.swipe_compare).apply {
            setSliderBarColorRes(R.color.white)
            setSliderIconBackground(R.drawable.circle_background)
            val sliderIconPadding = resources.getDimensionPixelSize(R.dimen.icon_padding)
            setSliderIconPadding(sliderIconPadding, sliderIconPadding, sliderIconPadding, sliderIconPadding)
            setFragments(fragmentManager = supportFragmentManager, leftFragment = LeftFragment(), rightFragment = RightFragment())
            setSliderBarWidth(resources.getDimensionPixelSize(R.dimen.bar_width))
            setSliderIconSize(resources.getDimensionPixelSize(R.dimen.icon_size), resources.getDimensionPixelSize(R.dimen.icon_size))
        }
    }
}
