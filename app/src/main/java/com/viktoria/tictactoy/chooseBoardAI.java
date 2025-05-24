package com.viktoria.tictactoy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity for choosing board size for AI game
 */
public class chooseBoardAI extends AppCompatActivity {

    // Hardcoded player names
    private final String playerName = "Player";
    private final String aiName = "AI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.choose_board_ai);

        // Optional: Add logging to confirm activity started properly
        System.out.println("chooseBoardAI activity created successfully");

        // Optional: Find and set up the 3x3 button directly
        Button btn3x3 = findViewById(R.id.button4AI); // Use your actual button ID
        if (btn3x3 != null) {
            btn3x3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submitButtonAI3x3Click(v);
                }
            });
        }
    }

    /**
     * Start 3x3 game with AI (onClick handler for submit button)
     */
    public void submitButtonAI3x3Click(View view) {
        // Log before starting activity
        System.out.println("Starting GameDisplayAI_3x3 activity");

        try {
            // Start the game activity with hardcoded player names
            Intent intent = new Intent(chooseBoardAI.this, GameDisplayAI_3x3.class);
            intent.putExtra("PLAYER_NAMES", new String[]{playerName, aiName});
            startActivity(intent);
        } catch (Exception e) {
            // Log any errors
            System.err.println("Error starting GameDisplayAI_3x3: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void submitButtonAI5x5Click(View view){
        System.out.println("Starting GameDisplayAI_5x5 activity");

        try {
            // Start the game activity with hardcoded player names
            Intent intent = new Intent(chooseBoardAI.this, GameDisplayAI_5x5.class);
            intent.putExtra("PLAYER_NAMES", new String[]{playerName, aiName});
            startActivity(intent);
        } catch (Exception e) {
            // Log any errors
            System.err.println("Error starting GameDisplayAI_5x5: " + e.getMessage());
            e.printStackTrace();
        }
    }
}