package com.viktoria.tictactoy;

public class GameLogicAI_5x5 {
    private int[][]  gameBoardAI_5x5;
    private int  player = 1;
    GameLogicAI_5x5(){
        gameBoardAI_5x5 = new int[5][5];
        for(int r = 0; r < 5; r ++){
            for(int c = 0; c < 5; c++){
                gameBoardAI_5x5[r][c] = 0;
            }
        }

    }

    public boolean updateGameBoardAI_5x5(int row, int col){
        if(gameBoardAI_5x5[row -1][col - 1] == 0){
            gameBoardAI_5x5[row-1][col - 1] = player;
            return true;
        }else{
            return false;
        }
    }

    public int[][] getGameBoardAI_5x5() {
        return gameBoardAI_5x5;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getPlayer() {
        return player;
    }
}
