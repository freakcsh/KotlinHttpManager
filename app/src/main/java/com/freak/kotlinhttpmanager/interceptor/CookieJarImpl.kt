package com.freak.kotlinhttpmanager.interceptor


import com.freak.kotlinhttpmanager.app.App
import com.freak.kotlinhttpmanager.kotlinhttpmanager.PersistentCookieStore
import com.freak.kotlinhttpmanager.kotlinhttpmanager.log.LogUtil
import java.util.ArrayList
import java.util.concurrent.ConcurrentHashMap

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class CookieJarImpl : CookieJar {
    private val cookieStore = ConcurrentHashMap<String, List<Cookie>>()
    private val mPersistentCookieStore = PersistentCookieStore(App.instance!!.applicationContext)

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        //本地可校验cookie，并根据需要存储
        LogUtil.e("cookie--》\n" + cookies.toString())
        for (cookie in cookies) {
            mPersistentCookieStore.add(url, cookie)
        }
        //        cookieStore.put(url.host(), cookies);
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        ////加载新的cookies
        //        List<Cookie> cookies = cookieStore.get(url.host());
        val cookies = mPersistentCookieStore.get(url)
        LogUtil.d("cookie--》\n$cookies")
        return cookies
    }
}
