package com.viktoria.tictactoy;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Handles the game logic for AI 3x3 Tic Tac Toe
 * Includes Minimax algorithm for AI decisions with configurable skill level
 */
public class GameLogicAI_3x3 {
    // Constants for board states
    public static final int EMPTY = 0;
    public static final int PLAYER = 1;
    public static final int AI = 2;

    private int[][] gameBoard;
    private int currentPlayer = PLAYER;
    private Button playAgainBtn;
    private Button homeBtn;
    private TextView playerTurnDisplay;
    // Hardcoded player names - Player always starts, AI continues
    private String[] playerNames = {"Player", "AI"};
    private int[] winType; // Stores information about winning line
    private boolean gameOver = false;

    // AI difficulty settings
    private final Random random = new Random();
    private final float aiMistakeRate = 0.40f; // 40% chance AI will make a suboptimal move
    private final float playerWinRate = 0.25f; // Target 25% win rate for player

    /**
     * Constructor initializes the game board
     */
    public GameLogicAI_3x3() {
        gameBoard = new int[3][3];
        resetGame();
    }

    /**
     * Updates the game board with the current player's move
     *
     * @param row The row of the move (1-3)
     * @param col The column of the move (1-3)
     * @return true if the move was valid and made, false otherwise
     */
    public boolean updateGameBoard(int row, int col) {
        // Check if game is over or move is out of bounds
        if (gameOver || row < 1 || row > 3 || col < 1 || col > 3) {
            return false;
        }

        // Convert from 1-based to 0-based indices
        int boardRow = row - 1;
        int boardCol = col - 1;

        // Check if cell is empty
        if (gameBoard[boardRow][boardCol] == EMPTY) {
            // Make the move
            gameBoard[boardRow][boardCol] = currentPlayer;

            // Update the player turn display
            updatePlayerTurnDisplay();

            return true;
        }

        return false;
    }

    /**
     * Updates the player turn display text
     */
    private void updatePlayerTurnDisplay() {
        if (playerTurnDisplay != null) {
            if (currentPlayer == PLAYER) {
                playerTurnDisplay.setText(playerNames[1] + "'s Turn");
            } else {
                playerTurnDisplay.setText(playerNames[0] + "'s Turn");
            }
        }
    }

    /**
     * Checks if there's a winner or tie
     *
     * @return true if game is over (win or tie), false otherwise
     */
    public boolean checkForWinOrTie() {
        // Check for winner
        if (checkForWinner()) {
            gameOver = true;
            String winnerName = (currentPlayer == PLAYER) ? playerNames[0] : playerNames[1];
            showGameOver(winnerName + " Won!!!");
            return true;
        }

        // Check for tie
        if (isBoardFull()) {
            gameOver = true;
            showGameOver("Tie Game!!!");
            return true;
        }

        return false;
    }

    /**
     * Checks all possible win conditions
     *
     * @return true if there's a winner, false otherwise
     */
    private boolean checkForWinner() {
        // Check rows
        for (int r = 0; r < 3; r++) {
            if (gameBoard[r][0] != EMPTY &&
                    gameBoard[r][0] == gameBoard[r][1] &&
                    gameBoard[r][0] == gameBoard[r][2]) {
                winType = new int[]{r, 0, 1}; // Row win
                return true;
            }
        }

        // Check columns
        for (int c = 0; c < 3; c++) {
            if (gameBoard[0][c] != EMPTY &&
                    gameBoard[0][c] == gameBoard[1][c] &&
                    gameBoard[0][c] == gameBoard[2][c]) {
                winType = new int[]{0, c, 2}; // Column win
                return true;
            }
        }

        // Check diagonal (top-left to bottom-right)
        if (gameBoard[0][0] != EMPTY &&
                gameBoard[0][0] == gameBoard[1][1] &&
                gameBoard[0][0] == gameBoard[2][2]) {
            winType = new int[]{0, 2, 3}; // Diagonal win (top-left to bottom-right)
            return true;
        }

        // Check diagonal (bottom-left to top-right)
        if (gameBoard[2][0] != EMPTY &&
                gameBoard[2][0] == gameBoard[1][1] &&
                gameBoard[2][0] == gameBoard[0][2]) {
            winType = new int[]{2, 2, 4}; // Diagonal win (bottom-left to top-right)
            return true;
        }

        return false;
    }

    /**
     * Checks if the board is full (tie condition)
     *
     * @return true if board is full, false otherwise
     */
    private boolean isBoardFull() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (gameBoard[r][c] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Shows game over state with result message
     *
     * @param message Result message to display
     */
    private void showGameOver(String message) {
        if (playAgainBtn != null) {
            playAgainBtn.setVisibility(View.VISIBLE);
        }
        if (homeBtn != null) {
            homeBtn.setVisibility(View.VISIBLE);
        }
        if (playerTurnDisplay != null) {
            playerTurnDisplay.setText(message);
        }
    }

    /**
     * Resets the game to initial state
     */
    public void resetGame() {
        // Clear the board
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                gameBoard[r][c] = EMPTY;
            }
        }

        // Reset game state
        currentPlayer = PLAYER;
        gameOver = false;

