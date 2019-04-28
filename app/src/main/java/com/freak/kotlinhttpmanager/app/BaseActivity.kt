package com.freak.kotlinhttpmanager.app

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.freak.kotlinhttpmanager.kotlinhttpmanager.BasePresenter
import com.freak.kotlinhttpmanager.kotlinhttpmanager.RxBaseView

abstract class BaseActivity<T : BasePresenter<RxBaseView>> : AppCompatActivity(), RxBaseView {
    protected var mPresenter: T? = null
    protected var mActivity: Activity? = null
    protected abstract fun initEventAndData()

    protected abstract fun createPresenter(): T

    protected abstract fun getlayout(): Int

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * 创建presenter对象
         */
        mPresenter = createPresenter()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        super.onCreate(savedInstanceState)
        setContentView(getlayout())

        mActivity = this
        //活动控制器
        App.instance!!.addActivity(this)


        if (mPresenter != null) {
            mPresenter!!.attachView(this)
        }

        initEventAndData()
    }

    override fun onDestroy() {
        super.onDestroy()
        /**
         * presenter 解除view订阅
         */
        if (mPresenter != null) {
            mPresenter!!.detachView()
        }
        App.instance!!.removeActivity(this)
    }

    /**
     * 是否需要注册网络变化的Observer,如果不需要监听网络变化,则返回false;否则返回true
     */
    protected fun needRegisterNetworkChangeObserver(): Boolean {
        return true
    }

    @JvmOverloads
    fun gotoActivity(clz: Class<*>, isCloseCurrentActivity: Boolean = false, ex: Bundle? = null) {
        val intent = Intent(this, clz)
        if (ex != null) intent.putExtras(ex)
        startActivity(intent)
        if (isCloseCurrentActivity) {
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                super.onBackPressed()//返回
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}