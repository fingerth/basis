package com.fingerth.basislib

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics

object Screen {
    private var sysWidth = 0
    private var sysHeight = 0


    fun getSysWidth(activity: Activity): Int {
        if (sysWidth <= 0) {
            val dm = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(dm)
            sysWidth = dm.widthPixels
            sysHeight = dm.heightPixels
        }
        return sysWidth
    }

    fun getSysHeight(activity: Activity): Int {
        if (sysHeight <= 0) {
            val dm = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(dm)
            sysWidth = dm.widthPixels
            sysHeight = dm.heightPixels
        }
        return sysHeight
    }

    fun getStatusBarHeight(c: Context): Int {
        var result = 0
        val resourceId = c.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = c.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }
}