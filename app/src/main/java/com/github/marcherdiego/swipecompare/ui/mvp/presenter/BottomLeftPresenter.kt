package com.github.marcherdiego.swipecompare.ui.mvp.presenter

import com.nerdscorner.mvplib.events.presenter.BaseFragmentPresenter
import com.github.marcherdiego.swipecompare.ui.mvp.view.BottomLeftView
import com.github.marcherdiego.swipecompare.ui.mvp.model.BottomLeftModel

class BottomLeftPresenter(view: BottomLeftView, model: BottomLeftModel) :
    BaseFragmentPresenter<BottomLeftView, BottomLeftModel>(view, model) {

}
