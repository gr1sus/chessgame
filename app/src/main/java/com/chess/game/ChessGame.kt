package com.chess.game

object ChessGame {

    var pieceBox= mutableSetOf<ChessPiece>()



    init{
       reset()

    }

    fun movePiece(from:Square,to:Square){
        movePiece(from.col,from.row,to.col,to.row)
    }

   private fun movePiece(fromCol:Int,fromRow:Int,toCol:Int,toRow:Int){
        if(fromCol==toCol && fromRow==toRow){
             return
        }

        val movingPiece= pieceAt(fromCol,fromRow)?:return

        pieceAt(toCol,toRow)?.let {
            if (it.player==movingPiece.player){
                return
            }
            pieceBox.remove(it)
        }
        pieceBox.remove(movingPiece)
        pieceBox.add(movingPiece.copy(col=toCol,row=toRow))

    }




     fun reset(){
        pieceBox.removeAll(pieceBox)

        for (i in 0..1){
            pieceBox.add(ChessPiece(0+i*7,0,ChessPlayer.WHITE,ChessMan.ROOK,R.drawable.whiterook))
            pieceBox.add(ChessPiece(0+i*7,7,ChessPlayer.BLACK,ChessMan.ROOK,R.drawable.blackrook))

            pieceBox.add(ChessPiece(1+i*5,0,ChessPlayer.WHITE,ChessMan.KNIGHT,R.drawable.whiteknight))
            pieceBox.add(ChessPiece(1+i*5,7,ChessPlayer.BLACK,ChessMan.KNIGHT,R.drawable.blackknight))

            pieceBox.add(ChessPiece(2+i*3,0,ChessPlayer.WHITE,ChessMan.BISHOP,R.drawable.whitebishop))
            pieceBox.add(ChessPiece(2+i*3,7,ChessPlayer.BLACK,ChessMan.BISHOP,R.drawable.blackbishop))
        }
        for (i in 0..7){
            pieceBox.add(ChessPiece(i,1,ChessPlayer.WHITE,ChessMan.PAWN,R.drawable.whitepawn))
            pieceBox.add(ChessPiece(i,6,ChessPlayer.BLACK,ChessMan.PAWN,R.drawable.blackpawn))
        }

        pieceBox.add(ChessPiece(4,0,ChessPlayer.WHITE,ChessMan.KING,R.drawable.whiteking))
        pieceBox.add(ChessPiece(4,7,ChessPlayer.BLACK,ChessMan.KING,R.drawable.blackking))
        pieceBox.add(ChessPiece(3,0,ChessPlayer.WHITE,ChessMan.QUEEN,R.drawable.whitequeen))
        pieceBox.add(ChessPiece(3,7,ChessPlayer.BLACK,ChessMan.QUEEN,R.drawable.blackqueen))

    }

    fun pieceAt(square: Square): ChessPiece? {
        return pieceAt(square.col,square.row)
    }

    private fun pieceAt(col:Int, row:Int):ChessPiece? {
        for (piace in pieceBox){
            if (col==piace.col && row ==piace.row){
                return piace
            }
        }
        return null
    }

    fun pgnboard():String{
        var desc = "  a b c d e f g h \n"
        desc += ""
        for(row in 7 downTo 0) {
            desc += "${row+1}"
            desc+= boardRow(row)
            desc += " ${row+1}"
            desc+="\n"
        }
        desc += "  a b c d e f g h \n"
        return desc
    }
    override fun toString(): String {
        var desc = "\n"
        for(row in 7 downTo 0) {
            desc += "$row"
            desc+= boardRow(row)
            desc+="\n"
        }
            desc += "  0 1 2 3 4 5 6 7 \n"
            return desc
    }
    private fun boardRow(row:Int):String{
        var desc = ""
        for (col in 0 until 8) {
            desc+= " "
            desc+=pieceAt(col,row)?.let{
                val white=it.player == ChessPlayer.WHITE
                when (it.rank){
                    ChessMan.KING -> if (white)"k" else "K"
                    ChessMan.QUEEN -> if (white)"q" else "Q"
                    ChessMan.ROOK -> if (white)"r" else "R"
                    ChessMan.KNIGHT -> if (white)"n" else "N"
                    ChessMan.BISHOP -> if (white)"b" else "B"
                    ChessMan.PAWN -> if (white)"p" else "P"
                }
            } ?: "."

        }
        return desc
    }
}