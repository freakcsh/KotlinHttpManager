package com.freak.kotlinhttpmanager.entity


/**
 * Created by Administrator on 2019/1/25.
 */

class HttpResult<T> {
    var code: Int = 0

    var msg: String? = null

    var data: T? = null


    override fun toString(): String {
        return "HttpResult{" +
                "code=" + code +
                ", msg='" + msg + '\''.toString() +
                ", data=" + data +
                '}'.toString()
    }
}
