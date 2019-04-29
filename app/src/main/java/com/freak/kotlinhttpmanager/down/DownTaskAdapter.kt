package com.freak.kotlinhttpmanager.down

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.freak.kotlinhttpmanager.R
import com.freak.kotlinhttpmanager.kotlinhttpmanager.download.HttpDownInfo
import com.freak.kotlinhttpmanager.kotlinhttpmanager.download.HttpDownListener
import com.freak.kotlinhttpmanager.kotlinhttpmanager.download.HttpDownMethods
import com.freak.kotlinhttpmanager.kotlinhttpmanager.download.HttpDownStatus
import com.freak.kotlinhttpmanager.kotlinhttpmanager.log.LogUtil

class DownTaskAdapter(layoutResId: Int, data: List<HttpDownInfo>?, private val mHttpDownMethods: HttpDownMethods) : BaseQuickAdapter<HttpDownInfo, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: HttpDownInfo) {
        mHttpDownMethods.setHttpDownListener(object : HttpDownListener {
            override fun downStart() {
                helper.setText(R.id.btn_download_task, "暂停")
                helper.setText(R.id.tv_file_name_task, "下载中：")
            }

            override fun downPause(httpDownInfo: HttpDownInfo, progress: Long) {
                LogUtil.e("暂停了" + helper.adapterPosition)
                item.readLength = progress
                item.state = HttpDownStatus.PAUSE
                helper.setText(R.id.btn_download_task, "下载")
                helper.setText(R.id.tv_file_name_task, "下载暂停：")
            }

            override fun downStop(httpDownInfo: HttpDownInfo) {
                LogUtil.e("停止了" + helper.adapterPosition)
                remove(helper.adapterPosition)
                notifyDataSetChanged()
            }

            override fun downFinish(httpDownInfo: HttpDownInfo) {
                LogUtil.e("下载完成" + helper.adapterPosition + "\n下载完成地址：" + httpDownInfo.url)
                remove(helper.adapterPosition)
                notifyDataSetChanged()
                //                item.setReadLength(httpDownInfo.getReadLength());
                //                item.setCountLength(httpDownInfo.getCountLength());
                //                item.setState(HttpDownStatus.FINISH);
                //                helper.setText(R.id.btn_download_task, "下载完成");
                //                helper.setText(R.id.tv_file_name_task, "下载完成：");
                //                int pro = (int) (httpDownInfo.getReadLength() * 100 / httpDownInfo.getCountLength());
                //                helper.setProgress(R.id.pb_progress_task, pro);
                //                helper.setText(R.id.tv_progress_task, pro + "%");
            }

            override fun downError(httpDownInfo: HttpDownInfo, msg: String) {
                LogUtil.e("出错了" + helper.adapterPosition + "\n下载完成地址：" + httpDownInfo.url)
                item.readLength = httpDownInfo.readLength
                item.countLength = httpDownInfo.countLength
                item.state = HttpDownStatus.ERROR
                helper.setText(R.id.btn_download_task, "下载出错了")
                helper.setText(R.id.tv_file_name_task, "下载出错了：")
                val pro = (httpDownInfo.readLength * 100 / httpDownInfo.countLength).toInt()
                helper.setProgress(R.id.pb_progress_task, pro)
                helper.setText(R.id.tv_progress_task, pro.toString() + "%")
            }

            override fun downProgress(readLength: Long, countLength: Long) {
                val pro = (readLength * 100 / countLength).toInt()
                helper.setProgress(R.id.pb_progress_task, pro)
                helper.setText(R.id.tv_progress_task, pro.toString() + "%")
            }
        })
        //        mHttpDownMethods.addDownTask(item);
    }
}
