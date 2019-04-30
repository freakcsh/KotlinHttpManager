@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.freak.kotlinhttpmanager.kotlinhttpmanager.download

import android.os.Environment
import android.text.TextUtils
import com.freak.kotlinhttpmanager.kotlinhttpmanager.HttpMethods
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.*
import java.nio.channels.FileChannel
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * 下载助手
 *
 * @author Administrator
 */
class HttpDownMethods {
    /**
     * 这是连接网络的时间
     */
    private var connectTimeOut = 0
    private var readTimeOut = 0
    private var writeTimeOut = 0
    private val baseUrl: String = HttpMethods.instanceBuilder.getBaseUrl()!!
    private val mHttpDownInfoSet: MutableSet<HttpDownInfo>
    private val mCallBackMap: MutableMap<String, HttpDownCallBack<HttpDownInfo>>
    private val mTaskList: List<HttpDownInfo>? = null
    private var mHttpDownListener: HttpDownListener? = null

    /**
     * 取消下载\停止下载时，是否删除未下载完成的文件，默认不删除
     */
    private var mIsDeleteFile = false

    init {
        mHttpDownInfoSet = HashSet()
        mCallBackMap = HashMap<String, HttpDownCallBack<HttpDownInfo>>()
        //        mTaskList = Collections.synchronizedList(new ArrayList<HttpDownInfo>());
        //        setTaskCount(TASK_COUNT);
    }

    fun setHttpDownListener(httpDownListener: HttpDownListener) {
        mHttpDownListener = httpDownListener
    }

    fun setDeleteFile(deleteFile: Boolean) {
        mIsDeleteFile = deleteFile
    }

    //    /**
    //     * 添加下载任务
    //     *
    //     * @param httpDownInfo
    //     */
    //    public boolean addDownTask(HttpDownInfo httpDownInfo) {
    //        if (mTaskList != null && !mTaskList.contains(httpDownInfo)) {
    //            mTaskList.add(httpDownInfo);
    //            return true;
    //        } else {
    //            return false;
    //        }
    //    }
    //
    //    /**
    //     * 一键添加所有下载任务
    //     *
    //     * @param httpDownInfo
    //     */
    //    public synchronized void addDownTaskList(List<HttpDownInfo> httpDownInfo) {
    //        for (HttpDownInfo mHttpDownInfo : httpDownInfo) {
    //            addDownTask(mHttpDownInfo);
    //        }
    //    }
    //
    //    public void moreTaskDownloadStart() {
    //        if (mTaskList == null) {
    //            new IllegalArgumentException("list is null");
    //            return;
    //        }
    //        for (HttpDownInfo httpDownInfo : mTaskList) {
    //            if (httpDownInfo.getState() == HttpDownStatus.FINISH || httpDownInfo.getState() == HttpDownStatus.STOP){
    ////                mTaskList.remove()
    //            }
    //        }
    //        downStartAll(mTaskList);
    //    }
    //
    //    private synchronized void downStartAll(List<HttpDownInfo> httpDownInfoList) {
    //
    //        for (ListIterator<HttpDownInfo> iterator = httpDownInfoList.listIterator(); iterator.hasNext(); ) {
    //            Logger.e("剩余下载任务数：" + getTaskCount());
    //            HttpDownInfo httpDownInfo = iterator.next();
    //            if (getTaskCount() > 0) {
    //                if (httpDownInfo.getState() == HttpDownStatus.WAITING) {
    //                    Logger.e("下载等待任务");
    //                    if (mHttpDownListener != null) {
    //                        downStart(httpDownInfo, mHttpDownListener);
    //                    } else {
    //                        downStart(httpDownInfo, null);
    //                    }
    //                    setTaskCount(getTaskCount() - 1);
    //                }
    //                if (httpDownInfo.getState() == HttpDownStatus.PAUSE) {
    //                    Logger.e("下载暂停任务");
    //                    if (mHttpDownListener != null) {
    //                        downStart(httpDownInfo, mHttpDownListener);
    //                    } else {
    //                        downStart(httpDownInfo, null);
    //                    }
    //                    setTaskCount(getTaskCount() - 1);
    //                }
    //                if (httpDownInfo.getState() == HttpDownStatus.ERROR) {
    //                    Logger.e("下载错误任务");
    //                    if (mHttpDownListener != null) {
    //                        downStart(httpDownInfo, mHttpDownListener);
    //                    } else {
    //                        downStart(httpDownInfo, null);
    //                    }
    //                    setTaskCount(getTaskCount() - 1);
    //                }
    //            }
    //
    //        }
    //
    //    }

