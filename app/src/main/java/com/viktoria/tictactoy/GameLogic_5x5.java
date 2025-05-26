package com.viktoria.tictactoy;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;


public class GameLogic_5x5 {
    private static final String TAG = "GameLogic_5x5";

    private Button playAgainBTN_5x5;
    private Button homeBTN_5x5;
    private TextView playerTurn_5x5;
    private String[] playerNames_5x5 = {"Player 1", "Player 2"};

    private int[][] gameBoard_5x5;
    private int player_5x5 = 1;

    // 1st element --> row, 2nd element --> col, 3rd element --> line type
    private int[] winType = {-1, -1, -1};
    private int[] winCoords = null;  // Stores {startRow, startCol, endRow, endCol}


    GameLogic_5x5() {
        gameBoard_5x5 = new int[5][5];
        resetGame_5x5();
    }



    public boolean updateGameBoard(int row, int col) {
        // Bounds checking to prevent crashes
        if (row < 1 || row > 5 || col < 1 || col > 5) {
            Log.e(TAG, "Invalid position: row=" + row + ", col=" + col);
            return false;
        }


        if (gameBoard_5x5[row - 1][col - 1] == 0) {
            gameBoard_5x5[row - 1][col - 1] = player_5x5;

            // Safely update player turn text
            if (playerTurn_5x5 != null) {
                try {
                    // Check for null playerNames_5x5 array
                    if (playerNames_5x5 == null) {
                        playerNames_5x5 = new String[]{"Player 1", "Player 2"};
                    }

                    if (player_5x5 == 1) {
                        playerTurn_5x5.setText((playerNames_5x5[1] + "'s Turn"));
                    } else {
                        playerTurn_5x5.setText((playerNames_5x5[0] + "'s Turn"));
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error updating player turn text", e);
                    // Fallback text if there's an error
                    playerTurn_5x5.setText("Next player's turn");
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean winnerCheck() {
        boolean isWinner = false;

        // Check rows - we need 4 in a row for a 5x5 board
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c <= 1; c++) {
                if (gameBoard_5x5[r][c] != 0 &&
                        gameBoard_5x5[r][c] == gameBoard_5x5[r][c+1] &&
                        gameBoard_5x5[r][c] == gameBoard_5x5[r][c+2] &&
                        gameBoard_5x5[r][c] == gameBoard_5x5[r][c+3]) {
                    winType = new int[] {r, c, 1};
                    isWinner = true;
                    Log.d(TAG, "Win detected: 4 in a row at row=" + r + ", starting col=" + c);
                }
            }
        }

        // Check columns - we need 4 in a column for a 5x5 board
        for (int c = 0; c < 5; c++) {
            for (int r = 0; r <= 1; r++) {
                if (gameBoard_5x5[r][c] != 0 &&
                        gameBoard_5x5[r][c] == gameBoard_5x5[r+1][c] &&
                        gameBoard_5x5[r][c] == gameBoard_5x5[r+2][c] &&
                        gameBoard_5x5[r][c] == gameBoard_5x5[r+3][c]) {
                    winType = new int[] {r, c, 2};
                    isWinner = true;
                    Log.d(TAG, "Win detected: 4 in a column at col=" + c + ", starting row=" + r);
                }
            }
        }

        // Check diagonals - we need 4 in a diagonal for a 5x5 board
        // Diagonal from top-left to bottom-right
        for (int r = 0; r <= 1; r++) {
            for (int c = 0; c <= 1; c++) {
                if (gameBoard_5x5[r][c] != 0 &&
                        gameBoard_5x5[r][c] == gameBoard_5x5[r+1][c+1] &&
                        gameBoard_5x5[r][c] == gameBoard_5x5[r+2][c+2] &&
                        gameBoard_5x5[r][c] == gameBoard_5x5[r+3][c+3]) {
                    winType = new int[] {r, c, 3};
                    isWinner = true;
                    Log.d(TAG, "Win detected: diagonal NE-SW at starting row=" + r + ", col=" + c);
                }
            }
        }

        // Diagonal from top-right to bottom-left
        for (int r = 0; r <= 1; r++) {
            for (int c = 3; c < 5; c++) {
                if (gameBoard_5x5[r][c] != 0 &&
                        gameBoard_5x5[r][c] == gameBoard_5x5[r+1][c-1] &&
                        gameBoard_5x5[r][c] == gameBoard_5x5[r+2][c-2] &&
                        gameBoard_5x5[r][c] == gameBoard_5x5[r+3][c-3]) {
                    winType = new int[] {r, c, 4};
                    isWinner = true;
                    Log.d(TAG, "Win detected: diagonal NW-SE at starting row=" + r + ", col=" + c);
                }
            }
        }

        // Check for a tie
        int boardFilled = 0;
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                if (gameBoard_5x5[r][c] != 0) {
                    boardFilled += 1;
                }
            }
        }

        if (isWinner) {
            playAgainBTN_5x5.setVisibility(View.VISIBLE);
            homeBTN_5x5.setVisibility(View.VISIBLE);
            playerTurn_5x5.setText((playerNames_5x5[player_5x5 - 1] + " Won!!!"));
            return true;
        } else if (boardFilled == 25) {
            playAgainBTN_5x5.setVisibility(View.VISIBLE);
            homeBTN_5x5.setVisibility(View.VISIBLE);
            playerTurn_5x5.setText("Tie Game!!!");
            return true;
        } else {
            return false;
        }
    }

    public void resetGame_5x5() {
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                gameBoard_5x5[r][c] = 0;
            }
        }

        // Reset to first player and update display
        player_5x5 = 1;
        if (playerTurn_5x5 != null && playerNames_5x5 != null && playerNames_5x5.length >= 2) {
            playerTurn_5x5.setText(playerNames_5x5[0] + "'s Turn");
        }

        // Reset win type
        winType = new int[] {-1, -1, -1};
    }

    public void setPlayAgainBTN_5x5(Button playAgainBTN_5x5) {
        this.playAgainBTN_5x5 = playAgainBTN_5x5;
    }

    public void setHomeBTN_5x5(Button homeBTN_5x5) {
        this.homeBTN_5x5 = homeBTN_5x5;
    }

    public void setPlayerTurn_5x5(TextView playerTurn_5x5) {
        this.playerTurn_5x5 = playerTurn_5x5;
        // Initialize the text when the TextView is set
        if (playerTurn_5x5 != null && playerNames_5x5 != null && playerNames_5x5.length >= 1) {
            playerTurn_5x5.setText(playerNames_5x5[0] + "'s Turn");
        }
    }

    public void setPlayerNames_5x5(String[] playerNames_5x5) {
        // Defensive coding to avoid null pointer exceptions
        if (playerNames_5x5 != null && playerNames_5x5.length >= 2) {
            this.playerNames_5x5 = playerNames_5x5;
        } else {
            // Fallback to default names
            this.playerNames_5x5 = new String[]{"Player 1", "Player 2"};
            Log.w(TAG, "Invalid player names provided, using defaults");
        }

        // Update the display with the first player's name
        if (playerTurn_5x5 != null) {
            playerTurn_5x5.setText(this.playerNames_5x5[0] + "'s Turn");
        }
    }

    public int[][] getGameBoard_5x5() {
        return gameBoard_5x5;
    }

    public void setPlayer_5x5(int player_5x5) {
        this.player_5x5 = player_5x5;
    }

    public int getPlayer_5x5() {
        return player_5x5;
    }

    public int[] getWinType() {
        return winType;
    }
}