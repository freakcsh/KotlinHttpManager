package com.freak.kotlinhttpmanager.kotlinhttpmanager.download

/**
 * @author Administrator
 */
class HttpDownInfo {
    /**
     * id                   下载id
     * readLength           完成大小(当前下载大小)
     * countLength          总大小
     * url                  下载地址
     * savePath             保存路径
     * listener             下载监听
     * state                下载状态
     * isStartMoreThread    是否开启多线程下载
     * downThreadCount      下载线程数
     */
    var id: Long = 0
    var readLength: Long = 0
    var countLength: Long = 0
    var url: String? = null
    var savePath: String? = null
    var listener: HttpDownListener? = null
    var state: Int = 0
    var isStartMoreThread = false
    var downThreadCount = 3

    init {
        state = HttpDownStatus.WAITING
    }

    override fun toString(): String {
        return "HttpDownInfo{" +
                "id=" + id +
                ", readLength=" + readLength +
                ", countLength=" + countLength +
                ", url='" + url + '\'' +
                ", savePath='" + savePath + '\'' +
                ", listener=" + listener +
                ", state=" + state +
                ", isStartMoreThread=" + isStartMoreThread +
                ", downThreadCount=" + downThreadCount +
                '}'
    }
}
