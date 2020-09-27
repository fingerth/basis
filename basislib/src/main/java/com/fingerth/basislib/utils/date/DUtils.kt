package com.fingerth.basislib.utils.date

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DUtils {

    private val hourMinuteFormat: DateFormat by lazy { SimpleDateFormat("HH:mm", Locale.getDefault()) }
    private val mdhmFormat: DateFormat by lazy { SimpleDateFormat("MM-dd HH:mm", Locale.getDefault()) }
    private val ymdhmFormat: DateFormat by lazy { SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()) }

    /**
     * 得到相对时间   下午 14:20    ---     昨天  13:30   ---
     */
    fun conversionTime(l: Long): String {
        val date = Date(l)
        val currentC = Calendar.getInstance()
        val c = Calendar.getInstance().apply { time = date }
        if (currentC.get(Calendar.YEAR) == c.get(Calendar.YEAR)) {
            if (currentC.get(Calendar.MONTH) == c.get(Calendar.MONTH)) {
                if (currentC.get(Calendar.DAY_OF_MONTH) == c.get(Calendar.DAY_OF_MONTH)) {
                    val h = c.get(Calendar.HOUR_OF_DAY)
                    return when {
                        h < 6 -> "凌晨 " + hourMinuteFormat.format(date)
                        h < 9 -> "早上 " + hourMinuteFormat.format(date)
                        h < 12 -> "上午 " + hourMinuteFormat.format(date)
                        h < 18 -> "下午 " + hourMinuteFormat.format(date)
                        h < 19 -> "傍晚 " + hourMinuteFormat.format(date)
                        else -> "晚上 " + hourMinuteFormat.format(date)
                    }
                } else {
                    return when (currentC.get(Calendar.DAY_OF_MONTH) - c.get(Calendar.DAY_OF_MONTH)) {
                        1 -> "昨天 " + hourMinuteFormat.format(date)
                        2 -> "前天 " + hourMinuteFormat.format(date)
                        else -> mdhmFormat.format(date)
                    }
                }
            } else return mdhmFormat.format(date)
        } else return ymdhmFormat.format(date)
    }

    /**
     * 当前时间   ---  9月22日  周二
     */
    fun formatCurrent(): String = Calendar.getInstance().run {
        "${get(Calendar.MONTH) + 1}月${get(Calendar.DAY_OF_MONTH)}日  周${getDayOfWeek(get(Calendar.DAY_OF_WEEK))}"
    }


    private fun getDayOfWeek(c: Int): String = when (c) {
        1 -> "日"
        2 -> "一"
        3 -> "二"
        4 -> "三"
        5 -> "四"
        6 -> "五"
        else -> "六"
    }

}