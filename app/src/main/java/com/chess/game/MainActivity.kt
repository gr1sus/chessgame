package com.chess.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button


const val TAG = "Andrii"

class MainActivity : AppCompatActivity(),chessDelegate {

    var chessModel = ChessModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "$chessModel")


        val chessView=findViewById<chessView>(R.id.chess_view)
        val resetButton:Button= findViewById(R.id.reset_button)

        resetButton.setOnClickListener {
            chessModel.reset()
            chessView.invalidate()
        }
        chessView.chessDelegate=this


    }

    override fun piaceAt(col: Int, row: Int): ChessPiace? {
        return chessModel.piaceAt(col,row)
    }

    override fun movePiece(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int) {
         chessModel.movePiece(fromCol,fromRow,toCol,toRow)
        val chessView=findViewById<chessView>(R.id.chess_view)
        chessView.invalidate()
    }
}