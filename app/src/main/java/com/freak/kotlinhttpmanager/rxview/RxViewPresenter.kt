package com.freak.kotlinhttpmanager.rxview

import com.freak.kotlinhttpmanager.app.ApiServer
import com.freak.kotlinhttpmanager.entity.HttpResult
import com.freak.kotlinhttpmanager.kotlinhttpmanager.ApiCallback
import com.freak.kotlinhttpmanager.kotlinhttpmanager.HttpMethods
import com.freak.kotlinhttpmanager.kotlinhttpmanager.RxPresenter
import com.freak.kotlinhttpmanager.kotlinhttpmanager.SubscriberCallBack
import com.orhanobut.logger.Logger

/**
 * Created by Administrator on 2019/4/29.
 */
class RxViewPresenter: RxPresenter<RxViewContract.View>(), RxViewContract.Presenter {
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
}