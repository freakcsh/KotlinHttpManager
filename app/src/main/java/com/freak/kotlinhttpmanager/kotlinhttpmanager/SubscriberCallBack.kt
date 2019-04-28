package com.freak.kotlinhttpmanager.kotlinhttpmanager

import android.util.Log
import com.google.gson.JsonSyntaxException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException

/**
 * Created by Administrator on 2019/4/28.
 */
class SubscriberCallBack<T>(private val apiCallback: ApiCallback<T>) : Observer<T> {

    override fun onSubscribe(d: Disposable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * 错误时调用
     *
     * @param e
     */
    @Suppress("UNREACHABLE_CODE")
    override fun onError(e: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        try {
            val msg: String
            if (e is SocketTimeoutException) {
                msg = "连接服务器超时,请检查您的网络状态"
                apiCallback.onFailure(msg)
            } else if (e is ConnectException) {
                msg = "网络中断，请检查您的网络状态"
                apiCallback.onFailure(msg)
            } else if (e is TimeoutException) {
                msg = "连接超时，请检查您的网络状态"
                apiCallback.onFailure(msg)
            } else if (e is JsonSyntaxException) {
                Log.e("SubscriberCallBack", "JSON解析错误，请查看JSON结构", e)
                e.printStackTrace()
                apiCallback.onFailure((e.message.toString()))
            } else {
                apiCallback.onFailure((e.message.toString()))
            }
        } catch (e: Exception) {
            apiCallback.onFailure((e.message.toString()))
            e.printStackTrace()
        }
    }

    override fun onComplete() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }

    /**
     * 成功时调用
     *
     * @param o
     */
    override fun onNext(t: T) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        apiCallback.onSuccess(t)
    }
}