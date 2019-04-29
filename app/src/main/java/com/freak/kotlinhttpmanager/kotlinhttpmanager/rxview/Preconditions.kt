package com.freak.kotlinhttpmanager.kotlinhttpmanager.rxview

import android.os.Looper

/**
 *
 * @author Administrator
 * @date 2019/4/8
 */

class Preconditions private constructor() {

    init {
        throw AssertionError("No instances.")
    }

    companion object {
        fun checkArgument(assertion: Boolean, message: String) {
            if (!assertion) {
                throw IllegalArgumentException(message)
            }
        }

        fun <T> checkNotNull(value: T?, message: String): T {
            if (value == null) {
                throw NullPointerException(message)
            }
            return value
        }

        fun checkUiThread() {
            if (Looper.getMainLooper() != Looper.myLooper()) {
                throw IllegalStateException(
                        "Must be called from the main thread. Was: " + Thread.currentThread())
            }
        }
    }
}
