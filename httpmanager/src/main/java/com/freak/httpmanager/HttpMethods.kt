@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.freak.kotlinhttpmanager.kotlinhttpmanager

import android.text.TextUtils
import com.freak.httpmanager.ssl.HttpsUtils
import com.freak.kotlinhttpmanager.kotlinhttpmanager.log.HttpLogger
import com.freak.kotlinhttpmanager.kotlinhttpmanager.log.LogUtil
import io.reactivex.disposables.CompositeDisposable
import okhttp3.CookieJar
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

class HttpMethods {
    private val retrofit: Retrofit

    init {
        val builder = OkHttpClient.Builder()
        /**
         * 设置网络超时时间 时间按秒计算
         */
        builder.connectTimeout((if (instanceBuilder.getConnectTimeOut() == 0) CONNECT_TIMEOUT else instanceBuilder.getConnectTimeOut()).toLong(), TimeUnit.SECONDS)

        builder.readTimeout((if (instanceBuilder.getReadTimeOut() == 0) READ_TIMEOUT else instanceBuilder.getReadTimeOut()).toLong(), TimeUnit.SECONDS)

        builder.writeTimeout((if (instanceBuilder.getWriteTimeOut() == 0) WRITE_TIMEOUT else instanceBuilder.getWriteTimeOut()).toLong(), TimeUnit.SECONDS)
        val sslParams = HttpsUtils.getSslSocketFactory(emptyArray(), null, null)
        if (instanceBuilder.isUseDefaultSSLSocketFactory()) {
            builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
            builder.hostnameVerifier { hostname, session -> true }
        } else {
            if (instanceBuilder.getsSLSocketFactory() != null) {
                if (instanceBuilder.getTrustManager() != null) {
                    builder.sslSocketFactory(instanceBuilder.getsSLSocketFactory())
                } else {
                    builder.sslSocketFactory(instanceBuilder.getsSLSocketFactory(), instanceBuilder.getTrustManager())
                }
                builder.hostnameVerifier { hostname, session -> true }
            }

        }

        if (instanceBuilder.getsInterceptorList() != null && instanceBuilder.getsInterceptorList()!!.isNotEmpty()) {
            for (interceptor in instanceBuilder.getsInterceptorList()!!) {
                builder.addInterceptor(interceptor)
            }
        } else {
            if (instanceBuilder.getInterceptor() != null) {
                builder.addInterceptor(instanceBuilder.getInterceptor()!!)
            }
        }
        if (instanceBuilder.getLogger() != null) {
            val httpLoggingInterceptor = HttpLoggingInterceptor(instanceBuilder.getLogger()!!)
            //设置日志界级别
            httpLoggingInterceptor.level = if (instanceBuilder.getLevel() == null) HttpLoggingInterceptor.Level.BODY else instanceBuilder.getLevel()
            builder.addNetworkInterceptor(httpLoggingInterceptor)
        } else {
            LogUtil.init(if (TextUtils.isEmpty(instanceBuilder.getLogName())) "HttpMethods" else instanceBuilder.getLogName()!!, instanceBuilder.isIsOpenLog()!!)
            val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLogger())
            //设置日志界级别
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addNetworkInterceptor(httpLoggingInterceptor)
        }
        if (instanceBuilder.getCookieJar() != null) {
            builder.cookieJar(instanceBuilder.getCookieJar()!!)
        }
        if (instanceBuilder.getNetworkInterceptor() != null) {
            builder.addNetworkInterceptor(instanceBuilder.getNetworkInterceptor()!!)
        }

        /**
         * addConverterFactory 添加格式转换器工程  GsonConverterFactory
         * addCallAdapterFactory 添加调用适配器工程 RxJava2CallAdapterFactory
         * baseUrl 基础地址
         */
        retrofit = Retrofit.Builder().client(builder.build())
                .addConverterFactory(if (instanceBuilder.getFactory() == null) GsonConverterFactory.create() else instanceBuilder.getFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(instanceBuilder.getBaseUrl())
                .build()
    }

    fun <T> create(service: Class<T>): T {
        return this.retrofit.create(service)
    }

    companion object {
        private var mCompositeDisposable: CompositeDisposable? = null
        private var mHttpMethods: HttpMethods? = null
        private var sBuilder: Builder? = null
        private val CONNECT_TIMEOUT = 10
        private val READ_TIMEOUT = 10
        private val WRITE_TIMEOUT = 10
        /**
         * 在访问HttpMethods时创建单例
         *
         * @return 返回HttpMethods对象
         */
        val instanceBuilder: Builder
            get() {
                if (sBuilder == null) {
                    synchronized(Builder::class.java) {
                        sBuilder = Builder()
                    }
                }
                return sBuilder!!
            }
        /**
         * 获取构建对象
         *
         * @return
         */
        val instance: HttpMethods
            get() {
                if (mHttpMethods == null) {
                    synchronized(HttpMethods::class.java) {
                        mHttpMethods = HttpMethods()
                    }
                }
                return mHttpMethods!!
            }

        /**
         * 获取单例CompositeDisposable
         *
         * @return
         */
        val compositeDisposableInstance: CompositeDisposable
            get() {
                if (mCompositeDisposable == null) {
                    synchronized(CompositeDisposable::class.java) {
                        mCompositeDisposable = CompositeDisposable()
                    }
                }
                return mCompositeDisposable!!
            }
    }

