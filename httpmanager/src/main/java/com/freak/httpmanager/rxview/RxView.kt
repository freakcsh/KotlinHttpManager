@file:Suppress("NAME_SHADOWING")

package com.freak.kotlinhttpmanager.kotlinhttpmanager.rxview

import android.annotation.SuppressLint
import android.support.annotation.CheckResult
import android.support.v4.util.Preconditions.checkNotNull
import android.view.View
import com.freak.kotlinhttpmanager.kotlinhttpmanager.rxview.Preconditions.Companion.checkUiThread
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import java.lang.Exception
import java.util.concurrent.TimeUnit


/**
 * @author Administrator
 * @date 2019/4/8
 */

class RxView {
    private var intervalTime: Int = 0

    /**
     * 设置点击间隔时间
     *
     * @param intervalTime
     * @return
     */
    fun setIntervalTime(intervalTime: Int) {
        this.intervalTime = intervalTime
    }

    companion object {
        private var rxView: RxView? = null
        val instanced: RxView
            get() {
                if (rxView == null) {
                    synchronized(RxView::class.java) {
                        rxView = RxView()
                    }
                }
                return rxView!!
            }
    }

    /**
     * 防止重复点击
     *
     * @param target 目标view
     * @param action 监听器
     */
    fun setOnClickListeners(action: OnRxViewClickListener<View>?, vararg target: View) {
        for (view in target) {
            RxView.instanced.onClick(view).throttleFirst((if (intervalTime == 0) 1000 else intervalTime).toLong(), TimeUnit.MILLISECONDS).subscribe { view -> action?.onRxViewClick(view) }
        }
    }

    /**
     * 监听onclick事件防抖动
     *
     * @param view
     * @return
     */
    @SuppressLint("RestrictedApi")
    @CheckResult
    private fun onClick(view: View): Observable<View> {
        checkNotNull(view, "view == null")
        return Observable.create(ViewClickOnSubscribe(view))
    }

    /**
     * onclick事件防抖动
     * 返回view
     */
    private class ViewClickOnSubscribe(private val view: View) : ObservableOnSubscribe<View> {

        @Throws(Exception::class)
        override fun subscribe(@io.reactivex.annotations.NonNull e: ObservableEmitter<View>) {
            checkUiThread()

            val listener = View.OnClickListener {
                if (!e.isDisposed) {
                    e.onNext(view)
                }
            }
            view.setOnClickListener(listener)
        }
    }

    /**
     * A one-argument action. 点击事件转发接口
     *
     * @param <T> the first argument type
    </T> */
    interface OnRxViewClickListener<in T> {
        /**
         * 点击事件
         *
         * @param view
         */
        fun onRxViewClick(view: T)
    }
}
