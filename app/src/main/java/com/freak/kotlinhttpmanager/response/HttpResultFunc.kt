package com.freak.kotlinhttpmanager.response


import com.freak.kotlinhttpmanager.entity.HttpResult
import com.freak.kotlinhttpmanager.kotlinhttpmanager.ApiException

import io.reactivex.functions.Function

/**
 * 此方法是接口返回数据的解析
 *
 * @param <T>
 * @author freak
 * @date 2019/01/25
</T> */
class HttpResultFunc<T> : Function<HttpResult<T>, T> {


    @Throws(Exception::class)
    override fun apply(tHttpResult: HttpResult<T>): T {
        if (tHttpResult.code != 200) {
            throw ApiException(tHttpResult.msg!!)
        }
        return tHttpResult.data!!
    }
}