    //    public void handleTask(int type) {
    //        switch (type) {
    //            case HttpDownStatus.START:
    //                Log.d("DownTask", "开始下载,下载总数量" + mTaskList.size());
    //                break;
    //            case HttpDownStatus.PAUSE:
    //                setTaskCount(getTaskCount() + 1);
    //                moreTaskDownloadStart();
    //                break;
    //            case HttpDownStatus.STOP:
    //                setTaskCount(getTaskCount() + 1);
    //                moreTaskDownloadStart();
    //                break;
    //            case HttpDownStatus.FINISH:
    //                setTaskCount(getTaskCount() + 1);
    //                Logger.e("下载完成，释放任务之后的任务数量：" + getTaskCount());
    //                moreTaskDownloadStart();
    //                break;
    //            case HttpDownStatus.ERROR:
    //                setTaskCount(getTaskCount() + 1);
    //                moreTaskDownloadStart();
    //                break;
    //            case 7:
    //                break;
    //            default:
    //                break;
    //        }
    //    }

    /**
     * 开始下载
     *
     * @param httpDownInfo     下载信息
     * @param httpDownListener 监听
     * @param isMoreThread     是否多线程下载
     */
    @JvmOverloads
    fun downStart(httpDownInfo: HttpDownInfo?, httpDownListener: HttpDownListener?, isMoreThread: Boolean = false) {
        //正在下载不处理
        if (httpDownInfo?.url == null) {
            return
        }
        //正在下载不处理
        if (mCallBackMap[httpDownInfo.url!!] != null) {
            mCallBackMap[httpDownInfo.url!!]!!.httpDownInfo = httpDownInfo
            return
        }

        if (httpDownInfo.readLength == httpDownInfo.countLength) {
            httpDownInfo.readLength = 0
        }
        //添加回调处理类
        if (httpDownListener != null) {
            httpDownInfo.listener = httpDownListener
        }
        val callBack = HttpDownCallBack<HttpDownInfo>(httpDownInfo)
        callBack.setResultListener(object : ResultListener {
            override fun downFinish(httpDownInfo: HttpDownInfo) {
                mCallBackMap.remove(httpDownInfo.url)
            }

            override fun downError(httpDownInfo: HttpDownInfo) {
                mCallBackMap.remove(httpDownInfo.url)
            }

        })
        //创建自定义下载拦截器
        val interceptor = HttpDownInterceptor(callBack)

        val builder = OkHttpClient.Builder()
        builder.connectTimeout((if (connectTimeOut == 0) CONNECT_TIMEOUT else connectTimeOut).toLong(), TimeUnit.SECONDS)
                .readTimeout((if (readTimeOut == 0) READ_TIMEOUT else readTimeOut).toLong(), TimeUnit.SECONDS)
                .writeTimeout((if (writeTimeOut == 0) WRITE_TIMEOUT else writeTimeOut).toLong(), TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)

        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
                .build()

        httpDownListener?.downStart()

        downLoad(retrofit, httpDownInfo.readLength, httpDownInfo).subscribe(callBack)


        callBack.downStart()

        httpDownInfo.state = HttpDownStatus.START
        //记录回调mCallBackMap
        mHttpDownInfoSet.add(httpDownInfo)
        mCallBackMap.put(httpDownInfo.url!!, callBack)
    }

