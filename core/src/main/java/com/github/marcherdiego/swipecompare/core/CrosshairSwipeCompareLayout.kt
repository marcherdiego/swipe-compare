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
class CrosshairSwipeCompareLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SwipeCompareLayout(context, attrs, defStyleAttr) {

    private val topLeftFragmentContainer: FrameLayout
    private val topRightFragmentContainer: FrameLayout
    private val bottomLeftFragmentContainer: FrameLayout
    private val bottomRightFragmentContainer: FrameLayout

    private var dX = 0f
    private var selectorWidth = 0

    private var dY = 0f
    private var selectorHeight = 0

    private var horizontalSelectorIconDy = 0f
    private var verticalSelectorIconDx = 0f

    init {
        LayoutInflater.from(context).inflate(R.layout.crosshair_swipe_compare_layout, this)

        topLeftFragmentContainer = findViewById(R.id.fragment_top_left)
        topRightFragmentContainer = findViewById(R.id.fragment_top_right)
        bottomLeftFragmentContainer = findViewById(R.id.fragment_bottom_left)
        bottomRightFragmentContainer = findViewById(R.id.fragment_bottom_right)

        horizontalSlider = findViewById(R.id.horizontal_slider)
        horizontalSelectorBar = horizontalSlider?.findViewById(R.id.horizontal_selector_bar)
        horizontalSelectorIcon = horizontalSlider?.findViewById(R.id.horizontal_selector_icon)

        verticalSlider = findViewById(R.id.vertical_slider)
        verticalSelectorBar = verticalSlider?.findViewById(R.id.vertical_selector_bar)
        verticalSelectorIcon = verticalSlider?.findViewById(R.id.vertical_selector_icon)

        horizontalSlider?.post {
            selectorWidth = (horizontalSlider?.width ?: 0) / 2
            invalidate()
        }
        horizontalSlider?.setOnTouchListener { view, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    dX = view.x - event.rawX
                    horizontalSelectorIconDy = (horizontalSelectorIcon?.y ?: 0f) - event.rawY
                    lastAction = MotionEvent.ACTION_DOWN
                }
                MotionEvent.ACTION_MOVE -> {
                    val newX = event.rawX + dX
                    val newHorizontalSelectorIconY = event.rawY + horizontalSelectorIconDy
                    if (newX > -selectorWidth && newX < this.width - selectorWidth) {
                        view.x = newX
                        lastAction = MotionEvent.ACTION_MOVE
                        horizontalSelectorIcon?.y = newHorizontalSelectorIconY
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

        topLeftFragmentContainer.clipToOutline = true
        topRightFragmentContainer.clipToOutline = true
        bottomLeftFragmentContainer.clipToOutline = true
        bottomRightFragmentContainer.clipToOutline = true
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        val bottomLine = (verticalSlider?.y?.toInt() ?: 0) + selectorHeight
        val rightLine = (horizontalSlider?.x?.toInt() ?: 0) + selectorWidth
        topLeftFragmentContainer.clipBounds = Rect(0, 0, rightLine, bottomLine)
        topRightFragmentContainer.clipBounds = Rect(rightLine, 0, width, bottomLine)

        bottomLeftFragmentContainer.clipBounds = Rect(0, bottomLine, rightLine, height)
        bottomRightFragmentContainer.clipBounds = Rect(rightLine, bottomLine, width, height)
        super.onDraw(canvas)
    }

    fun setFragments(
        fragmentManager: FragmentManager,
        topLeftFragment: Fragment,
        topRightFragment: Fragment,
        bottomLeftFragment: Fragment,
        bottomRightFragment: Fragment
    ) {
        fragmentManager.beginTransaction().apply {
            replace(R.id.fragment_top_left, topLeftFragment)
            replace(R.id.fragment_top_right, topRightFragment)
            replace(R.id.fragment_bottom_left, bottomLeftFragment)
            replace(R.id.fragment_bottom_right, bottomRightFragment)
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

    fun setSliderBarWidth(width: Int) {
        if (width == 0) {
            horizontalSelectorBar?.visibility = View.INVISIBLE
        } else {
            horizontalSelectorBar?.visibility = View.VISIBLE
            horizontalSelectorBar?.layoutParams?.width = width
        }
    }
}
