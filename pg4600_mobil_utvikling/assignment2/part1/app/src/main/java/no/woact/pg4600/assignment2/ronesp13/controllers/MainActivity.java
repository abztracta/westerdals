package no.woact.pg4600.assignment2.ronesp13.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import no.woact.pg4600.assignment2.ronesp13.R;
import no.woact.pg4600.assignment2.ronesp13.db.DB;
import no.woact.pg4600.assignment2.ronesp13.db.NameDAO;
import no.woact.pg4600.assignment2.ronesp13.models.KimsGame;
import no.woact.pg4600.assignment2.ronesp13.views.KimsGameAdapter;

import static no.woact.pg4600.assignment2.ronesp13.models.KimsGame.MAX_ROUNDS;

public class MainActivity extends Activity {

    private Context context;
    private KimsGame game;

    private Button startButton;
    private ListView objectsToRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = this;
        initDB();
        initGame();
        initWidgets();
        initAdapters();
        initListeners();
    }

    private void initDB() {
        DB.nameDAO = new NameDAO(this).open();
    }

    private void initGame() {
        Intent intent = getIntent();
        game = (KimsGame) intent.getSerializableExtra("kims_game");
        if (game == null) {
            game = new KimsGame();
            game.initGame();
        } else {
            game.startNewRound();
        }
    }

    private void initWidgets() {
        TextView roundLabel = (TextView) findViewById(R.id.round_number);
        String text = getString(R.string.round_number, game.getRoundNumber(), MAX_ROUNDS);
        roundLabel.setText(text);

        startButton = (Button) findViewById(R.id.ready_button);
        objectsToRemember = (ListView) findViewById(R.id.objects_to_remember);
    }

    private void initAdapters() {
        objectsToRemember.setAdapter(new KimsGameAdapter(this, R.layout.custom_list_item, R.id.label, game.getObjectsToRemember()));
    }

    private void initListeners() {
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FindActivity.class);
                intent.putExtra("kims_game", game);
                startActivity(intent);
                finish();
            }
        });
    }
}
