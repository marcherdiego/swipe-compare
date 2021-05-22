package com.github.marcherdiego.swipecompare.core.base

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PorterDuff.Mode
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
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

    private var lastAction = 0

    private var dX = 0f
    protected var selectorWidth = 0

    private var dY = 0f
    protected var selectorHeight = 0

    private var horizontalSelectorIconDy = 0f
    private var verticalSelectorIconDx = 0f

    protected fun init() {
        horizontalSlider?.post {
            selectorWidth = (horizontalSelectorIcon?.width ?: 0) / 2
            invalidate()
        }
        horizontalSlider?.setOnTouchListener { view, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    dX = view.x - event.rawX
                    horizontalSelectorIconDy = (horizontalSelectorIcon?.y ?: 0f) - event.rawY
                    lastAction = MotionEvent.ACTION_DOWN
                    checkUnifiedController(event)
                }
                MotionEvent.ACTION_MOVE -> {
                    val newX = event.rawX + dX
                    val newHorizontalSelectorIconY = event.rawY + horizontalSelectorIconDy
                    if (newX > -selectorWidth && newX < this.width - selectorWidth) {
                        view.x = newX
                        lastAction = MotionEvent.ACTION_MOVE
                        horizontalSelectorIcon?.y = newHorizontalSelectorIconY
                        checkUnifiedController(event)
                    }
                }
                MotionEvent.ACTION_UP -> if (lastAction != MotionEvent.ACTION_DOWN) {
                    return@setOnTouchListener false
                }
            }
            invalidate()
            return@setOnTouchListener true
        }
        verticalSlider?.post {
            selectorHeight = (verticalSlider?.height ?: 0) / 2
            invalidate()
        }
        verticalSlider?.setOnTouchListener { view, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    dY = view.y - event.rawY
                    verticalSelectorIconDx = (verticalSelectorIcon?.x ?: 0f) - event.rawX
                    lastAction = MotionEvent.ACTION_DOWN
                }
                MotionEvent.ACTION_MOVE -> {
                    val newY = event.rawY + dY
                    val newVerticalSelectorIconX = event.rawX + verticalSelectorIconDx
                    if (newY > -selectorHeight && newY < this.height - selectorHeight) {
                        view.y = newY
                        lastAction = MotionEvent.ACTION_MOVE
                        verticalSelectorIcon?.x = newVerticalSelectorIconX
                    }
                }
                MotionEvent.ACTION_UP -> if (lastAction != MotionEvent.ACTION_DOWN) {
                    return@setOnTouchListener false
                }
            }
            invalidate()
            return@setOnTouchListener true
        }

        setWillNotDraw(false)
    }

    open fun checkUnifiedController(event: MotionEvent) {}

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
