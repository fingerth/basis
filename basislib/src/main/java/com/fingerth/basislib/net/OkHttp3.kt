package com.nurse.base.net

import android.net.Uri
import com.google.gson.Gson
import okhttp3.*


object OkHttp3 {
    private val client: OkHttpClient by lazy {
        OkHttpClient()
    }

    fun get(url: String, map: HashMap<String, String>? = null, callback: Callback) =
        client.newCall(Request.Builder().get().url(appendParams(url, map)).build())
            .enqueue(callback)

    fun post(url: String, map: HashMap<String, String>? = null, callback: Callback) =
        client.newCall(Request.Builder().post(addParams(map)).url(url).build())
            .enqueue(callback)


    fun post(url: String, body: Any, callback: Callback) = client.newCall(
            Request.Builder().post(RequestBody.create(MediaType.parse("text/plain;charset=utf-8"), Gson().toJson(body))).url(url).build()).enqueue(callback)


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