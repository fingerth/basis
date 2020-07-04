package com.fingerth.basislib.view.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class InvalidTextView @JvmOverloads constructor(con: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatTextView(con, attrs, defStyleAttr) {
    private val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        mPaint.color = Color.RED
        mPaint.strokeWidth = 1.5f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawLine(0f, height / 2f, width.toFloat(), height / 2f, mPaint)
    }
}