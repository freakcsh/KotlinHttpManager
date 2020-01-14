package com.freak.kotlinhttpmanager.app

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.freak.kotlinhttpmanager.kotlinhttpmanager.BasePresenter


/**
 * MVP Fragment基类
 *
 * @author freak
 * @date 2019/9/11.
 */

abstract class BaseAbstractMvpFragment<V : BaseView,P:BasePresenter<V>> : Fragment()
     {
    lateinit var mPresenter: P
    protected lateinit var mView: View
    protected lateinit var mActivity: AppCompatActivity
    protected lateinit var mContext: Context
    private val netErrorView: RelativeLayout? = null
    protected var loadingView: View? = null
    private val hidden = false
//    private var unbinder: Unbinder? = null
    //Fragment的View加载完毕的标记
    private var isViewCreated: Boolean = false
    //Fragment对用户可见的标记
    private var isUIVisible: Boolean = false

    protected abstract val layoutId: Int

    protected abstract fun createPresenter(): P

    protected abstract fun initEventAndData(view: View)

    protected abstract fun initLazyData()

    /**
     * 显示加载中view，由子类实现
     */
    protected abstract fun showLoading()

    @SuppressLint("MissingSuperCall")
    override fun onAttach(context: Context) {
        mActivity = context as AppCompatActivity
        mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(layoutId, null)
        if (needRegisterNetworkChangeObserver()) {
            //            netErrorView = mView.findViewById(R.id.rl_net_error);
        }
        //返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
//        unbinder = ButterKnife.bind(this, mView)
        return mView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = createPresenter()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.attachView(this as V)
        initEventAndData(view)
        isViewCreated = true
        lazyLoad()
        showLoading()
        if (needRegisterNetworkChangeObserver()) {
            //            netErrorView = mView.findViewById(R.id.rl_net_error);
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            isUIVisible = true
            lazyLoad()
        } else {
            isUIVisible = false
        }
    }

    private fun lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            initLazyData()
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false
            isUIVisible = false
            //Log.i("xx","走了");
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        //        this.hidden=hidden;
        //        /**
        //         * 判断该页面是否需要开启网络变化监听，如需要则启动广播
        //         */
        //        if (!hidden){
        //            if (needRegisterNetworkChangeObserver()) {
        //                NetStateChangeReceiver.registerObserver(this);
        //            }
        //        }

    }

    override fun onResume() {
        super.onResume()
        /**
         * 判断该页面是否需要开启网络变化监听，如需要则启动广播
         */

    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (mPresenter != null) {
            mPresenter!!.detachView()
        }

//        unbinder!!.unbind()
    }

    /**
     * 是否需要注册网络变化的Observer,如果不需要监听网络变化,则返回false;否则返回true
     */
    protected fun needRegisterNetworkChangeObserver(): Boolean {
        return true
    }



    /**
     * 打开一个Activity 默认 不关闭当前activity
     *
     * @param className
     */
    fun gotoActivity(className: Class<*>) {
        gotoActivity(className, false)
    }

    /**
     * 打开一个Activity  关闭当前activity
     *
     * @param className
     */
    fun gotoActivityWithFinish(className: Class<*>) {
        gotoActivity(className, true)
    }

    /**
     * 打开一个Activity，并控制是否finish
     *
     * @param className              className
     * @param isCloseCurrentActivity 是否关闭
     */
    fun gotoActivity(className: Class<*>, isCloseCurrentActivity: Boolean) {
        gotoActivity(className, isCloseCurrentActivity, null)
    }

    /**
     * 打开一个activity，并传递数据
     *
     * @param className              className
     * @param isCloseCurrentActivity 是否关闭
     * @param bundle                 bundle数据
     */
    fun gotoActivity(
        className: Class<*>,
        isCloseCurrentActivity: Boolean = false,
        bundle: Bundle? = null
    ) {
        val intent = Intent(mActivity, className)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
        if (isCloseCurrentActivity) {
            mActivity.finish()
        }
    }

    /**
     * 打开一个带结果返回的activity，并传递数据
     *
     * @param className   className
     * @param bundle      bundle数据
     * @param requestCode 请求码
     */
    fun gotoActivityWithResult(className: Class<*>, bundle: Bundle?, requestCode: Int) {
        val intent = Intent(mActivity, className)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivityForResult(intent, requestCode)
    }
}

