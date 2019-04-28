package com.freak.kotlinhttpmanager.kotlinhttpmanager

/**
 * Created by Administrator on 2019/4/28.
 */
interface BasePresenter<T : RxBaseView> {
    /**
     * 绑定view
     *
     * @param view view
     */
    fun attachView(view: T)

    /**
     * 分离view
     */
    fun detachView()
}