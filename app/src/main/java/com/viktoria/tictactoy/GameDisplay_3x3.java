package com.viktoria.tictactoy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class GameDisplay_3x3 extends AppCompatActivity {

    private TicTacToyBoard_3x3 ticTacToyBoard_3x3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.game_display3x3);

        Button playAgain3x3 = findViewById(R.id.button7);
        Button homeBTN3x3 = findViewById(R.id.button8);
        TextView playerTurn3x3 = findViewById(R.id.textView6);

        playAgain3x3.setVisibility(View.GONE);
        homeBTN3x3.setVisibility(View.GONE);

        // Get player names from intent
        String player1Name = getIntent().getStringExtra("PLAYER_1_NAME");
        String player2Name = getIntent().getStringExtra("PLAYER_2_NAME");

        // Set default player names if null
        if (player1Name == null) {
            player1Name = "Player 1";
        }

        if (player2Name == null) {
            player2Name = "Player 2";
        }

        // Set initial player turn text
        playerTurn3x3.setText(player1Name + "'s Turn");

        ticTacToyBoard_3x3 = findViewById(R.id.ticTacToyBoard_3x3);
        ticTacToyBoard_3x3.setUpGame(playAgain3x3, homeBTN3x3, playerTurn3x3, player1Name, player2Name);
    }

    public void playAgainButtonClick(View view) {
        ticTacToyBoard_3x3.resetGame();
        ticTacToyBoard_3x3.invalidate();
    }

    public void homeButtonClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}