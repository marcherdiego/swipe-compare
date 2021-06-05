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

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.HorizontalSwipeCompareLayout, 0, 0)
        val leftViewId = attributes.getResourceId(R.styleable.HorizontalSwipeCompareLayout_horizontal_left_view, 0)
        val rightViewId = attributes.getResourceId(R.styleable.HorizontalSwipeCompareLayout_horizontal_right_view, 0)
        val sliderBarWidth = attributes.getDimensionPixelSize(R.styleable.HorizontalSwipeCompareLayout_horizontal_slider_bar_width, 0)
        val sliderBarColor = attributes.getColor(R.styleable.HorizontalSwipeCompareLayout_horizontal_slider_bar_color, 0)
        var sliderIconWidth = attributes.getDimensionPixelSize(R.styleable.HorizontalSwipeCompareLayout_horizontal_slider_icon_width, 0)
        val sliderIconHeight = attributes.getDimensionPixelSize(R.styleable.HorizontalSwipeCompareLayout_horizontal_slider_icon_height, 0)
        val sliderIconColor = attributes.getColor(R.styleable.HorizontalSwipeCompareLayout_horizontal_slider_icon_color, 0)
        val sliderIconImage = attributes.getResourceId(R.styleable.HorizontalSwipeCompareLayout_horizontal_slider_icon_image, 0)
        val touchEnabled = attributes.getBoolean(R.styleable.HorizontalSwipeCompareLayout_horizontal_touch_enabled, true)
        val fixedSliderIcon = attributes.getBoolean(R.styleable.HorizontalSwipeCompareLayout_fixed_horizontal_slider_icon, false)
        val horizontalSliderIconPaddingLeft = attributes.getDimensionPixelSize(R.styleable.HorizontalSwipeCompareLayout_horizontal_slider_icon_padding_left, 0)
        val horizontalSliderIconPaddingTop = attributes.getDimensionPixelSize(R.styleable.HorizontalSwipeCompareLayout_horizontal_slider_icon_padding_top, 0)
        val horizontalSliderIconPaddingRight = attributes.getDimensionPixelSize(R.styleable.HorizontalSwipeCompareLayout_horizontal_slider_icon_padding_right, 0)
        val horizontalSliderIconPaddingBottom = attributes.getDimensionPixelSize(R.styleable.HorizontalSwipeCompareLayout_horizontal_slider_icon_padding_bottom, 0)
        attributes.recycle()

        setSliderBarWidth(sliderBarWidth)
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
        setSliderIconPadding(horizontalSliderIconPaddingLeft, horizontalSliderIconPaddingTop, horizontalSliderIconPaddingRight, horizontalSliderIconPaddingBottom)

        val leftView = getViewFromId(context, leftViewId)
        val rightView = getViewFromId(context, rightViewId)
        setViews(leftView, rightView)
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
