package com.freak.kotlinhttpmanager.kotlinhttpmanager.log

import java.io.UnsupportedEncodingException

object UrlUtil {
    private val ENCODE = "UTF-8"

    /**
     * URL 解码
     *
     * @return String
     * @author lifq
     */
    fun getURLDecoderString(str: String?): String {
        var result = ""
        if (null == str) {
            return ""
        }
        try {
            result = java.net.URLDecoder.decode(str, ENCODE)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return result
    }

    /**
     * URL 转码
     *
     * @return String
     * @author lifq
     */
    fun getURLEncoderString(str: String?): String {
        var result = ""
        if (null == str) {
            return ""
        }
        try {
            result = java.net.URLEncoder.encode(str, ENCODE)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return result
    }
}