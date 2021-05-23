package com.github.marcherdiego.swipecompare.core

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
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

            // Save previous values to restore them
            lastHorizontalSelectorIconY = horizontalSelectorIcon?.y
            lastVerticalSelectorIconX = verticalSelectorIcon?.x

            // Set new values
            horizontalSelectorIcon?.y = intersectionY
            verticalSelectorIcon?.x = intersectionX
        } else {
            // Restore previous values and unset them
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
}
