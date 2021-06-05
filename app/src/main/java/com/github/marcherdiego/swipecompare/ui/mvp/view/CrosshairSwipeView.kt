package com.github.marcherdiego.swipecompare.ui.mvp.view

import android.util.Log
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

        crosshairSwipeCompareLayout.setSliderPositionChangedListener { x, y ->
            Log.v("CrosshairSwipeView", "x=$x, y=$y")
        }
    }

    fun setUnifiedControls(unifiedControls: Boolean) {
        crosshairSwipeCompareLayout.unifiedControllers = unifiedControls
    }

    class CombinedControlsCheckedChangedEvent(val isChecked: Boolean)
}
