package com.chess.game
import org.junit.Test
import org.junit.Assert.*

class CanRookMoveUnitTest {
    @Test
    fun canRookMove_singlePiece(){
        ChessGame.clear()
        ChessGame.addPiece(ChessPiece(3,3,ChessPlayer.WHITE,ChessMan.ROOK,-1))
        println(ChessGame)

        assertTrue(ChessGame.canRookMove(Square(3,3),Square(3,0)))
        assertFalse(ChessGame.canRookMove(Square(3,3),Square(4,4)))
        assertTrue(ChessGame.canRookMove(Square(3,3),Square(7,3)))
    }

    @Test
    fun canRookMove_blocked(){
        ChessGame.clear()
        ChessGame.addPiece(ChessPiece(3,3,ChessPlayer.WHITE,ChessMan.ROOK,-1))
        ChessGame.addPiece(ChessPiece(5,3,ChessPlayer.WHITE,ChessMan.PAWN,-1))
        println(ChessGame)
        assertFalse(ChessGame.canRookMove(Square(3,3),Square(7,3)))
    }
}