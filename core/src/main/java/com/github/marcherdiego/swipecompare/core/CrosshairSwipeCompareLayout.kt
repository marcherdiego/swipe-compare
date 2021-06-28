package com.github.marcherdiego.swipecompare.core

import android.annotation.SuppressLint
import android.content.Context
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
import com.github.marcherdiego.swipecompare.core.extensions.getDimen
import com.github.marcherdiego.swipecompare.core.extensions.getResId
import com.github.marcherdiego.swipecompare.core.extensions.getColor

@SuppressLint("ClickableViewAccessibility")
open class CrosshairSwipeCompareLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SwipeCompareLayout<CrosshairSwipeCompareLayout>(context, attrs, defStyleAttr) {

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

        val a = context.obtainStyledAttributes(attrs, R.styleable.CrosshairSwipeCompareLayout, 0, 0)
        val topLeftViewId = a.getResId(R.styleable.CrosshairSwipeCompareLayout_crosshair_top_left_view)
        val topRightViewId = a.getResId(R.styleable.CrosshairSwipeCompareLayout_crosshair_top_right_view)
        val bottomLeftViewId = a.getResId(R.styleable.CrosshairSwipeCompareLayout_crosshair_bottom_left_view)
        val bottomRightViewId = a.getResId(R.styleable.CrosshairSwipeCompareLayout_crosshair_bottom_right_view)

        val horizontalBarWidth = a.getDimen(R.styleable.CrosshairSwipeCompareLayout_crosshair_horizontal_slider_bar_width)
        val horizontalBarColor = a.getColor(R.styleable.CrosshairSwipeCompareLayout_crosshair_horizontal_slider_bar_color)
        var horizontalIconWidth = a.getDimen(R.styleable.CrosshairSwipeCompareLayout_crosshair_horizontal_slider_icon_width)
        val horizontalIconHeight = a.getDimen(R.styleable.CrosshairSwipeCompareLayout_crosshair_horizontal_slider_icon_height)
        val horizontalIconColor = a.getColor(R.styleable.CrosshairSwipeCompareLayout_crosshair_horizontal_slider_icon_color)
        val horizontalIconImage = a.getResId(R.styleable.CrosshairSwipeCompareLayout_crosshair_horizontal_slider_icon_image)
        val horizontalIconPaddingLeft = a.getDimen(R.styleable.CrosshairSwipeCompareLayout_crosshair_horizontal_slider_icon_padding_left)
        val horizontalIconPaddingTop = a.getDimen(R.styleable.CrosshairSwipeCompareLayout_crosshair_horizontal_slider_icon_padding_top)
        val horizontalIconPaddingRight = a.getDimen(R.styleable.CrosshairSwipeCompareLayout_crosshair_horizontal_slider_icon_padding_right)
        val horizontalIconPaddingBottom = a.getDimen(R.styleable.CrosshairSwipeCompareLayout_crosshair_horizontal_slider_icon_padding_bottom)

        val verticalBarHeight = a.getDimen(R.styleable.CrosshairSwipeCompareLayout_crosshair_vertical_slider_bar_height)
        val verticalBarColor = a.getColor(R.styleable.CrosshairSwipeCompareLayout_crosshair_vertical_slider_bar_color)
        var verticalIconWidth = a.getDimen(R.styleable.CrosshairSwipeCompareLayout_crosshair_vertical_slider_icon_width)
        val verticalIconHeight = a.getDimen(R.styleable.CrosshairSwipeCompareLayout_crosshair_vertical_slider_icon_height)
        val verticalIconColor = a.getColor(R.styleable.CrosshairSwipeCompareLayout_crosshair_vertical_slider_icon_color)
        val verticalIconImage = a.getResId(R.styleable.CrosshairSwipeCompareLayout_crosshair_vertical_slider_icon_image)
        val verticalIconPaddingLeft = a.getDimen(R.styleable.CrosshairSwipeCompareLayout_crosshair_vertical_slider_icon_padding_left)
        val verticalIconPaddingTop = a.getDimen(R.styleable.CrosshairSwipeCompareLayout_crosshair_vertical_slider_icon_padding_top)
        val verticalIconPaddingRight = a.getDimen(R.styleable.CrosshairSwipeCompareLayout_crosshair_vertical_slider_icon_padding_right)
        val verticalIconPaddingBottom = a.getDimen(R.styleable.CrosshairSwipeCompareLayout_crosshair_vertical_slider_icon_padding_bottom)

