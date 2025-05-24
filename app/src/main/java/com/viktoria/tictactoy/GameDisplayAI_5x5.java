package com.viktoria.tictactoy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class GameDisplayAI_5x5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.game_display_ai5x5);

    }

    public void playAgainButtonClick_5x5AI(View view){
        // do fancy stuff
    }
    public void homeButtonClick_5x5AI(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}