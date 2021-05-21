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
class VerticalSwipeCompareLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SwipeCompareLayout(context, attrs, defStyleAttr) {

    private val topFragmentContainer: FrameLayout
    private val bottomFragmentContainer: FrameLayout

    private var dY = 0f
    private var selectorHeight = 0

    init {
        LayoutInflater.from(context).inflate(R.layout.vertical_swipe_compare_layout, this)

        topFragmentContainer = findViewById(R.id.fragment_top)
        bottomFragmentContainer = findViewById(R.id.fragment_bottom)
        verticalSlider = findViewById(R.id.slider)
        verticalSelectorBar = verticalSlider?.findViewById(R.id.selector_bar)
        verticalSelectorIcon = verticalSlider?.findViewById(R.id.selector_icon)
        verticalSelectorIcon?.post {
            selectorHeight = (verticalSelectorIcon?.height ?: 0) / 2
            invalidate()
        }
        verticalSlider?.setOnTouchListener { view, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    dY = view.y - event.rawY
                    lastAction = MotionEvent.ACTION_DOWN
                }
                MotionEvent.ACTION_MOVE -> {
                    val newY = event.rawY + dY
                    if (newY > -selectorHeight && newY < this.height - selectorHeight) {
                        view.y = newY
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

        topFragmentContainer.clipToOutline = true
        bottomFragmentContainer.clipToOutline = true
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        val bottomLine = (verticalSlider?.y?.toInt() ?: 0) + selectorHeight
        topFragmentContainer.clipBounds = Rect(0, 0, width, bottomLine)
        bottomFragmentContainer.clipBounds = Rect(0, bottomLine, width, height)
        super.onDraw(canvas)
    }

    fun setFragments(fragmentManager: FragmentManager, topFragment: Fragment, bottomFragment: Fragment) {
        fragmentManager.beginTransaction().apply {
            replace(R.id.fragment_top, topFragment)
            replace(R.id.fragment_bottom, bottomFragment)
            commit()
        }
    }

    fun setSliderBarHeight(height: Int) {
        if (height == 0) {
            verticalSelectorBar?.visibility = View.INVISIBLE
        } else {
            verticalSelectorBar?.visibility = View.VISIBLE
            verticalSelectorBar?.layoutParams?.height = height
        }
    }
}
