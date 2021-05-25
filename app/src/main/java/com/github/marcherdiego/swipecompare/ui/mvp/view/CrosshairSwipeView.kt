package com.github.marcherdiego.swipecompare.ui.mvp.view

import android.util.Log
import android.widget.ImageView
import android.widget.ImageView.ScaleType.CENTER_CROP
import com.github.marcherdiego.swipecompare.R
import com.github.marcherdiego.swipecompare.core.CrosshairSwipeCompareLayout
import com.nerdscorner.mvplib.events.view.BaseActivityView
import com.github.marcherdiego.swipecompare.ui.activities.CrosshairSwipeActivity
import com.google.android.material.switchmaterial.SwitchMaterial

class CrosshairSwipeView(activity: CrosshairSwipeActivity) : BaseActivityView(activity) {

    private val crosshairSwipeCompareLayout: CrosshairSwipeCompareLayout = activity.findViewById(R.id.crosshair_swipe_compare)
    private val combinedControlsSwitch: SwitchMaterial = activity.findViewById(R.id.combined_controls_switch)

    init {
        activity.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
        combinedControlsSwitch.setOnCheckedChangeListener { _, isChecked ->
            bus.post(CombinedControlsCheckedChangedEvent(isChecked))
        }

        val resources = activity.resources
        val sliderIconPadding = resources.getDimensionPixelSize(R.dimen.icon_padding)
        val sliderBarHeight = resources.getDimensionPixelSize(R.dimen.bar_height)
        val sliderBarWidth = resources.getDimensionPixelSize(R.dimen.bar_width)
        val sliderIconSize = resources.getDimensionPixelSize(R.dimen.icon_size)
        crosshairSwipeCompareLayout
            .setSliderBarColorRes(R.color.white)
            .setSliderIconBackground(R.drawable.circle_background)
            .setSliderIconPadding(sliderIconPadding, sliderIconPadding, sliderIconPadding, sliderIconPadding)
            .setViews(
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
            .setSliderBarHeight(sliderBarHeight)
            .setSliderBarWidth(sliderBarWidth)
            .setSliderIconSize(sliderIconSize, sliderIconSize)
            .setSliderPositionChangedListener { x, y ->
                Log.v("CrosshairSwipeView", "x=$x, y=$y")
            }
    }

    fun setUnifiedControls(unifiedControls: Boolean) {
        crosshairSwipeCompareLayout.unifiedControllers = unifiedControls
    }

    class CombinedControlsCheckedChangedEvent(val isChecked: Boolean)
}
