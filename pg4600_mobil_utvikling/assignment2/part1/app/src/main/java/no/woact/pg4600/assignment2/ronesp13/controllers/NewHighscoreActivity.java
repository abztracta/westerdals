package no.woact.pg4600.assignment2.ronesp13.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import no.woact.pg4600.assignment2.ronesp13.R;
import no.woact.pg4600.assignment2.ronesp13.db.DB;
import no.woact.pg4600.assignment2.ronesp13.db.HighscoreDAO;

public class NewHighscoreActivity extends Activity {

    private int score;
    private Context context;
    private EditText name;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_highscore);
        context = this;

        initDB();
        initWidgets();
        initListeners();
        readExtras();
    }

    private void readExtras() {
        score = getIntent().getIntExtra("score", 0);
    }

    private void initDB() {
        DB.highscoreDAO = new HighscoreDAO(this).open();
    }

    private void initWidgets() {
        name = (EditText) findViewById(R.id.highscorer);
        submitButton = (Button) findViewById(R.id.confirm_name);
    }

    private void addNewHighscore(String name, int score) {
        DB.highscoreDAO.addHighscore(name, score);
    }

    private void initListeners() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = name.getText().toString();
                addNewHighscore(value, score);
                Intent intent = new Intent(context, HighscoreActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
