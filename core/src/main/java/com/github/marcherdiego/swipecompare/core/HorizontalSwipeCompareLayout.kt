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
open class HorizontalSwipeCompareLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SwipeCompareLayout<HorizontalSwipeCompareLayout>(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.horizontal_swipe_compare_layout, this)
        init()
    }

    override fun setupTouchControl() {
        setOnTouchListener { _, event ->
            return@setOnTouchListener if (allowTouchControl) {
                fixedHorizontalSliderIcon = true

                horizontalSlider?.x = event.x - horizontalSelectorWidth
                horizontalSlider?.dispatchTouchEvent(event)

                fixedHorizontalSliderIcon = false
                true
            } else {
                false
            }
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        val rightLine = (horizontalSlider?.x?.toInt() ?: 0) + horizontalSelectorWidth
        topLeftFragmentContainer?.clipBounds = Rect(0, 0, rightLine, height)
        topRightFragmentContainer?.clipBounds = Rect(rightLine, 0, width, height)
        super.onDraw(canvas)
    }

    fun setFragments(fragmentManager: FragmentManager, leftFragment: Fragment, rightFragment: Fragment): HorizontalSwipeCompareLayout {
        fragmentManager.beginTransaction().apply {
            replace(topLeftFragmentContainer?.id ?: return this@HorizontalSwipeCompareLayout, leftFragment)
            replace(topRightFragmentContainer?.id ?: return this@HorizontalSwipeCompareLayout, rightFragment)
            commit()
        }
        return this
    }

    fun setViews(leftView: View, rightView: View): HorizontalSwipeCompareLayout {
        topLeftFragmentContainer?.removeAllViews()
        topLeftFragmentContainer?.addView(leftView)

        topRightFragmentContainer?.removeAllViews()
        topRightFragmentContainer?.addView(rightView)
        return this
    }

    fun setSliderBarWidth(width: Int): HorizontalSwipeCompareLayout {
        if (width == 0) {
            horizontalSelectorBar?.visibility = View.INVISIBLE
        } else {
            horizontalSelectorBar?.visibility = View.VISIBLE
            horizontalSelectorBar?.layoutParams = horizontalSelectorBar?.layoutParams?.apply {
                this.width = width
            }
        }
        return this
    }

    fun setSliderPosition(sliderX: Float, sliderIconY: Float = horizontalSelectorIcon?.y ?: 0f): HorizontalSwipeCompareLayout {
        horizontalSlider?.x = sliderX
        horizontalSelectorIcon?.y = sliderIconY
        invalidate()
        return this
    }

    fun centerSliderIconColor(): HorizontalSwipeCompareLayout {
        setSliderIconPosition(height / 2 - (horizontalSelectorIcon?.height?.toFloat()?.div(2f) ?: 0f))
        return this
    }

    fun setSliderIconPosition(y: Float): HorizontalSwipeCompareLayout {
        horizontalSelectorIcon?.y = y
        return this
    }

    fun setSliderPositionChangedListener(listener: (Float) -> Unit): HorizontalSwipeCompareLayout {
        horizontalSliderValueListener = listener
        return this
    }

    override fun setSliderBarColor(@ColorInt color: Int): HorizontalSwipeCompareLayout {
        horizontalSelectorBar?.setBackgroundColor(color)
        return this
    }

    override fun setSliderBarColorRes(@ColorRes color: Int): HorizontalSwipeCompareLayout {
        horizontalSelectorBar?.setBackgroundColor(ContextCompat.getColor(context, color))
        return this
    }

    override fun setSliderIconColor(@ColorInt color: Int): HorizontalSwipeCompareLayout {
        horizontalSelectorIcon?.setBackgroundColor(color)
        return this
    }

    override fun setSliderIconColorRes(@ColorRes color: Int): HorizontalSwipeCompareLayout {
        horizontalSelectorIcon?.setBackgroundColor(ContextCompat.getColor(context, color))
        return this
    }

    override fun setSliderIconSize(width: Int, height: Int): HorizontalSwipeCompareLayout {
        horizontalSelectorIcon?.layoutParams?.width = width
        horizontalSelectorIcon?.layoutParams?.height = height
        return this
    }

    override fun setSliderIconBackground(@DrawableRes background: Int): HorizontalSwipeCompareLayout {
        horizontalSelectorIcon?.setBackgroundResource(background)
        return this
    }

    override fun setSliderIconBackground(background: Drawable?): HorizontalSwipeCompareLayout {
        horizontalSelectorIcon?.background = background
        return this
    }

    override fun setSliderIcon(@DrawableRes icon: Int): HorizontalSwipeCompareLayout {
        horizontalSelectorIcon?.setImageResource(icon)
        return this
    }

    override fun setSliderIcon(icon: Drawable?): HorizontalSwipeCompareLayout {
        horizontalSelectorIcon?.setImageDrawable(icon)
        return this
    }

    override fun setSliderIconTint(@ColorRes color: Int): HorizontalSwipeCompareLayout {
        horizontalSelectorIcon?.setColorFilter(ContextCompat.getColor(context, color), Mode.SRC_IN)
        return this
    }

    override fun setSliderIconPadding(left: Int, top: Int, right: Int, bottom: Int): HorizontalSwipeCompareLayout {
        horizontalSelectorIcon?.setPadding(left, top, right, bottom)
        return this
    }

    override fun setFixedSliderIcon(fixed: Boolean): HorizontalSwipeCompareLayout {
        fixedHorizontalSliderIcon = fixed
        return this
    }
}
