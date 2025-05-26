package com.viktoria.tictactoy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class GameDisplayAI_5x5 extends AppCompatActivity {
    private TicTacToyBoardAI_5x5 ticTacToyBoardAI5x5;
    private Handler aiHandler;
    private static final int AI_DELAY_MS = 1000; // 1 second delay for AI move

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.game_display_ai5x5);

        // Initialize UI components
        ticTacToyBoardAI5x5 = findViewById(R.id.ticTacToyBoardAI_5x5);
        Button playAgainBTNAI_5x5 = findViewById(R.id.button10);
        Button homeBTNAI_5x5 = findViewById(R.id.button11);
        TextView playerTurnAI_5x5 = findViewById(R.id.textView9);

        // Get player names from intent
        String[] playerNamesAI_5x5 = getIntent().getStringArrayExtra("PLAYER_NAMES");
        if (playerNamesAI_5x5 == null) {
            // Default names if none provided
            playerNamesAI_5x5 = new String[]{"Player", "AI"};
        }

        // Hide buttons initially
        playAgainBTNAI_5x5.setVisibility(View.GONE);
        homeBTNAI_5x5.setVisibility(View.GONE);

        // Initialize handler for AI moves
        aiHandler = new Handler(Looper.getMainLooper());

        // Set up the game
        ticTacToyBoardAI5x5.setUpGame(playAgainBTNAI_5x5, homeBTNAI_5x5, playerTurnAI_5x5, playerNamesAI_5x5);

        // Set callback for when player makes a move
        ticTacToyBoardAI5x5.setOnPlayerMoveListener(new TicTacToyBoardAI_5x5.OnPlayerMoveListener() {
            @Override
            public void onPlayerMove() {
                handlePlayerMove();
            }
        });
    }

    /**
     * Handles the logic after a player makes a move
     */
    private void handlePlayerMove() {
        // Check for game end conditions first
        if (ticTacToyBoardAI5x5.checkGameEnd()) {
            return; // Game is over, no AI move needed
        }

        // Switch to AI turn and make AI move after a short delay
        ticTacToyBoardAI5x5.switchToAI();

        aiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                makeAIMove();
            }
        }, AI_DELAY_MS);
    }

    /**
     * Makes the AI move and handles the aftermath
     */
    private void makeAIMove() {
        if (ticTacToyBoardAI5x5.makeAIMove()) {
            ticTacToyBoardAI5x5.invalidate(); // Redraw the board

            // Check if AI won or game tied
            if (!ticTacToyBoardAI5x5.checkGameEnd()) {
                // Switch back to player turn
                ticTacToyBoardAI5x5.switchToPlayer();
            }
        }
    }

    /**
     * Play again button click handler
     */
    public void playAgainButtonClick_5x5AI(View view) {
        // Cancel any pending AI moves
        aiHandler.removeCallbacksAndMessages(null);

        // Reset the game
        ticTacToyBoardAI5x5.resetGameAI_5x5();
        ticTacToyBoardAI5x5.invalidate();
    }

    /**
     * Home button click handler
     */
    public void homeButtonClick_5x5AI(View view) {
        // Cancel any pending AI moves
        aiHandler.removeCallbacksAndMessages(null);

        // Return to main activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Close this activity
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clean up handler to prevent memory leaks
        if (aiHandler != null) {
            aiHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pause AI moves when activity is not visible
        if (aiHandler != null) {
            aiHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Resume AI logic if it's AI's turn
        if (ticTacToyBoardAI5x5 != null && ticTacToyBoardAI5x5.isAITurn() && !ticTacToyBoardAI5x5.isGameOver()) {
            aiHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    makeAIMove();
                }
            }, AI_DELAY_MS);
        }
    }
}