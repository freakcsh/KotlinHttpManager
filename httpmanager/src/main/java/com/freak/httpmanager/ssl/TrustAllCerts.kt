package com.freak.httpmanager.ssl

import java.security.cert.X509Certificate

import javax.net.ssl.X509TrustManager

class TrustAllCerts : X509TrustManager {
    // 检查客户端证书
    override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}

    // 检查服务器端证书
    override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}

    // 返回受信任的X509证书数组
    override fun getAcceptedIssuers(): Array<X509Certificate?> {
        return arrayOfNulls(0)
    }
}
