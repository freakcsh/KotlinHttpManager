package com.freak.kotlinhttpmanager.kotlinhttpmanager.download

/**
 * Created by Administrator on 2019/4/29.
 */
interface ProgressListener {
    /**
     * 下载进度监听
     *
     * @param bytesRead     下载好的数据
     * @param contentLength 总下载大小
     * @param done          是否完成
     */
    fun onProgress(bytesRead: Long, contentLength: Long, done: Boolean)
}