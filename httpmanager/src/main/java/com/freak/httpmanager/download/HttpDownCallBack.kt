package com.freak.kotlinhttpmanager.kotlinhttpmanager.download

import android.annotation.SuppressLint
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

/**
 * @author Administrator
 */
class HttpDownCallBack<T>(var httpDownInfo: HttpDownInfo?) : Observer<T>, ProgressListener {
    private val mHttpDownListener: HttpDownListener? = httpDownInfo?.listener
    var disposable: Disposable? = null
        private set
    private var mResultListener: ResultListener? = null

    fun setResultListener(resultListener: ResultListener) {
        mResultListener = resultListener
    }

    @SuppressLint("CheckResult")
    override fun onProgress(bytesRead: Long, contentLength: Long, done: Boolean) {
        var bytesRead = bytesRead
        if (httpDownInfo!!.countLength > contentLength) {
            bytesRead = httpDownInfo!!.countLength - contentLength + bytesRead
        } else {
            httpDownInfo!!.countLength = contentLength
        }

        httpDownInfo!!.readLength = bytesRead
        if (mHttpDownListener != null) {
            if (httpDownInfo!!.state == HttpDownStatus.PAUSE || httpDownInfo!!.state == HttpDownStatus.STOP) {
                return
            }

            Observable.just(bytesRead).observeOn(AndroidSchedulers.mainThread()).subscribe { aLong ->
                Log.d("DownloadProgress", "Progress = " + aLong!!)
                mHttpDownListener.downProgress(aLong, httpDownInfo!!.countLength)
            }
        }
    }


    override fun onSubscribe(d: Disposable) {
        disposable = d
    }

    override fun onNext(t: T) {

    }

    override fun onError(e: Throwable) {
        httpDownInfo!!.state = HttpDownStatus.ERROR
        mHttpDownListener?.downError(httpDownInfo!!, e.message.toString())
        if (mResultListener != null) {
            mResultListener!!.downError(httpDownInfo!!)
        }
        //        mHandler.sendEmptyMessage(HttpDownStatus.ERROR);
    }

    override fun onComplete() {
        httpDownInfo!!.state = HttpDownStatus.FINISH
        mHttpDownListener?.downFinish(httpDownInfo!!)
        if (mResultListener != null) {
            mResultListener!!.downFinish(httpDownInfo!!)
        }
        //        mHandler.sendEmptyMessage(HttpDownStatus.FINISH);
        HttpDownMethods.instance.remove(httpDownInfo!!)
    }

    /**
     * 开始下载
     */
    fun downStart() {
        httpDownInfo!!.state = HttpDownStatus.START
        mHttpDownListener?.downStart()
        //        mHandler.sendEmptyMessage(HttpDownStatus.START);
    }

    /**
     * 暂停下载
     */
    fun downPause() {
        httpDownInfo!!.state = HttpDownStatus.PAUSE
        mHttpDownListener?.downPause(httpDownInfo!!, httpDownInfo!!.readLength)
        //        mHandler.sendEmptyMessage(HttpDownStatus.PAUSE);
    }

    /**
     * 停止下载、取消下载
     *
     * @param
     */
    fun downStop() {
        httpDownInfo!!.state = HttpDownStatus.STOP
        mHttpDownListener?.downStop(httpDownInfo!!)
        //        mHandler.sendEmptyMessage(HttpDownStatus.STOP);
    }


    //    @SuppressLint("HandlerLeak")
    //    private Handler mHandler = new Handler() {
    //        @Override
    //        public void handleMessage(Message msg) {
    //            switch (msg.what) {
    //                case HttpDownStatus.START:
    //                    HttpDownMethods.getInstance().handleTask(HttpDownStatus.START);
    //                    break;
    //                case HttpDownStatus.PAUSE:
    //                    HttpDownMethods.getInstance().handleTask(HttpDownStatus.PAUSE);
    //                    break;
    //                case HttpDownStatus.STOP:
    //                    HttpDownMethods.getInstance().handleTask(HttpDownStatus.STOP);
    //                    break;
    //                case HttpDownStatus.FINISH:
    //                    HttpDownMethods.getInstance().handleTask(HttpDownStatus.FINISH);
    //                    break;
    //                case HttpDownStatus.ERROR:
    //                    HttpDownMethods.getInstance().handleTask(HttpDownStatus.ERROR);
    //                    break;
    //                default:
    //                    break;
    //            }
    //        }
    //    };

}
