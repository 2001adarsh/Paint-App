package com.adarsh.paintapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DrawingViewOther(ctx: Context, attr: AttributeSet): View(ctx, attr) {

    lateinit var drawPath : Path
    var erasing = false
    lateinit var drawPaint:Paint
    lateinit var canvasPaint:Paint
    lateinit var drawCanvas: Canvas
    var paintColor = 0xFF660000
    lateinit var canvasBitmap: Bitmap
    var currentBrushSize = 0F
    var lastBrushSize = 0F

    init {
        setUpDrawing()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.i("Situation", "onSizeChanged")
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        drawCanvas = Canvas(canvasBitmap)
    }

    override fun onDraw(canvas: Canvas?) {
        Log.i("Situation", "OnDraw + ${DrawingView.store.details.size}")
        for(k in 0 until DrawingView.store.details.size) {
            for (i in 0 until DrawingView.store.details[k].size) {
                drawPath.moveTo(
                    DrawingView.store.details[k][i][0][0],
                    DrawingView.store.details[k][i][0][1]
                )
                for (j in 0 until DrawingView.store.details[k][i].size) {
                    drawPath.lineTo(
                        DrawingView.store.details[k][i][j][0],
                        DrawingView.store.details[k][i][j][1]
                    )
                }
                canvas?.drawBitmap(canvasBitmap, 0F, 0F, canvasPaint)
                canvas?.drawPath(drawPath, drawPaint)
                drawPath.reset()
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return false
    }

    public fun setUpDrawing(){
        drawPath = Path()
        drawPaint = Paint()
        drawPaint.color = paintColor.toInt()
        drawPaint.isAntiAlias = true
        drawPaint.strokeWidth = 5F
        drawPaint.style = Paint.Style.STROKE
        drawPaint.strokeJoin = Paint.Join.ROUND

        canvasPaint  = Paint(Paint.DITHER_FLAG)
        currentBrushSize = 10F
        lastBrushSize = currentBrushSize
        drawPaint.strokeWidth = currentBrushSize

    }
}