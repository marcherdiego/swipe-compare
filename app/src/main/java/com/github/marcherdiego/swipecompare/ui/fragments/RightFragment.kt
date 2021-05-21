package com.github.marcherdiego.swipecompare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.marcherdiego.swipecompare.R

import com.nerdscorner.mvplib.events.fragment.BaseFragment
import com.github.marcherdiego.swipecompare.ui.mvp.model.RightModel
import com.github.marcherdiego.swipecompare.ui.mvp.presenter.RightPresenter
import com.github.marcherdiego.swipecompare.ui.mvp.view.RightView

class RightFragment : BaseFragment<RightPresenter>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.right_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = RightPresenter(
            RightView(this),
            RightModel()
        )
    }
}
