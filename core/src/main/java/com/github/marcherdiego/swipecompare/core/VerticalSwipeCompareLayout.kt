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
class VerticalSwipeCompareLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SwipeCompareLayout(context, attrs, defStyleAttr) {

    private val topFragmentContainer: FrameLayout
    private val bottomFragmentContainer: FrameLayout
    
    init {
        LayoutInflater.from(context).inflate(R.layout.vertical_swipe_compare_layout, this)

        topFragmentContainer = findViewById(R.id.fragment_top)
        topFragmentContainer.id = topFragmentContainer.hashCode()
        
        bottomFragmentContainer = findViewById(R.id.fragment_bottom)
        bottomFragmentContainer.id = bottomFragmentContainer.hashCode()
        
        verticalSlider = findViewById(R.id.slider)
        verticalSelectorBar = verticalSlider?.findViewById(R.id.selector_bar)
        verticalSelectorIcon = verticalSlider?.findViewById(R.id.selector_icon)

        topFragmentContainer.clipToOutline = true
        bottomFragmentContainer.clipToOutline = true
        init()
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
            replace(topFragmentContainer.id, topFragment)
            replace(bottomFragmentContainer.id, bottomFragment)
            commit()
        }
    }

    fun setViews(topView: View, bottomView: View) {
        topFragmentContainer.removeAllViews()
        topFragmentContainer.addView(topView)

        bottomFragmentContainer.removeAllViews()
        bottomFragmentContainer.addView(bottomView)
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
