package no.woact.pg4600.assignment2.ronesp13.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import no.woact.pg4600.assignment2.ronesp13.R;
import no.woact.pg4600.assignment2.ronesp13.db.DB;
import no.woact.pg4600.assignment2.ronesp13.db.HighscoreDAO;
import no.woact.pg4600.assignment2.ronesp13.views.HighscoreAdapter;

public class HighscoreActivity extends Activity {

    private static final int DISPLAY_MAX_HIGHSCORES = 7;
    private Context context;
    private ListView highscoresList;
    private Button newGameButton;
    private Button exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscore);
        context = this;

        initDB();
        initWidgets();
        initAdapters();
        initListeners();
    }

    private void initDB() {
        DB.highscoreDAO = new HighscoreDAO(this).open();
    }

    private void initWidgets() {
        highscoresList = (ListView) findViewById(R.id.highscores_list);
        exitButton = (Button) findViewById(R.id.quit_button);
        newGameButton = (Button) findViewById(R.id.new_game_button);
    }

    private void initAdapters() {
        highscoresList.setAdapter(new HighscoreAdapter(this, R.layout.custom_list_item, R.id.label, getScores()));
    }

    private void initListeners() {
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private List<String> getScores() {
        List<String> elements = new ArrayList<>();
        Cursor cursor = DB.highscoreDAO.getAll();
        while (cursor.moveToNext()) {
            elements.add(cursor.getString(cursor.getColumnIndex(HighscoreDAO.NAME_ATTR)) + " - " + cursor.getInt(cursor.getColumnIndex(HighscoreDAO.SCORE_ATTR)));
        }
        cursor.close();
        return elements.subList(0, elements.size() < DISPLAY_MAX_HIGHSCORES ? elements.size() : DISPLAY_MAX_HIGHSCORES);
    }
}
