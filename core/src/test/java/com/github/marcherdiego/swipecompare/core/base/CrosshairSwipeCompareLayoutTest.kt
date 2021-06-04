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
import com.github.marcherdiego.swipecompare.core.CrosshairSwipeCompareLayout
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
class CrosshairSwipeCompareLayoutTest {

    private lateinit var crosshairSwipeCompareLayout: TestCrosshairSwipeCompareLayout
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext<Application>()
        crosshairSwipeCompareLayout = TestCrosshairSwipeCompareLayout(context)
    }

    @Test
    fun `it should set views`() {
        // Given
        val topLeftContainer = crosshairSwipeCompareLayout.getTopLeft()!!
        val topLeftView = View(context)

        val topRightContainer = crosshairSwipeCompareLayout.getTopRight()!!
        val topRightView = View(context)

        val bottomLeftContainer = crosshairSwipeCompareLayout.getBottomLeft()!!
        val bottomLeftView = View(context)

        val bottomRightContainer = crosshairSwipeCompareLayout.getBottomRight()!!
        val bottomRightView = View(context)

        // When
        crosshairSwipeCompareLayout.setViews(
            topLeftView = topLeftView,
            topRightView = topRightView,
            bottomLeftView = bottomLeftView,
            bottomRightView = bottomRightView
        )

        // Then
        assertEquals(1, topLeftContainer.childCount)
        assertEquals(topLeftView, topLeftContainer.getChildAt(0))

        assertEquals(1, topRightContainer.childCount)
        assertEquals(topRightView, topRightContainer.getChildAt(0))

        assertEquals(1, bottomLeftContainer.childCount)
        assertEquals(bottomLeftView, bottomLeftContainer.getChildAt(0))

        assertEquals(1, bottomRightContainer.childCount)
        assertEquals(bottomRightView, bottomRightContainer.getChildAt(0))
    }

    @Test
    fun `it should set slider bar height positive value`() {
        // Given
        val sliderBarHeight = 114

        // When
        crosshairSwipeCompareLayout.setSliderBarHeight(sliderBarHeight)

        // Then
        val verticalSelector = crosshairSwipeCompareLayout.getVerticalSelectorBar()
        assertEquals(sliderBarHeight, verticalSelector.layoutParams?.height)
    }

    @Test
    fun `it should set slider bar height zero value`() {
        // Given
        val sliderBarHeight = 0

        // When
        crosshairSwipeCompareLayout.setSliderBarHeight(sliderBarHeight)

        // Then
        val verticalSelector = crosshairSwipeCompareLayout.getVerticalSelectorBar()
        assertEquals(View.INVISIBLE, verticalSelector.visibility)
    }

    @Test
    fun `it should set slider bar width positive value`() {
        // Given
        val sliderBarWidth = 114

        // When
        crosshairSwipeCompareLayout.setSliderBarWidth(sliderBarWidth)

        // Then
        val horizontalSelector = crosshairSwipeCompareLayout.getHorizontalSelectorBar()
        assertEquals(sliderBarWidth, horizontalSelector.layoutParams?.width)
    }

    @Test
    fun `it should set slider bar width zero value`() {
        // Given
        val sliderBarWidth = 0

        // When
        crosshairSwipeCompareLayout.setSliderBarWidth(sliderBarWidth)

        // Then
        val horizontalSelector = crosshairSwipeCompareLayout.getHorizontalSelectorBar()
        assertEquals(View.INVISIBLE, horizontalSelector.visibility)
    }

    @Test
    fun `it should set slider bar position`() {
        // Given
        val sliderXPosition = 13f
        val sliderYPosition = 341f

        // When
        crosshairSwipeCompareLayout.setSliderPosition(sliderXPosition, sliderYPosition)

        // Then
        val horizontalSlider = crosshairSwipeCompareLayout.getHorizontalSlider()
        val verticalSlider = crosshairSwipeCompareLayout.getVerticalSlider()
        assertEquals(sliderXPosition, horizontalSlider.x)
        assertEquals(sliderYPosition, verticalSlider.y)
    }

    @Test
    fun `it should set slider icon position`() {
        // Given
        val horizontalSliderIconPosition = 341f
        val verticalSliderIconPosition = 114f

        // When
        crosshairSwipeCompareLayout.setHorizontalSliderIconPosition(horizontalSliderIconPosition)
        crosshairSwipeCompareLayout.setVerticalSliderIconPosition(verticalSliderIconPosition)

        // Then
        val horizontalSelectorIcon = crosshairSwipeCompareLayout.getHorizontalSelectorIcon()
        assertEquals(horizontalSliderIconPosition, horizontalSelectorIcon.y)

        val verticalSelectorIcon = crosshairSwipeCompareLayout.getVerticalSelectorIcon()
        assertEquals(verticalSliderIconPosition, verticalSelectorIcon.x)
    }

    @Test
    fun `it should set slider value listener`() {
        // Given
        val listener: (Float, Float) -> Unit = { _, _ -> }

        // When
        crosshairSwipeCompareLayout.setSliderPositionChangedListener(listener)

        // Then
        assertEquals(listener, crosshairSwipeCompareLayout.getSliderValueListener())
    }

    @Test
    fun `it should set slider bar color int`() {
        // Given
        @ColorInt val color = 14

        // When
        crosshairSwipeCompareLayout.setSliderBarColor(color)

        // Then
        val horizontalSelector = crosshairSwipeCompareLayout.getHorizontalSelectorBar()
        verify(horizontalSelector).setBackgroundColor(color)
        
        val verticalSelector = crosshairSwipeCompareLayout.getVerticalSelectorBar()
        verify(verticalSelector).setBackgroundColor(color)
    }

    @Test
    fun `it should set slider bar color res`() {
        // Given
        @ColorRes val color = R.color.design_default_color_error

        // When
        crosshairSwipeCompareLayout.setSliderBarColorRes(color)

        // Then
        val horizontalSelector = crosshairSwipeCompareLayout.getHorizontalSelectorBar()
        verify(horizontalSelector).setBackgroundColor(Color.parseColor("#B00020"))
        
        val verticalSelector = crosshairSwipeCompareLayout.getVerticalSelectorBar()
        verify(verticalSelector).setBackgroundColor(Color.parseColor("#B00020"))
    }

    @Test
    fun `it should set slider icon color int`() {
        // Given
        @ColorInt val color = 14

        // When
        crosshairSwipeCompareLayout.setSliderIconColor(color)

        // Then
        val verticalSelectorIcon = crosshairSwipeCompareLayout.getVerticalSelectorIcon()
        verify(verticalSelectorIcon).setBackgroundColor(color)

        val horizontalSelectorIcon = crosshairSwipeCompareLayout.getHorizontalSelectorIcon()
        verify(horizontalSelectorIcon).setBackgroundColor(color)
    }

    @Test
    fun `it should set slider icon color res`() {
        // Given
        @ColorRes val color = R.color.design_default_color_error

        // When
        crosshairSwipeCompareLayout.setSliderIconColorRes(color)

        // Then
        val verticalSelectorIcon = crosshairSwipeCompareLayout.getVerticalSelectorIcon()
        verify(verticalSelectorIcon).setBackgroundColor(Color.parseColor("#B00020"))

        val horizontalSelectorIcon = crosshairSwipeCompareLayout.getHorizontalSelectorIcon()
        verify(horizontalSelectorIcon).setBackgroundColor(Color.parseColor("#B00020"))
    }

    @Test
    fun `it should set slider icon size`() {
        // Given
        val width = 123
        val height = 321

        // When
        crosshairSwipeCompareLayout.setSliderIconSize(width, height)

        // Then
        val verticalSelectorIcon = crosshairSwipeCompareLayout.getVerticalSelectorIcon()
        assertEquals(width, verticalSelectorIcon.layoutParams?.width)
        assertEquals(height, verticalSelectorIcon.layoutParams?.height)

        val horizontalSelectorIcon = crosshairSwipeCompareLayout.getHorizontalSelectorIcon()
        assertEquals(width, horizontalSelectorIcon.layoutParams?.width)
        assertEquals(height, horizontalSelectorIcon.layoutParams?.height)
    }

    @Test
    fun `it should set slider icon background res`() {
        // Given
        @DrawableRes val background = android.R.drawable.checkbox_off_background

        // When
        crosshairSwipeCompareLayout.setSliderIconBackground(background)

        // Then
        val verticalSelectorIcon = crosshairSwipeCompareLayout.getVerticalSelectorIcon()
        verify(verticalSelectorIcon).setBackgroundResource(background)

        val horizontalSelectorIcon = crosshairSwipeCompareLayout.getHorizontalSelectorIcon()
        verify(horizontalSelectorIcon).setBackgroundResource(background)
    }

    @Test
    fun `it should set slider icon background drawable`() {
        // Given
        val background = context.getDrawable(android.R.drawable.checkbox_off_background)

        // When
        crosshairSwipeCompareLayout.setSliderIconBackground(background)

        // Then
        val verticalSelectorIcon = crosshairSwipeCompareLayout.getVerticalSelectorIcon()
        verify(verticalSelectorIcon).background = background

        val horizontalSelectorIcon = crosshairSwipeCompareLayout.getHorizontalSelectorIcon()
        verify(horizontalSelectorIcon).background = background
    }

    @Test
    fun `it should set slider icon res`() {
        // Given
        @DrawableRes val background = android.R.drawable.checkbox_off_background

        // When
        crosshairSwipeCompareLayout.setSliderIcon(background)

        // Then
        val verticalSelectorIcon = crosshairSwipeCompareLayout.getVerticalSelectorIcon()
        verify(verticalSelectorIcon).setImageResource(background)

        val horizontalSelectorIcon = crosshairSwipeCompareLayout.getHorizontalSelectorIcon()
        verify(horizontalSelectorIcon).setImageResource(background)
    }

    @Test
    fun `it should set slider icon drawable`() {
        // Given
        val background = context.getDrawable(android.R.drawable.checkbox_off_background)

        // When
        crosshairSwipeCompareLayout.setSliderIcon(background)

        // Then
        val verticalSelectorIcon = crosshairSwipeCompareLayout.getVerticalSelectorIcon()
        verify(verticalSelectorIcon).setImageDrawable(background)

        val horizontalSelectorIcon = crosshairSwipeCompareLayout.getHorizontalSelectorIcon()
        verify(horizontalSelectorIcon).setImageDrawable(background)
    }

    @Test
    fun `it should set slider icon tint`() {
        // Given
        @ColorRes val colorTint = R.color.design_default_color_error

        // When
        crosshairSwipeCompareLayout.setSliderIconTint(colorTint)

        // Then
        val verticalSelectorIcon = crosshairSwipeCompareLayout.getVerticalSelectorIcon()
        verify(verticalSelectorIcon).setColorFilter(Color.parseColor("#B00020"), Mode.SRC_IN)

        val horizontalSelectorIcon = crosshairSwipeCompareLayout.getHorizontalSelectorIcon()
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
        crosshairSwipeCompareLayout.setSliderIconPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)

        // Then
        val verticalSelectorIcon = crosshairSwipeCompareLayout.getVerticalSelectorIcon()
        verify(verticalSelectorIcon).setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)

        val horizontalSelectorIcon = crosshairSwipeCompareLayout.getHorizontalSelectorIcon()
        verify(horizontalSelectorIcon).setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
    }

    @Test
    fun `it should set fixed slider icon velue`() {
        // Given
        val fixedIcon = true

        // When
        crosshairSwipeCompareLayout.setFixedSliderIcon(fixedIcon)

        // Then
        assertTrue(crosshairSwipeCompareLayout.unifiedControllers)
    }

    class TestCrosshairSwipeCompareLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
    ) : CrosshairSwipeCompareLayout(context, attrs, defStyleAttr) {

        override fun init() {
            super.init()
            horizontalSelectorBar = spy(horizontalSelectorBar)
            horizontalSlider = spy(horizontalSlider)
            horizontalSelectorIcon = spy(horizontalSelectorIcon)

            verticalSelectorBar = spy(verticalSelectorBar)
            verticalSlider = spy(verticalSlider)
            verticalSelectorIcon = spy(verticalSelectorIcon)
        }

        @JvmName("getSelectorH")
        fun getHorizontalSelectorBar() = horizontalSelectorBar!!

        @JvmName("getSliderH")
        fun getHorizontalSlider() = horizontalSlider!!

        @JvmName("getSelectorIconH")
        fun getHorizontalSelectorIcon() = horizontalSelectorIcon!!

        @JvmName("getSelectorV")
        fun getVerticalSelectorBar() = verticalSelectorBar!!

        @JvmName("getSliderV")
        fun getVerticalSlider() = verticalSlider!!

        @JvmName("getSelectorIconV")
        fun getVerticalSelectorIcon() = verticalSelectorIcon!!

        fun getSliderValueListener() = crosshairSliderValueListener!!
    }
}
