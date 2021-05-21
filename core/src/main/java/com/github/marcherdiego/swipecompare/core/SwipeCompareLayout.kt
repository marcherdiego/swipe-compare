package com.github.marcherdiego.swipecompare.core

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.PorterDuff.Mode
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

@SuppressLint("ClickableViewAccessibility")
class SwipeCompareLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val leftFragmentContainer: FrameLayout
    private val rightFragmentContainer: FrameLayout

    private val slider: ConstraintLayout
    private val selectorBar: View
    private val selectorIcon: ImageView

    private var dX = 0f
    private var lastAction = 0
    private var selectorWidth = 0

    init {
        LayoutInflater.from(context).inflate(R.layout.swipe_compare_layout, this)

        leftFragmentContainer = findViewById(R.id.fragment_left)
        rightFragmentContainer = findViewById(R.id.fragment_right)
        slider = findViewById(R.id.slider)
        selectorBar = slider.findViewById(R.id.selector_bar)
        selectorIcon = slider.findViewById(R.id.selector_icon)
        selectorIcon.post {
            selectorWidth = (selectorIcon?.width ?: 0) / 2
            invalidate()
        }
        slider.setOnTouchListener { view, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    dX = view.x - event.rawX
                    lastAction = MotionEvent.ACTION_DOWN
                }
                MotionEvent.ACTION_MOVE -> {
                    val newX = event.rawX + dX
                    if (newX > -selectorWidth && newX < this.width - selectorWidth) {
                        view.x = newX
                        lastAction = MotionEvent.ACTION_MOVE
                    }
                }
                MotionEvent.ACTION_UP -> if (lastAction != MotionEvent.ACTION_DOWN) {
                    return@setOnTouchListener false
                }
            }
            invalidate()
            return@setOnTouchListener true
        }

        leftFragmentContainer.clipToOutline = true
        rightFragmentContainer.clipToOutline = true
        setWillNotDraw(false)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        rightFragmentContainer.clipBounds = Rect(slider.x.toInt() + selectorWidth, 0, width, height)
        super.onDraw(canvas)
    }

    fun setFragments(fragmentManager: FragmentManager, leftFragment: Fragment, rightFragment: Fragment) {
        fragmentManager.beginTransaction().apply {
            replace(R.id.fragment_left, leftFragment)
            replace(R.id.fragment_right, rightFragment)
            commit()
        }
        invalidate()
    }

    fun setSliderBarColor(@ColorInt color: Int) {
        selectorBar.setBackgroundColor(color)
    }

    fun setSliderBarColorRes(@ColorRes color: Int) {
        selectorBar.setBackgroundColor(ContextCompat.getColor(context, color))
    }

    fun setSliderBarWidth(width: Int) {
        if (width == 0) {
            selectorBar.visibility = View.INVISIBLE
        } else {
            selectorBar.visibility = View.VISIBLE
            selectorBar.layoutParams.width = width
        }
    }

    fun setSliderIconColor(@ColorInt color: Int) {
        selectorIcon.setBackgroundColor(color)
    }

    fun setSliderIconColorRes(@ColorRes color: Int) {
        selectorIcon.setBackgroundColor(ContextCompat.getColor(context, color))
    }

    fun setSliderIconSize(width: Int, height: Int) {
        selectorIcon.layoutParams.width = width
        selectorIcon.layoutParams.height = height
    }

    fun setSliderIconBackground(@DrawableRes background: Int) {
        selectorIcon.setBackgroundResource(background)
    }

    fun setSliderIconBackground(background: Drawable) {
        selectorIcon.background = background
    }

    fun setSliderIcon(@DrawableRes icon: Int) {
        selectorIcon.setImageResource(icon)
    }

    fun setSliderIcon(icon: Drawable) {
        selectorIcon.setImageDrawable(icon)
    }

    fun setSliderIconTint(@ColorRes color: Int) {
        selectorIcon.setColorFilter(ContextCompat.getColor(context, color), Mode.SRC_IN);
    }

    fun setSliderIconPadding(left: Int, top: Int, right: Int, bottom: Int) {
        selectorIcon.setPadding(left, top, right, bottom)
    }
}
