package com.freak.httpmanager.uploading


import com.freak.kotlinhttpmanager.kotlinhttpmanager.download.ProgressListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.*
import java.io.IOException


/**
 * @author Administrator
 */
class HttpUploadProgressResponseBody : RequestBody {

    private val mRequestBody: RequestBody
    private var mBufferedSink: BufferedSink? = null
    private var mListener: ProgressListener? = null

    constructor(mRequestBody: RequestBody) {
        this.mRequestBody = mRequestBody
    }

    constructor(mRequestBody: RequestBody, listener: ProgressListener) {
        this.mRequestBody = mRequestBody
        this.mListener = listener
    }


    override fun contentType(): MediaType? {
        return mRequestBody.contentType()
    }

    @Throws(IOException::class)
    override fun contentLength(): Long {
        return mRequestBody.contentLength()
    }

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        if (mBufferedSink == null) {
            mBufferedSink = Okio.buffer(sink(sink))
        }
        mRequestBody.writeTo(mBufferedSink!!)
        //必须调用flush，否则最后一部分数据可能不会被写入
        mBufferedSink!!.flush()
    }


    private fun sink(sink: Sink): Sink {
        return object : ForwardingSink(sink) {
            //当前进度
            internal var currentByteLength = 0L
            //总长度
            internal var allByteLength = 0L

            @Throws(IOException::class)
            override fun write(source: Buffer, byteCount: Long) {
                super.write(source, byteCount)

                currentByteLength = +byteCount
                if (allByteLength == 0L) {
                    allByteLength = contentLength()
                }
                Observable.just(currentByteLength).observeOn(AndroidSchedulers.mainThread()).subscribe { aLong ->
                    mListener?.onProgress(aLong!!, allByteLength, aLong == allByteLength)
                }


            }
        }
    }

}
