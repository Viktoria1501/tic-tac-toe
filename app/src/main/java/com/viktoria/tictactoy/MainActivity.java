
package com.viktoria.tictactoy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Main entry point of the application.
 * Provides navigation to different game modes.
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "MainActivity created");
    }

    /**
     * Handler for Play vs AI button
     */
    public void playVsAIButtonClick(View view) {
        Log.d(TAG, "Play vs AI button clicked");
        Intent intent = new Intent(this, chooseBoardAI.class);
        startActivity(intent);
    }

    public void playButtonClick(View view){
        Intent intent = new Intent(this, PlayerSetup.class);
        startActivity(intent);

    }
    public void playButtonClickAI(View view){
        Intent intent = new Intent(this, chooseBoardAI.class);
        startActivity(intent);

    }


    // Other navigation methods as needed for your app...
}