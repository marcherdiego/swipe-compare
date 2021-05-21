package com.github.marcherdiego.swipecompare.ui.mvp.presenter

import com.nerdscorner.mvplib.events.presenter.BaseFragmentPresenter
import com.github.marcherdiego.swipecompare.ui.mvp.view.TopRightView
import com.github.marcherdiego.swipecompare.ui.mvp.model.TopRightModel

class TopRightPresenter(view: TopRightView, model: TopRightModel) :
    BaseFragmentPresenter<TopRightView, TopRightModel>(view, model) {

}
