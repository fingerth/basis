package com.fingerth.basislib

import android.content.Context
import android.content.SharedPreferences


object Shared {
    private const val data_shared_str = "sharedpreferences_name"
    var shared: SharedPreferences? = null
    fun put(con: Context, key: String, any: Any) {
        getShared(con).put {
            when (any) {
                is String -> putString(key, any)
                is Int -> putInt(key, any)
                is Long -> putLong(key, any)
                is Float -> putFloat(key, any)
                is Boolean -> putBoolean(key, any)
            }
        }
    }

    fun getString(con: Context, key: String, def: String = ""): String {
        return getShared(con).getString(key, def) ?: def
    }

    fun getInt(con: Context, key: String, def: Int = 0): Int {
        return getShared(con).getInt(key, def)
    }

    fun getLong(con: Context, key: String, def: Long = 0): Long {
        return getShared(con).getLong(key, def)
    }

    fun getFloat(con: Context, key: String, def: Float = 0f): Float {
        return getShared(con).getFloat(key, def)
    }

    fun getBoolean(con: Context, key: String, def: Boolean = false): Boolean {
        return getShared(con).getBoolean(key, def)
    }


    private fun getShared(con: Context): SharedPreferences {
        if (shared == null) {
            shared = con.getSharedPreferences(data_shared_str, Context.MODE_PRIVATE)
        }
        return shared!!
    }

}