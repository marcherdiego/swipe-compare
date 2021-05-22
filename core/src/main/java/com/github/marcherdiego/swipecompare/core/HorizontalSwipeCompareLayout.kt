package com.github.marcherdiego.swipecompare.core

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.LayoutInflater
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

    init {
        LayoutInflater.from(context).inflate(R.layout.horizontal_swipe_compare_layout, this)

        leftFragmentContainer = findViewById(R.id.fragment_left)
        leftFragmentContainer.id = leftFragmentContainer.hashCode()
        rightFragmentContainer = findViewById(R.id.fragment_right)
        rightFragmentContainer.id = rightFragmentContainer.hashCode()

        horizontalSlider = findViewById(R.id.slider)
        horizontalSelectorBar = horizontalSlider?.findViewById(R.id.selector_bar)
        horizontalSelectorIcon = horizontalSlider?.findViewById(R.id.selector_icon)

        leftFragmentContainer.clipToOutline = true
        rightFragmentContainer.clipToOutline = true
        init()
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
            replace(leftFragmentContainer.id, leftFragment)
            replace(rightFragmentContainer.id, rightFragment)
            commit()
        }
    }
    
    fun setViews(leftView: View, rightView: View) {
        leftFragmentContainer.removeAllViews()
        leftFragmentContainer.addView(leftView)

        rightFragmentContainer.removeAllViews()
        rightFragmentContainer.addView(rightView)
    }

    fun setSliderBarWidth(width: Int) {
        if (width == 0) {
            horizontalSelectorBar?.visibility = View.INVISIBLE
        } else {
            horizontalSelectorBar?.visibility = View.VISIBLE
            horizontalSelectorBar?.layoutParams = horizontalSelectorBar?.layoutParams?.apply {
                this.width = width
            }
        }
    }
}
