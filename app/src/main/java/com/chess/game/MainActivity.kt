package com.chess.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import java.io.PrintWriter
import java.net.Socket
import java.util.Scanner
import java.util.concurrent.Executors


const val TAG = "Andrii"

class MainActivity : AppCompatActivity(), ChessDelegate {


    private var printWriter:PrintWriter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "$ChessGame")


        val chessView = findViewById<chessView>(R.id.chess_view)
        val resetButton: Button = findViewById(R.id.reset_button)
        val listenButton = findViewById<Button>(R.id.listen_button)
        val connectButton = findViewById<Button>(R.id.connect_button)


        resetButton.setOnClickListener {
            ChessGame.reset()
            chessView.invalidate()
        }
        chessView.chessDelegate = this


        listenButton.setOnClickListener {

        }


        connectButton.setOnClickListener {
//            Executors.newSingleThreadExecutor().execute {
//                val socket = Socket("192.168.0.107", 50000) // 192.168.0.107
//                val scanner = Scanner(socket.getInputStream())
//                printWriter = PrintWriter(socket.getOutputStream(),true)
//                while (scanner.hasNextLine()){
////                    Log.d(TAG,"${scanner.nextLine()}")
//                    val move= scanner.nextLine().split(",").map { it.toInt() }
//                    runOnUiThread{
//                        movePiece(move[0],move[1],move[2],move[3])
//                    }
//                }
//            }

        }
    }
    override fun pieceAt(square: Square): ChessPiece? {
        return ChessGame.pieceAt(square)
    }

    override fun movePiece(from: Square,to:Square) {

        ChessGame.movePiece(from,to)
        val chessView = findViewById<chessView>(R.id.chess_view)
        chessView.invalidate()
        /*printWriter?.let {
            val moveStr = "$fromCol,$fromRow,$toCol,$toRow"
            printWriter?.println(moveStr)
        }*/

    }
}