        val touchEnabled = a.getBoolean(R.styleable.CrosshairSwipeCompareLayout_crosshair_touch_enabled, true)
        val fixedIcon = a.getBoolean(R.styleable.CrosshairSwipeCompareLayout_fixed_crosshair_slider_icon, false)
        a.recycle()

        // Horizontal slider
        setSliderBarWidth(horizontalBarWidth)
        setHorizontalSliderBarColor(horizontalBarColor)
        if (horizontalIconWidth == 0) {
            horizontalIconWidth = horizontalIconHeight
        }
        if (horizontalIconWidth != 0) {
            setHorizontalSliderIconSize(horizontalIconWidth, horizontalIconHeight)
        }
        setHorizontalSliderIconColor(horizontalIconColor)
        if (horizontalIconImage != 0) {
            setHorizontalSliderIcon(ContextCompat.getDrawable(context, horizontalIconImage))
        }
        setHorizontalSliderIconPadding(horizontalIconPaddingLeft, horizontalIconPaddingTop, horizontalIconPaddingRight, horizontalIconPaddingBottom)

        // Vertical slider
        setSliderBarHeight(verticalBarHeight)
        setVerticalSliderBarColor(verticalBarColor)
        if (verticalIconWidth == 0) {
            verticalIconWidth = verticalIconHeight
        }
        if (verticalIconWidth != 0) {
            setVerticalSliderIconSize(verticalIconWidth, verticalIconHeight)
        }
        setVerticalSliderIconColor(verticalIconColor)
        if (verticalIconImage != 0) {
            setVerticalSliderIcon(ContextCompat.getDrawable(context, verticalIconImage))
        }
        setVerticalSliderIconPadding(verticalIconPaddingLeft, verticalIconPaddingTop, verticalIconPaddingRight, verticalIconPaddingBottom)

        allowTouchControl = touchEnabled
        setFixedSliderIcon(fixedIcon)

