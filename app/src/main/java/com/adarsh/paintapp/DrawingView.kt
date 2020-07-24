package com.adarsh.paintapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DrawingView(ctx: Context, attr: AttributeSet): View(ctx, attr) {

    companion object{
        public var store = Data()
        val figure = mutableListOf<MutableList<MutableList<Float>>>()
    }
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

    public fun startNew(){
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR)
        invalidate()
    }

    public fun setErase(isErase:Boolean){
        erasing = isErase
        if(erasing){
            drawPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        }else{
            drawPaint.xfermode = null
        }
    }

    public fun setBrushSize(newSize: Float){
        val pixelAmount = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, newSize, resources.displayMetrics)
        currentBrushSize = pixelAmount
         drawPaint.strokeWidth = currentBrushSize
    }

    public fun setLastBrush(lastSize: Float){
        lastBrushSize = lastSize
    }

    public fun getBrushSize():Float{
        return lastBrushSize
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.i("Situation", "onSizeChanged")
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        drawCanvas = Canvas(canvasBitmap)
    }

    override fun onDraw(canvas: Canvas?) {
        Log.i("Situation", "OnDraw")
        canvas?.drawBitmap(canvasBitmap, 0F, 0F, canvasPaint)
        canvas?.drawPath(drawPath, drawPaint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchX = event!!.x
        val touchY = event.y
        Log.i("Situation", "onTouchEvent")
        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                val lineF = mutableListOf<MutableList<Float>>()
                val pointF = mutableListOf<Float>()
                drawPath.moveTo(touchX, touchY)
                pointF.add(touchX)
                pointF.add(touchY)
                lineF.add(pointF)
                figure.add(lineF)
            }
            MotionEvent.ACTION_MOVE -> {
                val lineF = mutableListOf<MutableList<Float>>()
                val pointF = mutableListOf<Float>()
                drawPath.lineTo(touchX, touchY)
                pointF.add(touchX)
                pointF.add(touchY)
                lineF.add(pointF)
                figure.add(lineF)
            }
            MotionEvent.ACTION_UP -> {
                store.details.add(figure)
                drawCanvas.drawPath(drawPath, drawPaint)
                drawPath.reset()
            }
            else -> {
                return false
            }
        }
        invalidate()
        return true
    }

    public fun setColor(newColor: String){
        invalidate()
        paintColor = Color.parseColor(newColor).toLong()
        drawPaint.color = paintColor.toInt()
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

   /* public fun DisplayMutable(){
       startNew()
        setUpDrawing()
        for(i in 0..store.details[0].size-1){
            drawPath.moveTo(store.details[0][i][0][0], store.details[0][i][0][1])
            for(j in 0..store.details[0][i].size-1){
                drawPath.lineTo(store.details[0][i][j][0], store.details[0][i][j][1])
            }
            drawCanvas.drawPath(drawPath, drawPaint)
            drawCanvas?.drawBitmap(canvasBitmap, 0F, 0F, canvasPaint)
            drawPath.reset()
            drawCanvas?.drawPath(drawPath, drawPaint)
        }
    }*/
}