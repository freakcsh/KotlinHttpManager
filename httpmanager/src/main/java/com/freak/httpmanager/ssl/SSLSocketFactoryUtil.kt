package com.freak.httpmanager.ssl

import java.security.SecureRandom

import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager

object SSLSocketFactoryUtil {
    fun createSSLSocketFactory(): SSLSocketFactory? {
        var ssfFactory: SSLSocketFactory? = null

        try {
            val sc = SSLContext.getInstance("TLS")
            sc.init(null, arrayOf<TrustManager>(TrustAllCerts()), SecureRandom())

            ssfFactory = sc.socketFactory
        } catch (e: Exception) {
        }

        return ssfFactory
    }

}
