package com.github.marcherdiego.swipecompare.ui.mvp.presenter

import com.nerdscorner.mvplib.events.presenter.BaseActivityPresenter

import com.github.marcherdiego.swipecompare.ui.mvp.model.HorizontalSwipeModel
import com.github.marcherdiego.swipecompare.ui.mvp.view.HorizontalSwipeView

class HorizontalSwipePresenter(view: HorizontalSwipeView, model: HorizontalSwipeModel) :
    BaseActivityPresenter<HorizontalSwipeView, HorizontalSwipeModel>(view, model) {

}
