package com.github.marcherdiego.swipecompare.ui.mvp.presenter

import com.nerdscorner.mvplib.events.presenter.BaseActivityPresenter

import com.github.marcherdiego.swipecompare.ui.mvp.model.CrosshairSwipeModel
import com.github.marcherdiego.swipecompare.ui.mvp.view.CrosshairSwipeView

class CrosshairSwipePresenter(view: CrosshairSwipeView, model: CrosshairSwipeModel) :
    BaseActivityPresenter<CrosshairSwipeView, CrosshairSwipeModel>(view, model) {

}
