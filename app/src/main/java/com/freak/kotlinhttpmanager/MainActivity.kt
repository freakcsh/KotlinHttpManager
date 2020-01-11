@file:Suppress("UNREACHABLE_CODE", "DUPLICATE_LABEL_IN_WHEN")

package com.freak.kotlinhttpmanager

import android.content.Intent
import android.view.View
import com.freak.kotlinhttpmanager.aopactivity.AopActivity
import com.freak.kotlinhttpmanager.app.BaseActivity
import com.freak.kotlinhttpmanager.down.DownActivity
import com.freak.kotlinhttpmanager.down.SystemDownloadActivity
import com.freak.kotlinhttpmanager.entity.HttpResult
import com.freak.kotlinhttpmanager.entity.LoginEntity
import com.freak.kotlinhttpmanager.kotlinhttpmanager.log.LogUtil
import com.freak.kotlinhttpmanager.rxview.RxViewActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : BaseActivity<MainPresenter<MainContract.View>>(), MainContract.View, View.OnClickListener {

    //    override fun onRxViewClick(view: View) {
//        when (view.id) {
//            R.id.btn_http -> {
////                LogUtil.e("点击了")
////                toast("网络请求")
////            btn_http.text = test("abfjabjkjk")
//                mPresenter.doLogin1("freak", "123456")
//            }
//            R.id.btn_rx -> {
//                var intent = Intent(this@MainActivity, RxViewActivity::class.java)
//                startActivity(intent)
//                LogUtil.e("点击了")
//            }
//            R.id.btn_ok_http_down -> {
//                var intent = Intent(this@MainActivity, DownActivity::class.java)
//                startActivity(intent)
//            }
//            R.id.btn_system_down_onclick -> {
//                var intent = Intent(this@MainActivity, SystemDownloadActivity::class.java)
//                startActivity(intent)
//            }
//            else -> {
//            }
//        }
//    }
//

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_http -> {
                LogUtil.e("点击了")
                toast("网络请求")
//            btn_http.text = test("abfjabjkjk")
//                mPresenter.doLogin1("freak", "123456")
                mPresenter.login11("123456", "123456", "1")
            }
            R.id.btn_rx -> {
                var intent = Intent(this@MainActivity, RxViewActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_ok_http_down -> {
                var intent = Intent(this@MainActivity, DownActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_system_down_onclick -> {
                var intent = Intent(this@MainActivity, SystemDownloadActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_aop -> {
                var intent = Intent(this@MainActivity, AopActivity::class.java)
                startActivity(intent)            }
            else -> {
            }
        }
    }

    override fun onSuccess(msg: String) {
        LogUtil.e("请求成功回调")
    }

    override fun onSuccess(loginEntity: LoginEntity) {
    }

    override fun onSuccess(httpResult: HttpResult<Any>) {
        LogUtil.d(httpResult.toString())
    }

    override fun onError(msg: String) {
    }

    override fun initEventAndData() {
//        RxView.instanced.setOnClickListeners(this, btn_http, btn_rx, btn_ok_http_down, btn_system_down_onclick)
        btn_http.setOnClickListener(this)
        btn_rx.setOnClickListener(this)
        btn_ok_http_down.setOnClickListener(this)
        btn_system_down_onclick.setOnClickListener(this)
        btn_aop.setOnClickListener(this)
//        btn_http.onClick {
//            toast("网络请求")
////            btn_http.text = test("abfjabjkjk")
//            mPresenter.doLogin1("freak", "123456")
//
//        }
//        btn_aop.onClick {
////            var intent = Intent(this@MainActivity, RxViewActivity::class.java)
////            startActivity(intent)
//            aop()
//            sing()
//        }
//        btn_aop.setOnClickListener {
//            aop()
//        }

    }


    override fun createPresenter(): MainPresenter<MainContract.View> {
        return MainPresenter(this)
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }





//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        btn_http.onClick {
//            toast("网络请求")
//            btn_http.text = test("abfjabjkjk")
//        }
//    }

    fun test(): String {

        return "2231231231351"
    }

    fun test(str: String): String {
        return str
    }

}
