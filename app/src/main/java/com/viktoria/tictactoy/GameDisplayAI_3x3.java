package com.viktoria.tictactoy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Activity for displaying the 3x3 AI game board
 */
public class GameDisplayAI_3x3 extends AppCompatActivity {

    private TicTacToyBoardAI_3x3 tictactoyBoardAI_3x3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content view before finding any views
        EdgeToEdge.enable(this);
        setContentView(R.layout.game_display_ai3x3);

        // Find views after setting content view
        Button playAgainBTNAI = findViewById(R.id.button14);
        Button homeBTNAI = findViewById(R.id.button15);
        TextView playerTurnAI = findViewById(R.id.textView8);
        tictactoyBoardAI_3x3 = findViewById(R.id.ticTacToyBoardAI_3x3);

        // Hide buttons initially
        playAgainBTNAI.setVisibility(View.GONE);
        homeBTNAI.setVisibility(View.GONE);

        // Get player names from intent, use defaults if null
        String[] playerNames = {"Player", "AI"};
        if (getIntent().hasExtra("PLAYER_NAMES")) {
            String[] names = getIntent().getStringArrayExtra("PLAYER_NAMES");
            if (names != null && names.length >= 2) {
                playerNames = names;
            }
        }

        // Set up the game board with UI elements and player names
        tictactoyBoardAI_3x3.setUpGame(playAgainBTNAI, homeBTNAI, playerTurnAI, playerNames);

        // Set initial player turn text
        playerTurnAI.setText(playerNames[0] + "'s Turn");

        // Log to confirm activity started properly
        System.out.println("GameDisplayAI_3x3 activity created successfully");
    }

    /**
     * Handler for Play Again button click
     */
    public void playAgainButtonAIClick(View view) {
        tictactoyBoardAI_3x3.resetGame();
    }

    /**
     * Handler for Home button click
     */
    public void homeButtonAIClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Close this activity
    }
}