    /**
     * 单线程下载
     *
     * @param retrofit     Retrofit
     * @param start        开始位置
     * @param httpDownInfo 下载信息
     * @return Observable
     */
    private fun downLoad(retrofit: Retrofit, start: Long, httpDownInfo: HttpDownInfo): Observable<HttpDownInfo> {
        return retrofit.create(HttpDownloadApiService::class.java).downFile("bytes=$start-", httpDownInfo.url!!)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map { responseBody ->
                    writeToCaches(responseBody, httpDownInfo)
                    httpDownInfo
                }
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun writeToCaches(body: ResponseBody, httpDownInfo: HttpDownInfo) {

        var randomAccessFile: RandomAccessFile? = null
        var fileChannel: FileChannel? = null
        var inputStream: InputStream? = null

        val file = File(getPathname(httpDownInfo)!!)
        if (!file.parentFile.exists()) {
            file.parentFile.mkdirs()
        }
        try {
            val allLength = if (0L == httpDownInfo.countLength)
                body.contentLength()
            else
                httpDownInfo.readLength + body
                        .contentLength()

            randomAccessFile = RandomAccessFile(file, "rwd")
            fileChannel = randomAccessFile.channel
            inputStream = body.byteStream()
            val byteBuffer = fileChannel!!.map(FileChannel.MapMode.READ_WRITE, httpDownInfo.readLength, allLength - httpDownInfo.readLength)

            val buffer = ByteArray(1024 * 4)
            while (inputStream!!.read(buffer) != -1) {
                byteBuffer.put(buffer, 0, inputStream.read(buffer))
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close()
                }
                if (fileChannel != null) {
                    fileChannel.close()
                }
                if (randomAccessFile != null) {
                    randomAccessFile.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

     fun getPathname(httpDownInfo: HttpDownInfo): String? {
        return if (TextUtils.isEmpty(httpDownInfo.savePath)) Environment.getExternalStorageDirectory().absolutePath + "/download/" + getFileName(httpDownInfo.url) else httpDownInfo.savePath
    }

     fun getFileName(url: String?): String {
        return url!!.substring(url.lastIndexOf("/") + 1)
    }

    /**
     * 停止下载
     *
     * @param httpDownInfo 下载信息
     */
     fun downStop(httpDownInfo: HttpDownInfo?): HttpDownInfo? {
        if (httpDownInfo != null) {
            //            if (httpDownInfo.getState() == HttpDownStatus.PAUSE) {
            //                if (mHttpDownListener != null) {
            //                    mHttpDownListener.downStop();
            //                }
            //            }
            if (mCallBackMap.containsKey(httpDownInfo.url)) {
                val callBack = mCallBackMap.get(httpDownInfo.url)!!
                callBack.disposable?.dispose()
                callBack.downStop()
                mCallBackMap.remove(httpDownInfo.url)

                if (mIsDeleteFile) {
                    val file = File(getPathname(httpDownInfo)!!)
                    if (file.exists()) {
                        file.delete()
                    }
                }
            }
        }
        return httpDownInfo
    }

    /**
     * 下载暂停
     *
     * @param httpDownInfo
     */
    fun downPause(httpDownInfo: HttpDownInfo?): HttpDownInfo? {
        if (httpDownInfo != null) {
            if (mCallBackMap.containsKey(httpDownInfo.url)) {
                val callBack = mCallBackMap.get(httpDownInfo.url)!!
                callBack.disposable?.dispose()
                callBack.downPause()
                httpDownInfo.state = HttpDownStatus.PAUSE
                //                mTaskList.remove(httpDownInfo);
                mCallBackMap.remove(httpDownInfo.url)
            }
        }
        return httpDownInfo
    }

    /**
     * 停止所有的下载
     */
    fun downStopAll() {
        for (httpDownInfo in mHttpDownInfoSet) {
            downStop(httpDownInfo)
        }
        mCallBackMap.clear()
        //        mTaskList.clear();
        mHttpDownInfoSet.clear()
    }

    /**
     * 暂停所有
     */
    fun downPauseAll() {
        for (httpDownInfo in mHttpDownInfoSet) {
            downPause(httpDownInfo)
        }
        mCallBackMap.clear()
        mHttpDownInfoSet.clear()
    }

    fun remove(httpDownInfo: HttpDownInfo) {
        mCallBackMap.remove(httpDownInfo.url)
        mHttpDownInfoSet.remove(httpDownInfo)
    }

    fun setConnectTimeOut(connectTimeOut: Int) {
        this.connectTimeOut = connectTimeOut
    }

    fun setReadTimeOut(readTimeOut: Int) {
        this.readTimeOut = readTimeOut
    }

    fun setWriteTimeOut(writeTimeOut: Int) {
        this.writeTimeOut = writeTimeOut
    }

    companion object {

        private val CONNECT_TIMEOUT = 60
        private val READ_TIMEOUT = 60
        private val WRITE_TIMEOUT = 60
        private var mHttpDownMethods: HttpDownMethods? = null
        /**
         * 设置下载任务个数（最多同时下载多少个）
         *
         * @param mTaskCount
         */
        var taskCount = 0
        private val TASK_COUNT = 2

        val instance: HttpDownMethods
            get() {
                if (mHttpDownMethods == null) {
                    synchronized(HttpDownMethods::class.java) {
                        mHttpDownMethods = HttpDownMethods()
                    }
                }
                return mHttpDownMethods!!
            }

    }

}
