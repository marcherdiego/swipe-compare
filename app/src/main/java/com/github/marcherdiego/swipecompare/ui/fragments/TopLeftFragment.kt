package com.github.marcherdiego.swipecompare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.marcherdiego.swipecompare.R

import com.nerdscorner.mvplib.events.fragment.BaseFragment
import com.github.marcherdiego.swipecompare.ui.mvp.model.TopLeftModel
import com.github.marcherdiego.swipecompare.ui.mvp.presenter.TopLeftPresenter
import com.github.marcherdiego.swipecompare.ui.mvp.view.TopLeftView

class TopLeftFragment : BaseFragment<TopLeftPresenter>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.top_left_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = TopLeftPresenter(
                TopLeftView(this),
                TopLeftModel()
        )
    }
}
