package com.github.marcherdiego.swipecompare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.marcherdiego.swipecompare.R

import com.nerdscorner.mvplib.events.fragment.BaseFragment
import com.github.marcherdiego.swipecompare.ui.mvp.model.LeftModel
import com.github.marcherdiego.swipecompare.ui.mvp.presenter.LeftPresenter
import com.github.marcherdiego.swipecompare.ui.mvp.view.LeftView

class LeftFragment : BaseFragment<LeftPresenter>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.left_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = LeftPresenter(
            LeftView(this),
            LeftModel()
        )
    }
}
