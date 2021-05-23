package com.github.marcherdiego.swipecompare.ui.mvp.view

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.ImageView.ScaleType.CENTER_CROP
import android.widget.TextView
import com.github.marcherdiego.swipecompare.R
import com.github.marcherdiego.swipecompare.core.HorizontalSwipeCompareLayout
import com.nerdscorner.mvplib.events.view.BaseActivityView
import com.github.marcherdiego.swipecompare.ui.activities.HorizontalSwipeActivity
import com.github.marcherdiego.swipecompare.ui.fragments.LeftFragment
import com.github.marcherdiego.swipecompare.ui.fragments.RightFragment
import com.google.android.material.slider.Slider
import com.google.android.material.switchmaterial.SwitchMaterial

class HorizontalSwipeView(activity: HorizontalSwipeActivity) : BaseActivityView(activity) {

    private val horizontalSwipeCompareLayout1: HorizontalSwipeCompareLayout = activity.findViewById(R.id.horizontal_swipe_compare_1)
    private val horizontalSwipeCompareLayout2: HorizontalSwipeCompareLayout = activity.findViewById(R.id.horizontal_swipe_compare_2)
    private val barWidthSliderLabel: TextView = activity.findViewById(R.id.bar_width_slider_label)
    private val barWidthSlider: Slider = activity.findViewById(R.id.bar_width_slider)

    private val sliderPosition: EditText = activity.findViewById(R.id.slider_position)
    private val fixedSliderIconSwitch: SwitchMaterial = activity.findViewById(R.id.fixed_slider_icon_switch)
    
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            sliderPosition.removeTextChangedListener(this)
            bus.post(SliderPositionValueChangedEvent(s?.toString()))
            sliderPosition.addTextChangedListener(this)
            sliderPosition.setSelection(sliderPosition.text.length)
        }
    }

    init {
        activity.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
        barWidthSlider.stepSize = 1f
        barWidthSlider.addOnChangeListener { _, value, _ ->
            bus.post(BarWidthChangedEvent(value.toInt()))
        }
        fixedSliderIconSwitch.setOnCheckedChangeListener { _, isChecked ->
            bus.post(FixedSliderIconCheckedChangedEvent(isChecked))
        }
        sliderPosition.addTextChangedListener(textWatcher)
        horizontalSwipeCompareLayout1.apply {
            setSliderBarColorRes(R.color.white)
            setSliderIconBackground(R.drawable.circle_background)
            val sliderIconPadding = resources.getDimensionPixelSize(R.dimen.icon_padding)
            setSliderIconPadding(sliderIconPadding, sliderIconPadding, sliderIconPadding, sliderIconPadding)
            setFragments(fragmentManager = activity.supportFragmentManager, leftFragment = LeftFragment(), rightFragment = RightFragment())
            setSliderBarWidth(resources.getDimensionPixelSize(R.dimen.bar_width))
            setSliderIconSize(resources.getDimensionPixelSize(R.dimen.icon_size), resources.getDimensionPixelSize(R.dimen.icon_size))
        }
        horizontalSwipeCompareLayout2.apply {
            setSliderBarColorRes(R.color.white)
            setSliderIconBackground(R.drawable.circle_background)
            val sliderIconPadding = resources.getDimensionPixelSize(R.dimen.icon_padding)
            setSliderIconPadding(sliderIconPadding, sliderIconPadding, sliderIconPadding, sliderIconPadding)
            setViews(
                leftView = ImageView(activity).apply {
                    scaleType = CENTER_CROP
                    setImageResource(R.drawable.img1)
                },
                rightView = ImageView(activity).apply {
                    scaleType = CENTER_CROP
                    setImageResource(R.drawable.img2)
                }
            )
            setSliderBarWidth(resources.getDimensionPixelSize(R.dimen.bar_width))
            setSliderIconSize(resources.getDimensionPixelSize(R.dimen.icon_size), resources.getDimensionPixelSize(R.dimen.icon_size))
            setSliderPositionChangedListener {
                sliderPosition.removeTextChangedListener(textWatcher)
                bus.post(SliderPositionChangedEvent(it))
                sliderPosition.addTextChangedListener(textWatcher)
                sliderPosition.setSelection(sliderPosition.text.length)
            }
        }
    }

    fun setBarWidth(width: Int) {
        barWidthSliderLabel.text = "Bar width: ${width}dp"
        horizontalSwipeCompareLayout1.setSliderBarWidth(width)
    }

    fun setFixedSlider(fixed: Boolean) {
        horizontalSwipeCompareLayout2.setFixedSliderIcon(fixed)
    }

    fun setSliderPosition(position: Float) {
        sliderPosition.setText(position.toInt().toString())
        horizontalSwipeCompareLayout2.setSliderPosition(position)
    }

    class BarWidthChangedEvent(val width: Int)
    class FixedSliderIconCheckedChangedEvent(val fixed: Boolean)

    class SliderPositionChangedEvent(val value: Float)
    class SliderPositionValueChangedEvent(val value: String?)
}
