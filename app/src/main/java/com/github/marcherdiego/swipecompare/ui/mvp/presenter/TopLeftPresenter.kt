package com.github.marcherdiego.swipecompare.ui.mvp.presenter

import com.nerdscorner.mvplib.events.presenter.BaseFragmentPresenter
import com.github.marcherdiego.swipecompare.ui.mvp.view.TopLeftView
import com.github.marcherdiego.swipecompare.ui.mvp.model.TopLeftModel

class TopLeftPresenter(view: TopLeftView, model: TopLeftModel) :
    BaseFragmentPresenter<TopLeftView, TopLeftModel>(view, model) {

}
