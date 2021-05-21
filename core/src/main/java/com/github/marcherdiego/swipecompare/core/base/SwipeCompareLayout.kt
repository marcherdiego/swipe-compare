package com.github.marcherdiego.swipecompare.core.base

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PorterDuff.Mode
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat

@SuppressLint("ClickableViewAccessibility")
abstract class SwipeCompareLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    protected var horizontalSlider: ConstraintLayout? = null
    protected var horizontalSelectorBar: View? = null
    protected var horizontalSelectorIcon: ImageView? = null

    protected var verticalSlider: ConstraintLayout? = null
    protected var verticalSelectorBar: View? = null
    protected var verticalSelectorIcon: ImageView? = null

    protected var lastAction = 0

    init {
        setWillNotDraw(false)
    }

    fun setSliderBarColor(@ColorInt color: Int) {
        horizontalSelectorBar?.setBackgroundColor(color)
        verticalSelectorBar?.setBackgroundColor(color)
    }

    fun setSliderBarColorRes(@ColorRes color: Int) {
        horizontalSelectorBar?.setBackgroundColor(ContextCompat.getColor(context, color))
        verticalSelectorBar?.setBackgroundColor(ContextCompat.getColor(context, color))
    }

    fun setSliderIconColor(@ColorInt color: Int) {
        horizontalSelectorIcon?.setBackgroundColor(color)
        verticalSelectorIcon?.setBackgroundColor(color)
    }

    fun setSliderIconColorRes(@ColorRes color: Int) {
        horizontalSelectorIcon?.setBackgroundColor(ContextCompat.getColor(context, color))
        verticalSelectorIcon?.setBackgroundColor(ContextCompat.getColor(context, color))
    }

    fun setSliderIconSize(width: Int, height: Int) {
        horizontalSelectorIcon?.layoutParams?.width = width
        horizontalSelectorIcon?.layoutParams?.height = height

        verticalSelectorIcon?.layoutParams?.width = width
        verticalSelectorIcon?.layoutParams?.height = height
    }

    fun setSliderIconBackground(@DrawableRes background: Int) {
        horizontalSelectorIcon?.setBackgroundResource(background)
        verticalSelectorIcon?.setBackgroundResource(background)
    }

    fun setSliderIconBackground(background: Drawable) {
        horizontalSelectorIcon?.background = background
        verticalSelectorIcon?.background = background
    }

    fun setSliderIcon(@DrawableRes icon: Int) {
        horizontalSelectorIcon?.setImageResource(icon)
        verticalSelectorIcon?.setImageResource(icon)
    }

    fun setSliderIcon(icon: Drawable) {
        horizontalSelectorIcon?.setImageDrawable(icon)
        verticalSelectorIcon?.setImageDrawable(icon)
    }

    fun setSliderIconTint(@ColorRes color: Int) {
        horizontalSelectorIcon?.setColorFilter(ContextCompat.getColor(context, color), Mode.SRC_IN)
        verticalSelectorIcon?.setColorFilter(ContextCompat.getColor(context, color), Mode.SRC_IN)
    }

    fun setSliderIconPadding(left: Int, top: Int, right: Int, bottom: Int) {
        horizontalSelectorIcon?.setPadding(left, top, right, bottom)
        verticalSelectorIcon?.setPadding(left, top, right, bottom)
    }
}
