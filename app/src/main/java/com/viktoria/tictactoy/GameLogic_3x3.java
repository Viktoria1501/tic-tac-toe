
package com.viktoria.tictactoy;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameLogic_3x3 {
    private int[][] gameBoard_3x3;

    // 1st element --> row, 2nd element --> col, 3rd element --> line type
    private int[] winType = {-1, -1, -1};

    // Default names
    private String player1Name = "Player 1";
    private String player2Name = "Player 2";

    private Button playAgainBTN_3x3;
    private Button homeBTN_3x3;

    private TextView playerTurn;

    private int player = 1;

    GameLogic_3x3() {
        gameBoard_3x3 = new int[3][3];
        resetBoard();
    }

    private void resetBoard() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                gameBoard_3x3[r][c] = 0;
            }
        }
    }

    public boolean updateGameBoard_3x3(int row, int col) {
        // Validate inputs
        if (row < 1 || row > 3 || col < 1 || col > 3) {
            return false;
        }

        if (gameBoard_3x3[row - 1][col - 1] == 0) {
            gameBoard_3x3[row - 1][col - 1] = player;

            // Update player turn text
            if (player == 1) {
                playerTurn.setText(player2Name + "'s Turn");
            } else {
                playerTurn.setText(player1Name + "'s Turn");
            }

            return true;
        } else {
            return false;
        }
    }

    public void setPlayAgainBTN_3x3(Button playAgainBTN_3x3) {
        this.playAgainBTN_3x3 = playAgainBTN_3x3;
    }

    public void setHomeBTN_3x3(Button homeBTN_3x3) {
        this.homeBTN_3x3 = homeBTN_3x3;
    }

    public void setPlayerTurn(TextView playerTurn) {
        this.playerTurn = playerTurn;
    }

    public void setPlayerNames(String player1, String player2) {
        this.player1Name = player1;
        this.player2Name = player2;

        // Update display if playerTurn is set
        if (playerTurn != null) {
            playerTurn.setText(player1Name + "'s Turn");
        }
    }

    public int[][] getGameBoard_3x3() {
        return gameBoard_3x3;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getPlayer() {
        return player;
    }

    public int[] getWinType(){
        return winType;
    }

    public boolean winnerCheck() {
        boolean isWinner = false;

        // Check rows
        for (int r = 0; r < 3; r++) {
            if (gameBoard_3x3[r][0] == gameBoard_3x3[r][1] &&
                    gameBoard_3x3[r][0] == gameBoard_3x3[r][2] &&
                    gameBoard_3x3[r][0] != 0) {
                winType = new int[] {r, 0, 1};
                isWinner = true;
            }
        }

        // Check columns - FIXED: now checking gameBoard_3x3[0][c] != 0 instead of gameBoard_3x3[0][0] != 0
        for (int c = 0; c < 3; c++) {
            if (gameBoard_3x3[0][c] == gameBoard_3x3[1][c] &&
                    gameBoard_3x3[0][c] == gameBoard_3x3[2][c] &&
                    gameBoard_3x3[0][c] != 0) {
                winType = new int[] {0, c, 2};
                isWinner = true;
            }
        }

        // Check diagonals
        if (gameBoard_3x3[0][0] == gameBoard_3x3[1][1] &&
                gameBoard_3x3[0][0] == gameBoard_3x3[2][2] &&
                gameBoard_3x3[0][0] != 0) {
            winType = new int[] {0, 2, 3};
            isWinner = true;
        }

        if (gameBoard_3x3[2][0] == gameBoard_3x3[1][1] &&
                gameBoard_3x3[2][0] == gameBoard_3x3[0][2] &&
                gameBoard_3x3[2][0] != 0) {
            winType = new int[] {2, 2, 4};
            isWinner = true;
        }

        // Check for a tie
        int boardFilled = 0;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (gameBoard_3x3[r][c] != 0) {
                    boardFilled += 1;
                }
            }
        }
        if (isWinner) {
            String winnerName = (player == 1) ? player1Name : player2Name;
            playAgainBTN_3x3.setVisibility(View.VISIBLE);
            homeBTN_3x3.setVisibility(View.VISIBLE);
            playerTurn.setText(winnerName + " Won!!!");
            return true;
        } else if (boardFilled == 9) {
            playAgainBTN_3x3.setVisibility(View.VISIBLE);
            homeBTN_3x3.setVisibility(View.VISIBLE);
            playerTurn.setText("Tie Game!!!");
            return false;
        } else {
            return false;
        }
    }

    public void resetGame() {
        resetBoard();
        player = 1;

        playAgainBTN_3x3.setVisibility(View.GONE);
        homeBTN_3x3.setVisibility(View.GONE);

        playerTurn.setText(player1Name + "'s Turn");
    }

    public boolean isTie() {
        if (winType[2] != -1) {
            return false;
        }

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (gameBoard_3x3[r][c] == 0) {
                    return false;
                }
            }
        }

        return true;
    }
}