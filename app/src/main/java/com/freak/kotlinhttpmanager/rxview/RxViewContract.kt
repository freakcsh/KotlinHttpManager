package com.freak.kotlinhttpmanager.rxview

import com.freak.kotlinhttpmanager.app.BaseView
import com.freak.kotlinhttpmanager.entity.HttpResult
import com.freak.kotlinhttpmanager.kotlinhttpmanager.BasePresenter

/**
 * Created by Administrator on 2019/4/29.
 */
interface RxViewContract {
    interface View : BaseView {
        fun onSuccess(httpResult: HttpResult<Any>)
        fun onError(msg: String)
    }

    interface Presenter : BasePresenter<View> {
        fun doLogin1(userName: String, pwd: String)
    }
}