package com.freak.kotlinhttpmanager.kotlinhttpmanager

import android.view.View

/**
 * Created by Administrator on 2019/4/28.
 */
interface BasePresenter<in T : RxBaseView<*>> {
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