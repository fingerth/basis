package com.fingerth.basislib.net

import android.app.Activity
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

object Http3 {
    private const val TAG = false
    private const val GET_METHOD = 1
    private const val POST_METHOD = 2
    private val client: OkHttpClient by lazy { OkHttpClient() }

    private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }

    class Builder {
        private var url: String? = null
        private var method = GET_METHOD
        private var map: Map<String, String>? = null
        private var body: RequestBody? = null
        private var delayed: Long = 0


        fun get(): Builder {
            method = GET_METHOD
            return this
        }

        fun post(): Builder {
            method = POST_METHOD
            return this
        }

        fun url(url: String): Builder {
            this.url = url
            return this
        }

        fun params(map: Map<String, String>? = null): Builder {
            this.map = map
            return this
        }

        fun delay(delayed: Long): Builder {
            this.delayed = delayed
            return this
        }

        fun paramsBody(body: Any): Builder {
            this.body = RequestBody.create(MediaType.parse("text/plain;charset=utf-8"), Gson().toJson(body))
            return this
        }

        /**
         * 如果Activity已经返回，不响应了。
         */
        inline fun <reified T> exe(act: Activity?, crossinline callbackError: (IOException?) -> Unit = {}, crossinline callback: (T) -> Unit = {}) {
            exe({ _, exception -> callbackError(exception) }) { response ->
                //Log.v("3Http3", "response =$response ")
                if (act != null && !act.isFinishing) {
                    if (T::class.java == String::class.java) callback(response as T) else {
                        try {
                            callback(Gson().fromJson(response, T::class.java))
                        } catch (e: Exception) {
                            callbackError(null)
                        }
                    }
                }
            }
        }

        inline fun <reified T> exe(crossinline callbackError: (IOException?) -> Unit = {}, crossinline callback: (T) -> Unit = {}) {
            exe({ _, exception -> callbackError(exception) }) { response ->
                //Log.v("3Http3", "response =$response ")
                if (T::class.java == String::class.java) callback(response as T) else {
                    try {
                        callback(Gson().fromJson(response, T::class.java))
                    } catch (e: Exception) {
                        callbackError(null)
                    }
                }
            }
        }

        //注意，在非UI线程
        fun exe(callback: Callback? = null) = execute(callback)

        fun exe(onFailure: (Call?, IOException?) -> Unit = { _, _ -> }, onResponse: (String) -> Unit) = execute(onFailure = onFailure, onResponse = onResponse)

        private fun execute(callback: Callback? = null, onFailure: (Call?, IOException?) -> Unit = { _, _ -> }, onResponse: (String) -> Unit = { _ -> }) {
            if (delayed == 0L) executeDelayed(callback, onFailure, onResponse) else handler.postDelayed({ executeDelayed(callback, onFailure, onResponse) }, delayed)
        }

        private fun executeDelayed(callback: Callback? = null, onFailure: (Call?, IOException?) -> Unit = { _, _ -> }, onResponse: (String) -> Unit = { _ -> }) {
            val cb = callback ?: object : Callback {
                override fun onFailure(call: Call?, e: IOException?) {
                    handler.post { onFailure(call, e) }
                }

                override fun onResponse(call: Call?, response: Response?) {
                    val str = response?.body()?.string() ?: ""
                    if (TAG) Log.v("3Http3", "response = $str")
                    handler.post { onResponse(str) }
                }
            }
            if (TAG) Log.v("3Http3", "url = $url")
            if (TAG) if (body != null) Log.v("3Http3", "body = ${Gson().toJson(body)}") else Log.v("3Http3", "map = ${Gson().toJson(map)}")
            when (method) {
                GET_METHOD -> client.newCall(Request.Builder().get().url(appendParams(url!!, map)).build()).enqueue(cb)
                POST_METHOD -> if (body != null) client.newCall(Request.Builder().post(RequestBody.create(MediaType.parse("text/plain;charset=utf-8"), Gson().toJson(body))).url(url).build()).enqueue(cb) else client.newCall(Request.Builder().post(addParams(map)).url(url).build()).enqueue(cb)
            }
        }
    }

    private fun addParams(params: Map<String, String>?): FormBody {
        val builder = FormBody.Builder()
        if (!params.isNullOrEmpty()) {
            for (key in params.keys) {
                builder.add(key, params[key] ?: "")
            }
        }
        return builder.build()
    }

    private fun appendParams(url: String, params: Map<String, String>?): String =
        if (params.isNullOrEmpty()) url else {
            val builder = Uri.parse(url).buildUpon()
            val iterator = params.keys.iterator()
            while (iterator.hasNext()) {
                val key = iterator.next()
                builder.appendQueryParameter(key, params[key])
            }
            builder.build().toString()
        }
}