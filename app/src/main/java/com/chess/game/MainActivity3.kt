package com.chess.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        val buttClose = findViewById<ImageButton>(R.id.imageButton3)


        buttClose.setOnClickListener{
            finish()
        }
    }
}