package com.freak.kotlinhttpmanager.interceptor

import com.freak.kotlinhttpmanager.app.Constants

import java.io.IOException

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * 添加公共参数
 *
 * @author Administrator
 * @date 2019/3/11
 */

class CommonParametersInterceptor : Interceptor {
    private val TAG = "CommonParametersInterceptor"

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()
        var response: Response? = null
        // 新的请求,添加参数
        val newRequest = addParam(oldRequest)
        response = chain.proceed(newRequest)
        return response
    }

    /**
     * 添加公共参数
     *
     * @param oldRequest
     * @return
     */
    private fun addParam(oldRequest: Request): Request {

        val builder = oldRequest.url()
                .newBuilder()
                .setEncodedQueryParameter("renovate", Constants.RENOVATE)
                .setEncodedQueryParameter("token", Constants.TOKEN_ABLE)
        return oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(builder.build())
                .build()
    }


}
