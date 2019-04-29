@file:Suppress("DEPRECATED_IDENTITY_EQUALS")

package com.freak.kotlinhttpmanager.down


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.freak.kotlinhttpmanager.R
import com.freak.kotlinhttpmanager.kotlinhttpmanager.download.HttpDownInfo
import com.freak.kotlinhttpmanager.kotlinhttpmanager.download.HttpDownMethods
import com.freak.kotlinhttpmanager.kotlinhttpmanager.download.HttpDownStatus
import java.util.*

@SuppressLint("Registered")
class DownTaskListActivity : AppCompatActivity() {
    internal var wechatUrl = "http://dldir1.qq.com/weixin/android/weixin703android1400.apk"
    internal var qqUrl = "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk"
    internal var jrtt = "http://gdown.baidu.com/data/wisegame/55dc62995fe9ba82/jinritoutiao_448.apk"
    internal var url = "http://pic2.zhimg.com/80/v2-4bd879d9876f90c1db0bd98ffdee17f0_hd.jpg"
    internal var url2 = "http://pic1.win4000.com/wallpaper/2017-10-11/59dde2bca944f.jpg"
    private var recycle_view: RecyclerView? = null
    private var mDownTaskAdapter: DownTaskAdapter? = null
    private var mHttpDownInfoList: MutableList<HttpDownInfo>? = null
    private var mHttpDownInfo: HttpDownInfo? = null
    private var mHttpDownInfo2: HttpDownInfo? = null
    private var mHttpDownInfo3: HttpDownInfo? = null
    private var mHttpDownInfo4: HttpDownInfo? = null
    private var mHttpDownInfo5: HttpDownInfo? = null
    private var mHttpDownMethods: HttpDownMethods? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_down_task_list)
        mHttpDownMethods = HttpDownMethods.instance
        mHttpDownInfoList = ArrayList<HttpDownInfo>()
        recycle_view = findViewById(R.id.recycle_view)
        recycle_view!!.setLayoutManager(LinearLayoutManager(this))
        mDownTaskAdapter = DownTaskAdapter(R.layout.item_download_task, mHttpDownInfoList, mHttpDownMethods!!)
        mDownTaskAdapter!!.bindToRecyclerView(recycle_view)
        mDownTaskAdapter!!.setEmptyView(R.layout.item_no_date, recycle_view)
        mHttpDownInfo = HttpDownInfo()
        mHttpDownInfo!!.url = wechatUrl

        mHttpDownInfo2 = HttpDownInfo()
        mHttpDownInfo2!!.url = qqUrl

        mHttpDownInfo3 = HttpDownInfo()
        mHttpDownInfo3!!.url = jrtt

        mHttpDownInfo4 = HttpDownInfo()
        mHttpDownInfo4!!.url = url

        mHttpDownInfo5 = HttpDownInfo()
        mHttpDownInfo5!!.url = url2
    }

    fun download1(view: View) {
        if (mHttpDownInfo!!.state === HttpDownStatus.WAITING) {
            mHttpDownInfoList!!.add(mHttpDownInfo!!)

        }
        mDownTaskAdapter!!.notifyItemChanged(0)
    }

    fun download2(view: View) {
        if (mHttpDownInfo2!!.state === HttpDownStatus.WAITING) {
            mHttpDownInfoList!!.add(mHttpDownInfo2!!)

        }
        mDownTaskAdapter!!.notifyItemChanged(1)
    }

    fun download3(view: View) {
        if (mHttpDownInfo3!!.state === HttpDownStatus.WAITING) {
            mHttpDownInfoList!!.add(mHttpDownInfo3!!)
        }

        mDownTaskAdapter!!.notifyItemChanged(2)
    }

    fun download4(view: View) {
        if (mHttpDownInfo4!!.state === HttpDownStatus.WAITING) {
            mHttpDownInfoList!!.add(mHttpDownInfo4!!)
        }
        mDownTaskAdapter!!.notifyItemChanged(3)
    }

    fun download5(view: View) {
        if (mHttpDownInfo5!!.state === HttpDownStatus.WAITING) {
            mHttpDownInfoList!!.add(mHttpDownInfo5!!)
        }
        mDownTaskAdapter!!.notifyItemChanged(4)
    }

    fun downloadAll(view: View) {
        mHttpDownInfoList!!.add(mHttpDownInfo!!)
        mHttpDownInfoList!!.add(mHttpDownInfo2!!)
        mHttpDownInfoList!!.add(mHttpDownInfo3!!)
        mHttpDownInfoList!!.add(mHttpDownInfo4!!)
        mHttpDownInfoList!!.add(mHttpDownInfo5!!)
        mDownTaskAdapter!!.notifyDataSetChanged()
    }

    fun cancelAll(view: View) {
        mHttpDownMethods!!.downStopAll()
    }

    fun cancel5(view: View) {
        mHttpDownMethods!!.downStop(mHttpDownInfo5)
    }

    fun cancel4(view: View) {
        mHttpDownMethods!!.downStop(mHttpDownInfo4)
    }

    fun cancel3(view: View) {
        mHttpDownMethods!!.downStop(mHttpDownInfo3)
    }

    fun cancel2(view: View) {
        mHttpDownMethods!!.downStop(mHttpDownInfo2)
    }

    fun cancel1(view: View) {
        mHttpDownMethods!!.downStop(mHttpDownInfo)
    }

    fun pauseAll(view: View) {
        mHttpDownMethods!!.downPauseAll()
    }

    fun pause5(view: View) {
        mHttpDownMethods!!.downPause(mHttpDownInfo5)
    }

    fun pause4(view: View) {
        mHttpDownMethods!!.downPause(mHttpDownInfo4)
    }

    fun pause3(view: View) {
        mHttpDownMethods!!.downPause(mHttpDownInfo3)
    }

    fun pause2(view: View) {
        mHttpDownMethods!!.downPause(mHttpDownInfo2)
    }

    fun pause1(view: View) {
        mHttpDownMethods!!.downPause(mHttpDownInfo)
    }

    companion object {

        fun startAction(context: Context) {
            val intent = Intent(context, DownTaskListActivity::class.java)
            context.startActivity(intent)
        }
    }
}
