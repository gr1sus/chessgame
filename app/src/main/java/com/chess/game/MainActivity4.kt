package com.chess.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainActivity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)
        val buttClose= findViewById<ImageButton>(R.id.imageButton)

        buttClose.setOnClickListener {
            finish()
        }

    }
}