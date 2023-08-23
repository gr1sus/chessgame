package com.chess.game

import kotlin.math.abs

object ChessGame {
    private var currentPlayer = ChessPlayer.WHITE
    private var pieceBox = mutableSetOf<ChessPiece>()


    init {
        reset()

    }

    fun clear() {
        //pieceBox.removeAll(pieceBox)
        pieceBox.clear()
    }

    fun addPiece(piece: ChessPiece) {
        pieceBox.add(piece)
    }

    fun canKnightMove(from: Square, to: Square): Boolean {
        val movingPiece = pieceAt(from) ?: return false
        return abs(from.col - to.col) == 2 && abs(from.row - to.row) == 1 || abs(from.col - to.col) == 1 && abs(
            from.row - to.row
        ) == 2 &&  movingPiece.player == currentPlayer
    }

    fun canRookMove(from: Square, to: Square): Boolean {
        val movingPiece = pieceAt(from) ?: return false

        if (movingPiece.player != currentPlayer) {
            return false
        }
        if (from.col == to.col && isClearVerticallyBetween(from,to)|| from.row == to.row && isClearHorizontallyBetween(from, to) ) {
             return true
        }
        return false
    }
    fun canBishopMove(from: Square,to: Square):Boolean{
        val movingPiece = pieceAt(from) ?: return false

        if (movingPiece.player != currentPlayer) {
            return false
        }
        if (abs(from.col-to.col)==abs(from.row-to.row))return isClearDiagonally(from,to)
        return false
    }
    fun canQueenMove(from: Square,to: Square):Boolean{
        val movingPiece = pieceAt(from) ?: return false

        if (movingPiece.player != currentPlayer) {
            return false
        }
        if (isClearDiagonally(from,to)||isClearVerticallyBetween(from,to)||isClearHorizontallyBetween(from, to))return true
        return false
    }
    fun canKingMove(from: Square,to: Square):Boolean{
        val movingPiece = pieceAt(from) ?: return false

        if (movingPiece.player != currentPlayer) {
            return false
        }
        val deltaCol=abs(from.col-to.col)
        val deltaRow=abs(from.row-to.row)
        if (canQueenMove(from, to)){
            return deltaCol==1&& deltaRow==1 || deltaCol+deltaRow==1
        }
        return false
    }
    fun canPawnMove(from: Square,to: Square):Boolean{
        val deltaCol = abs(from.col - to.col)
        val deltaRow = abs(from.row - to.row)

        val movingPiece = pieceAt(from) ?: return false
        val player = movingPiece.player
        if (movingPiece.player != currentPlayer) {
            return false
        }

        if (deltaCol == 0) {

            val direction = if (player == ChessPlayer.WHITE) 1 else -1
            if ((deltaRow == 1 && to.row - from.row == direction) ||
                (deltaRow == 2 && from.row == 1 && to.row - from.row == direction * 2) ||
                (deltaRow == 2 && from.row == 6 && to.row - from.row == direction * 2)
            ) {
                if (pieceAt(to) == null) {
                    return true
                }
            }
        } else if (deltaCol == 1 && deltaRow == 1) {

            val targetPiece = pieceAt(to)
            if (targetPiece != null && targetPiece.player != player) {
                return true
            }
        }
        return false
    }


    private fun isClearDiagonally(from: Square,to: Square):Boolean{
        if (abs(from.col-to.col)!=abs(from.row-to.row)) return false
        val gap = abs(from.col - to.col)-1
        for (i in 1..gap){
            val nextCol= if(to.col>from.col) from.col+i else from.col-i
            val nextRow= if (to.row>from.row)from.row+i else from.row-i
            if (pieceAt(nextCol,nextRow)!=null) return false
        }
        return true
    }
    private fun isClearVerticallyBetween(from: Square, to: Square): Boolean{
        if (from.col != to.col) return false
        val gapV = abs(from.row - to.row) - 1
        if(gapV==0)return true
        for (i in 1..gapV){
            val nextRow= if (to.row>from.row)from.row+i else from.row-i
            if (pieceAt(Square(from.col, nextRow)) != null) {
                return false
            }
        }
        return true
    }
    private fun isClearHorizontallyBetween(from: Square, to: Square): Boolean {
        if (from.row != to.row) return false
        val gapH = abs(from.col - to.col) - 1

        if (gapH == 0) return true
        for (i in 1..gapH) {
            val nextCol = if (to.col > from.col) from.col + i else from.col - i
            if (pieceAt(Square(nextCol, from.row)) != null) {
                return false
            }
        }

        return true
    }

    private fun canMove(from: Square, to: Square): Boolean {
        if (from.col == to.col && from.row == to.row) return false
        val movingPiece = pieceAt(from) ?: return false
        return when (movingPiece.rank) {
            ChessMan.KNIGHT -> canKnightMove(from, to)
            ChessMan.ROOK -> canRookMove(from, to)
            ChessMan.BISHOP -> canBishopMove(from,to)
            ChessMan.QUEEN -> canQueenMove(from, to)
            ChessMan.KING-> canKingMove(from, to)
            ChessMan.PAWN-> canPawnMove(from, to)
        }

    }

