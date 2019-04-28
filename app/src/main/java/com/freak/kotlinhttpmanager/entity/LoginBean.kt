package com.freak.kotlinhttpmanager.entity

/**
 * Created by Administrator on 2018/12/25.
 */

class LoginBean {
    var userName: String? = null
    var pwd: String? = null
    var abc: String? = null
    var msg: String? = null

    override fun toString(): String {
        return "LoginBean{" +
                "userName='" + userName + '\''.toString() +
                ", pwd='" + pwd + '\''.toString() +
                ", abc='" + abc + '\''.toString() +
                ", msg='" + msg + '\''.toString() +
                '}'.toString()
    }

}
