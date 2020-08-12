package com.fingerth.basislib

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics

/**
 * ======================================================
 * Created by admin fingerth on 2020/06/15.
 * <p/>
 * <p>
 */
object S {
    private var SYS_WIDTH = 0
    private var SYS_HEIGHT = 0

    fun getSysWidth(activity: Activity?): Int {
        if (SYS_WIDTH <= 0) {
            val dm = DisplayMetrics()
            activity?.windowManager?.defaultDisplay?.getMetrics(dm)
            SYS_WIDTH = dm.widthPixels
        }
        return SYS_WIDTH
    }

    fun getSysHeight(activity: Activity?): Int {
        if (SYS_HEIGHT <= 0) {
            val dm = DisplayMetrics()
            activity?.windowManager?.defaultDisplay?.getMetrics(dm)
            SYS_HEIGHT = dm.heightPixels
        }
        return SYS_HEIGHT
    }

    fun getStatusBarHeight(c: Context): Int {
        var result = 0
        val resourceId = c.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = c.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    /**
     * 获取导航栏高度
     */
    fun getNavigationBarHeight(context: Context): Int {
        val rid = context.resources.getIdentifier("config_showNavigationBar", "bool", "android")
        return if (rid != 0) {
            val resourceId = context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
            context.resources.getDimensionPixelSize(resourceId)
        } else 0
    }

}