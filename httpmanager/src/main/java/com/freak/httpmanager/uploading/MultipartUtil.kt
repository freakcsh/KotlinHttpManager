package com.freak.httpmanager.uploading


import com.freak.kotlinhttpmanager.kotlinhttpmanager.download.ProgressListener
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*


/**
 * @author Administrator
 */
class MultipartUtil {

    private val maps = HashMap<String, String>()

    fun addParam(key: String, value: String): MultipartUtil? {
        maps[key] = value
        return sMultipartUtil
    }

    fun Build(): Map<String, RequestBody> {
        val bodyMap = HashMap<String, RequestBody>()
        for (key in maps.keys) {
            val body = RequestBody.create(MediaType.parse("multipart/form-data"), maps[key]!!)
            bodyMap[key] = body
        }
        return bodyMap
    }

    companion object {

        private var sMultipartUtil: MultipartUtil? = null

        val instance: MultipartUtil?
            get() {
                if (sMultipartUtil == null) {
                    synchronized(MultipartUtil::class.java) {
                        sMultipartUtil = MultipartUtil()
                    }
                }
                return sMultipartUtil
            }

        @JvmOverloads
        fun makeMultipart(key: String, file: File, listener: ProgressListener? = null): MultipartBody.Part? {

            val builder = MultipartBody.Builder()
            builder.setType(MultipartBody.FORM)
            if (file == null || !file.exists()) {
                try {
                    throw UploadRequestException("文件为null")
                } catch (e: UploadRequestException) {
                    e.printStackTrace()
                }

                return null
            }
            val body = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val upLoadBody = HttpUploadProgressResponseBody(body, listener!!)
            builder.addFormDataPart(key, file.name, upLoadBody)
            return builder.build().parts()[0]
        }


        /**
         * @param key      文件key
         * @param files    文件
         * @param listener 文件上传进度监听，只支持一个文件的时候使用，多文件不生效
         * @return List<MultipartBody.Part>
        </MultipartBody.Part> */
        @JvmOverloads
        fun makeMultipart(key: String, files: List<File>, listener: ProgressListener? = null): List<MultipartBody.Part>? {
            var listener = listener

            val builder = MultipartBody.Builder()
            builder.setType(MultipartBody.FORM)
            if (files.size > 1) {
                listener = null
            }
            for (i in files.indices) {
                if (files[i] == null) {
                    try {
                        throw UploadRequestException("文件为null")
                    } catch (e: UploadRequestException) {
                        e.printStackTrace()
                    }

                    return null
                }

                val body = RequestBody.create(MediaType.parse("multipart/form-data"), files[i])
                val upLoadBody = HttpUploadProgressResponseBody(body, listener!!)
                builder.addFormDataPart(key, files[i].name, upLoadBody)
            }
            return builder.build().parts()
        }
    }

}
