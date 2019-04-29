package com.freak.kotlinhttpmanager.kotlinhttpmanager.download

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * @author Administrator
 */
interface HttpDownloadApiService {
    @Streaming
    @GET
    fun downFile(@Header("RANGE") start: String, @Url url: String): Observable<ResponseBody>
}
