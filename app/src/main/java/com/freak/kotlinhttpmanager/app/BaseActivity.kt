@file:Suppress("UNREACHABLE_CODE", "SENSELESS_COMPARISON", "UNNECESSARY_NOT_NULL_ASSERTION")

package com.freak.kotlinhttpmanager.app

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.freak.kotlinhttpmanager.kotlinhttpmanager.BasePresenter

abstract class BaseActivity<T : BasePresenter<*>> : AppCompatActivity() {
    lateinit var mPresenter: T
    private var mActivity: AppCompatActivity? = null
    protected abstract fun initEventAndData()

    protected abstract fun createPresenter(): T

    protected abstract fun getLayout(): Int

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
        /**
         * 创建presenter对象
         */
        mPresenter = createPresenter()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        super.onCreate(savedInstanceState)
        setContentView(getLayout())

        mActivity = this
        //活动控制器
        App.instance!!.addActivity(this)


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