package com.freak.kotlinhttpmanager.kotlinhttpmanager.download

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * 下载拦截器
 *
 * @author Administrator
 */
class HttpDownInterceptor(private val mProgressListener: ProgressListener) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        return response.newBuilder().body(HttpDownProgressResponseBody(response.body()!!, mProgressListener)).build()
    }
}
