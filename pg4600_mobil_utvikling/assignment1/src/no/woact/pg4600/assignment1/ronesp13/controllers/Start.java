package no.woact.pg4600.assignment1.ronesp13.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import no.woact.pg4600.assignment1.ronesp13.R;

public class Start extends Activity {

    private Context context;
    private Button startGame;
    private EditText playerOne;
    private EditText playerTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        context = this;
        initWidgets();
        initListeners();
    }

    private void initWidgets() {
        playerOne = (EditText) findViewById(R.id.player_one_input);
        playerTwo = (EditText) findViewById(R.id.player_two_input);
        startGame = (Button) findViewById(R.id.start_game_button);
    }

    private void initListeners() {
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Game.class);
                intent.putExtra("player_one", playerOne.getText().toString());
                intent.putExtra("player_two", playerTwo.getText().toString());
                startActivity(intent);
                finish();
            }
        });
    }
}
