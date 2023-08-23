package com.chess.game

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val buttStart=findViewById<Button>(R.id.buttonStart)
        val buttHelp=findViewById<Button>(R.id.buttonHelp)
        val buttTitles=findViewById<Button>(R.id.buttonTitles)

        buttStart.setOnClickListener {
           val intent = Intent (this,MainActivity::class.java)
           startActivity(intent)
        }

        buttHelp.setOnClickListener {
            val intent = Intent (this,MainActivity3::class.java)
            startActivity(intent)
        }

        buttTitles.setOnClickListener {
            val intent = Intent (this,MainActivity4::class.java)
            startActivity(intent)
        }

    }
}