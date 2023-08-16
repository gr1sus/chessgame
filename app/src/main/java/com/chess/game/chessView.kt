package com.chess.game

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlin.math.min


class chessView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private final val scaleFactor = .9f
    private final var originX = 20f
    private final var originY = 400f
    private final var cellSide = 130f
    private final val paint = Paint()


    @SuppressLint("NewApi")
    private final val blackColor = Color.argb(1f, .7f, .7f, .7f)

    @SuppressLint("NewApi")
    private final val lightColor = Color.argb(1f, .9f, .9f, .9f)


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

    private final val bitmaps = mutableMapOf<Int, Bitmap>()

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

        val chessBoardSide = min(canvas.width, canvas.height)*scaleFactor
        cellSide = chessBoardSide / 8f
        originX = (canvas.width-chessBoardSide)/2f
        originY = (canvas.height-chessBoardSide)/2f

        drawChessBoard(canvas)
        drawPieces(canvas)
    }

    private fun drawPieces(canvas: Canvas) {
        for (row in 0..7) {
            for (col in 0..7) {
                chessDelegate?.piaceAt(col, row)?.let { drawPieceAt(canvas, col, row, it.resID) }
            }
        }
        // drawPieceAt(canvas,0,0,R.drawable.blackqueen)
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