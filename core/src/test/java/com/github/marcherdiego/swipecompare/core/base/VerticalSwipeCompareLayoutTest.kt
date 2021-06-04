package com.github.marcherdiego.swipecompare.core.base

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff.Mode
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.test.core.app.ApplicationProvider
import com.github.marcherdiego.swipecompare.core.VerticalSwipeCompareLayout
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
class VerticalSwipeCompareLayoutTest {

    private lateinit var verticalSwipeCompareLayout: TestVerticalSwipeCompareLayout
    private lateinit var topContainer: FrameLayout
    private lateinit var bottomContainer: FrameLayout
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext<Application>()
        verticalSwipeCompareLayout = TestVerticalSwipeCompareLayout(context)
        topContainer = verticalSwipeCompareLayout.getTopLeft()!!
        bottomContainer = verticalSwipeCompareLayout.getBottomLeft()!!
    }

    @Test
    fun `it should set views`() {
        // Given
        val topView = View(context)
        val bottomView = View(context)

        // When
        verticalSwipeCompareLayout.setViews(topView = topView, bottomView = bottomView)

        // Then
        assertEquals(1, topContainer.childCount)
        assertEquals(topView, topContainer.getChildAt(0))

        assertEquals(1, bottomContainer.childCount)
        assertEquals(bottomView, bottomContainer.getChildAt(0))
    }

    @Test
    fun `it should set slider bar width positive value`() {
        // Given
        val sliderBarWidth = 114

        // When
        verticalSwipeCompareLayout.setSliderBarHeight(sliderBarWidth)

        // Then
        val horizontalSelector = verticalSwipeCompareLayout.getVerticalSelectorBar()
        assertEquals(sliderBarWidth, horizontalSelector.layoutParams?.height)
    }

    @Test
    fun `it should set slider bar width zero value`() {
        // Given
        val sliderBarWidth = 0

        // When
        verticalSwipeCompareLayout.setSliderBarHeight(sliderBarWidth)

        // Then
        val horizontalSelector = verticalSwipeCompareLayout.getVerticalSelectorBar()
        assertEquals(View.INVISIBLE, horizontalSelector.visibility)
    }

    @Test
    fun `it should set slider bar position`() {
        // Given
        val sliderBarPosition = 13f
        val sliderIconPosition = 341f

        // When
        verticalSwipeCompareLayout.setSliderPosition(sliderBarPosition, sliderIconPosition)

        // Then
        val horizontalSlider = verticalSwipeCompareLayout.getVerticalSlider()
        val horizontalSelectorIcon = verticalSwipeCompareLayout.getVerticalSelectorIcon()
        assertEquals(sliderBarPosition, horizontalSlider.y)
        assertEquals(sliderIconPosition, horizontalSelectorIcon.x)
    }

    @Test
    fun `it should set slider icon position`() {
        // Given
        val sliderIconPosition = 341f

        // When
        verticalSwipeCompareLayout.setSliderIconPosition(sliderIconPosition)

        // Then
        val horizontalSelectorIcon = verticalSwipeCompareLayout.getVerticalSelectorIcon()
        assertEquals(sliderIconPosition, horizontalSelectorIcon.x)
    }

    @Test
    fun `it should set slider value listener`() {
        // Given
        val listener: (Float) -> Unit = {}

        // When
        verticalSwipeCompareLayout.setSliderPositionChangedListener(listener)

        // Then
        assertEquals(listener, verticalSwipeCompareLayout.getSliderValueListener())
    }

    @Test
    fun `it should set slider bar color int`() {
        // Given
        @ColorInt val color = 14

        // When
        verticalSwipeCompareLayout.setSliderBarColor(color)

        // Then
        val horizontalSelector = verticalSwipeCompareLayout.getVerticalSelectorBar()
        verify(horizontalSelector).setBackgroundColor(color)
    }

    @Test
    fun `it should set slider bar color res`() {
        // Given
        @ColorRes val color = R.color.design_default_color_error

        // When
        verticalSwipeCompareLayout.setSliderBarColorRes(color)

        // Then
        val horizontalSelector = verticalSwipeCompareLayout.getVerticalSelectorBar()
        verify(horizontalSelector).setBackgroundColor(Color.parseColor("#B00020"))
    }

    @Test
    fun `it should set slider icon color int`() {
        // Given
        @ColorInt val color = 14

        // When
        verticalSwipeCompareLayout.setSliderIconColor(color)

        // Then
        val horizontalSelectorIcon = verticalSwipeCompareLayout.getVerticalSelectorIcon()
        verify(horizontalSelectorIcon).setBackgroundColor(color)
    }

    @Test
    fun `it should set slider icon color res`() {
        // Given
        @ColorRes val color = R.color.design_default_color_error

        // When
        verticalSwipeCompareLayout.setSliderIconColorRes(color)

        // Then
        val horizontalSelectorIcon = verticalSwipeCompareLayout.getVerticalSelectorIcon()
        verify(horizontalSelectorIcon).setBackgroundColor(Color.parseColor("#B00020"))
    }

    @Test
    fun `it should set slider icon size`() {
        // Given
        val width = 123
        val height = 321

        // When
        verticalSwipeCompareLayout.setSliderIconSize(width, height)

        // Then
        val horizontalSelectorIcon = verticalSwipeCompareLayout.getVerticalSelectorIcon()
        assertEquals(width, horizontalSelectorIcon.layoutParams?.width)
        assertEquals(height, horizontalSelectorIcon.layoutParams?.height)
    }

    @Test
    fun `it should set slider icon background res`() {
        // Given
        @DrawableRes val background = android.R.drawable.checkbox_off_background

        // When
        verticalSwipeCompareLayout.setSliderIconBackground(background)

        // Then
        val horizontalSelectorIcon = verticalSwipeCompareLayout.getVerticalSelectorIcon()
        verify(horizontalSelectorIcon).setBackgroundResource(background)
    }

    @Test
    fun `it should set slider icon background drawable`() {
        // Given
        val background = context.getDrawable(android.R.drawable.checkbox_off_background)

        // When
        verticalSwipeCompareLayout.setSliderIconBackground(background)

        // Then
        val horizontalSelectorIcon = verticalSwipeCompareLayout.getVerticalSelectorIcon()
        verify(horizontalSelectorIcon).background = background
    }

    @Test
    fun `it should set slider icon res`() {
        // Given
        @DrawableRes val background = android.R.drawable.checkbox_off_background

        // When
        verticalSwipeCompareLayout.setSliderIcon(background)

        // Then
        val horizontalSelectorIcon = verticalSwipeCompareLayout.getVerticalSelectorIcon()
        verify(horizontalSelectorIcon).setImageResource(background)
    }

    @Test
    fun `it should set slider icon drawable`() {
        // Given
        val background = context.getDrawable(android.R.drawable.checkbox_off_background)

        // When
        verticalSwipeCompareLayout.setSliderIcon(background)

        // Then
        val horizontalSelectorIcon = verticalSwipeCompareLayout.getVerticalSelectorIcon()
        verify(horizontalSelectorIcon).setImageDrawable(background)
    }

    @Test
    fun `it should set slider icon tint`() {
        // Given
        @ColorRes val colorTint = R.color.design_default_color_error

        // When
        verticalSwipeCompareLayout.setSliderIconTint(colorTint)

        // Then
        val horizontalSelectorIcon = verticalSwipeCompareLayout.getVerticalSelectorIcon()
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
        verticalSwipeCompareLayout.setSliderIconPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)

        // Then
        val horizontalSelectorIcon = verticalSwipeCompareLayout.getVerticalSelectorIcon()
        verify(horizontalSelectorIcon).setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
    }

    @Test
    fun `it should set fixed slider icon velue`() {
        // Given
        val fixedIcon = true

        // When
        verticalSwipeCompareLayout.setFixedSliderIcon(fixedIcon)

        // Then
        assertTrue(verticalSwipeCompareLayout.isFixedVerticalSliderIcon())
    }

    class TestVerticalSwipeCompareLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
    ) : VerticalSwipeCompareLayout(context, attrs, defStyleAttr) {

        override fun init() {
            super.init()
            verticalSelectorBar = spy(verticalSelectorBar)
            verticalSlider = spy(verticalSlider)
            verticalSelectorIcon = spy(verticalSelectorIcon)
        }

        @JvmName("getSelector")
        fun getVerticalSelectorBar() = verticalSelectorBar!!

        @JvmName("getSlider")
        fun getVerticalSlider() = verticalSlider!!

        @JvmName("getSelectorIcon")
        fun getVerticalSelectorIcon() = verticalSelectorIcon!!

        fun getSliderValueListener() = verticalSliderValueListener!!
    }
}
