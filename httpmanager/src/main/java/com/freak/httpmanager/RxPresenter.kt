package com.freak.kotlinhttpmanager.kotlinhttpmanager

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class RxPresenter<T : RxBaseView> : BasePresenter<T> {
    protected var mView: T? = null
    /**
     * 解除订阅
     */
    fun unSubscribe() {
        HttpMethods.compositeDisposableInstance.clear()
    }


    /**
     * RxJava绑定
     * 添加订阅
     *
     * @param observable
     * @param observer
     * @param <T>
     */
    protected fun <T> addSubscription(observable: Observable<T>, observer: Observer<T>) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
    }

    /**
     * 绑定view
     *
     * @param view
     */
    override fun attachView(view: T) {
        this.mView = view
    }

    override fun detachView() {
        this.mView = null
    }
}