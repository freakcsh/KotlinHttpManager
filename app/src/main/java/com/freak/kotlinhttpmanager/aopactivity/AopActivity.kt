package com.freak.kotlinhttpmanager.aopactivity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.freak.kotlinhttpmanager.R
import com.freak.kotlinhttpmanager.aop.AopOnclick
import com.freak.kotlinhttpmanager.kotlinhttpmanager.log.LogUtil
import kotlinx.android.synthetic.main.activity_aop.*

/**
 * Created by Administrator on 2019/4/30.
 */
@SuppressLint("SetTextI18n")
class AopActivity : AppCompatActivity(), View.OnClickListener {
    @AopOnclick
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.aop -> {
                LogUtil.e("点击")
            }
        }
    }

    var nornalSum = 0
    var singleSum = 0
    var saopSum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aop)
        aop.setOnClickListener(this)
//        aop.onClick {
//            test()
////            sing()
//            aop()
//        }
        aop.setOnClickListener {
            aop()
        }
    }

    private fun test() {
        text.text = "点击次数:${nornalSum++}次"
    }

//    @AopOnclick
//    fun sing() {
//        text1.text = "Aop默认时间防止多次点击:${singleSum++}次"
//    }


    @AopOnclick(2000)
    fun aop() {
        text2.text = "aop自定义时间点击次数:${saopSum++}次"
    }
}