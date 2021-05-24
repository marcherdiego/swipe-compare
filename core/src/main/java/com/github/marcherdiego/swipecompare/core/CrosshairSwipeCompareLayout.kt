package com.github.marcherdiego.swipecompare.core

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.PorterDuff.Mode
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.github.marcherdiego.swipecompare.core.base.SwipeCompareLayout

@SuppressLint("ClickableViewAccessibility")
class CrosshairSwipeCompareLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SwipeCompareLayout(context, attrs, defStyleAttr) {

    private var lastHorizontalSelectorIconY: Float? = null
    private var lastVerticalSelectorIconX: Float? = null

    var unifiedControllers: Boolean = false
        set(value) {
            field = value
            invalidate()
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.crosshair_swipe_compare_layout, this)
        init()
    }

    override fun checkUnifiedController(event: MotionEvent) {
        if (unifiedControllers) {
            verticalSlider?.dispatchTouchEvent(event)
        }
    }

    override fun invalidate() {
        if (unifiedControllers) {
            val intersectionX = horizontalSlider?.x ?: 0f
            val intersectionY = verticalSlider?.y ?: 0f

            // Save previous values
            lastHorizontalSelectorIconY = horizontalSelectorIcon?.y
            lastVerticalSelectorIconX = verticalSelectorIcon?.x

            // Set new values
            horizontalSelectorIcon?.y = intersectionY
            verticalSelectorIcon?.x = intersectionX
        } else {
            // Restore and unset previous values
            lastHorizontalSelectorIconY?.let {
                horizontalSelectorIcon?.y = it
                lastHorizontalSelectorIconY = null
            }
            lastVerticalSelectorIconX?.let {
                verticalSelectorIcon?.x = it
                lastVerticalSelectorIconX = null
            }
        }
        super.invalidate()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        val bottomLine = (verticalSlider?.y?.toInt() ?: 0) + verticalSelectorHeight
        val rightLine = (horizontalSlider?.x?.toInt() ?: 0) + horizontalSelectorWidth
        topLeftFragmentContainer?.clipBounds = Rect(0, 0, rightLine, bottomLine)
        topRightFragmentContainer?.clipBounds = Rect(rightLine, 0, width, bottomLine)

        bottomLeftFragmentContainer?.clipBounds = Rect(0, bottomLine, rightLine, height)
        bottomRightFragmentContainer?.clipBounds = Rect(rightLine, bottomLine, width, height)
        super.onDraw(canvas)
    }

    fun setFragments(
        fragmentManager: FragmentManager,
        topLeftFragment: Fragment,
        topRightFragment: Fragment,
        bottomLeftFragment: Fragment,
        bottomRightFragment: Fragment
    ) {
        fragmentManager.beginTransaction().apply {
            replace(topLeftFragmentContainer?.id ?: return, topLeftFragment)
            replace(topRightFragmentContainer?.id ?: return, topRightFragment)
            replace(bottomLeftFragmentContainer?.id ?: return, bottomLeftFragment)
            replace(bottomRightFragmentContainer?.id ?: return, bottomRightFragment)
            commit()
        }
    }

    fun setViews(
        topLeftView: View,
        topRightView: View,
        bottomLeftView: View,
        bottomRightView: View
    ) {
        topLeftFragmentContainer?.removeAllViews()
        topLeftFragmentContainer?.addView(topLeftView)

        topRightFragmentContainer?.removeAllViews()
        topRightFragmentContainer?.addView(topRightView)

        bottomLeftFragmentContainer?.removeAllViews()
        bottomLeftFragmentContainer?.addView(bottomLeftView)

        bottomRightFragmentContainer?.removeAllViews()
        bottomRightFragmentContainer?.addView(bottomRightView)
    }

    fun setSliderBarHeight(height: Int) {
        if (height == 0) {
            verticalSelectorBar?.visibility = View.INVISIBLE
        } else {
            verticalSelectorBar?.visibility = View.VISIBLE
            verticalSelectorBar?.layoutParams?.height = height
        }
    }

    fun setSliderBarWidth(width: Int) {
        if (width == 0) {
            horizontalSelectorBar?.visibility = View.INVISIBLE
        } else {
            horizontalSelectorBar?.visibility = View.VISIBLE
            horizontalSelectorBar?.layoutParams?.width = width
        }
    }
    
    fun setSliderPosition(sliderX: Float, sliderY: Float) {
        horizontalSlider?.x = sliderX
        verticalSlider?.y = sliderY
        invalidate()
    }

    fun setSliderPositionChangedListener(listener: (Float, Float) -> Unit) {
        crosshairSliderValueListener = listener
    }

    override fun setSliderBarColor(@ColorInt color: Int) {
        horizontalSelectorBar?.setBackgroundColor(color)
        verticalSelectorBar?.setBackgroundColor(color)
    }

    override fun setSliderBarColorRes(@ColorRes color: Int) {
        horizontalSelectorBar?.setBackgroundColor(ContextCompat.getColor(context, color))
        verticalSelectorBar?.setBackgroundColor(ContextCompat.getColor(context, color))
    }

    override fun setSliderIconColor(@ColorInt color: Int) {
        horizontalSelectorIcon?.setBackgroundColor(color)
        verticalSelectorIcon?.setBackgroundColor(color)
    }

    override fun setSliderIconColorRes(@ColorRes color: Int) {
        horizontalSelectorIcon?.setBackgroundColor(ContextCompat.getColor(context, color))
        verticalSelectorIcon?.setBackgroundColor(ContextCompat.getColor(context, color))
    }

    override fun setSliderIconSize(width: Int, height: Int) {
        horizontalSelectorIcon?.layoutParams?.width = width
        horizontalSelectorIcon?.layoutParams?.height = height

        verticalSelectorIcon?.layoutParams?.width = width
        verticalSelectorIcon?.layoutParams?.height = height
    }

    override fun setSliderIconBackground(@DrawableRes background: Int) {
        horizontalSelectorIcon?.setBackgroundResource(background)
        verticalSelectorIcon?.setBackgroundResource(background)
    }

    override fun setSliderIconBackground(background: Drawable) {
        horizontalSelectorIcon?.background = background
        verticalSelectorIcon?.background = background
    }

    override fun setSliderIcon(@DrawableRes icon: Int) {
        horizontalSelectorIcon?.setImageResource(icon)
        verticalSelectorIcon?.setImageResource(icon)
    }

    override fun setSliderIcon(icon: Drawable) {
        horizontalSelectorIcon?.setImageDrawable(icon)
        verticalSelectorIcon?.setImageDrawable(icon)
    }

    override fun setSliderIconTint(@ColorRes color: Int) {
        horizontalSelectorIcon?.setColorFilter(ContextCompat.getColor(context, color), Mode.SRC_IN)
        verticalSelectorIcon?.setColorFilter(ContextCompat.getColor(context, color), Mode.SRC_IN)
    }

    override fun setSliderIconPadding(left: Int, top: Int, right: Int, bottom: Int) {
        horizontalSelectorIcon?.setPadding(left, top, right, bottom)
        verticalSelectorIcon?.setPadding(left, top, right, bottom)
    }

    override fun setFixedSliderIcon(fixed: Boolean) {
        unifiedControllers = fixed
    }
}
