@file:Suppress("UNREACHABLE_CODE")

package com.freak.kotlinhttpmanager

import com.freak.kotlinhttpmanager.app.ApiServer
import com.freak.kotlinhttpmanager.entity.HttpResult
import com.freak.kotlinhttpmanager.entity.LoginBean
import com.freak.kotlinhttpmanager.entity.LoginStatusEntity
import com.freak.kotlinhttpmanager.kotlinhttpmanager.ApiCallback
import com.freak.kotlinhttpmanager.kotlinhttpmanager.HttpMethods
import com.freak.kotlinhttpmanager.kotlinhttpmanager.RxPresenter
import com.freak.kotlinhttpmanager.kotlinhttpmanager.SubscriberCallBack
import com.orhanobut.logger.Logger

class MainPresenter<T>(mainView:MainContract.View) : RxPresenter<MainContract.View>(mainView), MainContract.Presenter {
   private val apiServer = HttpMethods.instance.create(ApiServer::class.java)

    override fun doLogin1(userName: String, pwd: String) {

        val observable = apiServer.login1(userName, pwd)
        addSubscription(observable, SubscriberCallBack(object : ApiCallback<HttpResult<Any>> {
            override fun onSuccess(model: HttpResult<Any>) {
                mView!!.onSuccess(model)
                Logger.d(model)
            }

            override fun onFailure(msg: String) {
                mView!!.onError(msg)
                Logger.d(msg)
            }
        }))
    }

    override fun doLogin2(userName: String, pwd: String) {
        val observable = apiServer.login2(userName, pwd)
        addSubscription(observable, SubscriberCallBack(object : ApiCallback<HttpResult<LoginBean>> {
            override fun onSuccess(model: HttpResult<LoginBean>) {
            }

            override fun onFailure(msg: String) {
            }

        }))
    }

    override fun loadLoginStatusEntity() {
        val observable = apiServer.loadLoginStatus()
        addSubscription(observable, SubscriberCallBack(object : ApiCallback<LoginStatusEntity> {
            override fun onSuccess(model: LoginStatusEntity) {
            }

            override fun onFailure(msg: String) {
            }

        }))
    }

    override fun doLogin(phone: String, password: String) {
    }

    override fun uploading(tip: String, tip1: String, path: String) {
    }

    override fun uploading1(tip: String, tip1: String, path: String) {
    }

    override fun uploading2(path: String) {
    }

    override fun doLogin3(userName: String, pwd: String) {
    }

}