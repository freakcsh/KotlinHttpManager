package com.freak.kotlinhttpmanager.entity

import org.json.JSONObject

/**
 *
 * @author Administrator
 * @date 2018/4/10
 */

class BaseBean<T> {

    private var respCode: String? = null

    private var respDesc: String? = null

    var respData: T? = null

    private var jsonObject: JSONObject? = null
    //
    private var errorJson: String? = null

    fun getRespCode(): String {
        return if (respCode == null) "" else respCode!!
    }

    fun setRespCode(respCode: String) {
        this.respCode = respCode
    }

    fun getRespDesc(): String {
        return if (respDesc == null) "" else respDesc!!
    }

    fun setRespDesc(respDesc: String) {
        this.respDesc = respDesc
    }


    fun getJsonObject(): JSONObject {
        return if (jsonObject == null) JSONObject() else jsonObject!!
    }

    fun setJsonObject(jsonObject: JSONObject) {
        this.jsonObject = jsonObject
    }

    fun getErrorJson(): String {
        return if (errorJson == null) "" else errorJson!!
    }

    fun setErrorJson(errorJson: String) {
        this.errorJson = errorJson
    }

    override fun toString(): String {
        return if (jsonObject == null) {
            "BaseBean{" +
                    "respCode='" + respCode + '\''.toString() +
                    ", respDesc='" + respDesc + '\''.toString() +
                    ", respData=" + respData +
                    ", errorJson=" + errorJson +
                    '}'.toString()
        } else "BaseBean{" +
                "respCode='" + respCode + '\''.toString() +
                ", respDesc='" + respDesc + '\''.toString() +
                ", respData=" + respData +
                ", jsonObject=" + jsonObject!!.toString() +
                '}'.toString()
    }
}
