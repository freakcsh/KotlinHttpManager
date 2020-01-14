package com.freak.freakmusic.model.example

import com.freak.kotlinhttpmanager.kotlinhttpmanager.RxPresenter

class HomepagePresenter :RxPresenter<HomepageContract.View>() ,HomepageContract.Presenter {
    override fun test() {
          mView!!.testSuccess()
    }

}