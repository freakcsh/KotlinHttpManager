package com.freak.kotlinhttpmanager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_http.onClick {
            toast("网络请求")
            btn_http.text = test("abfjabjkjk")
        }
    }

    fun test(): String {

        return "2231231231351"
    }

    fun test(str: String): String {
        return str
    }
}
