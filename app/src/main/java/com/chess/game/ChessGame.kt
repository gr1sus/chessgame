package com.chess.game

object ChessGame {

    var pieceBox= mutableSetOf<ChessPiace>()



    init{
       reset()
    /*
        //TODO
        Log.d(TAG,"${pieceBox.size}")
        movePiece(0,0,1,7)
        movePiece(1,7,1,4)

        Log.d(TAG,toString())
        Log.d(TAG,"${pieceBox.size}")*/
    }



    fun movePiece(fromCol:Int,fromRow:Int,toCol:Int,toRow:Int){
        if(fromCol==toCol && fromRow==toRow){
             return
        }

        val movingPiece= piaceAt(fromCol,fromRow)?:return

        piaceAt(toCol,toRow)?.let {
            if (it.player==movingPiece.player){
                return
            }
            pieceBox.remove(it)
        }
        pieceBox.remove(movingPiece)
        pieceBox.add(ChessPiace(toCol,toRow,movingPiece.player,movingPiece.rank,movingPiece.resID))





    }




     fun reset(){
        pieceBox.removeAll(pieceBox)

        for (i in 0..1){
            pieceBox.add(ChessPiace(0+i*7,0,ChessPlayer.WHITE,ChessRank.ROOK,R.drawable.whiterook))
            pieceBox.add(ChessPiace(0+i*7,7,ChessPlayer.BLACK,ChessRank.ROOK,R.drawable.blackrook))

            pieceBox.add(ChessPiace(1+i*5,0,ChessPlayer.WHITE,ChessRank.KNIGHT,R.drawable.whiteknight))
            pieceBox.add(ChessPiace(1+i*5,7,ChessPlayer.BLACK,ChessRank.KNIGHT,R.drawable.blackknight))

            pieceBox.add(ChessPiace(2+i*3,0,ChessPlayer.WHITE,ChessRank.BISHOP,R.drawable.whitebishop))
            pieceBox.add(ChessPiace(2+i*3,7,ChessPlayer.BLACK,ChessRank.BISHOP,R.drawable.blackbishop))
        }
        for (i in 0..7){
            pieceBox.add(ChessPiace(i,1,ChessPlayer.WHITE,ChessRank.PAWN,R.drawable.whitepawn))
            pieceBox.add(ChessPiace(i,6,ChessPlayer.BLACK,ChessRank.PAWN,R.drawable.blackpawn))
        }

        pieceBox.add(ChessPiace(4,0,ChessPlayer.WHITE,ChessRank.KING,R.drawable.whiteking))
        pieceBox.add(ChessPiace(4,7,ChessPlayer.BLACK,ChessRank.KING,R.drawable.blackking))
        pieceBox.add(ChessPiace(3,0,ChessPlayer.WHITE,ChessRank.QUEEN,R.drawable.whitequeen))
        pieceBox.add(ChessPiace(3,7,ChessPlayer.BLACK,ChessRank.QUEEN,R.drawable.blackqueen))

    }
    fun piaceAt(col:Int, row:Int):ChessPiace? {
        for (piace in pieceBox){
            if (col==piace.col && row ==piace.row){
                return piace
            }
        }
        return null
    }

    override fun toString(): String {
        var desc = ""
        for(row in 7 downTo 0) {
            desc += "$row"
            for (col in 0..7) {
                val piece = piaceAt(col,row)
                if(piece==null){
                    desc += " ."
                }
                else {
                    val white=piece.player == ChessPlayer.WHITE
                    desc+=" "
                    desc += when (piece.rank){
                        ChessRank.KING -> {
                            if (white)"k" else "K"
                        }

                        ChessRank.QUEEN -> {
                            if (white)"q" else "Q"
                        }

                        ChessRank.ROOK -> {
                            if (white)"r" else "R"
                        }

                        ChessRank.KNIGHT -> {
                            if (white)"n" else "N"
                        }

                        ChessRank.BISHOP -> {
                            if (white)"b" else "B"
                        }

                        ChessRank.PAWN -> {
                            if (white)"p" else "P"
                        }
                    }
                }
            }
            desc+="\n"

        }
            desc += "  0 1 2 3 4 5 6 7 "
            return desc
    }
}