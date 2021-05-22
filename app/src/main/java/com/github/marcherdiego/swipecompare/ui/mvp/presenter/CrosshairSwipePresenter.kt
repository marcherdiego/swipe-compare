package com.github.marcherdiego.swipecompare.ui.mvp.presenter

import android.view.MenuItem
import com.nerdscorner.mvplib.events.presenter.BaseActivityPresenter

import com.github.marcherdiego.swipecompare.ui.mvp.model.CrosshairSwipeModel
import com.github.marcherdiego.swipecompare.ui.mvp.view.CrosshairSwipeView
import com.github.marcherdiego.swipecompare.ui.mvp.view.CrosshairSwipeView.CombinedControlsCheckedChangedEvent
import org.greenrobot.eventbus.Subscribe

class CrosshairSwipePresenter(view: CrosshairSwipeView, model: CrosshairSwipeModel) :
    BaseActivityPresenter<CrosshairSwipeView, CrosshairSwipeModel>(view, model) {
    
    @Subscribe
    fun onCombinedControlsCheckedChanged(event: CombinedControlsCheckedChangedEvent) {
        view.setUnifiedControls(event.isChecked)
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
