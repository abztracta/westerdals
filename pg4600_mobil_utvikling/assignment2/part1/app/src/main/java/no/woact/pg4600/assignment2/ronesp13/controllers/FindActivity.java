package no.woact.pg4600.assignment2.ronesp13.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import no.woact.pg4600.assignment2.ronesp13.R;
import no.woact.pg4600.assignment2.ronesp13.db.DB;
import no.woact.pg4600.assignment2.ronesp13.db.HighscoreDAO;
import no.woact.pg4600.assignment2.ronesp13.models.KimsGame;
import no.woact.pg4600.assignment2.ronesp13.views.KimsGameAdapter;

import static no.woact.pg4600.assignment2.ronesp13.models.KimsGame.MAX_ROUNDS;

public class FindActivity extends Activity {

    private Context context;
    private KimsGame game;
    private List<Button> findButtons;
    private ListView objectsToRemember;
    private Button exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find);
        context = this;

        initDB();
        initGame();
        initWidgets();
        initAdapters();
        initListeners();
    }

    private void initDB() {
        DB.highscoreDAO = new HighscoreDAO(this).open();
    }

    private void initGame() {
        Intent intent = getIntent();
        game = (KimsGame) intent.getSerializableExtra("kims_game");
        game.selectActivity();
    }

    private void initWidgets() {
        TextView roundLabel = (TextView) findViewById(R.id.round_number);
        String text = getString(R.string.round_number, game.getRoundNumber(), MAX_ROUNDS);
        roundLabel.setText(text);
        objectsToRemember = (ListView) findViewById(R.id.find_objects_to_remember);
        findButtons = new ArrayList<>();
        findButtons.add((Button) findViewById(R.id.find_button_1));
        findButtons.get(0).setText(game.getObjectsOnButtons().get(0).getName());
        findButtons.add((Button) findViewById(R.id.find_button_2));
        findButtons.get(1).setText(game.getObjectsOnButtons().get(1).getName());
        findButtons.add((Button) findViewById(R.id.find_button_3));
        findButtons.get(2).setText(game.getObjectsOnButtons().get(2).getName());
        exitButton = (Button) findViewById(R.id.quit_button);
    }

    private void initAdapters() {
        objectsToRemember.setAdapter(new KimsGameAdapter(this, R.layout.custom_list_item, R.id.label, game.getObjectsToRemember()));
    }


    private void initListeners() {
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        for (int i = 0; i < findButtons.size(); i++) {
            findButtons.get(i).setOnClickListener(new FindButtonListener(i));
        }
    }

    private class FindButtonListener implements View.OnClickListener {

        private int index;

        public FindButtonListener(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            game.userSelected(index);
            Intent intent = null;
            if (game.isCompletedRound()) {
                intent = new Intent(context, MainActivity.class);
                intent.putExtra("kims_game", game);
            } else if (game.isGameOver() || game.isGameCompleted()) {
                if (game.isNewHighscore()) {
                    intent = new Intent(context, NewHighscoreActivity.class);
                    intent.putExtra("score", game.getScore());
                } else {
                    intent = new Intent(context, HighscoreActivity.class);
                }
            } else {
                Log.e("error", "Something terrible has happened");
                finish();
            }
            startActivity(intent);
            finish();
        }
    }
}
