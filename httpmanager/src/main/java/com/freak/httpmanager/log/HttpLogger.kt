@file:Suppress("UNREACHABLE_CODE", "IMPLICIT_BOXING_IN_IDENTITY_EQUALS", "NAME_SHADOWING")

package com.freak.kotlinhttpmanager.kotlinhttpmanager.log

import com.freak.kotlinhttpmanager.kotlinhttpmanager.HttpMethods
import okhttp3.logging.HttpLoggingInterceptor
import java.lang.StringBuilder

class HttpLogger : HttpLoggingInterceptor.Logger {
    private val mMessage = StringBuilder()
    override fun log(message: String) {
        var message = message
        // 请求或者响应开始
        if (message.startsWith("--> POST")) {
            mMessage.setLength(0)
            if (message.endsWith("http/1.1")) {
                val url = message.substring(message.indexOf("http"), message.lastIndexOf("http/1.1"))
                mMessage.append("请求地址--> " + UrlUtil.getURLDecoderString(url) + "\n")
            }
        }

        /**
         * 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
         */
        if (message.startsWith("{") && message.endsWith("}") || message.startsWith("[") && message.endsWith("]")) {
            message = JsonUtil.formatJson(message)
        }
        mMessage.append(message + "\n")
        /**
         * 请求或者响应结束，打印整条日志
         */
        if (message.startsWith("<-- END HTTP")) {
            if (HttpMethods.instanceBuilder.getLogLevel() === 0) {
                LogUtil.d(mMessage.toString())
            } else {
                when (HttpMethods.instanceBuilder.getLogLevel()) {
                    LogLevel.ERROR -> LogUtil.e(mMessage.toString())
                    LogLevel.DEBUG -> LogUtil.d(mMessage.toString())
                    else -> LogUtil.d(mMessage.toString())
                }
            }


        }
    }
}