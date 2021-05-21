package com.github.marcherdiego.swipecompare.ui.activities

import android.os.Bundle

import com.nerdscorner.mvplib.events.activity.BaseActivity

import com.github.marcherdiego.swipecompare.R
import com.github.marcherdiego.swipecompare.ui.mvp.model.CrosshairSwipeModel
import com.github.marcherdiego.swipecompare.ui.mvp.presenter.CrosshairSwipePresenter
import com.github.marcherdiego.swipecompare.ui.mvp.view.CrosshairSwipeView

class CrosshairSwipeActivity : BaseActivity<CrosshairSwipePresenter>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crosshair_swipe_activity)

        presenter = CrosshairSwipePresenter(
            CrosshairSwipeView(this),
            CrosshairSwipeModel()
        )
    }
}
