package com.fingerth.basislib.net

import android.net.Uri
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.*


object OkHttp3 {
    private val client: OkHttpClient by lazy {
        OkHttpClient()
    }
    //fun synchronous  asynchronous

    fun get(url: String, map: Map<String, String>? = null, callback: Callback) =
        client.newCall(Request.Builder().get().url(appendParams(url, map)).build()).enqueue(callback)

    fun post(url: String, map: Map<String, String>? = null, callback: Callback) =
        client.newCall(Request.Builder().post(addParams(map)).url(url).build()).enqueue(callback)

    fun postByBody(url: String, body: Any, callback: Callback) = client.newCall(
        Request.Builder().post(RequestBody.create(MediaType.parse("text/plain;charset=utf-8"), Gson().toJson(body))).url(url).build()).enqueue(callback)


    inline fun <reified T> rxGet(url: String, map: Map<String, String>, crossinline errorBlock: () -> Unit = {}, crossinline successBlock: (T) -> Unit): Disposable = rxMethod(0, url, map, null, errorBlock, successBlock)


    inline fun <reified T> rxPost(url: String, map: Map<String, String>, crossinline errorBlock: () -> Unit = {}, crossinline successBlock: (T) -> Unit): Disposable = rxMethod(1, url, map, null, errorBlock, successBlock)

    inline fun <reified T> rxPost(url: String, body: Any?, crossinline errorBlock: () -> Unit = {}, crossinline successBlock: (T) -> Unit): Disposable = rxMethod(2, url, null, body, errorBlock, successBlock)

    inline fun <reified T> rxMethod(method: Int, url: String, map: Map<String, String>?, body: Any?, crossinline errorBlock: () -> Unit = {},
                                    crossinline successBlock: (T) -> Unit): Disposable {
        val o: Observable<String> = if (method == 0) rg(url, map) else if (method == 1) rp(url, map) else rpb(url, body)
        return o
            .map {
                if (T::class.java == String::class.java) return@map it as T else {
                    return@map try {
                        Gson().fromJson(it, T::class.java)
                    } catch (e: Exception) {
                        null
                    }
                }
            }
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe { t: T? ->
                if (t == null) errorBlock() else successBlock(t)
            }
    }

    fun rg(url: String, map: Map<String, String>?): Observable<String> = sync(url = url, map = map) { u, m, _ -> syncGet(u, m) }
    fun rp(url: String, map: Map<String, String>?): Observable<String> = sync(url = url, map = map) { u, m, _ -> syncPost(u, m) }
    fun rpb(url: String, body: Any?): Observable<String> = sync(url = url, body = body) { u, _, b -> syncPostByBody(u, b) }


    private fun sync(url: String, map: Map<String, String>? = null, body: Any? = null, black: (String, Map<String, String>?, Any?) -> Response): Observable<String> = Observable.defer {
        try {
            Observable.just(black(url, map, body).body()!!.string())
        } catch (e: Exception) {
            Observable.error<String>(e)
        }
    }

    private fun syncGet(url: String, map: Map<String, String>? = null): Response =
        client.newCall(Request.Builder().get().url(appendParams(url, map)).build()).execute()

    private fun syncPost(url: String, map: Map<String, String>? = null): Response =
        client.newCall(Request.Builder().post(addParams(map)).url(url).build()).execute()


    private fun syncPostByBody(url: String, body: Any?): Response =
        client.newCall(Request.Builder().post(RequestBody.create(MediaType.parse("text/plain;charset=utf-8"), Gson().toJson(body))).url(url).build()).execute()


    private fun addParams(params: Map<String, String>?): FormBody {
        val builder = FormBody.Builder()
        if (!params.isNullOrEmpty()) {
            for (key in params.keys) {
                builder.add(key, params[key] ?: "")
            }
        }
        return builder.build()
    }

    private fun appendParams(url: String, params: Map<String, String>?): String {
        if (params == null || params.isEmpty()) {
            return url
        }
        val builder = Uri.parse(url).buildUpon()
        val keys = params.keys
        val iterator = keys.iterator()
        while (iterator.hasNext()) {
            val key = iterator.next()
            builder.appendQueryParameter(key, params[key])
        }
        return builder.build().toString()
    }
}

