package com.github.marcherdiego.swipecompare.core.base

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PorterDuff.Mode
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.github.marcherdiego.swipecompare.core.R

@SuppressLint("ClickableViewAccessibility")
abstract class SwipeCompareLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    protected var topLeftFragmentContainer: FrameLayout? = null
    protected var topRightFragmentContainer: FrameLayout? = null
    protected var bottomLeftFragmentContainer: FrameLayout? = null
    protected var bottomRightFragmentContainer: FrameLayout? = null

    protected var horizontalSlider: ConstraintLayout? = null
    protected var horizontalSelectorBar: View? = null
    protected var horizontalSelectorIcon: ImageView? = null

    protected var verticalSlider: ConstraintLayout? = null
    protected var verticalSelectorBar: View? = null
    protected var verticalSelectorIcon: ImageView? = null

    private var lastHorizontalAction = 0
    private var lastVerticalAction = 0

    private var dX = 0f
    protected var horizontalSelectorWidth = 0
    protected var horizontalSelectorHeight = 0

    private var dY = 0f
    protected var verticalSelectorWidth = 0
    protected var verticalSelectorHeight = 0

    private var horizontalSelectorIconDy = 0f
    private var verticalSelectorIconDx = 0f

    protected fun init() {
        topLeftFragmentContainer = findViewById(R.id.fragment_top_left)
        topLeftFragmentContainer?.id = topLeftFragmentContainer.hashCode()

        topRightFragmentContainer = findViewById(R.id.fragment_top_right)
        topRightFragmentContainer?.id = topRightFragmentContainer.hashCode()

        bottomLeftFragmentContainer = findViewById(R.id.fragment_bottom_left)
        bottomLeftFragmentContainer?.id = bottomLeftFragmentContainer.hashCode()

        bottomRightFragmentContainer = findViewById(R.id.fragment_bottom_right)
        bottomRightFragmentContainer?.id = bottomRightFragmentContainer.hashCode()

        horizontalSlider = findViewById(R.id.horizontal_slider)
        horizontalSelectorBar = horizontalSlider?.findViewById(R.id.horizontal_selector_bar)
        horizontalSelectorIcon = horizontalSlider?.findViewById(R.id.horizontal_selector_icon)

        verticalSlider = findViewById(R.id.vertical_slider)
        verticalSelectorBar = verticalSlider?.findViewById(R.id.vertical_selector_bar)
        verticalSelectorIcon = verticalSlider?.findViewById(R.id.vertical_selector_icon)

        topLeftFragmentContainer?.clipToOutline = true
        topRightFragmentContainer?.clipToOutline = true
        bottomLeftFragmentContainer?.clipToOutline = true
        bottomRightFragmentContainer?.clipToOutline = true
        horizontalSlider?.post {
            horizontalSelectorWidth = (horizontalSelectorIcon?.width ?: 0) / 2
            horizontalSelectorHeight = horizontalSelectorIcon?.height ?: 0
            invalidate()
        }
        horizontalSlider?.setOnTouchListener { view, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    dX = view.x - event.rawX
                    horizontalSelectorIconDy = (horizontalSelectorIcon?.y ?: 0f) - event.rawY
                    lastHorizontalAction = MotionEvent.ACTION_DOWN
                    checkUnifiedController(event)
                }
                MotionEvent.ACTION_MOVE -> {
                    val newX = event.rawX + dX
                    if (newX > -horizontalSelectorWidth && newX < measuredWidth - horizontalSelectorWidth) {
                        view.x = newX
                        lastHorizontalAction = MotionEvent.ACTION_MOVE
                        checkUnifiedController(event)
                    }
                    horizontalSelectorIcon?.y = adjustToBounds(event.rawY + horizontalSelectorIconDy, 0, measuredHeight - horizontalSelectorHeight)
                }
                MotionEvent.ACTION_UP -> if (lastHorizontalAction != MotionEvent.ACTION_DOWN) {
                    return@setOnTouchListener false
                }
            }
            invalidate()
            return@setOnTouchListener true
        }
        verticalSlider?.post {
            verticalSelectorWidth = verticalSelectorIcon?.width ?: 0
            verticalSelectorHeight = (verticalSelectorIcon?.height ?: 0) / 2
            invalidate()
        }
        verticalSlider?.setOnTouchListener { view, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    dY = view.y - event.rawY
                    verticalSelectorIconDx = (verticalSelectorIcon?.x ?: 0f) - event.rawX
                    lastVerticalAction = MotionEvent.ACTION_DOWN
                }
                MotionEvent.ACTION_MOVE -> {
                    val newY = event.rawY + dY
                    if (newY > -verticalSelectorHeight && newY < measuredHeight - verticalSelectorHeight) {
                        view.y = newY
                        lastVerticalAction = MotionEvent.ACTION_MOVE
                    }
                    verticalSelectorIcon?.x = adjustToBounds(event.rawX + verticalSelectorIconDx, 0, measuredWidth - verticalSelectorWidth)
                }
                MotionEvent.ACTION_UP -> if (lastVerticalAction != MotionEvent.ACTION_DOWN) {
                    return@setOnTouchListener false
                }
            }
            invalidate()
            return@setOnTouchListener true
        }

        setWillNotDraw(false)
    }

    internal open fun checkUnifiedController(event: MotionEvent) {}

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

    private fun adjustToBounds(value: Float, lowerBound: Int, upperBound: Int): Float {
        return when {
            value < lowerBound -> lowerBound.toFloat()
            value > upperBound -> upperBound.toFloat()
            else -> value
        }
    }
}