    fun movePiece(from: Square, to: Square) {
        if (canMove(from, to)) {

            movePiece(from.col, from.row, to.col, to.row)
            currentPlayer = if (currentPlayer == ChessPlayer.WHITE) ChessPlayer.BLACK else ChessPlayer.WHITE

        }
    }

    private fun movePiece(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int) {
        if (fromCol == toCol && fromRow == toRow) {
            return
        }

        val movingPiece = pieceAt(fromCol, fromRow) ?: return

        pieceAt(toCol, toRow)?.let {
            if (it.player == movingPiece.player) {
                return
            }
            pieceBox.remove(it)
        }
        pieceBox.remove(movingPiece)
        pieceBox.add(movingPiece.copy(col = toCol, row = toRow))

    }


    fun reset() {
        clear()
        currentPlayer=ChessPlayer.WHITE
        for (i in 0 until 2) {
            addPiece(
                ChessPiece(
                    0 + i * 7,
                    0,
                    ChessPlayer.WHITE,
                    ChessMan.ROOK,
                    R.drawable.whiterook
                )
            )
            addPiece(
                ChessPiece(
                    0 + i * 7,
                    7,
                    ChessPlayer.BLACK,
                    ChessMan.ROOK,
                    R.drawable.blackrook
                )
            )

            addPiece(
                ChessPiece(
                    1 + i * 5,
                    0,
                    ChessPlayer.WHITE,
                    ChessMan.KNIGHT,
                    R.drawable.whiteknight
                )
            )
            addPiece(
                ChessPiece(
                    1 + i * 5,
                    7,
                    ChessPlayer.BLACK,
                    ChessMan.KNIGHT,
                    R.drawable.blackknight
                )
            )

            addPiece(
                ChessPiece(
                    2 + i * 3,
                    0,
                    ChessPlayer.WHITE,
                    ChessMan.BISHOP,
                    R.drawable.whitebishop
                )
            )
            addPiece(
                ChessPiece(
                    2 + i * 3,
                    7,
                    ChessPlayer.BLACK,
                    ChessMan.BISHOP,
                    R.drawable.blackbishop
                )
            )
        }
        for (i in 0 until 8) {
            addPiece(ChessPiece(i, 1, ChessPlayer.WHITE, ChessMan.PAWN, R.drawable.whitepawn))
            addPiece(ChessPiece(i, 6, ChessPlayer.BLACK, ChessMan.PAWN, R.drawable.blackpawn))
        }

        addPiece(ChessPiece(4, 0, ChessPlayer.WHITE, ChessMan.KING, R.drawable.whiteking))
        addPiece(ChessPiece(4, 7, ChessPlayer.BLACK, ChessMan.KING, R.drawable.blackking))
        addPiece(ChessPiece(3, 0, ChessPlayer.WHITE, ChessMan.QUEEN, R.drawable.whitequeen))
        addPiece(ChessPiece(3, 7, ChessPlayer.BLACK, ChessMan.QUEEN, R.drawable.blackqueen))

    }

    fun pieceAt(square: Square): ChessPiece? {
        return pieceAt(square.col, square.row)
    }

    private fun pieceAt(col: Int, row: Int): ChessPiece? {
        for (piace in pieceBox) {
            if (col == piace.col && row == piace.row) {
                return piace
            }
        }
        return null
    }

    fun pgnboard(): String {
        var desc = "  a b c d e f g h \n"
        desc += ""
        for (row in 7 downTo 0) {
            desc += "${row + 1}"
            desc += boardRow(row)
            desc += " ${row + 1}"
            desc += "\n"
        }
        desc += "  a b c d e f g h \n"
        return desc
    }

    override fun toString(): String {
        var desc = "\n"
        for (row in 7 downTo 0) {
            desc += "$row"
            desc += boardRow(row)
            desc += "\n"
        }
        desc += "  0 1 2 3 4 5 6 7 \n"
        return desc
    }

    private fun boardRow(row: Int): String {
        var desc = ""
        for (col in 0 until 8) {
            desc += " "
            desc += pieceAt(col, row)?.let {
                val white = it.player == ChessPlayer.WHITE
                when (it.rank) {
                    ChessMan.KING -> if (white) "k" else "K"
                    ChessMan.QUEEN -> if (white) "q" else "Q"
                    ChessMan.ROOK -> if (white) "r" else "R"
                    ChessMan.KNIGHT -> if (white) "n" else "N"
                    ChessMan.BISHOP -> if (white) "b" else "B"
                    ChessMan.PAWN -> if (white) "p" else "P"
                }
            } ?: "."

        }
        return desc
    }
}