package com.adarsh.paintapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var myCanvas = (MyCanvas(this, null))
        myCanvas = findViewById<MyCanvas>(R.id.drawing)
    }
}