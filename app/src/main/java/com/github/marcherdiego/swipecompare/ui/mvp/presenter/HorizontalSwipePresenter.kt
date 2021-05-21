package com.github.marcherdiego.swipecompare.ui.mvp.presenter

import android.view.MenuItem
import com.nerdscorner.mvplib.events.presenter.BaseActivityPresenter

import com.github.marcherdiego.swipecompare.ui.mvp.model.HorizontalSwipeModel
import com.github.marcherdiego.swipecompare.ui.mvp.view.HorizontalSwipeView

class HorizontalSwipePresenter(view: HorizontalSwipeView, model: HorizontalSwipeModel) :
    BaseActivityPresenter<HorizontalSwipeView, HorizontalSwipeModel>(view, model) {

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
