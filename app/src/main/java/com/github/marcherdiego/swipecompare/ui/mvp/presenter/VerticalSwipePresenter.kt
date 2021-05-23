package com.github.marcherdiego.swipecompare.ui.mvp.presenter

import android.view.MenuItem
import com.nerdscorner.mvplib.events.presenter.BaseActivityPresenter

import com.github.marcherdiego.swipecompare.ui.mvp.model.VerticalSwipeModel
import com.github.marcherdiego.swipecompare.ui.mvp.view.VerticalSwipeView
import com.github.marcherdiego.swipecompare.ui.mvp.view.VerticalSwipeView.BarHeightChangedEvent
import com.github.marcherdiego.swipecompare.ui.mvp.view.VerticalSwipeView.CenterSliderIconCheckedChangedEvent
import com.github.marcherdiego.swipecompare.ui.mvp.view.VerticalSwipeView.FixedSliderIconCheckedChangedEvent
import com.github.marcherdiego.swipecompare.ui.mvp.view.VerticalSwipeView.SliderPositionChangedEvent
import com.github.marcherdiego.swipecompare.ui.mvp.view.VerticalSwipeView.SliderPositionValueChangedEvent
import org.greenrobot.eventbus.Subscribe

class VerticalSwipePresenter(view: VerticalSwipeView, model: VerticalSwipeModel) :
    BaseActivityPresenter<VerticalSwipeView, VerticalSwipeModel>(view, model) {

    @Subscribe
    fun onBarHeightChanged(event: BarHeightChangedEvent) {
        view.setBarHeight(event.height)
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
    
    @Subscribe
    fun onCenterSliderIconCheckedChanged(event: CenterSliderIconCheckedChangedEvent) {
        view.centerSliderIcon()
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
