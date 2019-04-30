package com.freak.kotlinhttpmanager.kotlinhttpmanager.download

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*
import java.io.IOException

/**
 * 下载进度ResponseBody
 *
 * @author Administrator
 */
class HttpDownProgressResponseBody(private val mResponseBody: ResponseBody, private val mProgressListener: ProgressListener?) : ResponseBody() {
    private var mBufferedSource: BufferedSource? = null

    /**
     * @return 返回响应内容的类型，比如image/jpeg
     */
    override fun contentType(): MediaType? {
        return mResponseBody.contentType()
    }

    /**
     * @return 返回响应内容的长度
     */
    override fun contentLength(): Long {
        return mResponseBody.contentLength()
    }

    override fun source(): BufferedSource {
        if (mBufferedSource == null) {
            mBufferedSource = Okio.buffer(source(mResponseBody.source()))
        }
        return mBufferedSource!!
    }

    private fun source(source: BufferedSource): Source {
        return object : ForwardingSource(source) {
            internal var totalBytesRead = 0L

            @Throws(IOException::class)
            override fun read(sink: Buffer, byteCount: Long): Long {
                var byteRead: Long = 0
                try {
                    byteRead = super.read(sink, byteCount)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                //不断统计当前下载好的数据
                totalBytesRead += if (byteRead != ((-1).toLong())) {
                    byteRead
                } else 0
                //接口回调
                mProgressListener?.onProgress(totalBytesRead, mResponseBody.contentLength(), totalBytesRead == ((-1).toLong()))
                return byteRead
            }
        }
    }
}
