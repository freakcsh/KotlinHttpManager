package com.freak.kotlinhttpmanager

import com.freak.kotlinhttpmanager.entity.HttpResult
import com.freak.kotlinhttpmanager.entity.LoginEntity
import com.freak.kotlinhttpmanager.kotlinhttpmanager.BasePresenter
import com.freak.kotlinhttpmanager.kotlinhttpmanager.RxBaseView

interface MainContract {
    interface View : RxBaseView<Any> {
        fun onSuccess(loginEntity: LoginEntity)
        fun onSuccess(httpResult: HttpResult<Any>)
        fun onSuccess(msg: String)
        fun onError(msg: String)
    }

    interface Presenter : BasePresenter<View> {
        fun doLogin1(userName: String, pwd: String)

        fun doLogin2(userName: String, pwd: String)

        fun loadLoginStatusEntity()

        fun doLogin(phone: String, password: String)

        fun uploading(tip: String, tip1: String, path: String)

        fun uploading1(tip: String, tip1: String, path: String)

        fun uploading2(path: String)

        fun doLogin3(userName: String, pwd: String)

        fun login11(account: String, pwd: String, app_type: String)
    }
}