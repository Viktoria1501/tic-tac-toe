package com.viktoria.tictactoy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TicTacToyBoard_5x5 extends View {
    private static final String TAG = "TicTacToyBoard_5x5";

    private final int boardColor_5x5;
    private final int XColor_5x5;
    private final int OColor_5x5;
    private final int winningLineColor_5x5;
    private boolean winningLine_5x5 = false;
    private final Paint paint_5x5 = new Paint();
    private final GameLogic_5x5 game_5x5;
    private int cellSize_5x5;

    public TicTacToyBoard_5x5(Context context, @Nullable AttributeSet attrs_5x5) {
        super(context, attrs_5x5);
        game_5x5 = new GameLogic_5x5();

        TypedArray b = context.getTheme().obtainStyledAttributes(attrs_5x5, R.styleable.TicTacToyBoard_5x5, 0, 0);

        try {
            boardColor_5x5 = b.getInteger(R.styleable.TicTacToyBoard_5x5_boardColor_5x5, 0);
            XColor_5x5 = b.getInteger(R.styleable.TicTacToyBoard_5x5_XColor_5x5, 0);
            OColor_5x5 = b.getInteger(R.styleable.TicTacToyBoard_5x5_OColor_5x5, 0);
            winningLineColor_5x5 = b.getInteger(R.styleable.TicTacToyBoard_5x5_winningLineColor_5x5, 0);
        } finally {
            b.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int dimension = Math.min(getMeasuredWidth(), getMeasuredHeight());
        cellSize_5x5 = dimension / 5;
        setMeasuredDimension(dimension, dimension);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint_5x5.setStyle(Paint.Style.STROKE);
        paint_5x5.setAntiAlias(true);

        drawGameBoard_5x5(canvas);
        drawMarkers_5x5(canvas);

        if (winningLine_5x5) {
            paint_5x5.setColor(winningLineColor_5x5);
            drawWinningLine_5x5(canvas);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            // Only proceed if cellSize is valid (board has been measured)
            if (cellSize_5x5 <= 0) return false;

            int row = (int) Math.ceil(y / cellSize_5x5);
            int col = (int) Math.ceil(x / cellSize_5x5);

            // Enforce bounds checking for row/col values
            if (row < 1) row = 1;
            if (row > 5) row = 5;
            if (col < 1) col = 1;
            if (col > 5) col = 5;

            if(!winningLine_5x5) {
                if (game_5x5.updateGameBoard(row, col)) {
                    invalidate();

                    if (game_5x5.winnerCheck()) {
                        winningLine_5x5 = true;
                        invalidate();
                    }

                    // Alternate player turns
                    if (game_5x5.getPlayer_5x5() % 2 == 0) {
                        game_5x5.setPlayer_5x5(game_5x5.getPlayer_5x5() - 1);
                    } else {
                        game_5x5.setPlayer_5x5(game_5x5.getPlayer_5x5() + 1);
                    }
                }
            }

            invalidate();
            return true;
        }
        return false;
    }

    private void drawGameBoard_5x5(Canvas canvas) {
        paint_5x5.setColor(boardColor_5x5);
        paint_5x5.setStrokeWidth(16);

        for (int c = 1; c < 5; c++) {
            canvas.drawLine(cellSize_5x5 * c, 0, cellSize_5x5 * c, getHeight(), paint_5x5);
        }

        for (int r = 1; r < 5; r++) {
            canvas.drawLine(0, cellSize_5x5 * r, getWidth(), cellSize_5x5 * r, paint_5x5);
        }
    }

    private void drawMarkers_5x5(Canvas canvas) {
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                int value = game_5x5.getGameBoard_5x5()[r][c];
                if (value == 1) {
                    drawX_5x5(canvas, r, c);
                } else if (value == 2) {
                    drawO_5x5(canvas, r, c);
                }
            }
        }
    }

    private void drawX_5x5(Canvas canvas, int row, int col) {
        paint_5x5.setColor(XColor_5x5);
        paint_5x5.setStrokeWidth(10);

        float startX = col * cellSize_5x5 + cellSize_5x5 * 0.2f;
        float startY = row * cellSize_5x5 + cellSize_5x5 * 0.2f;
        float endX = (col + 1) * cellSize_5x5 - cellSize_5x5 * 0.2f;
        float endY = (row + 1) * cellSize_5x5 - cellSize_5x5 * 0.2f;

        canvas.drawLine(startX, startY, endX, endY, paint_5x5);
        canvas.drawLine(startX, endY, endX, startY, paint_5x5);
    }

    private void drawO_5x5(Canvas canvas, int row, int col) {
        paint_5x5.setColor(OColor_5x5);
        paint_5x5.setStrokeWidth(10);

        float left = col * cellSize_5x5 + cellSize_5x5 * 0.2f;
        float top = row * cellSize_5x5 + cellSize_5x5 * 0.2f;
        float right = (col + 1) * cellSize_5x5 - cellSize_5x5 * 0.2f;
        float bottom = (row + 1) * cellSize_5x5 - cellSize_5x5 * 0.2f;

        canvas.drawOval(left, top, right, bottom, paint_5x5);
    }

    private void drawHorizontalLine_5x5(Canvas canvas, int row, int col) {
        canvas.drawLine(col * cellSize_5x5,
                (row + 0.5f) * cellSize_5x5,
                (col + 5) * cellSize_5x5,  // changed from +4 to +5 for 5 cells
                (row + 0.5f) * cellSize_5x5,
                paint_5x5);
    }

    private void drawVerticalLine_5x5(Canvas canvas, int row, int col) {
        canvas.drawLine((col + 0.5f) * cellSize_5x5,
                row * cellSize_5x5,
                (col + 0.5f) * cellSize_5x5,
                (row + 5) * cellSize_5x5, // changed from +4 to +5 for 5 cells
                paint_5x5);
    }

    private void drawDiagonalLineNeg_5x5(Canvas canvas, int row, int col) {
        // top-left to bottom-right diagonal over 5 cells
        canvas.drawLine(col * cellSize_5x5,
                row * cellSize_5x5,
                (col + 5) * cellSize_5x5,
                (row + 5) * cellSize_5x5,
                paint_5x5);
    }

    private void drawDiagonalLinePos_5x5(Canvas canvas, int row, int col) {
        // bottom-left to top-right diagonal over 5 cells
        canvas.drawLine(col * cellSize_5x5,
                (row + 5) * cellSize_5x5,
                (col + 5) * cellSize_5x5,
                row * cellSize_5x5,
                paint_5x5);
    }


    private void drawWinningLine_5x5(Canvas canvas) {
        int row = game_5x5.getWinType()[0];
        int col = game_5x5.getWinType()[1];
        int type = game_5x5.getWinType()[2];

        Log.d(TAG, "Drawing winning line: row=" + row + ", col=" + col + ", type=" + type);

        switch (type) {
            case 1:
                drawHorizontalLine_5x5(canvas, row, col);
                break;
            case 2:
                drawVerticalLine_5x5(canvas, row, col);
                break;
            case 3:
                drawDiagonalLineNeg_5x5(canvas, row, col);
                break;
            case 4:
                drawDiagonalLinePos_5x5(canvas, row, col);
                break;
        }
    }

    public void setUpGame_5x5(Button playAgain_5x5, Button home_5x5, TextView playerDisplay_5x5, String[] names) {
        game_5x5.setPlayAgainBTN_5x5(playAgain_5x5);
        game_5x5.setHomeBTN_5x5(home_5x5);
        game_5x5.setPlayerTurn_5x5(playerDisplay_5x5);
        game_5x5.setPlayerNames_5x5(names);
    }

    public void resetGame_5x5() {
        game_5x5.resetGame_5x5();
        winningLine_5x5 = false;
    }
}