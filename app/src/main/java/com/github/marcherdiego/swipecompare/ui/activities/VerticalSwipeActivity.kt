package com.github.marcherdiego.swipecompare.ui.activities

import android.os.Bundle

import com.nerdscorner.mvplib.events.activity.BaseActivity

import com.github.marcherdiego.swipecompare.R
import com.github.marcherdiego.swipecompare.ui.mvp.model.VerticalSwipeModel
import com.github.marcherdiego.swipecompare.ui.mvp.presenter.VerticalSwipePresenter
import com.github.marcherdiego.swipecompare.ui.mvp.view.VerticalSwipeView

class VerticalSwipeActivity : BaseActivity<VerticalSwipePresenter>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vertical_swipe_activity)

        presenter = VerticalSwipePresenter(
            VerticalSwipeView(this),
            VerticalSwipeModel()
        )
    }
}
