package com.freak.kotlinhttpmanager.kotlinhttpmanager.download

interface ResultListener {
    /**
     * 下载结束
     *
     * @param httpDownInfo
     */
    fun downFinish(httpDownInfo: HttpDownInfo)

    /**
     * 下载错误
     *
     * @param httpDownInfo
     */
    fun downError(httpDownInfo: HttpDownInfo)
}
