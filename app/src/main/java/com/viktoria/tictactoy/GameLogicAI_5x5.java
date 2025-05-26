package com.viktoria.tictactoy;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Enhanced game logic for 5x5 Tic Tac Toe with AI implementation
 * Uses minimax algorithm with alpha-beta pruning and configurable difficulty
 */
public class GameLogicAI_5x5 {
    // Constants for board states
    public static final int EMPTY = 0;
    public static final int PLAYER = 1;
    public static final int AI = 2;

    private static final int BOARD_SIZE = 5;
    private static final int WIN_LENGTH = 4; // Need 4 in a row to win

    // Game state
    private int[][] gameBoardAI_5x5;
    private int currentPlayer = PLAYER;
    private boolean gameOver = false;

    // UI components
    private Button playAgainBTNAI_5x5;
    private Button homeBTNAI_5x5;
    private TextView playerTurnAI_5x5;
    private String[] playerNamesAI_5x5 = {"Player", "AI"};
    private int[] winType;

    // AI configuration
    private final Random random = new Random();
    private final float aiMistakeRate = 0.20f; // 20% chance AI makes suboptimal move
    private final int maxDepth = 4; // Limit search depth for performance

    public GameLogicAI_5x5() {
        gameBoardAI_5x5 = new int[BOARD_SIZE][BOARD_SIZE];
        resetGameAI_5x5();
    }

    /**
     * Updates the game board with a move
     */
    public boolean updateGameBoardAI_5x5(int row, int col) {
        // Check bounds and game state
        if (gameOver || row < 1 || row > BOARD_SIZE || col < 1 || col > BOARD_SIZE) {
            return false;
        }

        // Convert to 0-based indexing
        int boardRow = row - 1;
        int boardCol = col - 1;

        // Check if cell is empty
        if (gameBoardAI_5x5[boardRow][boardCol] == EMPTY) {
            gameBoardAI_5x5[boardRow][boardCol] = currentPlayer;
            updatePlayerTurnDisplay();
            return true;
        }

        return false;
    }

    /**
     * Updates the player turn display
     */
    private void updatePlayerTurnDisplay() {
        if (playerTurnAI_5x5 != null && !gameOver) {
            if (currentPlayer == PLAYER) {
                playerTurnAI_5x5.setText(playerNamesAI_5x5[0] + "'s Turn");
            } else {
                playerTurnAI_5x5.setText(playerNamesAI_5x5[1] + "'s Turn");
            }
        }
    }

