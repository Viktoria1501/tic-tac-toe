package com.viktoria.tictactoy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

public class GameDisplay_5x5 extends AppCompatActivity {

    private TicTacToyBoard_5x5 ticTacToyBoard_5x5;
    private Button playAgainButton;
    private Button homeButton;
    private TextView playerTurnTextView;
    private static final String TAG = "GameDisplay_5x5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_display5x5);
        Log.d(TAG, "onCreate called");

        // Initialize UI elements
        try {
            playAgainButton = findViewById(R.id.button6);
            homeButton = findViewById(R.id.button9);
            playerTurnTextView = findViewById(R.id.textView7);

            if (playAgainButton == null) Log.e(TAG, "Play Again button not found!");
            if (homeButton == null) Log.e(TAG, "Home button not found!");
            if (playerTurnTextView == null) Log.e(TAG, "Player turn TextView not found!");

        } catch (Exception e) {
            Log.e(TAG, "Error initializing UI elements", e);
        }

        // Get player names from intent
        String[] playerNames_5x5;
        if (getIntent() != null && getIntent().hasExtra("PLAYER_NAMES_5x5")) {
            playerNames_5x5 = getIntent().getStringArrayExtra("PLAYER_NAMES_5x5");
            Log.d(TAG, "Received player names from intent: " +
                    (playerNames_5x5 != null ?
                            playerNames_5x5[0] + ", " + playerNames_5x5[1] : "null"));
        } else {
            // Default player names if not provided
            playerNames_5x5 = new String[]{"Player 1", "Player 2"};
            Log.d(TAG, "Using default player names");
        }

        // Safety check for player names
        if (playerNames_5x5 == null || playerNames_5x5.length < 2) {
            playerNames_5x5 = new String[]{"Player 1", "Player 2"};
            Log.w(TAG, "Invalid player names array, using defaults");
        }

        // Find and initialize the TicTacToyBoard view
        try {
            ticTacToyBoard_5x5 = findViewById(R.id.ticTacToyBoard_5x5);
            if (ticTacToyBoard_5x5 == null) {
                Log.e(TAG, "Failed to find ticTacToyBoard_5x5 view!");
            } else {
                Log.d(TAG, "Successfully found ticTacToyBoard_5x5 view");
                // Initialize the game
                ticTacToyBoard_5x5.setUpGame_5x5(playAgainButton, homeButton, playerTurnTextView, playerNames_5x5);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error finding or initializing TicTacToyBoard view", e);
        }

        // Set up play again button click listener
        if (playAgainButton != null) {
            playAgainButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Play Again button clicked");
                    resetGame();
                    playAgainButton.setVisibility(View.GONE);
                    homeButton.setVisibility(View.GONE);
                }
            });
            // Initially hide the button (will show after game ends)
            playAgainButton.setVisibility(View.GONE);
        }

        // Set up home button click listener
        if (homeButton != null) {
            homeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Home button clicked");
                    homeButtonClick_5x5();
                    playAgainButton.setVisibility(View.GONE);
                    homeButton.setVisibility(View.GONE);
                }
            });
            // Initially hide the button (will show after game ends)
            homeButton.setVisibility(View.GONE);
        }

        // Initialize player turn display
        if (playerTurnTextView != null && playerNames_5x5 != null && playerNames_5x5.length > 0) {
            playerTurnTextView.setText(playerNames_5x5[0] + "'s Turn");
        }
    }

    // Method to reset the game
    private void resetGame() {
        if (ticTacToyBoard_5x5 != null) {
            ticTacToyBoard_5x5.resetGame_5x5();
            ticTacToyBoard_5x5.invalidate(); // Force redraw
            Log.d(TAG, "Game reset successfully");
        } else {
            Log.e(TAG, "Cannot reset game - ticTacToyBoard_5x5 is null");
        }
    }

    // XML onClick handler for Play Again button
    public void playAgainButtonClick(View view) {
        Log.d(TAG, "playAgainButtonClick method called via XML");
        resetGame();
    }

    // Home button click handler
    private void homeButtonClick_5x5() {
        Log.d(TAG, "Returning to main menu");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Close this activity to prevent stacking
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause called");
    }
}