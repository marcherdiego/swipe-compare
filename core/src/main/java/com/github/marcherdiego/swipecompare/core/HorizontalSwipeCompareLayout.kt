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
class HorizontalSwipeCompareLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SwipeCompareLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.horizontal_swipe_compare_layout, this)
        init()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        val rightLine = (horizontalSlider?.x?.toInt() ?: 0) + horizontalSelectorWidth
        topLeftFragmentContainer?.clipBounds = Rect(0, 0, rightLine, height)
        topRightFragmentContainer?.clipBounds = Rect(rightLine, 0, width, height)
        super.onDraw(canvas)
    }

    fun setFragments(fragmentManager: FragmentManager, leftFragment: Fragment, rightFragment: Fragment) {
        fragmentManager.beginTransaction().apply {
            replace(topLeftFragmentContainer?.id ?: return, leftFragment)
            replace(topRightFragmentContainer?.id?: return, rightFragment)
            commit()
        }
    }
    
    fun setViews(leftView: View, rightView: View) {
        topLeftFragmentContainer?.removeAllViews()
        topLeftFragmentContainer?.addView(leftView)

        topRightFragmentContainer?.removeAllViews()
        topRightFragmentContainer?.addView(rightView)
    }

    fun setSliderBarWidth(width: Int) {
        if (width == 0) {
            horizontalSelectorBar?.visibility = View.INVISIBLE
        } else {
            horizontalSelectorBar?.visibility = View.VISIBLE
            horizontalSelectorBar?.layoutParams = horizontalSelectorBar?.layoutParams?.apply {
                this.width = width
            }
        }
    }
    
    fun setSliderPosition(sliderX: Float, sliderIconY: Float = horizontalSelectorIcon?.y ?: 0f) {
        horizontalSlider?.x = sliderX
        horizontalSelectorIcon?.y = sliderIconY
        invalidate()
    }
    
    fun setSliderIconPosition(y: Float) {
        horizontalSelectorIcon?.y = y
    }

    fun setSliderPositionChangedListener(listener: (Float) -> Unit) {
        horizontalSliderValueListener = listener
    }

    override fun setSliderBarColor(@ColorInt color: Int) {
        horizontalSelectorBar?.setBackgroundColor(color)
    }

    override fun setSliderBarColorRes(@ColorRes color: Int) {
        horizontalSelectorBar?.setBackgroundColor(ContextCompat.getColor(context, color))
    }

    override fun setSliderIconColor(@ColorInt color: Int) {
        horizontalSelectorIcon?.setBackgroundColor(color)
    }

    override fun setSliderIconColorRes(@ColorRes color: Int) {
        horizontalSelectorIcon?.setBackgroundColor(ContextCompat.getColor(context, color))
    }

    override fun setSliderIconSize(width: Int, height: Int) {
        horizontalSelectorIcon?.layoutParams?.width = width
        horizontalSelectorIcon?.layoutParams?.height = height
    }

    override fun setSliderIconBackground(@DrawableRes background: Int) {
        horizontalSelectorIcon?.setBackgroundResource(background)
    }

    override fun setSliderIconBackground(background: Drawable) {
        horizontalSelectorIcon?.background = background
    }

    override fun setSliderIcon(@DrawableRes icon: Int) {
        horizontalSelectorIcon?.setImageResource(icon)
    }

    override fun setSliderIcon(icon: Drawable) {
        horizontalSelectorIcon?.setImageDrawable(icon)
    }

    override fun setSliderIconTint(@ColorRes color: Int) {
        horizontalSelectorIcon?.setColorFilter(ContextCompat.getColor(context, color), Mode.SRC_IN)
    }

    override fun setSliderIconPadding(left: Int, top: Int, right: Int, bottom: Int) {
        horizontalSelectorIcon?.setPadding(left, top, right, bottom)
    }

    override fun setFixedSliderIcon(fixed: Boolean) {
        fixedHorizontalSliderIcon = fixed
    }
}
