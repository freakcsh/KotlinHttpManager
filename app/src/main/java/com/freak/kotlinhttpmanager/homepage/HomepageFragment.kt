package com.freak.freakmusic.model.example

import android.view.View
import android.widget.TextView
import com.freak.kotlinhttpmanager.R
import com.freak.kotlinhttpmanager.app.BaseAbstractMvpFragment
import com.freak.kotlinhttpmanager.kotlinhttpmanager.log.LogUtil


class HomepageFragment : BaseAbstractMvpFragment<HomepageContract.View,HomepageContract.Presenter>(),HomepageContract.View,View.OnClickListener {
    private lateinit var test:TextView
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.text_homepage->{
                LogUtil.e("点击")
                mPresenter.test()
            }
        }
    }

    override fun testSuccess() {
        LogUtil.e("成功")
        showToast("成功")
    }


    override val layoutId: Int
        get() = R.layout.fragment_homepage

    override fun createPresenter(): HomepagePresenter {
        return HomepagePresenter()
    }

    override fun initEventAndData(view: View) {
        LogUtil.e("初始化")
        test=view.findViewById(R.id.text_homepage)
        test.setOnClickListener(this)
    }

    override fun initLazyData() {


    }

    override fun showLoading() {
    }

    override fun showToast(toast: String) {
        LogUtil.e(toast)
    }
}