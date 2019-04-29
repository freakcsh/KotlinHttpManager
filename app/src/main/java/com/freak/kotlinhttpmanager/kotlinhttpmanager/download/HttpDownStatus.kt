package com.freak.kotlinhttpmanager.kotlinhttpmanager.download

/**
 * 下载状态
 *
 * @author Administrator
 */
object HttpDownStatus {
    /**
     * START    正在下载（开始下载）
     * PAUSE    暂停下载
     * STOP     停止下载（取消下载）
     * FINISH   下载完成
     * ERROR    下载错误
     * WAITING  等待下载
     */
    val START = 1
    val PAUSE = 2
    val STOP = 3
    val FINISH = 4
    val ERROR = 5
    val WAITING = 6
}
