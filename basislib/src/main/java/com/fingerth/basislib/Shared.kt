package com.fingerth.basislib

import android.content.Context
import android.content.SharedPreferences


object Shared {
    const val data_shared_str = "sharedpreferences_name"
    var shared: SharedPreferences? = null
    fun put(con: Context, key: String, any: Any) {
        if (shared == null) {
            shared = con.getSharedPreferences(data_shared_str, Context.MODE_PRIVATE)
        }
        shared?.put {
            when (any) {
                is String -> putString(key, any)
                is Int -> putInt(key, any)
                is Long -> putLong(key, any)
                is Float -> putFloat(key, any)
                is Boolean -> putBoolean(key, any)
            }
        }
    }

    inline fun <reified T : Comparable<T>> get(con: Context, key: String, def: T? = null): T? {
        if (shared == null) {
            shared = con.getSharedPreferences(data_shared_str, Context.MODE_PRIVATE)
        }
        return when (T::class.java) {
            String::class.java -> shared?.getString(key, def?.toString() ?: "") as T
            Int::class.java -> shared?.getInt(key, (def ?: 0) as Int) as T
            Long::class.java -> shared?.getLong(key, (def ?: 0) as Long) as T
            Float::class.java -> shared?.getFloat(key, (def ?: 0) as Float) as T
            Boolean::class.java -> shared?.getBoolean(key, (def ?: false) as Boolean) as T
            else -> def
        }
    }
}