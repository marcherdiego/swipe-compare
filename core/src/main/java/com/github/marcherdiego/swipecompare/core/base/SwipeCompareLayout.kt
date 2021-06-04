package com.github.marcherdiego.swipecompare.core.base

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ImageView.ScaleType.CENTER_CROP
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.marcherdiego.swipecompare.core.R

@SuppressLint("ClickableViewAccessibility")
abstract class SwipeCompareLayout<T> @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    protected var horizontalSliderValueListener: ((Float) -> Unit)? = null
    protected var verticalSliderValueListener: ((Float) -> Unit)? = null
    protected var crosshairSliderValueListener: ((Float, Float) -> Unit)? = null

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

    protected var fixedHorizontalSliderIcon = false
    protected var fixedVerticalSliderIcon = false

    var allowTouchControl = true

    protected open fun init() {
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

        setupHorizontalSlider()
        setupVerticalSlider()

        setupTouchControl()

        setWillNotDraw(false)
    }

    protected fun getViewFromId(context: Context, id: Int): View {
        return try {
            when (context.resources.getResourceTypeName(id)) {
                RESOURCE_TYPE_LAYOUT -> LayoutInflater.from(context).inflate(id, null)
                RESOURCE_TYPE_DRAWABLE -> ImageView(context).apply {
                    scaleType = CENTER_CROP
                    setImageResource(id)
                }
                else -> getFullScreenView(context)
            }
        } catch (e: Exception) {
            getFullScreenView(context)
        }
    }

    protected abstract fun setupTouchControl()

    private fun setupHorizontalSlider() {
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
                        horizontalSliderValueListener?.invoke(newX)
                        notifyCrosshairChanged()
                        checkUnifiedController(event)
                    }
                    if (fixedHorizontalSliderIcon.not()) {
                        horizontalSelectorIcon?.y = adjustToBounds(
                            event.rawY + horizontalSelectorIconDy,
                            0,
                            measuredHeight - horizontalSelectorHeight
                        )
                    }
                }
                MotionEvent.ACTION_UP -> if (lastHorizontalAction != MotionEvent.ACTION_DOWN) {
                    return@setOnTouchListener false
                }
            }
            invalidate()
            return@setOnTouchListener true
        }
    }

    private fun setupVerticalSlider() {
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
                        verticalSliderValueListener?.invoke(newY)
                        notifyCrosshairChanged()
                        lastVerticalAction = MotionEvent.ACTION_MOVE
                    }
                    if (fixedVerticalSliderIcon.not()) {
                        verticalSelectorIcon?.x = adjustToBounds(
                            event.rawX + verticalSelectorIconDx,
                            0,
                            measuredWidth - verticalSelectorWidth
                        )
                    }
                }
                MotionEvent.ACTION_UP -> if (lastVerticalAction != MotionEvent.ACTION_DOWN) {
                    return@setOnTouchListener false
                }
            }
            invalidate()
            return@setOnTouchListener true
        }
    }

    private fun notifyCrosshairChanged() {
        crosshairSliderValueListener?.invoke(horizontalSlider?.x ?: return, verticalSlider?.y ?: return)
    }

    internal open fun checkUnifiedController(event: MotionEvent) {}

    abstract fun setFixedSliderIcon(fixed: Boolean): T

    abstract fun setSliderBarColor(@ColorInt color: Int): T

    abstract fun setSliderBarColorRes(@ColorRes color: Int): T

    abstract fun setSliderIconColor(@ColorInt color: Int): T

    abstract fun setSliderIconColorRes(@ColorRes color: Int): T

    abstract fun setSliderIconSize(width: Int, height: Int): T

    abstract fun setSliderIconBackground(@DrawableRes background: Int): T

    abstract fun setSliderIconBackground(background: Drawable?): T

    abstract fun setSliderIcon(@DrawableRes icon: Int): T

    abstract fun setSliderIcon(icon: Drawable?): T

    abstract fun setSliderIconTint(@ColorRes color: Int): T

    abstract fun setSliderIconPadding(left: Int, top: Int, right: Int, bottom: Int): T

    fun getTopLeft() = topLeftFragmentContainer

    fun getTopRight() = topRightFragmentContainer

    fun getBottomLeft() = bottomLeftFragmentContainer

    fun getBottomRight() = bottomRightFragmentContainer

    fun isFixedHorizontalSliderIcon() = fixedHorizontalSliderIcon

    fun isFixedVerticalSliderIcon() = fixedVerticalSliderIcon

    private fun adjustToBounds(value: Float, lowerBound: Int, upperBound: Int): Float {
        return when {
            value < lowerBound -> lowerBound.toFloat()
            value > upperBound -> upperBound.toFloat()
            else -> value
        }
    }

    private fun getFullScreenView(context: Context): View {
        return View(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        }
    }

    companion object {
        private const val RESOURCE_TYPE_DRAWABLE = "drawable"
        private const val RESOURCE_TYPE_LAYOUT = "layout"
    }
}
