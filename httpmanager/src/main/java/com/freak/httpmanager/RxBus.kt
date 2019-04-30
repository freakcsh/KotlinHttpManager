package com.freak.kotlinhttpmanager.kotlinhttpmanager

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

class RxBus {
    private val bus: Subject<Any>

    /**
     * PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
     */
    init {
        bus = PublishSubject.create<Any>().toSerialized()
    }

    /**
     * 发送一个新的事件
     *
     * @param o
     */
    fun post(o: Any) {
        bus.onNext(o)
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     * @param eventType
     * @param <T>
     * @return
     */
    fun <T> tObservable(eventType: Class<T>): Observable<T> {
        return bus.ofType(eventType)
    }

    /**
     * 单例RxBus
     * java中的静态声明在kotlin中使用companion object声明
     * @return
     */
    companion object {
        @Volatile
        private var defaultInstance: RxBus? = null
        val default: RxBus?
            get() {
                if (defaultInstance == null) {
                    synchronized(RxBus::class.java) {
                        if (defaultInstance == null) {
                            defaultInstance = RxBus()
                        }
                    }
                }
                return defaultInstance
            }
    }


}