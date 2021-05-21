package com.github.marcherdiego.swipecompare.ui.mvp.presenter

import com.nerdscorner.mvplib.events.presenter.BaseFragmentPresenter
import com.github.marcherdiego.swipecompare.ui.mvp.view.LeftView
import com.github.marcherdiego.swipecompare.ui.mvp.model.LeftModel

class LeftPresenter(view: LeftView, model: LeftModel) :
    BaseFragmentPresenter<LeftView, LeftModel>(view, model) {

}
