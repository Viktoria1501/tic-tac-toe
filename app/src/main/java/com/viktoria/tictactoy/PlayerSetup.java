package com.viktoria.tictactoy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class PlayerSetup extends AppCompatActivity {
    private EditText player1, player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_setup);

        player1 = findViewById(R.id.Player1Name);
        player2 = findViewById(R.id.Player2Name);
    }

    public void submitButtonClick(View view) {
        String player1Name = player1.getText().toString().trim();
        String player2Name = player2.getText().toString().trim();

        // Set default names if empty
        if (player1Name.isEmpty()) player1Name = "Player 1";
        if (player2Name.isEmpty()) player2Name = "Player 2";

        Intent intent = new Intent(this, chooseBoard.class);

        // Pass player names as regular strings
        intent.putExtra("PLAYER_1_NAME", player1Name);
        intent.putExtra("PLAYER_2_NAME", player2Name);
        startActivity(intent);
    }
}