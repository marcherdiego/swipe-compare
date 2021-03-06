package com.github.marcherdiego.swipecompare.core.base

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff.Mode
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.test.core.app.ApplicationProvider
import com.github.marcherdiego.swipecompare.core.HorizontalSwipeCompareLayout
import com.github.marcherdiego.swipecompare.core.test.R
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.spy
import org.mockito.kotlin.verify
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class HorizontalSwipeCompareLayoutTest {

    private lateinit var horizontalSwipeCompareLayout: TestHorizontalSwipeCompareLayout
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext<Application>()
        horizontalSwipeCompareLayout = TestHorizontalSwipeCompareLayout(context)
    }

    @Test
    fun `it should set views`() {
        // Given
        val leftContainer = horizontalSwipeCompareLayout.getTopLeft()!!
        val leftView = View(context)
        
        val rightContainer = horizontalSwipeCompareLayout.getTopRight()!!
        val rightView = View(context)

        // When
        horizontalSwipeCompareLayout.setViews(leftView = leftView, rightView = rightView)

        // Then
        assertEquals(1, leftContainer.childCount)
        assertEquals(leftView, leftContainer.getChildAt(0))

        assertEquals(1, rightContainer.childCount)
        assertEquals(rightView, rightContainer.getChildAt(0))
    }

    @Test
    fun `it should set slider bar width positive value`() {
        // Given
        val sliderBarWidth = 114

        // When
        horizontalSwipeCompareLayout.setSliderBarWidth(sliderBarWidth)

        // Then
        val horizontalSelector = horizontalSwipeCompareLayout.getHorizontalSelectorBar()
        assertEquals(sliderBarWidth, horizontalSelector.layoutParams?.width)
    }

    @Test
    fun `it should set slider bar width zero value`() {
        // Given
        val sliderBarWidth = 0

        // When
        horizontalSwipeCompareLayout.setSliderBarWidth(sliderBarWidth)

        // Then
        val horizontalSelector = horizontalSwipeCompareLayout.getHorizontalSelectorBar()
        assertEquals(View.INVISIBLE, horizontalSelector.visibility)
    }

    @Test
    fun `it should set slider bar position`() {
        // Given
        val sliderBarPosition = 13f
        val sliderIconPosition = 341f

        // When
        horizontalSwipeCompareLayout.setSliderPosition(sliderBarPosition, sliderIconPosition)

        // Then
        val horizontalSlider = horizontalSwipeCompareLayout.getHorizontalSlider()
        val horizontalSelectorIcon = horizontalSwipeCompareLayout.getHorizontalSelectorIcon()
        assertEquals(sliderBarPosition, horizontalSlider.x)
        assertEquals(sliderIconPosition, horizontalSelectorIcon.y)
    }

    @Test
    fun `it should set slider icon position`() {
        // Given
        val sliderIconPosition = 341f

        // When
        horizontalSwipeCompareLayout.setSliderIconPosition(sliderIconPosition)

        // Then
        val horizontalSelectorIcon = horizontalSwipeCompareLayout.getHorizontalSelectorIcon()
        assertEquals(sliderIconPosition, horizontalSelectorIcon.y)
    }

    @Test
    fun `it should set slider value listener`() {
        // Given
        val listener: (Float) -> Unit = {}

        // When
        horizontalSwipeCompareLayout.setSliderPositionChangedListener(listener)

        // Then
        assertEquals(listener, horizontalSwipeCompareLayout.getSliderValueListener())
    }

    @Test
    fun `it should set slider bar color int`() {
        // Given
        @ColorInt val color = 14

        // When
        horizontalSwipeCompareLayout.setSliderBarColor(color)

        // Then
        val horizontalSelector = horizontalSwipeCompareLayout.getHorizontalSelectorBar()
        verify(horizontalSelector).setBackgroundColor(color)
    }

    @Test
    fun `it should set slider bar color res`() {
        // Given
        @ColorRes val color = R.color.design_default_color_error

        // When
        horizontalSwipeCompareLayout.setSliderBarColorRes(color)

        // Then
        val horizontalSelector = horizontalSwipeCompareLayout.getHorizontalSelectorBar()
        verify(horizontalSelector).setBackgroundColor(Color.parseColor("#B00020"))
    }

    @Test
    fun `it should set slider icon color int`() {
        // Given
        @ColorInt val color = 14

        // When
        horizontalSwipeCompareLayout.setSliderIconColor(color)

        // Then
        val horizontalSelectorIcon = horizontalSwipeCompareLayout.getHorizontalSelectorIcon()
        verify(horizontalSelectorIcon).setBackgroundColor(color)
    }

    @Test
    fun `it should set slider icon color res`() {
        // Given
        @ColorRes val color = R.color.design_default_color_error

        // When
        horizontalSwipeCompareLayout.setSliderIconColorRes(color)

        // Then
        val horizontalSelectorIcon = horizontalSwipeCompareLayout.getHorizontalSelectorIcon()
        verify(horizontalSelectorIcon).setBackgroundColor(Color.parseColor("#B00020"))
    }

    @Test
    fun `it should set slider icon size`() {
        // Given
        val width = 123
        val height = 321

        // When
        horizontalSwipeCompareLayout.setSliderIconSize(width, height)

        // Then
        val horizontalSelectorIcon = horizontalSwipeCompareLayout.getHorizontalSelectorIcon()
        assertEquals(width, horizontalSelectorIcon.layoutParams?.width)
        assertEquals(height, horizontalSelectorIcon.layoutParams?.height)
    }

    @Test
    fun `it should set slider icon background res`() {
        // Given
        @DrawableRes val background = android.R.drawable.checkbox_off_background

        // When
        horizontalSwipeCompareLayout.setSliderIconBackground(background)

        // Then
        val horizontalSelectorIcon = horizontalSwipeCompareLayout.getHorizontalSelectorIcon()
        verify(horizontalSelectorIcon).setBackgroundResource(background)
    }

    @Test
    fun `it should set slider icon background drawable`() {
        // Given
        val background = context.getDrawable(android.R.drawable.checkbox_off_background)

        // When
        horizontalSwipeCompareLayout.setSliderIconBackground(background)

        // Then
        val horizontalSelectorIcon = horizontalSwipeCompareLayout.getHorizontalSelectorIcon()
        verify(horizontalSelectorIcon).background = background
    }

    @Test
    fun `it should set slider icon res`() {
        // Given
        @DrawableRes val background = android.R.drawable.checkbox_off_background

        // When
        horizontalSwipeCompareLayout.setSliderIcon(background)

        // Then
        val horizontalSelectorIcon = horizontalSwipeCompareLayout.getHorizontalSelectorIcon()
        verify(horizontalSelectorIcon).setImageResource(background)
    }

    @Test
    fun `it should set slider icon drawable`() {
        // Given
        val background = context.getDrawable(android.R.drawable.checkbox_off_background)

        // When
        horizontalSwipeCompareLayout.setSliderIcon(background)

        // Then
        val horizontalSelectorIcon = horizontalSwipeCompareLayout.getHorizontalSelectorIcon()
        verify(horizontalSelectorIcon).setImageDrawable(background)
    }

    @Test
    fun `it should set slider icon tint`() {
        // Given
        @ColorRes val colorTint = R.color.design_default_color_error

        // When
        horizontalSwipeCompareLayout.setSliderIconTint(colorTint)

        // Then
        val horizontalSelectorIcon = horizontalSwipeCompareLayout.getHorizontalSelectorIcon()
        verify(horizontalSelectorIcon).setColorFilter(Color.parseColor("#B00020"), Mode.SRC_IN)
    }

    @Test
    fun `it should set slider icon padding`() {
        // Given
        val paddingLeft = 1
        val paddingTop = 2
        val paddingRight = 3
        val paddingBottom = 4

        // When
        horizontalSwipeCompareLayout.setSliderIconPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)

        // Then
        val horizontalSelectorIcon = horizontalSwipeCompareLayout.getHorizontalSelectorIcon()
        verify(horizontalSelectorIcon).setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
    }

    @Test
    fun `it should set fixed slider icon velue`() {
        // Given
        val fixedIcon = true

        // When
        horizontalSwipeCompareLayout.setFixedSliderIcon(fixedIcon)

        // Then
        assertTrue(horizontalSwipeCompareLayout.isFixedHorizontalSliderIcon())
    }

    class TestHorizontalSwipeCompareLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
    ) : HorizontalSwipeCompareLayout(context, attrs, defStyleAttr) {

        override fun init() {
            super.init()
            horizontalSelectorBar = spy(horizontalSelectorBar)
            horizontalSlider = spy(horizontalSlider)
            horizontalSelectorIcon = spy(horizontalSelectorIcon)
        }

        @JvmName("getSelector")
        fun getHorizontalSelectorBar() = horizontalSelectorBar!!

        @JvmName("getSlider")
        fun getHorizontalSlider() = horizontalSlider!!

        @JvmName("getSelectorIcon")
        fun getHorizontalSelectorIcon() = horizontalSelectorIcon!!

        fun getSliderValueListener() = horizontalSliderValueListener!!
    }
}
