package com.freak.kotlinhttpmanager.app

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import com.freak.kotlinhttpmanager.interceptor.CommonParametersInterceptor
import com.freak.kotlinhttpmanager.interceptor.CommonParametersInterceptorHead
import com.freak.kotlinhttpmanager.interceptor.CookieJarImpl
import com.freak.kotlinhttpmanager.kotlinhttpmanager.HttpMethods
import com.freak.kotlinhttpmanager.kotlinhttpmanager.log.LogLevel
import java.util.HashSet

@SuppressLint("Registered")
class App : Application() {
    private var allActivities: MutableSet<Activity>? = null
    //    public static BaseActivity baseActivity;


    fun getAllActivities(): Set<Activity>? {
        return allActivities
    }

    fun setAllActivities(allActivities: MutableSet<Activity>) {
        this.allActivities = allActivities
    }

    override fun onCreate() {
        super.onCreate()
        App.instance = this
        HttpMethods
                .instanceBuilder
                .setBaseUrl(Constants.BASE_URL)//设置域名
                .setLogLevel(LogLevel.ERROR)//设置日志打印级别，使用默认的日志打印才需要设置这个
                .setLogName("123456")//设置默认日志打印名字
                .setIsOpenLog(true)//设置是否开启框架默认的日志打印
                .setCookieJar(CookieJarImpl())//设置自定义的cookiejar
                //                .setLogger(new HttpLogger())//设置自定义logger，此设置是打印网络请求的数据（如果设置了自定义的，则框架默认的则不需要设置）
                //                .setLevel(LoggerLevel.BODY)//设置日志打印级别（自定义logger可设置，框架默认的是BODY级别，如果上架需要关闭日志打印，则设置setIsOpenLog(false)即可）
                .setReadTimeOut(60)
                .setConnectTimeOut(60)
                .setWriteTimeOut(60)
                //                .setInterceptor(new CommonParametersInterceptor())//设置拦截器
                //                .setNetworkInterceptor(new CommonParametersInterceptor())//设置拦截器
                //                .setFactory(CustomConverterFactory.create())//设置自定义解析器
                .setInterceptors(CommonParametersInterceptor(), CommonParametersInterceptorHead())//设置多个拦截器

    }
    fun addActivity(act: Activity) {
        if (allActivities == null) {
            allActivities = HashSet()
        }
        allActivities!!.add(act)
    }

    fun removeActivity(act: Activity) {
        if (allActivities != null) {
            allActivities!!.remove(act)
        }
    }
//    private fun initImagePicker() {
//        val imagePicker = ImagePicker.getInstance()
//        //        //设置图片加载器
//        imagePicker.setImageLoader(ImagePickerGlideLoader())
//        //显示拍照按钮
//        imagePicker.setShowCamera(true)
//        //允许裁剪（单选才有效）
//        imagePicker.setCrop(true)
//        //是否按矩形区域保存
//        imagePicker.setSaveRectangle(true)
//        //选中数量限制
//        imagePicker.setSelectLimit(9)
//        //裁剪框的形状
//        imagePicker.setStyle(CropImageView.Style.RECTANGLE)
//        //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setFocusWidth(800)
//        //裁剪框的高度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setFocusHeight(800)
//        //保存文件的宽度。单位像素
//        imagePicker.setOutPutX(1000)
//        //保存文件的高度。单位像素
//        imagePicker.setOutPutY(1000)
//    }
    companion object {
        var instance: App? = null
    }
}