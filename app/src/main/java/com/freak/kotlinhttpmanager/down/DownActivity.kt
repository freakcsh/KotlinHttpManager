package com.freak.kotlinhttpmanager.down

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.freak.kotlinhttpmanager.R
import com.freak.kotlinhttpmanager.kotlinhttpmanager.download.HttpDownInfo
import com.freak.kotlinhttpmanager.kotlinhttpmanager.download.HttpDownListener
import com.freak.kotlinhttpmanager.kotlinhttpmanager.download.HttpDownMethods
import com.freak.kotlinhttpmanager.kotlinhttpmanager.log.LogUtil

class DownActivity : AppCompatActivity() {
    private lateinit var tv_file_name1: TextView
    internal lateinit var tv_progress1: TextView
    private lateinit var tv_file_name2: TextView
    internal lateinit var tv_progress2: TextView
    internal lateinit var btn_download1: Button
    internal lateinit var btn_download2: Button
    private lateinit var btn_download_all: Button
    internal lateinit var pb_progress1: ProgressBar
    internal lateinit var pb_progress2: ProgressBar
    private lateinit var mHttpDownMethods: HttpDownMethods
    private var wechatUrl = "http://dldir1.qq.com/weixin/android/weixin703android1400.apk"
    private var qqUrl = "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk"
    private var mDownInfo: HttpDownInfo? = null
    private var mDownInfo2: HttpDownInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_down)
        initViews()
        initDownloads()
    }

    private fun initDownloads() {
        mHttpDownMethods = HttpDownMethods.instance
        mHttpDownMethods.setDeleteFile(true)
        mDownInfo = HttpDownInfo()
        mDownInfo!!.url = qqUrl
        mDownInfo!!.savePath = Environment.getExternalStorageDirectory().absolutePath + "/text/qq.apk"
        mDownInfo2 = HttpDownInfo()
        mDownInfo2!!.url = wechatUrl
    }

    /**
     * 初始化View控件
     */
    private fun initViews() {
        tv_file_name1 = findViewById(R.id.tv_file_name1)
        tv_progress1 = findViewById(R.id.tv_progress1)
        pb_progress1 = findViewById(R.id.pb_progress1)
        btn_download1 = findViewById(R.id.btn_download1)
        tv_file_name1.text = "微信"

        tv_file_name2 = findViewById(R.id.tv_file_name2)
        tv_progress2 = findViewById(R.id.tv_progress2)
        pb_progress2 = findViewById(R.id.pb_progress2)
        btn_download2 = findViewById(R.id.btn_download2)
        tv_file_name2.text = "qq"

        btn_download_all = findViewById(R.id.btn_download_all)

    }

    /**
     * 下载或暂停下载
     *
     * @param view
     */
    fun downloadOrPause(view: View) {
        when (view.id) {
            R.id.btn_download1 -> {
                if (mDownInfo == null || "" == mDownInfo!!.url) {
                    return
                }
                mHttpDownMethods.downStart(mDownInfo, object : HttpDownListener {
                    override fun downStart() {
                        btn_download1.text = "暂停"
                    }

                    override fun downPause(httpDownInfo: HttpDownInfo, progress: Long) {
                        LogUtil.e("暂停了")
                        mDownInfo!!.readLength = progress
                        btn_download1.text = "下载"
                    }

                    override fun downStop(httpDownInfo: HttpDownInfo) {
                        LogUtil.e("停止了")
                        pb_progress1.progress = 0
                        tv_progress1.text = 0.toString() + "%"
                        mDownInfo!!.readLength = 0
                        mDownInfo!!.countLength = 0
                        btn_download1.text = "下载"
                    }

                    override fun downFinish(httpDownInfo: HttpDownInfo) {
                        LogUtil.e("下载完成")
                        mDownInfo = httpDownInfo
                        btn_download1.text = "下载完成"
                    }

                    override fun downError(httpDownInfo: HttpDownInfo, msg: String) {
                        LogUtil.e("出错了")
                        mDownInfo = httpDownInfo
                        btn_download1.text = "下载出错了"
                    }

                    override fun downProgress(readLength: Long, countLength: Long) {
                        var pro = 0
                        try {
                            pro = (readLength * 100 / countLength).toInt()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                        pb_progress1.progress = pro
                        tv_progress1.text = pro.toString() + "%"
                    }
                })
            }
            R.id.btn_download2 -> mHttpDownMethods.downStart(mDownInfo2, object : HttpDownListener {
                override fun downStart() {
                    btn_download2.text = "暂停"
                }

                override fun downPause(httpDownInfo: HttpDownInfo, progress: Long) {
                    LogUtil.e("暂停了")
                    mDownInfo2!!.readLength = progress
                    btn_download2.text = "下载"
                }

                override fun downStop(httpDownInfo: HttpDownInfo) {
                    LogUtil.e("停止了")
                    pb_progress2.progress = 0
                    tv_progress2.text = 0.toString() + "%"
                    mDownInfo2!!.readLength = 0
                    mDownInfo2!!.countLength = 0
                    btn_download2.text = "下载"
                }

                override fun downFinish(httpDownInfo: HttpDownInfo) {
                    LogUtil.e("下载完成")
                    mDownInfo2 = httpDownInfo
                    btn_download2.text = "下载完成"
                }

                override fun downError(httpDownInfo: HttpDownInfo, msg: String) {
                    LogUtil.e("出错了" + msg)
                    mDownInfo2 = httpDownInfo
                    btn_download2.text = "下载出错了"
                }

                override fun downProgress(readLength: Long, countLength: Long) {
                    var pro = 0
                    try {
                        pro = (readLength * 100 / countLength).toInt()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    pb_progress2.progress = pro
                    tv_progress2.text = pro.toString() + "%"
                }
            })
            else -> {
            }
        }
    }

    fun downloadOrPauseAll(view: View) {
        //        if (!mDownloadManager.isDownloading(wechatUrl, qqUrl)) {
        //            btn_download1.setText("暂停");
        //            btn_download2.setText("暂停");
        //            btn_download_all.setText("全部暂停");
        //            mDownloadManager.download(wechatUrl, qqUrl);//最好传入个String[]数组进去
        //        } else {
        //            mDownloadManager.pause(wechatUrl, qqUrl);
        //            btn_download1.setText("下载");
        //            btn_download2.setText("下载");
        //            btn_download_all.setText("全部下载");
        //        }
    }

    /**
     * 取消下载
     *
     * @param view
     */
    fun cancel(view: View) {

        when (view.id) {
            R.id.btn_cancel1 -> {
            }
            R.id.btn_cancel2 -> {
            }
            else -> {
            }
        }//                mHttpDownMethods.downStop(mDownInfo);
        //                mHttpDownMethods.downStop(mDownInfo2);
    }

    fun cancelAll(view: View) {
        //        mHttpDownMethods.downStopAll();
        //        btn_download1.setText("下载");
        //        btn_download2.setText("下载");
        //        btn_download_all.setText("全部下载");
    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return
        }
        val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE

        if (!checkPermission(permission)) {//针对android6.0动态检测申请权限
            if (shouldShowRationale(permission)) {
                showMessage("需要权限跑demo哦...")
            }
            ActivityCompat.requestPermissions(this, arrayOf(permission), PERMISSION_REQUEST_CODE)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    /**
     * 显示提示消息
     *
     * @param msg
     */
    private fun showMessage(msg: String) {
        Toast.makeText(this@DownActivity, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * 检测用户权限
     *
     * @param permission
     * @return
     */
    protected fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * 是否需要显示请求权限的理由
     *
     * @param permission
     * @return
     */
    protected fun shouldShowRationale(permission: String): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
    }

    fun pause(view: View) {
        mHttpDownMethods.downPause(mDownInfo)
    }

    fun pause2(view: View) {
        mHttpDownMethods.downPause(mDownInfo2)
    }

    fun pauseAll(view: View) {
        mHttpDownMethods.downPauseAll()

    }

    companion object {
        private val PERMISSION_REQUEST_CODE = 1

        fun startAction(context: Context) {
            val intent = Intent(context, DownActivity::class.java)
            context.startActivity(intent)
        }
    }
}
