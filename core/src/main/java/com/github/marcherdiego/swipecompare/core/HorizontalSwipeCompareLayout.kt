package com.github.marcherdiego.swipecompare.core

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.github.marcherdiego.swipecompare.core.base.SwipeCompareLayout

@SuppressLint("ClickableViewAccessibility")
class HorizontalSwipeCompareLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SwipeCompareLayout(context, attrs, defStyleAttr) {

    private val leftFragmentContainer: FrameLayout
    private val rightFragmentContainer: FrameLayout

    private var dX = 0f
    private var selectorWidth = 0

    init {
        LayoutInflater.from(context).inflate(R.layout.horizontal_swipe_compare_layout, this)

        leftFragmentContainer = findViewById(R.id.fragment_left)
        rightFragmentContainer = findViewById(R.id.fragment_right)

        horizontalSlider = findViewById(R.id.slider)
        horizontalSelectorBar = horizontalSlider?.findViewById(R.id.selector_bar)
        horizontalSelectorIcon = horizontalSlider?.findViewById(R.id.selector_icon)

        horizontalSelectorIcon?.post {
            selectorWidth = (horizontalSelectorIcon?.width ?: 0) / 2
            invalidate()
        }
        horizontalSlider?.setOnTouchListener { view, event ->
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
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        val rightLine = (horizontalSlider?.x?.toInt() ?: 0) + selectorWidth
        leftFragmentContainer.clipBounds = Rect(0, 0, rightLine, height)
        rightFragmentContainer.clipBounds = Rect(rightLine, 0, width, height)
        super.onDraw(canvas)
    }

    fun setFragments(fragmentManager: FragmentManager, leftFragment: Fragment, rightFragment: Fragment) {
        fragmentManager.beginTransaction().apply {
            replace(R.id.fragment_left, leftFragment)
            replace(R.id.fragment_right, rightFragment)
            commit()
        }
    }

    fun setSliderBarWidth(width: Int) {
        if (width == 0) {
            horizontalSelectorBar?.visibility = View.INVISIBLE
        } else {
            horizontalSelectorBar?.visibility = View.VISIBLE
            horizontalSelectorBar?.layoutParams?.width = width
        }
    }
}
