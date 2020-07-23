package com.adarsh.paintapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

public class MyCanvas(context:Context,attrs: AttributeSet?) :
    View(context, attrs) {

    val paint = Paint()
    val path = Path()

    init {
        paint.isAntiAlias = true
        paint.color = Color.RED
        paint.strokeJoin = Paint.Join.ROUND
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5f
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(path,paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val xPos = event!!.x
        val yPos = event.y

        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(xPos, yPos)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(xPos, yPos)
            }
            MotionEvent.ACTION_UP -> {  }
            else -> return false
        }
        invalidate()
        return true
    }
}