package com.freak.kotlinhttpmanager.kotlinhttpmanager

/**
 * Created by Administrator on 2019/4/28.
 */
interface ApiCallback<T> {
    /**
     * 请求成功回调
     *
     * @param model 实体
     */
    fun onSuccess(model: T)

    /**
     * 请求失败回调
     *
     * @param msg 错误信息/状态码
     */
    fun onFailure(msg: String)
}