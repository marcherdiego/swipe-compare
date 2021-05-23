package com.github.marcherdiego.swipecompare.core

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.PorterDuff.Mode
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.github.marcherdiego.swipecompare.core.base.SwipeCompareLayout

@SuppressLint("ClickableViewAccessibility")
class VerticalSwipeCompareLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SwipeCompareLayout(context, attrs, defStyleAttr) {
    
    init {
        LayoutInflater.from(context).inflate(R.layout.vertical_swipe_compare_layout, this)
        init()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        val bottomLine = (verticalSlider?.y?.toInt() ?: 0) + verticalSelectorHeight
        topLeftFragmentContainer?.clipBounds = Rect(0, 0, width, bottomLine)
        bottomLeftFragmentContainer?.clipBounds = Rect(0, bottomLine, width, height)
        super.onDraw(canvas)
    }

    fun setFragments(fragmentManager: FragmentManager, topFragment: Fragment, bottomFragment: Fragment) {
        fragmentManager.beginTransaction().apply {
            replace(topLeftFragmentContainer?.id ?: return, topFragment)
            replace(bottomLeftFragmentContainer?.id ?: return, bottomFragment)
            commit()
        }
    }

    fun setViews(topView: View, bottomView: View) {
        topLeftFragmentContainer?.removeAllViews()
        topLeftFragmentContainer?.addView(topView)

        bottomLeftFragmentContainer?.removeAllViews()
        bottomLeftFragmentContainer?.addView(bottomView)
    }

    fun setSliderBarHeight(height: Int) {
        if (height == 0) {
            verticalSelectorBar?.visibility = View.INVISIBLE
        } else {
            verticalSelectorBar?.visibility = View.VISIBLE
            verticalSelectorBar?.layoutParams?.height = height
        }
    }

    fun setSliderPosition(sliderY: Float, sliderIconX: Float = verticalSelectorIcon?.x ?: 0f) {
        verticalSlider?.y = sliderY
        verticalSelectorIcon?.x = sliderIconX
        invalidate()
    }

    fun setSliderPositionChangedListener(listener: (Float) -> Unit) {
        verticalSliderValueListener = listener
    }

    fun setSliderIconPosition(x: Float) {
        verticalSelectorIcon?.x = x
    }

    override fun setSliderBarColor(@ColorInt color: Int) {
        verticalSelectorBar?.setBackgroundColor(color)
    }

    override fun setSliderBarColorRes(@ColorRes color: Int) {
        verticalSelectorBar?.setBackgroundColor(ContextCompat.getColor(context, color))
    }

    override fun setSliderIconColor(@ColorInt color: Int) {
        verticalSelectorIcon?.setBackgroundColor(color)
    }

    override fun setSliderIconColorRes(@ColorRes color: Int) {
        verticalSelectorIcon?.setBackgroundColor(ContextCompat.getColor(context, color))
    }

    override fun setSliderIconSize(width: Int, height: Int) {
        verticalSelectorIcon?.layoutParams?.width = width
        verticalSelectorIcon?.layoutParams?.height = height
    }

    override fun setSliderIconBackground(@DrawableRes background: Int) {
        verticalSelectorIcon?.setBackgroundResource(background)
    }

    override fun setSliderIconBackground(background: Drawable) {
        verticalSelectorIcon?.background = background
    }

    override fun setSliderIcon(@DrawableRes icon: Int) {
        verticalSelectorIcon?.setImageResource(icon)
    }

    override fun setSliderIcon(icon: Drawable) {
        verticalSelectorIcon?.setImageDrawable(icon)
    }

    override fun setSliderIconTint(@ColorRes color: Int) {
        verticalSelectorIcon?.setColorFilter(ContextCompat.getColor(context, color), Mode.SRC_IN)
    }

    override fun setSliderIconPadding(left: Int, top: Int, right: Int, bottom: Int) {
        verticalSelectorIcon?.setPadding(left, top, right, bottom)
    }

    override fun setFixedSliderIcon(fixed: Boolean) {
        fixedVerticalSliderIcon = fixed
    }
}
