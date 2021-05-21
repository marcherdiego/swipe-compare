package com.github.marcherdiego.swipecompare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.marcherdiego.swipecompare.R

import com.nerdscorner.mvplib.events.fragment.BaseFragment
import com.github.marcherdiego.swipecompare.ui.mvp.model.BottomLeftModel
import com.github.marcherdiego.swipecompare.ui.mvp.presenter.BottomLeftPresenter
import com.github.marcherdiego.swipecompare.ui.mvp.view.BottomLeftView

class BottomLeftFragment : BaseFragment<BottomLeftPresenter>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_left_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = BottomLeftPresenter(
                BottomLeftView(this),
                BottomLeftModel()
        )
    }
}
