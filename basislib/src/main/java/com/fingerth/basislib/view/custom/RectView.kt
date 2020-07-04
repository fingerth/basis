package com.fingerth.basislib.view.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class RectView @JvmOverloads constructor(con: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(con, attrs, defStyleAttr) {
    private val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        mPaint.color = Color.RED
        mPaint.strokeWidth = 1.5f
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), mPaint)
    }
}