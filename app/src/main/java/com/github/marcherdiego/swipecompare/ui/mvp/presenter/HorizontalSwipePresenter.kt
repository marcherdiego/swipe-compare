package com.github.marcherdiego.swipecompare.ui.mvp.presenter

import android.view.MenuItem
import com.nerdscorner.mvplib.events.presenter.BaseActivityPresenter

import com.github.marcherdiego.swipecompare.ui.mvp.model.HorizontalSwipeModel
import com.github.marcherdiego.swipecompare.ui.mvp.view.HorizontalSwipeView
import com.github.marcherdiego.swipecompare.ui.mvp.view.HorizontalSwipeView.BarWidthChangedEvent
import com.github.marcherdiego.swipecompare.ui.mvp.view.HorizontalSwipeView.FixedSliderIconCheckedChangedEvent
import com.github.marcherdiego.swipecompare.ui.mvp.view.HorizontalSwipeView.SliderPositionChangedEvent
import com.github.marcherdiego.swipecompare.ui.mvp.view.HorizontalSwipeView.SliderPositionValueChangedEvent
import org.greenrobot.eventbus.Subscribe

class HorizontalSwipePresenter(view: HorizontalSwipeView, model: HorizontalSwipeModel) :
    BaseActivityPresenter<HorizontalSwipeView, HorizontalSwipeModel>(view, model) {

    @Subscribe
    fun onBarWidthChanged(event: BarWidthChangedEvent) {
        view.setBarWidth(event.width)
    }

    @Subscribe
    fun onFixedSliderIconCheckedChanged(event: FixedSliderIconCheckedChangedEvent) {
        view.setFixedSlider(event.fixed)
    }

    @Subscribe
    fun onSliderPositionChanged(event: SliderPositionValueChangedEvent) {
        try {
            view.setSliderPosition(event.value?.toFloat() ?: return)
        } catch (e: Exception) {
            view.setSliderPosition(0f)
        }
    }
    
    @Subscribe
    fun onSliderPositionChanged(event: SliderPositionChangedEvent) {
        view.setSliderPosition(event.value)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                view.activity?.finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
