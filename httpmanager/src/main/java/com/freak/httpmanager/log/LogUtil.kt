package com.freak.kotlinhttpmanager.kotlinhttpmanager.log

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

object LogUtil {
    /**
     * 初始化log工具，在app入口处调用
     *
     * @param logName 打印日志名字
     * @param isLog 是否打印log
     */
    fun init(logName: String, isLog: Boolean) {
        val mFormatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // （可选）是否显示线程信息。默认值true
                //                .methodCount(5)         // （可选）要显示的方法行数。默认值2
                //                .methodOffset(7)        // （可选）隐藏内部方法调用到偏移量。默认值5
                //                .logStrategy() // （可选）更改要打印的日志策略。默认LogCat
                .tag(logName)   // （可选）每个日志的全局标记。默认PRETTY_LOGGER .build
                .build()
        //log日志打印框架Logger
        Logger.addLogAdapter(object : AndroidLogAdapter(mFormatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return isLog
            }
        })
    }

    fun d(message: String) {
        Logger.d(message)
    }

    fun e(message: String) {
        Logger.e(message)
    }

    fun i(message: String) {
        Logger.i(message)
    }

    fun w(message: String, e: Throwable) {
        val info = e?.toString() ?: "null"
        Logger.w("$message：$info")
    }

    fun e(message: String, e: Throwable) {
        Logger.e(e, message)
    }

    fun json(json: String) {
        Logger.json(json)
    }

}