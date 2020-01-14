package com.freak.freakmusic.model.example

import com.freak.kotlinhttpmanager.app.BaseView
import com.freak.kotlinhttpmanager.kotlinhttpmanager.BasePresenter

class HomepageContract {
    interface View : BaseView {
        fun testSuccess()
    }

    interface Presenter : BasePresenter<View> {
        fun test()
    }

}