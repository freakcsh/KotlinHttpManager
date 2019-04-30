package com.freak.kotlinhttpmanager.kotlinhttpmanager

/**
 * 自定义api异常
 * @author freak
 * @date 2019/01/25
 */
class ApiException(detailMessage: String) : RuntimeException(detailMessage)