package com.github.marcherdiego.swipecompare.ui.mvp.presenter

import com.nerdscorner.mvplib.events.presenter.BaseFragmentPresenter
import com.github.marcherdiego.swipecompare.ui.mvp.view.RightView
import com.github.marcherdiego.swipecompare.ui.mvp.model.RightModel

class RightPresenter(view: RightView, model: RightModel) :
    BaseFragmentPresenter<RightView, RightModel>(view, model) {

}
