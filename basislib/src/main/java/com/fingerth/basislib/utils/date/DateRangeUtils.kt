package com.fingerth.basislib.utils.date

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import com.fingerth.basislib.click
import com.fingerth.basislib.showToast
import com.fingerth.basislib.R
import com.fingerth.basislib.S
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.*

/**
 * 选择时间段，时间范围。
 */
object DateRangeUtils {
    private var act: WeakReference<Activity>? = null
    private var dialog: Dialog? = null
    private lateinit var calendarS: Calendar
    private lateinit var calendarE: Calendar
    fun show(activity: Activity, block: (String, String) -> Unit) {
        if (dialog != null && dialog!!.isShowing) return
        if (act == null || act!!.get() !== activity) {
            act = WeakReference(activity)
            calendarS = Calendar.getInstance()
            calendarE = Calendar.getInstance()
        }

        val view: View = activity.layoutInflater.inflate(R.layout.dialog_view_date_range_view, null)
        val dialog = Dialog(activity, R.style.transparentFrameWindowStyle).apply {//android:style/Theme.Dialog
            setContentView(view, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
            setCanceledOnTouchOutside(true)
        }

        dialog.window?.let {
            it.setWindowAnimations(R.style.main_menu_animstyle)
            it.attributes.x = 0
            it.attributes.y = S.getSysHeight(activity)
            it.attributes.width = ViewGroup.LayoutParams.MATCH_PARENT
            it.attributes.height = ViewGroup.LayoutParams.WRAP_CONTENT
            it.decorView.setPadding(0, 0, 0, 0)
            dialog.onWindowAttributesChanged(it.attributes)
        }

        val dp1 = view.findViewById<DatePicker>(R.id.dp1)
        val dp2 = view.findViewById<DatePicker>(R.id.dp2)

        val sf = SimpleDateFormat(f2, Locale.PRC)
        var s1 = sf.format(calendarS.time)
        var es1 = sf.format(calendarE.time)
        dp1.init(calendarS.get(Calendar.YEAR), calendarS.get(Calendar.MONTH), calendarS.get(Calendar.DAY_OF_MONTH)) { _, i, i2, i3 ->
            calendarS.set(Calendar.YEAR, i)
            calendarS.set(Calendar.MONTH, i2)
            calendarS.set(Calendar.DAY_OF_MONTH, i3)
            s1 = sf.format(calendarS.time)
        }

        dp2.init(calendarE.get(Calendar.YEAR), calendarE.get(Calendar.MONTH), calendarE.get(Calendar.DAY_OF_MONTH)) { _, i, i2, i3 ->
            calendarE.set(Calendar.YEAR, i)
            calendarE.set(Calendar.MONTH, i2)
            calendarE.set(Calendar.DAY_OF_MONTH, i3)
            es1 = sf.format(calendarE.time)
        }

        view.findViewById<View>(R.id.sure) click {
            if (calendarS.timeInMillis > calendarE.timeInMillis) {
                "结束时间必须大于开始时间".showToast(activity)
            } else {
                block(s1, es1)
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    private const val f2 = "yyyy-MM-dd"
}