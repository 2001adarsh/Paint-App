package com.adarsh.paintapp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_new_program.*
import java.util.*

class NewProgram : AppCompatActivity() {
    lateinit var drawingView: DrawingView
    lateinit var currentPaint: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_program)
        drawingView = findViewById<DrawingView>(R.id.drawing)
        currentPaint = paintColors.getChildAt(0) as ImageButton
        currentPaint.setImageDrawable(resources.getDrawable(R.drawable.paint2_pressed))

        draw.setOnClickListener {
            drawingView.setUpDrawing()
        }

        erase.setOnClickListener {
            drawingView.setErase(true)
            drawingView.setBrushSize(drawingView.getBrushSize())
        }

        newButton.setOnClickListener {
            val newDialog = AlertDialog.Builder(this)
                .setTitle("New Drawing")
                .setMessage("Start New Drawing")
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                    drawingView.startNew()
                        dialogInterface.dismiss()
                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.cancel()
                })
                .show()
        }

        save.setOnClickListener {
            val saveDialog = AlertDialog.Builder(this)
                .setTitle("Save Drawing")
                .setMessage("Save Drawing to Device Gallery")
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                    drawingView.isDrawingCacheEnabled = true
                    val imageSaved = MediaStore.Images.Media.insertImage(contentResolver,
                            drawingView.drawingCache,
                        UUID.randomUUID().toString()+".png",
                    "drawing")
                    if(imageSaved != null){
                        Toast.makeText(applicationContext, "Drawing saved to gallery", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(applicationContext, "Counldn't save image", Toast.LENGTH_SHORT).show()
                    }
                    drawingView.destroyDrawingCache()
                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.cancel()
                })
                .show()
        }

        button.setOnClickListener {
            //drawingView.DisplayMutable()
           Log.d("Store size: ", drawingView.store.details.size.toString())
            for(i in 0..drawingView.store.details.size-1){
                Log.d("Store.details[i].size:", drawingView.store.details[i].size.toString())
                for(j in 0..drawingView.store.details[i].size-1){
                    Log.d("Values: ", drawingView.store.details[i][0].toString())
                }
            }
        }

    }

    fun paintClicked(view: View) {
        if(view != currentPaint){
            val imgView = view as ImageButton
            val color = view.tag.toString()
            drawingView.setColor(color)
            imgView.setImageDrawable(resources.getDrawable(R.drawable.paint2_pressed))
            currentPaint.setImageDrawable(resources.getDrawable(R.drawable.paint2))
            currentPaint = view as ImageButton
        }
    }


}