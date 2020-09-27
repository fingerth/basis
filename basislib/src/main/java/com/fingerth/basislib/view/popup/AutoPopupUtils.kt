package com.fingerth.basislib.view.popup

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.fingerth.basislib.S

/**
 * 自适应，向下或者向上弹出
 */
class AutoPopupUtils {
    private var apply: PopupWindow? = null

    fun show(act: Activity, view: View, location: IntArray, layoutId: Int, blockView: (View, Boolean) -> Unit) {
        val barHeight = S.getStatusBarHeight(act)
        val isTop: Boolean = location[1] <= S.getSysHeight(act) * 2 / 3
        apply = PopupWindow(act).apply {
            contentView = View.inflate(act, layoutId, null)
            blockView(contentView, isTop)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            width = ViewGroup.LayoutParams.MATCH_PARENT
            height = ViewGroup.LayoutParams.WRAP_CONTENT
            isOutsideTouchable = true
            isTouchable = true
            isFocusable = true
            if (isTop) showAsDropDown(view) else {
                contentView.post {
                    //Log.v("LocationX", "contentView.measuredHeight post 后 = ${contentView.measuredHeight}")
                    val h: Int
                    val y = when {
                        contentView.measuredHeight >= location[1] - barHeight -> {
                            h = location[1] - barHeight
                            0
                        }
                        else -> {
                            h = contentView.measuredHeight
                            location[1] - contentView.measuredHeight
                        }
                    }
                    apply?.update(0, y, -2, h)
                }
                contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
                val msHeight = if (contentView.measuredHeight >= location[1]) location[1] else contentView.measuredHeight
                showAtLocation(view, Gravity.NO_GRAVITY, location[0] - contentView.measuredWidth / 2, location[1] - msHeight)
                //Log.v("LocationX", "contentView.measuredHeight = ${contentView.measuredHeight}")
            }
        }
    }

    fun dismiss() = apply?.dismiss()
}