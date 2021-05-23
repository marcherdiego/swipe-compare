package com.github.marcherdiego.swipecompare.core

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.github.marcherdiego.swipecompare.core.base.SwipeCompareLayout

@SuppressLint("ClickableViewAccessibility")
class VerticalSwipeCompareLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SwipeCompareLayout(context, attrs, defStyleAttr) {
    
    init {
        LayoutInflater.from(context).inflate(R.layout.vertical_swipe_compare_layout, this)
        init()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        val bottomLine = (verticalSlider?.y?.toInt() ?: 0) + selectorHeight
        topLeftFragmentContainer?.clipBounds = Rect(0, 0, width, bottomLine)
        bottomLeftFragmentContainer?.clipBounds = Rect(0, bottomLine, width, height)
        super.onDraw(canvas)
    }

    fun setFragments(fragmentManager: FragmentManager, topFragment: Fragment, bottomFragment: Fragment) {
        fragmentManager.beginTransaction().apply {
            replace(topLeftFragmentContainer?.id ?: return, topFragment)
            replace(bottomLeftFragmentContainer?.id ?: return, bottomFragment)
            commit()
        }
    }

    fun setViews(topView: View, bottomView: View) {
        topLeftFragmentContainer?.removeAllViews()
        topLeftFragmentContainer?.addView(topView)

        bottomLeftFragmentContainer?.removeAllViews()
        bottomLeftFragmentContainer?.addView(bottomView)
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
