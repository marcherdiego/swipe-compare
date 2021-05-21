package com.github.marcherdiego.swipecompare.ui.mvp.presenter

import com.nerdscorner.mvplib.events.presenter.BaseFragmentPresenter
import com.github.marcherdiego.swipecompare.ui.mvp.view.BottomRightView
import com.github.marcherdiego.swipecompare.ui.mvp.model.BottomRightModel

class BottomRightPresenter(view: BottomRightView, model: BottomRightModel) :
    BaseFragmentPresenter<BottomRightView, BottomRightModel>(view, model) {

}
