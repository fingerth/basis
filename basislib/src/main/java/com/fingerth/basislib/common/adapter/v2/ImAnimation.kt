package com.jiankangbao.Utils.xadapter.v2

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.LinearInterpolator

interface ImAnimation {
    fun getAnimators(view: View?): Array<Animator> {
        val scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.9f, 1f).apply {
            duration = 300
            interpolator = LinearInterpolator()
        }
        val scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.6f, 1f).apply {
            duration = 300
            interpolator = LinearInterpolator()
        }
        return arrayOf(scaleX, scaleY)
    }
    fun getAnModel(): AnModel = AnModel.NORMAL
    fun getStart(): Int = -1
}