package com.viktoria.tictactoy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class chooseBoard extends AppCompatActivity {
    private String player1Name, player2Name;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_board);

        // Get player names from intent
        player1Name = getIntent().getStringExtra("PLAYER_1_NAME");
        player2Name = getIntent().getStringExtra("PLAYER_2_NAME");

        // Set default names if null
        if(player1Name == null) player1Name = "Player 1";
        if(player2Name == null) player2Name = "Player 2";
    }

    public void submitButton3x3Click(View view){
        Intent intent = new Intent(this, GameDisplay_3x3.class);
        intent.putExtra("PLAYER_1_NAME", player1Name);
        intent.putExtra("PLAYER_2_NAME", player2Name);
        startActivity(intent);
    }

    public void submitButton5x5Click(View view){
        Intent intent = new Intent(this, GameDisplay_5x5.class);
        intent.putExtra("PLAYER_1_NAME", player1Name);
        intent.putExtra("PLAYER_2_NAME", player2Name);
        startActivity(intent);
    }
}