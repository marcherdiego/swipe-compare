package com.github.marcherdiego.swipecompare.ui.mvp.presenter

import com.nerdscorner.mvplib.events.presenter.BaseActivityPresenter

import com.github.marcherdiego.swipecompare.ui.mvp.model.VerticalSwipeModel
import com.github.marcherdiego.swipecompare.ui.mvp.view.VerticalSwipeView

class VerticalSwipePresenter(view: VerticalSwipeView, model: VerticalSwipeModel) :
    BaseActivityPresenter<VerticalSwipeView, VerticalSwipeModel>(view, model) {

}
