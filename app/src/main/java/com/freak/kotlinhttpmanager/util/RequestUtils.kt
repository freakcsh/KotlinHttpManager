package com.freak.kotlinhttpmanager.util

import android.text.TextUtils
import com.freak.kotlinhttpmanager.app.App
import com.freak.kotlinhttpmanager.app.Constants
import com.freak.kotlinhttpmanager.kotlinhttpmanager.log.LogUtil
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*


object RequestUtils {

    fun newParams(): InnerParam {
        return InnerParam()
    }

    fun newParams(isWithToken: Boolean): InnerParam {
        return InnerParam(isWithToken)
    }

    class InnerParam @JvmOverloads constructor(isWithToken: Boolean = true) {

        internal var mMap: MutableMap<String, Any?>

        init {
            mMap = HashMap()
            //            if (isWithToken) {
            //                mMap.put("token", TextUtils.isEmpty((String) SPUtils.get(App.getInstance().getApplicationContext(), Constants.ACCESS_TOKEN, "")) ? "" : (String) SPUtils.get(App.getInstance().getApplicationContext(), Constants.ACCESS_TOKEN, ""));
            //            }
        }

        fun addParams(key: String, value: String): InnerParam {
            mMap[key] = value
            return this
        }

        fun addParams(key: String, value: Any): InnerParam {
            mMap[key] = value
            return this
        }

        fun addParams(key: String, value: Int): InnerParam {
            mMap[key] = value.toString() + ""
            return this
        }

        fun addParams(key: String, value: Long): InnerParam {
            mMap[key] = value.toString() + ""
            return this
        }

        fun addParams(key: String, value: Double): InnerParam {
            mMap[key] = value.toString() + ""
            return this
        }

        fun create(): Map<String, Any?> {
            return mMap
        }

        fun createRequestBody(): RequestBody {
            return transformToRequestBody(mMap)
        }

        fun createRequestMultipartBody(file: File, fileKey: String): RequestBody {
            return transformToRequestMultipartBody(mMap, file, fileKey)
        }

    }

    fun transformToRequestBody(requestDataMap: MutableMap<String, Any?>): RequestBody {
        requestDataMap["token"] = if (TextUtils.isEmpty(SPUtils.get(App.instance?.applicationContext!!, Constants.ACCESS_TOKEN, "") as String)) "" else SPUtils.get(App.instance?.applicationContext!!, Constants.ACCESS_TOKEN, "")
        LogUtil.e("请求参数--》$requestDataMap")
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), Gson().toJson(requestDataMap))
    }

    /**
     * 文件加参数混合上传
     * 例子： @POST("api/staff/v1/staff/update_avatar")
     * Observable<HttpResult></HttpResult><UpLoadEntity>> upLoad(@Body RequestBody body);
     *
     * @param requestDataMap
     * @param file
     * @param fileKey
     * @return
    </UpLoadEntity> */
    fun transformToRequestMultipartBody(requestDataMap: MutableMap<String, Any?>, file: File?, fileKey: String): RequestBody {
        requestDataMap["token"] = if (TextUtils.isEmpty(SPUtils.get(App.instance?.applicationContext!!, Constants.ACCESS_TOKEN, "") as String)) "" else SPUtils.get(App.instance?.applicationContext!!, Constants.ACCESS_TOKEN, "")
        LogUtil.e("请求参数--》$requestDataMap")
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        if (file != null) {
            val body = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            builder.addFormDataPart(fileKey, file.name, body)
        }
        for ((key, value) in requestDataMap) {
            builder.addFormDataPart(key, value.toString())
        }
        return builder.build()
    }

    /**
     * 单文件上传
     * 例子： @Multipart
     * @POST("api/other/v1/upload")
     * Observable<HttpResult> upLoad(@Part MultipartBody.Part body);
     *
     * @param file
     * @return
    </HttpResult> */
    fun transformToRequestMultipartBodyPart(file: File): MultipartBody.Part {
        val fileRQ = RequestBody.create(MediaType.parse("image/*"), file)
        return MultipartBody.Part.createFormData("file", file.name, fileRQ)
    }
}
