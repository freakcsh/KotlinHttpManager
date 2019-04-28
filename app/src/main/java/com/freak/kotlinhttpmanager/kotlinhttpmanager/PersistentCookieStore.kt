package com.freak.kotlinhttpmanager.kotlinhttpmanager

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import android.util.Log
import okhttp3.Cookie
import okhttp3.HttpUrl
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.ArrayList
import kotlin.experimental.and

/**
 * Created by Administrator on 2019/4/28.
 */
class PersistentCookieStore(context: Context) {
    companion object {
        private val COOKIE_PREFS = "Cookies_Prefs"
        private val LOG_TAG = "PersistentCookieStore"
    }

    /**
     * 根据各自的业务形态进行定制，可以使用hashMap，甚至也可以选用其他数据结构存储Cookie。
     * 例子中使用了HashMap实现，key作为一级域名；value则是以cookieToken为key的Cookie映射，cookieToken的获取见下述方法。
     */
    private val cookies: MutableMap<String, ConcurrentHashMap<String, Cookie>>


    private val cookiePrefs: SharedPreferences

    /**
     * 构造方法初始化
     */
    init {
        cookiePrefs = context.getSharedPreferences(COOKIE_PREFS, 0)
        cookies = ConcurrentHashMap()
        //将持久化的cookies缓存到内存中 即map cookies
        val prefsMap = cookiePrefs.all
        for ((key, value) in prefsMap) {
            val cookieNames = TextUtils.split(value as String, ",")
            for (name in cookieNames) {
                val encodedCookie = cookiePrefs.getString(name, null)
                if (encodedCookie != null) {
                    val decodedCookie = decodeCookie(encodedCookie)
                    if (decodedCookie != null) {
                        if (!cookies.containsKey(key)) {
                            cookies.put(key, ConcurrentHashMap())
                        }
                        cookies[key]?.put(name, decodedCookie)
                    }
                }
            }
        }
    }

    /**
     * cookieToken的获取
     */
    private fun getCookieToken(cookie: Cookie): String {
        return cookie.name() + "@" + cookie.domain()
    }

    /**
     * cookie的存储
     * key是自定义的
     *
     * @param key    自定义的key
     * @param cookie Cookie
     */
    @SuppressLint("CommitPrefEdits")
    fun add(key: String, cookie: Cookie) {
        val name = getCookieToken(cookie)
        if (!cookies.containsKey(key)) {
            cookies.put(key, ConcurrentHashMap())
        }
        cookies[key]?.put(name, cookie)
        val prefsWriter = cookiePrefs.edit()
        if (cookies.containsKey(key)) {
            prefsWriter.putString(key, TextUtils.join(",", cookies[key]?.keys))
            prefsWriter.putString(name, encodeCookie(SerializableHttpCookie(cookie)))
            prefsWriter.apply()
        }
    }

    /**
     * 获取cookie
     * 保存的key之是自定义的值
     *
     * @param key 自定义的key
     * @return
     */
    fun get(key: String): List<Cookie> {
        val ret = ArrayList<Cookie>()
        if (cookies.containsKey(key)) {
            ret.addAll(cookies[key]?.values!!)
        }
        return ret
    }

    @SuppressLint("CommitPrefEdits")
            /**
             * cookie的存储
             * key是url.host()
             *
             * @param url    HttpUrl
             * @param cookie Cookie
             */
    fun add(url: HttpUrl, cookie: Cookie) {
        val name = getCookieToken(cookie)
        if (!cookies.containsKey(url.host())) {
            cookies.put(url.host(), ConcurrentHashMap())
        }
        cookies[url.host()]?.put(name, cookie)
        //将cookies持久化到本地
        val prefsWriter = cookiePrefs.edit()
        if (cookies.containsKey(url.host())) {
            prefsWriter.putString(url.host(), TextUtils.join(",", cookies[url.host()]?.keys))
            prefsWriter.putString(name, encodeCookie(SerializableHttpCookie(cookie)))
            prefsWriter.apply()
        }
    }

