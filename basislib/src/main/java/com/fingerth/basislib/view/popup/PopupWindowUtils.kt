package com.fingerth.basislib.view.popup

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.fingerth.basislib.alpha

/**
 * ======================================================
 * Created by admin fingerth on 2020/06/30.
 * <p/>
 * <p>
 */

object PopupWindowUtils {
    private var apply: PopupWindow? = null
    fun show(act: Activity, view: View?, layoutId: Int, blockView: () -> Unit) {
        apply = PopupWindow(act).apply {
            contentView = View.inflate(act, layoutId, null)
            blockView()
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            width = ViewGroup.LayoutParams.WRAP_CONTENT
            height = ViewGroup.LayoutParams.WRAP_CONTENT
            isOutsideTouchable = true
            isTouchable = true
            isFocusable = true
            showAsDropDown(view)
            act.window alpha 0.4f
            setOnDismissListener {
                act.window alpha 1f
            }
        }
    }

    fun dismiss() = apply?.dismiss()
}