package com.freak.kotlinhttpmanager.down

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.freak.kotlinhttpmanager.kotlinhttpmanager.log.LogUtil
import java.io.File

/**
 * @author Administrator
 */
class DownLoadCompleteReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == intent.action) {
            //在广播中取出下载任务的id
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            LogUtil.e("编号：" + id + "的下载任务已经完成！")
            Toast.makeText(context, "编号：" + id + "的下载任务已经完成！", Toast.LENGTH_SHORT).show()
            val query = DownloadManager.Query()
            val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            query.setFilterById(id)
            val c = dm.query(query)
            if (c != null) {
                try {
                    if (c.moveToFirst()) {
                        //获取文件下载路径
                        val filename = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME))
                        val status = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS))
                        if (status == DownloadManager.STATUS_SUCCESSFUL) {
                            //启动更新
                            val uri = Uri.fromFile(File(filename))
                            if (uri != null) {
                                val install = Intent(Intent.ACTION_VIEW)
                                install.setDataAndType(uri, "application/vnd.android.package-archive")
                                install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                context.startActivity(install)
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    return
                } finally {
                    c.close()
                }
            }
        }
    }

}
