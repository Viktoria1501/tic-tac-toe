package com.viktoria.tictactoy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.viktoria.tictactoy.GameLogicAI_3x3;
import com.viktoria.tictactoy.R;

/**
 * Custom View for rendering and interacting with the Tic Tac Toe board
 */
public class TicTacToyBoardAI_3x3 extends View {

    // Board configuration constants
    private final int boardColor;
    private final int xColor;
    private final int oColor;
    private final int winningLineColor;

    // Game state
    private boolean winningLineVisible = false;
    private int cellSize = 0;
    private final GameLogicAI_3x3 gameLogic;
    private final Paint paint = new Paint();

    /**
     * Constructor used when creating view from XML
     */
    public TicTacToyBoardAI_3x3(Context context, AttributeSet attrs) {
        super(context, attrs);

        gameLogic = new GameLogicAI_3x3();

        // Get custom attributes from XML
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.TicTacToyBoardAI_3x3, 0, 0);

        try {
            boardColor = typedArray.getInteger(R.styleable.TicTacToyBoardAI_3x3_boardColorAI, 0);
            xColor = typedArray.getInteger(R.styleable.TicTacToyBoardAI_3x3_XColorAI, 0);
            oColor = typedArray.getInteger(R.styleable.TicTacToyBoardAI_3x3_OColorAI, 0);
            winningLineColor = typedArray.getInteger(R.styleable.TicTacToyBoardAI_3x3_winningLineColorAI, 0);
        } finally {
            typedArray.recycle();
        }
    }

    /**
     * Measure the view and set its dimensions
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Make the board square
        int dimension = Math.min(getMeasuredWidth(), getMeasuredHeight());
        cellSize = dimension / 3;
        setMeasuredDimension(dimension, dimension);
    }

    /**
     * Draw the board and game elements
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        drawGameBoard(canvas);
        drawMarkers(canvas);

        // Draw winning line if game is won
        if (winningLineVisible) {
            drawWinningLine(canvas);
        }
    }

    /**
     * Draw the grid lines of the game board
     */
    private void drawGameBoard(Canvas canvas) {
        paint.setColor(boardColor);
        paint.setStrokeWidth(16);

        // Draw vertical lines
        for (int c = 1; c < 3; c++) {
            canvas.drawLine(cellSize * c, 0, cellSize * c, canvas.getWidth(), paint);
        }

        // Draw horizontal lines
        for (int r = 1; r < 3; r++) {
            canvas.drawLine(0, cellSize * r, canvas.getWidth(), cellSize * r, paint);
        }
    }

    /**
     * Draw an X marker at the specified cell
     */
    private void drawX(Canvas canvas, int row, int col) {
        paint.setColor(xColor);
        paint.setStrokeWidth(16);

        float margin = cellSize * 0.2f;

        // Draw the X (two diagonal lines)
        canvas.drawLine(
                (col + 1) * cellSize - margin,
                row * cellSize + margin,
                col * cellSize + margin,
                (row + 1) * cellSize - margin,
                paint
        );

        canvas.drawLine(
                col * cellSize + margin,
                row * cellSize + margin,
                (col + 1) * cellSize - margin,
                (row + 1) * cellSize - margin,
                paint
        );
    }
    /**
     * Draw an O marker at the specified cell
     */
    private void drawO(Canvas canvas, int row, int col) {
        paint.setColor(oColor);
        paint.setStrokeWidth(16);

        float margin = cellSize * 0.2f;

        canvas.drawOval(
                col * cellSize + margin,
                row * cellSize + margin,
                (col * cellSize + cellSize) - margin,
                (row * cellSize + cellSize) - margin,
                paint
        );
    }

    /**
     * Draw the winning line when game is won
     */
    private void drawWinningLine(Canvas canvas) {
        paint.setColor(winningLineColor);
        paint.setStrokeWidth(20);

        int[] winType = gameLogic.getWinType();
        if (winType == null) return;

        // Extract winning line information
        int row = winType[0];
        int col = winType[1];
        int type = winType[2];

        // Draw appropriate winning line based on win type
        switch (type) {
            case 1: // Row win
                canvas.drawLine(
                        0, (row * cellSize) + (cellSize / 2),
                        getWidth(), (row * cellSize) + (cellSize / 2),
                        paint);
                break;
            case 2: // Column win
                canvas.drawLine(
                        (col * cellSize) + (cellSize / 2), 0,
                        (col * cellSize) + (cellSize / 2), getHeight(),
                        paint);
                break;
            case 3: // Diagonal win (top-left to bottom-right)
                canvas.drawLine(0, 0, getWidth(), getHeight(), paint);
                break;
            case 4: // Diagonal win (bottom-left to top-right)
                canvas.drawLine(0, getHeight(), getWidth(), 0, paint);
                break;
        }
    }

    /**
     * Handle touch events on the board
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // Only process touch if board is properly sized
            if (cellSize <= 0) return false;

            // If game is over, ignore touch
            if (gameLogic.isGameOver()) return false;

            // Calculate which cell was touched
            float x = event.getX();
            float y = event.getY();

            int row = (int) Math.ceil(y / cellSize);
            int col = (int) Math.ceil(x / cellSize);

            // Ensure row/col are within valid range
            row = Math.max(1, Math.min(3, row));
            col = Math.max(1, Math.min(3, col));

            // Process the move
            if (gameLogic.updateGameBoard(row, col)) {
                invalidate(); // Redraw the board

                // Check if the game is over
                if (gameLogic.checkForWin()) {
                    winningLineVisible = true;
                    invalidate();
                } else if (gameLogic.checkForTie()) {
                    invalidate();
                } else {
                    // Switch to next player
                    gameLogic.switchPlayer();

                    // If AI's turn, make AI move with minimax
                    if (gameLogic.getCurrentPlayer() == GameLogicAI_3x3.AI) {
                        makeAIMove();
                    }
                }
                return true;
            }
        }

        return false;
    }

    /**
     * Make a move for the AI player using minimax
     */
    private void makeAIMove() {
        // Use the minimax algorithm in GameLogicAI_3x3
        if (gameLogic.makeAIMove()) {
            // Check if game is over after AI move
            if (gameLogic.checkForWin()) {
                winningLineVisible = true;
            } else if (gameLogic.checkForTie()) {
                invalidate();
            } else {
                gameLogic.switchPlayer();
            }
            invalidate();
        }
    }
    /**
     * Draw the current game markers (X's and O's)
     */
    private void drawMarkers(Canvas canvas) {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (gameLogic.getGameBoard()[r][c] == GameLogicAI_3x3.PLAYER) {
                    drawX(canvas, r, c);
                } else if (gameLogic.getGameBoard()[r][c] == GameLogicAI_3x3.AI) {
                    drawO(canvas, r, c);
                }
            }
        }
    }

    /**
     * Reset the game to initial state
     */
    public void resetGame() {
        gameLogic.resetGame();
        winningLineVisible = false;
        invalidate();
    }

    /**
     * Set up game with UI elements and player names
     */
    public void setUpGame(Button playAgainBtn, Button homeBtn, TextView playerDisplay, String[] names) {
        gameLogic.setPlayAgainButton(playAgainBtn);
        gameLogic.setHomeButton(homeBtn);
        gameLogic.setPlayerTurnDisplay(playerDisplay);
        gameLogic.setPlayerNames(names);
    }
}