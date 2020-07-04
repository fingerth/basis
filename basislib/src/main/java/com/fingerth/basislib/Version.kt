package com.fingerth.basislib

import android.app.Activity
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

object Version {
    private var packageName: String = ""
    private var versionName: String = ""
    private var versionCode = -1

    fun getPackageName(context: Context): String {
        return if (packageName.isBlank()) {
            packageName = getPackageInfo(context)?.packageName ?: ""
            packageName
        } else packageName
    }


    fun getVersionName(context: Context): String {
        return if (versionName.isBlank()) {
            versionName = getPackageInfo(context)?.versionName ?: ""
            versionName
        } else versionName
    }

    fun getVersionCode(context: Context): Int {
        return if (versionCode < 0) {
            versionCode = getPackageInfo(context)?.versionCode ?: -1
            versionCode
        } else versionCode
    }

    private fun getPackageInfo(context: Context): PackageInfo? {
        var pi: PackageInfo? = null
        try {
            pi = context.packageManager.getPackageInfo(context.packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return pi
    }
}