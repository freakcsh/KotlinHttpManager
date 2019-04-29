@file:Suppress("DEPRECATED_JAVA_ANNOTATION")

package com.freak.kotlinhttpmanager.aop

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Created by Administrator on 2019/4/23.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class AopOnclick(
        /**
         * 点击间隔时间
         */
        val value: Long = 1000)
