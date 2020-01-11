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
import com.freak.kotlinhttpmanager.kotlinhttpmanager.log.LogUtil
import com.freak.kotlinhttpmanager.util.RequestUtils

class MainPresenter<T>(mainView:MainContract.View) : RxPresenter<MainContract.View>(mainView), MainContract.Presenter {
    override fun login11(account: String, pwd: String, app_type: String) {
            val requestBody = RequestUtils.newParams()
                    .addParams("account", account)
                    .addParams("pwd", pwd)
                    .addParams("app_type", app_type)
                    .createRequestBody()
            //.map { HttpResultFunc<LoginModel>() }
            val observable = apiServer.login11(requestBody)
//                    .map { HttpResult<LoginModel>() }

        addSubscription(observable,SubscriberCallBack(object :ApiCallback<HttpResult<LoginModel>>{
            override fun onSuccess(model: HttpResult<LoginModel>) {
                LogUtil.e(model.toString())
            }

            override fun onFailure(msg: String) {
            }

        }))



//            addSubscription(observable, SubscriberCallBack<LoginModel>(object : ApiCallback<LoginModel> {
////                override fun onSuccess(model: HttpResult<LoginModel>) {
////                    LogUtil.e(model.toString())//To change body of created functions use File | Settings | File Templates.
////                }
//
//            override fun onSuccess(model: LoginModel) {
//                LogUtil.e(model.toString())
//            }
//
//
//
//                override fun onFailure(msg: String) {
//                    LogUtil.e(msg)
//                }
//            }))
    }

    private val apiServer = HttpMethods.instance.create(ApiServer::class.java)

    override fun doLogin1(userName: String, pwd: String) {

        val observable = apiServer.login1(userName, pwd)
        addSubscription(observable, SubscriberCallBack(object : ApiCallback<HttpResult<Any>> {
            override fun onSuccess(model: HttpResult<Any>) {
                mView!!.onSuccess(model)
                LogUtil.d(model.toString())
            }

            override fun onFailure(msg: String) {
                mView!!.onError(msg)
                LogUtil.d(msg)
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