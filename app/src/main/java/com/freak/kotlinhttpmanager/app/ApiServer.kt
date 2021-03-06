package com.freak.kotlinhttpmanager.app

import com.freak.kotlinhttpmanager.LoginModel
import com.freak.kotlinhttpmanager.entity.BaseBean
import com.freak.kotlinhttpmanager.entity.HttpResult
import com.freak.kotlinhttpmanager.entity.LoginBean
import com.freak.kotlinhttpmanager.entity.LoginStatusEntity
import com.google.gson.JsonObject
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiServer {
    /**
     * 用户登陆
     *
     * @return
     */
    @POST("/login")
    fun login1(@Query("userName") userName: String,
               @Query("pwd") pwd: String): Observable<HttpResult<Any>>

    /**
     * 用户登陆
     *
     * @return
     */
    @POST("/login")
    fun login2(@Query("userName") userName: String,
               @Query("pwd") pwd: String): Observable<HttpResult<LoginBean>>

    @POST("/app/user/login")
    fun login3(@Query("user_mobile") user_mobile: String,
               @Query("user_password") user_password: String): Observable<BaseBean<Any>>

    /**
     * apk文件下载
     *
     * @param apkUrl
     */
    @Streaming
    @GET
    fun downloadApk(@Url apkUrl: String): Observable<ResponseBody>

    /**
     * 用户登陆
     *
     * @return
     */
    @POST("login/cellphone")
    fun login(@Query("phone") phone: String,
              @Query("password") password: String
    ): Observable<LoginModel>

    @POST("login/status")
    fun loadLoginStatus(): Observable<LoginStatusEntity>


    @Multipart
    @POST("user/uploadIdentyfyImg")
    fun uploadFile(@PartMap map: Map<String, RequestBody>, @Part part: List<MultipartBody.Part>): Observable<JsonObject>

    /**
     * 上传用户身份证图片
     *
     * @param body
     * @return
     */
    @Multipart
    @POST("uploading")
    fun uploading(@Query("tip") tip: String, @Query("tip1") tip1: String, @Part body: MultipartBody.Part): Observable<HttpResult<Any>>

    @Multipart
    @POST("uploading1")
    fun uploadingUserPhoto(@Part body: MultipartBody.Part): Observable<HttpResult<Any>>

    /**
     * 登录
     *
     * @param body 默认为全部，0未支付 1处理中 2待收货 3待取货 4已完成
     * @return
     */
    @Headers("Content-Type:application/json")
    @POST("api/staff/v1/staff/login")
    fun login11(@Body body: RequestBody): Observable<HttpResult<LoginModel>>
}