package com.chess.game

interface chessDelegate {
    fun piaceAt(col:Int, row:Int):ChessPiace?
    fun movePiece(fromCol:Int,fromRow:Int,toCol:Int,toRow:Int)
}