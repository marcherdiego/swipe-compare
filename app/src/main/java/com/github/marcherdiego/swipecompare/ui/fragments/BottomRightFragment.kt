package com.github.marcherdiego.swipecompare.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.marcherdiego.swipecompare.R

import com.nerdscorner.mvplib.events.fragment.BaseFragment
import com.github.marcherdiego.swipecompare.ui.mvp.model.BottomRightModel
import com.github.marcherdiego.swipecompare.ui.mvp.presenter.BottomRightPresenter
import com.github.marcherdiego.swipecompare.ui.mvp.view.BottomRightView

class BottomRightFragment : BaseFragment<BottomRightPresenter>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_right_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = BottomRightPresenter(
                BottomRightView(this),
                BottomRightModel()
        )
    }
}
