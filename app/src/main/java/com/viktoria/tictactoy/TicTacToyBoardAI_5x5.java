package com.viktoria.tictactoy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class TicTacToyBoardAI_5x5 extends View {

    private final int boardColorAI_5x5;
    private final int XColorAI_5x5;
    private final int OColorAI_5x5;
    private final int winningLineColorAI_5x5;

    private final Paint paintAI_5x5 = new Paint();
    private int cellSizeAI_5x5 = getWidth()/5; // Wheres  your head at at attttt...
    private final GameLogicAI_5x5 gameAI_5x5;

    public TicTacToyBoardAI_5x5(Context context, @Nullable AttributeSet attrsAI_5x5) {
        super(context, attrsAI_5x5);
        gameAI_5x5 = new GameLogicAI_5x5();

        TypedArray d = context.getTheme().obtainStyledAttributes(attrsAI_5x5, R.styleable.TicTacToyBoardAI_5x5,0,0);

        try{
            boardColorAI_5x5 = d.getInteger(R.styleable.TicTacToyBoardAI_5x5_boardColorAI_5x5,0);
            XColorAI_5x5 = d.getInteger(R.styleable.TicTacToyBoardAI_5x5_XColorAI_5x5,0);
            OColorAI_5x5 = d.getInteger(R.styleable.TicTacToyBoardAI_5x5_OColorAI_5x5,0);
            winningLineColorAI_5x5 = d.getInteger(R.styleable.TicTacToyBoardAI_5x5_winningLineColorAI_5x5,0);
        }finally {
            d.recycle();
        }

    }

    @Override
    protected void onMeasure(int width, int height){
        super.onMeasure(width, height);

        int dimension = Math.min(getMeasuredWidth(), getMeasuredHeight());
        cellSizeAI_5x5 = dimension/5;

        setMeasuredDimension(dimension, dimension);

    }
    @Override
    protected void onDraw(Canvas canvas){
        paintAI_5x5.setStyle(Paint.Style.STROKE);
        paintAI_5x5.setAntiAlias(true);

        drawGameBoardAI_5x5(canvas);
        drawMarkers(canvas);

    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN){
            int row = (int) Math.ceil(y / cellSizeAI_5x5);
            int col = (int) Math.ceil(x / cellSizeAI_5x5);
            if(gameAI_5x5.updateGameBoardAI_5x5(row, col)){
                invalidate();
                // updating the players turn
                if(gameAI_5x5.getPlayer() % 2 == 0){
                    gameAI_5x5.setPlayer(gameAI_5x5.getPlayer() - 1);
                }else{
                    gameAI_5x5.setPlayer(gameAI_5x5.getPlayer() + 1);
                }
            }
            invalidate();
            return true;
        }
        return false;
    }


    private void drawGameBoardAI_5x5(Canvas canvas){
        paintAI_5x5.setColor(boardColorAI_5x5);
        paintAI_5x5.setStrokeWidth(16);

        for (int c = 1; c < 5; c++){
            canvas.drawLine(cellSizeAI_5x5 * c, 0, cellSizeAI_5x5 * c, canvas.getWidth(), paintAI_5x5);
        }
        for (int r = 1; r < 5; r++){
            canvas.drawLine(0, cellSizeAI_5x5 * r,canvas.getWidth(),cellSizeAI_5x5 * r , paintAI_5x5);
        }


    }
    private void drawMarkers(Canvas canvas){
        for(int r = 0; r < 5; r ++){
            for(int c = 0; c < 5; c++){
                if(gameAI_5x5.getGameBoardAI_5x5()[r][c] != 0){
                    if(gameAI_5x5.getGameBoardAI_5x5()[r][c] == 1){
                        drawXAI_5x5(canvas, r, c);
                    }
                    else{
                        drawOAI_5x5(canvas, r,c);
                    }
                }
            }
        }
    }

    private void drawXAI_5x5(Canvas canvas, int row, int col){
        paintAI_5x5.setColor(XColorAI_5x5);
        paintAI_5x5.setStrokeWidth(10);

        float startX = col * cellSizeAI_5x5 + cellSizeAI_5x5 * 0.2f;
        float startY = row * cellSizeAI_5x5 + cellSizeAI_5x5 * 0.2f;
        float endX = (col + 1) * cellSizeAI_5x5 - cellSizeAI_5x5 * 0.2f;
        float endY = (row + 1) * cellSizeAI_5x5 - cellSizeAI_5x5 * 0.2f;

        canvas.drawLine(startX, startY, endX, endY, paintAI_5x5);
        canvas.drawLine(startX, endY, endX, startY, paintAI_5x5);
    }
    private void drawOAI_5x5(Canvas canvas, int row, int col){
        paintAI_5x5.setColor(OColorAI_5x5);
        paintAI_5x5.setStrokeWidth(10);

        float left = col * cellSizeAI_5x5 + cellSizeAI_5x5 * 0.2f;
        float top = row * cellSizeAI_5x5 + cellSizeAI_5x5 * 0.2f;
        float right = (col + 1) * cellSizeAI_5x5 - cellSizeAI_5x5 * 0.2f;
        float bottom = (row + 1) * cellSizeAI_5x5 - cellSizeAI_5x5 * 0.2f;

        canvas.drawOval(left, top, right, bottom, paintAI_5x5);;
    }



}
