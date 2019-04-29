package com.freak.kotlinhttpmanager.aop

import android.os.SystemClock
import android.view.View
import com.freak.kotlinhttpmanager.kotlinhttpmanager.log.LogUtil

/**
 *
 * @author Administrator
 * @date 2019/4/23
 */

object AopClickUtil {
    /**
     * 最近一次点击的时间
     */
    private var mLastClickTime: Long = 0
    /**
     * 最近一次点击的控件ID
     */
    private var mLastClickViewId: Int = 0

    /**
     * 是否是快速点击
     *
     * @param v  点击的控件
     * @param intervalMillis  时间间期（毫秒）
     * @return  true:是，false:不是
     */
    fun isFastDoubleClick(v: View, intervalMillis: Long): Boolean {
        LogUtil.e("调用注解")
        val viewId = v.id
        //        long time = System.currentTimeMillis();
        val time = SystemClock.elapsedRealtime()
        val timeInterval = Math.abs(time - mLastClickTime)
        if (timeInterval < intervalMillis && viewId == mLastClickViewId) {
            return true
        } else {
            mLastClickTime = time
            mLastClickViewId = viewId
            return false
        }
    }
}
