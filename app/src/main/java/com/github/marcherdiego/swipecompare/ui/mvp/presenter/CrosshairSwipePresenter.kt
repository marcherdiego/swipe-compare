package com.github.marcherdiego.swipecompare.ui.mvp.presenter

import android.view.MenuItem
import com.nerdscorner.mvplib.events.presenter.BaseActivityPresenter

import com.github.marcherdiego.swipecompare.ui.mvp.model.CrosshairSwipeModel
import com.github.marcherdiego.swipecompare.ui.mvp.view.CrosshairSwipeView

class CrosshairSwipePresenter(view: CrosshairSwipeView, model: CrosshairSwipeModel) :
    BaseActivityPresenter<CrosshairSwipeView, CrosshairSwipeModel>(view, model) {

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
