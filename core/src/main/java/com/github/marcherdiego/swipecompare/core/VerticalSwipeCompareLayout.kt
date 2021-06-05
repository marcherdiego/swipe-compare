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
open class VerticalSwipeCompareLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SwipeCompareLayout<VerticalSwipeCompareLayout>(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.vertical_swipe_compare_layout, this)
        init()

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.VerticalSwipeCompareLayout, 0, 0)
        val topViewId = attributes.getResourceId(R.styleable.VerticalSwipeCompareLayout_vertical_top_view, 0)
        val bottomViewId = attributes.getResourceId(R.styleable.VerticalSwipeCompareLayout_vertical_bottom_view, 0)
        val sliderBarHeight = attributes.getDimensionPixelSize(R.styleable.VerticalSwipeCompareLayout_vertical_slider_bar_height, 0)
        val sliderBarColor = attributes.getColor(R.styleable.VerticalSwipeCompareLayout_vertical_slider_bar_color, 0)
        var sliderIconWidth = attributes.getDimensionPixelSize(R.styleable.VerticalSwipeCompareLayout_vertical_slider_icon_width, 0)
        val sliderIconHeight = attributes.getDimensionPixelSize(R.styleable.VerticalSwipeCompareLayout_vertical_slider_icon_height, 0)
        val sliderIconColor = attributes.getColor(R.styleable.VerticalSwipeCompareLayout_vertical_slider_icon_color, 0)
        val sliderIconImage = attributes.getResourceId(R.styleable.VerticalSwipeCompareLayout_vertical_slider_icon_image, 0)
        val touchEnabled = attributes.getBoolean(R.styleable.VerticalSwipeCompareLayout_vertical_touch_enabled, true)
        val fixedSliderIcon = attributes.getBoolean(R.styleable.VerticalSwipeCompareLayout_fixed_vertical_slider_icon, false)
        val verticalSliderIconPaddingLeft = attributes.getDimensionPixelSize(R.styleable.VerticalSwipeCompareLayout_vertical_slider_icon_padding_left, 0)
        val verticalSliderIconPaddingTop = attributes.getDimensionPixelSize(R.styleable.VerticalSwipeCompareLayout_vertical_slider_icon_padding_top, 0)
        val verticalSliderIconPaddingRight = attributes.getDimensionPixelSize(R.styleable.VerticalSwipeCompareLayout_vertical_slider_icon_padding_right, 0)
        val verticalSliderIconPaddingBottom = attributes.getDimensionPixelSize(R.styleable.VerticalSwipeCompareLayout_vertical_slider_icon_padding_bottom, 0)
        attributes.recycle()

        setSliderBarHeight(sliderBarHeight)
        setSliderBarColor(sliderBarColor)
        if (sliderIconWidth == 0) {
            sliderIconWidth = sliderIconHeight
        }
        if (sliderIconWidth != 0) {
            setSliderIconSize(sliderIconWidth, sliderIconHeight)
        }
        setSliderIconColor(sliderIconColor)
        if (sliderIconImage != 0) {
            setSliderIcon(ContextCompat.getDrawable(context, sliderIconImage))
        }
        allowTouchControl = touchEnabled
        setFixedSliderIcon(fixedSliderIcon)
        setSliderIconPadding(verticalSliderIconPaddingLeft, verticalSliderIconPaddingTop, verticalSliderIconPaddingRight, verticalSliderIconPaddingBottom)

        val topView = getViewFromId(context, topViewId)
        val bottomView = getViewFromId(context, bottomViewId)
        setViews(topView, bottomView)
    }

    override fun setupTouchControl() {
        setOnTouchListener { _, event ->
            return@setOnTouchListener if (allowTouchControl) {
                fixedVerticalSliderIcon = true

                verticalSlider?.y = event.y - verticalSelectorHeight
                verticalSlider?.dispatchTouchEvent(event)

                fixedVerticalSliderIcon = false
                true
            } else {
                false
            }
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        val bottomLine = (verticalSlider?.y?.toInt() ?: 0) + verticalSelectorHeight
        topLeftFragmentContainer?.clipBounds = Rect(0, 0, width, bottomLine)
        bottomLeftFragmentContainer?.clipBounds = Rect(0, bottomLine, width, height)
        super.onDraw(canvas)
    }

    fun setFragments(fragmentManager: FragmentManager, topFragment: Fragment, bottomFragment: Fragment): VerticalSwipeCompareLayout {
        fragmentManager.beginTransaction().apply {
            replace(topLeftFragmentContainer?.id ?: return this@VerticalSwipeCompareLayout, topFragment)
            replace(bottomLeftFragmentContainer?.id ?: return this@VerticalSwipeCompareLayout, bottomFragment)
            commit()
        }
        return this
    }

    fun setViews(topView: View, bottomView: View): VerticalSwipeCompareLayout {
        topLeftFragmentContainer?.removeAllViews()
        topLeftFragmentContainer?.addView(topView)

        bottomLeftFragmentContainer?.removeAllViews()
        bottomLeftFragmentContainer?.addView(bottomView)
        return this
    }

    fun setSliderBarHeight(height: Int): VerticalSwipeCompareLayout {
        if (height == 0) {
            verticalSelectorBar?.visibility = View.INVISIBLE
        } else {
            verticalSelectorBar?.visibility = View.VISIBLE
            verticalSelectorBar?.layoutParams = verticalSelectorBar?.layoutParams?.apply {
                this.height = height
            }
        }
        return this
    }

    fun setSliderPosition(sliderY: Float, sliderIconX: Float = verticalSelectorIcon?.x ?: 0f): VerticalSwipeCompareLayout {
        verticalSlider?.y = sliderY
        verticalSelectorIcon?.x = sliderIconX
        invalidate()
        return this
    }

    fun setSliderPositionChangedListener(listener: (Float) -> Unit): VerticalSwipeCompareLayout {
        verticalSliderValueListener = listener
        return this
    }

    fun setSliderIconPosition(x: Float): VerticalSwipeCompareLayout {
        verticalSelectorIcon?.x = x
        return this
    }

    fun centerSliderIconColor(): VerticalSwipeCompareLayout {
        setSliderIconPosition(width / 2 - (verticalSelectorIcon?.width?.toFloat()?.div(2f) ?: 0f))
        return this
    }

    override fun setSliderBarColor(@ColorInt color: Int): VerticalSwipeCompareLayout {
        verticalSelectorBar?.setBackgroundColor(color)
        return this
    }

    override fun setSliderBarColorRes(@ColorRes color: Int): VerticalSwipeCompareLayout {
        verticalSelectorBar?.setBackgroundColor(ContextCompat.getColor(context, color))
        return this
    }

    override fun setSliderIconColor(@ColorInt color: Int): VerticalSwipeCompareLayout {
        verticalSelectorIcon?.setBackgroundColor(color)
        return this
    }

    override fun setSliderIconColorRes(@ColorRes color: Int): VerticalSwipeCompareLayout {
        verticalSelectorIcon?.setBackgroundColor(ContextCompat.getColor(context, color))
        return this
    }

    override fun setSliderIconSize(width: Int, height: Int): VerticalSwipeCompareLayout {
        verticalSelectorIcon?.layoutParams?.width = width
        verticalSelectorIcon?.layoutParams?.height = height
        return this
    }

    override fun setSliderIconBackground(@DrawableRes background: Int): VerticalSwipeCompareLayout {
        verticalSelectorIcon?.setBackgroundResource(background)
        return this
    }

    override fun setSliderIconBackground(background: Drawable?): VerticalSwipeCompareLayout {
        verticalSelectorIcon?.background = background
        return this
    }

    override fun setSliderIcon(@DrawableRes icon: Int): VerticalSwipeCompareLayout {
        verticalSelectorIcon?.setImageResource(icon)
        return this
    }

    override fun setSliderIcon(icon: Drawable?): VerticalSwipeCompareLayout {
        verticalSelectorIcon?.setImageDrawable(icon)
        return this
    }

    override fun setSliderIconTint(@ColorRes color: Int): VerticalSwipeCompareLayout {
        verticalSelectorIcon?.setColorFilter(ContextCompat.getColor(context, color), Mode.SRC_IN)
        return this
    }

    override fun setSliderIconPadding(left: Int, top: Int, right: Int, bottom: Int): VerticalSwipeCompareLayout {
        verticalSelectorIcon?.setPadding(left, top, right, bottom)
        return this
    }

    override fun setFixedSliderIcon(fixed: Boolean): VerticalSwipeCompareLayout {
        fixedVerticalSliderIcon = fixed
        return this
    }
}
