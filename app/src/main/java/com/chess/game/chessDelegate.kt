package com.chess.game

interface chessDelegate {
    fun piaceAt(col:Int, row:Int):ChessPiace?
}