        // Hide buttons and update display
        if (playAgainBtn != null) {
            playAgainBtn.setVisibility(View.GONE);
        }
        if (homeBtn != null) {
            homeBtn.setVisibility(View.GONE);
        }
        if (playerTurnDisplay != null) {
            playerTurnDisplay.setText(playerNames[0] + "'s Turn");
        }
    }

    /**
     * Switches the current player
     */
    public void switchPlayer() {
        currentPlayer = (currentPlayer == PLAYER) ? AI : PLAYER;
    }

    /**
     * Make the AI move using the Minimax algorithm with configurable difficulty
     * @return true if a move was made, false otherwise
     */
    public boolean makeAIMove() {
        // Check if game is already over
        if (gameOver) {
            return false;
        }

        // Get current game state
        int[][] boardCopy = new int[3][3];
        for (int r = 0; r < 3; r++) {
            System.arraycopy(gameBoard[r], 0, boardCopy[r], 0, 3);
        }

        // Use minimax to find best move
        if (random.nextFloat() < aiMistakeRate && !isFirstMove()) {
            // Make a random move instead of optimal one based on configured mistake rate
            List<int[]> availableMoves = getAvailableMoves();

            if (!availableMoves.isEmpty()) {
                int[] randomMove = availableMoves.get(random.nextInt(availableMoves.size()));
                // Convert from 0-based to 1-based indices
                return updateGameBoard(randomMove[0] + 1, randomMove[1] + 1);
            }
        } else {
            // Use minimax to find optimal move
            int[] bestMove = findBestMove();
            if (bestMove != null) {
                // Convert from 0-based to 1-based indices
                return updateGameBoard(bestMove[0] + 1, bestMove[1] + 1);
            }
        }

        return false;
    }

    /**
     * Check if this is the first move of the game
     * @return true if the board is empty except for one player move
     */
    private boolean isFirstMove() {
        int count = 0;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (gameBoard[r][c] != EMPTY) {
                    count++;
                }
            }
        }
        return count <= 1;
    }

    /**
     * Get list of all available moves
     * @return List of row,column pairs for empty cells
     */
    private List<int[]> getAvailableMoves() {
        List<int[]> moves = new ArrayList<>();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (gameBoard[r][c] == EMPTY) {
                    moves.add(new int[]{r, c});
                }
            }
        }
        return moves;
    }

    /**
     * Find the best move for AI using minimax algorithm
     * @return array [row, col] of best move (0-based indices)
     */
    private int[] findBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = null;

        // Try all possible moves
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                // Check if cell is empty
                if (gameBoard[r][c] == EMPTY) {
                    // Make the move
                    gameBoard[r][c] = AI;

                    // Compute score for this move
                    int score = minimax(gameBoard, 0, false);

                    // Undo the move
                    gameBoard[r][c] = EMPTY;

                    // Update best move if this is better
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new int[]{r, c};
                    }
                }
            }
        }

        return bestMove;
    }

    /**
     * Minimax algorithm implementation for finding optimal moves
     * @param board Current game board state
     * @param depth Current depth in game tree
     * @param isMaximizing Whether current player is maximizing (AI) or minimizing (Player)
     * @return Best score for the current board state
     */
    private int minimax(int[][] board, int depth, boolean isMaximizing) {
        // Check for terminal states
        Integer score = evaluateBoard(board);
        if (score != null) {
            return score;
        }

        // If AI's turn (maximizing)
        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    if (board[r][c] == EMPTY) {
                        board[r][c] = AI;
                        bestScore = Math.max(bestScore, minimax(board, depth + 1, false));
                        board[r][c] = EMPTY;
                    }
                }
            }
            return bestScore;
        }
        // If Player's turn (minimizing)
        else {
            int bestScore = Integer.MAX_VALUE;
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    if (board[r][c] == EMPTY) {
                        board[r][c] = PLAYER;
                        bestScore = Math.min(bestScore, minimax(board, depth + 1, true));
                        board[r][c] = EMPTY;
                    }
                }
            }
            return bestScore;
        }
    }

    /**
     * Evaluate the current board state
     * @param board Current board state
     * @return Score for terminal states (win/loss/tie), null for non-terminal states
     */
    private Integer evaluateBoard(int[][] board) {
        // Check rows for win
        for (int r = 0; r < 3; r++) {
            if (board[r][0] != EMPTY && board[r][0] == board[r][1] && board[r][0] == board[r][2]) {
                if (board[r][0] == AI) return 10;
                if (board[r][0] == PLAYER) return -10;
            }
        }

        // Check columns for win
        for (int c = 0; c < 3; c++) {
            if (board[0][c] != EMPTY && board[0][c] == board[1][c] && board[0][c] == board[2][c]) {
                if (board[0][c] == AI) return 10;
                if (board[0][c] == PLAYER) return -10;
            }
        }

        // Check diagonal (top-left to bottom-right)
        if (board[0][0] != EMPTY && board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
            if (board[0][0] == AI) return 10;
            if (board[0][0] == PLAYER) return -10;
        }

        // Check diagonal (bottom-left to top-right)
        if (board[2][0] != EMPTY && board[2][0] == board[1][1] && board[2][0] == board[0][2]) {
            if (board[2][0] == AI) return 10;
            if (board[2][0] == PLAYER) return -10;
        }

        // Check if board is full (tie)
        boolean isFull = true;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c] == EMPTY) {
                    isFull = false;
                    break;
                }
            }
            if (!isFull) break;
        }

        if (isFull) return 0; // Tie

        // Game is not over
        return null;
    }

    // Getters and setters

    public int[][] getGameBoard() {
        return gameBoard;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int player) {
        this.currentPlayer = player;
    }

    public int[] getWinType() {
        return winType;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setPlayAgainButton(Button playAgainBtn) {
        this.playAgainBtn = playAgainBtn;
    }

    public void setHomeButton(Button homeBtn) {
        this.homeBtn = homeBtn;
    }

    public void setPlayerTurnDisplay(TextView playerTurnDisplay) {
        this.playerTurnDisplay = playerTurnDisplay;
    }

    public void setPlayerNames(String[] names) {
        if (names != null && names.length >= 2) {
            this.playerNames = names;
        }
    }
}