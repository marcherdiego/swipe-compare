package com.github.marcherdiego.swipecompare.ui.activities

import android.os.Bundle

import com.nerdscorner.mvplib.events.activity.BaseActivity

import com.github.marcherdiego.swipecompare.R
import com.github.marcherdiego.swipecompare.ui.mvp.model.HorizontalSwipeModel
import com.github.marcherdiego.swipecompare.ui.mvp.presenter.HorizontalSwipePresenter
import com.github.marcherdiego.swipecompare.ui.mvp.view.HorizontalSwipeView

class HorizontalSwipeActivity : BaseActivity<HorizontalSwipePresenter>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.horizontal_swipe_activity)

        presenter = HorizontalSwipePresenter(
            HorizontalSwipeView(this),
            HorizontalSwipeModel()
        )
    }
}
