package com.github.marcherdiego.swipecompare.ui.mvp.view

import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.ImageView
import android.widget.ImageView.ScaleType.CENTER_CROP
import android.widget.TextView
import com.github.marcherdiego.swipecompare.R
import com.github.marcherdiego.swipecompare.core.VerticalSwipeCompareLayout
import com.nerdscorner.mvplib.events.view.BaseActivityView
import com.github.marcherdiego.swipecompare.ui.activities.VerticalSwipeActivity
import com.github.marcherdiego.swipecompare.ui.fragments.LeftFragment
import com.github.marcherdiego.swipecompare.ui.fragments.RightFragment
import com.google.android.material.slider.Slider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText

class VerticalSwipeView(activity: VerticalSwipeActivity) : BaseActivityView(activity) {
    private val verticalSwipeCompareLayout1: VerticalSwipeCompareLayout = activity.findViewById(R.id.vertical_swipe_compare_1)
    private val verticalSwipeCompareLayout2: VerticalSwipeCompareLayout = activity.findViewById(R.id.vertical_swipe_compare_2)
    private val barHeightSliderLabel: TextView = activity.findViewById(R.id.bar_height_slider_label)
    private val barHeightSlider: Slider = activity.findViewById(R.id.bar_height_slider)

    private val sliderPosition: TextInputEditText = activity.findViewById(R.id.slider_position)
    private val fixedSliderIconSwitch: SwitchMaterial = activity.findViewById(R.id.fixed_slider_icon_switch)
    private val centerSliderIconButton: Button = activity.findViewById(R.id.center_slider_icon_button)

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            sliderPosition.removeTextChangedListener(this)
            bus.post(SliderPositionValueChangedEvent(s?.toString()))
            sliderPosition.addTextChangedListener(this)
            sliderPosition.setSelection(sliderPosition.text?.length ?: 0)
        }
    }

    init {
        activity.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
        barHeightSlider.stepSize = 1f
        barHeightSlider.addOnChangeListener { _, value, _ ->
            bus.post(BarHeightChangedEvent(value.toInt()))
        }
        fixedSliderIconSwitch.setOnCheckedChangeListener { _, isChecked ->
            bus.post(FixedSliderIconCheckedChangedEvent(isChecked))
        }
        centerSliderIconButton.setOnClickListener {
            bus.post(CenterSliderIconCheckedChangedEvent())
        }
        sliderPosition.addTextChangedListener(textWatcher)

        val resources = activity.resources
        val sliderIconPadding = resources.getDimensionPixelSize(R.dimen.icon_padding)
        val sliderBarHeight = resources.getDimensionPixelSize(R.dimen.bar_height)
        val sliderIconSize = resources.getDimensionPixelSize(R.dimen.icon_size)

        verticalSwipeCompareLayout1
            .setSliderBarColorRes(R.color.white)
            .setSliderIconBackground(R.drawable.circle_background)
            .setSliderIconPadding(sliderIconPadding, sliderIconPadding, sliderIconPadding, sliderIconPadding)
            .setFragments(fragmentManager = activity.supportFragmentManager, topFragment = LeftFragment(), bottomFragment = RightFragment())
            .setSliderBarHeight(sliderBarHeight)
            .setSliderIconSize(sliderIconSize, sliderIconSize)

        verticalSwipeCompareLayout2
            .setSliderBarColorRes(R.color.white)
            .setSliderIconBackground(R.drawable.circle_background)
            .setSliderIconPadding(sliderIconPadding, sliderIconPadding, sliderIconPadding, sliderIconPadding)
            .setViews(
                topView = ImageView(activity).apply {
                    scaleType = CENTER_CROP
                    setImageResource(R.drawable.img1)
                },
                bottomView = ImageView(activity).apply {
                    scaleType = CENTER_CROP
                    setImageResource(R.drawable.img2)
                }
            )
            .setSliderBarHeight(sliderBarHeight)
            .setSliderIconSize(resources.getDimensionPixelSize(R.dimen.icon_size), resources.getDimensionPixelSize(R.dimen.icon_size))
            .setSliderPositionChangedListener {
                sliderPosition.removeTextChangedListener(textWatcher)
                bus.post(SliderPositionChangedEvent(it))
                sliderPosition.addTextChangedListener(textWatcher)
                sliderPosition.setSelection(sliderPosition.text?.length ?: 0)
            }
    }

    fun setBarHeight(height: Int) {
        barHeightSliderLabel.text = "Bar height: ${height}px"
        verticalSwipeCompareLayout1.setSliderBarHeight(height)
    }

    fun setFixedSlider(fixed: Boolean) {
        verticalSwipeCompareLayout2.setFixedSliderIcon(fixed)
    }

    fun setSliderPosition(position: Float) {
        sliderPosition.setText(position.toInt().toString())
        verticalSwipeCompareLayout2.setSliderPosition(position)
    }

    fun centerSliderIcon() {
        verticalSwipeCompareLayout2.centerSliderIconColor()
    }

    class BarHeightChangedEvent(val height: Int)
    class FixedSliderIconCheckedChangedEvent(val fixed: Boolean)

    class SliderPositionChangedEvent(val value: Float)
    class SliderPositionValueChangedEvent(val value: String?)
    class CenterSliderIconCheckedChangedEvent
}
