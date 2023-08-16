package com.chess.game


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.math.min


class chessView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val scaleFactor = .9f
    private var originX = 20f
    private var originY = 400f
    private var cellSide = 130f
    private val paint = Paint()
    private var fromCol: Int = -1
    private var fromRow: Int = -1

    /*private var toCol:Int=-1
    private var toRow:Int=-1*/
    private var movingPieceX: Float = -1f
    private var movingPieceY: Float = -1f

    private val blackColor = Color.parseColor("#BBBBBB")


    private val lightColor = Color.parseColor("#EEEEEE")


    private final val imageResiDs = setOf(
        R.drawable.whitequeen,
        R.drawable.whiteking,
        R.drawable.whitebishop,
        R.drawable.whiteknight,
        R.drawable.whiterook,
        R.drawable.whitepawn,
        R.drawable.blackqueen,
        R.drawable.blackking,
        R.drawable.blackbishop,
        R.drawable.blackknight,
        R.drawable.blackrook,
        R.drawable.blackpawn
    )

    var chessDelegate: chessDelegate? = null

    private val bitmaps = mutableMapOf<Int, Bitmap>()
    private var movingPiece: ChessPiace? = null
    private var movingpieceBitmap: Bitmap? = null

    private fun loadBitmaps() {
        imageResiDs.forEach {
            bitmaps[it] = BitmapFactory.decodeResource(resources, it)
        }
    }

    init {
        loadBitmaps()
    }

    override fun onDraw(canvas: Canvas?) {
        canvas ?: return

        val chessBoardSide = min(width, height) * scaleFactor
        cellSide = chessBoardSide / 8f
        originX = (width - chessBoardSide) / 2f
        originY = (height - chessBoardSide) / 2f

        drawChessBoard(canvas)
        drawPieces(canvas)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return false
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                fromCol = ((event.x - originX) / cellSide).toInt()
                fromRow = 7 - ((event.y - originY) / cellSide).toInt()

                chessDelegate?.piaceAt(fromCol, fromRow)?.let {
                    movingPiece = it
                    movingpieceBitmap = bitmaps[it.resID]
                }
            }

            MotionEvent.ACTION_MOVE -> {
                movingPieceX = event.x
                movingPieceY = event.y
                invalidate()
            }

            MotionEvent.ACTION_UP -> {
                var toCol = ((event.x - originX) / cellSide).toInt()
                var toRow = 7 - ((event.y - originY) / cellSide).toInt()
                Log.d(TAG, "from($fromCol,$fromRow) to ($toCol,$toRow)")
                chessDelegate?.movePiece(fromCol, fromRow, toCol, toRow)
                movingPiece = null
                movingpieceBitmap = null


            }
        }
        return true
    }

    private fun drawPieces(canvas: Canvas) {
        for (row in 0..7) {
            for (col in 0..7) {
                /*if (row != fromRow || col != fromCol) {
                    chessDelegate?.piaceAt(col, row)
                        ?.let { drawPieceAt(canvas, col, row, it.resID) }
                }*/
                chessDelegate?.piaceAt(col, row)
                    ?.let {
                    if(it!=movingPiece){
                        drawPieceAt(canvas, col, row, it.resID) }

                    }
            }
        }

        movingpieceBitmap?.let {
            canvas.drawBitmap(
                it,
                null,
                RectF(
                    movingPieceX - cellSide / 2,
                    movingPieceY - cellSide / 2,
                    movingPieceX + cellSide / 2,
                    movingPieceY + cellSide / 2
                ),
                paint
            )
        }


    }


    private fun drawPieceAt(canvas: Canvas, col: Int, row: Int, resId: Int) {
        val bitmap = bitmaps[resId]!!
        canvas.drawBitmap(
            bitmap,
            null,
            RectF(
                originX + col * cellSide,
                originY + (7 - row) * cellSide,
                originX + (col + 1) * cellSide,
                originY + ((7 - row) + 1) * cellSide
            ),
            paint
        )
    }


    private fun drawChessBoard(canvas: Canvas) {
        for (j in 0..7) {
            for (i in 0..7) {
                paint.color = if ((i + j) % 2 == 0) lightColor else blackColor
                canvas.drawRect(
                    originX + i * cellSide,
                    originY + j * cellSide,
                    originX + (i + 1) * cellSide,
                    originY + (j + 1) * cellSide,
                    paint
                )
            }
        }
    }
}