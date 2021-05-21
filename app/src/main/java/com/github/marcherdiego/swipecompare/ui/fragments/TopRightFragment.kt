package com.github.marcherdiego.swipecompare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.marcherdiego.swipecompare.R

import com.nerdscorner.mvplib.events.fragment.BaseFragment
import com.github.marcherdiego.swipecompare.ui.mvp.model.TopRightModel
import com.github.marcherdiego.swipecompare.ui.mvp.presenter.TopRightPresenter
import com.github.marcherdiego.swipecompare.ui.mvp.view.TopRightView

class TopRightFragment : BaseFragment<TopRightPresenter>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.top_right_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = TopRightPresenter(
                TopRightView(this),
                TopRightModel()
        )
    }
}
