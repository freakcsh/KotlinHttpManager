package com.freak.kotlinhttpmanager.rxview

import android.view.View
import com.freak.kotlinhttpmanager.R
import com.freak.kotlinhttpmanager.app.BaseActivity
import com.freak.kotlinhttpmanager.entity.HttpResult
import com.freak.kotlinhttpmanager.kotlinhttpmanager.log.LogUtil
import com.freak.kotlinhttpmanager.kotlinhttpmanager.rxview.RxView
import kotlinx.android.synthetic.main.antivity_rx_view.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by Administrator on 2019/4/29.
 */
class RxViewActivity : BaseActivity<RxViewContract.View,RxViewContract.Presenter>(), RxViewContract.View, RxView.OnRxViewClickListener<View> {
    override fun showToast(toast: String) {

    }

    override fun onRxViewClick(view: View) {
        when (view.id) {
            R.id.rx_view -> {
                LogUtil.e("点击了")
            }
            else -> {

            }
        }
    }

    override fun onSuccess(httpResult: HttpResult<Any>) {
    }

    override fun onError(msg: String) {
    }

    override fun initEventAndData() {
        btn_rx_view.onClick {
            mPresenter.doLogin1("freak", "123456")
        }
        RxView.instanced.setOnClickListeners(this, rx_view);
    }

    override fun createPresenter(): RxViewPresenter {
        return RxViewPresenter()
    }

    override fun getLayout(): Int {
        return R.layout.antivity_rx_view
    }
}