    class Builder {
        private var connectTimeOut = 0
        private var readTimeOut = 0
        private var writeTimeOut = 0
        private var baseUrl: String? = null
        private var mFactory: Converter.Factory? = null
        private var mInterceptor: Interceptor? = null
        private var mNetworkInterceptor: Interceptor? = null
        private var sInterceptorList: MutableList<Interceptor>? = null
        private var mLogger: HttpLoggingInterceptor.Logger? = null
        private var mCookieJar: CookieJar? = null
        private var mLevel: HttpLoggingInterceptor.Level? = null
        private var logLevel: Int? = 0
        private var isOpenLog: Boolean? = false
        private var logName: String? = null
        private var sSLSocketFactory: SSLSocketFactory? = null
        private var trustManager: X509TrustManager? = null

        private var useDefaultSSLSocketFactory: Boolean = false

        fun isUseDefaultSSLSocketFactory(): Boolean {
            return useDefaultSSLSocketFactory
        }

        fun setUseDefaultSSLSocketFactory(useDefaultSSLSocketFactory: Boolean): Builder {
            this.useDefaultSSLSocketFactory = useDefaultSSLSocketFactory
            return this
        }

        fun getsSLSocketFactory(): SSLSocketFactory? {
            return sSLSocketFactory
        }

        fun setsSLSocketFactory(sSLSocketFactory: SSLSocketFactory): Builder {
            this.sSLSocketFactory = sSLSocketFactory
            return this
        }

        fun getTrustManager(): X509TrustManager? {
            return trustManager
        }

        fun setTrustManager(trustManager: X509TrustManager): Builder {
            this.trustManager = trustManager
            return this
        }

        fun isIsOpenLog(): Boolean? {
            return isOpenLog
        }

        /**
         * 设置是否开启日志打印
         *
         * @return
         */
        fun setIsOpenLog(isOpenLog: Boolean): Builder {
            this.isOpenLog = isOpenLog
            return this
        }

        fun getLogLevel(): Int? {
            return logLevel
        }

        /**
         * 设置日志打印机别
         *
         * @param logLevel
         * @return
         */
        fun setLogLevel(logLevel: Int): Builder {
            this.logLevel = logLevel
            return this
        }

        fun getLogName(): String? {
            return logName
        }

        /**
         * 设置日志打印级别
         *
         * @param logName
         * @return
         */
        fun setLogName(logName: String): Builder {
            this.logName = logName
            return this
        }

        /**
         * 设置服务器域名
         *
         * @param url 服务器域名
         */
        fun setBaseUrl(url: String): Builder {
            this.baseUrl = url
            return this
        }


        /**
         * 设置解析库
         * 可自定义解析逻辑
         *
         * @param factory 解析库
         */
        fun setFactory(factory: Converter.Factory): Builder {
            mFactory = factory
            return this
        }

        /**
         * 设置拦截器
         *
         * @param interceptor
         */
        fun setInterceptor(interceptor: Interceptor): Builder {
            mInterceptor = interceptor
            return this
        }

        /**
         * 设置多个拦截器
         *
         * @param interceptors
         */
        fun setInterceptors(vararg interceptors: Interceptor): Builder {
            sInterceptorList = ArrayList()
            for (interceptor in interceptors) {
                sInterceptorList!!.add(interceptor)
            }
            return this
        }

        /**
         * 设置自定义日志打印logger拦截器
         *
         * @param logger HttpLoggingInterceptor.Logger
         */
        fun setLogger(logger: HttpLoggingInterceptor.Logger): Builder {
            mLogger = logger
            return this
        }

        /**
         * 设置日志打印级别
         *
         * @param level
         */
        fun setLevel(level: HttpLoggingInterceptor.Level): Builder {
            mLevel = level
            return this
        }

        /**
         * 设置连接超时时间(秒)
         *
         * @param connectTimeOut 连接时间
         */
        fun setConnectTimeOut(connectTimeOut: Int): Builder {
            this.connectTimeOut = connectTimeOut
            return this
        }

        fun getConnectTimeOut(): Int {
            return connectTimeOut
        }

        fun getReadTimeOut(): Int {
            return readTimeOut
        }

        fun getWriteTimeOut(): Int {
            return writeTimeOut
        }

        fun getBaseUrl(): String? {
            return baseUrl
        }

        fun getFactory(): Converter.Factory? {
            return mFactory
        }

        fun getInterceptor(): Interceptor? {
            return mInterceptor
        }

        fun getNetworkInterceptor(): Interceptor? {
            return mNetworkInterceptor
        }

        fun getsInterceptorList(): List<Interceptor>? {
            return sInterceptorList
        }

        fun getLogger(): HttpLoggingInterceptor.Logger? {
            return mLogger
        }

        fun getCookieJar(): CookieJar? {
            return mCookieJar
        }

        fun getLevel(): HttpLoggingInterceptor.Level? {
            return mLevel
        }


        /**
         * 设置读取超时时间(秒)
         *
         * @param readTimeOut 读取时间
         */
        fun setReadTimeOut(readTimeOut: Int): Builder {
            this.readTimeOut = readTimeOut
            return this
        }

        /**
         * 设置写入超时时间(秒)
         *
         * @param writeTimeOut 写入时间
         */
        fun setWriteTimeOut(writeTimeOut: Int): Builder {
            this.writeTimeOut = writeTimeOut
            return this
        }

        /**
         * 设置cookieJar
         *
         * @param mCookieJar
         */
        fun setCookieJar(mCookieJar: CookieJar): Builder {
            this.mCookieJar = mCookieJar
            return this
        }

        /**
         * 设置网络拦截器
         *
         * @param mNetworkInterceptor
         */
        fun setNetworkInterceptor(mNetworkInterceptor: Interceptor): Builder {
            this.mNetworkInterceptor = mNetworkInterceptor
            return this
        }
    }
}