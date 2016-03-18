package no.woact.pg4600.assignment1.ronesp13.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import no.woact.pg4600.assignment1.ronesp13.R;
import no.woact.pg4600.assignment1.ronesp13.models.GameState;
import no.woact.pg4600.assignment1.ronesp13.models.TicTacToe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Game extends Activity {

    private Context context;
    private TextView playerOneLabel;
    private TextView playerTwoLabel;
    private Button startNewGame;
    private Button displayHistory;

    private TicTacToe ticTacToe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        context = this;
        initWidgets();
        initListeners();
        setPlayerInformation();
        ticTacToe = new TicTacToe();
    }

    private void initWidgets() {
        playerOneLabel = (TextView) findViewById(R.id.player_one_label);
        playerOneLabel.setTextColor(getResources().getColor(R.color.active_player));
        playerTwoLabel = (TextView) findViewById(R.id.player_two_label);
        startNewGame = (Button) findViewById(R.id.start_new_game_button);
        displayHistory = (Button) findViewById(R.id.history_button);
    }

    private void initListeners() {
        int row = 0;
        int column = 0;
        ArrayList<View> buttons = findViewById(R.id.game_grid).getTouchables();
        for (View view : buttons) {
            Button button = (Button) view;
            button.setOnClickListener(new GameButton(row, column++));
            if (column == 3) {
                row++;
                column = 0;
            }
        }
        startNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, Start.class));
                finish();
            }
        });
        displayHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, History.class));
            }
        });
    }

    private void saveHistory(String status) {
        long time = System.currentTimeMillis();
        SharedPreferences settings = getSharedPreferences("history", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        if (settings.getAll().size() == 5) {
            updateHistory(settings.getAll(), editor, time, status);
        } else {
            editor.putString(String.valueOf(time), status).commit();
        }
    }

    private void updateHistory(Map<String, ?> history, SharedPreferences.Editor editor, long time, String status) {
        long[] timestamps = new long[5];
        int i = 0;
        for (String key : history.keySet()) { // loop through all keys
            timestamps[i++] = Long.parseLong(key);
        }
        Arrays.sort(timestamps);
        editor.remove(String.valueOf(timestamps[0])) // remove the oldest item from the map
                .putString(String.valueOf(time), status)
                .commit();
    }

    private void setPlayerInformation() {
        Intent intent = getIntent();
        playerOneLabel.setText(intent.getExtras().getString("player_one"));
        playerTwoLabel.setText(intent.getExtras().getString("player_two"));
    }

    private void switchPlayersTurn(String playerIcon) {
        int active = getResources().getColor(R.color.active_player);
        int inactive = getResources().getColor(R.color.waiting_player);
        playerOneLabel.setTextColor(playerIcon.equals("X") ? active : inactive);
        playerTwoLabel.setTextColor(playerIcon.equals("X") ? inactive : active);
    }

    private class GameButton implements View.OnClickListener {

        private int row;
        private int column;

        public GameButton(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public void onClick(View v) {
            if (ticTacToe.isGameOver()) {
                return;
            }
            Button button = (Button) v;
            ticTacToe.move(row, column);
            if (ticTacToe.hasPlayerMoved()) {
                handleMove(button);
            }
        }

        private void handleMove(Button button) {
            button.setText(ticTacToe.getPlayerIcon());
            ticTacToe.update();
            if (ticTacToe.isGameOver()) {
                handleGameOver();
                return;
            }
            switchPlayersTurn(ticTacToe.getPlayerIcon());
        }

        private void handleGameOver() {
            GameState state = ticTacToe.getGameState();
            String result;
            switch (state) {
                case PLAYER_ONE_WON:
                    displayStatus(R.color.victory, R.string.winner, playerOneLabel.getText().toString());
                    result = String.format(getResources().getString(R.string.winner), playerOneLabel.getText().toString());
                    break;
                case PLAYER_TWO_WON:
                    displayStatus(R.color.victory, R.string.winner, playerTwoLabel.getText().toString());
                    result = String.format(getResources().getString(R.string.winner), playerTwoLabel.getText().toString());
                    break;
                case DRAW:
                    displayStatus(R.color.draw, R.string.draw, null);
                    result = getResources().getString(R.string.draw);
                    break;
                default:
                    Log.e("Game", "Game status is incorrect");
                    result = getResources().getString(R.string.error);
                    break;
            }
            saveHistory(result);
        }

        private void displayStatus(int color, int text, String name) {
            TextView view = (TextView) findViewById(R.id.game_result);
            view.setTextColor(getResources().getColor(color));
            if (name == null) {
                view.setText(getResources().getString(text));
            } else {
                view.setText(String.format(getResources().getString(text), name));
            }
        }
    }
}
