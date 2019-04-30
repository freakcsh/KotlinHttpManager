package com.freak.kotlinhttpmanager.kotlinhttpmanager.download

interface HttpDownListener {
    /**
     * 开始下载
     */
    fun downStart()

    /**
     * 下载暂停
     *
     * @param progress     下载进度
     * @param httpDownInfo 下载信息
     */
    fun downPause(httpDownInfo: HttpDownInfo, progress: Long)

    /**
     * 下载停止
     */
    fun downStop(httpDownInfo: HttpDownInfo)

    /**
     * 下载完成
     *
     * @param httpDownInfo 下载文件实体类
     */
    fun downFinish(httpDownInfo: HttpDownInfo)

    /**
     * 下载出错
     *
     * @param httpDownInfo 下载文件实体类
     * @param msg          错误信息
     */
    fun downError(httpDownInfo: HttpDownInfo, msg: String)

    /**
     * 下载进度
     *
     * @param readLength  已下载文件大小（当前下载进度）
     * @param countLength 文件总大小
     */
    fun downProgress(readLength: Long, countLength: Long)
}