    /**
     * Checks for winner with 4 in a row
     */
    public boolean winnerCheckAI_5x5() {
        boolean isWinner = false;

        // Check rows
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c <= BOARD_SIZE - WIN_LENGTH; c++) {
                if (gameBoardAI_5x5[r][c] != EMPTY &&
                        gameBoardAI_5x5[r][c] == gameBoardAI_5x5[r][c + 1] &&
                        gameBoardAI_5x5[r][c] == gameBoardAI_5x5[r][c + 2] &&
                        gameBoardAI_5x5[r][c] == gameBoardAI_5x5[r][c + 3]) {
                    winType = new int[]{r, c, 1};
                    isWinner = true;
                }
            }
        }

        // Check columns
        for (int c = 0; c < BOARD_SIZE; c++) {
            for (int r = 0; r <= BOARD_SIZE - WIN_LENGTH; r++) {
                if (gameBoardAI_5x5[r][c] != EMPTY &&
                        gameBoardAI_5x5[r][c] == gameBoardAI_5x5[r + 1][c] &&
                        gameBoardAI_5x5[r][c] == gameBoardAI_5x5[r + 2][c] &&
                        gameBoardAI_5x5[r][c] == gameBoardAI_5x5[r + 3][c]) {
                    winType = new int[]{r, c, 2};
                    isWinner = true;
                }
            }
        }

        // Check diagonals (top-left to bottom-right)
        for (int r = 0; r <= BOARD_SIZE - WIN_LENGTH; r++) {
            for (int c = 0; c <= BOARD_SIZE - WIN_LENGTH; c++) {
                if (gameBoardAI_5x5[r][c] != EMPTY &&
                        gameBoardAI_5x5[r][c] == gameBoardAI_5x5[r + 1][c + 1] &&
                        gameBoardAI_5x5[r][c] == gameBoardAI_5x5[r + 2][c + 2] &&
                        gameBoardAI_5x5[r][c] == gameBoardAI_5x5[r + 3][c + 3]) {
                    winType = new int[]{r, c, 3};
                    isWinner = true;
                }
            }
        }

        // Check diagonals (top-right to bottom-left)
        for (int r = 0; r <= BOARD_SIZE - WIN_LENGTH; r++) {
            for (int c = WIN_LENGTH - 1; c < BOARD_SIZE; c++) {
                if (gameBoardAI_5x5[r][c] != EMPTY &&
                        gameBoardAI_5x5[r][c] == gameBoardAI_5x5[r + 1][c - 1] &&
                        gameBoardAI_5x5[r][c] == gameBoardAI_5x5[r + 2][c - 2] &&
                        gameBoardAI_5x5[r][c] == gameBoardAI_5x5[r + 3][c - 3]) {
                    winType = new int[]{r, c, 4};
                    isWinner = true;
                }
            }
        }

        if (isWinner) {
            gameOver = true;
            showGameOver(playerNamesAI_5x5[currentPlayer - 1] + " Won!!!");
            return true;
        }

        return false;
    }

    /**
     * Checks if the board is full (tie condition)
     */
    public boolean checkForTie() {
        if (isBoardFull()) {
            gameOver = true;
            showGameOver("Tie Game!!!");
            return true;
        }
        return false;
    }

    /**
     * Checks if board is completely filled
     */
    private boolean isBoardFull() {
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                if (gameBoardAI_5x5[r][c] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Shows game over state
     */
    private void showGameOver(String message) {
        if (playAgainBTNAI_5x5 != null) {
            playAgainBTNAI_5x5.setVisibility(View.VISIBLE);
        }
        if (homeBTNAI_5x5 != null) {
            homeBTNAI_5x5.setVisibility(View.VISIBLE);
        }
        if (playerTurnAI_5x5 != null) {
            playerTurnAI_5x5.setText(message);
        }
    }

    /**
     * Switches the current player
     */
    public void switchPlayer() {
        currentPlayer = (currentPlayer == PLAYER) ? AI : PLAYER;
        updatePlayerTurnDisplay();
    }

    /**
     * Makes an AI move using minimax with alpha-beta pruning
     * Returns true if a move was made successfully
     */
    public boolean makeAIMove() {
        if (gameOver || currentPlayer != AI) {
            return false;
        }

        // Sometimes make a random move to vary difficulty
        if (random.nextFloat() < aiMistakeRate && !isEarlyGame()) {
            List<int[]> availableMoves = getAvailableMoves();
            if (!availableMoves.isEmpty()) {
                int[] randomMove = availableMoves.get(random.nextInt(availableMoves.size()));
                // Make the move directly on the board
                gameBoardAI_5x5[randomMove[0]][randomMove[1]] = AI;
                return true;
            }
        } else {
            // Use minimax to find best move
            int[] bestMove = findBestMove();
            if (bestMove != null) {
                // Make the move directly on the board
                gameBoardAI_5x5[bestMove[0]][bestMove[1]] = AI;
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if it's early in the game (first few moves)
     */
    private boolean isEarlyGame() {
        int moveCount = 0;
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                if (gameBoardAI_5x5[r][c] != EMPTY) {
                    moveCount++;
                }
            }
        }
        return moveCount <= 3;
    }

    /**
     * Gets all available moves
     */
    private List<int[]> getAvailableMoves() {
        List<int[]> moves = new ArrayList<>();
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                if (gameBoardAI_5x5[r][c] == EMPTY) {
                    moves.add(new int[]{r, c});
                }
            }
        }
        return moves;
    }

    /**
     * Finds the best move using minimax with alpha-beta pruning
     */
    private int[] findBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = null;

        List<int[]> prioritizedMoves = getPrioritizedMoves();

        for (int[] move : prioritizedMoves) {
            int r = move[0];
            int c = move[1];

            // Make the move
            gameBoardAI_5x5[r][c] = AI;

            // Calculate score
            int score = minimax(0, false, Integer.MIN_VALUE, Integer.MAX_VALUE);

            // Undo the move
            gameBoardAI_5x5[r][c] = EMPTY;

            if (score > bestScore) {
                bestScore = score;
                bestMove = new int[]{r, c};
            }
        }

        return bestMove;
    }

    /**
     * Gets moves prioritized by strategic importance
     */
    private List<int[]> getPrioritizedMoves() {
        List<int[]> winningMoves = new ArrayList<>();
        List<int[]> blockingMoves = new ArrayList<>();
        List<int[]> centerMoves = new ArrayList<>();
        List<int[]> otherMoves = new ArrayList<>();

        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                if (gameBoardAI_5x5[r][c] == EMPTY) {
                    // Check if this move wins the game
                    gameBoardAI_5x5[r][c] = AI;
                    if (isWinningMove(AI)) {
                        winningMoves.add(new int[]{r, c});
                    } else {
                        gameBoardAI_5x5[r][c] = PLAYER;
                        if (isWinningMove(PLAYER)) {
                            blockingMoves.add(new int[]{r, c});
                        } else if (Math.abs(r - 2) <= 1 && Math.abs(c - 2) <= 1) {
                            centerMoves.add(new int[]{r, c});
                        } else {
                            otherMoves.add(new int[]{r, c});
                        }
                    }
                    gameBoardAI_5x5[r][c] = EMPTY;
                }
            }
        }

        // Prioritize: winning moves > blocking moves > center moves > other moves
        List<int[]> result = new ArrayList<>();
        result.addAll(winningMoves);
        result.addAll(blockingMoves);
        result.addAll(centerMoves);
        result.addAll(otherMoves);

        return result;
    }

    /**
     * Checks if the last move results in a win
     */
    private boolean isWinningMove(int player) {
        // Check rows
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c <= BOARD_SIZE - WIN_LENGTH; c++) {
                if (gameBoardAI_5x5[r][c] == player &&
                        gameBoardAI_5x5[r][c + 1] == player &&
                        gameBoardAI_5x5[r][c + 2] == player &&
                        gameBoardAI_5x5[r][c + 3] == player) {
                    return true;
                }
            }
        }

        // Check columns
        for (int c = 0; c < BOARD_SIZE; c++) {
            for (int r = 0; r <= BOARD_SIZE - WIN_LENGTH; r++) {
                if (gameBoardAI_5x5[r][c] == player &&
                        gameBoardAI_5x5[r + 1][c] == player &&
                        gameBoardAI_5x5[r + 2][c] == player &&
                        gameBoardAI_5x5[r + 3][c] == player) {
                    return true;
                }
            }
        }

        // Check diagonals
        for (int r = 0; r <= BOARD_SIZE - WIN_LENGTH; r++) {
            for (int c = 0; c <= BOARD_SIZE - WIN_LENGTH; c++) {
                if (gameBoardAI_5x5[r][c] == player &&
                        gameBoardAI_5x5[r + 1][c + 1] == player &&
                        gameBoardAI_5x5[r + 2][c + 2] == player &&
                        gameBoardAI_5x5[r + 3][c + 3] == player) {
                    return true;
                }
            }
        }

        for (int r = 0; r <= BOARD_SIZE - WIN_LENGTH; r++) {
            for (int c = WIN_LENGTH - 1; c < BOARD_SIZE; c++) {
                if (gameBoardAI_5x5[r][c] == player &&
                        gameBoardAI_5x5[r + 1][c - 1] == player &&
                        gameBoardAI_5x5[r + 2][c - 2] == player &&
                        gameBoardAI_5x5[r + 3][c - 3] == player) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Minimax algorithm with alpha-beta pruning
     */
    private int minimax(int depth, boolean isMaximizing, int alpha, int beta) {
        // Check terminal states
        if (isWinningMove(AI)) return 1000 - depth;
        if (isWinningMove(PLAYER)) return -1000 + depth;
        if (isBoardFull() || depth >= maxDepth) return evaluateBoard();

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (int r = 0; r < BOARD_SIZE && maxEval < beta; r++) {
                for (int c = 0; c < BOARD_SIZE && maxEval < beta; c++) {
                    if (gameBoardAI_5x5[r][c] == EMPTY) {
                        gameBoardAI_5x5[r][c] = AI;
                        int eval = minimax(depth + 1, false, alpha, beta);
                        gameBoardAI_5x5[r][c] = EMPTY;
                        maxEval = Math.max(maxEval, eval);
                        alpha = Math.max(alpha, eval);
                        if (beta <= alpha) break;
                    }
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int r = 0; r < BOARD_SIZE && minEval > alpha; r++) {
                for (int c = 0; c < BOARD_SIZE && minEval > alpha; c++) {
                    if (gameBoardAI_5x5[r][c] == EMPTY) {
                        gameBoardAI_5x5[r][c] = PLAYER;
                        int eval = minimax(depth + 1, true, alpha, beta);
                        gameBoardAI_5x5[r][c] = EMPTY;
                        minEval = Math.min(minEval, eval);
                        beta = Math.min(beta, eval);
                        if (beta <= alpha) break;
                    }
                }
            }
            return minEval;
        }
    }

    /**
     * Evaluates the current board state for non-terminal positions
     */
    private int evaluateBoard() {
        int score = 0;

        // Evaluate all possible 4-in-a-row combinations
        score += evaluateLines(AI) - evaluateLines(PLAYER);

        return score;
    }

    /**
     * Evaluates all lines for a specific player
     */
    private int evaluateLines(int player) {
        int score = 0;

        // Check rows
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c <= BOARD_SIZE - WIN_LENGTH; c++) {
                score += evaluateLine(player, r, c, 0, 1); // horizontal
            }
        }

        // Check columns
        for (int c = 0; c < BOARD_SIZE; c++) {
            for (int r = 0; r <= BOARD_SIZE - WIN_LENGTH; r++) {
                score += evaluateLine(player, r, c, 1, 0); // vertical
            }
        }

        // Check diagonals
        for (int r = 0; r <= BOARD_SIZE - WIN_LENGTH; r++) {
            for (int c = 0; c <= BOARD_SIZE - WIN_LENGTH; c++) {
                score += evaluateLine(player, r, c, 1, 1); // diagonal \
            }
        }

        for (int r = 0; r <= BOARD_SIZE - WIN_LENGTH; r++) {
            for (int c = WIN_LENGTH - 1; c < BOARD_SIZE; c++) {
                score += evaluateLine(player, r, c, 1, -1); // diagonal /
            }
        }

        return score;
    }

    /**
     * Evaluates a specific line of 4 positions
     */
    private int evaluateLine(int player, int row, int col, int deltaRow, int deltaCol) {
        int playerCount = 0;
        int opponentCount = 0;
        int opponent = (player == AI) ? PLAYER : AI;

        for (int i = 0; i < WIN_LENGTH; i++) {
            int r = row + i * deltaRow;
            int c = col + i * deltaCol;

            if (gameBoardAI_5x5[r][c] == player) {
                playerCount++;
            } else if (gameBoardAI_5x5[r][c] == opponent) {
                opponentCount++;
            }
        }

        // If opponent has pieces in this line, it's blocked
        if (opponentCount > 0) return 0;

        // Score based on how many pieces the player has in this line
        switch (playerCount) {
            case 4: return 1000; // Should not happen (would be caught as win)
            case 3: return 100;
            case 2: return 10;
            case 1: return 1;
            default: return 0;
        }
    }

    /**
     * Resets the game to initial state
     */
    public void resetGameAI_5x5() {
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                gameBoardAI_5x5[r][c] = EMPTY;
            }
        }
        currentPlayer = PLAYER;
        gameOver = false;
        winType = null;

        // Hide buttons and reset display
        if (playAgainBTNAI_5x5 != null) {
            playAgainBTNAI_5x5.setVisibility(View.GONE);
        }
        if (homeBTNAI_5x5 != null) {
            homeBTNAI_5x5.setVisibility(View.GONE);
        }
        if (playerTurnAI_5x5 != null) {
            playerTurnAI_5x5.setText(playerNamesAI_5x5[0] + "'s Turn");
        }
    }

    // Getters and Setters
    public void setPlayAgainBTNAI_5x5(Button playAgainBTNAI_5x5) {
        this.playAgainBTNAI_5x5 = playAgainBTNAI_5x5;
    }

    public void setHomeBTNAI_5x5(Button homeBTNAI_5x5) {
        this.homeBTNAI_5x5 = homeBTNAI_5x5;
    }

    public void setPlayerTurnAI_5x5(TextView playerTurnAI_5x5) {
        this.playerTurnAI_5x5 = playerTurnAI_5x5;
    }

    public void setPlayerNamesAI_5x5(String[] playerNamesAI_5x5) {
        if (playerNamesAI_5x5 != null && playerNamesAI_5x5.length >= 2) {
            this.playerNamesAI_5x5 = playerNamesAI_5x5;
        }
    }

    public int[][] getGameBoardAI_5x5() {
        return gameBoardAI_5x5;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
        updatePlayerTurnDisplay();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int[] getWinType() {
        return winType;
    }

    // Legacy compatibility methods
    public void setPlayer(int player) {
        this.currentPlayer = player;
        updatePlayerTurnDisplay();
    }

    public int getPlayer() {
        return currentPlayer;
    }
}