    /**
     * 获取cookie
     * 保存的key之是url.host()
     *
     * @param url HttpUrl
     * @return
     */
    fun get(url: HttpUrl): List<Cookie> {
        val ret = ArrayList<Cookie>()
        if (cookies.containsKey(url.host())) {
            ret.addAll(cookies[url.host()]?.values!!)
        }
        return ret
    }

    /**
     * 删除所有
     *
     * @return
     */
    fun removeAll(): Boolean {
        val prefsWriter = cookiePrefs.edit()
        prefsWriter.clear()
        prefsWriter.apply()
        cookies.clear()
        return true
    }

    /**
     * 删除
     * @param key 自定义的key
     * @return
     */
    fun remove(key: String): Boolean {
        if (cookies.containsKey(key)) {
            val prefsWriter = cookiePrefs.edit()
            for (cookie in cookies[key]?.values!!) {
                val name = getCookieToken(cookie)
                if (cookiePrefs.contains(name)) {
                    prefsWriter.remove(name)
                }
            }
            prefsWriter.remove(key)
            prefsWriter.apply()
            cookies[key]?.clear()
            cookies.remove(key)
            return true
        } else {
            return false
        }
    }

    /**
     * 删除
     *
     * @param url
     * @param cookie
     * @return
     */
    fun remove(url: HttpUrl, cookie: Cookie): Boolean {
        val name = getCookieToken(cookie)
        if (cookies.containsKey(url.host()) && cookies[url.host()]?.containsKey(name)!!) {
            cookies[url.host()]?.remove(name)
            val prefsWriter = cookiePrefs.edit()
            if (cookiePrefs.contains(name)) {
                prefsWriter.remove(name)
            }
            prefsWriter.putString(url.host(), TextUtils.join(",", cookies[url.host()]?.keys))
            prefsWriter.apply()
            return true
        } else {
            return false
        }
    }

    /**
     * cookies 序列化成 string
     *
     * @param cookie 要序列化的cookie
     * @return 序列化之后的string
     */
    private fun encodeCookie(cookie: SerializableHttpCookie?): String? {
        if (cookie == null) {
            return null
        }
        val os = ByteArrayOutputStream()
        try {
            val outputStream = ObjectOutputStream(os)
            outputStream.writeObject(cookie)
        } catch (e: Exception) {
            Log.d(LOG_TAG, "IOException in encodeCookie", e)
            return null
        }
        return byteArrayToHexString(os.toByteArray())
    }

    /**
     * 将字符串反序列化成cookies
     *
     * @param cookieString cookies string
     * @return cookie object
     */
    private fun decodeCookie(cookieString: String): Cookie? {
        val bytes = hexStringToByteArray(cookieString)
        val byteArrayInputStream = ByteArrayInputStream(bytes)
        var cookie: Cookie? = null
        try {
            val objectInputStream = ObjectInputStream(byteArrayInputStream)
            cookie = (objectInputStream.readObject() as SerializableHttpCookie).cookies!!
        } catch (e: Exception) {
            Log.d(LOG_TAG, "IOException in decodeCookie", e)
        } catch (e: ClassNotFoundException) {
            Log.d(LOG_TAG, "ClassNotFoundException in decodeCookie", e)
        }
        return cookie
    }

    /**
     * 二进制数组转十六进制字符串
     *
     * @param bytes byte array to be converted
     * @return string containing hex values
     */
    private fun byteArrayToHexString(bytes: ByteArray): String {
        val sb = StringBuilder(bytes.size * 2)
        for (element in bytes) {
            val v = element and 0xff.toByte()
            if (v < 16) {
                sb.append('0')
            }
            sb.append(Integer.toHexString(v.toInt()))
        }
        return sb.toString().toUpperCase(Locale.US)
    }

    /**
     * 十六进制字符串转二进制数组
     *
     * @param hexString string of hex-encoded values
     * @return decoded byte array
     */
    private fun hexStringToByteArray(hexString: String): ByteArray {
        val len = hexString.length
        val data = ByteArray(len / 2)
        var i = 0
        while (i < len) {
            data[i / 2] = ((Character.digit(hexString[i], 16) shl 4) + Character.digit(hexString[i + 1], 16)).toByte()
            i += 2
        }
        return data
    }
}