        val topLeftView = getViewFromId(context, topLeftViewId)
        val topRightView = getViewFromId(context, topRightViewId)
        val bottomLeftView = getViewFromId(context, bottomLeftViewId)
        val bottomRightView = getViewFromId(context, bottomRightViewId)
        setViews(topLeftView, topRightView, bottomLeftView, bottomRightView)
    }

    override fun setupTouchControl() {
        setOnTouchListener { _, event ->
            return@setOnTouchListener if (allowTouchControl) {
                unifiedControllers = true

                horizontalSlider?.x = event.x - horizontalSelectorWidth
                horizontalSlider?.dispatchTouchEvent(event)

                verticalSlider?.y = event.y - verticalSelectorHeight
                verticalSlider?.dispatchTouchEvent(event)

                unifiedControllers = false
                true
            } else {
                false
            }
        }
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
        val bottomLine = (verticalSlider?.y?.toInt() ?: 0) + verticalSelectorHeight
        val rightLine = (horizontalSlider?.x?.toInt() ?: 0) + horizontalSelectorWidth
        topLeftFragmentContainer?.clipBounds = Rect(0, 0, rightLine, bottomLine)
        topRightFragmentContainer?.clipBounds = Rect(rightLine, 0, width, bottomLine)

        bottomLeftFragmentContainer?.clipBounds = Rect(0, bottomLine, rightLine, height)
        bottomRightFragmentContainer?.clipBounds = Rect(rightLine, bottomLine, width, height)
        super.invalidate()
    }
    
    fun setFragments(
        fragmentManager: FragmentManager,
        topLeftFragment: Fragment,
        topRightFragment: Fragment,
        bottomLeftFragment: Fragment,
        bottomRightFragment: Fragment
    ): CrosshairSwipeCompareLayout {
        fragmentManager.beginTransaction().apply {
            replace(topLeftFragmentContainer?.id ?: return this@CrosshairSwipeCompareLayout, topLeftFragment)
            replace(topRightFragmentContainer?.id ?: return this@CrosshairSwipeCompareLayout, topRightFragment)
            replace(bottomLeftFragmentContainer?.id ?: return this@CrosshairSwipeCompareLayout, bottomLeftFragment)
            replace(bottomRightFragmentContainer?.id ?: return this@CrosshairSwipeCompareLayout, bottomRightFragment)
            commit()
        }
        return this
    }

    fun setViews(
        topLeftView: View,
        topRightView: View,
        bottomLeftView: View,
        bottomRightView: View
    ): CrosshairSwipeCompareLayout {
        topLeftFragmentContainer?.removeAllViews()
        topLeftFragmentContainer?.addView(topLeftView)

        topRightFragmentContainer?.removeAllViews()
        topRightFragmentContainer?.addView(topRightView)

        bottomLeftFragmentContainer?.removeAllViews()
        bottomLeftFragmentContainer?.addView(bottomLeftView)

        bottomRightFragmentContainer?.removeAllViews()
        bottomRightFragmentContainer?.addView(bottomRightView)
        return this
    }

    fun setSliderBarHeight(height: Int): CrosshairSwipeCompareLayout {
        if (height == 0) {
            verticalSelectorBar?.visibility = View.INVISIBLE
        } else {
            verticalSelectorBar?.visibility = View.VISIBLE
            verticalSelectorBar?.layoutParams?.height = height
        }
        return this
    }

    fun setSliderBarWidth(width: Int): CrosshairSwipeCompareLayout {
        if (width == 0) {
            horizontalSelectorBar?.visibility = View.INVISIBLE
        } else {
            horizontalSelectorBar?.visibility = View.VISIBLE
            horizontalSelectorBar?.layoutParams?.width = width
        }
        return this
    }

    fun setSliderPosition(sliderX: Float, sliderY: Float): CrosshairSwipeCompareLayout {
        horizontalSlider?.x = sliderX
        verticalSlider?.y = sliderY
        invalidate()
        return this
    }

    fun setSliderPositionChangedListener(listener: (Float, Float) -> Unit): CrosshairSwipeCompareLayout {
        crosshairSliderValueListener = listener
        return this
    }

    override fun setSliderBarColor(@ColorInt color: Int): CrosshairSwipeCompareLayout {
        horizontalSelectorBar?.setBackgroundColor(color)
        verticalSelectorBar?.setBackgroundColor(color)
        return this
    }

    fun setHorizontalSliderBarColor(@ColorInt color: Int): CrosshairSwipeCompareLayout {
        horizontalSelectorBar?.setBackgroundColor(color)
        return this
    }

    fun setVerticalSliderBarColor(@ColorInt color: Int): CrosshairSwipeCompareLayout {
        verticalSelectorBar?.setBackgroundColor(color)
        return this
    }

    override fun setSliderBarColorRes(@ColorRes color: Int): CrosshairSwipeCompareLayout {
        horizontalSelectorBar?.setBackgroundColor(ContextCompat.getColor(context, color))
        verticalSelectorBar?.setBackgroundColor(ContextCompat.getColor(context, color))
        return this
    }

    fun setHorizontalSliderIconPosition(y: Float): CrosshairSwipeCompareLayout {
        horizontalSelectorIcon?.y = y
        return this
    }

    fun setVerticalSliderIconPosition(x: Float): CrosshairSwipeCompareLayout {
        verticalSelectorIcon?.x = x
        return this
    }

    override fun setSliderIconColor(@ColorInt color: Int): CrosshairSwipeCompareLayout {
        horizontalSelectorIcon?.setBackgroundColor(color)
        verticalSelectorIcon?.setBackgroundColor(color)
        return this
    }

    fun setHorizontalSliderIconColor(@ColorInt color: Int): CrosshairSwipeCompareLayout {
        horizontalSelectorIcon?.setBackgroundColor(color)
        return this
    }

    fun setVerticalSliderIconColor(@ColorInt color: Int): CrosshairSwipeCompareLayout {
        verticalSelectorIcon?.setBackgroundColor(color)
        return this
    }

    override fun setSliderIconColorRes(@ColorRes color: Int): CrosshairSwipeCompareLayout {
        horizontalSelectorIcon?.setBackgroundColor(ContextCompat.getColor(context, color))
        verticalSelectorIcon?.setBackgroundColor(ContextCompat.getColor(context, color))
        return this
    }

    override fun setSliderIconSize(width: Int, height: Int): CrosshairSwipeCompareLayout {
        horizontalSelectorIcon?.layoutParams?.width = width
        horizontalSelectorIcon?.layoutParams?.height = height

        verticalSelectorIcon?.layoutParams?.width = width
        verticalSelectorIcon?.layoutParams?.height = height
        return this
    }

    fun setHorizontalSliderIconSize(width: Int, height: Int): CrosshairSwipeCompareLayout {
        horizontalSelectorIcon?.layoutParams?.width = width
        horizontalSelectorIcon?.layoutParams?.height = height
        return this
    }

    fun setVerticalSliderIconSize(width: Int, height: Int): CrosshairSwipeCompareLayout {
        verticalSelectorIcon?.layoutParams?.width = width
        verticalSelectorIcon?.layoutParams?.height = height
        return this
    }

    override fun setSliderIconBackground(@DrawableRes background: Int): CrosshairSwipeCompareLayout {
        horizontalSelectorIcon?.setBackgroundResource(background)
        verticalSelectorIcon?.setBackgroundResource(background)
        return this
    }

    override fun setSliderIconBackground(background: Drawable?): CrosshairSwipeCompareLayout {
        horizontalSelectorIcon?.background = background
        verticalSelectorIcon?.background = background
        return this
    }

    override fun setSliderIcon(@DrawableRes icon: Int): CrosshairSwipeCompareLayout {
        horizontalSelectorIcon?.setImageResource(icon)
        verticalSelectorIcon?.setImageResource(icon)
        return this
    }

    override fun setSliderIcon(icon: Drawable?): CrosshairSwipeCompareLayout {
        horizontalSelectorIcon?.setImageDrawable(icon)
        verticalSelectorIcon?.setImageDrawable(icon)
        return this
    }

    fun setHorizontalSliderIcon(icon: Drawable?): CrosshairSwipeCompareLayout {
        horizontalSelectorIcon?.setImageDrawable(icon)
        return this
    }

    fun setVerticalSliderIcon(icon: Drawable?): CrosshairSwipeCompareLayout {
        verticalSelectorIcon?.setImageDrawable(icon)
        return this
    }

    override fun setSliderIconTint(@ColorRes color: Int): CrosshairSwipeCompareLayout {
        horizontalSelectorIcon?.setColorFilter(ContextCompat.getColor(context, color), Mode.SRC_IN)
        verticalSelectorIcon?.setColorFilter(ContextCompat.getColor(context, color), Mode.SRC_IN)
        return this
    }

    override fun setSliderIconPadding(left: Int, top: Int, right: Int, bottom: Int): CrosshairSwipeCompareLayout {
        horizontalSelectorIcon?.setPadding(left, top, right, bottom)
        verticalSelectorIcon?.setPadding(left, top, right, bottom)
        return this
    }

    fun setHorizontalSliderIconPadding(left: Int, top: Int, right: Int, bottom: Int): CrosshairSwipeCompareLayout {
        horizontalSelectorIcon?.setPadding(left, top, right, bottom)
        return this
    }

    fun setVerticalSliderIconPadding(left: Int, top: Int, right: Int, bottom: Int): CrosshairSwipeCompareLayout {
        verticalSelectorIcon?.setPadding(left, top, right, bottom)
        return this
    }

    override fun setFixedSliderIcon(fixed: Boolean): CrosshairSwipeCompareLayout {
        unifiedControllers = fixed
        return this
    }
}
